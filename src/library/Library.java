package library;

import java.util.ArrayList;

// Manages the library collections. Uses ArrayList and implements both
// interfaces, so it also demonstrates hybrid inheritance.
public class Library implements Reportable, Reservable {

    private final String name;
    private final ArrayList<Member> members;
    private final ArrayList<LibraryStaff> staff;
    private final ArrayList<Book> books;

    public Library(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Library name cannot be blank.");
        }
        this.name = name.trim();
        this.members = new ArrayList<>();
        this.staff = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public ArrayList<LibraryStaff> getStaff() {
        return staff;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void addMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }
        members.add(member);
    }

    public void addStaff(LibraryStaff staffMember) {
        if (staffMember == null) {
            throw new IllegalArgumentException("Staff member cannot be null.");
        }
        staff.add(staffMember);
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        books.add(book);
    }

    // Returns null when no member has that member number.
    public Member findMember(String memberNumber) {
        if (memberNumber == null) {
            return null;
        }
        for (Member member : members) {
            if (member.getMemberNumber().equalsIgnoreCase(memberNumber.trim())) {
                return member;
            }
        }
        return null;
    }

    // Returns null when no book has that id.
    public Book findBook(String bookId) {
        if (bookId == null) {
            return null;
        }
        for (Book book : books) {
            if (book.getBookId().equalsIgnoreCase(bookId.trim())) {
                return book;
            }
        }
        return null;
    }

    @Override
    public void reserveBook(String bookId) {
        Book book = findBook(bookId);
        if (book == null) {
            System.out.println("[FAILED] Reservation rejected: no book with ID " + bookId + ".");
            return;
        }
        book.reserveBook(bookId);
    }

    @Override
    public void generateReport() {
        System.out.println("===== " + name + " =====");
        System.out.println("Members: " + members.size());
        System.out.println("Staff  : " + staff.size());
        System.out.println("Books  : " + books.size());

        double payroll = 0;
        for (LibraryStaff staffMember : staff) {
            payroll = payroll + staffMember.calculateMonthlyCompensation();
        }
        System.out.printf("Monthly payroll: $%.2f%n", payroll);

        for (Book book : books) {
            book.generateReport();
        }
    }
}
