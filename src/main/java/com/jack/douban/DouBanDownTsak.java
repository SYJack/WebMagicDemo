package com.jack.douban;

import java.util.List;

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
					 * File("D:\\爬虫测试\\douban_" + start + ".txt")); PrintStream
					 * p = new PrintStream(fs);
					 * p.println(page.getRawText().toString()); p.close();
					 */
					/*parseUrlJson(page.getRawText());*/
				} else {
					douBanProcessor.getDouBanDownLoadThreadPooExecutor().execute(new DouBanDownTsak(start));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			String url = "https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=&start=0";
			SimpleHttpClient httpClient = new SimpleHttpClient(
					Site.me().setTimeOut(10000).setRetryTimes(3).setSleepTime(5000).setUserAgent(
							"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Safari/537.36"));
			Page page = httpClient.get(url);
			if (page.getRawText() != null && page.getStatusCode() == 200) {
				parseUrlJson(page.getRawText(),httpClient);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void parseUrlJson(String content,SimpleHttpClient httpClient) {
		String baseJsonPath = "$.data.length()";
		DocumentContext dc = JsonPath.parse(content);
		Integer userCount = dc.read(baseJsonPath);
		for (int i = 0; i < 1; i++) {
			String userBaseJsonPath = "$.data[" + i + "]";
			String url = dc.read(userBaseJsonPath + ".url");
			parseMovieInfo(httpClient.get(url));
		}
	}
	public static void parseMovieInfo(Page page){
		String name_css="div.content h1 span:eq(0)";
		String director_css="div.info span#attrs:eq(0)";
		/*List<Selectable> nodes=page.getHtml().$("div#info>span").nodes();
		for (Selectable selectable : nodes) {
			System.out.println(selectable.toString());
		}*/
		Selectable selectable=page.getHtml().xpath("//div[@id='info']");
		String runTime=selectable.$("span[property=v:runtime]","text").toString();
		String genre=selectable.$("span[property=v:genre]","text").toString();
		String actor=selectable.$("span.actor").toString();
		System.out.println(actor);
		/*List<Selectable> nodes=page.getHtml().xpath("//div[@id='info']").nodes();*/
	
	}
}
