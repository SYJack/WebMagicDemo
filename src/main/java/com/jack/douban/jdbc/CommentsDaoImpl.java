package com.jack.douban.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.jack.douban.model.Comments;
import com.mysql.jdbc.PreparedStatement;

public class CommentsDaoImpl {
	private volatile static CommentsDaoImpl commentsDaoImpl = null;
	public synchronized static CommentsDaoImpl getInstance() {
		if (commentsDaoImpl == null) {
			synchronized (CommentsDaoImpl.class) {
				if (commentsDaoImpl == null) {
					commentsDaoImpl = new CommentsDaoImpl();
				}
			}
		}
		return commentsDaoImpl;
	}
	private Connection con = DBStatement.getCon();
	public boolean insert(Comments comments) {
		String sql = "insert into comments "
				+ "(doubanMovieId,movieName,commentInfo,commentAuthor,commentVote) "
				+ "values (?,?,?,?,?)";
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setInt(1, comments.getDoubanMovieId());
			pstmt.setString(2, comments.getMovieName());
			pstmt.setString(3, comments.getCommentInfo());
			pstmt.setString(4, comments.getCommentAuthor());
			pstmt.setInt(5, comments.getCommentVote());
			// 存储user
			int i = pstmt.executeUpdate();
			if (i > 0)
				return flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}	
