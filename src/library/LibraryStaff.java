package library;

// Abstract staff class.
// Multilevel chain: Person -> LibraryStaff -> Librarian -> SeniorLibrarian.
public abstract class LibraryStaff extends Person {

    private final String staffId;
    private String department;
    private double monthlySalary;

    private static int staffCount;

    public LibraryStaff(String personId, String name, String phoneNumber,
                        String staffId, String department, double monthlySalary) {
        super(personId, name, phoneNumber);

        if (staffId == null || staffId.isBlank()) {
            throw new IllegalArgumentException("Staff ID cannot be blank.");
        }
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Department cannot be blank.");
        }
        if (monthlySalary <= 0) {
            throw new IllegalArgumentException("Monthly salary must be greater than zero.");
        }

        this.staffId = staffId.trim();
        this.department = department.trim();
        this.monthlySalary = monthlySalary;

        // Count the staff member only after all the checks above have passed.
        personCount++;
        staffCount++;
    }

    public static int getStaffCount() {
        return staffCount;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Department cannot be blank.");
        }
        this.department = department.trim();
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        if (monthlySalary <= 0) {
            throw new IllegalArgumentException("Monthly salary must be greater than zero.");
        }
        this.monthlySalary = monthlySalary;
    }

    public void displayStaffDetails() {
        displayBasicDetails();
        System.out.println("Staff ID : " + staffId);
        System.out.println("Department: " + department);
        System.out.printf("Salary   : $%.2f%n", monthlySalary);
    }

    // Each staff type calculates its own pay.
    public abstract double calculateMonthlyCompensation();

    @Override
    public String getRoleDescription() {
        return "Library staff member";
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Staff ID: " + staffId
                + ", Department: " + department
                + String.format(", Salary: $%.2f", monthlySalary);
    }
}
