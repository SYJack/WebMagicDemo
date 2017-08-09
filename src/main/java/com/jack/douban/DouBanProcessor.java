package com.jack.douban;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DouBanProcessor {

	private ThreadPoolExecutor douBanDownLoadThreadPooExecutor;
	private volatile static DouBanProcessor instance;

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
		douBanDownLoadThreadPooExecutor = new ThreadPoolExecutor(100, 100, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(2000), new ThreadPoolExecutor.DiscardPolicy());
	}

	public void startCrawl() {
		douBanDownLoadThreadPooExecutor.execute(new DouBanDownTsak());
	}

	public ThreadPoolExecutor getDouBanDownLoadThreadPooExecutor() {
		return douBanDownLoadThreadPooExecutor;
	}
}
