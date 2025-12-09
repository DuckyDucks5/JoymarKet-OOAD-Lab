package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    // Database credentials dan URL
	private String USERNAME = "root";
	private String PASSWORD = "";
	private String DATABASE = "joymarketdb";
	private String HOST = "localhost:3306";
	private String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

    // Koneksi dan instance singleton
	private Connection conn;
	private static Connect connect;

    
    // Konstruktor privat untuk singleton
	private Connect() {
		try {
			conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			System.out.println("Database connected!"); // Notifikasi berhasil koneksi
		} catch (SQLException e) {
			e.printStackTrace(); // Tangani exception SQL
		}
	}

    
    // Mendapatkan instance Connect (singleton)
	public static Connect getInstance() {
		if (connect == null) {
			connect = new Connect();
		}
		return connect;
	}

    
    // Mendapatkan objek Connection
	public Connection getConn() {
		return this.conn;
	}
}
