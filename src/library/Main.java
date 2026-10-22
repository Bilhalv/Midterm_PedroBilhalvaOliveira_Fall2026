package library;

// Console demo for the Maple Leaf Public Library midterm.
public class Main {

    public static void main(String[] args) {
        Library library = new Library("Maple Leaf Public Library");

        // ------------------------------------------------------------
        // 1. Staff
        // ------------------------------------------------------------
        Librarian librarian = new Librarian(
                "P-1001", "Alice Morgan", "416-555-0101",
                "S-201", "Circulation", 3200.00,
                25, 120);

        SeniorLibrarian seniorLibrarian = new SeniorLibrarian(
                "P-1002", "Ben Carter", "416-555-0102",
                "S-202", "Reference", 4200.00,
                30, 210,
                "Digital Archives", 650.00);

        LibraryAssistant libraryAssistant = new LibraryAssistant(
                "P-1003", "Chloe Nguyen", "416-555-0103",
                "S-203", "Front Desk", 2600.00,
                "Evening", 300.00);

        library.addStaff(librarian);
        library.addStaff(seniorLibrarian);
        library.addStaff(libraryAssistant);

        // ------------------------------------------------------------
        // 2. Member registration
        // ------------------------------------------------------------
        printHeader("1. MEMBER REGISTRATION");

        Member daniel = new Member(
                "P-2001", "Daniel Silva", "647-555-0201",
                "M-9001", 29, "Standard", 12.50);
        daniel.registerMember();

        Member priya = new Member(
                "P-2002", "Priya Raman", "647-555-0202",
                "M-9002", 34, "Standard", 0.00);
        priya.registerMember("Premium");

        library.addMember(daniel);
        library.addMember(priya);

        System.out.println();
        System.out.println("Rejected registrations:");

        try {
            new Member("P-9003", "Too Young", "647-555-0203",
                    "M-9003", 16, "Standard", 0.00);
            System.out.println("[FAILED] An under-age member was accepted - BUG!");
        } catch (IllegalArgumentException exception) {
            System.out.println("[FAILED] " + exception.getMessage());
        }

        try {
            new Member("P-9004", "   ", "647-555-0204",
                    "M-9004", 30, "Standard", 0.00);
            System.out.println("[FAILED] A blank name was accepted - BUG!");
        } catch (IllegalArgumentException exception) {
            System.out.println("[FAILED] " + exception.getMessage());
        }

        try {
            new Member("P-9005", "Negative Fees", "647-555-0205",
                    "M-9005", 30, "Standard", -10.00);
            System.out.println("[FAILED] Negative fees were accepted - BUG!");
        } catch (IllegalArgumentException exception) {
            System.out.println("[FAILED] " + exception.getMessage());
        }

        // ------------------------------------------------------------
        // 3. Member and staff details
        // ------------------------------------------------------------
        printHeader("2. MEMBER DETAILS");
        daniel.displayMemberDetails();
        System.out.println();
        priya.displayMemberDetails();

        printHeader("3. STAFF DETAILS");
        librarian.displayStaffDetails();
        System.out.println();
        seniorLibrarian.displayStaffDetails();
        System.out.println();
        libraryAssistant.displayStaffDetails();

        // ------------------------------------------------------------
        // 4. Late fees (overloaded methods)
        // ------------------------------------------------------------
        printHeader("4. LATE FEES");
        System.out.printf("5 days overdue at $1.00 per day: $%.2f%n",
                librarian.calculateLateFee(5));
        System.out.printf("5 days overdue at $2.50 per day: $%.2f%n",
                librarian.calculateLateFee(5, 2.50));

        System.out.println();
        System.out.println("Rejected late-fee calculations:");
        try {
            librarian.calculateLateFee(-3);
            System.out.println("[FAILED] Negative days were accepted - BUG!");
        } catch (IllegalArgumentException exception) {
            System.out.println("[FAILED] " + exception.getMessage());
        }

        try {
            librarian.calculateLateFee(4, 0);
            System.out.println("[FAILED] A zero daily rate was accepted - BUG!");
        } catch (IllegalArgumentException exception) {
            System.out.println("[FAILED] " + exception.getMessage());
        }

        // ------------------------------------------------------------
        // 5. Compensation
        // ------------------------------------------------------------
        printHeader("5. MONTHLY COMPENSATION");
        System.out.printf("%s: $%.2f%n", librarian.getName(),
                librarian.calculateMonthlyCompensation());
        System.out.printf("%s: $%.2f%n", seniorLibrarian.getName(),
                seniorLibrarian.calculateMonthlyCompensation());
        System.out.printf("%s: $%.2f%n", libraryAssistant.getName(),
                libraryAssistant.calculateMonthlyCompensation());

        // ------------------------------------------------------------
        // 6. Runtime polymorphism
        // ------------------------------------------------------------
        printHeader("6. RUNTIME POLYMORPHISM");
        LibraryStaff[] staffMembers = {librarian, seniorLibrarian, libraryAssistant};
        for (LibraryStaff staffMember : staffMembers) {
            System.out.printf("%s -> %s ($%.2f)%n",
                    staffMember.getName(),
                    staffMember.getRoleDescription(),
                    staffMember.calculateMonthlyCompensation());
        }

        Person[] people = {daniel, priya, librarian, seniorLibrarian, libraryAssistant};
        System.out.println();
        for (Person person : people) {
            System.out.println(person.getName() + " -> " + person.getRoleDescription());
        }

        // ------------------------------------------------------------
        // 7. Interfaces
        // ------------------------------------------------------------
        printHeader("7. INTERFACES");
        Book effectiveJava = new Book("B-1001", "Effective Java", "Joshua Bloch");
        Book cleanCode = new Book("B-1002", "Clean Code", "Robert C. Martin");
        library.addBook(effectiveJava);
        library.addBook(cleanCode);

        Reservable reservable = library;
        System.out.println("Reserve B-1001 through the library:");
        reservable.reserveBook("B-1001");

        System.out.println("Reserve B-1001 again:");
        reservable.reserveBook("B-1001");

        System.out.println("Reserve an unknown book:");
        reservable.reserveBook("B-9999");

        Reservable singleBook = cleanCode;
        System.out.println("Reserve B-1002 through a Book reference:");
        singleBook.reserveBook("B-1002");

        System.out.println();
        Reportable reportable = library;
        reportable.generateReport();

        // ------------------------------------------------------------
        // 8. Static counters
        // ------------------------------------------------------------
        printHeader("8. STATIC COUNTERS");
        System.out.println("Total people: " + Person.getPersonCount());
        System.out.println("Members     : " + Member.getMemberCount());
        System.out.println("Staff       : " + LibraryStaff.getStaffCount());

        // ------------------------------------------------------------
        // 9. Test cases
        // ------------------------------------------------------------
        printHeader("9. TEST CASES");
        runTestCases();
    }

