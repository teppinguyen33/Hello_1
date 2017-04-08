package com.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private static final String PREFIX = "jdbc:mysql://";
	private static final String HOST = "localhost:3306";
	private static final String DATABASE = "/teppi";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	private Connection con;

	public Connection getCon() {
		if (con == null) {
			openConnection();
		}
		return con;
	}

	private void openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(PREFIX + HOST + DATABASE,
					USERNAME, PASSWORD);
			con.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
