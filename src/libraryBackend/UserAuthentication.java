package libraryBackend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserAuthentication {
	
	

	    // Method to authenticate user
	    public static Optional<String> authenticateUser(String username, String password) {
	        // Establish database connection
	        try (Connection connection = DatabaseConnector.getConnection()) {
	            // Prepare SQL query to check user credentials
	            String query = "SELECT userType FROM Member WHERE Username = ? AND Password = ?";
	            try (PreparedStatement statement = connection.prepareStatement(query)) {
	                statement.setString(1, username);
	                statement.setString(2, password);
	                // Execute query
	                try (ResultSet resultSet = statement.executeQuery()) {
	                    if (resultSet.next()) {
	                        // User authenticated, return user type
	                        return Optional.of(resultSet.getString("userType"));
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle database errors
	            System.err.println("Error occurred while authenticating user: " + e.getMessage());
	        }
	        // Authentication failed, return empty optional
	        return Optional.empty();
	    }
	}


