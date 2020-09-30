package library.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


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

import library.entities.helpers.*;

@ExtendWith(MockitoExtension.class)
class LoanTest {

    @Mock IBook book;
    @Mock IPatron patron;
    
    ILoanHelper loanHelper;
    
    @Spy
    @InjectMocks ILoan loan = new Loan(book, patron);
    

    @BeforeEach
    void setUp() throws Exception {
    	
    	MockitoAnnotations.initMocks(this);
    	//book = new Book("author","title","callNo",1);
    	//patron = new Patron("lastName","firstName","email",12345678,1);
    }



    @Test
    void testLoanCommit() {
        
        //  arrange
    	Date date = new Date();
    	
    	// act
        loan.commit(1, date);
        
        //  verify
        verify(loan, times(1)).commit(1, date);
        verify(patron, times(1)).takeOutLoan(loan);
        
    }
    
    @Test
    void testLoanCommitWhenLoanNotInPendingState() {
        
        //  arrange
    	Date date = new Date();
    	
    	// act
        loan.commit(1, date);

	    Exception exception = assertThrows(RuntimeException.class, () -> {
	    	loan.commit(1, date);
	    });

        //  assert
	    String expectedMessage = "Cannot commit a non PENDING loan";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));
        
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
