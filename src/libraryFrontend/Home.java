package libraryFrontend;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Home extends JFrame {


	    private JPanel sideMenu;
	    private JPanel contentPanel;
	    private CardLayout cardLayout;

	    private List<String> menuItems = Arrays.asList("Home Page","Manage Book", "Manage Member", "Issue Book", "Return Book", "View Books","View Available Books", "View Issued Books", "View Reserved Books");

	    public Home() {
	        setTitle("Library Management System");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(1200, 700);
	        setLocationRelativeTo(null);

	        // Create side menu
	        sideMenu = new JPanel();
	        sideMenu.setBackground(new Color(250, 250, 250));
	        sideMenu.setSize(230, 700);
	        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));

            sideMenu.add(Box.createRigidArea(new Dimension(0, 40)));

	        // Add menu items to side menu
	        for (String item : menuItems) {
	            JButton button = new JButton(item);
	            //button.setBounds(new Rectangle(0, 0, 170, 35));
	            button.setBackground(new Color(250,250,250));
	            button.setFont(new Font("Simplified Arabic Fixed", Font.PLAIN, 16));
	            button.setPreferredSize(new Dimension(230, 35));
	            button.setMaximumSize(new Dimension(230, 40));
	            button.setSize(new Dimension(230,35));
	            button.setMinimumSize(new Dimension(230,35));
	            button.setAlignmentX(Component.LEFT_ALIGNMENT);
	            button.setHorizontalAlignment(SwingConstants.LEFT);
	            button.setFocusPainted(false);
	            


	            button.addMouseListener(new MenuMouseListener());
	            sideMenu.add(button);
	        }

	        // Create content panel with CardLayout
	        contentPanel = new JPanel();
	        cardLayout = new CardLayout();
	        contentPanel.setLayout(cardLayout);

	        // Add panels to content panel
	        HomePage homepage = new HomePage();
	        contentPanel.add(homepage, "Home Page");
	        
	        BookManagementPanel manageBookPanel = new BookManagementPanel();
	        contentPanel.add(manageBookPanel, "Manage Book");

	        MemberManagementPanel manageMemberPanel = new MemberManagementPanel();
	        contentPanel.add(manageMemberPanel, "Manage Member");

	        IssueBook issueBookPanel = new IssueBook();
	        contentPanel.add(issueBookPanel, "Issue Book");
	        

	        ReturnBook returnBook = new ReturnBook();
	        contentPanel.add(returnBook, "Return Book");
	        
	        AllBooks allbook = new AllBooks();
	        contentPanel.add(allbook, "View Books");
	        

	        AvailableBooks availablebook = new AvailableBooks();
	        contentPanel.add(availablebook, "View Available Books");
	        
	        BorrowedBooks borrowedbook = new BorrowedBooks();
	        contentPanel.add(borrowedbook, "View Issued Books");
	        
	        ReservedBooks reservedbook = new ReservedBooks();
	        contentPanel.add(reservedbook, "View Reserved Books");

	        // Add components to main frame
	        getContentPane().setLayout(new BorderLayout());
	        getContentPane().add(sideMenu, BorderLayout.WEST);
	        getContentPane().add(contentPanel, BorderLayout.CENTER);
	        setVisible(true);
	    }

	    private class MenuMouseListener extends MouseAdapter {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            JButton button = (JButton) e.getSource();
	            String menuItem = button.getText();
	            cardLayout.show(contentPanel, menuItem);
	        }
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> new Home());
	    }
	}


