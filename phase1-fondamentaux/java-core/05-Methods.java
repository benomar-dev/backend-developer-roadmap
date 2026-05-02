/**
 * 05 - Methods: declaration, parameters, return values, overloading, recursion
 *
 * Topics covered:
 *  - Method declaration and calling
 *  - Parameters and return types
 *  - void vs value-returning methods
 *  - Method overloading
 *  - Recursion
 *  - Variable-length arguments (varargs)
 *  - Exercises: factorial, power, GCD, isPalindrome
 */
public class Methods {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. SIMPLE METHOD CALLS
        // ----------------------------------------------------------------
        System.out.println("=== Simple Methods ===");
        greet("Alice");
        greet("Bob");

        int result = add(10, 25);
        System.out.println("10 + 25 = " + result);

        double circleArea = calculateCircleArea(5.0);
        System.out.printf("Area of circle with radius 5 = %.2f%n", circleArea);

        // ----------------------------------------------------------------
        // 2. MULTIPLE PARAMETERS AND TYPES
        // ----------------------------------------------------------------
        System.out.println("\n=== Multiple Parameters ===");
        System.out.println("Max of 12, 7, 19: " + max(12, 7, 19));

        double bmi = calculateBMI(70.0, 1.75);
        System.out.printf("BMI (70kg, 1.75m) = %.2f -> %s%n", bmi, bmiCategory(bmi));

        // ----------------------------------------------------------------
        // 3. METHOD OVERLOADING
        // ----------------------------------------------------------------
        System.out.println("\n=== Method Overloading ===");

        // Same method name, different parameter types/count
        System.out.println("area(5)         = " + area(5));          // square
        System.out.println("area(4, 6)      = " + area(4, 6));       // rectangle
        System.out.println("area(3.0)       = " + area(3.0));        // circle
        System.out.println("area(3.0, 4.0)  = " + area(3.0, 4.0));  // triangle

        System.out.println("print(42)     -> "); print(42);
        System.out.println("print(3.14)   -> "); print(3.14);
        System.out.println("print(\"Hi\") -> "); print("Hi");

        // ----------------------------------------------------------------
        // 4. RECURSION
        // ----------------------------------------------------------------
        System.out.println("\n=== Recursion ===");

        // Factorial
        for (int i = 0; i <= 10; i++) {
            System.out.println(i + "! = " + factorial(i));
        }

        // Fibonacci (recursive)
        System.out.println("\nFibonacci (recursive):");
        for (int i = 0; i <= 10; i++) {
            System.out.print(fibonacci(i) + " ");
        }
        System.out.println();

        // Power
        System.out.println("\n2^10 = " + power(2, 10));
        System.out.println("3^5  = " + power(3, 5));

        // GCD (Euclidean algorithm)
        System.out.println("\nGCD(48, 18) = " + gcd(48, 18));
        System.out.println("GCD(100, 75) = " + gcd(100, 75));

        // ----------------------------------------------------------------
        // 5. VARARGS (variable-length arguments)
        // ----------------------------------------------------------------
        System.out.println("\n=== Varargs ===");
        System.out.println("sum(1,2,3)      = " + sum(1, 2, 3));
        System.out.println("sum(10,20)      = " + sum(10, 20));
        System.out.println("sum(5,5,5,5,5)  = " + sum(5, 5, 5, 5, 5));

        // ----------------------------------------------------------------
        // 6. EXERCISES
        // ----------------------------------------------------------------
        System.out.println("\n=== Exercises ===");

        // isPalindrome
        String[] words = {"racecar", "hello", "level", "java", "madam"};
        for (String w : words) {
            System.out.println("isPalindrome(\"" + w + "\") = " + isPalindrome(w));
        }

        // Reverse string recursively
        System.out.println("\nreverseString(\"Hello\") = " + reverseString("Hello"));

        // Binary search
        int[] sorted = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
        System.out.println("\nbinarySearch(23) index = " + binarySearch(sorted, 23, 0, sorted.length - 1));
        System.out.println("binarySearch(99) index = " + binarySearch(sorted, 99, 0, sorted.length - 1));

        // Convert decimal to binary recursively
        System.out.println("\ndecimalToBinary(10)  = " + decimalToBinary(10));
        System.out.println("decimalToBinary(255) = " + decimalToBinary(255));
    }

    // ----------------------------------------------------------------
    // BASIC METHODS
    // ----------------------------------------------------------------

    /** Prints a greeting – void, one parameter */
    static void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }

    /** Returns the sum of two integers */
    static int add(int a, int b) {
        return a + b;
    }

    /** Returns the area of a circle given its radius */
    static double calculateCircleArea(double radius) {
        return Math.PI * radius * radius;
    }

    /** Returns the largest of three integers */
    static int max(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    /** Calculates Body Mass Index */
    static double calculateBMI(double weightKg, double heightM) {
        return weightKg / (heightM * heightM);
    }

    /** Returns a BMI category string */
    static String bmiCategory(double bmi) {
        if (bmi < 18.5)      return "Underweight";
        else if (bmi < 25.0) return "Normal weight";
        else if (bmi < 30.0) return "Overweight";
        else                 return "Obese";
    }

    // ----------------------------------------------------------------
    // METHOD OVERLOADING – area
    // ----------------------------------------------------------------
    static int    area(int side)                  { return side * side; }
    static int    area(int length, int width)     { return length * width; }
    static double area(double radius)             { return Math.PI * radius * radius; }
    static double area(double base, double height){ return 0.5 * base * height; }

    // METHOD OVERLOADING – print
    static void print(int    v) { System.out.println("int:    " + v); }
    static void print(double v) { System.out.println("double: " + v); }
    static void print(String v) { System.out.println("String: " + v); }

    // ----------------------------------------------------------------
    // RECURSION
    // ----------------------------------------------------------------

    /** Calculates n! recursively. Returns 1 for n <= 1. */
    static long factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    /** Returns the nth Fibonacci number (0-indexed) using recursion. */
    static int fibonacci(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /** Raises base to the exponent power recursively. */
    static long power(int base, int exponent) {
        if (exponent == 0) return 1;
        return base * power(base, exponent - 1);
    }

    /** Greatest Common Divisor using the Euclidean algorithm (recursive). */
    static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    // ----------------------------------------------------------------
    // VARARGS
    // ----------------------------------------------------------------

    /** Sums any number of integer arguments */
    static int sum(int... numbers) {
        int total = 0;
        for (int n : numbers) total += n;
        return total;
    }

    // ----------------------------------------------------------------
    // EXERCISES
    // ----------------------------------------------------------------

    /** Returns true if the given string is a palindrome (case-insensitive). */
    static boolean isPalindrome(String s) {
        s = s.toLowerCase();
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }

    /** Reverses a string using recursion. */
    static String reverseString(String s) {
        if (s.isEmpty()) return s;
        return reverseString(s.substring(1)) + s.charAt(0);
    }

    /**
     * Binary search (recursive).
     * Returns the index of target in sorted array arr[left..right],
     * or -1 if not found.
     */
    static int binarySearch(int[] arr, int target, int left, int right) {
        if (left > right) return -1;
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        if (arr[mid] < target)  return binarySearch(arr, target, mid + 1, right);
        return binarySearch(arr, target, left, mid - 1);
    }

    /**
     * Converts a positive decimal integer to its binary string representation
     * using recursion.
     */
    static String decimalToBinary(int n) {
        if (n == 0) return "0";
        if (n == 1) return "1";
        return decimalToBinary(n / 2) + (n % 2);
    }
}
