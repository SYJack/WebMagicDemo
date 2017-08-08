package com.jack.ProxyPool;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

public class XicidailiProxyListPageParser implements ProxyListPageParser {

	public List<Proxy> parse(Page page) {
		List<Proxy> proxyList = new ArrayList<Proxy>();
		List<Selectable> nodes = page.getHtml().xpath("//table[@id='ip_list']//tr[@class]").nodes();
		for (Selectable selectable : nodes) {
			String isAnonymous = selectable.$("td:eq(4)", "text").toString();
			if (!anonymousFlag || isAnonymous.contains("匿")) {
				String ip = selectable.$("td:eq(1)", "text").toString();
				String port = selectable.$("td:eq(2)", "text").toString();
				proxyList.add(new Proxy(ip, Integer.valueOf(port)));
			}
		}
		return proxyList;
	}
}
