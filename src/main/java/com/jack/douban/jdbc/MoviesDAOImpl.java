package com.jack.douban.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.jack.douban.model.MovieSubjects;
import com.mysql.jdbc.PreparedStatement;


public class MoviesDAOImpl {
	private Connection con = DBStatement.getCon();
	public boolean insert(MovieSubjects subjectsInfo) {
		String sql = "insert into movieSubjects "
				+ "(doubanMovieId,name,director,actors,country,language,releaseData,runTime,ratingNum,tags,type,url,urlMd5) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setInt(1, subjectsInfo.getDoubanMovieId());
			pstmt.setString(2, subjectsInfo.getName());
			pstmt.setString(3, subjectsInfo.getDirector());
			pstmt.setString(4, subjectsInfo.getActors());
			pstmt.setString(5, subjectsInfo.getCountry());
			pstmt.setString(6, subjectsInfo.getLanguage());
			pstmt.setString(7, subjectsInfo.getReleaseData());
			pstmt.setString(8, subjectsInfo.getRunTime());
			pstmt.setFloat(9, subjectsInfo.getRatingNum());
			pstmt.setString(10, subjectsInfo.getTags());
			pstmt.setString(11, subjectsInfo.getType());
			pstmt.setString(12, subjectsInfo.getUrl());
			pstmt.setString(13, subjectsInfo.getUrlMd5());
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
