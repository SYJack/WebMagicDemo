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
		douBanDownLoadThreadPooExecutor = new ThreadPoolExecutor(50, 50, 1L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(2000), new ThreadPoolExecutor.CallerRunsPolicy());
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
}
