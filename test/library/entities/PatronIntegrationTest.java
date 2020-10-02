package library.entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatronIntegrationTest {
	
	String author = "author";
	String title = "title";
	String callNo = "callNo";
	int bookId = 1;
	
	String lastName = "lastName";
	String firstName = "firstName";
	String email = "email";
	long phoneNo = 123456789;
	int patronId = 1;
	
	
	IPatron patron;
	ILoan loan;
	IBook book;
	

	@BeforeEach
	void setUp() throws Exception {
		
		patron = new Patron(lastName, firstName, email, phoneNo, patronId);
		book = new Book(author, title, callNo, bookId);
		loan = new Loan(book, patron);
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}


    @Test
    void testHasNoOverDueLoans() {
    	
        // arrange
        boolean expected = false;
        // act
        boolean result = patron.hasOverDueLoans();
        // assert
        assertEquals(expected, result);
    }
    
    @Test
    void testHasOverdueLoans() throws ParseException {
    	
        // arrange
    	boolean expected = true;
    	
    	SimpleDateFormat dateformat2 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    	String strDueDate = "01-01-2020 01:00:00";
    	Date dueDate = dateformat2.parse(strDueDate);
    	 
    	loan.commit(1, dueDate);
    	
        // act
        boolean result = patron.hasOverDueLoans();
        
        // assert
        assertEquals(expected, result);
    }
    
    
    @Test
    void testCannotTakeoutLoanWhenRestricted() {
    	
        // arrange
    	patron.restrictBorrowing();
        
    	// act
    	
	    Exception exception = assertThrows(RuntimeException.class, () -> {
	    	patron.takeOutLoan(loan);
	    });

        //  assert
	    
	    String expectedMessage = "Patron cannot borrow in RESTRICTED state";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));

    }
    
    
    @Test
    void testTakeoutDuplicateLoan() {
    	
        // arrange
    	patron.takeOutLoan(loan);
        
    	// act
    	
	    Exception exception = assertThrows(RuntimeException.class, () -> {
	    	patron.takeOutLoan(loan);
	    });

        //  assert
	    
	    String expectedMessage = "Attempted to add duplicate loan to patron";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));
   	
    	
    }
    
    
    
    
}
