package com.jack.douban;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jack.douban.jdbc.CommentsDaoImpl;
import com.jack.douban.jdbc.MoviesDAOImpl;
import com.jack.douban.model.Comments;
import com.jack.douban.model.MovieSubjects;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.SimpleHttpClient;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Selectable;

public class DouBanDownTsak implements Runnable {
	protected DouBanProcessor douBanProcessor;
	private int start;
	private static MoviesDAOImpl moviesDAOImpl = MoviesDAOImpl.getInstance();
	private static CommentsDaoImpl commentsDaoImpl = CommentsDaoImpl.getInstance();

	public DouBanDownTsak(int start) {
		this.start = start;
		douBanProcessor = DouBanProcessor.getInstance();
	}

	public void run() {
		try {
			String url = "https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=&start=" + start;
			/* ProxyIp proxyIp = ProxyPool.proxyQueue.take(); */
			String proxyLine = ReadFromFile.RandomReadLine();
			if (proxyLine != null) {
				douBanProcessor.getHttpClient().setProxyProvider(SimpleProxyProvider
						.from(new Proxy(proxyLine.split(":")[0], Integer.parseInt(proxyLine.split(":")[1]))));
				Page page = douBanProcessor.getHttpClient().get(url);
				if (page.getRawText() != null && page.getStatusCode() == 200) {

					/*
					 * FileOutputStream fs = new FileOutputStream(new
					 * File("D:\\爬虫测试\\douban" + start + ".txt")); PrintStream p
					 * = new PrintStream(fs);
					 * p.println(page.getRawText().toString()); p.close();
					 */

					parseUrlJson(page.getRawText(), douBanProcessor.getHttpClient());
				} else {
					douBanProcessor.getDouBanDownLoadThreadPooExecutor().execute(new DouBanDownTsak(start));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static void main(String[] args) { try { String url =
	 * "https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=&start=0";
	 * SimpleHttpClient httpClient = new SimpleHttpClient(
	 * Site.me().setTimeOut(10000).setRetryTimes(3).setSleepTime(5000).
	 * setUserAgent(
	 * "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Safari/537.36"
	 * )); Page page = httpClient.get(url); if (page.getRawText() != null &&
	 * page.getStatusCode() == 200) { parseUrlJson(page.getRawText(),
	 * httpClient); } } catch (Exception e) { e.printStackTrace(); } }
	 */

	public void parseUrlJson(String content, SimpleHttpClient httpClient) {
		String proxyLine = ReadFromFile.RandomReadLine();
		if (proxyLine != null) {
			httpClient.setProxyProvider(SimpleProxyProvider
					.from(new Proxy(proxyLine.split(":")[0], Integer.parseInt(proxyLine.split(":")[1]))));
			String baseJsonPath = "$.data.length()";
			DocumentContext dc = JsonPath.parse(content);
			Integer movieCount = dc.read(baseJsonPath);
			for (int i = 0; i < movieCount; i++) {
				String userBaseJsonPath = "$.data[" + i + "]";
				String url = dc.read(userBaseJsonPath + ".url");
				String doubanMovieId = dc.read(userBaseJsonPath + ".id");
				parseMovieInfo(httpClient.get(url), url, doubanMovieId);
				String commentUrl = "https://movie.douban.com/subject/" + doubanMovieId
						+ "/comments?sort=new_score&status=P";
				parseMovieComment(httpClient.get(commentUrl), doubanMovieId);
			}
		}
	}

	private void parseMovieComment(Page page, String doubanMovieId) {
		try {
			String movieName = page.getHtml().xpath("//h1/text()").toString().split(" ")[0];
			Selectable selectable = page.getHtml().xpath("//div[@id='comments']");
			List<Selectable> commentItemLs = selectable.xpath("//div[@class='comment-item']").nodes();
			for (Selectable commentItem : commentItemLs) {
				Comments comments = new Comments();
				comments.setDoubanMovieId(Integer.parseInt(doubanMovieId));
				comments.setMovieName(movieName);
				String commentAuthor = commentItem.xpath("//span[@class='comment-info']//a/text()").toString();
				comments.setCommentAuthor(commentAuthor);
				String commentInfo = commentItem.xpath("//p/text()").toString();
				comments.setCommentInfo(commentInfo);
				String commentVote = commentItem.xpath("//span[@class='votes']/text()").toString();
				comments.setCommentVote(Integer.parseInt(commentVote));
				commentsDaoImpl.insert(comments);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parseMovieInfo(Page page, String url, String doubanMovieId) {
		try {
			MovieSubjects movieSubjects = new MovieSubjects();
			movieSubjects.setDoubanMovieId(Integer.parseInt(doubanMovieId));
			String title = page.getHtml().$("h1 span[property=v:itemreviewed]", "text").toString();
			movieSubjects.setName(title);
			Selectable selectable = page.getHtml().xpath("//div[@id='info']");
			List<String> director = selectable.$("span:eq(0)").$("a[rel=v:directedBy]", "text").all();
			movieSubjects.setDirector(listToString(director, '/'));
			List<String> actor = selectable.$("span.actor").$("span.attrs a", "text").all();
			movieSubjects.setActors(listToString(actor, '/'));
			List<String> type = selectable.$("span[property=v:genre]", "text").all();
			movieSubjects.setType(listToString(type, '/'));
			String runTime = selectable.$("span[property=v:runtime]", "text").toString();
			movieSubjects.setRunTime(runTime);

			String language = selectable.regex(".语言:</span>.+\\s+<br>").toString().split("</span>")[1].split("<br>")[0]
					.trim();
			movieSubjects.setLanguage(language);

			String country = selectable.regex(".制片国家/地区:</span>.+[\\u4e00-\\u9fa5]+.+[\\u4e00-\\u9fa5]+\\s+<br>")
					.toString().split("</span>")[1].split("<br>")[0].trim();
			movieSubjects.setCountry(country);

			List<String> releaseData = selectable.$("span[property=v:initialReleaseDate]", "text").all();
			movieSubjects.setReleaseData(listToString(releaseData, '/'));

			String ratingNum = page.getHtml().$("strong[property=v:average]", "text").toString();
			movieSubjects.setRatingNum(Float.valueOf(ratingNum));

			List<String> tags = page.getHtml().xpath("//div[@class='tags-body']//a/text()").all();
			movieSubjects.setTags(listToString(tags, '/'));
			movieSubjects.setUrl(url);
			moviesDAOImpl.insert(movieSubjects);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * private void setUserInfoByJsonPth(MovieSubjects movieSubjects, String
	 * fieldName) { try { Field field =
	 * movieSubjects.getClass().getDeclaredField(fieldName);
	 * field.setAccessible(true); field.set(movieSubjects); } catch (Exception
	 * e) { e.printStackTrace(); } }
	 */
	public static String listToString(List<String> list, char separator) {
		return StringUtils.join(list.toArray(), separator);
	}
}
