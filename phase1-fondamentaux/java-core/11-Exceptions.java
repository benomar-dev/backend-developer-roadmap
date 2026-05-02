/**
 * 11 - Exception Handling: try/catch/finally, custom exceptions, multi-catch
 *
 * Topics covered:
 *  - Checked vs unchecked exceptions
 *  - try / catch / finally blocks
 *  - Multi-catch (Java 7+)
 *  - try-with-resources (Java 7+)
 *  - Creating custom exceptions
 *  - Rethrowing and chaining exceptions
 *  - Exercise: safe division, bank transfer validation
 */

// ============================================================
// CUSTOM EXCEPTION CLASSES
// ============================================================

/** Checked custom exception – must be declared or caught */
class InsufficientFundsException extends Exception {

    private final double shortfall;

    InsufficientFundsException(double shortfall) {
        super(String.format("Insufficient funds. Shortfall: %.2f", shortfall));
        this.shortfall = shortfall;
    }

    public double getShortfall() { return shortfall; }
}

/** Unchecked custom exception – extends RuntimeException */
class InvalidAmountException extends RuntimeException {

    private final double amount;

    InvalidAmountException(String message, double amount) {
        super(message + " Amount: " + amount);
        this.amount = amount;
    }

    public double getAmount() { return amount; }
}

/** Another custom exception showing exception chaining */
class DatabaseException extends Exception {

    DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

// ============================================================
// A simple wallet that throws custom exceptions
// ============================================================
class Wallet {

    private double balance;

    Wallet(double initialBalance) {
        if (initialBalance < 0) throw new InvalidAmountException("Initial balance cannot be negative.", initialBalance);
        this.balance = initialBalance;
    }

    public double getBalance() { return balance; }

    public void deposit(double amount) {
        if (amount <= 0) throw new InvalidAmountException("Deposit amount must be positive.", amount);
        balance += amount;
    }

