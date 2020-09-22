package library.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatronTest {
    IPatron patron;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        IPatron patron = new Patron("a","b","ab@localhost",12345678,1);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testPatronCanTakeOutLoan() {
        // arrange
             
        // act
        boolean result = patron.hasOverDueLoans();
        // assert
        assertFalse(result);
    }

}
