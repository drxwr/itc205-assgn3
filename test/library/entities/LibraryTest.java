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


import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import library.entities.helpers.BookHelper;
import library.entities.helpers.LoanHelper;
import library.entities.helpers.PatronHelper;

@ExtendWith(MockitoExtension.class)
class LibraryTest {
    
    @Mock Patron patron;
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
        boolean result = library.patronCanBorrow(patron);
        // assert
        assertEquals(result, expected);
    }

    @Test
    void testPatronBorrowRestricted() {
        // arrange        
        boolean expected = false;
        //when(patron.getNumberOfCurrentLoans()).thenReturn(14);
        when(library.patronCanBorrow(patron)).thenReturn(false);
        // action
        boolean actual = library.patronCanBorrow(patron);
        // assert
        System.out.println(actual);
        //assertEquals(result, expected);
    }
    
}
