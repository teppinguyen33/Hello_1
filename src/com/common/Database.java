package com.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Database {

	private String host;
	private String database;
	private String username;
	private String password;
	private String driver;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	private Connection con;

	public Connection getCon() {
		if (con == null) {
			openConnection();
		}
		return con;
	}

	private void openConnection() {
		ApplicationContext context = null;
		try {
			// Lấy thông tin DB
			context = new ClassPathXmlApplicationContext(
					"manual-config.xml");
			Database database = (Database) context.getBean("database");
			((ClassPathXmlApplicationContext)context).close();
			
			// Tạo connection
			Class.forName(database.getDriver());
			con = DriverManager.getConnection(
					database.getHost() + database.getDatabase(),
					database.getUsername(), database.getPassword());
			con.setAutoCommit(false);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
	}

}
