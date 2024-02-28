package libraryBackend;

import java.time.LocalDate;

public class BorrowedBook {
	 private int issueId;
	    private int memberId;
	    private int bookId;
	    private LocalDate StartDate;
	    private LocalDate returnDate;
	    private BorrowedBookStatus Status;
	    
	    
		public BorrowedBook(int issueId, int memberId, int bookId, LocalDate startDate, LocalDate returnDate,
				BorrowedBookStatus status) {
			
			this.issueId = issueId;
			this.memberId = memberId;
			this.bookId = bookId;
			this.StartDate = startDate;
			this.returnDate = returnDate;
			this.Status = status;
		}

		public BorrowedBook(int issueId, int bookId, LocalDate startDate, LocalDate returnDate,
				BorrowedBookStatus status) {
			
			this.issueId = issueId;
			this.bookId = bookId;
			this.StartDate = startDate;
			this.returnDate = returnDate;
			this.Status = status;
		}

		public int getIssueId() {
			return issueId;
		}


		public void setIssueId(int issueId) {
			this.issueId = issueId;
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


		public LocalDate getStartDate() {
			return StartDate;
		}


		public void setStartDate(LocalDate startDate) {
			StartDate = startDate;
		}


		public LocalDate getReturnDate() {
			return returnDate;
		}


		public void setReturnDate(LocalDate returnDate) {
			this.returnDate = returnDate;
		}


		public BorrowedBookStatus getStatus() {
			return Status;
		}


		public void setStatus(BorrowedBookStatus status) {
			Status = status;
		}
		
		
	    
	    


}
