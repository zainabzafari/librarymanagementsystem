package libraryFrontend;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import libraryBackend.BorrowedBook;
import libraryBackend.BorrowedBookStatus;
import libraryBackend.CustomTableCellRenderer;
import libraryBackend.CustomTableHeaderRenderer;
import libraryBackend.DatabaseConnector;
import libraryBackend.Reservation;
import libraryBackend.ReservationManager;
import libraryBackend.ReservationStatus;
import libraryBackend.SessionManager;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import rojerusan.RSMaterialButtonRectangle;
import javax.swing.ImageIcon;

public class MemberBorrowedBook extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane borrowedScrollPane;
	DefaultTableModel borrowedTableModel;
	JTable borrowedBookTable;
	RSMaterialButtonRectangle extendButton;
	RSMaterialButtonRectangle lostButton;
	
	
	

	 private static final String DB_URL = "jdbc:mysql://localhost:3306/LibraryManagementSystem";
	    private static final String DB_USER = "root";
	    private static final String DB_PASSWORD = "Elias$#22"; 

	/**
	 * Create the panel.
	 */
	public MemberBorrowedBook() {
		setBackground(new Color(255, 255, 255));
		
		initComponents();
		
		String username = SessionManager.getCurrentUsername(); // Assuming you have a method to get the current username
	    int memberId = SessionManager.getCurrentMemberId(username);
	    List<BorrowedBook> borrowedBooks = getBorrowedBookForCurrentMember(memberId);
	    updateTableView(borrowedBooks);
	    
	}
	
	
	public void updateTableView(List<BorrowedBook> borrowedBooks) {
	    borrowedTableModel.setRowCount(0); // Clear existing rows
	    
	    for (BorrowedBook borrowedbook : borrowedBooks) {
	    	borrowedTableModel.addRow(new Object[]{
	    			borrowedbook.getIssueId(),	
	    			borrowedbook.getBookId(),
	    			borrowedbook.getStartDate(),
	    			borrowedbook.getReturnDate(),
	    			borrowedbook.getStatus()
	        });
	    }
	}

	
	 public List<BorrowedBook> getBorrowedBookForCurrentMember(int memberId) {
	        List<BorrowedBook> borrowedbooks = new ArrayList<>();
	        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	            String sql = "SELECT * FROM BorrowedBook WHERE MemberId = ?";
	            PreparedStatement statement = connection.prepareStatement(sql);
	            statement.setInt(1, memberId);
	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	            	int transactionId = resultSet.getInt("TransactionId");
	                int bookId = resultSet.getInt("BookId");
	                LocalDate startDate = resultSet.getDate("StartDate").toLocalDate();
	                LocalDate returnDate = resultSet.getDate("returnDate").toLocalDate();
	                BorrowedBookStatus status = BorrowedBookStatus.valueOf(resultSet.getString("Status"));

	                BorrowedBook borrowed = new BorrowedBook(transactionId, bookId, startDate, returnDate, status);
	                borrowedbooks.add(borrowed);
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return borrowedbooks;
	    }
	
	private void initComponents() {
		setPreferredSize(new Dimension(1100, 750));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 1100, 77);
		add(panel);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MemberBorrowedBook.class.getResource("/adminIcons/icons8-book-48.png")));
		lblNewLabel.setBounds(439, 11, 54, 54);
		panel.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(0, 0, 0));
		separator.setBounds(439, 63, 270, 2);
		panel.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("Borrowed Books Details");
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 18));
		lblNewLabel_1.setBounds(494, 24, 248, 46);
		panel.add(lblNewLabel_1);
		
	
		borrowedScrollPane = new JScrollPane();
		borrowedScrollPane.setBounds(0, 75, 1090, 480);
		add(borrowedScrollPane);
		
		borrowedTableModel = new DefaultTableModel();
		borrowedTableModel.setColumnIdentifiers(new Object[]{"Transaction Id","Book Id", "Start Date", "End Date", "Status"});
		borrowedBookTable = new JTable(borrowedTableModel);
		borrowedScrollPane.setViewportView(borrowedBookTable);
		borrowedBookTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		borrowedBookTable.getTableHeader().setDefaultRenderer(new CustomTableHeaderRenderer(borrowedBookTable));
        
		
		
		extendButton = new RSMaterialButtonRectangle();
		extendButton.setText("Extend ");
		extendButton.setBackground(new Color(51, 153, 255));
		extendButton.setBounds(72, 581, 421, 53);
		add(extendButton);
		
		lostButton = new RSMaterialButtonRectangle();
		lostButton.setText("Mark as lost");
		lostButton.setBackground(new Color(51, 153, 255));
		lostButton.setBounds(592, 581, 421, 53);
		add(lostButton);

	}
}
