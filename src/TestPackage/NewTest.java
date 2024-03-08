package TestPackage;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import java.lang.reflect.Field;

import javax.swing.JOptionPane;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import libraryBackend.Library;
import libraryFrontend.BookManagementPanel;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BookManagementPanel.class, JOptionPane.class, Library.class })
@PowerMockIgnore("jdk.internal.reflect.*")

public class NewTest {
	
	  private Library libraryMock;
	    private BookManagementPanel bookManagementPanel;
	    
	    @Mock
	    private JOptionPane optionPaneMock;

	    @Before
	    public void setUp() throws Exception {
	        libraryMock = mock(Library.class);
	        bookManagementPanel = new BookManagementPanel();
	        bookManagementPanel.setLibrary(libraryMock);
	        
	        PowerMockito.mockStatic(JOptionPane.class);

	        PowerMockito.mockStatic(Library.class);
	        when(Library.isISBNUnique(anyString())).thenReturn(true);
	        
	    }
	    
	    public void testAddBook_ValidISBN_Success()throws Exception {
	    	
	    	Field titleField = BookManagementPanel.class.getDeclaredField("titleField");
	        titleField.setAccessible(true);
	        titleField.set(bookManagementPanel, "Test Title");
	        
	        Field authorField = BookManagementPanel.class.getDeclaredField("authorField");
	        authorField.setAccessible(true);
	        authorField.set(bookManagementPanel, "Test Author");

	        Field genreField = BookManagementPanel.class.getDeclaredField("genreField");
	        genreField.setAccessible(true);
	        genreField.set(bookManagementPanel, "Test Genre");

	        Field isbnField = BookManagementPanel.class.getDeclaredField("isbnField");
	        isbnField.setAccessible(true);
	        isbnField.set(bookManagementPanel, "123-4567890-123");


	        // Mock Library method behavior
	        when(libraryMock.isISBNUnique(anyString())).thenReturn(true);
	        when(Library.addBook(anyString(), anyString(), anyString(), anyString())).thenReturn(true);

	        // Call addBook method
	        bookManagementPanel.addBook();

	        // Verify if refreshBookList was called
	        verify(bookManagementPanel, times(1)).refreshBookList();
	        verify(libraryMock).addBook("Test Title", "Test Author", "Test Genre", "123-4567890-123");
	        

	        // Verify if success message was displayed
	        verifyStatic(JOptionPane.class, times(1));
	        JOptionPane.showMessageDialog(eq(bookManagementPanel), eq("Book added successfully"));

	        // Verify if no error message was displayed
	        verifyStatic(JOptionPane.class, never());
	        JOptionPane.showMessageDialog(eq(bookManagementPanel), anyString(), eq("Error"), anyInt());
	        
	    }

	   

}
