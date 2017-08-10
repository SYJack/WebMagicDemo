package com.jack.douban;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.List;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.SimpleHttpClient;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

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
			SimpleHttpClient httpClient = new SimpleHttpClient(
					Site.me().setTimeOut(10000).setRetryTimes(3).setSleepTime(5000).setUserAgent(
							"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Mobile Safari/537.36"));
			/* ProxyIp proxyIp = ProxyPool.proxyQueue.take(); */
			String proxyLine = ReadFromFile.RandomReadLine();
			if (proxyLine != null) {
				httpClient.setProxyProvider(SimpleProxyProvider
						.from(new Proxy(proxyLine.split(":")[0], Integer.parseInt(proxyLine.split(":")[1]))));
				Page page = httpClient.get(url);
				if (page.getRawText() != null && page.getStatusCode() == 200) {
					FileOutputStream fs = new FileOutputStream(new File("D:\\爬虫测试\\douban_" + start + ".txt"));
					PrintStream p = new PrintStream(fs);
					p.println(page.getRawText().toString());
					p.close();
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
							"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Mobile Safari/537.36"));
			Page page = httpClient.get(url);
			if (page.getRawText() != null && page.getStatusCode() == 200) {
				parseJson(page.getRawText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void parseJson(String content){
		String baseJsonPath = "$.data.length()";
		DocumentContext dc = JsonPath.parse(content);
		Integer userCount = dc.read(baseJsonPath);
		for (int i = 0; i < userCount; i++) {
			String userBaseJsonPath = "$.data[" + i + "]";
			String url = dc.read(userBaseJsonPath + ".url");
		}
	}
}
