/**
 * Simple Banking System — Mini Project (Week 4)
 *
 * Demonstrates: Inheritance, Interfaces, Encapsulation, Collections, Exceptions
 *
 * Features:
 *  - Multiple account types: Savings, Checking
 *  - Deposit, withdraw, transfer
 *  - Interest calculation (Savings)
 *  - Overdraft protection (Checking)
 *  - Complete transaction history
 *  - Bank-level statistics
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

// ============================================================
// Transaction record
// ============================================================
class Transaction {

    enum Type { DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT, INTEREST }

    private final Type          type;
    private final double        amount;
    private final double        balanceAfter;
    private final String        description;
    private final LocalDateTime timestamp;

    Transaction(Type type, double amount, double balanceAfter, String description) {
        this.type         = type;
        this.amount       = amount;
        this.balanceAfter = balanceAfter;
        this.description  = description;
        this.timestamp    = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%s] %-15s %+8.2f  balance: %8.2f  %s",
                timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                type, amount, balanceAfter, description);
    }
}

// ============================================================
// Custom exceptions
// ============================================================
class InsufficientBalanceException extends RuntimeException {
    InsufficientBalanceException(double requested, double available) {
        super(String.format("Insufficient balance. Requested: %.2f, Available: %.2f",
                requested, available));
    }
}

class AccountFrozenException extends RuntimeException {
    AccountFrozenException(String accountId) {
        super("Account is frozen: " + accountId);
    }
}

// ============================================================
// Abstract Account – base for all account types
// ============================================================
abstract class Account {

    private final String accountId;
    private final String ownerName;
    protected double balance;
    private boolean frozen = false;
    private final List<Transaction> transactions = new ArrayList<>();

    Account(String accountId, String ownerName, double initialDeposit) {
        if (initialDeposit < 0)
            throw new IllegalArgumentException("Initial deposit cannot be negative");
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance   = initialDeposit;
        if (initialDeposit > 0) {
            record(Transaction.Type.DEPOSIT, initialDeposit, "Initial deposit");
        }
    }

    // ---- Getters ----
    public String getAccountId()  { return accountId; }
    public String getOwnerName()  { return ownerName; }
    public double getBalance()    { return balance; }
    public boolean isFrozen()     { return frozen; }
    public String  getType()      { return getClass().getSimpleName(); }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    // ---- Admin ----
    public void freeze()   { frozen = true; }
    public void unfreeze() { frozen = false; }

    // ---- Core operations ----
    public void deposit(double amount) {
        checkNotFrozen();
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be positive");
        balance += amount;
        record(Transaction.Type.DEPOSIT, amount, "Deposit");
    }

    public void withdraw(double amount) {
        checkNotFrozen();
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal must be positive");
        validateWithdrawal(amount);   // subclasses may override
        balance -= amount;
        record(Transaction.Type.WITHDRAWAL, -amount, "Withdrawal");
    }

    /** Override to add account-type-specific logic (e.g. overdraft) */
    protected void validateWithdrawal(double amount) {
        if (amount > balance) throw new InsufficientBalanceException(amount, balance);
    }

    public void transfer(double amount, Account target) {
        checkNotFrozen();
        target.checkNotFrozen();
        if (amount <= 0) throw new IllegalArgumentException("Transfer amount must be positive");
        validateWithdrawal(amount);

        balance -= amount;
        record(Transaction.Type.TRANSFER_OUT, -amount, "Transfer to " + target.accountId);

        target.balance += amount;
        target.record(Transaction.Type.TRANSFER_IN, amount, "Transfer from " + accountId);
    }

    /** Hook for interest calculation – implemented by subclasses */
    public abstract void applyInterest();

    // ---- Display ----
    public void printStatement() {
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("  %s Account Statement%n", getType());
        System.out.printf("  Account: %s | Owner: %s | Balance: %.2f%s%n",
                accountId, ownerName, balance, frozen ? " [FROZEN]" : "");
        System.out.println("=".repeat(80));
        transactions.forEach(System.out::println);
        System.out.printf("  Current balance: %.2f%n", balance);
        System.out.println("=".repeat(80));
    }

    // ---- Internal helpers ----
    void record(Transaction.Type type, double amount, String description) {
        transactions.add(new Transaction(type, amount, balance, description));
    }

    protected void checkNotFrozen() {
        if (frozen) throw new AccountFrozenException(accountId);
    }

    @Override
    public String toString() {
        return String.format("%s[%s] %-15s | %s | balance=%.2f%s",
                getType(), accountId, ownerName,
                frozen ? "FROZEN" : "active", balance,
                frozen ? " 🔒" : "");
    }
}

// ============================================================
// SavingsAccount – earns interest, no overdraft
// ============================================================
class SavingsAccount extends Account {

    private double interestRate;   // annual rate, e.g. 0.035 = 3.5%

    SavingsAccount(String id, String owner, double deposit, double interestRate) {
        super(id, owner, deposit);
        this.interestRate = interestRate;
    }

    public double getInterestRate() { return interestRate; }
    public void   setInterestRate(double rate) {
        if (rate < 0 || rate > 1) throw new IllegalArgumentException("Rate must be 0-1");
        this.interestRate = rate;
    }

    @Override
    public void applyInterest() {
        double interest = balance * interestRate;
        if (interest > 0) {
            balance += interest;
            record(Transaction.Type.INTEREST, interest,
                    String.format("Annual interest (%.1f%%)", interestRate * 100));
        }
    }
}

