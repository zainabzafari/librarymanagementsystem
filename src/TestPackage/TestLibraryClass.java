package TestPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import libraryBackend.DatabaseConnector;
import libraryBackend.Library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"org.mockito.*","javax.management.*", "javax.net.ssl.*", "javax.crypto.*"})
@PrepareForTest(Library.class) // Specify the class containing the static method to be mocked


class TestLibraryClass {

	 @Test
	    public void testDeleteBook_Success() throws SQLException {
	        // Mock DatabaseConnector class and its getConnection method

		 	//PreparedStatement statementSpy = Mockito.spy(PreparedStatement.class);
		 	
		 	
		 	PowerMockito.mockStatic(Library.class);
		    //Connection connectionMock = Mockito.mock(Connection.class);
		    //PowerMockito.when(DatabaseConnector.getConnection()).thenReturn(connectionMock);

	        

	        // Mock the prepareStatement method to return the spy PreparedStatement
	        //Mockito.when(connectionMock.prepareStatement(Mockito.anyString())).thenReturn(statementSpy);

	        // Stub the executeUpdate method of PreparedStatement
	        //Mockito.when(statementSpy.executeUpdate()).thenReturn(1);

	        // Call the addBook method
		 	Mockito.when(Library.addBook(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(true);

	        PowerMockito.verifyStatic(Library.class, Mockito.times(2));
	        
	        //boolean result = Library.addBook("Test Title", "Test Author", "Test Genre", "123-4567890-12X");

	        // Verify that the method returns true (indicating success)
	       // Assertions.assertTrue(Library.addBook(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()));
	    }

}
