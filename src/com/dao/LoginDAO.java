package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.common.Database;

public class LoginDAO extends Database {

	private static final String LOGIN_SEL = 
			"SELECT id FROM user WHERE username = ? AND password = ?";
	
	public String login(String username, String password) throws SQLException {
		Connection con = getCon();
		PreparedStatement statement = con.prepareStatement(LOGIN_SEL);
		statement.setString(1, username);
		statement.setString(2, password);
		ResultSet rs = statement.executeQuery();
		return rs.next() ? rs.getString("id") : null;
	}
}
