package com.jack.WebMagicDemo;

import com.jack.ProxyPool.ProxyPool;
import com.jack.ProxyPool.ProxyProcessor;

import us.codecraft.webmagic.Spider;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		for (String url : ProxyPool.proxyMap.keySet()) {
			System.out.println(url);
			Spider.create(ProxyProcessor.getInstance(url)).addUrl(url).run();			
		}
	}
}
