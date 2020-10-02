package library.entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import library.entities.helpers.BookHelper;
import library.entities.helpers.IBookHelper;
import library.entities.helpers.ILoanHelper;
import library.entities.helpers.LoanHelper;
import library.entities.helpers.PatronHelper;

class LibraryIntegrationTest {
	

	String author = "author";
	String title = "title";
	String callNo = "callNo";
	int bookId = 1;
	
	String lastName = "lastName";
	String firstName = "firstName";
	String email = "email";
	long phoneNo = 123456789;
	int patronId = 1;
	
	
	ILibrary library;
	IPatron patron;
	ILoan loan;
	IBook book;
	
	BookHelper bookHelper;
	LoanHelper loanHelper;
	

	@BeforeEach
	void setUp() throws Exception {
		
		library = new Library(new BookHelper(), new PatronHelper(), new LoanHelper());
		patron = new Patron(lastName, firstName, email, phoneNo, patronId);
		book = new Book(author, title, callNo, bookId);
		loan = new Loan(book,patron);
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
    void testPatronCanBorrow() {
        // arrange        
        boolean expected = true;        
        // action
        boolean actual = library.patronCanBorrow(patron);
        // assert
        assertEquals(actual, expected);
    }
	
	
    @Test
    void testPatronCanBorrowWhenAtLoanLimit() {

        // arrange
    	boolean expected = false;
    	
    	IBook book1 = new Book(author, title, callNo, bookId);
    	IBook book2 = new Book(author, title, callNo, bookId + 1);
        
    	ILoan loan1 = new Loan(book1, patron);
    	ILoan loan2 = new Loan(book2, patron);
        
    	library.issueLoan(book1, patron);
    	library.commitLoan(loan1);
    	library.issueLoan(book2, patron);
    	library.commitLoan(loan2);
    	
    	assertTrue(patron.getNumberOfCurrentLoans() == 2);
    	
        // action
        boolean actual = library.patronCanBorrow(patron);

        // assert
        assertEquals(actual, expected);
    }
    
    @Test
    void testPatronCanBorrowWhenatMaxFinesOwed() {

        // arrange        
        boolean expected = false;
        patron.incurFine(5.0);
                
        // action
        boolean actual = library.patronCanBorrow(patron);

        // assert
        assertEquals(actual, expected);
    }
    
    @Test
    void testPatronCanBorrowWhenOverdueLoans() throws ParseException {

        // arrange        
        boolean expected = false;
    	
    	SimpleDateFormat dateformat2 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    	String strDueDate = "01-01-2020 01:00:00";
    	Date dueDate = dateformat2.parse(strDueDate);
    	 
    	loan.commit(1, dueDate);
               
        // action
        boolean actual = library.patronCanBorrow(patron);

        // assert
        assertEquals(actual, expected);
    }
    
    
    
    @Test
    void testIssueLoan() {
        
        // action
        ILoan loan = library.issueLoan(book, patron);
        
        // assert
        assertEquals(book,loan.getBook());
        assertEquals(patron,loan.getPatron());
        
    }
    
    @Test 
    void testCommitLoan() {
        
        // arrange
        loan = new Loan(book, patron);
        
        // action
        library.commitLoan(loan);
        
        // verify
        assertTrue(library.getCurrentLoansList().size() == 1);
        
    }

}

