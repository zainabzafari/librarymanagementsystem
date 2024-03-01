package libraryFrontend;

import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import java.awt.Color;
import app.bolivia.swing.JCTextField;
import libraryBackend.BookStatus;
import libraryBackend.BorrowedBookStatus;
import libraryBackend.ReservationManager;

import javax.swing.border.MatteBorder;
import java.awt.Font;
import rojeru_san.componentes.RSDateChooser;
import java.awt.ComponentOrientation;
import rojerusan.RSMaterialButtonRectangle;

public class ReturnBook extends JPanel {

	private static final long serialVersionUID = 1L;
	private JCTextField issueIdField;
	private JCTextField memberIdField;
	private JCTextField bookIdField;
	private JCTextField issueDateField;
	private JCTextField dueDateField;
	private JCTextField issueBookField;
	private JCTextField issueMemberField;

	private RSMaterialButtonRectangle BookDetails ;
	private RSMaterialButtonRectangle ReturnBook;
	
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/LibraryManagementSystem";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Elias$#22";

	/**
	 * Create the panel.
	 */
	public ReturnBook() {
		setBackground(new Color(255, 255, 255));
		initializeComponent();
		
		BookDetails.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                loadBookDetails();
            }

			
        });

        ReturnBook.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		

           	 int bookId = Integer.parseInt(issueBookField.getText());
             int memberId = Integer.parseInt(issueMemberField.getText());
             
                returnBook();
            }
        });
		
	}
	
	
	private void loadBookDetails() {
        String bookId = issueBookField.getText().trim();
        String memberId = issueMemberField.getText().trim();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM Borrowedbook WHERE BookId = ? AND MemberId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bookId);
            statement.setString(2, memberId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Populate fields with borrowed book details
                issueIdField.setText(resultSet.getString("TransactionId"));
                memberIdField.setText(resultSet.getString("MemberId"));
                bookIdField.setText(resultSet.getString("BookId"));
                issueDateField.setText(resultSet.getString("startDate"));
                dueDateField.setText(resultSet.getString("returnDate"));
            } else {
                JOptionPane.showMessageDialog(null, "No record found for the specified book and member.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void returnBook() {
        int issueId = Integer.parseInt( issueIdField.getText().trim());
        int bookId = Integer.parseInt(bookIdField.getText().trim());

       
            	ReservationManager reservationmanager =  new ReservationManager();
            	reservationmanager.updateBookStatus(bookId, BookStatus.AVAILABLE);
            	reservationmanager.updateBorrowedStatus(bookId, BorrowedBookStatus.RETURNED);
            	
            
      
    }
	

	private void initializeComponent() {
		setPreferredSize(new Dimension(1050, 600));
		setBounds(new Rectangle(0, 0, 1050, 750));
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ReturnBook.class.getResource("/icons/library-2.png")));
		lblNewLabel.setPreferredSize(new Dimension(325, 600));
		lblNewLabel.setBounds(0, 0, 400, 600);
		add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(325, 600));
		panel.setBackground(new Color(255, 153, 0));
		panel.setBounds(400, 0, 325, 750);
		add(panel);
		
		issueIdField = new JCTextField();
		issueIdField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		issueIdField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		issueIdField.setBackground(new Color(255, 153, 0));
		issueIdField.setBounds(130, 200, 150, 32);
		panel.add(issueIdField);
		
		JLabel lblIssueId = new JLabel("Issue ID:");
		lblIssueId.setForeground(Color.WHITE);
		lblIssueId.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblIssueId.setBounds(30, 200, 90, 27);
		panel.add(lblIssueId);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon(ReturnBook.class.getResource("/adminIcons/icons8-book-red 48.png")));
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(20, 52, 50, 50);
		panel.add(lblNewLabel_1_1);
		
		JCTextField bookIdField_1_1_1 = new JCTextField();
		bookIdField_1_1_1.setForeground(Color.WHITE);
		bookIdField_1_1_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 25));
		bookIdField_1_1_1.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		bookIdField_1_1_1.setBackground(new Color(255, 153, 0));
		bookIdField_1_1_1.setBounds(117, 111, 163, 3);
		panel.add(bookIdField_1_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Member ID:");
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1_2.setBounds(30, 269, 90, 27);
		panel.add(lblNewLabel_1_2);
		
		memberIdField = new JCTextField();
		memberIdField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		memberIdField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		memberIdField.setBackground(new Color(255, 153, 0));
		memberIdField.setBounds(130, 269, 150, 32);
		panel.add(memberIdField);
		
		JLabel lblNewLabel_1_3 = new JLabel("Book ID:");
		lblNewLabel_1_3.setForeground(Color.WHITE);
		lblNewLabel_1_3.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1_3.setBounds(30, 341, 90, 27);
		panel.add(lblNewLabel_1_3);
		
		bookIdField = new JCTextField();
		bookIdField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		bookIdField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		bookIdField.setBackground(new Color(255, 153, 0));
		bookIdField.setBounds(130, 341, 150, 32);
		panel.add(bookIdField);
		
		JLabel lblNewLabel_1_4 = new JLabel("Issue Date:");
		lblNewLabel_1_4.setForeground(Color.WHITE);
		lblNewLabel_1_4.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1_4.setBounds(30, 406, 90, 27);
		panel.add(lblNewLabel_1_4);
		
		issueDateField = new JCTextField();
		issueDateField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		issueDateField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		issueDateField.setBackground(new Color(255, 153, 0));
		issueDateField.setBounds(130, 406, 150, 32);
		panel.add(issueDateField);
		
		JLabel lblNewLabel_1_5 = new JLabel("Due Date:");
		lblNewLabel_1_5.setForeground(Color.WHITE);
		lblNewLabel_1_5.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1_5.setBounds(30, 470, 90, 27);
		panel.add(lblNewLabel_1_5);
		
		dueDateField = new JCTextField();
		dueDateField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		dueDateField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		dueDateField.setBackground(new Color(255, 153, 0));
		dueDateField.setBounds(130, 470, 150, 32);
		panel.add(dueDateField);
		
		JLabel lblBookDetail = new JLabel("Book Detail");
		lblBookDetail.setForeground(new Color(255, 255, 255));
		lblBookDetail.setFont(new Font("Palatino Linotype", Font.PLAIN, 25));
		lblBookDetail.setBounds(114, 64, 150, 40);
		panel.add(lblBookDetail);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setPreferredSize(new Dimension(325, 600));
		panel_2.setForeground(Color.BLUE);
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(725, 0, 325, 750);
		add(panel_2);
		
		JCTextField bookIdField_2 = new JCTextField();
		bookIdField_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		bookIdField_2.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 255)));
		bookIdField_2.setBackground(Color.WHITE);
		bookIdField_2.setBounds(91, 110, 200, 3);
		panel_2.add(bookIdField_2);
		
		JLabel lblReturnBook = new JLabel("Return Book");
		lblReturnBook.setForeground(new Color(51, 0, 255));
		lblReturnBook.setFont(new Font("Palatino Linotype", Font.PLAIN, 25));
		lblReturnBook.setBounds(102, 60, 150, 40);
		panel_2.add(lblReturnBook);
		
		JLabel lblNewLabel_2_2 = new JLabel("Book Id:");
		lblNewLabel_2_2.setForeground(new Color(51, 0, 255));
		lblNewLabel_2_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_2_2.setBounds(91, 164, 136, 27);
		panel_2.add(lblNewLabel_2_2);
		
		issueBookField = new JCTextField();
		issueBookField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		issueBookField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 255)));
		issueBookField.setBackground(Color.WHITE);
		issueBookField.setBounds(91, 187, 200, 32);
		panel_2.add(issueBookField);
		
		JLabel lblNewLabel_3_2 = new JLabel("Member Id:");
		lblNewLabel_3_2.setForeground(new Color(51, 0, 255));
		lblNewLabel_3_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_3_2.setBounds(91, 251, 136, 27);
		panel_2.add(lblNewLabel_3_2);
		
		issueMemberField = new JCTextField();
		issueMemberField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		issueMemberField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 255)));
		issueMemberField.setBackground(Color.WHITE);
		issueMemberField.setBounds(91, 274, 200, 32);
		panel_2.add(issueMemberField);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("");
		lblNewLabel_1_2_2.setIcon(new ImageIcon(ReturnBook.class.getResource("/adminIcons/icons8-book-26.png")));
		lblNewLabel_1_2_2.setForeground(Color.WHITE);
		lblNewLabel_1_2_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1_2_2.setBounds(32, 180, 47, 39);
		panel_2.add(lblNewLabel_1_2_2);
		
		ReturnBook = new RSMaterialButtonRectangle();
		ReturnBook.setText("Return Book");
		ReturnBook.setBackground(Color.BLUE);
		ReturnBook.setBounds(45, 420, 250, 53);
		panel_2.add(ReturnBook);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(ReturnBook.class.getResource("/adminIcons/icons8-books-48.png")));
		lblNewLabel_5.setBounds(37, 37, 50, 50);
		panel_2.add(lblNewLabel_5);
		
		JLabel lblNewLabel_1_2_2_1 = new JLabel("");
		lblNewLabel_1_2_2_1.setIcon(new ImageIcon(ReturnBook.class.getResource("/adminIcons/icons8-member-48.png")));
		lblNewLabel_1_2_2_1.setForeground(Color.WHITE);
		lblNewLabel_1_2_2_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1_2_2_1.setBounds(32, 267, 47, 39);
		panel_2.add(lblNewLabel_1_2_2_1);
		
		BookDetails = new RSMaterialButtonRectangle();
		BookDetails.setText("Book Details");
		BookDetails.setBackground(Color.BLUE);
		BookDetails.setBounds(45, 356, 250, 53);
		panel_2.add(BookDetails);
		
		
		
	}
    
	}

