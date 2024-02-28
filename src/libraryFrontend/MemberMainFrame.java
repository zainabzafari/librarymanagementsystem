package libraryFrontend;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;


public class MemberMainFrame extends JFrame {


    private JPanel sideMenu;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    private List<String> menuItems = Arrays.asList("Home Page","Reserve Book", "List of Reserved Books", "List of Borrowed Books");

    public MemberMainFrame() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        // Create side menu
        sideMenu = new JPanel();
        sideMenu.setBackground(new Color(250, 250, 250));
        sideMenu.setSize(240, 700);
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));

        sideMenu.add(Box.createRigidArea(new Dimension(0, 40)));

        // Add menu items to side menu
        for (String item : menuItems) {
            JButton button = new JButton(item);
            //button.setBounds(new Rectangle(0, 0, 170, 35));
            button.setBackground(new Color(250,250,250));
            button.setFont(new Font("Simplified Arabic Fixed", Font.PLAIN, 16));
            button.setPreferredSize(new Dimension(240, 35));
            button.setMaximumSize(new Dimension(240, 40));
            button.setSize(new Dimension(240,35));
            button.setMinimumSize(new Dimension(240,35));
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
        MemberHomePage memberhomepage = new MemberHomePage();
        contentPanel.add(memberhomepage, "Home Page");
        

        MemberPanel memberPanel = new MemberPanel();
        contentPanel.add(memberPanel, "Reserve Book");
        
        MemberReservedBooks memberreservedbook = new MemberReservedBooks();
        contentPanel.add(memberreservedbook, "List of Reserved Books");


        MemberBorrowedBook memberborrowedbook = new MemberBorrowedBook();
        contentPanel.add(memberborrowedbook, "List of Borrowed Books");
        
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
        SwingUtilities.invokeLater(() -> new MemberMainFrame());
    }

}
