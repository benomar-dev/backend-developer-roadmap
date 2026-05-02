/**
 * 04 - Loops: for, while, do-while, enhanced for
 *
 * Topics covered:
 *  - Classic for loop
 *  - while loop
 *  - do-while loop
 *  - Enhanced for-each loop
 *  - break and continue
 *  - Nested loops (multiplication table)
 *  - Exercises: Fibonacci sequence, prime numbers, array reversal
 */
public class Loops {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. CLASSIC FOR LOOP
        // ----------------------------------------------------------------
        System.out.println("=== Classic for loop ===");

        // Count 1 to 5
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Count down from 10 to 1
        for (int i = 10; i >= 1; i--) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Sum of first N natural numbers
        int n = 10;
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        System.out.println("Sum 1 to " + n + " = " + sum);

        // ----------------------------------------------------------------
        // 2. WHILE LOOP
        // ----------------------------------------------------------------
        System.out.println("\n=== while loop ===");

        int count = 1;
        while (count <= 5) {
            System.out.print(count + " ");
            count++;
        }
        System.out.println();

        // Collatz conjecture: reach 1 from any positive integer
        int num = 27;
        int steps = 0;
        System.out.print("Collatz from " + num + ": ");
        while (num != 1) {
            if (num % 2 == 0) {
                num /= 2;
            } else {
                num = 3 * num + 1;
            }
            steps++;
        }
        System.out.println("reached 1 in " + steps + " steps.");

        // ----------------------------------------------------------------
        // 3. DO-WHILE LOOP
        // ----------------------------------------------------------------
        System.out.println("\n=== do-while loop ===");

        // Always executes the body at least once
        int value = 1;
        do {
            System.out.print(value + " ");
            value++;
        } while (value <= 5);
        System.out.println();

        // Digit sum using do-while
        int number = 12345;
        int digitSum = 0;
        int temp = Math.abs(number);
        do {
            digitSum += temp % 10;
            temp /= 10;
        } while (temp > 0);
        System.out.println("Digit sum of " + number + " = " + digitSum);

        // ----------------------------------------------------------------
        // 4. ENHANCED FOR-EACH LOOP
        // ----------------------------------------------------------------
        System.out.println("\n=== Enhanced for-each loop ===");

        int[] scores = {85, 92, 78, 95, 61, 88};
        int total = 0;
        for (int score : scores) {
            total += score;
            System.out.print(score + " ");
        }
        System.out.println();
        System.out.println("Average score: " + (double) total / scores.length);

        String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Elderberry"};
        for (String fruit : fruits) {
            System.out.println("Fruit: " + fruit);
        }

        // ----------------------------------------------------------------
        // 5. BREAK AND CONTINUE
        // ----------------------------------------------------------------
        System.out.println("\n=== break and continue ===");

        // break: exit the loop early
        System.out.print("Break at 5: ");
        for (int i = 1; i <= 10; i++) {
            if (i == 6) break;
            System.out.print(i + " ");
        }
        System.out.println();

        // continue: skip current iteration
        System.out.print("Skip even numbers: ");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) continue;
            System.out.print(i + " ");
        }
        System.out.println();

        // Labelled break for nested loops
        System.out.print("Labelled break (stop outer at i=2,j=3): ");
        outer:
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 3) break outer;
                System.out.print("(" + i + "," + j + ") ");
            }
        }
        System.out.println();

        // ----------------------------------------------------------------
        // 6. NESTED LOOPS – Multiplication Table
        // ----------------------------------------------------------------
        System.out.println("\n=== Multiplication Table (1-5) ===");
        for (int row = 1; row <= 5; row++) {
            for (int col = 1; col <= 5; col++) {
                System.out.printf("%4d", row * col);
            }
            System.out.println();
        }

        // ----------------------------------------------------------------
        // 7. EXERCISES
        // ----------------------------------------------------------------
        System.out.println("\n=== Exercise: Fibonacci Sequence ===");
        printFibonacci(15);   // print Fibonacci numbers up to 15

        System.out.println("\n=== Exercise: Prime Numbers up to 50 ===");
        printPrimes(50);

        System.out.println("\n=== Exercise: Reverse an Array ===");
        int[] original = {1, 2, 3, 4, 5};
        printArray("Original", original);
        reverseArray(original);
        printArray("Reversed", original);

        System.out.println("\n=== Exercise: Star Patterns ===");
        printRightTriangle(5);
        System.out.println();
        printPyramid(4);
    }

    // ----------------------------------------------------------------
    // EXERCISE METHODS
    // ----------------------------------------------------------------

    /** Prints all Fibonacci numbers less than or equal to limit */
    static void printFibonacci(int limit) {
        int a = 0, b = 1;
        System.out.print("Fibonacci up to " + limit + ": ");
        while (a <= limit) {
            System.out.print(a + " ");
            int next = a + b;
            a = b;
            b = next;
        }
        System.out.println();
    }

    /** Prints all prime numbers up to max */
    static void printPrimes(int max) {
        System.out.print("Primes up to " + max + ": ");
        for (int i = 2; i <= max; i++) {
            if (isPrime(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    /** Reverses an array in-place */
    static void reverseArray(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int tmp   = arr[left];
            arr[left] = arr[right];
            arr[right] = tmp;
            left++;
            right--;
        }
    }

    static void printArray(String label, int[] arr) {
        System.out.print(label + ": ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println();
    }

    /** Prints a right-angled triangle of stars with given height */
    static void printRightTriangle(int height) {
        System.out.println("Right Triangle (height=" + height + "):");
        for (int i = 1; i <= height; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }

    /** Prints a centred pyramid of stars with given number of rows */
    static void printPyramid(int rows) {
        System.out.println("Pyramid (rows=" + rows + "):");
        for (int i = 1; i <= rows; i++) {
            // Leading spaces
            for (int s = 0; s < rows - i; s++) {
                System.out.print(" ");
            }
            // Stars
            for (int j = 1; j <= (2 * i - 1); j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
