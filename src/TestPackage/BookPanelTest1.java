package TestPackage;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import libraryBackend.Library;
import libraryFrontend.BookManagementPanel;
//import org.powermock.modules.testng.PowerMockTestCase;



@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "javax.net.ssl.*", "javax.crypto.*"})
@PrepareForTest(Library.class) // Specify the class containing the static method to be mocked

//extends PowerMockTestCase
public class BookPanelTest1  {

	    @Test
	    public void testAddBook_ValidISBN() throws Exception {
	    	
	    	PowerMockito.mockStatic(Library.class);
	    	BookManagementPanel bookManagementPanel = new BookManagementPanel();
	    	
	    	
	    	
	    	JTextField mockTitleField = Mockito.mock(JTextField.class);
	    	
	    	String title = mockTitleField.getText();
	    	
	    	JTextField mockAuthorField = Mockito.mock(JTextField.class);
	    	String author = mockAuthorField.getText();
	    	
	    	JTextField mockGenreField = Mockito.mock(JTextField.class);
	    	String genre = mockGenreField.getText();
	    	
	    	JTextField mockISBNField = Mockito.mock(JTextField.class);
	    	String ISBN = mockISBNField.getText();
	    	
	    	
	    	
	    	PowerMockito.mockStatic(Library.class);
	    	Mockito.when(Library.addBook(title, author, genre, ISBN)).thenReturn(true);
	    	PowerMockito.verifyStatic(Library.class, Mockito.times(1));
	    	
	    	 //classCallStaticMethodObj.execute();

	         // Different from Mockito, always use PowerMockito.verifyStatic(Class) first
	         // to start verifying behavior
	         
	         // IMPORTANT:  Call the static method you want to verify
	         //Library.addBook(title, author, genre, ISBN);
	        
	        /*Whitebox.invokeMethod(bookManagementPanel, "addBook");
	        
	       
	    	
	     // Mock the behavior of Library.isISBNUnique()
	        PowerMockito.mockStatic(Library.class);
	        Mockito.when(Library.isISBNUnique("123-4567890-12X")).thenReturn(true);

	        Whitebox.invokeMethod(bookManagementPanel, "addBook");
	        // Verify that the static method Library.isISBNUnique was called with the correct parameter
	        PowerMockito.verifyStatic(Library.class);
	        Library.addBook("Test Title", "Test Author", "Test Genre", "123-4567890-12X");

	        // Verify that the static method JOptionPane.showMessageDialog was called with the correct parameters
	        PowerMockito.verifyStatic(JOptionPane.class);
	        JOptionPane.showMessageDialog(null, "Book added successfully");	
	    	
	    	
	    	
	    /*	
	    	
	        // Set up the necessary private fields if needed
	        JTextField titleField = new JTextField("Test Title");
	        JTextField authorField = new JTextField("Test Author");
	        JTextField genreField = new JTextField("Test Genre");
	        JTextField isbnField = new JTextField("123-4567890-12X");
	        Whitebox.setInternalState(bookManagementPanel, "titleField", titleField);
	        Whitebox.setInternalState(bookManagementPanel, "authorField", authorField);
	        Whitebox.setInternalState(bookManagementPanel, "genreField", genreField);
	        Whitebox.setInternalState(bookManagementPanel, "isbnField", isbnField);

	        // Mock the behavior of Library.isISBNUnique()
	        Whitebox.setInternalState(bookManagementPanel, "isbnField", isbnField);

	        // Mock the behavior of Library.isISBNUnique()
	        PowerMockito.mockStatic(Library.class);
	       // Mockito.when(Library.class, "isISBNUnique", "123-4567890-12X").thenReturn(true);

	        Whitebox.invokeMethod(bookManagementPanel, "addBook");
	        // Verify that the static method Library.isISBNUnique was called with the correct parameter
	        PowerMockito.verifyStatic(Library.class);
	        Library.addBook("Test Title", "Test Author", "Test Genre", "123-4567890-12X");

	        // Verify that the static method JOptionPane.showMessageDialog was called with the correct parameters
	        PowerMockito.verifyStatic(JOptionPane.class);
	        JOptionPane.showMessageDialog(null, "Book added successfully");	    }
		
		*/
		
		

		
		
		
		
		
		
        // Create an instance of BookManagementPanel
		/*PowerMockito.mockStatic(Library.class);
		PowerMockito.mockStatic(JOptionPane.class);
        BookManagementPanel bookManagementPanel = new BookManagementPanel();

       

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
        
        PowerMockito.when(Library.isISBNUnique("123-4567890-12X")).thenReturn(true);
        

        // Call the public method that invokes the private method addBook
        // For example, if there's a public method like addBookWrapper() that calls addBook internally
        Whitebox.invokeMethod(bookManagementPanel, "addBook");

        // Verify that the private method was invoked
        PowerMockito.verifyStatic(Library.class);
        Library.addBook("Test Title", "Test Author", "Test Genre", "123-4567890-12X");

        // Verify that the success message dialog was displayed
        //PowerMockito.verifyStatic(JOptionPane.class);
        //JOptionPane.showMessageDialog(null, "Book added successfully");
         * 
         * 
         */
    }

}