// ============================================================
// CheckingAccount – allows overdraft up to a limit
// ============================================================
class CheckingAccount extends Account {

    private final double overdraftLimit;

    CheckingAccount(String id, String owner, double deposit, double overdraftLimit) {
        super(id, owner, deposit);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() { return overdraftLimit; }

    @Override
    protected void validateWithdrawal(double amount) {
        if (amount > balance + overdraftLimit) {
            throw new InsufficientBalanceException(amount, balance + overdraftLimit);
        }
    }

    @Override
    public void applyInterest() {
        // Checking accounts don't earn interest in this model
        System.out.println("[" + getAccountId() + "] Checking accounts do not accrue interest.");
    }
}

// ============================================================
// Bank – manages all accounts
// ============================================================
class Bank {

    private final String name;
    private final Map<String, Account> accounts = new LinkedHashMap<>();
    private int nextAccountNumber = 1001;

    Bank(String name) { this.name = name; }

    // ---- Account creation ----

    public SavingsAccount createSavings(String owner, double deposit, double rate) {
        String id = "SAV-" + nextAccountNumber++;
        SavingsAccount acc = new SavingsAccount(id, owner, deposit, rate);
        accounts.put(id, acc);
        System.out.printf("✅ Created %s for %s (%.1f%% APY)%n", id, owner, rate * 100);
        return acc;
    }

    public CheckingAccount createChecking(String owner, double deposit, double overdraft) {
        String id = "CHK-" + nextAccountNumber++;
        CheckingAccount acc = new CheckingAccount(id, owner, deposit, overdraft);
        accounts.put(id, acc);
        System.out.printf("✅ Created %s for %s (overdraft: %.2f)%n", id, owner, overdraft);
        return acc;
    }

    // ---- Operations ----

    public Optional<Account> findAccount(String id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public void applyAllInterest() {
        System.out.println("\n💰 Applying interest to all accounts...");
        accounts.values().forEach(Account::applyInterest);
    }

    // ---- Statistics ----

    public void printSummary() {
        System.out.println("\n🏦 " + name + " — Summary");
        System.out.println("=".repeat(70));
        accounts.values().forEach(System.out::println);
        System.out.println("-".repeat(70));

        DoubleSummaryStatistics stats = accounts.values().stream()
            .mapToDouble(Account::getBalance)
            .summaryStatistics();

        System.out.printf("Total accounts : %d%n", accounts.size());
        System.out.printf("Total deposits : %.2f%n", stats.getSum());
        System.out.printf("Average balance: %.2f%n", stats.getAverage());
        System.out.printf("Highest balance: %.2f%n", stats.getMax());
        System.out.printf("Lowest balance : %.2f%n%n", stats.getMin());

        // Accounts by type
        Map<String, Long> byType = accounts.values().stream()
            .collect(Collectors.groupingBy(Account::getType, Collectors.counting()));
        byType.forEach((type, count) -> System.out.printf("  %s accounts: %d%n", type, count));
    }
}

// ============================================================
// Main
// ============================================================
public class BankingSystem {

    public static void main(String[] args) {

        Bank bank = new Bank("Java National Bank");

        System.out.println("=== Creating Accounts ===\n");

        SavingsAccount alice  = bank.createSavings("Alice",   5000.00, 0.035);
        SavingsAccount bob    = bank.createSavings("Bob",     1500.00, 0.025);
        CheckingAccount carol = bank.createChecking("Carol",  3000.00, 500.00);
        CheckingAccount dave  = bank.createChecking("Dave",    200.00, 1000.00);

        // ---- Deposits ----
        System.out.println("\n=== Deposits ===");
        alice.deposit(2000.00);
        bob.deposit(500.00);
        carol.deposit(1000.00);

        // ---- Withdrawals ----
        System.out.println("\n=== Withdrawals ===");
        alice.withdraw(500.00);
        carol.withdraw(1500.00);

        // Dave uses overdraft
        System.out.println("\nDave's balance before: " + dave.getBalance());
        dave.withdraw(900.00);  // uses £700 of overdraft (200 + 700 = 900)
        System.out.printf("Dave's balance after 900 withdrawal (overdraft used): %.2f%n", dave.getBalance());

        // ---- Attempt to exceed overdraft ----
        System.out.println("\n=== Over-limit Withdrawal ===");
        try {
            dave.withdraw(500.00);  // balance=-700, limit=1000, would need 500 more → exceeds limit
        } catch (InsufficientBalanceException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // ---- Transfer ----
        System.out.println("\n=== Transfers ===");
        alice.transfer(1000.00, carol);
        System.out.printf("After transfer: Alice=%.2f, Carol=%.2f%n",
                alice.getBalance(), carol.getBalance());

        // ---- Freeze account ----
        System.out.println("\n=== Frozen Account ===");
        bob.freeze();
        try {
            bob.deposit(100.00);
        } catch (AccountFrozenException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        bob.unfreeze();
        bob.deposit(100.00);
        System.out.println("Bob unfrozen and deposited 100. Balance: " + bob.getBalance());

        // ---- Interest ----
        bank.applyAllInterest();

        // ---- Statements ----
        System.out.println("\n=== Account Statements ===");
        alice.printStatement();
        dave.printStatement();

        // ---- Bank summary ----
        bank.printSummary();
    }
}
