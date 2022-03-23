package tw.over_the_counter_stock.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactotry {
	
	private static final Connection conn = createConnection();
	
	private static Connection createConnection() {
		try {
			String urlStr = "jdbc:sqlserver://localhost:1433;databaseName=OTCStock;user=sa;password=Passw0rd";
			Connection conn = DriverManager.getConnection(urlStr);
			return conn;
			
		} catch (SQLException e) {
			System.out.println("Connection Wrong!");
			return null;
		}
	}

	public static Connection getConnection() {
		return conn;
	}
	
	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Connection Close Wrong!");
		}
	}
	
	
}

