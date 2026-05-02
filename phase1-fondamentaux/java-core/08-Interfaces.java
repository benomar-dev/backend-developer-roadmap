/**
 * 08 - Interfaces and Abstract Classes
 *
 * Topics covered:
 *  - Defining and implementing interfaces
 *  - Multiple interface implementation
 *  - Default and static interface methods (Java 8+)
 *  - Functional interfaces and lambda expressions (preview)
 *  - Abstract classes vs interfaces – when to use each
 *  - The Comparable and Comparator interfaces
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// ============================================================
// INTERFACES
// ============================================================

/** Anything that can be drawn on screen */
interface Drawable {
    void draw();                            // abstract – must be implemented
    default String getDescription() {       // default – optional to override
        return "A drawable object";
    }
    static String getLibraryName() {        // static – called on the interface
        return "JavaDraw 1.0";
    }
}

/** Anything that can be resized */
interface Resizable {
    void resize(double factor);
    double getSize();
}

/** Anything that can be serialised to a text format */
interface Serializable {
    String serialize();
    static <T> T deserialize(String data, Class<T> type) {
        throw new UnsupportedOperationException("Implement in concrete class");
    }
}

// ============================================================
// ABSTRACT CLASS
// ============================================================

/**
 * Abstract class provides partial implementation.
 * Subclasses must implement calculateArea() and calculatePerimeter().
 */
abstract class AbstractShape implements Drawable, Resizable {

    private String color;
    private double x, y;   // position

    AbstractShape(String color, double x, double y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    // Concrete method (shared behaviour)
    public void moveTo(double newX, double newY) {
        this.x = newX;
        this.y = newY;
        System.out.printf("Moved %s to (%.1f, %.1f)%n", getClass().getSimpleName(), newX, newY);
    }

    // Getters
    public String getColor() { return color; }
    public double getX()     { return x; }
    public double getY()     { return y; }

    // Abstract methods – subclasses MUST implement these
    public abstract double calculateArea();
    public abstract double calculatePerimeter();

    // Provide sensible default for getSize() (from Resizable)
    @Override
    public double getSize() { return calculateArea(); }

    // Provide a shared draw template
    @Override
    public void draw() {
        System.out.printf("Drawing %s [color=%s, area=%.2f, at=(%.1f,%.1f)]%n",
                getClass().getSimpleName(), color, calculateArea(), x, y);
    }

    @Override
    public String getDescription() {
        return color + " " + getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return String.format("%s[color=%s, area=%.2f, perimeter=%.2f]",
                getClass().getSimpleName(), color, calculateArea(), calculatePerimeter());
    }
}

// ============================================================
// Concrete subclass: DrawableCircle
// ============================================================
class DrawableCircle extends AbstractShape implements Serializable {

    private double radius;

    DrawableCircle(String color, double x, double y, double radius) {
        super(color, x, y);
        this.radius = radius;
    }

    @Override
    public double calculateArea()      { return Math.PI * radius * radius; }

    @Override
    public double calculatePerimeter() { return 2 * Math.PI * radius; }

    @Override
    public void resize(double factor) {
        radius *= factor;
    }

    @Override
    public String serialize() {
        return String.format("circle:%s:%.2f:%.2f:%.2f", getColor(), getX(), getY(), radius);
    }

    public double getRadius() { return radius; }
}

// ============================================================
// Concrete subclass: DrawableRectangle
// ============================================================
class DrawableRectangle extends AbstractShape implements Serializable {

    private double width;
    private double height;

    DrawableRectangle(String color, double x, double y, double width, double height) {
        super(color, x, y);
        this.width  = width;
        this.height = height;
    }

    @Override
    public double calculateArea()      { return width * height; }

    @Override
    public double calculatePerimeter() { return 2 * (width + height); }

    @Override
    public void resize(double factor) {
        width  *= factor;
        height *= factor;
    }

    @Override
    public String serialize() {
        return String.format("rect:%s:%.2f:%.2f:%.2f:%.2f",
                getColor(), getX(), getY(), width, height);
    }
}

// ============================================================
// COMPARABLE – sorting by natural order
// ============================================================
class Student implements Comparable<Student> {

    private String name;
    private double gpa;

    Student(String name, double gpa) {
        this.name = name;
        this.gpa  = gpa;
    }

    public String getName() { return name; }
    public double getGpa()  { return gpa; }

