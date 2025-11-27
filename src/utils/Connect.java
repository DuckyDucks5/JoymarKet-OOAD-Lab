package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	private String USERNAME = "root";
	private String PASSWORD = "";
	private String DATABASE = "joymarketdb";
	private String HOST = "localhost:3306";
	private String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

	private Connection conn;
	private static Connect connect;

	private Connect() {
		try {
			conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			System.out.println("Database connected!");
			System.out.println("Autocommit status: " + conn.getAutoCommit());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connect getInstance() {
		if (connect == null) {
			connect = new Connect();
		}
		return connect;
	}

	public Connection getConn() {
		return this.conn;
	}
}
