package libraryFrontend;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class BorrowedBooks extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;

	/**
	 * Create the panel.
	 */
	public BorrowedBooks() {
		initComponents();
		loadCheckedOutBooks();
		
		
	}
	
	  private void loadCheckedOutBooks() {

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementSystem", "root", "Elias$#22");
           
             String query = "SELECT * FROM BorrowedBook";
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int transactionId = rs.getInt("TransactionId");
                int bookId = rs.getInt("bookId");
                int memberId = rs.getInt("memberId");
                String startDate = rs.getString("startDate");
                String returnDate = rs.getString("returnDate");
                String status = rs.getString("Status");

                model.addRow(new Object[]{transactionId, bookId, memberId, startDate, returnDate, status});
            }

            table.setModel(model);

         }catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from database: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	  
	  private void initComponents() {
		setPreferredSize(new Dimension(1100, 700));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 51, 255));
		panel.setBounds(0, 0, 1100, 145);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(BorrowedBooks.class.getResource("/adminIcons/icons8-book-96.png")));
		lblNewLabel.setBounds(419, 11, 106, 78);
		panel.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 255, 255));
		separator.setBounds(417, 100, 293, 5);
		panel.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("Issued Book Details");
		lblNewLabel_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 18));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(526, 42, 184, 46);
		panel.add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 147, 1100, 553);
		add(scrollPane);
		
		model = new DefaultTableModel();
		 model.addColumn("Issued ID");
         model.addColumn("Book ID");
         model.addColumn("Member ID");
         model.addColumn("Start Date");
         model.addColumn("Return Date");
         model.addColumn("Status");
		table = new JTable(model);
		
		scrollPane.setViewportView(table);

	}
}
