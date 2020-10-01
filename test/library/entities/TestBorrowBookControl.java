package library.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import library.borrowbook.BorrowBookControl;
import library.borrowbook.BorrowBookUI;
import library.borrowbook.IBorrowBookUI;


class TestBorrowBookControl {

	@Mock ILibrary library;
	//@Mock IPatron patron;
		
	@InjectMocks BorrowBookControl borrowBookControl = new BorrowBookControl(library);
	@InjectMocks IBorrowBookUI borrowBookUI = new BorrowBookUI(borrowBookControl);
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCardSwipedWithNullPatron() {

		// arrange
		when(library.getPatronById(0)).thenReturn(null);

		// act
		Exception exception = assertThrows(RuntimeException.class, () -> {
			borrowBookControl.cardSwiped(0);
	    });

        //  assert
	    String expectedMessage = "Invalid patronId";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));
		
	}

}
