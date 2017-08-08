package com.jack.WebMagicDemo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;

public class DoubanMovieProcessor implements PageProcessor {

	private Site site=Site.me().setDomain("movie.douban.com").setSleepTime(3000).setRetryTimes(3);
	public void process(Page page) {
		System.out.println(page.getHtml().toString());
	}

	public Site getSite() {
		return site;
	}
	public static void main(String[] args) {
		int sd=0;
		while(true){
			String url="https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=&start="+sd;
			Spider spider=Spider.create(new DoubanMovieProcessor()).addUrl(url);
			spider.thread(5).run();
			sd+=20;
		}
		
	}
}
