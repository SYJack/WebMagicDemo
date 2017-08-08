package com.jack.ProxyPool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import us.codecraft.webmagic.Page;

public class ProxyProcessor extends AbstractProcessor {
	private static volatile ProxyProcessor instance;
	private ThreadPoolExecutor proxyDownloadThreadExecutor;
	private ThreadPoolExecutor proxyTestThreadExecutor;
	private String url;

	public static ProxyProcessor getInstance(String url) {
		if (instance == null) {
			synchronized (ProxyProcessor.class) {
				instance = new ProxyProcessor(url);
			}
		}
		return instance;
	}
	public static ProxyProcessor getInstance() {
		if (instance == null) {
			synchronized (ProxyProcessor.class) {
				instance = new ProxyProcessor();
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

	public void startDownLoadProxy( final Page page) {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					proxyDownloadThreadExecutor.execute(new ProxyDownTask(url, page));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
		// new Thread(new SaveProxyTask()).start();
	}

	@Override
	public void process(Page page) {
		startDownLoadProxy(page);
	}

	private void initThread() {
		proxyTestThreadExecutor = new ThreadPoolExecutor(100, 100, 0, TimeUnit.MILLISECONDS,
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
