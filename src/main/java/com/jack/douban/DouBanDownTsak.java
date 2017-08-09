package com.jack.douban;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import com.jack.ProxyPool.ProxyIp;
import com.jack.ProxyPool.ProxyPool;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.SimpleHttpClient;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

public class DouBanDownTsak implements Runnable {
	protected DouBanProcessor douBanProcessor=DouBanProcessor.getInstance();
	public void run() {
		try {
			String url = "https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=&start=0";
			SimpleHttpClient httpClient = new SimpleHttpClient(
					Site.me().setTimeOut(10000).setSleepTime(3000).setUserAgent(
							"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Mobile Safari/537.36"));
			ProxyIp proxyIp = ProxyPool.proxyQueue.take();
			if (proxyIp!=null) {
				httpClient.setProxyProvider(SimpleProxyProvider.from(new Proxy(proxyIp.getIp(), proxyIp.getPort())));
				Page page = httpClient.get(url);
				if (page.getHtml() != null && page.getStatusCode() == 200) {
					FileOutputStream fs = new FileOutputStream(new File("D:\\douban.txt"));
					PrintStream p = new PrintStream(fs);
					p.println(page.getHtml().toString());
					p.close();
				}else{
					douBanProcessor.getDouBanDownLoadThreadPooExecutor().execute(new DouBanDownTsak());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
