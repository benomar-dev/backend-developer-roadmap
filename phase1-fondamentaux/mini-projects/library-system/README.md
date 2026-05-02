# Library Management System

A Java mini-project demonstrating **OOP, Collections, and Encapsulation**.

## Features

- Add/remove books from catalogue
- Register library members
- Borrow and return books (with copy tracking)
- Search books by title, author, or ISBN
- Enforce borrow limits per member
- View full catalogue and member history

## How to Run

```bash
cd phase1-fondamentaux/mini-projects/library-system

# Compile
javac LibrarySystem.java

# Run
java LibrarySystem
```

## Concepts Demonstrated

| Concept | Where |
|---------|-------|
| Encapsulation | `Book`, `Member` – private fields + getters |
| Collections | `HashMap`, `ArrayList`, `Optional` |
| Streams API | Search methods, filter, count |
| Custom exceptions | `IllegalStateException` for borrow rules |
| OOP relationships | `Library` owns `Book` list and `Member` list |

## Class Diagram

```
Library
 ├── Map<String, Book>    (isbn -> Book)
 └── Map<String, Member>  (memberId -> Member)
         └── List<BorrowRecord>
```
