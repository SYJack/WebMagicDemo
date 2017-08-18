package com.jack.douban.model;

import org.apache.commons.codec.digest.DigestUtils;

public class MovieSubjects {
	
	/**
	 * 电影id
	 */
	private int movieId;
	private int doubanMovieId;
	private String name;
	private String director;
	private String actors;
	private String country;
	private String language;
	private String releaseData;
	private String runTime;
	private Float ratingNum;
	private String tags;
	private String type;
	private String url;
	private String urlMd5;
	
	

	public MovieSubjects() {
	}



	public MovieSubjects(int movieId, int doubanMovieId,String name, String director, String actors, String country, String language,
			String releaseData, String runTime, Float ratingNum, String tags, String type, String url, String urlMd5) {
		this.movieId = movieId;
		this.doubanMovieId=doubanMovieId;
		this.name = name;
		this.director = director;
		this.actors = actors;
		this.country = country;
		this.language = language;
		this.releaseData = releaseData;
		this.runTime = runTime;
		this.ratingNum = ratingNum;
		this.tags = tags;
		this.type = type;
		this.url = url;
		this.urlMd5 = urlMd5;
	}



	public int getMovieId() {
		return movieId;
	}



	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}


	public int getDoubanMovieId() {
		return doubanMovieId;
	}



	public void setDoubanMovieId(int doubanMovieId) {
		this.doubanMovieId = doubanMovieId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDirector() {
		return director;
	}



	public void setDirector(String director) {
		this.director = director;
	}



	public String getActors() {
		return actors;
	}



	public void setActors(String actors) {
		this.actors = actors;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getLanguage() {
		return language;
	}



	public void setLanguage(String language) {
		this.language = language;
	}



	public String getReleaseData() {
		return releaseData;
	}



	public void setReleaseData(String releaseData) {
		this.releaseData = releaseData;
	}



	public String getRunTime() {
		return runTime;
	}



	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public Float getRatingNum() {
		return ratingNum;
	}



	public void setRatingNum(Float ratingNum) {
		this.ratingNum = ratingNum;
	}



	public String getTags() {
		return tags;
	}



	public void setTags(String tags) {
		this.tags = tags;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
		this.urlMd5 = DigestUtils.md5Hex(url);
	}



	public String getUrlMd5() {
		return urlMd5;
	}



	public void setUrlMd5(String urlMd5) {
		this.urlMd5 = urlMd5;
	}
	
	
}
