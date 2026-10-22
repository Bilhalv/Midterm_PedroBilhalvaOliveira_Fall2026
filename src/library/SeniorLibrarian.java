package library;

// Senior librarian - top of the multilevel inheritance chain.
public class SeniorLibrarian extends Librarian {

    private final String specialization;
    private final double seniorityAllowance;

    public SeniorLibrarian(String personId, String name, String phoneNumber,
                           String staffId, String department, double monthlySalary,
                           int dailyIssueLimit, int booksProcessed,
                           String specialization, double seniorityAllowance) {
        super(personId, name, phoneNumber, staffId, department, monthlySalary,
                dailyIssueLimit, booksProcessed);

        if (specialization == null || specialization.isBlank()) {
            throw new IllegalArgumentException("Specialization cannot be blank.");
        }
        if (seniorityAllowance < 0) {
            throw new IllegalArgumentException("Seniority allowance cannot be negative.");
        }

        this.specialization = specialization.trim();
        this.seniorityAllowance = seniorityAllowance;
    }

    public String getSpecialization() {
        return specialization;
    }

    public double getSeniorityAllowance() {
        return seniorityAllowance;
    }

    // Salary + processing allowance (from Librarian) + seniority allowance.
    @Override
    public double calculateMonthlyCompensation() {
        return super.calculateMonthlyCompensation() + seniorityAllowance;
    }

    @Override
    public void displayStaffDetails() {
        super.displayStaffDetails();
        System.out.println("Specialty: " + specialization);
        System.out.printf("Seniority allowance: $%.2f%n", seniorityAllowance);
    }

    @Override
    public String getRoleDescription() {
        return "Senior librarian - " + specialization;
    }
}
