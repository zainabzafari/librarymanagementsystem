package libraryFrontend;

import javax.swing.JPanel;
import java.awt.Color;
import app.bolivia.swing.JCTextField;
import libraryBackend.BookStatus;
import libraryBackend.BorrowedBookStatus;
import libraryBackend.ReservationManager;
import libraryBackend.ReservationStatus;

import javax.swing.border.MatteBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import rojeru_san.componentes.RSDateChooser;
import java.awt.ComponentOrientation;
import rojerusan.RSMaterialButtonRectangle;
import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.Dimension;

public class IssueBook extends JPanel {

	private static final long serialVersionUID = 1L;
	private JCTextField bookIdField;
	private JCTextField bookTitleField;
	private JCTextField bookAuthorField;
	private JCTextField memberIdField;
	private JCTextField firstNameField;
	private JCTextField lastNameField;
	private JCTextField issueBookField;
	private JCTextField issueMemberField;
	private RSDateChooser startDateChooser;
	private RSDateChooser endDateChooser;
	private RSMaterialButtonRectangle issueButton;

	/**
	 * Create the panel.
	 */
	public IssueBook() {
		initializeComponent();
		
		issueBookField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get book details from the database based on the entered book ID
                String bookId = issueBookField.getText();
                getBookDetails(bookId);
            }

			
        });

        // Adding action listener to issueMemberField
        issueMemberField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get member details from the database based on the entered member ID
                String memberId = issueMemberField.getText();
                getMemberDetails(memberId);
            }
        });
        
        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call a method to save the information to the database
            	
            	 int bookId = Integer.parseInt(issueBookField.getText());
                 int memberId = Integer.parseInt(issueMemberField.getText());
                 
                 

                 LocalDate startDate = null;
                 java.util.Date selectedStartDate = startDateChooser.getDatoFecha(); // Use java.util.Date instead of java.sql.Date
                 if (selectedStartDate != null) {
                     startDate = selectedStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                 } else {
                     JOptionPane.showMessageDialog(null, "Start Date cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                     return; // Exit the method if start date is empty
                 }

                 LocalDate endDate = null;
                 java.util.Date selectedEndDate = endDateChooser.getDatoFecha();
                 if (selectedEndDate != null) {
                     endDate = selectedEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                 } else {
                     JOptionPane.showMessageDialog(null, "End Date cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                     return; // Exit the method if end date is empty
                 }
    	        ReservationManager reservationmanager = new ReservationManager();
                 // Check if the book is available for borrowing
    	        if (reservationmanager.isBookAvailable(bookId, startDate, endDate) && !reservationmanager.isBookReservedByOtherMember(bookId, memberId, startDate, endDate)) {
    	            // Check if the book is reserved by the current member
    	        	
    	            int reservationId = reservationmanager.isBookReservedByThisMember(bookId, memberId, startDate, endDate);
    	            if (reservationId != -1) {
    	                // If the book is reserved by the current member, update the reservation status
    	            	reservationmanager.updateReservationStatus(bookId, reservationId, ReservationStatus.COMPLETED);
    	            } else {
    	            	System.out.println("The book is not reserved");
    	            }
    	            
                	 
                	 
                	 issueBook();

  	               JOptionPane.showMessageDialog(null, "Book issued successfully!");
  	               reservationmanager.updateBookStatus(bookId, BookStatus.CHECKED_OUT);
  	               reservationmanager.updateBorrowedStatus(bookId, BorrowedBookStatus.CHECKED_OUT);
  	               reservationmanager.updateReservationStatus(bookId, reservationId, ReservationStatus.COMPLETED);
  	               clearTextFields();
                 
                 } else {
                     // If not available, display a message
                     JOptionPane.showMessageDialog(null, "Sorry! This book is not available for checkout in the specified dates.");
                 }
             
            }});

            
       

		
	
	}
	
	private int issueBook() {
        // Get the values from text fields and date choosers
		int issueId =0;
        String bookId = bookIdField.getText();
        String memberId = memberIdField.getText();
        java.util.Date startDate = startDateChooser.getDatoFecha();
        java.util.Date endDate = endDateChooser.getDatoFecha();

        // Insert into the BorrowedBook table
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementSystem", "root", "Elias$#22");
            String sql = "INSERT INTO BorrowedBook (bookId, memberId, startDate, returnDate) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, bookId);
            statement.setString(2, memberId);
            statement.setDate(3, new java.sql.Date(startDate.getTime()));
            statement.setDate(4, new java.sql.Date(endDate.getTime()));
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
            	
            	 ResultSet generatedKeys = statement.getGeneratedKeys();
 	            if (generatedKeys.next()) {
 	                issueId = generatedKeys.getInt(1); // Retrieve the auto-generated reservation ID

 	                System.out.println("A new record has been inserted into BorrowedBook table!");
 	            }
 	        }
            
        } catch (SQLException ex) {
            System.out.println("An error occurred while inserting data into BorrowedBook table: " + ex.getMessage());
        }
		return issueId;
    }
	
	 private void getBookDetails(String bookId) {
	        try {
	            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementSystem", "root", "Elias$#22");
	            String sql = "SELECT Title, Author FROM Book WHERE bookId = ?";
	            PreparedStatement statement = connection.prepareStatement(sql);
	            statement.setString(1, bookId);
	            ResultSet resultSet = statement.executeQuery();
	            if (resultSet.next()) {
	                String title = resultSet.getString("Title");
	                String author = resultSet.getString("Author");
	                // Display book details in the respective text fields
	                bookIdField.setText(bookId);
	                bookTitleField.setText(title);
	                bookAuthorField.setText(author);
	            } else {


	                JOptionPane.showMessageDialog(null, "Book not found!");
	            }
	        } catch (SQLException ex) {
	            System.out.println("An error occurred while fetching book details: " + ex.getMessage());
	        }
	    }

	    // Method to fetch member details from the database
	    private void getMemberDetails(String memberId) {
	        try {
	            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementSystem", "root", "Elias$#22");
	            String sql = "SELECT FirstName, LastName FROM Member WHERE MemberId = ?";
	            PreparedStatement statement = connection.prepareStatement(sql);
	            statement.setString(1, memberId);
	            ResultSet resultSet = statement.executeQuery();
	            if (resultSet.next()) {
	                String firstName = resultSet.getString("FirstName");
	                String lastName = resultSet.getString("LastName");
	                // Display member details in the respective text fields
	                memberIdField.setText(memberId);
	                firstNameField.setText(firstName);
	                lastNameField.setText(lastName);
	            } else {
	                JOptionPane.showMessageDialog(null, "Member not found!");
	            }
	        } catch (SQLException ex) {
	            System.out.println("An error occurred while fetching member details: " + ex.getMessage());
	        }
	    }
		
	    private void clearTextFields() {
	    	bookIdField.setText("");
	    	bookTitleField.setText("");
	    	bookAuthorField.setText("");
	    	memberIdField.setText("");

	    	firstNameField.setText("");
	    	lastNameField.setText("");
	    	issueBookField.setText("");
	    	issueMemberField.setText("");
	    	

	    	startDateChooser.setDatoFecha(null);
	    	endDateChooser.setDatoFecha(null);
	    	
	     }

	    
		private void initializeComponent() {
		setPreferredSize(new Dimension(990, 600));
		setVisible(true);
		setBounds(new Rectangle(0, 50, 990, 600));
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(325, 750));
		panel.setLayout(null);
		panel.setBackground(new Color(255, 153, 51));
		panel.setBounds(0, 0, 326, 750);
		add(panel);
		
		bookIdField = new JCTextField();
		bookIdField.setForeground(new Color(255, 255, 255));
		bookIdField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		bookIdField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		bookIdField.setBackground(new Color(255, 153, 0));
		bookIdField.setBounds(77, 252, 200, 32);
		panel.add(bookIdField);
		
		JLabel lblNewLabel = new JLabel("Book ID:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel.setBounds(77, 214, 136, 27);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Book Title:");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(77, 314, 136, 27);
		panel.add(lblNewLabel_2);
		
		bookTitleField = new JCTextField();
		bookTitleField.setForeground(new Color(255, 255, 255));
		bookTitleField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		bookTitleField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		bookTitleField.setBackground(new Color(255, 153, 0));
		bookTitleField.setBounds(77, 337, 200, 32);
		panel.add(bookTitleField);
		
		JLabel lblNewLabel_3 = new JLabel("Author:");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(77, 401, 136, 27);
		panel.add(lblNewLabel_3);
		
		bookAuthorField = new JCTextField();
		bookAuthorField.setForeground(new Color(255, 255, 255));
		bookAuthorField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		bookAuthorField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		bookAuthorField.setBackground(new Color(255, 153, 0));
		bookAuthorField.setBounds(77, 424, 200, 32);
		panel.add(bookAuthorField);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(IssueBook.class.getResource("/adminIcons/icons8-book-red 48.png")));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(55, 61, 50, 50);
		panel.add(lblNewLabel_1);
		
		JCTextField bookIdField_1_1_1 = new JCTextField();
		bookIdField_1_1_1.setVerifyInputWhenFocusTarget(false);
		bookIdField_1_1_1.setForeground(Color.WHITE);
		bookIdField_1_1_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 25));
		bookIdField_1_1_1.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		bookIdField_1_1_1.setBackground(new Color(255, 153, 0));
		bookIdField_1_1_1.setBounds(114, 131, 163, 2);
		panel.add(bookIdField_1_1_1);
		
		JLabel lblBookDetail = new JLabel("Book Detail");
		lblBookDetail.setForeground(Color.WHITE);
		lblBookDetail.setFont(new Font("Palatino Linotype", Font.PLAIN, 25));
		lblBookDetail.setBounds(115, 93, 206, 40);
		panel.add(lblBookDetail);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(325, 700));
		panel_1.setBounds(new Rectangle(3, 0, 0, 0));
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(51, 102, 255));
		panel_1.setBounds(326, 0, 332, 750);
		add(panel_1);
		
		memberIdField = new JCTextField();
		memberIdField.setForeground(new Color(255, 255, 255));
		memberIdField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		memberIdField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		memberIdField.setBackground(new Color(51, 102, 255));
		memberIdField.setBounds(98, 246, 200, 32);
		panel_1.add(memberIdField);
		
		JLabel lblNewLabel_4 = new JLabel("Member ID:");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(98, 208, 136, 27);
		panel_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_2_1 = new JLabel("First Name:");
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_2_1.setBounds(98, 303, 136, 27);
		panel_1.add(lblNewLabel_2_1);
		
		firstNameField = new JCTextField();
		firstNameField.setForeground(new Color(255, 255, 255));
		firstNameField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		firstNameField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		firstNameField.setBackground(new Color(51, 102, 255));
		firstNameField.setBounds(98, 331, 200, 32);
		panel_1.add(firstNameField);
		
		JLabel lblNewLabel_3_1 = new JLabel("Last Name:");
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_3_1.setBounds(98, 389, 136, 27);
		panel_1.add(lblNewLabel_3_1);
		
		lastNameField = new JCTextField();
		lastNameField.setForeground(new Color(255, 255, 255));
		lastNameField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lastNameField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		lastNameField.setBackground(new Color(51, 102, 255));
		lastNameField.setBounds(98, 420, 200, 32);
		panel_1.add(lastNameField);
		
		JLabel lblNewLabel_1_3 = new JLabel("");
		lblNewLabel_1_3.setIcon(new ImageIcon(IssueBook.class.getResource("/adminIcons/icons8-student-50.png")));
		lblNewLabel_1_3.setForeground(Color.WHITE);
		lblNewLabel_1_3.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1_3.setBounds(49, 57, 50, 50);
		panel_1.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1_1_1_1.setBounds(20, 266, 47, 32);
		panel_1.add(lblNewLabel_1_1_1_1);
		
		JCTextField bookIdField_1_1 = new JCTextField();
		bookIdField_1_1.setForeground(new Color(255, 255, 255));
		bookIdField_1_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 25));
		bookIdField_1_1.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		bookIdField_1_1.setBackground(new Color(51, 102, 255));
		bookIdField_1_1.setBounds(116, 132, 182, 2);
		panel_1.add(bookIdField_1_1);
		
		JLabel lblMemberDetails = new JLabel("Member Details");
		lblMemberDetails.setForeground(new Color(255, 255, 255));
		lblMemberDetails.setFont(new Font("Palatino Linotype", Font.PLAIN, 25));
		lblMemberDetails.setBounds(116, 81, 206, 40);
		panel_1.add(lblMemberDetails);
		
		JPanel panel_2 = new JPanel();
		panel_2.setRequestFocusEnabled(false);
		panel_2.setPreferredSize(new Dimension(325, 700));
		panel_2.setForeground(new Color(0, 0, 255));
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(660, 0, 325, 750);
		add(panel_2);
		panel_2.setLayout(null);
		
		JCTextField bookIdField_2 = new JCTextField();
		bookIdField_2.setSelectedTextColor(new Color(0, 0, 160));
		bookIdField_2.setForeground(new Color(0, 0, 160));
		bookIdField_2.setSize(new Dimension(200, 4));
		bookIdField_2.setVerifyInputWhenFocusTarget(false);
		bookIdField_2.setBounds(91, 129, 200, 3);
		bookIdField_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		bookIdField_2.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 255)));
		bookIdField_2.setBackground(new Color(255, 255, 255));
		panel_2.add(bookIdField_2);
		
		JLabel lblIssueBook = new JLabel("Issue Book");
		lblIssueBook.setBounds(91, 73, 136, 40);
		lblIssueBook.setForeground(new Color(51, 0, 255));
		lblIssueBook.setFont(new Font("Palatino Linotype", Font.PLAIN, 25));
		panel_2.add(lblIssueBook);
		
		JLabel lblNewLabel_2_2 = new JLabel("Book Id:");
		lblNewLabel_2_2.setBounds(91, 164, 136, 27);
		lblNewLabel_2_2.setForeground(new Color(51, 0, 255));
		lblNewLabel_2_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_2_2);
		
		issueBookField = new JCTextField();
		issueBookField.setBounds(91, 187, 200, 32);
		issueBookField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		issueBookField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 255)));
		issueBookField.setBackground(new Color(255, 255, 255));
		panel_2.add(issueBookField);
		
		JLabel lblNewLabel_3_2 = new JLabel("Member Id:");
		lblNewLabel_3_2.setBounds(91, 251, 136, 27);
		lblNewLabel_3_2.setForeground(new Color(51, 0, 255));
		lblNewLabel_3_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_3_2);
		
		issueMemberField = new JCTextField();
		issueMemberField.setBounds(91, 274, 200, 32);
		issueMemberField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		issueMemberField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 255)));
		issueMemberField.setBackground(new Color(255, 255, 255));
		panel_2.add(issueMemberField);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("");
		lblNewLabel_1_2_2.setBounds(32, 180, 47, 39);
		lblNewLabel_1_2_2.setIcon(new ImageIcon(IssueBook.class.getResource("/adminIcons/icons8-book-26.png")));
		lblNewLabel_1_2_2.setForeground(Color.WHITE);
		lblNewLabel_1_2_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_1_2_2);
		
		startDateChooser = new RSDateChooser();
		startDateChooser.setBounds(32, 339, 259, 40);
		startDateChooser.setPlaceholder("");
		startDateChooser.setForeground(Color.BLACK);
		startDateChooser.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		startDateChooser.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		startDateChooser.setColorTextDiaActual(new Color(0, 102, 255));
		startDateChooser.setColorSelForeground(new Color(0, 102, 255));
		startDateChooser.setColorBackground(new Color(0, 0, 255));
		startDateChooser.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		startDateChooser.setBackground(new Color(255, 255, 255));
		panel_2.add(startDateChooser);
		
		endDateChooser = new RSDateChooser();
		endDateChooser.setBounds(32, 409, 259, 40);
		endDateChooser.setPlaceholder("");
		endDateChooser.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		endDateChooser.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		endDateChooser.setColorTextDiaActual(new Color(0, 102, 255));
		endDateChooser.setColorSelForeground(new Color(0, 102, 255));
		endDateChooser.setColorBackground(new Color(0, 0, 255));
		endDateChooser.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		endDateChooser.setBackground(new Color(255, 255, 255));
		panel_2.add(endDateChooser);
		
		issueButton = new RSMaterialButtonRectangle();
		issueButton.setBounds(40, 477, 240, 53);
		issueButton.setText("Issue Book");
		issueButton.setBackground(new Color(0, 0, 255));
		panel_2.add(issueButton);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setBounds(32, 50, 50, 50);
		lblNewLabel_5.setIcon(new ImageIcon(IssueBook.class.getResource("/adminIcons/icons8-books-48.png")));
		panel_2.add(lblNewLabel_5);
		
		JLabel lblNewLabel_1_2_2_1 = new JLabel("");
		lblNewLabel_1_2_2_1.setBounds(32, 267, 47, 39);
		lblNewLabel_1_2_2_1.setIcon(new ImageIcon(IssueBook.class.getResource("/adminIcons/icons8-member-48.png")));
		lblNewLabel_1_2_2_1.setForeground(Color.WHITE);
		lblNewLabel_1_2_2_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_1_2_2_1);

	}
	}

