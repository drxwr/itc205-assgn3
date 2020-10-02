package library.entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoanIntegrationTest {

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
    void testLoanCommit() {
        
        //  arrange
    	Date date = new Date();
    	
    	// act
        loan.commit(1, date);
        
        // assert
        assertTrue(patron.getNumberOfCurrentLoans() == 1);
        
    }
   
    
    @Test
    void testCheckOverdueLoanWhenOverdue() throws ParseException {
        
    	// arrange
    	boolean expected = true;
    	
    	SimpleDateFormat dateformat2 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    	String strDueDate = "01-01-2020 01:00:00";
    	Date dueDate = dateformat2.parse(strDueDate);
    	 
    	loan.commit(1, dueDate);
    	 
    	// act
    	 
    	boolean result = loan.checkOverDue(new Date());
    	
    	// assert
    	assertEquals(expected, result);
    	 
    }
    
    
    @Test    
    void testCheckOverdueLoanWhenNotOverdue() throws ParseException {
        
    	// arrange
    	boolean expected = false;
    	
    	SimpleDateFormat dateformat2 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    	String strDueDate = "25-12-2020 01:00:00";
    	Date dueDate = dateformat2.parse(strDueDate);
    	 
    	loan.commit(1, dueDate);
    	 
    	// act
    	 
    	boolean result = loan.checkOverDue(new Date());
    	
    	// assert
    	assertEquals(expected, result);
    	 
    }
    
    
    

}
