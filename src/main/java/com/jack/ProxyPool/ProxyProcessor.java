package com.jack.ProxyPool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;

public class ProxyProcessor extends AbstractProcessor {
	private static volatile ProxyProcessor instance;
	private ThreadPoolExecutor proxyDownloadThreadExecutor;
	private ThreadPoolExecutor proxyTestThreadExecutor;
	private String url;

	public static ProxyProcessor getInstance(String url) {
		if (instance == null) {
			synchronized (ProxyProcessor.class) {
				if (instance == null) {
					instance = new ProxyProcessor(url);
				}
			}
		}
		instance.url = url;
		return instance;
	}

	public static ProxyProcessor getInstance() {
		if (instance == null) {
			synchronized (ProxyProcessor.class) {
				if (instance == null) {
					instance = new ProxyProcessor();
				}
			}
		}
		return instance;
	}

	public ProxyProcessor() {
		initThread();
	}

	public ProxyProcessor(String url) {
		this.url = url;
		initThread();
	}

	@Override
	public void process(Page page) {
		startDownLoadProxy(page);
	}

	public void startDownLoadProxy(final Page page) {
		proxyDownloadThreadExecutor.execute(new ProxyDownTask(url, page));
	}

	public static void startDownLoadProxy() {
		new Thread(new Runnable() {
			public void run() {
				for (String url : ProxyPool.proxyMap.keySet()) {
					Spider.create(ProxyProcessor.getInstance(url)).addUrl(url).run();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	private void initThread() {
		proxyTestThreadExecutor = new ThreadPoolExecutor(20, 20, 0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(10000), new ThreadPoolExecutor.DiscardPolicy());
		proxyDownloadThreadExecutor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
	}

	public ThreadPoolExecutor getProxyDownloadThreadExecutor() {
		return proxyDownloadThreadExecutor;
	}

	public ThreadPoolExecutor getProxyTestThreadExecutor() {
		return proxyTestThreadExecutor;
	}
}
