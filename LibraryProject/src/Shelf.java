/** Nadia Rahbany
CST 338
 THIS CLASS WILL BE ASHELF FOR A PART OF THE LIBRARY PROJECT FOR CST 338
SHELF.JAVA **/

import java.util.HashMap;
import java.util.Objects;


//CLASS
public class Shelf {

    //DECLARING THE VARIABLES FOR THE SHELF CLASS
    public static final int SHELF_NUMBER_ = 0;
    public static final int SUBJECT_ = 1;

    private int shelfNumber;
    private String subject;
    private HashMap<Book, Integer> books = new HashMap<>();

    public Shelf() {
        shelfNumber = 0;
        subject = ("");
        books = new HashMap<>();
    }

    //getters & setters for ShelfNumber, subject, HashMap<Books>,
    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return getShelfNumber() == shelf.getShelfNumber() && Objects.equals(getSubject(), shelf.getSubject()) && Objects.equals(getBooks(), shelf.getBooks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShelfNumber(), getSubject(), getBooks());
    }

    @Override
    //toString to print shelf: subject
    public String toString() {
        return shelfNumber + ": " + subject;
    }
//getter will return the book count to shelf.
    public int getBookCount(Book book) {
        if (books.containsKey(book)) {
            return (books.get(book));
        }
        //reutrns -1 if not put on shelf
        return -1;

    }
//
    public Code addBook(Book book) {
        if (books.containsKey(book )) {
            //increases
            books.put(book,books.get(book));
            System.out.println(book.toString()+" added to shelf "+ this.toString());
            return Code.SUCCESS;

        }
        //else statement , if book is not on shelf and if added to shelf
        else{
            if(books.containsKey(book)){
                //sets count of book to 1
                books.put(book,1);
                System.out.println(book.toString()+" added to shelf "+ this.toString());
                return Code.SUCCESS;

            }
            else{
                return Code.SHELF_SUBJECT_MISMATCH_ERROR;

            }
        }

    }
//if book not present it will return print statement and error code.
    public Code removeBook(Book book){
        if(!books.containsKey(book)){
            System.out.println(book.getTitle()+" is not on shelf "+subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }
        //if book is present and has 0 count, if print statement and error code will appear.
        if(books.get(book)==0){
            System.out.println("No copies of "+book.getTitle()+" remain on shelf "+subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }
        //else, book count will decrease by 1 and print statemnent and code will appear.
     else{
         books.put(book,books.get(book)-1);
             System.out.println(book.getTitle()+" successfully removed from shelf "+ subject);
            return Code.SUCCESS;
        }
    }
//this will display list of books
 public String listBooks(){
        return  books.size()+" books on shelf: " +shelfNumber+ ":" +subject+ books;
 }
}