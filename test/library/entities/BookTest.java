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

import library.entities.helpers.*;

class BookTest {
	
	@Mock IBook book;
	@Mock BookHelper bookHelper;
	
	String author = "author";
	String title = "title";
	String callNo = "callNo";
	int id = 1;
	
	 
	
	
    @BeforeEach
    void setUp() throws Exception {
    	
    	MockitoAnnotations.initMocks(this);
    	book = new Book(author, title, callNo, id);

    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testBookIsAvailable() {
    	
    	// assert
    	assertTrue(book.isAvailable());
    }
    

    @Test
    void testBorrowFromLibraryAndBookIsNotAvailableWhileOnLoan() {

    	// arrange
    	assertTrue(book.isAvailable());
    	// act
    	book.borrowFromLibrary();
    	// assert
    	assertFalse(book.isAvailable());
    }

    
    @Test
    void testBorrowFromLibraryAndBookIsAvailableAfterLoan() {

    	// arrange
    	book.borrowFromLibrary();
    	assertFalse(book.isAvailable());
    	// act
    	book.returnToLibrary(false);
    	// assert
    	assertTrue(book.isAvailable());
    	
    }
    
    @Test
    void testBorrowFromLibraryAndBookIsNotAvailableWhenDamaged() {

    	// arrange
    	book.borrowFromLibrary();
    	assertFalse(book.isAvailable());
    	// act
    	book.returnToLibrary(true);
    	// assert
    	assertFalse(book.isAvailable());
    }

}
