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

class PatronTest {

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
    void testPatronCanTakeOutLoan() {
    	
        // arrange
        when(loan.isOverDue()).thenReturn(false);
        boolean expected = true;
        // act
        boolean result = patron.hasOverDueLoans();
        // assert
        assertEquals(expected, result);
    }
    
    @Test
    void testPatronCannotTakeOutLoanWithOverdueItem() {
    	
        // arrange
        when(loan.isOverDue()).thenReturn(true);
        boolean expected = false;
        // act
        boolean result = patron.hasOverDueLoans();
        // assert
        assertEquals(expected, result);
    }
    
    @Test
    void testTakeoutLoan() {
    	
        // arrange
    	
        // act
    	patron.takeOutLoan(loan);
        // assert
    	
    }

}
