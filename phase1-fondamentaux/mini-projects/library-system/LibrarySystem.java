/**
 * Library Management System — Mini Project (Week 3)
 *
 * Demonstrates: Classes, objects, encapsulation, Collections, OOP
 *
 * Features:
 *  - Add books to the library catalogue
 *  - Remove books
 *  - Search books by title, author, or ISBN
 *  - Borrow and return books
 *  - Display the full catalogue
 *  - Simple in-memory storage (ArrayList + HashMap)
 */

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

// ============================================================
// Book – represents a single book in the library
// ============================================================
class Book {

    private final String isbn;
    private String title;
    private String author;
    private int    year;
    private String genre;
    private int    totalCopies;
    private int    availableCopies;

    Book(String isbn, String title, String author, int year, String genre, int copies) {
        this.isbn            = isbn;
        this.title           = title;
        this.author          = author;
        this.year            = year;
        this.genre           = genre;
        this.totalCopies     = copies;
        this.availableCopies = copies;
    }

    // Getters
    public String getIsbn()             { return isbn; }
    public String getTitle()            { return title; }
    public String getAuthor()           { return author; }
    public int    getYear()             { return year; }
    public String getGenre()            { return genre; }
    public int    getTotalCopies()      { return totalCopies; }
    public int    getAvailableCopies()  { return availableCopies; }
    public boolean isAvailable()        { return availableCopies > 0; }

    // Package-private: only Library should call these
    void borrow() {
        if (availableCopies == 0) throw new IllegalStateException("No copies available for: " + title);
        availableCopies--;
    }

    void returnBook() {
        if (availableCopies >= totalCopies) throw new IllegalStateException("All copies already returned for: " + title);
        availableCopies++;
    }

    @Override
    public String toString() {
        return String.format("%-14s | %-35s | %-20s | %d | %-12s | %d/%d copies",
                isbn, title, author, year, genre, availableCopies, totalCopies);
    }
}

// ============================================================
// Member – a library member who can borrow books
// ============================================================
class Member {

    private final String memberId;
    private final String name;
    private final String email;
    private final List<BorrowRecord> borrowHistory = new ArrayList<>();

    Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name     = name;
        this.email    = email;
    }

    public String getMemberId()              { return memberId; }
    public String getName()                  { return name; }
    public String getEmail()                 { return email; }
    public List<BorrowRecord> getBorrowHistory() { return Collections.unmodifiableList(borrowHistory); }

    void addRecord(BorrowRecord record)      { borrowHistory.add(record); }

    public long currentlyBorrowed() {
        return borrowHistory.stream().filter(r -> !r.isReturned()).count();
    }

    @Override
    public String toString() {
        return String.format("Member{id=%s, name=%s, email=%s, active borrows=%d}",
                memberId, name, email, currentlyBorrowed());
    }
}

// ============================================================
// BorrowRecord – tracks a single borrow event
// ============================================================
class BorrowRecord {

    private final String    memberId;
    private final String    isbn;
    private final LocalDate borrowDate;
    private LocalDate       returnDate;

    BorrowRecord(String memberId, String isbn) {
        this.memberId   = memberId;
        this.isbn       = isbn;
        this.borrowDate = LocalDate.now();
    }

    public String    getMemberId()  { return memberId; }
    public String    getIsbn()      { return isbn; }
    public LocalDate getBorrowDate(){ return borrowDate; }
    public LocalDate getReturnDate(){ return returnDate; }
    public boolean   isReturned()   { return returnDate != null; }

    void markReturned() { this.returnDate = LocalDate.now(); }

    @Override
    public String toString() {
        return String.format("BorrowRecord{member=%s, isbn=%s, borrowed=%s, returned=%s}",
                memberId, isbn, borrowDate, isReturned() ? returnDate : "not yet");
    }
}

// ============================================================
// Library – the main manager class
// ============================================================
class Library {

    private static final int MAX_BORROWS_PER_MEMBER = 5;

    private final String name;
    private final Map<String, Book>   catalogue = new LinkedHashMap<>();  // isbn -> Book
    private final Map<String, Member> members   = new LinkedHashMap<>();  // memberId -> Member

    Library(String name) { this.name = name; }

    // ---- Catalogue management ----

    public void addBook(Book book) {
        if (catalogue.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("Book already exists: " + book.getIsbn());
        }
        catalogue.put(book.getIsbn(), book);
    }

    public boolean removeBook(String isbn) {
        Book book = catalogue.get(isbn);
        if (book == null) return false;
        if (book.getAvailableCopies() < book.getTotalCopies()) {
            throw new IllegalStateException("Cannot remove: some copies are currently borrowed.");
        }
        catalogue.remove(isbn);
        return true;
    }

    // ---- Member management ----

    public void registerMember(Member member) {
        if (members.containsKey(member.getMemberId())) {
            throw new IllegalArgumentException("Member already registered: " + member.getMemberId());
        }
        members.put(member.getMemberId(), member);
    }

    // ---- Search ----

