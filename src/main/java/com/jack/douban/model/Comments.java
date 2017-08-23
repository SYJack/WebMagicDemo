package com.jack.douban.model;

public class Comments {
	private int commentsId;
	private int doubanMovieId;
	private String movieName;
	private String commentInfo;
	private String commentAuthor;
	private int commentVote;
	
	public Comments() {
	}

	public Comments(int commentsId, int doubanMovieId, String movieName, String commentInfo, String commentAuthor,
			int commentVote) {
		this.commentsId = commentsId;
		this.doubanMovieId = doubanMovieId;
		this.movieName = movieName;
		this.commentInfo = commentInfo;
		this.commentAuthor = commentAuthor;
		this.commentVote = commentVote;
	}

	public int getCommentsId() {
		return commentsId;
	}

	public void setCommentsId(int commentsId) {
		this.commentsId = commentsId;
	}

	public int getDoubanMovieId() {
		return doubanMovieId;
	}

	public void setDoubanMovieId(int doubanMovieId) {
		this.doubanMovieId = doubanMovieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getCommentInfo() {
		return commentInfo;
	}

	public void setCommentInfo(String commentInfo) {
		this.commentInfo = commentInfo;
	}

	public String getCommentAuthor() {
		return commentAuthor;
	}

	public void setCommentAuthor(String commentAuthor) {
		this.commentAuthor = commentAuthor;
	}

	public int getCommentVote() {
		return commentVote;
	}

	public void setCommentVote(int commentVote) {
		this.commentVote = commentVote;
	}
}
