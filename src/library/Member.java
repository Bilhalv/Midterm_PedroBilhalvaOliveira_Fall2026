package library;

// A library member. Single inheritance: Member extends Person.
public class Member extends Person {

    private final String memberNumber;
    private int age;
    private String membershipType;
    private double outstandingFees;

    private static int memberCount;

    public Member(String personId, String name, String phoneNumber,
                  String memberNumber, int age, String membershipType,
                  double outstandingFees) {
        super(personId, name, phoneNumber);

        if (memberNumber == null || memberNumber.isBlank()) {
            throw new IllegalArgumentException("Member number cannot be blank.");
        }
        if (age < 18 || age > 120) {
            throw new IllegalArgumentException("Member age must be between 18 and 120.");
        }
        if (membershipType == null || membershipType.isBlank()) {
            throw new IllegalArgumentException("Membership type cannot be blank.");
        }
        if (outstandingFees < 0) {
            throw new IllegalArgumentException("Outstanding fees cannot be negative.");
        }

        this.memberNumber = memberNumber.trim();
        this.age = age;
        this.membershipType = membershipType.trim();
        this.outstandingFees = outstandingFees;

        // Count the member only after all the checks above have passed.
        personCount++;
        memberCount++;
    }

    // Basic registration: the membership type becomes "Standard".
    public boolean registerMember() {
        return registerMember("Standard");
    }

    // Registration with an explicit membership type.
    public boolean registerMember(String type) {
        if (type == null || type.isBlank()) {
            System.out.println("Registration failed: membership type cannot be blank.");
            return false;
        }
        this.membershipType = type.trim();
        System.out.println("Member " + getName() + " registered as " + this.membershipType + ".");
        return true;
    }

    public static int getMemberCount() {
        return memberCount;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public int getAge() {
        return age;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public double getOutstandingFees() {
        return outstandingFees;
    }

    public void setAge(int age) {
        if (age < 18 || age > 120) {
            throw new IllegalArgumentException("Member age must be between 18 and 120.");
        }
        this.age = age;
    }

    public void setMembershipType(String membershipType) {
        if (membershipType == null || membershipType.isBlank()) {
            throw new IllegalArgumentException("Membership type cannot be blank.");
        }
        this.membershipType = membershipType.trim();
    }

    // Adds a late fee to the member's balance.
    public void addFee(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Fee amount must be greater than zero.");
        }
        outstandingFees = outstandingFees + amount;
    }

    // Records a payment against the member's balance.
    public void payFees(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment must be greater than zero.");
        }
        if (amount > outstandingFees) {
            throw new IllegalArgumentException("Payment is larger than the outstanding fees.");
        }
        outstandingFees = outstandingFees - amount;
    }

    public void displayMemberDetails() {
        displayBasicDetails();
        System.out.println("Member no: " + memberNumber);
        System.out.println("Age      : " + age);
        System.out.println("Type     : " + membershipType);
        System.out.printf("Fees owed: $%.2f%n", outstandingFees);
    }

    @Override
    public String getRoleDescription() {
        return "Library member (" + membershipType + ")";
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Member no: " + memberNumber
                + ", Type: " + membershipType
                + String.format(", Fees owed: $%.2f", outstandingFees);
    }
}
