package com.jack.WebMagicDemo;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;




public class DuoLaDao {
	private Connection con = DBStatement.getCon();
	
	public boolean insert(DuoLaVo duoLaVo) {
		String sql = "insert into duola "
				+ "(url,isJing,title,author,level,time,content,urlMd5) "
				+ "values (?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, duoLaVo.getUrl());
			pstmt.setInt(2, duoLaVo.getIsJing());
			pstmt.setString(3, duoLaVo.getTitle());
			pstmt.setString(4, duoLaVo.getAuthor());
			pstmt.setInt(5, duoLaVo.getLevel());
			pstmt.setString(6, duoLaVo.getTime());
			pstmt.setString(7, duoLaVo.getContent());
			pstmt.setString(8, duoLaVo.getUrlMd5());
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