    /**
     * Withdraws money from the wallet.
     *
     * @throws InvalidAmountException   if amount <= 0 (unchecked)
     * @throws InsufficientFundsException if balance < amount (checked)
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be positive.", amount);
        if (amount > balance) {
            throw new InsufficientFundsException(amount - balance);
        }
        balance -= amount;
    }

    @Override
    public String toString() {
        return String.format("Wallet{balance=%.2f}", balance);
    }
}

// ============================================================
// Simulated resource that implements AutoCloseable
// ============================================================
class FakeDatabase implements AutoCloseable {

    private final String name;
    private boolean connected = false;

    FakeDatabase(String name) throws DatabaseException {
        System.out.println("Opening connection to: " + name);
        if (name == null || name.isBlank()) {
            throw new DatabaseException("Database name is required",
                                        new IllegalArgumentException("blank name"));
        }
        this.name      = name;
        this.connected = true;
    }

    public String query(String sql) throws DatabaseException {
        if (!connected) throw new DatabaseException("Not connected", null);
        System.out.println("Executing: " + sql);
        if (sql.contains("DROP")) {
            throw new DatabaseException("DDL not allowed via this connection",
                                        new SecurityException("DROP blocked"));
        }
        return "Result of [" + sql + "]";
    }

    @Override
    public void close() {
        System.out.println("Closing connection to: " + name);
        connected = false;
    }
}

// ============================================================
// Main
// ============================================================
public class Exceptions {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. BASIC TRY / CATCH / FINALLY
        // ----------------------------------------------------------------
        System.out.println("=== Basic try/catch/finally ===");

        // ArithmeticException (unchecked)
        try {
            int result = divide(10, 0);
            System.out.println("Result: " + result);  // never reached
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        } finally {
            System.out.println("Finally block always runs.");
        }

        // NumberFormatException (unchecked)
        try {
            int number = Integer.parseInt("not_a_number");
            System.out.println(number);
        } catch (NumberFormatException e) {
            System.out.println("Caught NumberFormatException: " + e.getMessage());
        } finally {
            System.out.println("Parsing attempt finished.");
        }

        // ----------------------------------------------------------------
        // 2. MULTI-CATCH (Java 7+)
        // ----------------------------------------------------------------
        System.out.println("\n=== Multi-catch ===");

        String[] testValues = {"42", null, "abc", "0"};
        for (String val : testValues) {
            try {
                int parsed = Integer.parseInt(val.trim());  // possible NPE or NFE
                int result = 100 / parsed;                  // possible ArithmeticException
                System.out.println("100 / " + parsed + " = " + result);
            } catch (NullPointerException | NumberFormatException e) {
                System.out.println("Input error for '" + val + "': " + e.getClass().getSimpleName());
            } catch (ArithmeticException e) {
                System.out.println("Math error for '" + val + "': " + e.getMessage());
            }
        }

        // ----------------------------------------------------------------
        // 3. CUSTOM EXCEPTIONS
        // ----------------------------------------------------------------
        System.out.println("\n=== Custom Exceptions ===");

        Wallet wallet = new Wallet(100.0);
        System.out.println("Initial: " + wallet);

        // Successful deposit and withdrawal
        try {
            wallet.deposit(50.0);
            System.out.println("After deposit 50: " + wallet);
            wallet.withdraw(30.0);
            System.out.println("After withdraw 30: " + wallet);
        } catch (InsufficientFundsException e) {
            System.out.println("Should not happen: " + e.getMessage());
        }

        // Insufficient funds (checked exception)
        try {
            wallet.withdraw(500.0);
        } catch (InsufficientFundsException e) {
            System.out.println("Caught checked exception: " + e.getMessage());
            System.out.printf("  Shortfall: %.2f%n", e.getShortfall());
        }

        // Invalid amount (unchecked exception)
        try {
            wallet.deposit(-10);
        } catch (InvalidAmountException e) {
            System.out.println("Caught unchecked exception: " + e.getMessage());
        }

        // ----------------------------------------------------------------
        // 4. TRY-WITH-RESOURCES (AutoCloseable)
        // ----------------------------------------------------------------
        System.out.println("\n=== try-with-resources ===");

        // Successful usage
        try (FakeDatabase db = new FakeDatabase("users_db")) {
            String result = db.query("SELECT * FROM users");
            System.out.println(result);
        } catch (DatabaseException e) {
            System.out.println("DB error: " + e.getMessage());
        }
        // close() is called automatically even without explicit finally

        System.out.println();

        // Exception during query (resource still closed automatically)
        try (FakeDatabase db = new FakeDatabase("prod_db")) {
            db.query("DROP TABLE users");  // this will throw
        } catch (DatabaseException e) {
            System.out.println("Caught DB error: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("  Caused by: " + e.getCause().getMessage());
            }
        }

        System.out.println();

        // Exception during construction
        try (FakeDatabase db = new FakeDatabase("   ")) {
            db.query("SELECT 1");
        } catch (DatabaseException e) {
            System.out.println("Caught connection error: " + e.getMessage());
        }

        // ----------------------------------------------------------------
        // 5. RETHROWING AND EXCEPTION CHAINING
        // ----------------------------------------------------------------
        System.out.println("\n=== Rethrowing / Exception Chaining ===");

        try {
            doWork();
        } catch (DatabaseException e) {
            System.out.println("Top-level caught: " + e.getMessage());
            System.out.println("  Root cause: " + e.getCause().getMessage());
        }

        // ----------------------------------------------------------------
        // 6. EXERCISE: Safe division method
        // ----------------------------------------------------------------
        System.out.println("\n=== Exercise: Safe division ===");
        int[][] tests = {{10, 2}, {7, 0}, {100, 4}, {5, 0}};
        for (int[] pair : tests) {
            String result = safeDivide(pair[0], pair[1]);
            System.out.printf("%3d / %d = %s%n", pair[0], pair[1], result);
        }
    }

    // ----------------------------------------------------------------
    // HELPER METHODS
    // ----------------------------------------------------------------

    static int divide(int a, int b) {
        return a / b;  // throws ArithmeticException if b == 0
    }

    static void doWork() throws DatabaseException {
        try {
            // Simulate a low-level failure
            String text = null;
            text.length();  // throws NullPointerException
        } catch (NullPointerException e) {
            // Wrap in a higher-level exception (chaining)
            throw new DatabaseException("Failed to execute work", e);
        }
    }

    static String safeDivide(int a, int b) {
        try {
            return String.valueOf(a / b);
        } catch (ArithmeticException e) {
            return "Error: " + e.getMessage();
        }
    }
}
