package com.jack.ProxyPool;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public abstract class AbstractProcessor implements PageProcessor{

	private Site site=Site
			.me()
			.setSleepTime(3000)
			.setRetryTimes(3)
			.setUserAgent(
					"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Mobile Safari/537.36");
	public void process(Page page) {
		
	}

	public Site getSite() {
		return site;
	}
}
