package libraryBackend;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ReservationManager {
	
	

    private static final String DB_URL = "jdbc:mysql://localhost:3306/LibraryManagementSystem";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Elias$#22";
    
    
    
    // Method to reserve a book
   public  boolean reserveBook(int memberId, int bookId, String title, String author, LocalDate startDate, LocalDate endDate) {
	   
	   try {
	        // Step 1: Validate the selected book and member information
	        if (!isValidMember(memberId) || !isValidBook(bookId)) {
	            System.out.println("Invalid member ID or book ID.");
	            return false;
	        }
	        
	        
	        
	        // Step 3: Check if the member has any overdue books
	        if (hasOverdueBooks(memberId)) {
	            System.out.println("You have overdue books. Please return them before making a reservation.");
	            return false;
	        }
	        
	        if(isBookAvailable(bookId, startDate, endDate) && !isBookReserved(bookId, startDate, endDate)) {
	        // Step 4: Create a new reservation record in the database
	        int success = createReservation(memberId, bookId, title, author, startDate, endDate);
	        if (success>0) {
	            System.out.println("Reservation successful.");
	            
	            return true;
	        } else {
	            System.out.println("Failed to create reservation.");
	        }
	    }
	        else if (!isBookAvailable(bookId, startDate, endDate) || isBookReserved(bookId, startDate, endDate) ) {

	            JOptionPane.showMessageDialog(null, "Sorry! The book is not availble during the specified period!");
	            return false;
	        }
	   
   }catch (Exception ex) {
	        ex.printStackTrace();
	    }
	return false;
	}
	   
	  
	     
	  
    private  int createReservation(int memberId, int bookId, String title, String author,LocalDate startDate, LocalDate endDate) {
    	int reservationId = 0; 
    	try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
    	        String sql = "INSERT INTO Reservation (MemberId, BookId, title, author, startDate, endDate, Status) VALUES (?, ?, ?, ?, ?,?,?)";
    	        PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
    	        statement.setInt(1, memberId);
    	        statement.setInt(2, bookId);
    	        statement.setString(3, title);
    	        statement.setString(4, author);
    	        statement.setDate(5, Date.valueOf(startDate));
    	        statement.setDate(6, Date.valueOf(endDate));
    	        statement.setString(7, ReservationStatus.DUE.name()); // Set initial status to OPEN
    	        
    	        int rowsInserted = statement.executeUpdate();
    	        
    	        
    	        if (rowsInserted > 0) {
    	            ResultSet generatedKeys = statement.getGeneratedKeys();
    	            if (generatedKeys.next()) {
    	                reservationId = generatedKeys.getInt(1); // Retrieve the auto-generated reservation ID
    	            }
    	        }
    	    } catch (SQLException ex) {
    	        ex.printStackTrace();
    	    }
    	    return reservationId;
    	}
	

    private static boolean isBookReserved(int bookId, LocalDate startDate, LocalDate endDate) {
    	boolean isreserved = false;
    	try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
    	
         
    		
            //String sql = "SELECT COUNT(*) FROM Reservation WHERE BookId = ? AND Status=? AND startDate <= ? AND endDate >= ? ";
           // String sql = "SELECT COUNT(*) FROM Reservation WHERE BookId = ? AND Status = ? AND ((? BETWEEN startDate AND endDate) OR (? BETWEEN startDate AND endDate)) AND startDate <= ? AND endDate >= ?";
            //AND((startDate <= ? AND endDate >= ?) OR (startDate >= ? AND endDate <= ?) OR (startDate <= ? AND endDate >= ?))
    		 String sql = "SELECT COUNT(*) FROM Reservation WHERE BookId = ? AND Status = ? AND ((? BETWEEN startDate AND endDate) OR (? BETWEEN startDate AND endDate) OR (startDate BETWEEN ? AND ?) OR (endDate BETWEEN ? AND ?))";
    		PreparedStatement statement = connection.prepareStatement(sql);
            statement = connection.prepareStatement(sql);
            statement.setInt(1, bookId);
            statement.setString(2, ReservationStatus.DUE.name());

            statement.setDate(3, java.sql.Date.valueOf(startDate));
            statement.setDate(4, java.sql.Date.valueOf(endDate));

            statement.setDate(5, java.sql.Date.valueOf(startDate));
            statement.setDate(6, java.sql.Date.valueOf(endDate));

            statement.setDate(7, java.sql.Date.valueOf(startDate));
            statement.setDate(8, java.sql.Date.valueOf(endDate));


            ResultSet resultSet = statement.executeQuery();
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    isreserved= true; // Book is already reserved during the specified period
                }
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return isreserved;
    }

 // Method to check if the book is available during the specified period
    public boolean isBookAvailable(int bookId, LocalDate startDate, LocalDate endDate) {
    	boolean isBookavailable = true;
    	try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
    		 String sql = "SELECT COUNT(*) FROM Borrowedbook WHERE BookId = ? AND Status = ? AND ((? BETWEEN startDate AND returnDate) OR (? BETWEEN startDate AND returnDate) OR (startDate BETWEEN ? AND ?) OR (returnDate BETWEEN ? AND ?))";
             
    		//String sql = "SELECT COUNT(*) FROM BorrowedBook WHERE BookId = ? AND Status = ? AND startDate >= ? AND returnDate <= ? ";
           
            //((startDate <= ? AND returnDate >= ?) OR (startDate >= ? AND returnDate <= ?) OR (startDate <= ? AND returnDate >= ?))
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookId);

            statement.setString(2, BorrowedBookStatus.CHECKED_OUT.name());
            statement.setDate(3, java.sql.Date.valueOf(startDate));
            statement.setDate(4, java.sql.Date.valueOf(endDate));

            statement.setDate(5, java.sql.Date.valueOf(startDate));
            statement.setDate(6, java.sql.Date.valueOf(endDate));

            statement.setDate(7, java.sql.Date.valueOf(startDate));
            statement.setDate(8, java.sql.Date.valueOf(endDate));
            
            
            

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                	isBookavailable = false; // Book is already checked out during the specified period
                }
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return isBookavailable;
    }
    
    
    public boolean isBookReservedByOtherMember(int bookId, int memberId, LocalDate startDate, LocalDate endDate) {
    	boolean isreserved = false;
    	try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
    	
         
            String sql = "SELECT COUNT(*) FROM Reservation WHERE BookId = ? AND memberId != ? AND Status=? AND ((? BETWEEN startDate AND endDate) OR (? BETWEEN startDate AND endDate) OR (startDate BETWEEN ? AND ?) OR (endDate BETWEEN ? AND ?)) ?";
            
            //((startDate <= ? AND endDate >= ?) OR (startDate >= ? AND endDate <= ?) OR (startDate <= ? AND endDate >= ?))
            PreparedStatement statement = connection.prepareStatement(sql);
            statement = connection.prepareStatement(sql);
            statement.setInt(1, bookId);
            statement.setInt(2,memberId);

            statement.setString(3,ReservationStatus.DUE.name());

            statement.setDate(4, java.sql.Date.valueOf(startDate));
            statement.setDate(5, java.sql.Date.valueOf(endDate));

            
            statement.setDate(6, java.sql.Date.valueOf(startDate));
            statement.setDate(7, java.sql.Date.valueOf(endDate));

            statement.setDate(8, java.sql.Date.valueOf(startDate));
            statement.setDate(9, java.sql.Date.valueOf(endDate));
           



            ResultSet resultSet = statement.executeQuery();
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    isreserved=true; // Book is already reserved during the specified period
                }
            }
          
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return isreserved;
    }
    
    public int isBookReservedByThisMember(int bookId, int memberId, LocalDate startDate, LocalDate endDate) {
    	int reservationId = -1;
    	
       
    	try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
    	    
         
            String sql = "SELECT reservationId FROM Reservation WHERE BookId = ? AND memberId = ? AND Status = ? AND ((? BETWEEN startDate AND endDate) OR (? BETWEEN startDate AND endDate) OR (startDate BETWEEN ? AND ?) OR (endDate BETWEEN ? AND ?))";
            //((startDate <= ? AND endDate >= ?) OR (startDate >= ? AND endDate <= ?) OR (startDate <= ? AND endDate >= ?))
            PreparedStatement statement = connection.prepareStatement(sql);
            statement = connection.prepareStatement(sql);
            statement.setInt(1, bookId);
            statement.setInt(2,memberId);
            statement.setString(3, ReservationStatus.DUE.name());

            statement.setDate(4, java.sql.Date.valueOf(startDate));
            statement.setDate(5, java.sql.Date.valueOf(endDate));


            statement.setDate(6, java.sql.Date.valueOf(startDate));
            statement.setDate(7, java.sql.Date.valueOf(endDate));

            statement.setDate(8, java.sql.Date.valueOf(startDate));
            statement.setDate(9, java.sql.Date.valueOf(endDate));

            ResultSet resultSet = statement.executeQuery();
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
            	reservationId = resultSet.getInt("reservationId");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return reservationId;
    	}
        
    private static boolean hasOverdueBooks(int memberId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT COUNT(*) FROM BorrowedBook WHERE MemberId = ? AND ReturnDate < ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, memberId);
            statement.setDate(2, Date.valueOf(LocalDate.now())); // Get the current date
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    // Method to update the book status in the database
    public void updateBookStatus(int bookId, BookStatus status) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE Book SET Status = ? WHERE BookId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, status.name());
            statement.setInt(2, bookId);

         
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("Failed to update book status: No rows affected.");
            } else {
                System.out.println("Book status updated successfully.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void updateReservationStatus(int bookId, int reservationId, ReservationStatus status) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE Reservation SET Status = ? WHERE BookId = ? AND ReservationId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, status.name());
            statement.setInt(2, bookId);
            statement.setInt(3, reservationId);

         
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("Failed to update book status: No rows affected.");
            } else {
                System.out.println("Book status updated successfully.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateBorrowedStatus(int bookId, BorrowedBookStatus status) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE BorrowedBook SET Status = ? WHERE BookId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, status.name());
            statement.setInt(2, bookId);

         
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("Failed to update book status: No rows affected.");

                JOptionPane.showMessageDialog(null, "Failed to return the book");
            } else {
                System.out.println("Book status updated successfully.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static boolean isValidMember(int memberId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT COUNT(*) FROM Member WHERE MemberId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, memberId);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // If count is greater than 0, the member ID is valid
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // Return false if an error occurs or no records are found
    }

    private static boolean isValidBook(int bookId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT COUNT(*) FROM Book WHERE BookId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookId);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // If count is greater than 0, the book ID is valid
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // Return false if an error occurs or no records are found
    }

    public List<Reservation> getReservationsForCurrentMember(int memberId) {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM Reservation WHERE MemberId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, memberId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
            	int reservationId = resultSet.getInt("reservationId");
                int bookId = resultSet.getInt("BookId");
                String title = resultSet.getString("Title");

                String author = resultSet.getString("Author");
                LocalDate startDate = resultSet.getDate("StartDate").toLocalDate();
                LocalDate endDate = resultSet.getDate("EndDate").toLocalDate();
                ReservationStatus status = ReservationStatus.valueOf(resultSet.getString("Status"));

                Reservation reservation = new Reservation(reservationId, bookId, title, author, startDate, endDate, status);
                reservations.add(reservation);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reservations;
    }

}

