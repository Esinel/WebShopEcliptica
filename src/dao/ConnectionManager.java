package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

	private ConnectionManager() {}

	private static Connection connection;

	public static Connection getConnection() {
		try {
			if (connection == null) {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost/EclipticaDB", "root", "root");
			}
		} catch (Exception ex) {ex.printStackTrace();}

		return connection;
	}

	public static void closeConnection() {
		try {
			connection.close();
			connection = null;
		} catch (Exception ex) {ex.printStackTrace();}
	}

}