    // ------------------------------------------------------------
    // Test cases
    // ------------------------------------------------------------

    private static int testsPassed = 0;
    private static int testsFailed = 0;

    private static void runTestCases() {
        testsPassed = 0;
        testsFailed = 0;

        testRegistration();
        testRegistrationValidation();
        testLateFees();
        testCompensation();
        testStaticCounters();
        testReservation();
        testPolymorphism();

        System.out.println();
        System.out.println("Test result: " + testsPassed + " passed, " + testsFailed + " failed.");
    }

    // Test 1: registration and the two registerMember() overloads.
    private static void testRegistration() {
        System.out.println();
        System.out.println("Test 1 - member registration");

        int peopleBefore = Person.getPersonCount();
        int membersBefore = Member.getMemberCount();

        Member basic = new Member("T-1001", "Basic Member", "416-555-1001",
                "TM-1001", 25, "Standard", 0.00);
        Member premium = new Member("T-1002", "Premium Member", "416-555-1002",
                "TM-1002", 41, "Standard", 0.00);

        basic.registerMember();
        premium.registerMember("Premium");

        check("basic registration uses Standard", basic.getMembershipType().equals("Standard"));
        check("premium registration keeps Premium", premium.getMembershipType().equals("Premium"));
        checkEquals("personCount increased by 2", peopleBefore + 2, Person.getPersonCount());
        checkEquals("memberCount increased by 2", membersBefore + 2, Member.getMemberCount());
    }

    // Test 2: invalid member data is rejected.
    private static void testRegistrationValidation() {
        System.out.println();
        System.out.println("Test 2 - registration validation");

        int peopleBefore = Person.getPersonCount();
        int membersBefore = Member.getMemberCount();

        boolean rejected = false;
        try {
            new Member("T-2001", "Minor", "416-555-2001", "TM-2001", 16, "Standard", 0.00);
        } catch (IllegalArgumentException exception) {
            rejected = true;
        }
        check("under-age member is rejected", rejected);

        rejected = false;
        try {
            new Member("T-2002", "   ", "416-555-2002", "TM-2002", 30, "Standard", 0.00);
        } catch (IllegalArgumentException exception) {
            rejected = true;
        }
        check("blank name is rejected", rejected);

        rejected = false;
        try {
            new Member("T-2003", "No Number", "416-555-2003", "", 30, "Standard", 0.00);
        } catch (IllegalArgumentException exception) {
            rejected = true;
        }
        check("blank member number is rejected", rejected);

        rejected = false;
        try {
            new Member("T-2004", "Owes Money", "416-555-2004", "TM-2004", 30, "Standard", -1.00);
        } catch (IllegalArgumentException exception) {
            rejected = true;
        }
        check("negative fees are rejected", rejected);

        check("rejected registrations do not change the counters",
                peopleBefore == Person.getPersonCount() && membersBefore == Member.getMemberCount());
    }

