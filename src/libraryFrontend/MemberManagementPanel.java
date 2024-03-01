package libraryFrontend;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import libraryBackend.CustomTableCellRenderer;
import libraryBackend.CustomTableHeaderRenderer;
import libraryBackend.Library;
import libraryBackend.Member;
import org.mindrot.jbcrypt.BCrypt;
import java.awt.Color;
import app.bolivia.swing.JCTextField;
import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;

public class MemberManagementPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField emailField;
	private JTextField phoneNoField;
	private JTextField addressField;
	private JTextField passwordField;
	private JTextField searchField;
	private JTable memberTable;
	private DefaultTableModel tableModel;
	private JButton addButton;
	private JButton deleteButton;
	private JButton refreshButton;
	
	
	
	/**
	 * Create the panel.
	 */
	public MemberManagementPanel() {
		setBackground(new Color(255, 255, 255));
		setPreferredSize(new Dimension(1000, 1000));
		
		initializeUI();
		
		 addButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                addMember();
	            }

				
	        });

	        deleteButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                deleteMember();
	            }
	        });
	        
	        searchField.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                searchMember();
	            }
	        });
	        

	        refreshButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                refreshMemberList();
	            }
	        });

	        // Initially refresh the member list
	        refreshMemberList();
	    }
		
		
	private void addMember() {
	    String firstName = firstNameField.getText();
	    String lastName = lastNameField.getText();
	    String address = addressField.getText();
	    String phoneNo = phoneNoField.getText();
	    String email = emailField.getText();
	    
	    if (!phoneNo.matches("\\d{10,}")) {
	        JOptionPane.showMessageDialog(this, "Invalid phone number. Please enter a valid phone number.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    String namePattern = "^[a-zA-Z]+$";

	    if (!firstName.matches(namePattern) || !lastName.matches(namePattern)) {
	        // Display an error message or take appropriate action
	        JOptionPane.showMessageDialog(this, "First name and last name should only contain alphabetic characters", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Validate email
	    String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	    if (!email.matches(emailPattern)) {
	        JOptionPane.showMessageDialog(this, "Invalid email address. Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }


	    // Call Library method to add the member and get the generated member ID
	    int memberId = Library.addMember( firstName, lastName, email, phoneNo, address);
	   

	    if (memberId>0) {
		    String username = generateUsername(firstName, lastName);
	        String password = passwordField.getText();
	        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
	        boolean credentialsSuccess = Library.addUserCredentials( memberId, username, hashedPassword );

	        if (credentialsSuccess) {
	            JOptionPane.showMessageDialog(this, "Member added successfully");
	            clearFields();
	            refreshMemberList();
	        } else {
	            JOptionPane.showMessageDialog(this, "Failed to add credentials", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Failed to add member information", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	   
	}


	private String generateUsername(String firstName, String lastName) {
		String username = firstName.toLowerCase() + "." + lastName.toLowerCase();
	    // Check if username already exists
	    if (Library.isUsernameTaken(username)) {
	        // Append a number to make it unique
	        int i = 1;
	        while (Library.isUsernameTaken(username + i)) {
	            i++;
	        }
	        username += i;
	    }
	    return username;
	}


	private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        passwordField.setText("");
        addressField.setText("");
        phoneNoField.setText("");
        emailField.setText("");
		
	}


	private void deleteMember() {
	    int selectedRow = memberTable.getSelectedRow();
	    if (selectedRow == -1) {
	        JOptionPane.showMessageDialog(this, "Please select a member to delete", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    String memberId = (String) tableModel.getValueAt(selectedRow, 0);

	    // Delete member information and corresponding user credentials
	    boolean deleteSuccess = Library.deleteMember(memberId);

	    if (deleteSuccess) {
	        JOptionPane.showMessageDialog(this, "Member deleted successfully");
	        refreshMemberList();
	    } else {
	        JOptionPane.showMessageDialog(this, "Failed to delete member", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}


	private void searchMember() {
	    String keyword = searchField.getText(); //chatGpt says usernameField while it should be searchfield

	    // Search for members in the database
	    List<Member> searchResult = Library.searchMembers(keyword);

	    updateTable(searchResult);
	}

	private void refreshMemberList() {
	    // Retrieve all members from the database
		 List<Member> members = Library.getAllMembers();
		    if (members != null) {
		        updateTable(members);
		    } else {
		        // Handle the case when the list of members is null
		        System.out.println("Error: Failed to retrieve member list");
		    }
		
	 
	}

	private void updateTable(List<Member> members) {
	    // Clear existing data
	    tableModel.setRowCount(0);

	    // Add members to table
	    for (Member member : members) {
	        tableModel.addRow(new Object[]{member.getMemberId(), member.getFirstName(), member.getLastName(),  member.getEmail(), member.getPhoneNo(), member.getAddress()});
	    }

	}
	
	private void initializeUI() {
		setLayout(null);
		
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(new Color(255, 255, 255));
		inputPanel.setBounds(0, 12, 326, 800);
		inputPanel.setPreferredSize(new Dimension(350, 700));
		add(inputPanel);
		inputPanel.setLayout(null);
		
		JLabel lblFirstname = new JLabel("Firstname:");
		lblFirstname.setBounds(24, 170, 88, 36);
		lblFirstname.setPreferredSize(new Dimension(120, 30));
		lblFirstname.setFont(new Font("Serif", Font.PLAIN, 18));
		inputPanel.add(lblFirstname);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(24, 213, 93, 36);
		lblLastName.setPreferredSize(new Dimension(120, 30));
		lblLastName.setFont(new Font("Serif", Font.PLAIN, 18));
		inputPanel.add(lblLastName);
		
		
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(24, 253, 88, 36);
		lblEmail.setPreferredSize(new Dimension(120, 30));
		lblEmail.setFont(new Font("Serif", Font.PLAIN, 18));
		inputPanel.add(lblEmail);
		
		JLabel lblPhoneNo = new JLabel("Phone No:");
		lblPhoneNo.setBounds(24, 295, 88, 36);
		lblPhoneNo.setPreferredSize(new Dimension(120, 30));
		lblPhoneNo.setFont(new Font("Serif", Font.PLAIN, 18));
		inputPanel.add(lblPhoneNo);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(24, 336, 93, 36);
		lblAddress.setPreferredSize(new Dimension(120, 30));
		lblAddress.setFont(new Font("Serif", Font.PLAIN, 18));
		inputPanel.add(lblAddress);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(24, 377, 88, 36);
		lblPassword.setPreferredSize(new Dimension(120, 30));
		lblPassword.setFont(new Font("Serif", Font.PLAIN, 18));
		inputPanel.add(lblPassword);
		
		
		firstNameField = new JTextField();
		firstNameField.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
		firstNameField.setBounds(122, 175, 178, 30);
		firstNameField.setPreferredSize(new Dimension(180, 32));
		firstNameField.setFont(new Font("Serif", Font.PLAIN, 14));
		firstNameField.setColumns(10);
		inputPanel.add(firstNameField);
		
		lastNameField = new JTextField();
		lastNameField.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
		lastNameField.setBounds(122, 218, 178, 30);
		lastNameField.setPreferredSize(new Dimension(180, 32));
		lastNameField.setFont(new Font("Serif", Font.PLAIN, 14));
		lastNameField.setColumns(10);
		inputPanel.add(lastNameField);
		
		
		emailField = new JTextField();
		emailField.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
		emailField.setBounds(122, 259, 178, 30);
		emailField.setPreferredSize(new Dimension(180, 32));
		emailField.setFont(new Font("Serif", Font.PLAIN, 14));
		emailField.setColumns(10);
		inputPanel.add(emailField);
		
		phoneNoField = new JTextField();
		phoneNoField.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
		phoneNoField.setBounds(122, 301, 178, 30);
		phoneNoField.setPreferredSize(new Dimension(180, 32));
		phoneNoField.setFont(new Font("Serif", Font.PLAIN, 14));
		phoneNoField.setColumns(10);
		inputPanel.add(phoneNoField);
		
		addressField = new JTextField();
		addressField.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
		addressField.setBounds(122, 342, 178, 30);
		addressField.setPreferredSize(new Dimension(180, 32));
		addressField.setFont(new Font("Serif", Font.PLAIN, 14));
		addressField.setColumns(10);
		inputPanel.add(addressField);
		
		passwordField = new JTextField();
		passwordField.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
		passwordField.setBounds(122, 383, 178, 30);
		passwordField.setPreferredSize(new Dimension(180, 32));
		passwordField.setFont(new Font("Serif", Font.PLAIN, 14));
		passwordField.setColumns(10);
		inputPanel.add(passwordField);
		
		addButton = new JButton("Add ");
		addButton.setBackground(new Color(0, 153, 255));
		addButton.setBounds(24, 457, 121, 35);
		addButton.setFont(new Font("Serif", Font.PLAIN, 16));
		addButton.setPreferredSize(new Dimension(100, 30));
		inputPanel.add(addButton);
		
		deleteButton = new JButton("Delete ");
		deleteButton.setBackground(new Color(0, 153, 255));
		deleteButton.setBounds(179, 457, 121, 35);
		deleteButton.setPreferredSize(new Dimension(100, 30));
		deleteButton.setFont(new Font("Serif", Font.PLAIN, 16));
		inputPanel.add(deleteButton);
		
		refreshButton = new JButton("Refresh");
		refreshButton.setBackground(new Color(0, 153, 255));
		refreshButton.setBounds(24, 503, 121, 35);
		refreshButton.setPreferredSize(new Dimension(120, 35));
		refreshButton.setFont(new Font("Serif", Font.PLAIN, 16));
		inputPanel.add(refreshButton);
		
		JLabel lblSearch = new JLabel("Search:");
		lblSearch.setBounds(24, 96, 61, 36);
		lblSearch.setPreferredSize(new Dimension(120, 30));
		lblSearch.setFont(new Font("Serif", Font.PLAIN, 18));
		inputPanel.add(lblSearch);
		
		searchField = new JTextField();
		searchField.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
		searchField.setBounds(122, 108, 178, 30);
		searchField.setPreferredSize(new Dimension(180, 32));
		searchField.setFont(new Font("Serif", Font.PLAIN, 14));
		searchField.setColumns(10);
		inputPanel.add(searchField);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setBackground(new Color(0, 153, 255));
		btnEdit.setPreferredSize(new Dimension(120, 35));
		btnEdit.setFont(new Font("Serif", Font.PLAIN, 16));
		btnEdit.setBounds(179, 503, 121, 35);
		inputPanel.add(btnEdit);
		
		JCTextField bookIdField_1_1_1 = new JCTextField();
		bookIdField_1_1_1.setVerifyInputWhenFocusTarget(false);
		bookIdField_1_1_1.setForeground(Color.BLACK);
		bookIdField_1_1_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 25));
		bookIdField_1_1_1.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
		bookIdField_1_1_1.setBackground(Color.BLACK);
		bookIdField_1_1_1.setBounds(111, 83, 175, 2);
		inputPanel.add(bookIdField_1_1_1);
		
		JLabel lblBookDetail = new JLabel("Book Detail");
		lblBookDetail.setForeground(Color.BLACK);
		lblBookDetail.setFont(new Font("Palatino Linotype", Font.PLAIN, 22));
		lblBookDetail.setBackground(Color.WHITE);
		lblBookDetail.setBounds(109, 32, 177, 40);
		inputPanel.add(lblBookDetail);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(MemberManagementPanel.class.getResource("/adminIcons/icons8-member-48.png")));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(24, 10, 75, 71);
		inputPanel.add(lblNewLabel_1);
		
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.setBackground(new Color(255, 255, 255));
		tableScrollPane.setBounds(326, 100, 765, 700);
		tableScrollPane.setPreferredSize(new Dimension(900, 250));
		add(tableScrollPane);
		
		tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Member ID", "First Name", "Last Name", "Email", "Phone No", "Address"});
        memberTable = new JTable(tableModel);
        memberTable.setPreferredScrollableViewportSize(new Dimension(1000, 300));
        

        memberTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        memberTable.getTableHeader().setDefaultRenderer(new CustomTableHeaderRenderer(memberTable));
        
        tableScrollPane.setViewportView(memberTable);
        
        JLabel lblNewLabel_1_1 = new JLabel("");
        lblNewLabel_1_1.setIcon(new ImageIcon(MemberManagementPanel.class.getResource("/adminIcons/icons8-setting-64.png")));
        lblNewLabel_1_1.setForeground(Color.WHITE);
        lblNewLabel_1_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
        lblNewLabel_1_1.setBounds(536, 12, 65, 71);
        add(lblNewLabel_1_1);
        
        JLabel lblManageMmbers = new JLabel("Manage Mmbers");
        lblManageMmbers.setForeground(Color.BLACK);
        lblManageMmbers.setFont(new Font("PMingLiU-ExtB", Font.BOLD, 24));
        lblManageMmbers.setBackground(Color.WHITE);
        lblManageMmbers.setBounds(621, 34, 206, 40);
        add(lblManageMmbers);
        
        JCTextField bookIdField_1_1_1_1 = new JCTextField();
        bookIdField_1_1_1_1.setVerifyInputWhenFocusTarget(false);
        bookIdField_1_1_1_1.setForeground(Color.BLACK);
        bookIdField_1_1_1_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 25));
        bookIdField_1_1_1_1.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
        bookIdField_1_1_1_1.setBackground(Color.BLACK);
        bookIdField_1_1_1_1.setBounds(623, 85, 163, 2);
        add(bookIdField_1_1_1_1);
        memberTable.setVisible(true);
		
		

	}
}
