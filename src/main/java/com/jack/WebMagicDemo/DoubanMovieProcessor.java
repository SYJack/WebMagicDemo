package com.jack.WebMagicDemo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class DoubanMovieProcessor implements PageProcessor {

	private Site site=Site.me().setDomain("movie.douban.com").setSleepTime(3000).setRetryTimes(3);
	public void process(Page page) {

	}

	public Site getSite() {
		return null;
	}

}
