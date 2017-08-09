package com.jack.ProxyPool;

import java.util.List;

import us.codecraft.webmagic.Page;


public interface ProxyListPageParser extends Parser{
	/**
	 * 是否只要匿名代理
	 */
	static final boolean anonymousFlag = true;

	List<ProxyIp> parse(Page page);
}
 