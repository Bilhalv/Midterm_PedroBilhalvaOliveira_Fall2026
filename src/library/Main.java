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
    }

    private static void printHeader(String title) {
        System.out.println();
        System.out.println("========== " + title + " ==========");
    }
}
