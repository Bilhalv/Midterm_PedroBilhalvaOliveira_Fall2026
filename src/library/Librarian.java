package library;

// Librarian - third level of the inheritance chain and parent of SeniorLibrarian.
public class Librarian extends LibraryStaff {

    // Default late fee: $1.00 per overdue day.
    public static final double DEFAULT_DAILY_RATE = 1.00;
    // Processing allowance: $2.00 per processed book.
    public static final double PROCESSING_ALLOWANCE_PER_BOOK = 2.00;

    private final int dailyIssueLimit;
    private int booksProcessed;

    public Librarian(String personId, String name, String phoneNumber,
                     String staffId, String department, double monthlySalary,
                     int dailyIssueLimit, int booksProcessed) {
        super(personId, name, phoneNumber, staffId, department, monthlySalary);

        if (dailyIssueLimit <= 0) {
            throw new IllegalArgumentException("Daily issue limit must be greater than zero.");
        }
        if (booksProcessed < 0) {
            throw new IllegalArgumentException("Books processed cannot be negative.");
        }

        this.dailyIssueLimit = dailyIssueLimit;
        this.booksProcessed = booksProcessed;
    }

    public int getDailyIssueLimit() {
        return dailyIssueLimit;
    }

    public int getBooksProcessed() {
        return booksProcessed;
    }

    public void processBook() {
        booksProcessed++;
    }

    public void processBooks(int numberOfBooks) {
        if (numberOfBooks < 0) {
            throw new IllegalArgumentException("Number of books cannot be negative.");
        }
        booksProcessed = booksProcessed + numberOfBooks;
    }

    // Overload 1: uses the default rate of $1.00 per overdue day.
    public double calculateLateFee(int daysOverdue) {
        return calculateLateFee(daysOverdue, DEFAULT_DAILY_RATE);
    }

    // Overload 2: uses the supplied daily rate.
    public double calculateLateFee(int daysOverdue, double dailyRate) {
        if (daysOverdue < 0) {
            throw new IllegalArgumentException("Days overdue cannot be negative.");
        }
        if (dailyRate <= 0) {
            throw new IllegalArgumentException("Daily rate must be greater than zero.");
        }
        return daysOverdue * dailyRate;
    }

    public double calculateProcessingAllowance() {
        return booksProcessed * PROCESSING_ALLOWANCE_PER_BOOK;
    }

    @Override
    public double calculateMonthlyCompensation() {
        return getMonthlySalary() + calculateProcessingAllowance();
    }

    @Override
    public void displayStaffDetails() {
        super.displayStaffDetails();
        System.out.println("Issue limit: " + dailyIssueLimit + " books per day");
        System.out.println("Processed: " + booksProcessed + " books");
        System.out.printf("Compensation: $%.2f%n", calculateMonthlyCompensation());
    }

    @Override
    public String getRoleDescription() {
        return "Librarian - issues books and processes new stock";
    }
}
