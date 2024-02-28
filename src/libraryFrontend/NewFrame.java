package libraryFrontend;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

public class NewFrame extends JFrame {

	 private JPanel sideMenu;
	    private JPanel contentPanel;
	    private CardLayout cardLayout;
	    
	    private JButton manageBook;
	    private JButton manageMember;
	    private JButton issueBook;
	    private JButton ReturnBook;
	    

	   // private JButton menuToggleButton;

	    public NewFrame() {
	    	setIconImage(Toolkit.getDefaultToolkit().getImage(NewFrame.class.getResource("/adminIcons/icons8-book-96.png")));
	        setTitle(" Library Management System");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(800, 600);
	        setLocationRelativeTo(null);

	        // Create side menu
	        sideMenu = new JPanel();
	        sideMenu.setBackground(new Color(230, 230, 250));
	        sideMenu.setSize(new Dimension(170, 30));
	        sideMenu.setPreferredSize(new Dimension(170, 40));
	        
	        manageBook = new JButton("Manage Book");
	        manageBook.setFont(new Font("Simplified Arabic Fixed", Font.PLAIN, 16));
	        manageBook.setBackground(new Color(230, 230, 250));
	        manageBook.setBounds(new Rectangle(0, 0, 170, 35));
	        manageBook.setPreferredSize(new Dimension(170, 35));
	        
	        manageMember = new JButton("Manage Member");
	        manageMember.setFont(new Font("Simplified Arabic Fixed", Font.PLAIN, 16));
	        manageMember.setBackground(new Color(230, 230, 250));
	        manageMember.setPreferredSize(new Dimension(170, 30));
	        manageMember.setSize(new Dimension(200, 30));
	        manageMember.setBounds(new Rectangle(0, 31, 170, 35));
	        
	        issueBook = new JButton("Issue Book");
	        issueBook.setPreferredSize(new Dimension(170, 23));
	        issueBook.setFont(new Font("Simplified Arabic Fixed", Font.PLAIN, 16));
	        issueBook.setBackground(new Color(230, 230, 250));
	        issueBook.setBounds(0, 65, 170, 35);

	        manageBook.addActionListener(new MenuButtonListener());
	        manageMember.addActionListener(new MenuButtonListener());
	        issueBook.addActionListener(new MenuButtonListener());
	        sideMenu.setLayout(null);

	        ReturnBook = new JButton("Return Book");
	        ReturnBook.setPreferredSize(new Dimension(170, 23));
	        ReturnBook.setFont(new Font("Simplified Arabic Fixed", Font.PLAIN, 16));
	        ReturnBook.setBackground(new Color(230, 230, 250));
	        ReturnBook.setBounds(0, 99, 170, 35);
	        sideMenu.add(ReturnBook);
	        
	        sideMenu.add(manageBook);
	        sideMenu.add(manageMember);
	        sideMenu.add(issueBook);
	        // Create content panel with CardLayout
	        contentPanel = new JPanel();
	        cardLayout = new CardLayout();
	        contentPanel.setLayout(cardLayout);

	        JPanel panel1 = new JPanel();
	        panel1.setBackground(Color.RED);
	        panel1.add(new JLabel("Panel 1 Content"));

	        JPanel panel2 = new JPanel();
	        panel2.setBackground(Color.GREEN);
	        panel2.add(new JLabel("Panel 2 Content"));

	        JPanel panel3 = new JPanel();
	        panel3.setBackground(Color.BLUE);
	        panel3.add(new JLabel("Panel 3 Content"));

	        contentPanel.add(panel1, "Panel 1");
	        contentPanel.add(panel2, "Panel 2");
	        contentPanel.add(panel3, "Panel 3");

	        
	       /* menuToggleButton = new JButton(""); // Unicode character for three horizontal lines
	        menuToggleButton.setMargin(new Insets(0, 0, 0, 0));
	        menuToggleButton.setBounds(new Rectangle(0, 0, 32, 32));
	        menuToggleButton.setBackground(new Color(255, 255, 255));
	        menuToggleButton.setIcon(new ImageIcon(NewFrame.class.getResource("/adminIcons/icons8-menu-30.png")));
	        menuToggleButton.addActionListener(e -> toggleSideMenu());
	        */


	        // Add components to main frame
	        // Add components to main frame
	       // JPanel headerPanel = new JPanel(new BorderLayout());
	        //headerPanel.add(menuToggleButton, BorderLayout.WEST);
	        getContentPane().setLayout(new BorderLayout());
	       // getContentPane().add(headerPanel, BorderLayout.NORTH); // Add menu toggle button to top
	        getContentPane().add(sideMenu, BorderLayout.WEST); // Initially hidden
	        
	      
	        getContentPane().add(contentPanel, BorderLayout.CENTER);
	        setVisible(true);
	    }

	    private class MenuButtonListener implements ActionListener {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            JButton button = (JButton) e.getSource();
	            String buttonText = button.getText();
	            cardLayout.show(contentPanel, buttonText);
	            //sideMenu.setVisible(false); // Hide side menu after selecting a panel
	        }
	    }
	    
	   /* private void toggleSideMenu() {
	        if (sideMenu.isVisible()) {
	            sideMenu.setVisible(false);
	        } else {
	            sideMenu.setVisible(true);
	        }
	    }
*/
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> new NewFrame());
	    }
}
