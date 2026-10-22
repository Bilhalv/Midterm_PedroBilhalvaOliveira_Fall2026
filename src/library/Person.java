package library;

// Abstract base class for everyone in the library (members and staff).
public abstract class Person {

    private final String personId;
    private String name;
    private String phoneNumber;

    // Counts every successfully created person. Member and LibraryStaff
    // increment it once their own validation has passed.
    protected static int personCount;

    public Person(String personId, String name, String phoneNumber) {
        if (personId == null || personId.isBlank()) {
            throw new IllegalArgumentException("Person ID cannot be blank.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be blank.");
        }
        this.personId = personId.trim();
        this.name = name.trim();
        this.phoneNumber = phoneNumber.trim();
    }

    public String getPersonId() {
        return personId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        this.name = name.trim();
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be blank.");
        }
        this.phoneNumber = phoneNumber.trim();
    }

    public static int getPersonCount() {
        return personCount;
    }

    public void displayBasicDetails() {
        System.out.println("Person ID: " + personId);
        System.out.println("Name     : " + name);
        System.out.println("Phone    : " + phoneNumber);
    }

    // Each subclass says what its role is.
    public abstract String getRoleDescription();

    @Override
    public String toString() {
        return "Person ID: " + personId + ", Name: " + name + ", Phone: " + phoneNumber;
    }
}
