package TestPackage;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import libraryBackend.Library;
import libraryFrontend.BookManagementPanel;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BookManagementPanel.class, JOptionPane.class})
@PowerMockIgnore("jdk.internal.reflect.*")

public class BookManagementPanelNewTest {

    private BookManagementPanel bookManagementPanel;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField isbnField;

    @Before
    public void setUp() {
        // Initialize the text fields
        titleField = new JTextField();
        authorField = new JTextField();
        genreField = new JTextField();
        isbnField = new JTextField();

        // Initialize the BookManagementPanel
        bookManagementPanel = new BookManagementPanel();
        bookManagementPanel.add(titleField);
        bookManagementPanel.add(authorField);
        bookManagementPanel.add(genreField);
        bookManagementPanel.add(isbnField);
    }

    @Test
    public void testAddBook_InvalidISBNFormat() {
        // Set up a non-matching ISBN format
        isbnField.setText("invalid_isbn");

        // Mock the showMessageDialog method of JOptionPane
        JOptionPane optionPaneMock = mock(JOptionPane.class);
        Mockito.doNothing().when(optionPaneMock).showMessageDialog(eq(bookManagementPanel), anyString(), anyString(), eq(JOptionPane.ERROR_MESSAGE));
        
  
        // Set the mocked JOptionPane
       // bookManagementPanel.setOptionPane(optionPaneMock);

        // Call the addBook method
        //bookManagementPanel.addBook();

        // Verify that JOptionPane.showMessageDialog was called
        verify(optionPaneMock).showMessageDialog(eq(bookManagementPanel), anyString(), anyString(), eq(JOptionPane.ERROR_MESSAGE));
    }

   /* @Test
    public void testAddBook_DuplicateISBN() {
        // Set up a unique ISBN
        isbnField.setText("unique_isbn");

        // Mock Library.isISBNUnique method to return false (indicating duplicate ISBN)
        Library libraryMock = mock(Library.class);
        when(libraryMock.isISBNUnique(anyString())).thenReturn(false);
        bookManagementPanel.setLibrary(libraryMock);

        // Mock the showMessageDialog method of JOptionPane
        JOptionPane optionPaneMock = mock(JOptionPane.class);
        doNothing().when(optionPaneMock).showMessageDialog(eq(bookManagementPanel), anyString(), anyString(), eq(JOptionPane.ERROR_MESSAGE));
        
        // Set the mocked JOptionPane
       // bookManagementPanel.setOptionPane(optionPaneMock);

        // Call the addBook method
        bookManagementPanel.addBook();

        // Verify that JOptionPane.showMessageDialog was called
        verify(optionPaneMock).showMessageDialog(eq(bookManagementPanel), anyString(), anyString(), eq(JOptionPane.ERROR_MESSAGE));
    }
*/
}