    // Test 3: both late-fee overloads and their validation.
    private static void testLateFees() {
        System.out.println();
        System.out.println("Test 3 - late fees");

        Librarian librarian = new Librarian("T-3001", "Fee Librarian", "416-555-3001",
                "TS-3001", "Circulation", 3000.00, 20, 0);

        checkEquals("7 days at the default rate is $7.00", 7.00, librarian.calculateLateFee(7));
        checkEquals("7 days at $2.50 per day is $17.50", 17.50, librarian.calculateLateFee(7, 2.50));

        boolean rejected = false;
        try {
            librarian.calculateLateFee(-1);
        } catch (IllegalArgumentException exception) {
            rejected = true;
        }
        check("negative overdue days are rejected", rejected);

        rejected = false;
        try {
            librarian.calculateLateFee(3, 0);
        } catch (IllegalArgumentException exception) {
            rejected = true;
        }
        check("a zero daily rate is rejected", rejected);
    }

    // Test 4: salary plus allowances for every staff type.
    private static void testCompensation() {
        System.out.println();
        System.out.println("Test 4 - monthly compensation");

        Librarian librarian = new Librarian("T-4001", "Comp Librarian", "416-555-4001",
                "TS-4001", "Circulation", 3000.00, 20, 150);
        SeniorLibrarian senior = new SeniorLibrarian("T-4002", "Comp Senior", "416-555-4002",
                "TS-4002", "Reference", 4000.00, 25, 200,
                "Archives", 500.00);
        LibraryAssistant assistant = new LibraryAssistant("T-4003", "Comp Assistant", "416-555-4003",
                "TS-4003", "Front Desk", 2500.00, "Evening", 300.00);

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

    // Test 5: the static counters stay consistent.
    private static void testStaticCounters() {
        System.out.println();
        System.out.println("Test 5 - static counters");

        checkEquals("total people = members + staff",
                Member.getMemberCount() + LibraryStaff.getStaffCount(), Person.getPersonCount());
    }

    // Test 6: a book can be reserved, not double-booked, and returned.
    private static void testReservation() {
        System.out.println();
        System.out.println("Test 6 - reservations");

        Book book = new Book("B-7001", "Test Book", "Test Author");
        Book otherBook = new Book("B-7002", "Other Book", "Test Author");

        book.reserveBook("B-7001");
        check("an available book can be reserved", !book.isAvailable());

        book.reserveBook("B-7001");
        check("a second reservation is rejected", !book.isAvailable());

        book.returnBook();
        check("a returned book is available again", book.isAvailable());

        otherBook.reserveBook("B-9999");
        check("a wrong book ID does not reserve the book", otherBook.isAvailable());
    }

    // Test 7: overridden methods are chosen at runtime.
    private static void testPolymorphism() {
        System.out.println();
        System.out.println("Test 7 - runtime polymorphism");

        Librarian librarian = new Librarian("T-7001", "Poly Librarian", "416-555-7001",
                "TS-7001", "Circulation", 3000.00, 20, 100);
        SeniorLibrarian senior = new SeniorLibrarian("T-7002", "Poly Senior", "416-555-7002",
                "TS-7002", "Reference", 4000.00, 25, 100,
                "Archives", 500.00);
        LibraryAssistant assistant = new LibraryAssistant("T-7003", "Poly Assistant", "416-555-7003",
                "TS-7003", "Front Desk", 2500.00, "Evening", 300.00);

        LibraryStaff[] staff = {librarian, senior, assistant};
        double[] expected = {3200.00, 4700.00, 2800.00};

        for (int i = 0; i < staff.length; i++) {
            checkEquals("correct compensation for " + staff[i].getName(),
                    expected[i], staff[i].calculateMonthlyCompensation());
        }

        check("a SeniorLibrarian is a Librarian", senior instanceof Librarian);
        check("a Librarian is a Person", librarian instanceof Person);
    }

    // ------------------------------------------------------------
    // Small helpers
    // ------------------------------------------------------------

    private static void check(String description, boolean condition) {
        if (condition) {
            testsPassed++;
            System.out.println("PASS: " + description);
        } else {
            testsFailed++;
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

    private static void printHeader(String title) {
        System.out.println();
        System.out.println("========== " + title + " ==========");
    }
}
