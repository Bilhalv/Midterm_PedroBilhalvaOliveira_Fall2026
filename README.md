# COMP228 Practical Midterm - Library Management System

Pedro Bilhalva Oliveira - COMP228 Section 5 - Fall 2026

Console application for Maple Leaf Public Library. It registers members, keeps
track of library staff, calculates late fees and monthly compensation, reserves
books, prints reports and uses static counters.

## How to run in IntelliJ IDEA

1. Open the `Midterm_PedroBilhalvaOliveira_Fall2026` folder in IntelliJ IDEA.
   It is already an IntelliJ project.
2. Check that the Project SDK is Java 21 or newer
   (`File > Project Structure > Project SDK`).
3. In the Project panel open `src > library > Main.java`.
4. Click the green run arrow next to `public static void main`, or right-click
   `Main.java` and choose **Run 'Main.main()'**.
5. The program output appears in the **Run** window. The test cases run at the
   end of the program.

IntelliJ compiles the project automatically when you run `Main`, so no separate
build step is needed. Captured console output is in `docs/sample-output.txt`.

## Files

```
src/library/
  Person.java           abstract base class with personId, name, phone and personCount
  Member.java           member details, validation, registerMember() overloads
  LibraryStaff.java     abstract staff class with staffId, department and staffCount
  Librarian.java        late fees + processing allowance + compensation
  SeniorLibrarian.java  adds specialization and seniority allowance
  LibraryAssistant.java adds shift type and shift allowance
  Reportable.java       interface with generateReport()
  Reservable.java       interface with reserveBook(String bookId)
  Book.java             book details and reservation state (implements both interfaces)
  Library.java          keeps the ArrayList collections and prints the library report
  Main.java             console demo and test cases
```

## Class hierarchy

```
                        Person (abstract)
                        /              \
              Member                LibraryStaff (abstract)
                                        /            \
                                  Librarian     LibraryAssistant
                                        |
                                SeniorLibrarian

Interfaces: Reportable and Reservable
  Book    implements both interfaces
  Library implements both interfaces
```

- Single inheritance: `Member extends Person`.
- Multilevel inheritance: `Person -> LibraryStaff -> Librarian -> SeniorLibrarian`.
- Hierarchical inheritance: `Librarian` and `LibraryAssistant` both extend
  `LibraryStaff`.
- Hybrid inheritance: the class hierarchy above is combined with the
  `Reportable` and `Reservable` interfaces.

### Multiple inheritance

Java does not allow a class to extend more than one class. If it did, two
parent classes could have the same field or method with different behaviour,
and the compiler would not know which one to use (the diamond problem). Java
uses interfaces to solve this: a class can implement several interfaces because
interfaces only describe methods, not fields with values. `Book` and `Library`
implement both `Reportable` and `Reservable`.

## Main OOP features used

- Abstract classes and abstract methods: `Person.getRoleDescription()` and
  `LibraryStaff.calculateMonthlyCompensation()`.
- Constructors: each class sets its own fields and calls `super(...)`.
- `final` fields: `personId`, `memberNumber`, `staffId`, `dailyIssueLimit`,
  `specialization`, `shiftType`.
- Static fields and methods: `personCount`, `memberCount`, `staffCount`,
  `getPersonCount()`, `getMemberCount()`, `getStaffCount()`.
- Method overloading: `registerMember()` / `registerMember(String)` and
  `calculateLateFee(int)` / `calculateLateFee(int, double)`.
- Method overriding: `calculateMonthlyCompensation()` and
  `displayStaffDetails()` in `Librarian`, `SeniorLibrarian` and
  `LibraryAssistant`.
- Runtime polymorphism: `Main` stores the three staff objects in a
  `LibraryStaff[]` array and calls the overridden methods through the array.

## Business rules and validation

- Late fee is $1.00 per overdue day. The second `calculateLateFee` overload
  accepts a different daily rate.
- Negative overdue days and a daily rate of zero or less throw
  `IllegalArgumentException`.
- Librarian compensation is salary + $2.00 per processed book.
- Senior librarian compensation is the librarian compensation plus the
  seniority allowance.
- Library assistant compensation is salary plus the shift allowance.
- Members must be between 18 and 120 years old. Blank names, IDs, member
  numbers, membership types and negative fees are rejected.
- A book can only be reserved when it is available. A second reservation and an
  unknown book ID print an error message.
- `personCount` is incremented once in `Member` and once in `LibraryStaff`
  after all of their validation passes. `staffCount` is only incremented in
  `LibraryStaff`. Since `SeniorLibrarian` and `LibraryAssistant` go through the
  `LibraryStaff` constructor, every object is counted exactly once, and failed
  registrations are not counted.

## What Main demonstrates

1. Registers two members (basic registration and Premium registration) and shows
   three rejected registrations.
2. Displays member and staff details.
3. Calculates late fees with both overloads and shows the invalid cases.
4. Prints the compensation for each staff category.
5. Demonstrates runtime polymorphism with `LibraryStaff[]` and `Person[]`.
6. Reserves books through the `Reservable` interface (success, duplicate,
   unknown ID) and prints the library report through `Reportable`.
7. Prints the total people, member and staff counters.
8. Runs the test cases (section 9) and prints a pass/fail summary.

## Test cases

The test cases are inside `Main` and run automatically after the demo. There are
27 checks in seven groups:

- Member registration and both `registerMember` overloads.
- Registration validation (age, blank name, blank member number, negative fees)
  and that rejected members do not change the counters.
- Late fees with the default rate, a custom rate, negative days and an invalid
  rate.
- Compensation for the librarian, senior librarian and library assistant,
  including `processBook()`.
- Static counters (people = members + staff).
- Reservations (available, already reserved, returned, wrong book ID).
- Method overriding and runtime polymorphism.

The last run printed `Test result: 27 passed, 0 failed.`
