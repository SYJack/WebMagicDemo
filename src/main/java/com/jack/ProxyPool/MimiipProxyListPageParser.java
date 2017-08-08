package com.jack.ProxyPool;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

public class MimiipProxyListPageParser implements ProxyListPageParser {

	public List<Proxy> parse(Page page) {
		List<Proxy> proxyList = new ArrayList<Proxy>();
		List<Selectable> nodes = page.getHtml().xpath("//table[@class='list']//tr").nodes();
		for (Selectable selectable : nodes) {
			String isAnonymous = selectable.$("td:eq(3)", "text").toString();
			if (!anonymousFlag || isAnonymous.contains("匿")) {
				String ip = selectable.$("td:eq(0)", "text").toString();
				String port = selectable.$("td:eq(1)", "text").toString();
				proxyList.add(new Proxy(ip, Integer.valueOf(port)));
			}
		}
		return proxyList;
	}

}
