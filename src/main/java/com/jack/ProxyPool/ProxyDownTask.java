package com.jack.ProxyPool;

import java.util.List;

import us.codecraft.webmagic.Page;

public class ProxyDownTask implements Runnable {

	private String url;
	private Page page;// 是否通过代理下载
	protected static ProxyProcessor proxyProcessor = ProxyProcessor.getInstance();
	
	public ProxyDownTask(String url, Page page) {
		this.url = url;
		this.page = page;
	}
	public void run() {
		ProxyListPageParser parser=ProxyListPageParserFactory.getProxyListPageParser(ProxyPool.proxyMap.get(url));
		List<Proxy> proxyList = parser.parse(page);
		for (Proxy proxy : proxyList) {
			System.out.println(url+"---->"+proxy.getProxyStr());
		}
	}

}
