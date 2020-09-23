package library.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.mockito.Mockito.*;

import java.util.Date;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import library.entities.helpers.BookHelper;
import library.entities.helpers.IBookHelper;
import library.entities.helpers.ILoanHelper;
import library.entities.helpers.IPatronHelper;
import library.entities.helpers.LoanHelper;
import library.entities.helpers.PatronHelper;

@ExtendWith(MockitoExtension.class)
class LibraryTest {
    
    @Mock IPatron patron;
    @Mock IBook book;
    @Mock ILoan mockLoan;
    
    
    @Mock IBookHelper bookHelper;
    @Mock IPatronHelper patronHelper;
    @Mock ILoanHelper loanHelper;
    
    @Mock ILibrary mockLibrary = new Library(bookHelper, patronHelper, loanHelper); 
    
    ILibrary library;
    

    @BeforeEach
    void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        library = new Library(new BookHelper(), new PatronHelper(), new LoanHelper());

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
    
    
    @Test
    void testPatronCanBorrowWhenOverdueLoans() {

        // arrange        
        boolean expected = false;
        when(patron.hasOverDueLoans()).thenReturn(true);
        
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
        mockLoan = new Loan(book, patron);
        
        // action
        mockLibrary.commitLoan(mockLoan);
        
        // verify
        verify(mockLibrary, times(1)).commitLoan(mockLoan);
        
    }
    
    
}
