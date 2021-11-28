//Nadia Rahbany
//Library.java
//This will work together with Book.java, Reader.java, and Shelf.java

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Library {
    public final static int LENDING_LIMIT = 5;
    private String name;
    private static int libraryCard;
    private List<Reader> readers;
    private HashMap<String, Shelf> shelves;
    private HashMap<Book, Integer> books;


    public Library(String name) {
        this.name = name;
        this.libraryCard = 0;
        this.readers = new ArrayList<>();
        this.shelves = new HashMap<>();
        this.books = new HashMap<>();
    }


    public Code init(String filename) {
        int readerCount;
        int bookCount;
        int shelfCount;
        File f = new File(filename);
        Scanner scan;
        try {
            scan = new Scanner(f);
        } catch (FileNotFoundException e) {
            return Code.FILE_NOT_FOUND_ERROR;
        }

        bookCount = convertInt(scan.nextLine(), Code.BOOK_COUNT_ERROR);

        if (bookCount < 0) {
            return errorCode(bookCount);
        }
        initBooks(bookCount, scan);
        listBooks();

        shelfCount = convertInt(scan.nextLine(), Code.SHELF_NUMBER_PARSE_ERROR);
        if (shelfCount < 0) {
            System.out.println("Number of shelves does't match expected");
            return errorCode(shelfCount);
        }
        initShelves(shelfCount, scan);
        listShelves(true);

        readerCount = convertInt(scan.nextLine(), Code.READER_COUNT_ERROR);
        if (readerCount < 0) {
            return errorCode(readerCount);
        }
        initReader(readerCount, scan);
        listReaders();
        return Code.SUCCESS;
    }

    private Code initBooks(int bookCount, Scanner scan) {
        System.out.println("Parsing " + bookCount + " books");
        if (bookCount < 1) {
            return Code.LIBRARY_ERROR;
        }
        int count = 0;
        while (scan.hasNextLine() && count < bookCount) {
            String line = scan.nextLine();
            String[] bookinfo = line.split(",");
            String isbn = bookinfo[0];
            String title = bookinfo[1];
            String subject = bookinfo[2];
            int pageCount = convertInt(bookinfo[3], Code.PAGE_COUNT_ERROR);
            String author = bookinfo[4];
            LocalDate date = convertDate(bookinfo[5], Code.DATE_CONVERSION_ERROR);

            Book BookBeingAdded = new Book(isbn, title, subject, pageCount, author, date);
            addBook(BookBeingAdded);
            count++;
        }
        System.out.println("SUCCESS");
        return Code.SUCCESS;
    }

    private Code initShelves(int shelfCount, Scanner scan) {
        int shelfCountStart = shelfCount;
        System.out.println("parsing " + shelfCount + " shelves");
        if (shelfCount < 1) {
            return Code.SHELF_COUNT_ERROR;
        }
        while (scan.hasNextLine() && shelfCount > 0) {
            String line = scan.nextLine();
            String[] shelfinfo = line.split(",");
            int shelfNum = 0;
            String subject = shelfinfo[1];


            if (convertInt(shelfinfo[0], Code.SHELF_NUMBER_PARSE_ERROR) <= 0) {
                System.out.println("shelf number error");
                return Code.SHELF_NUMBER_PARSE_ERROR;
            }

            System.out.println("parsing shelf " + line);
            Shelf ShelfBeingAdded = new Shelf();
            ShelfBeingAdded.setSubject(subject);
            ShelfBeingAdded.setShelfNumber(shelfNum);
            addShelf(ShelfBeingAdded);
            for (Book book : books.keySet()) {
                    addBookToShelf(book, ShelfBeingAdded);
            }
            shelfCount--;
        }
        if (shelves.size() == shelfCountStart) {
            System.out.println("SUCCESS");
            return Code.SUCCESS;
        }
        System.out.println("Number of shelves doesn't match expected");
        return Code.SHELF_NUMBER_PARSE_ERROR;
    }

    private Code initReader(int readerCount, Scanner scan) {
        System.out.println("parsing " + readerCount + " readers");
        if (readerCount <= 0) {
            return Code.READER_COUNT_ERROR;
        }
        while (scan.hasNextLine() && readerCount > 0) {
            String fileLine = scan.nextLine();
            String[] readerinfo = fileLine.split(",");

            int cardNum = convertInt(readerinfo[0], Code.READER_CARD_NUMBER_ERROR);
            String name = readerinfo[1];
            String phone = readerinfo[2];
            LocalDate duedate = convertDate(readerinfo[5], Code.DATE_CONVERSION_ERROR);


            Reader read = new Reader(cardNum, name, phone);
            addReader(read);
            int bookCount = Integer.parseInt(readerinfo[Reader.BOOK_COUNT_]);
            for (int i = Reader.BOOK_COUNT_ + 1; i < Reader.BOOK_COUNT_ + bookCount * 2; i += 2) {
                Book bookadd = getBookByISBN(readerinfo[i]);
                bookadd.setDueDate(duedate);
                read.addBook(bookadd);
            }
        }
        System.out.println("SUCCESS");
        return Code.SUCCESS;
    }

    public static int convertInt(String recordCountString, Code code) {
        int num = 0;
        try {
            num = Integer.parseInt(recordCountString.trim());
        } catch (NumberFormatException nfe) {
            if (code == Code.PAGE_COUNT_ERROR) {
                System.out.println("Value which caused the error:" + recordCountString + Code.PAGE_COUNT_ERROR.getMessage());
                return Code.PAGE_COUNT_ERROR.getCode();
            } else if (code == Code.BOOK_COUNT_ERROR) {
                System.out.println("Value which caused the error:" + recordCountString + Code.BOOK_COUNT_ERROR.getMessage());
                return Code.BOOK_COUNT_ERROR.getCode();
            } else if (code == Code.DATE_CONVERSION_ERROR) {
                System.out.println("Value which caused the error:" + recordCountString + Code.DATE_CONVERSION_ERROR.getMessage());
                return Code.DATE_CONVERSION_ERROR.getCode();
            } else {
                System.out.println("Value which caused the error:" + recordCountString + Code.UNKNOWN_ERROR.getMessage());
            }

        }
        return num;

    }

    public Code addBook(Book newBook) {

        if (books.containsKey(newBook)) {
            books.put(newBook, books.get(newBook) + 1);
            System.out.println(books.get(newBook) + " copies of " + newBook.getTitle() + " in the stacks.");
        } else {
            books.put(newBook, 1);
            System.out.println(newBook.getTitle() + " added to the stacks.");
        }

        if (shelves.containsKey(newBook.getSubject())) {
            shelves.get(newBook.getSubject()).addBook(newBook);
            return Code.SUCCESS;
        } else {
            System.out.println("No shelf for " + newBook.getSubject() + " books.");
            return Code.SHELF_EXISTS_ERROR;
        }
    }

    public Code returnBook(Reader reader, Book book) {
        if (!reader.hasBook(book)) {
            System.out.println(reader.getName() + " doesn't have " + book.getTitle() + " checked out.");
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        }

        System.out.println(reader.getName() + " is returning " + book);
        Code result = reader.removeBook(book);

        if (result.equals(Code.SUCCESS)) {
            return returnBook(book);
        } else {
            System.out.println("Could not return " + book);
            return result;
        }
    }

    public Code returnBook(Book book) {
        if (!shelves.containsKey(book.getSubject())) {
            System.out.println("No shelf for " + book);
            return Code.SHELF_EXISTS_ERROR;
        } else {
            shelves.get(book.getSubject()).addBook(book);
            return Code.SUCCESS;
        }
    }

    private Code addBookToShelf(Book book, Shelf shelf) {
        //shelf.addBook(book);
        if (returnBook(book) == Code.SUCCESS) {
          //  System.out.println("book returned");
            return Code.SUCCESS;
        }
        if (shelf.getSubject().equals(book.getSubject())) {
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;
        }

        Code resCode = shelf.addBook(book);
        if (resCode.equals(Code.SUCCESS)) {
            System.out.println(book + " added to shelf.");
        }
        return resCode;
    }

    public int listBooks() {
        int bookCountAmount = 0;
        for (Book book : books.keySet()) {
            bookCountAmount += books.get(book);
            System.out.println(books.get(book) + " copies of " + book.toString());
        }
        return bookCountAmount;
    }


    public Code checkOutBook(Reader reader, Book book) {
        if (!readers.contains(reader)) {
            System.out.println(reader.getName() + " doesn't have an account here.");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }

        if (reader.getBookCount() >= LENDING_LIMIT) {
            System.out.println(reader.getName() + " has reached the lending limit, " + LENDING_LIMIT);
            return Code.BOOK_LIMIT_REACHED_ERROR;
        }

        if (!books.containsKey(book)) {
            System.out.println("ERROR: could not find " + book);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        if (!shelves.containsKey(book.getSubject())) {
            System.out.println("No shelf for " + book.getSubject() + " books!");
            return Code.SHELF_EXISTS_ERROR;
        }

        if (shelves.get(book.getSubject()).getBookCount(book) < 1) {
            System.out.println("ERROR: no copies of " + book + " remain.");
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        Code code = reader.addBook(book);
        if (!code.equals(Code.SUCCESS)) {
            System.out.println("Couldn't checkout " + book);
            return code;
        }

        Code result = shelves.get(book.getSubject()).removeBook(book);
        if (result.equals(Code.SUCCESS)) {
            System.out.println(book + " checked out successfully");
        }
        return result;
    }

    public Book getBookByISBN(String isbn) {

        for (Book book : books.keySet()) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        System.out.println("ERROR: Could not find a book with isbn: " + isbn);
        return null;
    }

    public Code listShelves(boolean showbooks) {
        if (showbooks) {
            for (Shelf shelf : shelves.values()) {
                System.out.println(shelf.listBooks());
            }
        } else {
            for (Shelf shelf : shelves.values()) {
                System.out.println(shelf.toString());
            }
        }
        return Code.SUCCESS;
    }

    public Code addShelf(String shelfSubject) {
        Shelf shelf = new Shelf();
        shelf.setSubject(shelfSubject);
        return addShelf(shelf);
    }

    public Code addShelf(Shelf shelf) {
        int nShelfNum = 0;

        if (shelves.containsKey(shelf)) {
            System.out.println("ERROR: Shelf already exists" + shelf);
            return Code.SHELF_EXISTS_ERROR;
        }
        for (Shelf shelf1 : shelves.values()) {
            if (shelf1.getShelfNumber() > nShelfNum) {
                nShelfNum = shelf1.getShelfNumber();
            }
        }
        shelves.put(shelf.getSubject(), shelf);
        shelf.setShelfNumber(nShelfNum + 1);

        for (Book book : books.keySet()) {
            if (book.getSubject().equals(shelf.getSubject())) {
                shelf.addBook(book);
            }
        }
        return Code.SUCCESS;
    }

    public Shelf getShelf(Integer shelfNumber) {

        for (Shelf shelf : shelves.values()) {
            if (shelf.getShelfNumber() == shelfNumber) {
                return shelf;
            }
        }
        System.out.println("No shelf number " + shelfNumber + " found");
        return null;
    }

    public Shelf getShelf(String subject) {

        if (shelves.containsKey(subject)) {
            return shelves.get(subject);
        }
        System.out.println("No shelf for " + subject + " books");
        return null;
    }

    public int listReaders() {
        for (Reader reader : readers) {
            System.out.println(reader.toString());
        }
        return readers.size();
    }

    public int listReaders(boolean showBooks) {

        if (showBooks) {
            for (Reader read : readers) {
                System.out.println(read.toString());
            }
        } else {
            for (Reader reader : readers) {
                System.out.println(reader.toString());
            }
        }
        return readers.size();
    }

    public Reader getReaderByCard(int cardNumber) {

        for (Reader read : readers) {
            if (read.getCardNumber() == cardNumber)
                return read;
        }
        System.out.println("Could not find a reader with card #" + cardNumber);
        return null;
    }

    public Code addReader(Reader reader) {

        if (readers.contains(reader)) {
            System.out.println(reader + " already has an account!");
            return Code.READER_ALREADY_EXISTS_ERROR;
        }

        for (Reader read : readers) {
            if (read.getCardNumber() == reader.getCardNumber()) {
                System.out.println(read.getName() + " and " + reader.getName() + " have the same card number!");
                return Code.READER_CARD_NUMBER_ERROR;
            }
        }

        readers.add(reader);
        System.out.println("\n" + reader.getName() + " added to the library!");
        if (reader.getCardNumber() > libraryCard) {
            libraryCard = reader.getCardNumber();
        }
        return Code.SUCCESS;
    }

    public Code removeReader(Reader reader) {
        if (!readers.contains(reader)) {
            System.out.println(reader.getName() + " is not part of this Library, " + name);
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }

        if (reader.getBookCount() >= 1) {
            System.out.println(reader.getName() + " must return all books!");
            return Code.READER_STILL_HAS_BOOKS_ERROR;
        }

        readers.remove(reader);
        return Code.SUCCESS;
    }

    public static LocalDate convertDate(String date, Code errorCode) {

        if (date.equals("0000")) {
            return LocalDate.of(1970, 1, 1);
        }

        String[] dateValues = date.split("-");
        int[] intDateValues = new int[dateValues.length];
        for (int i = 0; i < dateValues.length; i++) { //loop to parse string values
            intDateValues[i] = Integer.parseInt(dateValues[i]);
        }

        if (intDateValues.length < 3) {
            System.out.println("ERROR: " + errorCode.getMessage() + ", could not parse " + date);
            System.out.println("Using default date (01-jan-1970)");
            return LocalDate.of(1970, 1, 1);
        }

        for (int intDateValue : intDateValues) {
            if (intDateValue < 0) {
                System.out.println("Error converting date: Year " + intDateValues[0]);
                System.out.println("Error converting date: Month " + intDateValues[1]);
                System.out.println("Error converting date: Day " + intDateValues[2]);
                System.out.println("Using default date (01-jan-1970)");
                return LocalDate.of(1970, 1, 1);
            }
        }

        return LocalDate.of(intDateValues[0], intDateValues[1], intDateValues[2]);
    }


    public static int getLibraryCard() {
        return libraryCard++;
    }

    private Code errorCode(int codeNumber) {
        for (Code code : Code.values()) {
            if (code.getCode() == codeNumber) {
                return code;
            }
        }

        return Code.UNKNOWN_ERROR;
    }
}