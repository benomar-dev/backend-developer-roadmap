/**
 * 07 - Inheritance: extends, super, method overriding, polymorphism
 *
 * Topics covered:
 *  - Defining a superclass (parent class)
 *  - Extending with subclasses (child classes)
 *  - Using 'super' to call parent constructors and methods
 *  - Method overriding and the @Override annotation
 *  - Polymorphism (treating subclasses as superclass type)
 *  - instanceof operator
 *  - final classes and methods
 *
 * Example hierarchy: Shape -> Circle, Rectangle, Triangle
 */

// ============================================================
// Abstract-style superclass: Shape
// ============================================================
class Shape {

    private String color;
    private boolean filled;

    // Constructor
    Shape(String color, boolean filled) {
        this.color  = color;
        this.filled = filled;
    }

    // Default constructor
    Shape() {
        this("Unknown", false);
    }

    // Getters / Setters
    public String  getColor()  { return color; }
    public boolean isFilled()  { return filled; }
    public void setColor(String color)   { this.color  = color; }
    public void setFilled(boolean filled){ this.filled = filled; }

    // Methods to be overridden (provide sensible defaults)
    public double getArea()      { return 0; }
    public double getPerimeter() { return 0; }

    @Override
    public String toString() {
        return getClass().getSimpleName()
             + "[color=" + color + ", filled=" + filled + "]";
    }
}

// ============================================================
// Subclass: Circle
// ============================================================
class Circle extends Shape {

    private double radius;

    Circle(String color, boolean filled, double radius) {
        super(color, filled);  // call Shape's constructor
        this.radius = radius;
    }

    Circle(double radius) {
        this("Unknown", false, radius);
    }

    public double getRadius() { return radius; }
    public void setRadius(double radius) {
        if (radius <= 0) throw new IllegalArgumentException("Radius must be positive");
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return super.toString() + ", radius=" + radius;
    }
}

// ============================================================
// Subclass: Rectangle
// ============================================================
class Rectangle extends Shape {

    private double width;
    private double height;

    Rectangle(String color, boolean filled, double width, double height) {
        super(color, filled);
        this.width  = width;
        this.height = height;
    }

    Rectangle(double width, double height) {
        this("Unknown", false, width, height);
    }

    public double getWidth()  { return width; }
    public double getHeight() { return height; }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public String toString() {
        return super.toString() + ", width=" + width + ", height=" + height;
    }
}

// ============================================================
// Subclass of Rectangle: Square (is-a Rectangle)
// ============================================================
class Square extends Rectangle {

    Square(String color, boolean filled, double side) {
        super(color, filled, side, side);
    }

    Square(double side) {
        super(side, side);
    }

    public double getSide() { return getWidth(); }

    /** Keeps width and height in sync */
    public void setSide(double side) {
        // Cannot directly set private width/height; would use setters if available
    }

    @Override
    public String toString() {
        return "Square[color=" + getColor()
             + ", filled=" + isFilled()
             + ", side=" + getSide() + "]";
    }
}

// ============================================================
// Subclass: Triangle
// ============================================================
class Triangle extends Shape {

    private double sideA;
    private double sideB;
    private double sideC;

    Triangle(String color, boolean filled, double a, double b, double c) {
        super(color, filled);
        this.sideA = a;
        this.sideB = b;
        this.sideC = c;
    }

    @Override
    public double getPerimeter() {
        return sideA + sideB + sideC;
    }

    /** Heron's formula */
    @Override
    public double getArea() {
        double s = getPerimeter() / 2.0;  // semi-perimeter
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }

    @Override
    public String toString() {
        return super.toString()
             + String.format(", sides=[%.1f, %.1f, %.1f]", sideA, sideB, sideC);
    }
}

// ============================================================
// Another hierarchy: Employee -> Manager, Developer
// ============================================================
class Employee {

    protected String name;
    protected double salary;

    Employee(String name, double salary) {
        this.name   = name;
        this.salary = salary;
    }

    public String getName()   { return name; }
    public double getSalary() { return salary; }

    /** To be overridden in subclasses */
    public String getRole() { return "Employee"; }

    public String describe() {
        return getRole() + " " + name + " earns " + salary;
    }

