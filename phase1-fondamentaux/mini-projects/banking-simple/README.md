# Simple Banking System

A Java mini-project demonstrating **Inheritance, Abstract Classes, Exceptions, and Collections**.

## Features

- Multiple account types: **SavingsAccount** and **CheckingAccount**
- Deposit, withdraw, transfer between accounts
- Interest calculation (Savings only)
- Overdraft support (Checking accounts)
- Full transaction history per account
- Account freeze/unfreeze
- Bank-level statistics

## How to Run

```bash
cd phase1-fondamentaux/mini-projects/banking-simple

# Compile
javac BankingSystem.java

# Run
java BankingSystem
```

## Concepts Demonstrated

| Concept | Where |
|---------|-------|
| Abstract class | `Account` – shared behaviour, abstract `applyInterest()` |
| Inheritance | `SavingsAccount`, `CheckingAccount` extend `Account` |
| Encapsulation | Private fields, controlled access via methods |
| Custom exceptions | `InsufficientBalanceException`, `AccountFrozenException` |
| Collections | `Map<String, Account>`, `List<Transaction>` |
| Streams | Bank summary statistics |
| Enum | `Transaction.Type` |

## Class Diagram

```
Bank
 └── Map<String, Account>
           │
       Account (abstract)
      /           \
SavingsAccount  CheckingAccount
```
