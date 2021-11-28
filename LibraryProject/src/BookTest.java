import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    Book book;
    private String isbn;
    private String title;
    private String subject;
    private int pageCount;
    private String author;
    private LocalDate dueDate;

    @BeforeEach
    void setUp() {
        book = null;
        assertNull(book);
        book =new Book(isbn,subject,title,pageCount,author,dueDate);
        assertNotNull(book);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getIsbn() {
        assertEquals(isbn,book.getIsbn() );

    }

    @Test
    void setIsbn() {
        isbn= ("");


    }

    @Test
    void getTitle() {
        assertEquals(title,book.getTitle() );

    }

    @Test
    void setTitle() {
        title=("");
    }

    @Test
    void getSubject() {

    }

    @Test
    void setSubject() {
    }

    @Test
    void getPageCount() {
        assertEquals(pageCount,book.getPageCount() );

    }

    @Test
    void setPageCount() {

        pageCount= 0;
    }

    @Test
    void getAuthor() {
        assertEquals(author,book.getAuthor() );

    }

    @Test
    void setAuthor() {
        author=("");
    }

    @Test
    void getDueDate() {
        assertEquals(dueDate,book.getDueDate() );

    }

    @Test
    void setDueDate() {
    }

}