package com.jack.WebMagicDemo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class DuoLaPageProcessor implements PageProcessor {

	public static final String URL_POST = "//wapp.baidu.com/p/5181939003?lp=5028&mo_device=1&is_jingpost=0";
	public static final String URL_LIST = "https://tieba\\.baidu\\.com/f\\?kw=机器猫\\&ie=utf-8\\&pn=\\d+";
	private static int size = 0;// 共抓取到的帖子数量
	private Site site=Site
			.me()
			.setDomain("tieba.baidu.com")
			.setSleepTime(3000)
			.setRetryTimes(3)
			.setUserAgent(
					"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Mobile Safari/537.36");
	
	public void process(Page page) {
			String title_xpath="//div[@class='list_main']//div[@class='post_title_embed']/text()";
			String author_xpath="//div[@class='list_item_top_name']//span[@class='user_name']//a/text()";
			String time_xpath="//div[@class='list_item_top_name']//span[@class='list_item_time']/text()";
			String level_xpath="//div[@class='list_item_top_name']//span[@class='level']/text()";
			String content_xpath="//div[@class='list_main']//div[@class='content']/tidyText()";
			if(page.getUrl().regex(URL_LIST).match()){
				page.addTargetRequests(page.getHtml().xpath("//ul[@id='frslistcontent']").regex("/p/\\d+\\?lp=\\d+\\&amp;mo_device=1\\&amp;is_jingpost=[01]").all());
			}else{
			    size++;// 帖子数量加1
			    if(!"javascript:;".equals(page.getUrl().toString())){
			    	DuoLaVo vo=new DuoLaVo();
					vo.setUrl(page.getUrl().toString());
					vo.setTitle(page.getHtml().xpath(title_xpath).toString());
					page.putField("title", page.getHtml().xpath(title_xpath).toString());
					vo.setAuthor(page.getHtml().xpath(author_xpath).toString());
					vo.setTime(page.getHtml().xpath(time_xpath).toString());
					vo.setLevel(Integer.parseInt(page.getHtml().xpath(level_xpath).toString()));
					String s=page.getUrl().toString().split("&")[2].split("=")[1];
					vo.setIsJing(Integer.parseInt(s));
					vo.setContent(page.getHtml().xpath(content_xpath).toString());
					new DuoLaDao().insert(vo);
			    }
				
			}
	}

	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		int pn=10000;
		while (true) {
			String url="https://tieba.baidu.com/f?kw=机器猫&ie=utf-8&pn="+pn;
			Spider spider=Spider.create(new DuoLaPageProcessor()).addUrl(url).addPipeline(new TestPipeline());
			spider.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000))).thread(5).run();
			pn+=50;
		}
		/*Pattern patter = Pattern.compile("/p/\\d+\\?lp=\\d+\\&amp;mo_device=1\\&amp;is_jingpost=[01]");
		Matcher m=patter.matcher("/p/5238031821?lp=5027&amp;mo_device=1&amp;is_jingpost=0");
		
		System.out.println(m.matches());*/
	}
}
