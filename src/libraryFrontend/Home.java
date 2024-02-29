package libraryFrontend;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;
import javax.swing.JSeparator;
import java.awt.Point;

public class Home extends JFrame {


	    private JPanel sideMenu;
	    private JPanel contentPanel;
	    private CardLayout cardLayout;
	    private JPanel titleBar;

	    private int posX, posY;
	    private boolean isMaximized = false;

	    private List<String> menuItems = Arrays.asList("Home Page","Manage Book", "Manage Member", "Issue Book", "Return Book", "View Books","View Available Books", "View Issued Books", "View Reserved Books");

	    public Home() {
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(1100, 600);
	        setLocationRelativeTo(null);
	        
	        
	       
	        // Create custom title bar panel
	     /*  titleBar = new JPanel(new BorderLayout()) {
	    	   @Override
	            public Dimension getPreferredSize() {
	                if (isMaximized) {
	                    return new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 60);
	                } else {
	                    return new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 50);
	                }
	            }
	        };*/
	        
	        
	        titleBar = new JPanel(new BorderLayout());
	        titleBar.setBackground(new Color(0, 0, 204));

	        titleBar.setPreferredSize(new Dimension(10, 50));
	      
	        
	    

	        // Create a JLabel for the title
	        JPanel titlePanel = new JPanel();
	        titlePanel.setPreferredSize(new Dimension(170, 60)); // Set preferred size
	        titlePanel.setOpaque(false); // Make the panel transparent

	        titlePanel.setVisible(true);
	        JLabel titleLabel = new JLabel("Library Management System");
	        titleLabel.setForeground(Color.WHITE); // Set text color
	        titleLabel.setFont(new Font("Simplified Arabic Fixed", Font.BOLD, 22)); // Set font
	        titlePanel.add(titleLabel);
	        titleLabel.setVisible(true);

	        JPanel separatorPanel = new JPanel();
	        		//(new FlowLayout(FlowLayout.CENTER, 0, 5));
	        separatorPanel.setPreferredSize(new Dimension(170, 5)); // Set preferred size
	        separatorPanel.setOpaque(false); // Make the panel transparent
	        separatorPanel.setVisible(true);
	        
	        // Create separator
	        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
	        separator.setBackground(new Color(255, 255, 255));
	        separator.setForeground(new Color(255, 255, 255));
	        separatorPanel.add(separator); // Add separator to separator panel
	        separator.setVisible(true);


	        JLabel iconLabel = new JLabel(new ImageIcon(Home.class.getResource("/adminIcons/icons8-book-red 48.png")));
	        iconLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
	        titleBar.add(iconLabel, BorderLayout.WEST); // Align to the left
	        
	        JPanel buttonPanel = new JPanel(); // Right-aligned buttons
	        buttonPanel.setPreferredSize(new Dimension(120, 15));
	        buttonPanel.setOpaque(false); // Make button panel transparent
	        
	        JButton minimizeButton = new JButton("-");
	        minimizeButton.setBounds(34, 4, 20, 20);

	        minimizeButton.setFocusCycleRoot(true);
	        minimizeButton.setBorderPainted(false);
	        minimizeButton.setBorder(null);
	        minimizeButton.setPreferredSize(new Dimension(20, 20));
	        minimizeButton.setFocusPainted(false);
	        minimizeButton.setFocusable(true);
	        minimizeButton.setMargin(new Insets(4, 4, 4, 4));
	        minimizeButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
	        minimizeButton.setBackground(new Color(0, 0, 204));
	        minimizeButton.setForeground(new Color(0, 0, 0));
	        
	        
	        
	        
	        JButton maximizeButton = new JButton("");
	        maximizeButton.setBounds(60, 4, 20, 20);

	        maximizeButton.setFocusCycleRoot(true);
	        maximizeButton.setBorderPainted(false);
	        maximizeButton.setBorder(null);
	        maximizeButton.setRequestFocusEnabled(false);
	        maximizeButton.setIcon(new ImageIcon(Home.class.getResource("/adminIcons/icons8-restore-down-16.png")));
	        maximizeButton.setPreferredSize(new Dimension(20, 20));
	        maximizeButton.setFocusPainted(false);
	        maximizeButton.setFocusable(false);
	        maximizeButton.setMargin(new Insets(4, 4, 4, 4));
	        maximizeButton.setFont(new Font("Tahoma", Font.BOLD, 25));
	        maximizeButton.setBackground(new Color(0, 0, 204));
	        maximizeButton.setForeground(new Color(255, 255, 255));
	        
	        JButton closeButton = new JButton("x");
	        closeButton.setSize(20, 20);
	        closeButton.setLocation(new Point(90, 4));
	        
	        closeButton.setFocusCycleRoot(true);
	        closeButton.setBorderPainted(false);
	        closeButton.setBorder(null);
	        closeButton.setPreferredSize(new Dimension(20, 20));
	        closeButton.setFocusPainted(false);
	        closeButton.setFocusable(false);
	        closeButton.setMargin(new Insets(4, 4, 4, 4));
	        closeButton.setFont(new Font("Tahoma", Font.BOLD, 25));
	        closeButton.setBackground(new Color(0, 0, 204));
	        closeButton.setForeground(new Color(204, 0, 0));
	        buttonPanel.setLayout(null);
	        
	        buttonPanel.add(minimizeButton);
	        buttonPanel.add(maximizeButton);
	        buttonPanel.add(closeButton);
	        titleBar.add(buttonPanel, BorderLayout.EAST);
	        
	        
	        getContentPane().add(titleBar, BorderLayout.NORTH);
	        
	        JPanel paneltitle = new JPanel();
	        paneltitle.setBackground(new Color(0, 0, 204));
	        titleBar.add(paneltitle, BorderLayout.CENTER);
	        
	        JLabel lblNewLabel = new JLabel("Library Management System");
	        lblNewLabel.setBackground(new Color(0, 0, 255));
	        lblNewLabel.setForeground(new Color(255, 255, 255));
	        lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.BOLD, 25));
	        paneltitle.add(lblNewLabel);
	        setVisible(true);

	        minimizeButton.addActionListener(e -> setState(Frame.ICONIFIED)); // Minimize the frame
	        
	        
	        maximizeButton.addActionListener(e -> {
	        	isMaximized = !isMaximized;
	            titleBar.revalidate();
	            titleBar.repaint();
	            if (isMaximized) {
	                setExtendedState(getExtendedState() | Frame.MAXIMIZED_BOTH);
	                titleBar.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 50));
	                
	            } else {
	                setExtendedState(getExtendedState() & ~Frame.MAXIMIZED_BOTH);
	                titleBar.setSize(new Dimension(1100, 50));
	            }
	        });
	        
	        // Add mouse listener to enable dragging
	        titleBar.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent e) {
	                posX = e.getX();
	                posY = e.getY();
	            }
	        });
	        titleBar.addMouseMotionListener(new MouseAdapter() {
	            public void mouseDragged(MouseEvent e) {
	                setLocation(e.getXOnScreen() - posX, e.getYOnScreen() - posY);
	            }
	        });
	        
	        closeButton.addActionListener(e -> dispose()); // Close the frame


	       
	        
	        
	        
	        
	        

	        // Create side menu
	        sideMenu = new JPanel();
	        sideMenu.setBackground(new Color(255, 255, 255));
	        sideMenu.setSize(230, 700);
	        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));

            sideMenu.add(Box.createRigidArea(new Dimension(0, 65)));
            

	        // Add menu items to side menu
	        for (String item : menuItems) {
	            JButton button = new JButton(item);
	            //button.setBounds(new Rectangle(0, 0, 170, 35));
	            button.setBackground(new Color(255,255,255));
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
	        cardLayout.setVgap(50);
	        contentPanel.setLayout(cardLayout);

	        // Add panels to content panel
	        HomePage homepage = new HomePage();
	        homepage.setPreferredSize(new Dimension(1100, 600));
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

	    
	    @Override
	    public void setVisible(boolean b) {
	        setUndecorated(true); // Set the frame undecorated before making it visible
	        super.setVisible(b);
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
	    	 SwingUtilities.invokeLater(() -> {
	             new Home();
	         });
	    }
	}


