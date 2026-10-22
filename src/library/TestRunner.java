package library;

// Simple test program for the midterm.
// Run with: java -cp out library.TestRunner
public class TestRunner {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== COMP228 Midterm Tests ===");

        testRegistrationSuccess();
        testRegistrationValidation();
        testLateFees();
        testLateFeeValidation();
        testCompensation();
        testStaticCounters();
        testReservation();
        testPolymorphism();

        System.out.println();
        System.out.println("RESULT: " + passed + " passed, " + failed + " failed");

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void testRegistrationSuccess() {
        section("1. Member registration");

        int peopleBefore = Person.getPersonCount();
        int membersBefore = Member.getMemberCount();

        Member member1 = new Member("T-1001", "Basic Member", "416-555-1001",
                "TM-1001", 25, "Standard", 0.00);
        Member member2 = new Member("T-1002", "Premium Member", "416-555-1002",
                "TM-1002", 41, "Standard", 15.00);

        boolean basicOk = member1.registerMember();
        boolean premiumOk = member2.registerMember("Premium");

        check("basic registration returns true", basicOk);
        check("premium registration returns true", premiumOk);
        check("basic registration uses Standard", member1.getMembershipType().equals("Standard"));
        check("premium registration keeps Premium", member2.getMembershipType().equals("Premium"));
        checkEquals("personCount increased by 2", peopleBefore + 2, Person.getPersonCount());
        checkEquals("memberCount increased by 2", membersBefore + 2, Member.getMemberCount());
    }

    private static void testRegistrationValidation() {
        section("2. Registration validation");

        int peopleBefore = Person.getPersonCount();
        int membersBefore = Member.getMemberCount();

        boolean rejected = false;
        try {
            new Member("T-2001", "Minor", "416-555-2001", "TM-2001", 16, "Standard", 0.00);
        } catch (IllegalArgumentException exception) {
            rejected = true;
            System.out.println("  got expected error: " + exception.getMessage());
        }
        check("under-age member is rejected", rejected);

        rejected = false;
        try {
            new Member("T-2002", "   ", "416-555-2002", "TM-2002", 30, "Standard", 0.00);
        } catch (IllegalArgumentException exception) {
            rejected = true;
            System.out.println("  got expected error: " + exception.getMessage());
        }
        check("blank name is rejected", rejected);

        rejected = false;
        try {
            new Member("T-2003", "No Number", "416-555-2003", "", 30, "Standard", 0.00);
        } catch (IllegalArgumentException exception) {
            rejected = true;
            System.out.println("  got expected error: " + exception.getMessage());
        }
        check("blank member number is rejected", rejected);

        rejected = false;
        try {
            new Member("T-2004", "Owes Money", "416-555-2004", "TM-2004", 30, "Standard", -1.00);
        } catch (IllegalArgumentException exception) {
            rejected = true;
            System.out.println("  got expected error: " + exception.getMessage());
        }
        check("negative fees are rejected", rejected);

        checkEquals("rejected registrations do not change personCount",
                peopleBefore, Person.getPersonCount());
        checkEquals("rejected registrations do not change memberCount",
                membersBefore, Member.getMemberCount());
    }

    private static void testLateFees() {
        section("3. Late fees");

        Librarian librarian = new Librarian("T-3001", "Fee Librarian", "416-555-3001",
                "TS-3001", "Circulation", 3000.00, 20, 0);

        checkEquals("7 days at the default rate is $7.00", 7.00, librarian.calculateLateFee(7));
        checkEquals("0 days is $0.00", 0.00, librarian.calculateLateFee(0));
        checkEquals("7 days at $2.50 is $17.50", 17.50, librarian.calculateLateFee(7, 2.50));
        checkEquals("1 day at $0.75 is $0.75", 0.75, librarian.calculateLateFee(1, 0.75));
    }

    private static void testLateFeeValidation() {
        section("4. Late fee validation");

        Librarian librarian = new Librarian("T-4001", "Fee Librarian", "416-555-4001",
                "TS-4001", "Circulation", 3000.00, 20, 0);

        boolean rejected = false;
        try {
            librarian.calculateLateFee(-1);
        } catch (IllegalArgumentException exception) {
            rejected = true;
            System.out.println("  got expected error: " + exception.getMessage());
        }
        check("negative overdue days are rejected", rejected);

        rejected = false;
        try {
            librarian.calculateLateFee(3, 0);
        } catch (IllegalArgumentException exception) {
            rejected = true;
            System.out.println("  got expected error: " + exception.getMessage());
        }
        check("a zero daily rate is rejected", rejected);

        rejected = false;
        try {
            librarian.calculateLateFee(3, -2.00);
        } catch (IllegalArgumentException exception) {
            rejected = true;
            System.out.println("  got expected error: " + exception.getMessage());
        }
        check("a negative daily rate is rejected", rejected);
    }

