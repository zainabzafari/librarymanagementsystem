package libraryBackend;

public class Book {
	private int bookId;
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private BookStatus status;


    public Book( String title, String author, String genre, String isbn, BookStatus status) {
        
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.status = status;
    }

    public Book(int bookId, String title, String author, String genre, String isbn, BookStatus status) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.status = status;
    }
    

    public Book(int bookId, String title, String author, String genre, String isbn) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
    }
 


    public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}




	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
    
}
