/** Nadia Rahbany
 CST 338
 THIS CLASS IS THE TEST CLASS FOR SHELF AND WILL BE A PART OF THE LIBRARY PROJECT FOR CST 338
 SHELFTEST.JAVA **/
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


class ShelfTest {
    private int shelfNumber;
    private String subject;
    private HashMap<Book, Integer> books = new HashMap<>();

    @Test
    void Constructors() {
       Shelf theshelf =new Shelf();
       assertNotNull(theshelf);
    }

    @Test
    void getShelfNumber() {
        Shelf theshelf=new Shelf();
        assertEquals(shelfNumber,theshelf.getShelfNumber());

    }

    @Test
    void setShelfNumber() {
        Shelf theshelf=new Shelf();
        assertEquals(shelfNumber,theshelf.getShelfNumber());
        shelfNumber= 0;
    }

    @Test
    void getSubject() {


    }

    @Test
    void setSubject() {

    }

    @Test
    void getBooks() {
        Shelf theshelfA=new  Shelf();
        Shelf theshelfB=new  Shelf();
        assertEquals(books,theshelfA.getBooks() );
        assertEquals(books,theshelfB.getBooks() );
    }

    @Test
    void setBooks() {
        Shelf theshelfA=new  Shelf();
        Shelf theshelfB=new  Shelf();
        assertEquals(books,theshelfA.getBooks() );
        assertEquals(books,theshelfB.getBooks() );

        books = new HashMap<>();
    }

    @Test
    void testEquals() {
        Shelf theshelfA=new Shelf();
        Shelf theshelfB=new Shelf();
        theshelfA.setSubject("education");
        theshelfB.setSubject("education");
        assertTrue(theshelfA.equals(theshelfB));

    }


    @Test
    void addBook() {
        Shelf theShelfA=new Shelf();
        Book fifth = new Book("6756", "Healthy Habits","education",100,"Kylie Jenner", LocalDate.of(2021,1,2));

        theShelfA.addBook(fifth);

        Book sixth = new Book("7777", "Cooking with Nina","education",100,"Nina Dobrev", LocalDate.of(2021,1,4));



    }
    @Test
    void removeBook() {
        Shelf theShelfA= new Shelf();
        Book fifth = new Book("6756", "Healthy Habits","education",100,"Kylie Jenner", LocalDate.of(2021,1,2));
        theShelfA.removeBook(fifth);


    }
    @Test
    void getBookCount() {
        Shelf theShelfA=new Shelf();
        Book fifth = new Book("6756", "Healthy Habits","education",100,"Kylie Jenner", LocalDate.of(2021,1,2));
        Book sixth = new Book("7777", "Cooking with Nina","education",100,"Nina Dobrev", LocalDate.of(2021,1,4));

        Random random = new Random();

        int randomnum= random.nextInt();

        theShelfA.removeBook(sixth);
    }
    @Test
    void listBooks() {
        String theStringA;
        String theStringB;
        Shelf theshelfA=new Shelf();
        Shelf theshelfB=new Shelf();
        theshelfA.setSubject("education");
        theshelfB.setSubject("education");

        Book fifth = new Book("6756", "Healthy Habits","education",100,"Kylie Jenner", LocalDate.of(2021,1,2));
        theStringA=theshelfA.listBooks();
        theStringB=theshelfB.listBooks();
        assertEquals(theStringA,theStringB);
    }
}