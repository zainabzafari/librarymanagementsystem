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
import libraryBackend.Library;
import libraryBackend.Member;
import org.mindrot.jbcrypt.BCrypt;

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
	private JButton searchButton;
	
	
	
	/**
	 * Create the panel.
	 */
	public MemberManagementPanel() {
		setPreferredSize(new Dimension(1000, 1000));
		setLayout(new BorderLayout(0, 0));
		
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

	        searchButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                searchMember();
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
		
		
		JPanel inputPanel = new JPanel();
		inputPanel.setPreferredSize(new Dimension(800, 460));
		add(inputPanel, BorderLayout.NORTH);
		inputPanel.setLayout(null);
		
		JLabel lblFirstname = new JLabel("Firstname:");
		lblFirstname.setPreferredSize(new Dimension(120, 30));
		lblFirstname.setFont(new Font("Serif", Font.PLAIN, 18));
		lblFirstname.setBounds(40, 159, 120, 36);
		inputPanel.add(lblFirstname);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setPreferredSize(new Dimension(120, 30));
		lblLastName.setFont(new Font("Serif", Font.PLAIN, 18));
		lblLastName.setBounds(40, 202, 120, 36);
		inputPanel.add(lblLastName);
		
		
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setPreferredSize(new Dimension(120, 30));
		lblEmail.setFont(new Font("Serif", Font.PLAIN, 18));
		lblEmail.setBounds(40, 242, 120, 36);
		inputPanel.add(lblEmail);
		
		JLabel lblPhoneNo = new JLabel("Phone No:");
		lblPhoneNo.setPreferredSize(new Dimension(120, 30));
		lblPhoneNo.setFont(new Font("Serif", Font.PLAIN, 18));
		lblPhoneNo.setBounds(40, 284, 120, 36);
		inputPanel.add(lblPhoneNo);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setPreferredSize(new Dimension(120, 30));
		lblAddress.setFont(new Font("Serif", Font.PLAIN, 18));
		lblAddress.setBounds(40, 325, 120, 36);
		inputPanel.add(lblAddress);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setPreferredSize(new Dimension(120, 30));
		lblPassword.setFont(new Font("Serif", Font.PLAIN, 18));
		lblPassword.setBounds(40, 366, 120, 36);
		inputPanel.add(lblPassword);
		
		
		firstNameField = new JTextField();
		firstNameField.setSize(new Dimension(200, 30));
		firstNameField.setPreferredSize(new Dimension(200, 30));
		firstNameField.setFont(new Font("Serif", Font.PLAIN, 14));
		firstNameField.setColumns(10);
		firstNameField.setBounds(214, 163, 311, 30);
		inputPanel.add(firstNameField);
		
		lastNameField = new JTextField();
		lastNameField.setSize(new Dimension(200, 30));
		lastNameField.setPreferredSize(new Dimension(200, 30));
		lastNameField.setFont(new Font("Serif", Font.PLAIN, 14));
		lastNameField.setColumns(10);
		lastNameField.setBounds(214, 206, 311, 30);
		inputPanel.add(lastNameField);
		
		
		emailField = new JTextField();
		emailField.setSize(new Dimension(200, 30));
		emailField.setPreferredSize(new Dimension(200, 30));
		emailField.setFont(new Font("Serif", Font.PLAIN, 14));
		emailField.setColumns(10);
		emailField.setBounds(214, 247, 311, 30);
		inputPanel.add(emailField);
		
		phoneNoField = new JTextField();
		phoneNoField.setSize(new Dimension(200, 30));
		phoneNoField.setPreferredSize(new Dimension(200, 30));
		phoneNoField.setFont(new Font("Serif", Font.PLAIN, 14));
		phoneNoField.setColumns(10);
		phoneNoField.setBounds(214, 289, 311, 30);
		inputPanel.add(phoneNoField);
		
		addressField = new JTextField();
		addressField.setSize(new Dimension(200, 30));
		addressField.setPreferredSize(new Dimension(200, 30));
		addressField.setFont(new Font("Serif", Font.PLAIN, 14));
		addressField.setColumns(10);
		addressField.setBounds(214, 330, 311, 30);
		inputPanel.add(addressField);
		
		passwordField = new JTextField();
		passwordField.setSize(new Dimension(200, 30));
		passwordField.setPreferredSize(new Dimension(200, 30));
		passwordField.setFont(new Font("Serif", Font.PLAIN, 14));
		passwordField.setColumns(10);
		passwordField.setBounds(214, 371, 311, 30);
		inputPanel.add(passwordField);
		
		addButton = new JButton("Add Member");
		addButton.setFont(new Font("Serif", Font.PLAIN, 16));
		addButton.setPreferredSize(new Dimension(120, 35));
		addButton.setBounds(668, 152, 147, 35);
		inputPanel.add(addButton);
		
		deleteButton = new JButton("Delete Member");
		deleteButton.setPreferredSize(new Dimension(120, 35));
		deleteButton.setFont(new Font("Serif", Font.PLAIN, 16));
		deleteButton.setBounds(668, 219, 147, 35);
		inputPanel.add(deleteButton);
		
		refreshButton = new JButton("Refresh");
		refreshButton.setPreferredSize(new Dimension(120, 35));
		refreshButton.setFont(new Font("Serif", Font.PLAIN, 16));
		refreshButton.setBounds(668, 285, 147, 35);
		inputPanel.add(refreshButton);
		
		searchButton = new JButton("Search");
		searchButton.setPreferredSize(new Dimension(120, 35));
		searchButton.setFont(new Font("Serif", Font.PLAIN, 16));
		searchButton.setBounds(668, 48, 147, 35);
		inputPanel.add(searchButton);
		
		JLabel lblSearch = new JLabel("Search:");
		lblSearch.setPreferredSize(new Dimension(120, 30));
		lblSearch.setFont(new Font("Serif", Font.PLAIN, 18));
		lblSearch.setBounds(40, 37, 120, 36);
		inputPanel.add(lblSearch);
		
		searchField = new JTextField();
		searchField.setSize(new Dimension(200, 30));
		searchField.setPreferredSize(new Dimension(200, 30));
		searchField.setFont(new Font("Serif", Font.PLAIN, 14));
		searchField.setColumns(10);
		searchField.setBounds(214, 48, 311, 30);
		inputPanel.add(searchField);
		
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.setPreferredSize(new Dimension(900, 250));
		add(tableScrollPane, BorderLayout.CENTER);
		
		tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Member ID", "First Name", "Last Name", "Email", "Phone No", "Address"});
        memberTable = new JTable(tableModel);
        memberTable.setPreferredScrollableViewportSize(new Dimension(1000, 300));
        tableScrollPane.setViewportView(memberTable);
        memberTable.setVisible(true);
		
		

	}
}