    @Override
    public String toString() {
        return describe();
    }
}

class Manager extends Employee {

    private int teamSize;

    Manager(String name, double salary, int teamSize) {
        super(name, salary);
        this.teamSize = teamSize;
    }

    @Override
    public String getRole() { return "Manager"; }

    @Override
    public String describe() {
        return super.describe() + " and manages " + teamSize + " people";
    }
}

class Developer extends Employee {

    private String language;

    Developer(String name, double salary, String language) {
        super(name, salary);
        this.language = language;
    }

    @Override
    public String getRole() { return "Developer"; }

    @Override
    public String describe() {
        return super.describe() + " and codes in " + language;
    }
}

// ============================================================
// Main
// ============================================================
public class Inheritance {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. BASIC INHERITANCE + toString
        // ----------------------------------------------------------------
        System.out.println("=== Shape Hierarchy ===");

        Circle    c = new Circle("Red",   true,  5.0);
        Rectangle r = new Rectangle("Blue", false, 4.0, 6.0);
        Square    s = new Square("Green", true,  3.0);
        Triangle  t = new Triangle("Yellow", false, 3.0, 4.0, 5.0);

        System.out.println(c);
        System.out.printf("  Area=%.4f  Perimeter=%.4f%n", c.getArea(), c.getPerimeter());

        System.out.println(r);
        System.out.printf("  Area=%.4f  Perimeter=%.4f%n", r.getArea(), r.getPerimeter());

        System.out.println(s);
        System.out.printf("  Area=%.4f  Perimeter=%.4f%n", s.getArea(), s.getPerimeter());

        System.out.println(t);
        System.out.printf("  Area=%.4f  Perimeter=%.4f%n", t.getArea(), t.getPerimeter());

        // ----------------------------------------------------------------
        // 2. POLYMORPHISM – treat all shapes uniformly
        // ----------------------------------------------------------------
        System.out.println("\n=== Polymorphism ===");

        Shape[] shapes = { c, r, s, t,
                           new Circle(2.5),
                           new Rectangle(10, 3) };

        double totalArea = 0;
        for (Shape shape : shapes) {
            double area = shape.getArea();
            totalArea += area;
            System.out.printf("%-40s area=%.2f%n", shape, area);
        }
        System.out.printf("Total area of all shapes: %.2f%n", totalArea);

        // ----------------------------------------------------------------
        // 3. instanceof OPERATOR
        // ----------------------------------------------------------------
        System.out.println("\n=== instanceof ===");

        for (Shape shape : shapes) {
            if (shape instanceof Square sq) {
                // Pattern matching instanceof (Java 16+)
                System.out.println("Found a Square with side: " + sq.getSide());
            } else if (shape instanceof Rectangle rec) {
                System.out.println("Found a Rectangle " + rec.getWidth() + "x" + rec.getHeight());
            } else if (shape instanceof Circle ci) {
                System.out.println("Found a Circle with radius: " + ci.getRadius());
            } else {
                System.out.println("Found a: " + shape.getClass().getSimpleName());
            }
        }

        // ----------------------------------------------------------------
        // 4. SUPER KEYWORD
        // ----------------------------------------------------------------
        System.out.println("\n=== Using super ===");

        Manager   mgr = new Manager("Alice", 85000, 10);
        Developer dev = new Developer("Bob",   72000, "Java");

        System.out.println(mgr.describe());  // calls overridden + super.describe()
        System.out.println(dev.describe());

        // ----------------------------------------------------------------
        // 5. POLYMORPHISM WITH EMPLOYEE HIERARCHY
        // ----------------------------------------------------------------
        System.out.println("\n=== Employee Polymorphism ===");

        Employee[] team = {
            new Employee("Charlie", 50000),
            new Manager("Diana",   90000, 5),
            new Developer("Eve",    75000, "Python"),
            new Manager("Frank",   95000, 12),
            new Developer("Grace",  68000, "Go"),
        };

        double payroll = 0;
        for (Employee emp : team) {
            System.out.println("[" + emp.getRole() + "] " + emp.describe());
            payroll += emp.getSalary();
        }
        System.out.printf("Total payroll: %.2f%n", payroll);
    }
}
