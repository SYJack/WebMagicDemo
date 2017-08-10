package com.jack.ProxyPool;

import org.apache.log4j.Logger;

import com.jack.WebMagicDemo.App;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.SimpleHttpClient;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

public class ProxyTestTask implements Runnable {
	private final static Logger logger = Logger.getLogger(ProxyTestTask.class);

	private ProxyIp proxyIp;

	public ProxyTestTask(ProxyIp p) {
		this.proxyIp = p;
	}

	public void run() {
		try {
			long startTime = System.currentTimeMillis();
			SimpleHttpClient httpClient = new SimpleHttpClient(
					Site.me().setTimeOut(10000).setSleepTime(3000).setUserAgent(
							"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Mobile Safari/537.36"));
			httpClient.setProxyProvider(SimpleProxyProvider.from(new Proxy(proxyIp.getIp(), proxyIp.getPort())));
			Page page = httpClient.get("https://www.zhihu.com");
			long endTime = System.currentTimeMillis();
			if (page.getRawText() != null && page.getStatusCode() == 200) {
				if (!ProxyPool.proxySet.contains(proxyIp)) {
					logger.debug(proxyIp.toString() + "----------代理可用--------请求耗时:" + (endTime - startTime) + "ms");
					ProxyPool.proxySet.add(proxyIp);
					/*ProxyPool.proxyQueue.add(proxyIp);*/
					// 保存数据到文件
					App.save();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		SimpleHttpClient httpClient = new SimpleHttpClient(Site.me().setTimeOut(10000).setSleepTime(3000).setUserAgent(
				"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Mobile Safari/537.36"));
		httpClient.setProxyProvider(SimpleProxyProvider.from(new Proxy("116.52.52.177", 9999)));
		Page page = httpClient.get("https://www.zhihu.com");
		System.out.println(page.getStatusCode());

		long endTime = System.currentTimeMillis();
		if (page.getHtml() != null && page.getStatusCode() == 200) {
			System.out.println("----------代理可用--------请求耗时:" + (endTime - startTime) + "ms");
		}
	}
}
