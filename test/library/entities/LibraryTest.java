package library.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import library.entities.helpers.BookHelper;
import library.entities.helpers.LoanHelper;
import library.entities.helpers.PatronHelper;

@ExtendWith(MockitoExtension.class)
class LibraryTest {
    
    @Mock IPatron patron;
    @Mock ILoan loan;
    ILibrary library;
    

    @BeforeEach
    void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        library = new Library(new BookHelper(), new PatronHelper(), new LoanHelper());
        patron.takeOutLoan(loan);
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
        when(patron.getNumberOfCurrentLoans()).thenReturn(ILibrary.LOAN_LIMIT);
        
        // action
        boolean actual = library.patronCanBorrow(patron);

        // assert
        assertEquals(actual, expected);
    }
    
    @Test
    void testPatronCanBorrowWhenOverLoanLimit() {

        // arrange        
        boolean expected = false;
        when(patron.getNumberOfCurrentLoans()).thenReturn(ILibrary.LOAN_LIMIT + 1);
        
        // action
        boolean actual = library.patronCanBorrow(patron);

        // assert
        assertEquals(actual, expected);
    }
    
    @Test
    void testPatronCanBorrowWhenatMaxFinesOwed() {

        // arrange        
        boolean expected = false;
        when(patron.getFinesPayable()).thenReturn(ILibrary.MAX_FINES_OWED);
        
        // action
        boolean actual = library.patronCanBorrow(patron);

        // assert
        assertEquals(actual, expected);
    }
    
    /*
    @Test
    void testPatronCanBorrowWhenOverdueLoans() {

        // arrange        
        boolean expected = false;
        //when(patron.hasOverDueLoans()).thenReturn(true);
        
        // action
        boolean actual = library.patronCanBorrow(patron);

        // assert
        assertEquals(actual, expected);
    }
    */
    
}
