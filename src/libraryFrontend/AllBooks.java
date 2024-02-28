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

public class AllBooks extends JPanel {



	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;

	/**
	 * Create the panel.
	 */
	public AllBooks() {
		initComponents();
		loadAllBooks();
		
		
	}
	
	  private void loadAllBooks() {

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementSystem", "root", "Elias$#22");
           
             String query = "SELECT * FROM Book";
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
		panel.setBackground(new Color(0, 51, 255));
		panel.setBounds(0, 0, 1100, 145);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AllBooks.class.getResource("/adminIcons/icons8-book-96.png")));
		lblNewLabel.setBounds(419, 11, 106, 78);
		panel.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 255, 255));
		separator.setBounds(417, 100, 317, 5);
		panel.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("List of Books in Library");
		lblNewLabel_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 18));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(526, 42, 208, 46);
		panel.add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 147, 1100, 553);
		add(scrollPane);
		
		model = new DefaultTableModel();
		 model.addColumn("Book ID");
         model.addColumn("Title");
         model.addColumn("Genre");
         model.addColumn("ISBN");
         model.addColumn("Author");
         model.addColumn("Status");
		table = new JTable(model);
		

		scrollPane.setViewportView(table);

	}


}
