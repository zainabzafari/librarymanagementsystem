package libraryFrontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import libraryBackend.CustomTableCellRenderer;
import libraryBackend.CustomTableHeaderRenderer;
import libraryBackend.DatabaseConnector;

public class AvailableBooks extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;

	/**
	 * Create the panel.
	 */
	public AvailableBooks() {
		setBackground(new Color(255, 255, 255));
		initComponents();
		loadAvailableBooks();
		
		
	}
	
	  private void loadAvailableBooks() {

        try {

            Connection connection = DatabaseConnector.getConnection();
           
             String query = "SELECT * FROM Book WHERE Status='AVAILABLE'";
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt("bookId");
                String title = rs.getString("Title");

                String genre = rs.getString("Genre");
                String ISBN = rs.getString("ISBN");
                String author = rs.getString("Author");
                
                String status = rs.getString("Status");

                model.addRow(new Object[]{ bookId,  title, author, genre, ISBN, status});
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
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 1100, 77);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AvailableBooks.class.getResource("/adminIcons/icons8-book-48.png")));
		lblNewLabel.setBounds(383, 11, 50, 50);
		panel.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setBounds(383, 68, 317, 4);
		panel.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("List of Books in Library");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 18));
		lblNewLabel_1.setBounds(443, 31, 221, 35);
		panel.add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 70, 1100, 553);
		add(scrollPane);
		
		model = new DefaultTableModel();
		 model.addColumn("Book ID");
         model.addColumn("Title");
         model.addColumn("Genre");
         model.addColumn("ISBN");
         model.addColumn("Author");
         model.addColumn("Status");
		table = new JTable(model);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		table.getTableHeader().setDefaultRenderer(new CustomTableHeaderRenderer(table));
        
		
		scrollPane.setViewportView(table);

	}
}
