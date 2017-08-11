package com.jack.douban;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import us.codecraft.webmagic.SimpleHttpClient;
import us.codecraft.webmagic.Site;

public class DouBanProcessor extends AbstractProcessor{

	private ThreadPoolExecutor douBanDownLoadThreadPooExecutor;
	private volatile static DouBanProcessor instance;
	private SimpleHttpClient httpClient;

	public static DouBanProcessor getInstance() {
		if (instance == null) {
			synchronized (DouBanProcessor.class) {
				if (instance == null) {
					instance = new DouBanProcessor();
				}
			}
		}
		return instance;
	}

	private DouBanProcessor() {
		initThread();
	}

	private void initThread() {
		douBanDownLoadThreadPooExecutor = new ThreadPoolExecutor(50, 50, 1L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(2000), new ThreadPoolExecutor.CallerRunsPolicy());
		httpClient= new SimpleHttpClient(
				Site.me().setTimeOut(10000).setRetryTimes(3).setSleepTime(5000).setUserAgent(
						"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Safari/537.36"));
	}

	public void startCrawl() {
		int i = 0;
		while (true) {
			douBanDownLoadThreadPooExecutor.execute(new DouBanDownTsak(i));
			i += 20;
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ThreadPoolExecutor getDouBanDownLoadThreadPooExecutor() {
		return douBanDownLoadThreadPooExecutor;
	}

	public SimpleHttpClient getHttpClient() {
		return httpClient;
	}
	
}
