package TestPackage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import libraryBackend.BookStatus;
import libraryBackend.DatabaseConnector;
import libraryBackend.Library;

class LibraryTest {

  /*  @Test
    void testAddBook() throws SQLException {
        // Mocking the Connection and PreparedStatement
    	/*DatabaseConnector connection = mock(DatabaseConnector.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.statement(any(String.class))).thenReturn(statement);*/
        
       /* Library library = mock(Library.class);

        // Testing addBook method
        assertTrue(Library.addBook("Test Title", "Test Author", "Test Genre", "Test ISBN"));

        // Verifying that the appropriate methods are called
        verify(statement).setString(1, "Test Title");
        verify(statement).setString(2, "Test Author");
        verify(statement).setString(3, "Test Genre");
        verify(statement).setString(4, "Test ISBN");
        verify(statement).setString(5, BookStatus.AVAILABLE.name());
        verify(statement).executeUpdate();
    }
*/
    @Test
    void testDeleteBook() throws SQLException {
        // Mocking the Connection and PreparedStatement
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);

        // Testing deleteBook method
        assertTrue(Library.deleteBook(1));

        // Verifying that the appropriate methods are called
        verify(statement).setInt(1, 1);
        verify(statement).executeUpdate();
    }
}
    /*
    @Test
    void testSearchBooks() throws SQLException {
        // Mocking the Connection, PreparedStatement, and ResultSet
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("BookId")).thenReturn(1);
        when(resultSet.getString("Title")).thenReturn("Test Title");
        when(resultSet.getString("Author")).thenReturn("Test Author");
        when(resultSet.getString("Genre")).thenReturn("Test Genre");
        when(resultSet.getString("ISBN")).thenReturn("Test ISBN");
        when(resultSet.getString("Status")).thenReturn(BookStatus.AVAILABLE.name());

        // Testing searchBooks method
        List<Book> books = Library.searchBooks("Test");
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        Book book = books.get(0);
        assertEquals(1, book.getBookId());
        assertEquals("Test Title", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals("Test Genre", book.getGenre());
        assertEquals("Test ISBN", book.getIsbn());
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
    }

    @Test
    void testGetAllBooks() throws SQLException {
        // Mocking the Connection, PreparedStatement, and ResultSet
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("bookId")).thenReturn(1);
        when(resultSet.getString("Title")).thenReturn("Test Title");
        when(resultSet.getString("Author")).thenReturn("Test Author");
        when(resultSet.getString("Genre")).thenReturn("Test Genre");
        when(resultSet.getString("ISBN")).thenReturn("Test ISBN");
        when(resultSet.getString("Status")).thenReturn(BookStatus.AVAILABLE.name());

        // Testing getAllBooks method
        List<Book> books = Library.getAllBooks();
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        Book book = books.get(0);
        assertEquals(1, book.getBookId());
        assertEquals("Test Title", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals("Test Genre", book.getGenre());
        assertEquals("Test ISBN", book.getIsbn());
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
    }
}
*/
