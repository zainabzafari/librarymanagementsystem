package libraryFrontend;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import libraryBackend.Reservation;
import libraryBackend.ReservationManager;
import libraryBackend.ReservationStatus;
import libraryBackend.SessionManager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import rojerusan.RSMaterialButtonRectangle;

public class MemberReservedBooks extends JPanel {

	private static final long serialVersionUID = 1L;
	
	 private static final String DB_URL = "jdbc:mysql://localhost:3306/LibraryManagementSystem";
	    private static final String DB_USER = "root";
	    private static final String DB_PASSWORD = "Elias$#22";  
	    
	DefaultTableModel reservedTableModel;
	JTable reservedBookTable;
	JScrollPane reservedScrollPane;
	RSMaterialButtonRectangle cancelReservation;

	RSMaterialButtonRectangle refereshbutton;

	/**
	 * Create the panel.
	 */
	public MemberReservedBooks() {
		setBackground(new Color(255, 255, 255));
		initComponents();
		
		loadData();
		
		
	    cancelReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int selectedRow = reservedBookTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a reservation to cancel.");
                    return;
                }

                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this reservation?",
                        "Cancel Reservation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    int reservationId = (int)reservedTableModel.getValueAt(selectedRow, 0); // Assuming reservationId is in column 0
                    //ReservationManager reservationManager = new ReservationManager();
                    cancelReservation(reservationId);
                    removeReservationFromTable(selectedRow);
                }
            }
        });
		
	    refereshbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	loadData();
            }
        });
		
	    
	}
	
	private void removeReservationFromTable(int rowIndex) {
	    // Remove the row from the table model
		reservedTableModel.removeRow(rowIndex);
	}
	
	
		
		
		
	
	public boolean cancelReservation(int reservationId) {
	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	        // Get the bookId associated with the canceled reservation
	        String getBookIdSql = "SELECT BookId FROM Reservation WHERE ReservationId = ?";
	        PreparedStatement getBookIdStatement = connection.prepareStatement(getBookIdSql);
	        getBookIdStatement.setInt(1, reservationId);
	        ResultSet resultSet = getBookIdStatement.executeQuery();
	        if (resultSet.next()) {
	            
	            // Update the reservation status to CANCELED
	            String updateReservationSql = "UPDATE Reservation SET Status = ? WHERE ReservationId = ?";
	            PreparedStatement updateReservationStatement = connection.prepareStatement(updateReservationSql);
	            updateReservationStatement.setString(1, ReservationStatus.CANCELED.name());
	            updateReservationStatement.setInt(2, reservationId);
	            int reservationUpdateCount = updateReservationStatement.executeUpdate();
	            
	            
	            // Check if both updates were successful
	            if(reservationUpdateCount > 0) {

	                return true;
	            }
	            else {
	            return false;
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}

	
	public void updateTableView(List<Reservation> reservations) {
	    reservedTableModel.setRowCount(0); // Clear existing rows
	    
	    for (Reservation reservation : reservations) {
	        reservedTableModel.addRow(new Object[]{
	        	reservation.getReservationId(),	
	            reservation.getBookId(),
	            reservation.getTitle(),
	            reservation.getAuthor(),
	            reservation.getReservationStartDate(),
	            reservation.getReservationEndDate(),
	            reservation.getReservationStatus()
	        });
	    }
	}

	
	protected void loadData() {
        String username = SessionManager.getCurrentUsername();
        int memberId = SessionManager.getCurrentMemberId(username);
        ReservationManager reservationManager = new ReservationManager();
        List<Reservation> reservations = reservationManager.getReservationsForCurrentMember(memberId);
        
        // Update the table view with loaded data
        updateTableView(reservations);
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
		lblNewLabel.setIcon(new ImageIcon(MemberReservedBooks.class.getResource("/adminIcons/icons8-book-48.png")));
		lblNewLabel.setBounds(419, 11, 50, 50);
		panel.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(0, 0, 0));
		separator.setBackground(new Color(0, 0, 0));
		separator.setBounds(419, 65, 317, 5);
		panel.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("Reserved Books Details");
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 18));
		lblNewLabel_1.setBounds(479, 34, 255, 30);
		panel.add(lblNewLabel_1);
		
		reservedScrollPane = new JScrollPane();
		reservedScrollPane.setBounds(0, 77, 1100, 446);
		add(reservedScrollPane);
		
		reservedTableModel = new DefaultTableModel();
		reservedTableModel.setColumnIdentifiers(new Object[]{"ReservationId","bookId", "Title", "Author", "Start Date", "End Date", "Status"});
		reservedBookTable = new JTable(reservedTableModel);
		reservedScrollPane.setViewportView(reservedBookTable);
		
		cancelReservation = new RSMaterialButtonRectangle();
		cancelReservation.setText("cancel reservation  ");
		cancelReservation.setBackground(new Color(51, 51, 255));
		cancelReservation.setBounds(677, 545, 337, 53);
		add(cancelReservation);
		
		refereshbutton = new RSMaterialButtonRectangle();
		refereshbutton.setText("REFERESH");
		refereshbutton.setBackground(new Color(51, 51, 255));
		refereshbutton.setBounds(34, 545, 337, 53);
		add(refereshbutton);
		
		

	}
}
