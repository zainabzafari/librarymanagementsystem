package libraryBackend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	
	 private static final String DB_URL = "jdbc:mysql://localhost:3306/LibraryManagementSystem";
	    private static final String DB_USER = "root"; // Replace with your MySQL username
	    private static final String DB_PASSWORD = "Elias$#22"; // Replace with your MySQL password

	    // Method to establish database connection
	    public static Connection getConnection() throws SQLException {
	        Connection connection = null;
	        try {
	            // Register JDBC driver
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            // Open connection
	            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	            throw new SQLException("Failed to connect to database.");
	        }
	        return connection;
	    }
	
	public static void main(String [] args ) {
		try {
            Connection connection = DatabaseConnector.getConnection();
            if (connection != null) {
                System.out.println("Connected to the database.");
                connection.close(); // Close the connection
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
		
	}

}
