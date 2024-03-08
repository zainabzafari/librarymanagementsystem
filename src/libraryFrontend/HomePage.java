package libraryFrontend;

import javax.swing.JPanel;

import libraryBackend.DatabaseConnector;

import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Rectangle;

public class HomePage extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel label_availabel;
	private JLabel label_issue;
	private JLabel label_reserved;
	private JLabel label_overdue;
	private JLabel label_member;
	/**
	 * Create the panel.
	 */
	public HomePage() {
		setBounds(new Rectangle(0, 0, 1100, 900));
		initComponent();
		
		updateLabels();
	}
	
	
    private void updateLabels() {
    	
        try (Connection connection = DatabaseConnector.getConnection();) {
            // Query for counting books with different statuses
            String sqlAvailable = "SELECT COUNT(*) FROM Book WHERE Status = 'AVAILABLE'";
            String sqlIssued = "SELECT COUNT(*) FROM Book WHERE Status = 'CHECKED_OUT'";
            String sqlReserved = "SELECT COUNT(*) FROM Reservation WHERE Status = 'DUE'";
            String sqlOverdue = "SELECT COUNT(*) FROM Book WHERE Status = 'OVERDUE'";
            String sqlmember = "SELECT COUNT(*) FROM member";

            // Execute queries
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sqlAvailable);
                if (resultSet.next()) {
                	label_availabel.setText(resultSet.getString(1));
                }

                resultSet = statement.executeQuery(sqlIssued);
                if (resultSet.next()) {
                	label_issue.setText(resultSet.getString(1));
                }

                resultSet = statement.executeQuery(sqlReserved);
                if (resultSet.next()) {
                	label_reserved.setText(resultSet.getString(1));
                }

                resultSet = statement.executeQuery(sqlOverdue);
                if (resultSet.next()) {
                	label_overdue.setText(resultSet.getString(1));
                }
                
                resultSet = statement.executeQuery(sqlmember);
                if (resultSet.next()) {
                	label_member.setText(resultSet.getString(1));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

	
	
	public void initComponent() {
		setBackground(new Color(255, 255, 255));
		setPreferredSize(new Dimension(1201, 800));
		setLayout(null);
		
		JPanel panelavailablebooks = new JPanel();
		panelavailablebooks.setBounds(new Rectangle(0, 20, 0, 0));
		panelavailablebooks.setPreferredSize(new Dimension(220, 170));
		panelavailablebooks.setBounds(20, 30, 160, 115);
		add(panelavailablebooks);
		panelavailablebooks.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(250, 30));
		panel.setBackground(new Color(51, 153, 204));
		panel.setBounds(0, 0, 180, 35);
		panelavailablebooks.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("No. Available books");
		lblNewLabel_3.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
		lblNewLabel_3.setBounds(0, 0, 159, 35);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(HomePage.class.getResource("/adminIcons/icons8_Book_Shelf_50px.png")));
		lblNewLabel.setBounds(30, 59, 49, 43);
		panelavailablebooks.add(lblNewLabel);
		
		label_availabel = new JLabel("");
		label_availabel.setFont(new Font("Yu Gothic Light", Font.BOLD, 20));
		label_availabel.setBounds(107, 67, 49, 35);
		panelavailablebooks.add(label_availabel);
		
		JPanel panel_issuedbooks = new JPanel();
		panel_issuedbooks.setBounds(new Rectangle(0, 30, 0, 0));
		panel_issuedbooks.setLayout(null);
		panel_issuedbooks.setPreferredSize(new Dimension(220, 170));
		panel_issuedbooks.setBounds(205, 30, 160, 115);
		add(panel_issuedbooks);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(250, 30));
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(0, 102, 0));
		panel_1.setBounds(0, 0, 180, 35);
		panel_issuedbooks.add(panel_1);
		
		JLabel lblNewLabel_3_1 = new JLabel("No. Issued books");
		lblNewLabel_3_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
		lblNewLabel_3_1.setBounds(0, 0, 159, 35);
		panel_1.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(HomePage.class.getResource("/adminIcons/icons8_Sell_50px.png")));
		lblNewLabel_1.setBounds(32, 59, 49, 43);
		panel_issuedbooks.add(lblNewLabel_1);
		
		label_issue = new JLabel("");
		label_issue.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
		label_issue.setBounds(101, 59, 49, 35);
		panel_issuedbooks.add(label_issue);
		
		JPanel panel_reservedbooks = new JPanel();
		panel_reservedbooks.setBounds(new Rectangle(0, 30, 0, 0));
		panel_reservedbooks.setLayout(null);
		panel_reservedbooks.setPreferredSize(new Dimension(300, 200));
		panel_reservedbooks.setBounds(389, 30, 160, 115);
		add(panel_reservedbooks);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setPreferredSize(new Dimension(250, 30));
		panel_1_1.setBackground(new Color(204, 153, 51));
		panel_1_1.setBounds(0, 0, 180, 35);
		panel_reservedbooks.add(panel_1_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("No. Reserved books");
		lblNewLabel_3_2.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
		lblNewLabel_3_2.setBounds(0, 0, 159, 35);
		panel_1_1.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon(HomePage.class.getResource("/adminIcons/icons8-reserve-50.png")));
		lblNewLabel_1_1.setBounds(33, 59, 49, 43);
		panel_reservedbooks.add(lblNewLabel_1_1);
		
		label_reserved = new JLabel("");
		label_reserved.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
		label_reserved.setBounds(106, 67, 49, 35);
		panel_reservedbooks.add(label_reserved);
		
		JPanel panel_reservedbooks_1 = new JPanel();
		panel_reservedbooks_1.setBounds(new Rectangle(0, 30, 0, 0));
		panel_reservedbooks_1.setPreferredSize(new Dimension(230, 170));
		panel_reservedbooks_1.setBounds(576, 30, 160, 115);
		add(panel_reservedbooks_1);
		panel_reservedbooks_1.setLayout(null);
		
		JPanel panel_1_1_1 = new JPanel();
		panel_1_1_1.setBounds(0, 0, 180, 35);
		panel_1_1_1.setLayout(null);
		panel_1_1_1.setPreferredSize(new Dimension(250, 30));
		panel_1_1_1.setBackground(new Color(153, 51, 0));
		panel_reservedbooks_1.add(panel_1_1_1);
		
		JLabel lblNewLabel_3_3 = new JLabel("No. Overdue books");
		lblNewLabel_3_3.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
		lblNewLabel_3_3.setBounds(0, 0, 159, 35);
		panel_1_1_1.add(lblNewLabel_3_3);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.setBounds(44, 59, 49, 43);
		lblNewLabel_1_1_1.setIcon(new ImageIcon(HomePage.class.getResource("/adminIcons/icons8_List_of_Thumbnails_50px.png")));
		panel_reservedbooks_1.add(lblNewLabel_1_1_1);
		
		label_overdue = new JLabel("");
		label_overdue.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
		label_overdue.setBounds(120, 67, 49, 35);
		panel_reservedbooks_1.add(label_overdue);
		
		JPanel panel_reservedbooks_1_1 = new JPanel();
		panel_reservedbooks_1_1.setBounds(new Rectangle(0, 30, 0, 0));
		panel_reservedbooks_1_1.setLayout(null);
		panel_reservedbooks_1_1.setPreferredSize(new Dimension(300, 200));
		panel_reservedbooks_1_1.setBounds(765, 30, 160, 115);
		add(panel_reservedbooks_1_1);
		
		JPanel panel_1_1_1_1 = new JPanel();
		panel_1_1_1_1.setLayout(null);
		panel_1_1_1_1.setPreferredSize(new Dimension(250, 30));
		panel_1_1_1_1.setBackground(new Color(102, 51, 153));
		panel_1_1_1_1.setBounds(0, 0, 220, 35);
		panel_reservedbooks_1_1.add(panel_1_1_1_1);
		
		JLabel lblNewLabel_3_4 = new JLabel("No. Members");
		lblNewLabel_3_4.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
		lblNewLabel_3_4.setBounds(0, 0, 159, 35);
		panel_1_1_1_1.add(lblNewLabel_3_4);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1_1.setIcon(new ImageIcon(HomePage.class.getResource("/adminIcons/icons8_People_50px.png")));
		lblNewLabel_1_1_1_1.setBounds(44, 59, 49, 43);
		panel_reservedbooks_1_1.add(lblNewLabel_1_1_1_1);
		
		label_member = new JLabel("");
		label_member.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
		label_member.setBounds(120, 67, 49, 35);
		panel_reservedbooks_1_1.add(label_member);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(HomePage.class.getResource("/icons/library-3.png")));
		lblNewLabel_2.setPreferredSize(new Dimension(1100, 500));
		lblNewLabel_2.setBounds(20, 185, 1010, 574);
		add(lblNewLabel_2);

	}
}
