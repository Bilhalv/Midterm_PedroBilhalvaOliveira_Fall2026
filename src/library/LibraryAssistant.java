package library;

// Library assistant - extends LibraryStaff directly (hierarchical inheritance).
public class LibraryAssistant extends LibraryStaff {

    private final String shiftType;
    private final double shiftAllowance;

    public LibraryAssistant(String personId, String name, String phoneNumber,
                            String staffId, String department, double monthlySalary,
                            String shiftType, double shiftAllowance) {
        super(personId, name, phoneNumber, staffId, department, monthlySalary);

        if (shiftType == null || shiftType.isBlank()) {
            throw new IllegalArgumentException("Shift type cannot be blank.");
        }
        if (shiftAllowance < 0) {
            throw new IllegalArgumentException("Shift allowance cannot be negative.");
        }

        this.shiftType = shiftType.trim();
        this.shiftAllowance = shiftAllowance;
    }

    public String getShiftType() {
        return shiftType;
    }

    public double getShiftAllowance() {
        return shiftAllowance;
    }

    @Override
    public double calculateMonthlyCompensation() {
        return getMonthlySalary() + shiftAllowance;
    }

    @Override
    public void displayStaffDetails() {
        super.displayStaffDetails();
        System.out.println("Shift: " + shiftType);
        System.out.printf("Shift allowance: $%.2f%n", shiftAllowance);
        System.out.printf("Compensation: $%.2f%n", calculateMonthlyCompensation());
    }

    @Override
    public String getRoleDescription() {
        return "Library assistant - " + shiftType + " shift";
    }
}