    public List<Book> searchByTitle(String keyword) {
        String kw = keyword.toLowerCase();
        return catalogue.values().stream()
            .filter(b -> b.getTitle().toLowerCase().contains(kw))
            .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String keyword) {
        String kw = keyword.toLowerCase();
        return catalogue.values().stream()
            .filter(b -> b.getAuthor().toLowerCase().contains(kw))
            .collect(Collectors.toList());
    }

    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(catalogue.get(isbn));
    }

    public List<Book> getAvailableBooks() {
        return catalogue.values().stream()
            .filter(Book::isAvailable)
            .collect(Collectors.toList());
    }

    // ---- Borrow / Return ----

    public BorrowRecord borrowBook(String memberId, String isbn) {
        Member member = getOrThrow(members, memberId, "Member not found: " + memberId);
        Book   book   = getOrThrow(catalogue, isbn,    "Book not found: " + isbn);

        if (!book.isAvailable()) {
            throw new IllegalStateException("No copies available for: " + book.getTitle());
        }
        if (member.currentlyBorrowed() >= MAX_BORROWS_PER_MEMBER) {
            throw new IllegalStateException(member.getName() + " has reached the borrow limit.");
        }

        book.borrow();
        BorrowRecord record = new BorrowRecord(memberId, isbn);
        member.addRecord(record);
        return record;
    }

    public void returnBook(String memberId, String isbn) {
        Member member = getOrThrow(members, memberId, "Member not found: " + memberId);
        Book   book   = getOrThrow(catalogue, isbn,   "Book not found: " + isbn);

        BorrowRecord active = member.getBorrowHistory().stream()
            .filter(r -> r.getIsbn().equals(isbn) && !r.isReturned())
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(
                member.getName() + " hasn't borrowed: " + book.getTitle()));

        active.markReturned();
        book.returnBook();
    }

    // ---- Display ----

    public void displayCatalogue() {
        System.out.println("\n📚 " + name + " — Full Catalogue");
        System.out.println("=".repeat(115));
        System.out.printf("%-14s | %-35s | %-20s | %-4s | %-12s | Copies%n",
                "ISBN", "Title", "Author", "Year", "Genre");
        System.out.println("-".repeat(115));
        catalogue.values().forEach(System.out::println);
        System.out.println("=".repeat(115));
        System.out.printf("Total books: %d | Available copies: %d%n%n",
                catalogue.size(),
                catalogue.values().stream().mapToInt(Book::getAvailableCopies).sum());
    }

    public void displayMembers() {
        System.out.println("\n👤 Registered Members");
        System.out.println("-".repeat(60));
        members.values().forEach(System.out::println);
        System.out.println();
    }

    // ---- Utility ----

    private <K, V> V getOrThrow(Map<K, V> map, K key, String message) {
        V value = map.get(key);
        if (value == null) throw new NoSuchElementException(message);
        return value;
    }
}

// ============================================================
// Main – demonstrates all features
// ============================================================
public class LibrarySystem {

    public static void main(String[] args) {

        Library lib = new Library("City Central Library");

        // ---- Add books ----
        lib.addBook(new Book("978-0134685991", "Effective Java",                "Joshua Bloch",        2018, "Programming", 3));
        lib.addBook(new Book("978-0132350884", "Clean Code",                    "Robert C. Martin",    2008, "Programming", 2));
        lib.addBook(new Book("978-0201633610", "Design Patterns (GoF)",         "Gang of Four",        1994, "Programming", 2));
        lib.addBook(new Book("978-0321125217", "Domain-Driven Design",          "Eric Evans",          2003, "Architecture",1));
        lib.addBook(new Book("978-1491950357", "Spring Boot: Up & Running",     "Mark Heckler",        2021, "Framework",   2));
        lib.addBook(new Book("978-0596517748", "JavaScript: The Good Parts",    "Douglas Crockford",   2008, "Programming", 1));
        lib.addBook(new Book("978-0201485677", "The Pragmatic Programmer",      "Hunt & Thomas",       1999, "Practices",   3));
        lib.addBook(new Book("978-1492043447", "Designing Data-Intensive Apps", "Martin Kleppmann",    2017, "Architecture",2));

        // ---- Register members ----
        lib.registerMember(new Member("M001", "Alice Martin",  "alice@example.com"));
        lib.registerMember(new Member("M002", "Bob Dupont",    "bob@example.com"));
        lib.registerMember(new Member("M003", "Charlie Smith", "charlie@example.com"));

        // ---- Display initial state ----
        lib.displayCatalogue();
        lib.displayMembers();

        // ---- Search ----
        System.out.println("🔍 Search by title 'java':");
        lib.searchByTitle("java").forEach(System.out::println);

        System.out.println("\n🔍 Search by author 'Martin':");
        lib.searchByAuthor("Martin").forEach(System.out::println);

        // ---- Borrow books ----
        System.out.println("\n📖 Borrowing books...");
        try {
            BorrowRecord r1 = lib.borrowBook("M001", "978-0134685991");
            System.out.println("Alice borrowed: " + r1);

            BorrowRecord r2 = lib.borrowBook("M002", "978-0134685991");
            System.out.println("Bob borrowed:   " + r2);

            BorrowRecord r3 = lib.borrowBook("M001", "978-0132350884");
            System.out.println("Alice borrowed: " + r3);

            BorrowRecord r4 = lib.borrowBook("M003", "978-0321125217");
            System.out.println("Charlie borrowed: " + r4);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        lib.displayCatalogue();

        // ---- Try to borrow unavailable book ----
        System.out.println("🚫 Trying to borrow all copies of 'Domain-Driven Design':");
        try {
            lib.borrowBook("M002", "978-0321125217");  // only 1 copy, already taken
        } catch (IllegalStateException e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        // ---- Return a book ----
        System.out.println("\n↩️  Returning books...");
        lib.returnBook("M001", "978-0134685991");
        System.out.println("Alice returned 'Effective Java'");

        lib.returnBook("M003", "978-0321125217");
        System.out.println("Charlie returned 'Domain-Driven Design'");

        lib.displayCatalogue();

        // ---- Available books ----
        System.out.println("✅ Available books:");
        lib.getAvailableBooks().forEach(b ->
            System.out.printf("  %s (%d copies)%n", b.getTitle(), b.getAvailableCopies()));

        // ---- Member borrow history ----
        System.out.println("\n📋 Alice's borrow history:");
        lib.displayMembers();
    }
}