    private static void testCompensation() {
        section("5. Monthly compensation");

        Librarian librarian = new Librarian("T-5001", "Comp Librarian", "416-555-5001",
                "TS-5001", "Circulation", 3000.00, 20, 150);
        SeniorLibrarian senior = new SeniorLibrarian("T-5002", "Comp Senior", "416-555-5002",
                "TS-5002", "Reference", 4000.00, 25, 200,
                "Archives", 500.00);
        LibraryAssistant assistant = new LibraryAssistant("T-5003", "Comp Assistant", "416-555-5003",
                "TS-5003", "Front Desk", 2500.00, "Evening", 300.00);

        // librarian: 3000 + 150 * 2 = 3300
        checkEquals("librarian gets $2.00 per processed book",
                3300.00, librarian.calculateMonthlyCompensation());
        // senior: 4000 + 200 * 2 + 500 = 4900
        checkEquals("senior adds the seniority allowance",
                4900.00, senior.calculateMonthlyCompensation());
        // assistant: 2500 + 300 = 2800
        checkEquals("assistant adds the shift allowance",
                2800.00, assistant.calculateMonthlyCompensation());

        librarian.processBook();
        checkEquals("processBook adds $2.00",
                3302.00, librarian.calculateMonthlyCompensation());
    }

    private static void testStaticCounters() {
        section("6. Static counters");

        int people = Person.getPersonCount();
        int members = Member.getMemberCount();
        int staff = LibraryStaff.getStaffCount();

        check("staff counter is greater than zero", staff > 0);
        check("member counter is greater than zero", members > 0);
        checkEquals("total people = members + staff", members + staff, people);
    }

    private static void testReservation() {
        section("7. Reservations");

        Book book = new Book("B-7001", "Test Book", "Test Author");
        Book otherBook = new Book("B-7002", "Other Book", "Test Author");

        check("a new book is available", book.isAvailable());
        book.reserveBook("B-7001");
        check("an available book can be reserved", !book.isAvailable());

        book.reserveBook("B-7001");
        check("a reserved book stays reserved on a second attempt", !book.isAvailable());

        book.returnBook();
        check("a returned book is available again", book.isAvailable());

        otherBook.reserveBook("B-9999");
        check("an unknown book id does not reserve the book", otherBook.isAvailable());
    }

    private static void testPolymorphism() {
        section("8. Runtime polymorphism");

        Librarian librarian = new Librarian("T-8001", "Poly Librarian", "416-555-8001",
                "TS-8001", "Circulation", 3000.00, 20, 100);
        SeniorLibrarian senior = new SeniorLibrarian("T-8002", "Poly Senior", "416-555-8002",
                "TS-8002", "Reference", 4000.00, 25, 100,
                "Archives", 500.00);
        LibraryAssistant assistant = new LibraryAssistant("T-8003", "Poly Assistant", "416-555-8003",
                "TS-8003", "Front Desk", 2500.00, "Evening", 300.00);

        LibraryStaff[] staff = {librarian, senior, assistant};
        double[] expected = {3200.00, 4700.00, 2800.00};

        for (int i = 0; i < staff.length; i++) {
            checkEquals("correct compensation for " + staff[i].getName(),
                    expected[i], staff[i].calculateMonthlyCompensation());
        }

        check("a SeniorLibrarian is a Librarian", senior instanceof Librarian);
        check("a Librarian is LibraryStaff", librarian instanceof LibraryStaff);
        check("a Librarian is a Person", librarian instanceof Person);

        LibraryStaff assistantAsStaff = assistant;
        check("a LibraryAssistant is not a Librarian", !(assistantAsStaff instanceof Librarian));
    }

    // ------------------------------------------------------------
    // Small helpers
    // ------------------------------------------------------------

    private static void section(String title) {
        System.out.println();
        System.out.println("--- " + title + " ---");
    }

    private static void check(String description, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("PASS: " + description);
        } else {
            failed++;
            System.out.println("FAIL: " + description);
        }
    }

    private static void checkEquals(String description, int expected, int actual) {
        check(description + " (expected " + expected + ", got " + actual + ")",
                expected == actual);
    }

    private static void checkEquals(String description, double expected, double actual) {
        check(description + " (expected " + expected + ", got " + actual + ")",
                Math.abs(expected - actual) < 0.0001);
    }
}
