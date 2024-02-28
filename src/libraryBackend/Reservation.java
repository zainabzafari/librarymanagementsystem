package libraryBackend;

import java.time.LocalDate;

public class Reservation {
	    private int reservationId;
	    private int memberId;
	    private int bookId;
	    private LocalDate reservationStartDate;
	    private LocalDate reservationEndDate;
	    private ReservationStatus reservationStatus;
	    private String title;
	    private String author;

	    public Reservation(int reservationId, int memberId, int bookId, String title, String author, LocalDate reservationStartDate, LocalDate reservationEndDate, ReservationStatus reservationStatus) {
	        this.reservationId = reservationId;
	        this.memberId = memberId;
	        this.bookId = bookId;
	        this.reservationStartDate = reservationStartDate;
	        this.reservationEndDate = reservationEndDate;
	        this.reservationStatus = reservationStatus;
	        this.title = title;
	        this.author=author;
	    }
	    
	    public Reservation(int reservationId, int bookId, String title, String author, LocalDate reservationStartDate, LocalDate reservationEndDate, ReservationStatus reservationStatus) {
	    	this.reservationId = reservationId;
	        this.bookId = bookId;
	        this.reservationStartDate = reservationStartDate;
	        this.reservationEndDate = reservationEndDate;
	        this.reservationStatus = reservationStatus;
	        this.title = title;
	        this.author=author;
	    }

	    // Getters and setters
	    
	    
	    public int getReservationId() {
	        return reservationId;
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

		public void setReservationId(int reservationId) {
	        this.reservationId = reservationId;
	    }

	    public int getMemberId() {
	        return memberId;
	    }

	    public void setMemberId(int memberId) {
	        this.memberId = memberId;
	    }

	    public int getBookId() {
	        return bookId;
	    }

	    public void setBookId(int bookId) {
	        this.bookId = bookId;
	    }

	    public LocalDate getReservationStartDate() {
	        return reservationStartDate;
	    }

	    public void setReservationStartDate(LocalDate reservationStartDate) {
	        this.reservationStartDate = reservationStartDate;
	    }

	    public LocalDate getReservationEndDate() {
	        return reservationEndDate;
	    }

	    public void setReservationEndDate(LocalDate reservationEndDate) {
	        this.reservationEndDate = reservationEndDate;
	    }

	    public ReservationStatus getReservationStatus() {
	        return reservationStatus;
	    }

	    public void setReservationStatus(ReservationStatus reservationStatus) {
	        this.reservationStatus = reservationStatus;
	    }
	}



