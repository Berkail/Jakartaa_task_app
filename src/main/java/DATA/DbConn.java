package DATA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConn {

	private static DbConn instance;
	private static Connection connection;

	// Database credentials
	private static final String URL = "jdbc:mysql://localhost:3306/task_app_db";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	// Private constructor to prevent instantiation
	private DbConn() {
		try {
			// Establish the connection
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Database connected successfully!");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Public method to get the single instance
	public static synchronized DbConn getInstance() {
		if (instance == null) {
			instance = new DbConn();
		}
		return instance;
	}

	// Method to get the connection
	public Connection getConnection() {
		return connection;
	}

	// Close the connection (optional but good practice)
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				System.out.println("Database connection closed!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
