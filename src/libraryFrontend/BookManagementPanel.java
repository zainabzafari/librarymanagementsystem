package libraryFrontend;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import libraryBackend.Book;
import libraryBackend.Library;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.awt.ComponentOrientation;
import javax.swing.UIManager;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JList;
import app.bolivia.swing.JCTextField;
import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;

public class BookManagementPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	static JPanel inputPanel = new JPanel();
	private JButton addButton, deleteButton, refreshButton;
	private JTextField titleField, authorField, genreField, isbnField;
    private DefaultTableModel tableModel;
    private JLayeredPane layeredPane;
    private JTextField searchField;
    private JTable table;
    private JTable bookTable;

	/**
	 * Create the panel.
	 */
	public BookManagementPanel() {
		setPreferredSize(new Dimension(1000, 1000));
		
		initializeUI();
		

		    // Add action listener to search field
		    searchField.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            searchBooks();
		        }
		    });
		    
		    
		    
			addButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                addBook();
	                clearTextFields();
	            }
	        });

	        deleteButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                deleteBook();
	            }

				
	        });

	        // Call refreshBookList() to populate the book table initially
	        
	        
	        refreshButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	 refreshBookList();
	            }
	        });
	    }
	

	 private void clearTextFields() {
         titleField.setText("");
         authorField.setText("");
         genreField.setText("");
         isbnField.setText("");
     }

    // Method to add a book
    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();
        String isbn = isbnField.getText();
        
        if (!isbn.matches("^\\d{1,5}-\\d{1,7}-\\d{1,6}-[0-9Xx]$|^\\d{3}-\\d{1,5}-\\d{1,7}-\\d{1,6}-[0-9Xx]$")) {
	        JOptionPane.showMessageDialog(this, "Invalid ISBN Format. Please enter a valid ISBN.", "Error", JOptionPane.ERROR_MESSAGE);
	       

	        return;
	    }
        if(!Library.isISBNUnique(isbn)) {
        	JOptionPane.showMessageDialog(this,"Duplicate ISBN found!, Please enter a new Unieque ISBN", "Error", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        
        // Call Library method to add the book to the database
        boolean success = Library.addBook(title, author, genre, isbn);

        // If book was added successfully, refresh the book list
        if (success) {
            refreshBookList();
            // Optionally, display a message to the user
            JOptionPane.showMessageDialog(this, "Book added successfully");
        } else {
            // Optionally, display an error message if book addition failed
            JOptionPane.showMessageDialog(this, "Failed to add book", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBook() {
        // Get the selected book ID from the table
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
     // Prompt the user to confirm deletion
        int confirmDialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirmDialogResult != JOptionPane.YES_OPTION) {
            return; // User canceled the deletion
        }


        int bookId = (int)tableModel.getValueAt(selectedRow, 0);
       
        

        // Call Library method to delete the book from the database
        boolean success = Library.deleteBook(bookId);

        // If book was deleted successfully, refresh the book list
        if (success) {
        	tableModel.removeRow(selectedRow);
            refreshBookList();
            // Optionally, display a message to the user
            JOptionPane.showMessageDialog(this, "Book deleted successfully");
        } else {
            // Optionally, display an error message if book deletion failed
            JOptionPane.showMessageDialog(this, "Failed to delete book", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to search for books
    private void searchBooks() {
        // Get the search keyword from the text field
        String keyword = searchField.getText();

        // Call Library method to search for books with the keyword
        List<Book> searchResult = Library.searchBooks(keyword);

        tableModel.setRowCount(0);

        // Add the search result to the table model
        for (Book book : searchResult) {
            tableModel.addRow(new Object[]{book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getIsbn(), book.getStatus()});
            }
       
    }

    // Method to refresh the book list displayed in the table
    public void refreshBookList() {
        // Call Library method to retrieve the latest list of books from the database
    	 List<Book> books = Library.getAllBooks();

    	    // Clear the existing data in the table model
    	    tableModel.setRowCount(0);

    	    // Add the retrieved books to the table model
    	    for (Book book : books) {
    	        tableModel.addRow(new Object[]{book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getIsbn(), book.getStatus()});
    	    }    }
		

	

	private void initializeUI() {
        setLayout(null);
        inputPanel.setForeground(new Color(255, 255, 255));
        inputPanel.setBackground(new Color(255, 153, 51));
        inputPanel.setBounds(0, 0, 326, 767);

        // Create input fields
        
        inputPanel.setAlignmentX(2.0f);
        inputPanel.setAlignmentY(2.0f);
        inputPanel.setPreferredSize(new Dimension(326, 700));
        titleField = new JTextField();
        titleField.setBounds(new Rectangle(0, 0, 180, 32));
        titleField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
        titleField.setForeground(new Color(255, 255, 255));
        titleField.setBackground(new Color(255, 153, 51));
        titleField.setMargin(new Insets(5, 5, 5, 5));
        titleField.setFont(new Font("Serif", Font.PLAIN, 14));
        titleField.setBounds(114, 276, 179, 32);
        titleField.setPreferredSize(new Dimension(180, 32));
        authorField = new JTextField();
        authorField.setBounds(new Rectangle(0, 0, 180, 32));
        authorField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
        authorField.setForeground(new Color(255, 255, 255));
        authorField.setBackground(new Color(255, 153, 51));
        authorField.setMargin(new Insets(5, 5, 5, 5));
        authorField.setPreferredSize(new Dimension(180, 32));
        authorField.setFont(new Font("Serif", Font.PLAIN, 14));
        authorField.setBounds(114, 345, 179, 32);
        genreField = new JTextField();
        genreField.setBounds(new Rectangle(0, 0, 180, 32));
        genreField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
        genreField.setForeground(new Color(255, 255, 255));
        genreField.setBackground(new Color(255, 153, 51));
        genreField.setPreferredSize(new Dimension(180, 32));
        genreField.setMargin(new Insets(5, 5, 5, 5));
        genreField.setFont(new Font("Serif", Font.PLAIN, 14));
        genreField.setBounds(114, 402, 179, 31);
        isbnField = new JTextField();
        isbnField.setBounds(new Rectangle(0, 0, 180, 32));
        isbnField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
        isbnField.setForeground(new Color(255, 255, 255));
        isbnField.setBackground(new Color(255, 153, 51));
        isbnField.setPreferredSize(new Dimension(180, 32));
        isbnField.setFont(new Font("Serif", Font.PLAIN, 14));
        isbnField.setBounds(114, 465, 179, 31);
        inputPanel.setLayout(null);
        
        
        JLabel label = new JLabel("Title:");
        label.setForeground(new Color(255, 255, 255));
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        label.setBounds(32, 274, 97, 28);
        label.setPreferredSize(new Dimension(100, 20));
        inputPanel.add(label);
        inputPanel.add(titleField);
        JLabel label_1 = new JLabel("Author:");
        label_1.setForeground(new Color(255, 255, 255));
        label_1.setFont(new Font("Serif", Font.PLAIN, 20));
        label_1.setBounds(32, 343, 81, 28);
        label_1.setPreferredSize(new Dimension(100, 20));
        inputPanel.add(label_1);
        inputPanel.add(authorField);
        JLabel label_2 = new JLabel("Genre:");
        label_2.setForeground(new Color(255, 255, 255));
        label_2.setPreferredSize(new Dimension(33, 20));
        label_2.setFont(new Font("Serif", Font.PLAIN, 20));
        label_2.setBounds(32, 400, 108, 28);
        inputPanel.add(label_2);
        inputPanel.add(genreField);
        JLabel label_3 = new JLabel("ISBN:");
        label_3.setForeground(new Color(255, 255, 255));
        label_3.setPreferredSize(new Dimension(27, 20));
        label_3.setFont(new Font("Serif", Font.PLAIN, 20));
        label_3.setBounds(32, 463, 71, 28);
        inputPanel.add(label_3);
        inputPanel.add(isbnField);

        // Create table
       

        // Add components to panel
        add(inputPanel);
        
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(180, 32));
        searchField.setBounds(new Rectangle(0, 0, 180, 32));
        searchField.setForeground(new Color(255, 255, 255));
        searchField.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
        searchField.setBackground(new Color(255, 153, 51));
        searchField.setToolTipText("Search your desired book according to it's author, title, ISBN or Genre");
        searchField.setFont(new Font("Serif", Font.PLAIN, 16));
        searchField.setBounds(114, 212, 179, 32);
        inputPanel.add(searchField);
        searchField.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("Search");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        lblNewLabel.setBounds(32, 212, 51, 28);
        inputPanel.add(lblNewLabel);
         
         addButton = new JButton("Add Book");
         addButton.setBounds(new Rectangle(0, 0, 100, 30));
         addButton.setForeground(new Color(255, 255, 255));
         addButton.setBackground(new Color(0, 153, 204));
         addButton.setFont(new Font("Serif", Font.PLAIN, 16));
         addButton.setPreferredSize(new Dimension(100, 30));
         addButton.setBounds(21, 522, 115, 30);
         inputPanel.add(addButton);
         
         deleteButton = new JButton("Delete Book");
         deleteButton.setBounds(new Rectangle(0, 0, 100, 30));
         deleteButton.setForeground(new Color(255, 255, 255));
         deleteButton.setFont(new Font("Serif", Font.PLAIN, 16));
         deleteButton.setBackground(new Color(0, 153, 204));
         deleteButton.setPreferredSize(new Dimension(100, 30));
         deleteButton.setBounds(173, 522, 115, 30);
         inputPanel.add(deleteButton);
         
         refreshButton = new JButton("Refresh");
         refreshButton.setBounds(new Rectangle(0, 0, 100, 30));
         refreshButton.setForeground(new Color(255, 255, 255));
         refreshButton.setBackground(new Color(0, 153, 204));
         refreshButton.setFont(new Font("Serif", Font.PLAIN, 16));
         refreshButton.setPreferredSize(new Dimension(100, 30));
         refreshButton.setBounds(21, 574, 115, 30);
         inputPanel.add(refreshButton);
         
         JCTextField bookIdField_1_1_1 = new JCTextField();
         bookIdField_1_1_1.setVerifyInputWhenFocusTarget(false);
         bookIdField_1_1_1.setForeground(Color.WHITE);
         bookIdField_1_1_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 25));
         bookIdField_1_1_1.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
         bookIdField_1_1_1.setBackground(new Color(255, 153, 0));
         bookIdField_1_1_1.setBounds(114, 143, 163, 2);
         inputPanel.add(bookIdField_1_1_1);
         
         JLabel lblBookDetail = new JLabel("Book Detail");
         lblBookDetail.setForeground(Color.WHITE);
         lblBookDetail.setFont(new Font("Palatino Linotype", Font.PLAIN, 25));
         lblBookDetail.setBounds(110, 70, 206, 40);
         inputPanel.add(lblBookDetail);
         
         JLabel lblNewLabel_1 = new JLabel("");
         lblNewLabel_1.setIcon(new ImageIcon(BookManagementPanel.class.getResource("/adminIcons/icons8-books-96.png")));
         lblNewLabel_1.setForeground(Color.WHITE);
         lblNewLabel_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
         lblNewLabel_1.setBounds(10, 51, 94, 94);
         inputPanel.add(lblNewLabel_1);
         
         JButton refreshButton_1 = new JButton("Refresh");
         refreshButton_1.setBounds(new Rectangle(0, 0, 100, 30));
         refreshButton_1.setPreferredSize(new Dimension(100, 30));
         refreshButton_1.setForeground(Color.WHITE);
         refreshButton_1.setFont(new Font("Serif", Font.PLAIN, 16));
         refreshButton_1.setBackground(new Color(0, 153, 204));
         refreshButton_1.setBounds(173, 574, 115, 30);
         inputPanel.add(refreshButton_1);
         
         JScrollPane tableScrollPane = new JScrollPane();
         tableScrollPane.setBounds(332, 0, 678, 680);
         tableScrollPane.setPreferredSize(new Dimension(600, 200));
         add(tableScrollPane);
         tableScrollPane.setVisible(true);
         
         tableModel = new DefaultTableModel();
         tableModel.setColumnIdentifiers(new Object[]{"bookId", "Title", "Author", "Genre", "ISBN", "Status"});
         bookTable = new JTable(tableModel);
         
         tableScrollPane.setViewportView(bookTable);
         bookTable.setVisible(true);
         
         
         
		
	}
	    }
	
