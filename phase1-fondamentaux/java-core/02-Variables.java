/**
 * 02 - Variables, Data Types, Wrappers, Type Casting, and Operators
 *
 * Topics covered:
 *  - Primitive types: byte, short, int, long, float, double, char, boolean
 *  - Wrapper classes: Integer, Double, Character, Boolean, etc.
 *  - String and common String methods
 *  - Type casting (implicit widening / explicit narrowing)
 *  - Arithmetic, relational, logical, and bitwise operators
 */
public class Variables {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. PRIMITIVE TYPES
        // ----------------------------------------------------------------
        byte   myByte    = 100;                // 8-bit  (-128 to 127)
        short  myShort   = 30_000;             // 16-bit (-32 768 to 32 767)
        int    myInt     = 2_000_000;          // 32-bit – most common integer type
        long   myLong    = 9_000_000_000L;     // 64-bit – suffix L required
        float  myFloat   = 3.14f;              // 32-bit decimal – suffix f required
        double myDouble  = 3.141592653589793;  // 64-bit decimal – default decimal type
        char   myChar    = 'A';                // 16-bit Unicode character
        boolean myBool   = true;               // true / false

        System.out.println("=== Primitive Types ===");
        System.out.println("byte:    " + myByte);
        System.out.println("short:   " + myShort);
        System.out.println("int:     " + myInt);
        System.out.println("long:    " + myLong);
        System.out.println("float:   " + myFloat);
        System.out.println("double:  " + myDouble);
        System.out.println("char:    " + myChar);
        System.out.println("boolean: " + myBool);

        // ----------------------------------------------------------------
        // 2. WRAPPER CLASSES (autoboxing / unboxing)
        // ----------------------------------------------------------------
        Integer  wrappedInt    = 42;           // autoboxing: int -> Integer
        Double   wrappedDouble = 2.71828;
        Character wrappedChar  = 'Z';
        Boolean  wrappedBool   = false;

        int unboxed = wrappedInt;              // unboxing: Integer -> int
        System.out.println("\n=== Wrapper Classes ===");
        System.out.println("Integer max value: " + Integer.MAX_VALUE);
        System.out.println("Integer min value: " + Integer.MIN_VALUE);
        System.out.println("Parse string to int: " + Integer.parseInt("123"));
        System.out.println("Convert int to binary: " + Integer.toBinaryString(255));
        System.out.println("Unboxed value: " + unboxed);

        // ----------------------------------------------------------------
        // 3. STRING
        // ----------------------------------------------------------------
        String greeting = "Hello, World!";
        String name     = "  Java  ";

        System.out.println("\n=== String ===");
        System.out.println("Length:          " + greeting.length());
        System.out.println("Uppercase:       " + greeting.toUpperCase());
        System.out.println("Lowercase:       " + greeting.toLowerCase());
        System.out.println("Substring(0,5):  " + greeting.substring(0, 5));
        System.out.println("Contains 'World':" + greeting.contains("World"));
        System.out.println("Replace:         " + greeting.replace("World", "Java"));
        System.out.println("Trim:            '" + name.trim() + "'");
        System.out.println("Starts with 'He':" + greeting.startsWith("He"));
        System.out.println("IndexOf 'W':     " + greeting.indexOf('W'));
        System.out.println("CharAt(7):       " + greeting.charAt(7));
        System.out.println("Split by ',':    " + java.util.Arrays.toString(greeting.split(",")));

        // String comparison
        String s1 = new String("java");
        String s2 = new String("java");
        System.out.println("s1 == s2 (ref):  " + (s1 == s2));       // false – different objects
        System.out.println("s1.equals(s2):   " + s1.equals(s2));     // true  – same content

        // StringBuilder for mutable strings
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append(", ");
        sb.append("World!");
        System.out.println("StringBuilder:   " + sb.toString());

        // ----------------------------------------------------------------
        // 4. TYPE CASTING
        // ----------------------------------------------------------------
        System.out.println("\n=== Type Casting ===");

        // Implicit (widening) – no data loss
        int    i      = 150;
        long   l      = i;       // int  -> long  (widening)
        double d      = l;       // long -> double (widening)
        System.out.println("Widening int->long->double: " + d);

        // Explicit (narrowing) – possible data loss
        double pi     = 3.99;
        int    piInt  = (int) pi;   // decimal part is truncated, NOT rounded
        System.out.println("Narrowing double->int (3.99): " + piInt);  // 3

        long   bigNum = 1_000_000_000_000L;
        int    small  = (int) bigNum;           // data loss due to overflow
        System.out.println("Narrowing long->int overflow: " + small);

        // char <-> int
        char c   = 'A';
        int  code = c;            // widening: char -> int (Unicode code point)
        char back = (char)(code + 1);
        System.out.println("'A' as int: " + code + ", next char: " + back);

        // ----------------------------------------------------------------
        // 5. OPERATORS
        // ----------------------------------------------------------------
        System.out.println("\n=== Operators ===");

        // Arithmetic
        int a = 17, b = 5;
        System.out.println("a + b  = " + (a + b));
        System.out.println("a - b  = " + (a - b));
        System.out.println("a * b  = " + (a * b));
        System.out.println("a / b  = " + (a / b));   // integer division
        System.out.println("a % b  = " + (a % b));   // modulus (remainder)
        System.out.println("a++ (post): " + (a++));   // use then increment
        System.out.println("After a++, a = " + a);
        System.out.println("++a (pre):  " + (++a));   // increment then use

        // Relational
        int x = 10, y = 20;
        System.out.println("\nx == y: " + (x == y));
        System.out.println("x != y: " + (x != y));
        System.out.println("x <  y: " + (x < y));
        System.out.println("x >  y: " + (x > y));
        System.out.println("x <= y: " + (x <= y));
        System.out.println("x >= y: " + (x >= y));

        // Logical
        boolean p = true, q = false;
        System.out.println("\np && q (AND): " + (p && q));
        System.out.println("p || q (OR):  " + (p || q));
        System.out.println("!p     (NOT): " + (!p));

        // Bitwise
        int m = 0b1010, n = 0b1100;  // 10, 12
        System.out.println("\nm  = " + Integer.toBinaryString(m) + " (" + m + ")");
        System.out.println("n  = " + Integer.toBinaryString(n) + " (" + n + ")");
        System.out.println("m & n  (AND): " + Integer.toBinaryString(m & n)  + " = " + (m & n));
        System.out.println("m | n  (OR):  " + Integer.toBinaryString(m | n)  + " = " + (m | n));
        System.out.println("m ^ n  (XOR): " + Integer.toBinaryString(m ^ n)  + " = " + (m ^ n));
        System.out.println("~m     (NOT): " + (~m));
        System.out.println("m << 1 (left shift):  " + (m << 1));
        System.out.println("m >> 1 (right shift): " + (m >> 1));

        // Compound assignment
        int z = 10;
        z += 5;  System.out.println("\nz += 5  -> " + z);
        z -= 3;  System.out.println("z -= 3  -> " + z);
        z *= 2;  System.out.println("z *= 2  -> " + z);
        z /= 4;  System.out.println("z /= 4  -> " + z);
        z %= 3;  System.out.println("z %= 3  -> " + z);

        // Ternary
        int  age    = 20;
        String status = (age >= 18) ? "Adult" : "Minor";
        System.out.println("\nTernary: age=" + age + " -> " + status);
    }
}
