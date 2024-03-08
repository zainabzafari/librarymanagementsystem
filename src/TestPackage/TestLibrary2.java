package TestPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import libraryBackend.DatabaseConnector;
import libraryBackend.Library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({DatabaseConnector.class, Library.class})
public class TestLibrary2 {

    @Mock
    private DatabaseConnector databaseConnector;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Test
    public void testDeleteBook_Success() throws SQLException {
    	// Mock the static method getConnection() of DatabaseConnector class
        PowerMockito.mockStatic(DatabaseConnector.class);
        Connection connection = Mockito.mock(Connection.class);
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        PowerMockito.when(DatabaseConnector.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);

        // Call the method under test
        boolean result = Library.deleteBook(123);

        // Assert the result
        assertTrue("Expected deleteBook method to return true", result);

        
    }

    @Test
    public void testDeleteBook_Failure() throws SQLException {
        //Mockito.when(databaseConnector.getConnection()).thenReturn(connection);
       // Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
       // Mockito.when(preparedStatement.executeUpdate()).thenReturn(0);

       Boolean result = Library.deleteBook(123);

        assertFalse(result);
    }
}
