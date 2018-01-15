package com.yryz.generate.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DbConn {

	// 定义一个连接对象
	private Connection conn = null;

	/**
	 * 数据库名称
	 */
	public String databaseName;

	public String ipName;

	public String portName;

	/**
	 * 定义连接数据库的url资源
	 */
	private String url;

	/**
	 * 定义连接数据库的用户名
	 */
	private String userName = "mycat-activeii";

	/**
	 * 定义连接数据库的密码
	 */
	private String passWord = "123qwe";

	public DbConn(Map<String, Object> dbData) {
		this.databaseName = dbData.get("databaseName").toString();
		this.ipName = dbData.get("ipName").toString();
		this.portName = dbData.get("portName").toString();
		this.userName = dbData.get("userName").toString();
		this.passWord = dbData.get("passWord").toString();
		this.url= "jdbc:mysql://" + ipName + ":" + portName + "/" + databaseName;
	}

	// 加载数据库连接驱动
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, userName, passWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 关闭数据库
	public void closeALL(Connection conn, Statement st, ResultSet rs, PreparedStatement pst) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (conn != null) {
				conn.close();
			}
			if (pst != null)
				pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
