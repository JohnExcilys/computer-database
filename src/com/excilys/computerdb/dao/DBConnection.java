package com.excilys.computerdb.dao;

import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import java.sql.Connection;

public class DBConnection {
	private static BoneCP connectionPool = null;
	private static ThreadLocal<Connection> currentCon = new ThreadLocal<>();
	
	public static void initialize() throws SQLException {
		try {
			// load the database driver (make sure this is in your classpath!)
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		// setup the connection pool
		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull");
		config.setUsername("root");
		config.setPassword("admin");
		connectionPool = new BoneCP(config); // setup the connection pool
	}
	
	public static Connection getConnection() throws SQLException{
		return currentCon.get();
	}

	public static void openConnection() throws SQLException {
		if (connectionPool == null) {
			initialize();
		}
		if (currentCon != null && currentCon.get() != null && !currentCon.get().isClosed()) {
			currentCon.get().close();
		}
		currentCon.set(connectionPool.getConnection());
	}

	public static void closeConnection() throws SQLException {

		if (currentCon != null && currentCon.get() != null
				&& !currentCon.get().isClosed()) {
			currentCon.get().close();
		}
	}
}
