package library.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import library.entities.IPatron.PatronState;
import library.entities.helpers.*;

class PatronUnitTest {

	String lastName = "lastName";
	String firstName = "firstName";
	String email = "email";
	long phoneNo = 123456789;
	int id = 1;
	
	@Mock IPatron patron;
	@Mock ILoan loan;
	
	PatronHelper patronHelper;

    @BeforeEach
    void setUp() throws Exception {
    	
    	MockitoAnnotations.initMocks(this);
        patron = new Patron(lastName, firstName, email, phoneNo, id);
    	//patron = patronHelper.makePatron("a","b","ab@localhost",12345678,1);
    }

    @Test
    void testHasNoOverDueLoans() {
    	
        // arrange
        when(loan.isOverDue()).thenReturn(false);
        boolean expected = false;
        // act
        boolean result = patron.hasOverDueLoans();
        // assert
        assertEquals(expected, result);
    }
    
    @Test
    void testHasOverdueLoans() {
    	
        // arrange
        when(loan.isOverDue()).thenReturn(true);
        boolean expected = true;
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
