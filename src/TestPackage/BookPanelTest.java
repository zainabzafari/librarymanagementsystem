package TestPackage;

import org.junit.jupiter.api.Test;

import libraryBackend.Library;
import libraryFrontend.BookManagementPanel;


import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import javax.swing.*;

import javax.swing.JTextField;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Library.class) // Specify the class containing the static method to be mocked


public class BookPanelTest {

	@Test
    public void testAddBook_ValidISBN() throws Exception {
        // Create an instance of BookManagementPanel
        BookManagementPanel bookManagementPanel = new BookManagementPanel();

        // Mock the JOptionPane.showMessageDialog method
        PowerMockito.mockStatic(JOptionPane.class);

        // Set up the necessary private fields if needed
        JTextField titleField = new JTextField("Test Title");
        JTextField authorField = new JTextField("Test Author");
        JTextField genreField = new JTextField("Test Genre");
        JTextField isbnField = new JTextField("123-4567890-12X");
        Whitebox.setInternalState(bookManagementPanel, "titleField", titleField);
        Whitebox.setInternalState(bookManagementPanel, "authorField", authorField);
        Whitebox.setInternalState(bookManagementPanel, "genreField", genreField);
        Whitebox.setInternalState(bookManagementPanel, "isbnField", isbnField);

        // Mock the Library class
        PowerMockito.mockStatic(Library.class);
        PowerMockito.when(Library.isISBNUnique("123-4567890-12X")).thenReturn(true);

        // Call the public method that invokes the private method addBook
        // For example, if there's a public method like addBookWrapper() that calls addBook internally
        Whitebox.invokeMethod(bookManagementPanel, "addBook");
        
        // Verify that the private method was invoked
        PowerMockito.verifyStatic(Library.class);
        Library.addBook("Test Title", "Test Author", "Test Genre", "123-4567890-12X");
        
        // Verify that the success message dialog was displayed
        PowerMockito.verifyStatic(JOptionPane.class);
        JOptionPane.showMessageDialog(null, "Book added successfully");
    }

}
