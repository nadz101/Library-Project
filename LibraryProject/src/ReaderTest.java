/** Nadia Rahbany
 * ReaderTest.Java Assignment Project (2/4)
 * CST 338
 */

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    @Test
    void addBook() {
        Reader one =new Reader(23, "Maple","98765");
        Book first= new Book("1234", "To Kill a Mocking Bird","fiction",200,"Harper Lee", LocalDate.of(12,2,2021));
        assertEquals(one.addBook(first),Code.SUCCESS);
        assertNotEquals(one.addBook(first),Code.SUCCESS);
        assertEquals(one.addBook(first),Code.BOOK_ALREADY_CHECKED_OUT_ERROR);

    }

    @Test
    void getBookCount() {
        Reader two= new Reader(24, "Caroline","123456");
        assertEquals(two.getBookCount(),0);
        Book first= new Book("1234", "To Kill a Mocking Bird","fiction",200,"Harper Lee", LocalDate.of(12,2,2021));
       two.addBook(first);
       two.removeBook(first);
       assertEquals(two.getBookCount(),0);
    }

    @Test
    void hasBook() {
        Reader two= new Reader(24, "Caroline","123456");
        Book second= new Book("2468", "Odessy","non-fiction",300,"Homer", LocalDate.of(03,2,2021));
        assertFalse(two.hasBook(second));
        two.addBook(second);
        assertTrue(two.hasBook(second));
    }
    @Test
    void removeBook() {
        Reader two= new Reader(24, "Caroline","123456");
        Book second= new Book("2468", "Odessy","non-fiction",300,"Homer", LocalDate.of(03,2,2021));
        assertEquals(two.removeBook(second), Code.READER_DOESNT_HAVE_BOOK_ERROR);
        two.addBook(second);
        assertEquals(two.removeBook(second),Code.SUCCESS);



    }




}