    /** Natural ordering: by name alphabetically */
    @Override
    public int compareTo(Student other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        return String.format("Student{name='%s', gpa=%.2f}", name, gpa);
    }
}

// ============================================================
// FUNCTIONAL INTERFACE – single abstract method (SAM)
// ============================================================
@FunctionalInterface
interface MathOperation {
    double operate(double a, double b);
}

// ============================================================
// Main
// ============================================================
public class Interfaces {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. USING INTERFACES POLYMORPHICALLY
        // ----------------------------------------------------------------
        System.out.println("=== Drawable shapes ===");

        List<AbstractShape> shapes = new ArrayList<>();
        shapes.add(new DrawableCircle("Red",  0, 0, 5.0));
        shapes.add(new DrawableRectangle("Blue", 1, 1, 4.0, 6.0));
        shapes.add(new DrawableCircle("Green", 2, 3, 2.5));
        shapes.add(new DrawableRectangle("Yellow", 5, 5, 8.0, 3.0));

        for (AbstractShape s : shapes) {
            s.draw();
        }

        // ----------------------------------------------------------------
        // 2. DEFAULT AND STATIC INTERFACE METHODS
        // ----------------------------------------------------------------
        System.out.println("\n=== Default and Static interface methods ===");
        DrawableCircle dc = new DrawableCircle("Purple", 0, 0, 3.0);
        System.out.println(dc.getDescription());            // overridden default
        System.out.println(Drawable.getLibraryName());      // static method on interface

        // ----------------------------------------------------------------
        // 3. RESIZE (Resizable interface)
        // ----------------------------------------------------------------
        System.out.println("\n=== Resize ===");
        System.out.println("Before resize: " + dc);
        dc.resize(2.0);
        System.out.println("After ×2:      " + dc);
        dc.resize(0.5);
        System.out.println("After ×0.5:    " + dc);

        // ----------------------------------------------------------------
        // 4. MULTIPLE INTERFACES (Serializable)
        // ----------------------------------------------------------------
        System.out.println("\n=== Serialization ===");
        for (AbstractShape s : shapes) {
            if (s instanceof Serializable ser) {
                System.out.println(ser.serialize());
            }
        }

        // ----------------------------------------------------------------
        // 5. MOVE (concrete method from abstract class)
        // ----------------------------------------------------------------
        System.out.println("\n=== moveTo ===");
        shapes.get(0).moveTo(10.0, 15.0);

        // ----------------------------------------------------------------
        // 6. COMPARABLE – natural ordering
        // ----------------------------------------------------------------
        System.out.println("\n=== Comparable – sort students by name ===");

        List<Student> students = new ArrayList<>();
        students.add(new Student("Zara",    3.8));
        students.add(new Student("Alice",   3.5));
        students.add(new Student("Charlie", 3.9));
        students.add(new Student("Bob",     3.2));

        Collections.sort(students);   // uses compareTo
        students.forEach(System.out::println);

        // ----------------------------------------------------------------
        // 7. COMPARATOR – custom ordering without modifying the class
        // ----------------------------------------------------------------
        System.out.println("\n=== Comparator – sort students by GPA descending ===");

        Comparator<Student> byGpaDesc = Comparator.comparingDouble(Student::getGpa).reversed();
        students.sort(byGpaDesc);
        students.forEach(System.out::println);

        // Chained comparators
        System.out.println("\n=== Comparator – by GPA desc, then name asc ===");
        students.sort(
            Comparator.comparingDouble(Student::getGpa)
                      .reversed()
                      .thenComparing(Student::getName)
        );
        students.forEach(System.out::println);

        // ----------------------------------------------------------------
        // 8. FUNCTIONAL INTERFACE + LAMBDA EXPRESSIONS
        // ----------------------------------------------------------------
        System.out.println("\n=== Functional Interface + Lambdas ===");

        MathOperation add      = (a, b) -> a + b;
        MathOperation subtract = (a, b) -> a - b;
        MathOperation multiply = (a, b) -> a * b;
        MathOperation divide   = (a, b) -> {
            if (b == 0) throw new ArithmeticException("Division by zero");
            return a / b;
        };

        System.out.println("10 + 3 = "  + add.operate(10, 3));
        System.out.println("10 - 3 = "  + subtract.operate(10, 3));
        System.out.println("10 * 3 = "  + multiply.operate(10, 3));
        System.out.println("10 / 3 = "  + String.format("%.4f", divide.operate(10, 3)));

        // Passing a functional interface as a parameter
        System.out.println("\nUsing method reference (Math::pow):");
        MathOperation power = Math::pow;
        System.out.println("2^8 = " + power.operate(2, 8));
    }
}
