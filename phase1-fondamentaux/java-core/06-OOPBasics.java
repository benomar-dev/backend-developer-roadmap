/**
 * 06 - OOP Basics: Classes, Objects, Constructors, Encapsulation
 *
 * Topics covered:
 *  - Defining classes and instantiating objects
 *  - Instance variables (fields) and methods
 *  - Constructors (default, parameterised, copy)
 *  - Encapsulation: private fields + getters/setters
 *  - The 'this' keyword
 *  - static members
 *  - toString(), equals(), hashCode() overrides
 *
 * Exercise: Car class management system
 */

// ============================================================
// Car class – demonstrates encapsulation
// ============================================================
class Car {

    // Private fields (encapsulation)
    private String make;
    private String model;
    private int    year;
    private double fuelLevel;     // 0.0 to 100.0 percent
    private int    mileage;       // total km driven
    private static int carCount = 0;  // shared across all instances

    // ---- Constructors ----

    /** Default constructor */
    Car() {
        this("Unknown", "Unknown", 2000);
    }

    /** Parameterised constructor */
    Car(String make, String model, int year) {
        this.make      = make;
        this.model     = model;
        this.year      = year;
        this.fuelLevel = 100.0;
        this.mileage   = 0;
        carCount++;
    }

    /** Copy constructor */
    Car(Car other) {
        this(other.make, other.model, other.year);
        this.fuelLevel = other.fuelLevel;
        this.mileage   = other.mileage;
    }

    // ---- Getters ----
    public String getMake()      { return make; }
    public String getModel()     { return model; }
    public int    getYear()      { return year; }
    public double getFuelLevel() { return fuelLevel; }
    public int    getMileage()   { return mileage; }

    /** Returns total number of Car objects created */
    public static int getCarCount() { return carCount; }

    // ---- Setters with validation ----
    public void setMake(String make) {
        if (make == null || make.isBlank()) throw new IllegalArgumentException("Make cannot be empty");
        this.make = make;
    }

    public void setModel(String model) {
        if (model == null || model.isBlank()) throw new IllegalArgumentException("Model cannot be empty");
        this.model = model;
    }

    public void setYear(int year) {
        if (year < 1886 || year > 2100) throw new IllegalArgumentException("Invalid year: " + year);
        this.year = year;
    }

    // ---- Behaviour methods ----

    /**
     * Drives the car a given distance (km), consuming 0.08L/km fuel.
     * Throws IllegalArgumentException for negative distance.
     * Throws IllegalStateException when fuel runs out.
     */
    public void drive(int km) {
        if (km <= 0) throw new IllegalArgumentException("Distance must be positive");
        double fuelNeeded = km * 0.08;
        if (fuelNeeded > fuelLevel) {
            throw new IllegalStateException("Not enough fuel! Need " + fuelNeeded + "L, have " + fuelLevel + "L");
        }
        fuelLevel -= fuelNeeded;
        mileage   += km;
    }

    /** Refuels the car up to 100% */
    public void refuel(double litres) {
        if (litres <= 0) throw new IllegalArgumentException("Litres must be positive");
        fuelLevel = Math.min(100.0, fuelLevel + litres);
    }

    /** Returns a human-readable description */
    @Override
    public String toString() {
        return String.format("Car{%d %s %s | fuel=%.1f%% | mileage=%d km}",
                year, make, model, fuelLevel, mileage);
    }

    /** Two cars are equal if they share the same make, model and year */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Car)) return false;
        Car other = (Car) obj;
        return year == other.year
            && make.equalsIgnoreCase(other.make)
            && model.equalsIgnoreCase(other.model);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(make.toLowerCase(), model.toLowerCase(), year);
    }
}

// ============================================================
// BankAccount class – another encapsulation example
// ============================================================
class BankAccount {

    private final String accountNumber;
    private final String owner;
    private double balance;

    BankAccount(String accountNumber, String owner, double initialBalance) {
        if (initialBalance < 0) throw new IllegalArgumentException("Initial balance cannot be negative");
        this.accountNumber = accountNumber;
        this.owner         = owner;
        this.balance       = initialBalance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwner()         { return owner; }
    public double getBalance()       { return balance; }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be positive");
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal must be positive");
        if (amount > balance) throw new IllegalStateException("Insufficient funds");
        balance -= amount;
    }

    @Override
    public String toString() {
        return String.format("BankAccount{#%s owner='%s' balance=%.2f}", accountNumber, owner, balance);
    }
}

// ============================================================
// Main class
// ============================================================
public class OOPBasics {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. CREATING OBJECTS
        // ----------------------------------------------------------------
        System.out.println("=== Creating Car Objects ===");

        Car car1 = new Car("Toyota", "Corolla", 2022);
        Car car2 = new Car("BMW",    "M3",      2023);
        Car car3 = new Car();            // default constructor
        Car car4 = new Car(car1);        // copy constructor

        System.out.println("car1: " + car1);
        System.out.println("car2: " + car2);
        System.out.println("car3: " + car3);
        System.out.println("car4 (copy of car1): " + car4);
        System.out.println("Total cars created: " + Car.getCarCount());

        // ----------------------------------------------------------------
        // 2. USING GETTERS AND SETTERS
        // ----------------------------------------------------------------
        System.out.println("\n=== Getters and Setters ===");

        System.out.println("car1 make:  " + car1.getMake());
        System.out.println("car1 year:  " + car1.getYear());
        System.out.println("car1 fuel:  " + car1.getFuelLevel() + "%");

        car3.setMake("Honda");
        car3.setModel("Civic");
        car3.setYear(2021);
        System.out.println("Updated car3: " + car3);

        // ----------------------------------------------------------------
        // 3. BEHAVIOUR METHODS
        // ----------------------------------------------------------------
        System.out.println("\n=== Driving and Refuelling ===");

        car1.drive(200);
        System.out.println("After driving 200km: " + car1);

        car1.drive(500);
        System.out.println("After driving 500km more: " + car1);

        car1.refuel(40);
        System.out.println("After refuelling 40L: " + car1);

        // Handling insufficient fuel
        try {
            car2.drive(2000);  // will exhaust fuel
        } catch (IllegalStateException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }

        // ----------------------------------------------------------------
        // 4. EQUALS AND HASHCODE
        // ----------------------------------------------------------------
        System.out.println("\n=== equals() and hashCode() ===");

        System.out.println("car1.equals(car4): " + car1.equals(car4));  // true (same data)
        System.out.println("car1.equals(car2): " + car1.equals(car2));  // false
        System.out.println("car1 == car4:      " + (car1 == car4));     // false (different references)

        // ----------------------------------------------------------------
        // 5. BANK ACCOUNT EXERCISE
        // ----------------------------------------------------------------
        System.out.println("\n=== BankAccount Exercise ===");

        BankAccount acc1 = new BankAccount("ACC-001", "Alice", 1000.00);
        BankAccount acc2 = new BankAccount("ACC-002", "Bob",    500.00);

        System.out.println(acc1);
        System.out.println(acc2);

        acc1.deposit(250.00);
        System.out.println("After Alice deposits 250: " + acc1);

        acc2.withdraw(100.00);
        System.out.println("After Bob withdraws 100:  " + acc2);

        // Simulate a transfer
        double transferAmount = 150.00;
        acc1.withdraw(transferAmount);
        acc2.deposit(transferAmount);
        System.out.println("After transferring " + transferAmount + " from Alice to Bob:");
        System.out.println("  " + acc1);
        System.out.println("  " + acc2);

        // Attempt overdraft
        try {
            acc2.withdraw(10000);
        } catch (IllegalStateException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }
    }
}
