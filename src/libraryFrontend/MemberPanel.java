package libraryFrontend;

import javax.swing.JPanel;
import java.awt.Color;
import app.bolivia.swing.JCTextField;
import libraryBackend.Book;
import libraryBackend.BookStatus;
import libraryBackend.CustomTableCellRenderer;
import libraryBackend.CustomTableHeaderRenderer;
import libraryBackend.Library;
import libraryBackend.Reservation;
import libraryBackend.ReservationManager;
import libraryBackend.ReservationStatus;
import libraryBackend.SessionManager;

import javax.swing.border.LineBorder;
import rojerusan.RSMetroTextPlaceHolder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import rojeru_san.componentes.RSDateChooser;
import java.awt.ComponentOrientation;
import rojeru_san.componentes.RSDateChooserBeanInfo;
import com.toedter.calendar.JDateChooser;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import rojerusan.RSMaterialButtonCircle;
import rojerusan.RSMaterialButtonRectangle;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import rojeru_san.complementos.RSTableMetro;
import java.util.Locale;

public class MemberPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	    
	private JPanel panel;
	private JCTextField bookIdField;
	private JCTextField bookTitleField;
	private JCTextField bookAuthorField;
	private JDateChooser startDateChooser;
	private JDateChooser endDateChooser;
	private RSMaterialButtonRectangle reserveButton;
	private RSMaterialButtonRectangle extendButton;
	private RSMaterialButtonRectangle lostButton;
	private JCTextField searchField;
	private JScrollPane searchScrollPane;
	
	private DefaultTableModel tableModel;
	private JTable searchBookTable;
	private MemberReservedBooks memberRereservedbook;
	/**
	 * @wbp.nonvisual location=1077,19
	 */
	private  JLabel welcomelabel; 
	/**
	 * Create the panel.
	 */
	public MemberPanel() {
		

		
		
		initializeComponents();
		
		setWelcomeMessage();
		
		addActionListeners();
		
		    
		
		searchField.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            searchBooks();
	        }
	    });
		
		 reserveButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	               reserveBook();
	               
	            	   
	               }
	            
	        });
		
	}

	
	 public void setWelcomeMessage() {
		
	        String username = SessionManager.getCurrentUsername();

	        if (username != null) {
	        	welcomelabel.setText("Welcome, " + username);
	        } else {
	        	welcomelabel.setText("Welcome");
	        }
	    }
	

	protected void reserveBook() {
	    String username = SessionManager.getCurrentUsername();
	    int memberId = SessionManager.getCurrentMemberId(username);
	    int bookId = Integer.parseInt(bookIdField.getText());
	    LocalDate startDate = startDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalDate endDate = endDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    String title = bookTitleField.getText();
	    String author = bookAuthorField.getText();

    // Reserve the book
    ReservationManager reservationManager = new ReservationManager();

    boolean success = reservationManager.reserveBook(memberId, bookId, title, author, startDate, endDate);
    if (success) {
        JOptionPane.showMessageDialog(null, "You Reserved the Book Successfully!");
   
           

    }
}

	
	

	
	private void addActionListeners() {
		 searchBookTable.getSelectionModel().addListSelectionListener(e -> {
	            if (!e.getValueIsAdjusting()) {
	                int selectedRow = searchBookTable.getSelectedRow();
	                if (selectedRow != -1) {
	                    // Get book details from selected row
	                    String bookId = tableModel.getValueAt(selectedRow, 0).toString();
	                    String title = tableModel.getValueAt(selectedRow, 1).toString();
	                    String author = tableModel.getValueAt(selectedRow, 2).toString();

	                    // Display book details in text fields
	                    bookIdField.setText(bookId);
	                    bookTitleField.setText(title);
	                    bookAuthorField.setText(author);
	                }
	            }
	        });
		
	}

	private void searchBooks() {
	        // Get the search keyword from the text field
	        String keyword = searchField.getText();

	        // Call Library method to search for books with the keyword
	        List<Book> searchResult = Library.searchBooks(keyword);

	        tableModel.setRowCount(0);

	        // Add the search result to the table model
	        for (Book book : searchResult) {
	            tableModel.addRow(new Object[]{book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getIsbn()});
	            }
	       
	    }
	
	public void updateTableView(List<Reservation> reservations) {
		memberRereservedbook = new MemberReservedBooks();
		memberRereservedbook.reservedTableModel.setRowCount(0); // Clear existing rows
	    
	    for (Reservation reservation : reservations) {
	    	memberRereservedbook.reservedTableModel.addRow(new Object[]{
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

		
		private void initializeComponents() {
		
		setBackground(new Color(255, 255, 255));
		setBounds(new Rectangle(0, 0, 1000, 0));
		setPreferredSize(new Dimension(1000, 700));
		setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 325, 750);
		panel.setBackground(new Color(0, 102, 255));
		add(panel);
		panel.setLayout(null);
		
	    welcomelabel= new JLabel("");
	    welcomelabel.setBounds(new Rectangle(0, 0, 200, 0));
	    welcomelabel.setPreferredSize(new Dimension(200, 40));
		setVisible(true);
		
		bookIdField = new JCTextField();
		bookIdField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		bookIdField.setBackground(new Color(0, 102, 255));
		bookIdField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		bookIdField.setBounds(91, 96, 200, 32);
		panel.add(bookIdField);
		
		JLabel lblNewLabel = new JLabel("Book ID:");
		lblNewLabel.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(91, 73, 136, 27);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Book Title:");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(91, 164, 136, 27);
		panel.add(lblNewLabel_2);
		
		bookTitleField = new JCTextField();
		bookTitleField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		bookTitleField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		bookTitleField.setBackground(new Color(0, 102, 255));
		bookTitleField.setBounds(91, 187, 200, 32);
		panel.add(bookTitleField);
		
		JLabel lblNewLabel_3 = new JLabel("Author:");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(91, 251, 136, 27);
		panel.add(lblNewLabel_3);
		
		bookAuthorField = new JCTextField();
		bookAuthorField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		bookAuthorField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		bookAuthorField.setBackground(new Color(0, 102, 255));
		bookAuthorField.setBounds(101, 266, 200, 32);
		panel.add(bookAuthorField);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(MemberPanel.class.getResource("/adminIcons/icons8-buch-30.png")));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(20, 88, 47, 32);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon(MemberPanel.class.getResource("/adminIcons/icons8-überschrift-48.png")));
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(20, 179, 47, 32);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.setIcon(new ImageIcon(MemberPanel.class.getResource("/adminIcons/icons8-schriftstellerin-weiblich-48.png")));
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1_1_1.setBounds(20, 266, 47, 32);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("");
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1_2.setBounds(20, 180, 47, 39);
		panel.add(lblNewLabel_1_2);
		
		
		
		reserveButton = new RSMaterialButtonRectangle();
		reserveButton.setText("Reserve");
		reserveButton.setBackground(new Color(102, 204, 255));
		reserveButton.setBounds(21, 477, 270, 53);
		panel.add(reserveButton);
		
		extendButton = new RSMaterialButtonRectangle();
		extendButton.setText("extend");
		extendButton.setBackground(new Color(102, 204, 255));
		extendButton.setBounds(21, 541, 136, 53);
		panel.add(extendButton);
		
		lostButton = new RSMaterialButtonRectangle();
		lostButton.setText("lost");
		lostButton.setBackground(new Color(102, 204, 255));
		lostButton.setBounds(167, 541, 136, 53);
		panel.add(lostButton);
		
		endDateChooser = new JDateChooser();
		endDateChooser.getCalendarButton().setIcon(new ImageIcon(MemberPanel.class.getResource("/adminIcons/icons8-desk-calender-30.png")));
		endDateChooser.getCalendarButton().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		endDateChooser.setLocale(Locale.ENGLISH);
		endDateChooser.setForeground(Color.BLUE);
		endDateChooser.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		endDateChooser.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 51, 255)));
		endDateChooser.setBackground(new Color(0, 102, 255));
		endDateChooser.setBounds(31, 431, 259, 35);
		panel.add(endDateChooser);
		
		startDateChooser = new JDateChooser();
		startDateChooser.getCalendarButton().setIcon(new ImageIcon(MemberPanel.class.getResource("/adminIcons/icons8-desk-calender-30.png")));
		startDateChooser.getCalendarButton().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		startDateChooser.getCalendarButton().setBounds(new Rectangle(0, 0, 40, 40));
		startDateChooser.setLocale(Locale.ENGLISH);
		startDateChooser.setForeground(Color.BLUE);
		startDateChooser.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		startDateChooser.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 51, 255)));
		startDateChooser.setBackground(new Color(0, 102, 255));
		startDateChooser.setBounds(31, 357, 259, 35);
		panel.add(startDateChooser);
		
		JLabel lblNewLabel_3_1 = new JLabel("Start Date:");
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_3_1.setBounds(31, 329, 136, 27);
		panel.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("End Date:");
		lblNewLabel_3_1_1.setForeground(Color.WHITE);
		lblNewLabel_3_1_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_3_1_1.setBounds(31, 403, 136, 27);
		panel.add(lblNewLabel_3_1_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(325, 0, 670, 750);
		add(panel_1);
		panel_1.setLayout(null);
		
		searchField = new JCTextField();
		searchField.setBounds(20, 20, 499, 32);
		searchField.setPlaceholder("Search your book by title author or isbn");
		searchField.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		searchField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 51, 255)));
		searchField.setBackground(new Color(255, 255, 255));
		panel_1.add(searchField);
		
		JLabel lblNewLabel_1_3 = new JLabel("");
		lblNewLabel_1_3.setBounds(529, 20, 54, 32);
		lblNewLabel_1_3.setIcon(new ImageIcon(MemberPanel.class.getResource("/adminIcons/icons8-search-30.png")));
		lblNewLabel_1_3.setForeground(Color.WHITE);
		lblNewLabel_1_3.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		panel_1.add(lblNewLabel_1_3);
		
		
		
		
		 tableModel = new DefaultTableModel();
         tableModel.setColumnIdentifiers(new Object[]{"bookId", "Title", "Author", "Genre", "ISBN"});
       
        searchScrollPane = new JScrollPane();
 		searchScrollPane.setBounds(20, 70, 620, 629);
 		panel_1.add(searchScrollPane);
 		
         searchBookTable = new JTable(tableModel);
         searchBookTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
         searchBookTable.getTableHeader().setDefaultRenderer(new CustomTableHeaderRenderer(searchBookTable));
         
 		 searchScrollPane.setViewportView(searchBookTable);
		

		}
	}

