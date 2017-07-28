package com.jack.WebMagicDemo;

import org.apache.commons.codec.digest.DigestUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;

@SuppressWarnings("serial")
public class DuoLaVo implements AfterExtractor {
	private String url;
	private String title;
	private String author;
	private int level;
	private String time;
	private String content;
	private int isJing;
	private String urlMd5;

	public DuoLaVo() {
		super();
	}





	public DuoLaVo(String url, String title, String author, int level, String time, String content, int isJing) {
		super();
		this.url = url;
		this.title = title;
		this.author = author;
		this.level = level;
		this.time = time;
		this.content = content;
		this.isJing = isJing;
	}





	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}




	public int getIsJing() {
		return isJing;
	}

	public void setIsJing(int isJing) {
		this.isJing = isJing;
	}



	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
        this.urlMd5 = DigestUtils.md5Hex(url);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public void afterProcess(Page page) {
		// TODO Auto-generated method stub
		
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	


	public String getUrlMd5() {
		return urlMd5;
	}



	public void setUrlMd5(String urlMd5) {
		this.urlMd5 = urlMd5;
	}





	@Override
	public String toString() {
		return "DuoLaVo [url=" + url + ", title=" + title + ", author=" + author + ", time=" + time + ", isJing="
				+ isJing + "]";
	}

	
}
