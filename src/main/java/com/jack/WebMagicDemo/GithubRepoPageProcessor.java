package com.jack.WebMagicDemo;

import javax.management.JMException;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class GithubRepoPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
	}

	public static void main(String[] args) {
		try {
			Spider spider=Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").addPipeline(new ConsolePipeline()).addPipeline(new JsonFilePipeline("D:\\webmagic\\")).thread(5);
			SpiderMonitor.instance().register(spider);
			spider.run();
		} catch (JMException e) {
			e.printStackTrace();
		}
	}

}
