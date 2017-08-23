package com.jack.WebMagicDemo;

import java.io.RandomAccessFile;

import com.jack.ProxyPool.BloomFilter;
import com.jack.ProxyPool.ProxyIp;
import com.jack.ProxyPool.ProxyPool;
import com.jack.ProxyPool.ProxyProcessor;
import com.jack.douban.DouBanProcessor;

/**
 * Hello world!
 *
 */
public class App {
	public static BloomFilter filterIP = new BloomFilter();

	public static void main(String[] args) {
		/*ProxyProcessor.startDownLoadProxy();*/
		/*Spider.create(DouBanProcessor.getInstance()).run();*/
		DouBanProcessor.getInstance().startCrawl();
	}

	public static void save() {
		try {
			// 打开一个随机访问文件流，按读写方式
			RandomAccessFile randomFile = new RandomAccessFile("src/main/resources/proxyip", "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);

			ProxyIp[] proxyArray = new ProxyIp[ProxyPool.proxySet.size()];
			int i = 0;
			for (ProxyIp proxy : ProxyPool.proxySet) {
				if (proxy != null) {
					proxyArray[i++] = proxy;
				}
			}
			for (int j = 0; j < proxyArray.length; j++) {
				if (filterIP.contains(proxyArray[j].getProxyStr())) {
					continue;
				}
				filterIP.add(proxyArray[j].getProxyStr());
				randomFile.writeBytes(proxyArray[j].getProxyStr() + "\r\n");
			}

			randomFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
