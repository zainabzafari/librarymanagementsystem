package libraryBackend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Library {
	

    // Add a new book to the database
    public static boolean addBook( String title, String author, String genre, String isbn) {
        try (Connection connection = DatabaseConnector.getConnection()) {
        	String sql = "INSERT INTO Book (Title, Author, Genre, ISBN, Status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, genre);
            statement.setString(4, isbn);
            statement.setString(5, BookStatus.AVAILABLE.name()); // Set the initial status

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Delete a book from the database
    public static boolean deleteBook(int bookId) {
    	
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "DELETE FROM Book WHERE bookId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookId);

            int rowsDeleted = statement.executeUpdate();
            
            if (rowsDeleted > 0) {
            	return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }
		return false;
        
    }

    // Search for books in the database
    public static List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM Book WHERE Title LIKE ? OR Author LIKE ? OR Genre LIKE ? OR ISBN LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            String searchKeyword = "%" + keyword + "%";
            statement.setString(1, searchKeyword);
            statement.setString(2, searchKeyword);
            statement.setString(3, searchKeyword);
            statement.setString(4, searchKeyword);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	int bookId = resultSet.getInt("BookId");
                String title = resultSet.getString("Title");
                String author = resultSet.getString("Author");
                String genre = resultSet.getString("Genre");
                String isbn = resultSet.getString("ISBN");
                String statusStr = resultSet.getString("Status");
                BookStatus status = BookStatus.valueOf(statusStr); 

                books.add(new Book(bookId,title, author, genre, isbn,status));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return books;
    }
    
 // Method to retrieve all books from the database
    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM Book";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int bookId = resultSet.getInt("bookId");
                String title = resultSet.getString("Title");
                String author = resultSet.getString("Author");
                String genre = resultSet.getString("Genre");
                String isbn = resultSet.getString("ISBN");
                String statusStr = resultSet.getString("Status");
                BookStatus status = BookStatus.valueOf(statusStr); 

                // Create Book object and add it to the list
                Book book = new Book( bookId,title, author, genre, isbn, status);
                books.add(book);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return books;
    }

	
    
    
    
   public static int addMember( String firstName, String lastName, String email, String phoneNumber, String address ) {
    // SQL query to insert a new member into the Member table
    String insertMemberSQL = "INSERT INTO Member ( FirstName, LastName,   Email, PhoneNo, Address) VALUES ( ?, ?, ?, ?, ?)";

    try (
        // Get a connection to the database
        Connection connection = DatabaseConnector.getConnection();
        // Create a prepared statement with the SQL query
        PreparedStatement preparedStatement = connection.prepareStatement(insertMemberSQL, Statement.RETURN_GENERATED_KEYS)
    ) {
        // Set the parameters of the prepared statement
        preparedStatement.setString(1,firstName );
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4,phoneNumber );
        preparedStatement.setString(5, address);
       

        // Execute the query to insert the new member
        int rowsAffected = preparedStatement.executeUpdate();

        // Check if the insertion was successful
        if (rowsAffected > 0) {
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);

               
            }
        }
    }
 catch (SQLException e) {
        e.printStackTrace();
    }

    // Return false indicating failure
    return -1;
}

  public static boolean addUserCredentials(int memberId, String username, String password) {
    String insertCredentialsSQL = "INSERT INTO UserCredentials (memberId, Username, Password) VALUES (?, ?, ?)";

    try (
        Connection connection = DatabaseConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertCredentialsSQL)
    ) {
        preparedStatement.setInt(1, memberId);
        preparedStatement.setString(2, username);
        preparedStatement.setString(3, password);

        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

  public static boolean deleteMember(String memberId) {
    // SQL query to delete user credentials from the UserCredentials table
    String deleteUserCredentialsSQL = "DELETE FROM UserCredentials WHERE MemberId = ?";
    // SQL query to delete a member from the Member table
    String deleteMemberSQL = "DELETE FROM Member WHERE MemberId = ?";

    try (
        // Get a connection to the database
        Connection connection = DatabaseConnector.getConnection();
        // Create prepared statements with the SQL queries
        PreparedStatement deleteCredentialsStatement = connection.prepareStatement(deleteUserCredentialsSQL);
        PreparedStatement deleteMemberStatement = connection.prepareStatement(deleteMemberSQL)
    ) {
        // Set the parameter of the prepared statement for deleting user credentials
        deleteCredentialsStatement.setString(1, memberId);

        // Execute the query to delete the user credentials
        deleteCredentialsStatement.executeUpdate();

        // Set the parameter of the prepared statement for deleting the member
        deleteMemberStatement.setString(1, memberId);

        // Execute the query to delete the member
        int rowsAffected = deleteMemberStatement.executeUpdate();

        // Check if the deletion was successful
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle any SQL exceptions
        // You may choose to log the exception or display an error message
        return false;
    }
}

  
  public static List<Member> searchMembers(String keyword) {
      List<Member> searchResult = new ArrayList<>();

      // SQL query to search for members based on keyword
      String searchSQL = "SELECT * FROM Member WHERE MemberId LIKE ? OR FirstName LIKE ? OR LastName LIKE ?";

      try (
          // Get a connection to the database
          Connection connection = DatabaseConnector.getConnection();
          // Create a prepared statement with the SQL query
          PreparedStatement preparedStatement = connection.prepareStatement(searchSQL)
      ) {
          // Set the parameters of the prepared statement
          preparedStatement.setString(1, "%" + keyword + "%");
          preparedStatement.setString(2, "%" + keyword + "%");
          preparedStatement.setString(3, "%" + keyword + "%");

          // Execute the query
          ResultSet resultSet = preparedStatement.executeQuery();

          // Iterate through the result set and add members to the search result list
          while (resultSet.next()) {
              String memberId = resultSet.getString("MemberId");
              String firstName = resultSet.getString("FirstName");
              String lastName = resultSet.getString("LastName");
              String address = resultSet.getString("Address");
              String phoneNo = resultSet.getString("PhoneNo");
              String email = resultSet.getString("Email");

              Member member = new Member(memberId, firstName, lastName, email, phoneNo, address);
              searchResult.add(member);
          }
      } catch (SQLException e) {
          e.printStackTrace();
          // Handle any SQL exceptions
          // You may choose to log the exception or display an error message
      }

      return searchResult;
  }

	 public static List<Member> getAllMembers() {
	        List<Member> members = new ArrayList<>();

	        // SQL query to retrieve all members from the Member table
	        String query = "SELECT * FROM Member";

	        try (
	            // Get a connection to the database
	            Connection connection = DatabaseConnector.getConnection();
	            // Create a prepared statement with the SQL query
	            PreparedStatement preparedStatement = connection.prepareStatement(query);
	            // Execute the query and get the result set
	            ResultSet resultSet = preparedStatement.executeQuery()
	        ) {
	            // Iterate through the result set and create Member objects
	            while (resultSet.next()) {
	                // Retrieve data from the result set
	                String memberId = resultSet.getString("MemberId");
	                String firstName = resultSet.getString("FirstName");
	                String lastName = resultSet.getString("LastName");
	                String address = resultSet.getString("Address");
	                String phoneNo = resultSet.getString("PhoneNo");
	                String email = resultSet.getString("Email");

	                // Create a new Member object and add it to the list
	                Member member = new Member(memberId, firstName, lastName, email, phoneNo,  address);
	                members.add(member);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle any SQL exceptions
	            // You may choose to log the exception or display an error message
	        }

	        // Return the list of members
	        return members;
	    }
    
	 public static boolean isUsernameTaken(String username) {
		    String query = "SELECT COUNT(*) FROM UserCredentials WHERE Username = ?";
		    try (Connection connection = DatabaseConnector.getConnection();
		         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		        preparedStatement.setString(1, username);
		        try (ResultSet resultSet = preparedStatement.executeQuery()) {
		            if (resultSet.next()) {
		                int count = resultSet.getInt(1);
		                return count > 0;
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Handle exception (logging, error message, etc.)
		    }
		    // Default to username not being taken if an exception occurs
		    return false;
		}

	 public static boolean isISBNUnique(String isbn) {
		    try (Connection connection = DatabaseConnector.getConnection()) {
		        String sql = "SELECT COUNT(*) AS count FROM Book WHERE isbn = ?";
		        PreparedStatement statement = connection.prepareStatement(sql);
		        statement.setString(1, isbn);
		        ResultSet resultSet = statement.executeQuery();
		        
		        if (resultSet.next()) {
		            int count = resultSet.getInt("count");
		            return count == 0; // Return true if no books with the same ISBN found
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		    
		    // If an exception occurs or the query fails, return false by default
		    return false;
		}
	 
	public static boolean updateMember(String memberId, String firstName, String lastName, String email, String phoneNo, String address) {
    // Connect to the database and execute the update query
    try (Connection connection = DatabaseConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement("UPDATE member SET firstName = ?, lastName = ?, email = ?, phoneNo = ?, address = ? WHERE memberId = ?")) {
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setString(3, email);
        statement.setString(4, phoneNo);
        statement.setString(5, address);
        statement.setString(6, memberId);
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0; // If one or more rows are affected, return true
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Return false if an exception occurs
    }
}

	 public static Member getMemberById(String memberId) {
		    Member member = null;
		    // Connect to the database and execute the query to retrieve the member by ID
		    try (Connection connection = DatabaseConnector.getConnection();
		         PreparedStatement statement = connection.prepareStatement("SELECT * FROM member WHERE memberId = ?")) {
		        statement.setString(1, memberId);
		        try (ResultSet resultSet = statement.executeQuery()) {
		            if (resultSet.next()) {
		                // Create a new Member object using parameterized constructor
		                member = new Member(
		                        resultSet.getString("memberId"),
		                        resultSet.getString("firstName"),
		                        resultSet.getString("lastName"),
		                        resultSet.getString("email"),
		                        resultSet.getString("phoneNo"),
		                        resultSet.getString("address")
		                );
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return member; // Return the retrieved Member object or null if no member found
		}
    
	 
	 public static boolean updateBook(int bookId, String Title, String author, String genre, String ISBN) {
		    // Connect to the database and execute the update query
		    try (Connection connection = DatabaseConnector.getConnection();
		         PreparedStatement statement = connection.prepareStatement("UPDATE book SET Title = ?, author = ?, genre = ?, ISBN = ? WHERE bookId = ?")) {
		        statement.setString(1, Title);
		        statement.setString(2, author);
		        statement.setString(3, genre);
		        statement.setString(4, ISBN);
		        statement.setInt(5, bookId);
		        int rowsAffected = statement.executeUpdate();
		        return rowsAffected > 0; // If one or more rows are affected, return true
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false; // Return false if an exception occurs
		    }
		}

	 public static Book getBookById(int bookId) {
				    Book book = null;
				    // Connect to the database and execute the query to retrieve the member by ID
				    try (Connection connection = DatabaseConnector.getConnection();
				         PreparedStatement statement = connection.prepareStatement("SELECT * FROM book WHERE bookId = ?")) {
				        statement.setInt(1, bookId);
				        try (ResultSet resultSet = statement.executeQuery()) {
				            if (resultSet.next()) {
				                // Create a new Member object using parameterized constructor
				                book = new Book(
				                        resultSet.getInt("bookId"),
				                        resultSet.getString("title"),
				                        resultSet.getString("author"),
				                        resultSet.getString("genre"),
				                        resultSet.getString("ISBN")
				                );
				            }
				        }
				    } catch (SQLException e) {
				        e.printStackTrace();
				    }
				    return book; // Return the retrieved Member object or null if no member found
				}
		    
}

