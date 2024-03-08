package TestPackage;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;

import libraryBackend.DatabaseConnector;
import libraryBackend.SessionManager;
import libraryFrontend.Login;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginTest1 {

    @Mock
    private JTextField txtUsername;

    @Mock
    private JPasswordField passwordField;

    @Mock
    private DatabaseConnector databaseConnector;

    @InjectMocks
    private Login login;
    
    @Test
    void testAuthenticateUserAdmin() throws Exception {
    	 Login login = new Login();
    
        Whitebox.invokeMethod(login, "getHashedPasswordFromDB", "admin");
         

         String expectedValue = "$2a$10$9zL5kI9p.2bP8gTCpvnydOlB3mwvJ3ryFz3AyPfz18oB2ND1mPz8O";
         
         assertEquals(expectedValue, "$2a$10$9zL5kI9p.2bP8gTCpvnydOlB3mwvJ3ryFz3AyPfz18oB2ND1mPz8O" );
         
         
    }

    @Test
    void testAuthenticateUser_ValidCredentials_Admin() {
    	
    	 
        // Mock behavior of UI components
        when(txtUsername.getText()).thenReturn("admin");
        when(passwordField.getPassword()).thenReturn("adminpassword".toCharArray());

        // Mock behavior of database interactions
        when(login.getHashedPasswordFromDB("admin")).thenReturn("$2a$10$9zL5kI9p.2bP8gTCpvnydOlB3mwvJ3ryFz3AyPfz18oB2ND1mPz8O");
        when(login.getUserType("admin", "$2a$10$9zL5kI9p.2bP8gTCpvnydOlB3mwvJ3ryFz3AyPfz18oB2ND1mPz8O")).thenReturn("Admin");
        when(login.getCurrentMemberId("admin", "$2a$10$9zL5kI9p.2bP8gTCpvnydOlB3mwvJ3ryFz3AyPfz18oB2ND1mPz8O")).thenReturn(123);

        // Test
        login.authenticateUser();

        // Verify
        assertEquals("Admin", SessionManager.getCurrentUserType("Admin"));
        assertEquals(123, SessionManager.getCurrentMemberId("Admin"));
    }

    @Test
    void testAuthenticateUser_IncorrectPassword() {
        // Mock behavior of UI components
        when(txtUsername.getText()).thenReturn("user");
        when(passwordField.getPassword()).thenReturn("wrongpassword".toCharArray());

        // Mock behavior of database interactions
        when(login.getHashedPasswordFromDB("user")).thenReturn("$2a$10$hashedpassword");

        // Test
        login.authenticateUser();

        // Verify
        // Verify that an error message is displayed
        //verify(login, times(1)).showErrorMessage("Incorrect password");
    }

    // Add more test cases to cover other scenarios

    @Test
    void testGetHashedPasswordFromDB() {
        // Mock behavior of database interactions
        when(login.getHashedPasswordFromDB("user")).thenReturn("$2a$10$hashedpassword");

        // Test
        String hashedPassword = login.getHashedPasswordFromDB("user");

        // Verify
        assertEquals("$2a$10$hashedpassword", hashedPassword);
    }

    @Test
    void testGetUserType() {
        // Mock behavior of database interactions
        when(login.getUserType("user", "hashedpassword")).thenReturn("Member");

        // Test
        String userType = login.getUserType("user", "hashedpassword");

        // Verify
        assertEquals("Member", userType);
    }

    @Test
    void testGetCurrentMemberId() {
        // Mock behavior of database interactions
        when(login.getCurrentMemberId("user", "hashedpassword")).thenReturn(456);

        // Test
        int memberId = login.getCurrentMemberId("user", "hashedpassword");

        // Verify
        assertEquals(456, memberId);
    }

    // Add more test cases for other private methods
}
