package TestPackage;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import javax.swing.JOptionPane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import static org.mockito.ArgumentMatchers.anyString;
import java.lang.reflect.Field;


import libraryBackend.Library;
import libraryFrontend.BookManagementPanel;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BookManagementPanel.class, JOptionPane.class, Library.class })
@PowerMockIgnore("jdk.internal.reflect.*")

public class BookManagementPanelTest {
	
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

    
    @Test
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
        isbnField.set(bookManagementPanel, "123-4567890-12X");


        // Mock Library method behavior
        when(libraryMock.isISBNUnique(anyString())).thenReturn(true);
        when(Library.addBook(anyString(), anyString(), anyString(), anyString())).thenReturn(true);

        // Call addBook method
       // bookManagementPanel.addBook();

        // Verify if refreshBookList was called
        verify(bookManagementPanel, times(1)).refreshBookList();
        verify(libraryMock).addBook("Test Title", "Test Author", "Test Genre", "123-4567890-12X");
        

        // Verify if success message was displayed
        verifyStatic(JOptionPane.class, times(1));
        JOptionPane.showMessageDialog(eq(bookManagementPanel), eq("Book added successfully"));

        // Verify if no error message was displayed
        verifyStatic(JOptionPane.class, never());
        JOptionPane.showMessageDialog(eq(bookManagementPanel), anyString(), eq("Error"), anyInt());
        
    }
/*
    @Test
    public void testAddBook_InvalidISBN_FormatError() {
        // Mock invalid ISBN
        bookManagementPanel.setIsbnField("Invalid ISBN");

        // Call addBook method
        bookManagementPanel.addBook();

        // Verify if error message was displayed for invalid format
        verifyStatic(JOptionPane.class, times(1));
        JOptionPane.showMessageDialog(eq(bookManagementPanel), eq("Invalid ISBN Format. Please enter a valid ISBN."), eq("Error"), eq(JOptionPane.ERROR_MESSAGE));

        // Verify no other methods were called
        verifyZeroInteractions(libraryMock);
    }

    @Test
    public void testAddBook_DuplicateISBN_Error() {
        // Mock input fields
        bookManagementPanel.setTitleField("Test Title");
        bookManagementPanel.setAuthorField("Test Author");
        bookManagementPanel.setGenreField("Test Genre");
        bookManagementPanel.setIsbnField("123-4567890-12X");

        // Mock Library method behavior
        when(libraryMock.isISBNUnique(anyString())).thenReturn(false);

        // Call addBook method
        bookManagementPanel.addBook();

        // Verify if error message was displayed for duplicate ISBN
        verifyStatic(JOptionPane.class, times(1));
        JOptionPane.showMessageDialog(eq(bookManagementPanel), eq("Duplicate ISBN found!, Please enter a new Unique ISBN"), eq("Error"), eq(JOptionPane.ERROR_MESSAGE));

        // Verify no other methods were called
        verifyZeroInteractions(libraryMock);
    }
    
    */
   
}
