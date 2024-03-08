package TestPackage;import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;

import libraryBackend.DatabaseConnector;
import libraryBackend.SessionManager;
import libraryFrontend.Login;


public class LoginTest {

    @Mock
    private JTextField txtUsername;

    @Mock
    private JPasswordField passwordField;

    @Mock
    private DatabaseConnector databaseConnector;

    @BeforeEach
    void setUp() {
       MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateUser_ValidCredentials_Admin() {
        // Mock UI components
        Login login = new Login();
        /* String method = "getHashedPasswordFromDB";
   
        Whitebox.invokeMethod(login, "getHashedPasswordFromDB", "admin");
        

        String expectedValue = "$2a$10$9zL5kI9p.2bP8gTCpvnydOlB3mwvJ3ryFz3AyPfz18oB2ND1mPz8O";
        
        assertEquals(expectedValue, "$2a$10$9zL5kI9p.2bP8gTCpvnydOlB3mwvJ3ryFz3AyPfz18oB2ND1mPz8O" );
        
        
        */
        
        login.txtUsername = txtUsername;
        login.passwordField = passwordField;

        // Mock behavior of UI components
        when(txtUsername.getText()).thenReturn("admin");
        when(passwordField.getPassword()).thenReturn("adminpassword".toCharArray());

        // Mock database interactions
        when(databaseConnector.getHashedPasswordFromDB("admin")).thenReturn("$2a$10$9zL5kI9p.2bP8gTCpvnydOlB3mwvJ3ryFz3AyPfz18oB2ND1mPz8O");
        when(databaseConnector.getUserType("admin", "$2a$10$9zL5kI9p.2bP8gTCpvnydOlB3mwvJ3ryFz3AyPfz18oB2ND1mPz8O")).thenReturn("Admin");
        when(databaseConnector.getCurrentMemberId("admin", "$2a$10$9zL5kI9p.2bP8gTCpvnydOlB3mwvJ3ryFz3AyPfz18oB2ND1mPz8O")).thenReturn(123);

        // Test
        login.authenticateUser();

        // Verify
        assertEquals("Admin", SessionManager.getLoggedInUserType());
        assertEquals(123, SessionManager.getLoggedInMemberId());
    }

    // Add more test cases to cover other scenarios
}
