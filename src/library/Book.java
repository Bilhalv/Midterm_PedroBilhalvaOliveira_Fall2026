package library;

// A book in the library. Implements both interfaces (hybrid inheritance:
// normal classes plus interface contracts).
public class Book implements Reportable, Reservable {

    private final String bookId;
    private final String title;
    private final String author;
    private boolean available;

    public Book(String bookId, String title, String author) {
        if (bookId == null || bookId.isBlank()) {
            throw new IllegalArgumentException("Book ID cannot be blank.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Book title cannot be blank.");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Book author cannot be blank.");
        }
        this.bookId = bookId.trim();
        this.title = title.trim();
        this.author = author.trim();
        this.available = true;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public void reserveBook(String bookId) {
        if (bookId == null || !this.bookId.equalsIgnoreCase(bookId.trim())) {
            System.out.println("[FAILED] Reservation rejected: " + bookId + " is not this book.");
            return;
        }
        if (!available) {
            System.out.println("[FAILED] Reservation rejected: " + title + " is already reserved.");
            return;
        }
        available = false;
        System.out.println("[OK] Reservation successful: " + title + " (" + this.bookId + ").");
    }

    public void returnBook() {
        if (available) {
            System.out.println("[INFO] " + title + " is already available.");
            return;
        }
        available = true;
        System.out.println("[OK] " + title + " returned to the library.");
    }

    @Override
    public void generateReport() {
        String status = "available";
        if (!available) {
            status = "reserved";
        }
        System.out.println("Book " + bookId + ": " + title + " by " + author + " - " + status);
    }

    @Override
    public String toString() {
        return "Book " + bookId + ": " + title + " by " + author;
    }
}
