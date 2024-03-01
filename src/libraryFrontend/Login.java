package libraryFrontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.mindrot.jbcrypt.BCrypt;

import libraryBackend.SessionManager;
import libraryBackend.UserAuthentication;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import javax.swing.border.MatteBorder;



public class Login extends JFrame  {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JButton btnLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/adminIcons/icons8-book-red 48.png")));
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		initUI();
		
		
		 btnLogin.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                authenticateUser();
	            }

				
	        });
	    }
		
	
	
	private void authenticateUser() {
	    String username = txtUsername.getText();
	    String password = new String(passwordField.getPassword());
	    
	    String hashedPasswordFromDB = getHashedPasswordFromDB(username);
	    
	    if (hashedPasswordFromDB != null) {
	        // Step 3: Verify the entered password by comparing its hash with the hash from the database
	        if (BCrypt.checkpw(password, hashedPasswordFromDB)) {
	            // Passwords match, authentication successful
	            System.out.println("Password is correct.");

	            // Step 4: Proceed with user authentication using the userType and memberId
	            String userType = getUserType(username, hashedPasswordFromDB);
	            int memberId = getCurrentMemberId(username, hashedPasswordFromDB);

	            if (userType != null && memberId > 0) {
	                System.out.println("Authentication successful. User role: " + userType);

	                // Set the logged-in user session
	                SessionManager.setLoggedInUser(username, userType, memberId);

	                // Redirect to appropriate frame based on user type
	                if (userType.equals("Admin")) {
	                    Home mainFrame = new Home();
	                    mainFrame.setVisible(true);
	                    dispose();
	                } else if (userType.equals("Member")) {
	                	MemberMainFrame mainFrame = new MemberMainFrame();
	                	mainFrame.setVisible(true);
	                    dispose();
	                } else {
	                    JOptionPane.showMessageDialog(this, "Unrecognized user type: " + userType, "Login Error", JOptionPane.ERROR_MESSAGE);
	                    System.out.println("Unrecognized user type: " + userType);
	                }
	            } else {
	                // Invalid userType or memberId
	                JOptionPane.showMessageDialog(this, "Invalid user type or member ID", "Login Error", JOptionPane.ERROR_MESSAGE);
	                System.out.println("Invalid user type or member ID.");
	            }
	        } else {
	            // Passwords don't match, authentication failed
	            JOptionPane.showMessageDialog(this, "Incorrect password", "Login Failed", JOptionPane.ERROR_MESSAGE);
	            System.out.println("Authentication failed. Incorrect password.");
	        }
	    } else {
	        // Username not found in the database
	        JOptionPane.showMessageDialog(this, "Username not found", "Login Failed", JOptionPane.ERROR_MESSAGE);
	        System.out.println("Username not found in the database.");
	    }
	}
	
	
	private String getHashedPasswordFromDB(String username) {
    String hashedPassword = null;
    
    String query = "SELECT Password FROM UserCredentials WHERE Username = ?";
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementSystem", "root", "Elias$#22");
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            hashedPassword = resultSet.getString("Password");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return hashedPassword;
}


	private int getCurrentMemberId(String username, String password) {
	    int memberId = 0;
	    
	    String query = "SELECT MemberId FROM UserCredentials WHERE BINARY Username = ? AND BINARY Password = ?";
	    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementSystem", "root", "Elias$#22");
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, username);
	        preparedStatement.setString(2, password);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            memberId = resultSet.getInt("memberId");
	            
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return memberId;
	    
	}

	private String getUserType(String username, String password) {
	    String userType = null;
	    
	    String query = "SELECT UserType FROM UserCredentials WHERE BINARY Username = ? AND BINARY Password = ?";
	    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementSystem", "root", "Elias$#22");
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, username);
	        preparedStatement.setString(2, password);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            userType = resultSet.getString("UserType");
	            
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return userType;
	    
	}


	private void initUI() {
		contentPane = new JPanel();
		contentPane.setBackground(new Color(211, 211, 211));
		contentPane.setAlignmentY(1.0f);
		contentPane.setAlignmentX(1.0f);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 102, 255));
		panel.setName("Login");
		panel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Login", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(102, 102, 255)));
		panel.setPreferredSize(new Dimension(250, 120));
		panel.setMaximumSize(new Dimension(400, 300));
		panel.setBounds(40, 39, 344, 200);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(new Color(0, 0, 0));
		lblUsername.setBackground(new Color(51, 102, 255));
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsername.setBounds(31, 33, 120, 30);
		panel.add(lblUsername);
		lblUsername.setPreferredSize(new Dimension(150, 30));
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(new Color(0, 0, 0));
		lblPassword.setBackground(new Color(51, 102, 255));
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(31, 77, 120, 30);
		panel.add(lblPassword);
		lblPassword.setPreferredSize(new Dimension(150, 30));
		
		txtUsername = new JTextField();
		txtUsername.setBackground(new Color(51, 102, 255));
		txtUsername.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 255, 255)));
		txtUsername.setBounds(151, 33, 157, 30);
		panel.add(txtUsername);
		txtUsername.setPreferredSize(new Dimension(150, 30));
		txtUsername.setColumns(10);
		
		btnLogin = new JButton("Login");
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBackground(new Color(0, 0, 102));
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogin.setBounds(31, 131, 90, 30);
		panel.add(btnLogin);
		btnLogin.setPreferredSize(new Dimension(50, 30));
		
		
		
		JLabel lblForgotPassword = new JLabel("Forgot Password?");
		lblForgotPassword.setForeground(new Color(0, 0, 0));
		lblForgotPassword.setBackground(new Color(51, 102, 255));
		lblForgotPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblForgotPassword.setBounds(151, 131, 157, 30);
		panel.add(lblForgotPassword);
		lblForgotPassword.setPreferredSize(new Dimension(150, 30));
		
		passwordField = new JPasswordField();
		passwordField.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 255, 255)));
		passwordField.setBackground(new Color(51, 102, 255));
		passwordField.setBounds(151, 81, 157, 30);
		panel.add(passwordField);
	
		}

	
}
