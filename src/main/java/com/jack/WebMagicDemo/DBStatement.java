package com.jack.WebMagicDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBStatement {
	Statement stmt;
	Connection con;

	public DBStatement() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String mySqlUrl = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useOldAliasMetadataBehavior=true";
			String username = "root";
			String password = "167349951";
			// 得到连接对象
			con = DriverManager.getConnection(mySqlUrl, username, password);
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final DBStatement dbStatement = new DBStatement();

	public synchronized static Statement getInstance() {
		return dbStatement.stmt;
	}

	public synchronized static Connection getCon() {
		return dbStatement.con;
	}
	public static void main(String[] args) {
		try {
			Connection con = DBStatement.getCon();
		} catch (Exception e) {
		}
		
		System.out.println("连接成功");
	}
}
