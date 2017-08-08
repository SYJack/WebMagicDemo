package com.jack.ProxyPool;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class ProxyTest implements PageProcessor {
	private Site site=Site.me().setSleepTime(3000).setRetryTimes(3);
	
	public void process(Page page) {
		try {
			ProxyListPageParser parser = (ProxyListPageParser) Ip66ProxyListPageParser.class.newInstance();
			parser.parse(page);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public Site getSite() {
		return site;
	}
	public static void main(String[] args) {
		Spider.create(new ProxyTest()).addUrl("http://www.66ip.cn/areaindex_1/1.html").run();
	}

}
