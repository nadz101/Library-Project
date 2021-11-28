
/** Nadia Rahbany
 * Book.Java Assignment Project (1/4)
 * CST 338
 */

import java.time.LocalDate;
import java.util.Objects;
import java.util.TimerTask;

public class Book {

    public static final int ISBN_ =0 ;
    public static final int TITLE_ = 1;
    public static final int SUBJECT_ = 2;
    public static final int PAGE_COUNT_ = 3;
    public static final int AUTHOR_ = 4;
    public static final int DUE_DATE_ = 5;

    private String isbn;
    private String title;
    private String subject;
    private int pageCount;
    private String author;
    private LocalDate dueDate;

    public Book(String Isbn, String Title, String Subject, int PageCount, String Author, LocalDate DueDate){
        isbn=Isbn;
        title=Title;
        subject=Subject;
        pageCount=PageCount;
        author=Author;
        dueDate=DueDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getPageCount() == book.getPageCount() && Objects.equals(getIsbn(), book.getIsbn()) && Objects.equals(getTitle(), book.getTitle()) && Objects.equals(getSubject(), book.getSubject()) && Objects.equals(getAuthor(), book.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsbn(), getTitle(), getSubject(), getPageCount(), getAuthor());
    }

    @Override
    public String toString() {
        return title + "by" + author  + "ISBN :" + isbn ;
    }
}
