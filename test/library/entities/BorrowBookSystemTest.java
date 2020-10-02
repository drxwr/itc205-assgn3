package library.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import library.entities.helpers.BookHelper;
import library.entities.helpers.LoanHelper;
import library.entities.helpers.PatronHelper;



class BorrowBookSystemTest {
	
	String author = "author";
	String title = "title";
	String callNo = "callNo";
	int bookId = 1;
	
	String lastName = "lastName";
	String firstName = "firstName";
	String email = "email";
	long phoneNo = 123456789;
	int patronId = 1;
	
	
	Library library;
	Patron patron;
	ILoan loan;
	Book book;
	
	@Mock
	ILoan mockLoan;

 	
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		library = new Library(new BookHelper(), new PatronHelper(), new LoanHelper());
		patron = new Patron(lastName, firstName, email, phoneNo, patronId);
		book = new Book(author, title, callNo, bookId);
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testBorrowBookNoRestrictionsBookSuccessfullyBorrowed() {
		
		// arrange
		assertTrue(book.isAvailable());
		assertTrue(patron.getNumberOfCurrentLoans() < ILibrary.LOAN_LIMIT);
		
		// act
		loan = library.issueLoan(book, patron);
		library.commitLoan(loan);
		
		// assert
		assertTrue(patron.getNumberOfCurrentLoans() == 1);
		assertFalse(book.isAvailable());
		
	}
	
	@Test
	void testBorrowBorrowBookWhenBookOnLoan() {
		
		// arrange
		assertTrue(book.isAvailable());
		assertTrue(patron.getNumberOfCurrentLoans() < ILibrary.LOAN_LIMIT);
		loan = library.issueLoan(book, patron);
		library.commitLoan(loan);
		assertTrue(patron.getNumberOfCurrentLoans() == 1);
		assertFalse(book.isAvailable());
		
		// act
		loan = library.issueLoan(book, patron);
	    Exception exception = assertThrows(RuntimeException.class, () -> {
	    	library.commitLoan(loan);
	    });

        //  assert
	    String expectedMessage = "Book: cannot borrow while book is in state: ON_LOAN";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));
		
	}
	
	
	@Test
	void testBorrowBorrowBookWhenBookIsDamaged() {
		
		// arrange
		assertTrue(book.isAvailable());
		assertTrue(patron.getNumberOfCurrentLoans() < ILibrary.LOAN_LIMIT);
		loan = library.issueLoan(book, patron);
		library.commitLoan(loan);
		assertTrue(patron.getNumberOfCurrentLoans() == 1);
		assertFalse(book.isAvailable());
		library.dischargeLoan(loan, true);
		
		//assertTrue(book.isDamaged());		// BOOK STATE IS DAMAGED, SHOULD RETURN TRUE

		// act
		loan = library.issueLoan(book, patron);
	    Exception exception = assertThrows(RuntimeException.class, () -> {
	    	library.commitLoan(loan);
	    });
		
		// assert 
	    String expectedMessage = "Book: cannot borrow while book is in state: IS_DAMAGED";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));

	}
	
	
	@Test
	void testBorrowBookWhenPatronIsRestricted() {
		
		// arrange
		assertTrue(library.patronCanBorrow(patron));
		assertTrue(patron.getNumberOfCurrentLoans() < ILibrary.LOAN_LIMIT);
				
		patron.restrictBorrowing();
		
		//assertFalse(library.patronCanBorrow(patron));  //  PATRON IS RESTRICTED, SHOULD RETURN FALSE
		
		// act
		loan = library.issueLoan(book, patron);
	    Exception exception = assertThrows(RuntimeException.class, () -> {
	    	library.commitLoan(loan);
	    });
		
		// assert 
	    String expectedMessage = "Patron cannot borrow in RESTRICTED state";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));	
		
	}
	
	@Test
	void testBorrowBookWhenPatronHasOverDueLoans() throws ParseException {
		
		// arrange
    	SimpleDateFormat dateformat2 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    	String strDueDate = "01-01-2020 01:00:00";
    	Date dueDate = dateformat2.parse(strDueDate);
		
		assertTrue(library.patronCanBorrow(patron));
		assertTrue(patron.getNumberOfCurrentLoans() < ILibrary.LOAN_LIMIT);

		when(mockLoan.checkOverDue(dueDate)).thenReturn(true);
		when(mockLoan.isOverDue()).thenReturn(true);
		
		mockLoan = library.issueLoan(book, patron);
		library.commitLoan(mockLoan);
		
		// assert
		assertFalse(patron.getNumberOfCurrentLoans() == 1);
		
	}	
	

}
