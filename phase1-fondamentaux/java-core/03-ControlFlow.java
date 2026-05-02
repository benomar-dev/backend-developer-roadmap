/**
 * 03 - Control Flow: if/else, switch, ternary operator
 *
 * Topics covered:
 *  - if / else if / else
 *  - Nested if statements
 *  - switch statement (classic and enhanced arrow-syntax)
 *  - Ternary operator
 *  - Practice: grade classifier, day-of-week printer, odd/even checker
 */
public class ControlFlow {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. IF / ELSE IF / ELSE
        // ----------------------------------------------------------------
        System.out.println("=== if / else if / else ===");

        int score = 75;

        if (score >= 90) {
            System.out.println("Grade: A (Excellent)");
        } else if (score >= 80) {
            System.out.println("Grade: B (Good)");
        } else if (score >= 70) {
            System.out.println("Grade: C (Average)");
        } else if (score >= 60) {
            System.out.println("Grade: D (Below Average)");
        } else {
            System.out.println("Grade: F (Failing)");
        }

        // ----------------------------------------------------------------
        // 2. NESTED IF
        // ----------------------------------------------------------------
        System.out.println("\n=== Nested if ===");

        int temperature = 28;
        boolean isRaining = false;

        if (temperature > 25) {
            if (!isRaining) {
                System.out.println("Great weather for outdoor activities!");
            } else {
                System.out.println("Warm but rainy – bring an umbrella.");
            }
        } else {
            System.out.println("It's a bit cold today.");
        }

        // ----------------------------------------------------------------
        // 3. CLASSIC SWITCH STATEMENT
        // ----------------------------------------------------------------
        System.out.println("\n=== Classic switch ===");

        int dayNumber = 3;
        String dayName;

        switch (dayNumber) {
            case 1:
                dayName = "Monday";
                break;
            case 2:
                dayName = "Tuesday";
                break;
            case 3:
                dayName = "Wednesday";
                break;
            case 4:
                dayName = "Thursday";
                break;
            case 5:
                dayName = "Friday";
                break;
            case 6:
                dayName = "Saturday";
                break;
            case 7:
                dayName = "Sunday";
                break;
            default:
                dayName = "Unknown";
        }
        System.out.println("Day " + dayNumber + " is: " + dayName);

        // Fall-through example (intentional: group weekdays vs weekend)
        System.out.print("Day type: ");
        switch (dayNumber) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                System.out.println("Weekday");
                break;
            case 6:
            case 7:
                System.out.println("Weekend");
                break;
            default:
                System.out.println("Invalid day number");
        }

        // ----------------------------------------------------------------
        // 4. SWITCH WITH STRING
        // ----------------------------------------------------------------
        System.out.println("\n=== Switch with String ===");

        String season = "Summer";
        switch (season) {
            case "Spring":
                System.out.println("Flowers are blooming.");
                break;
            case "Summer":
                System.out.println("It's hot outside!");
                break;
            case "Autumn":
                System.out.println("Leaves are falling.");
                break;
            case "Winter":
                System.out.println("Bundle up – it's cold!");
                break;
            default:
                System.out.println("Unknown season.");
        }

        // ----------------------------------------------------------------
        // 5. ENHANCED SWITCH (arrow syntax – Java 14+)
        // ----------------------------------------------------------------
        System.out.println("\n=== Enhanced switch (arrow syntax) ===");

        int month = 4;
        String monthName = switch (month) {
            case 1  -> "January";
            case 2  -> "February";
            case 3  -> "March";
            case 4  -> "April";
            case 5  -> "May";
            case 6  -> "June";
            case 7  -> "July";
            case 8  -> "August";
            case 9  -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "Invalid month";
        };
        System.out.println("Month " + month + " is: " + monthName);

        // Days in month using enhanced switch
        int daysInMonth = switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11           -> 30;
            case 2                     -> 28; // simplified, ignoring leap year
            default                    -> -1;
        };
        System.out.println("Days in month " + month + ": " + daysInMonth);

        // ----------------------------------------------------------------
        // 6. TERNARY OPERATOR
        // ----------------------------------------------------------------
        System.out.println("\n=== Ternary Operator ===");

        // Syntax: condition ? valueIfTrue : valueIfFalse
        int number = 7;
        String parity = (number % 2 == 0) ? "Even" : "Odd";
        System.out.println(number + " is " + parity);

        // Chained ternary (use sparingly – can reduce readability)
        int marks = 82;
        String grade = (marks >= 90) ? "A"
                     : (marks >= 80) ? "B"
                     : (marks >= 70) ? "C"
                     : (marks >= 60) ? "D"
                     : "F";
        System.out.println("Marks " + marks + " -> Grade: " + grade);

        // Ternary for absolute value
        int val = -42;
        int abs = (val >= 0) ? val : -val;
        System.out.println("Absolute value of " + val + ": " + abs);

        // ----------------------------------------------------------------
        // 7. EXERCISES
        // ----------------------------------------------------------------
        System.out.println("\n=== Exercises ===");

        // Exercise 1: Odd or Even checker
        checkOddEven(0);
        checkOddEven(7);
        checkOddEven(14);

        // Exercise 2: Traffic light
        trafficLight("RED");
        trafficLight("YELLOW");
        trafficLight("GREEN");

        // Exercise 3: FizzBuzz for a single number
        fizzBuzz(15);
        fizzBuzz(9);
        fizzBuzz(10);
        fizzBuzz(7);
    }

    // ----- Helper methods for exercises -----

    static void checkOddEven(int n) {
        if (n == 0) {
            System.out.println(n + " is neither odd nor even (it's zero)");
        } else if (n % 2 == 0) {
            System.out.println(n + " is Even");
        } else {
            System.out.println(n + " is Odd");
        }
    }

    static void trafficLight(String color) {
        String action = switch (color.toUpperCase()) {
            case "RED"    -> "STOP";
            case "YELLOW" -> "SLOW DOWN";
            case "GREEN"  -> "GO";
            default       -> "INVALID SIGNAL";
        };
        System.out.println("Traffic light " + color + " -> " + action);
    }

    static void fizzBuzz(int n) {
        if (n % 15 == 0) {
            System.out.println(n + " -> FizzBuzz");
        } else if (n % 3 == 0) {
            System.out.println(n + " -> Fizz");
        } else if (n % 5 == 0) {
            System.out.println(n + " -> Buzz");
        } else {
            System.out.println(n + " -> " + n);
        }
    }
}
