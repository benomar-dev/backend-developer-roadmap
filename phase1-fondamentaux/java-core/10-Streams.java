/**
 * 10 - Streams API, Lambda Expressions, and Optional
 *
 * Topics covered:
 *  - Creating streams (from collections, arrays, Stream.of, Stream.generate)
 *  - Intermediate operations: filter, map, flatMap, distinct, sorted, limit, skip, peek
 *  - Terminal operations: collect, forEach, reduce, count, anyMatch, allMatch, findFirst
 *  - Collectors: toList, toSet, toMap, groupingBy, joining, summarizingInt
 *  - Optional – avoiding NullPointerException
 *  - Method references (static, instance, constructor)
 */

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Streams {

    // Sample data class
    record Person(String name, int age, String city, double salary) {}

    public static void main(String[] args) {

        List<Person> people = List.of(
            new Person("Alice",   30, "Paris",      75_000),
            new Person("Bob",     25, "London",     55_000),
            new Person("Charlie", 35, "Paris",      90_000),
            new Person("Diana",   28, "New York",   80_000),
            new Person("Eve",     22, "London",     45_000),
            new Person("Frank",   40, "Paris",      110_000),
            new Person("Grace",   33, "New York",   95_000),
            new Person("Henry",   27, "Berlin",     60_000)
        );

        // ----------------------------------------------------------------
        // 1. CREATING STREAMS
        // ----------------------------------------------------------------
        System.out.println("=== Creating Streams ===");

        // From collection
        Stream<String> streamFromList = List.of("a", "b", "c").stream();
        System.out.print("From List:  "); streamFromList.forEach(s -> System.out.print(s + " "));
        System.out.println();

        // From array
        int[] arr = {1, 2, 3, 4, 5};
        IntStream intStream = Arrays.stream(arr);
        System.out.println("Sum from array: " + intStream.sum());

        // Stream.of
        Stream.of("X", "Y", "Z").forEach(s -> System.out.print(s + " "));
        System.out.println();

        // Stream.range / rangeClosed
        System.out.print("IntStream.range(1,6): ");
        IntStream.range(1, 6).forEach(i -> System.out.print(i + " "));
        System.out.println();

        // Infinite stream with limit
        System.out.print("First 5 random ints: ");
        new Random(42).ints(1, 100).limit(5)
                      .forEach(i -> System.out.print(i + " "));
        System.out.println();

        // ----------------------------------------------------------------
        // 2. FILTER, MAP, COLLECT
        // ----------------------------------------------------------------
        System.out.println("\n=== filter + map + collect ===");

        // People from Paris
        List<String> parisians = people.stream()
            .filter(p -> p.city().equals("Paris"))
            .map(Person::name)
            .sorted()
            .collect(Collectors.toList());
        System.out.println("People in Paris: " + parisians);

        // Names in uppercase of people under 30
        List<String> youngNames = people.stream()
            .filter(p -> p.age() < 30)
            .map(p -> p.name().toUpperCase())
            .sorted()
            .collect(Collectors.toList());
        System.out.println("Under-30 names (upper): " + youngNames);

        // ----------------------------------------------------------------
        // 3. FLATMAP
        // ----------------------------------------------------------------
        System.out.println("\n=== flatMap ===");

        List<List<Integer>> nested = List.of(
            List.of(1, 2, 3),
            List.of(4, 5),
            List.of(6, 7, 8, 9)
        );
        List<Integer> flat = nested.stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        System.out.println("Flattened: " + flat);

        // Word frequency using flatMap
        List<String> sentences = List.of("hello world", "java streams are great", "hello java");
        Map<String, Long> wordFreq = sentences.stream()
            .flatMap(s -> Arrays.stream(s.split(" ")))
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("Word frequency: " + new TreeMap<>(wordFreq));

        // ----------------------------------------------------------------
        // 4. REDUCE
        // ----------------------------------------------------------------
        System.out.println("\n=== reduce ===");

        // Sum
        int sum = IntStream.rangeClosed(1, 10)
            .reduce(0, Integer::sum);
        System.out.println("Sum 1..10 = " + sum);

        // Product (factorial of 5)
        long product = LongStream.rangeClosed(1, 5)
            .reduce(1, (a, b) -> a * b);
        System.out.println("5! = " + product);

        // Longest word
        Optional<String> longest = List.of("apple", "banana", "kiwi", "strawberry")
            .stream()
            .reduce((a, b) -> a.length() >= b.length() ? a : b);
        longest.ifPresent(w -> System.out.println("Longest word: " + w));

        // ----------------------------------------------------------------
        // 5. TERMINAL OPERATIONS: count, min, max, anyMatch, allMatch, findFirst
        // ----------------------------------------------------------------
        System.out.println("\n=== Terminal operations ===");

        long countHighEarners = people.stream()
            .filter(p -> p.salary() > 80_000)
            .count();
        System.out.println("People earning >80k: " + countHighEarners);

        OptionalDouble avgAge = people.stream()
            .mapToInt(Person::age)
            .average();
        System.out.printf("Average age: %.1f%n", avgAge.orElse(0));

        Optional<Person> youngestPerson = people.stream()
            .min(Comparator.comparingInt(Person::age));
        youngestPerson.ifPresent(p ->
            System.out.println("Youngest: " + p.name() + " (" + p.age() + ")"));

        Optional<Person> highestPaid = people.stream()
            .max(Comparator.comparingDouble(Person::salary));
        highestPaid.ifPresent(p ->
            System.out.println("Highest paid: " + p.name() + " ($" + p.salary() + ")"));

        boolean anyoneFromBerlin = people.stream().anyMatch(p -> p.city().equals("Berlin"));
        System.out.println("Anyone from Berlin: " + anyoneFromBerlin);

        boolean allAdults = people.stream().allMatch(p -> p.age() >= 18);
        System.out.println("All adults: " + allAdults);

        Optional<Person> firstNewYorker = people.stream()
            .filter(p -> p.city().equals("New York"))
            .findFirst();
        firstNewYorker.ifPresent(p -> System.out.println("First New Yorker: " + p.name()));

        // ----------------------------------------------------------------
        // 6. COLLECTORS
        // ----------------------------------------------------------------
        System.out.println("\n=== Collectors ===");

        // toMap
        Map<String, Double> nameSalary = people.stream()
            .collect(Collectors.toMap(Person::name, Person::salary));
        System.out.println("Name->Salary: " + new TreeMap<>(nameSalary));

        // groupingBy
        Map<String, List<Person>> byCity = people.stream()
            .collect(Collectors.groupingBy(Person::city));
        byCity.forEach((city, persons) ->
            System.out.println(city + ": " + persons.stream().map(Person::name).toList()));

        // counting per city
        Map<String, Long> countByCity = people.stream()
            .collect(Collectors.groupingBy(Person::city, Collectors.counting()));
        System.out.println("Count by city: " + new TreeMap<>(countByCity));

        // average salary by city
        Map<String, Double> avgSalaryByCity = people.stream()
            .collect(Collectors.groupingBy(Person::city,
                     Collectors.averagingDouble(Person::salary)));
        avgSalaryByCity.forEach((city, avg) ->
            System.out.printf("  %-10s avg salary: %.0f%n", city, avg));

        // joining
        String nameList = people.stream()
            .map(Person::name)
            .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Names: " + nameList);

        // statistics
        IntSummaryStatistics ageStats = people.stream()
            .collect(Collectors.summarizingInt(Person::age));
        System.out.println("Age stats: " + ageStats);

        // ----------------------------------------------------------------
        // 7. OPTIONAL
        // ----------------------------------------------------------------
        System.out.println("\n=== Optional ===");

        Optional<String> present = Optional.of("Hello");
        Optional<String> empty   = Optional.empty();
        Optional<String> nullable = Optional.ofNullable(null);

        System.out.println("present.get():          " + present.get());
        System.out.println("empty.isPresent():      " + empty.isPresent());
        System.out.println("empty.orElse(default):  " + empty.orElse("default"));
        System.out.println("nullable.orElse(n/a):   " + nullable.orElse("n/a"));
        System.out.println("empty.orElseGet(lambda):" + empty.orElseGet(() -> "generated value"));

        present.ifPresent(v -> System.out.println("present.ifPresent: " + v));

        String upper = present.map(String::toUpperCase).orElse("none");
        System.out.println("present.map(toUpper):   " + upper);

        Optional<String> filtered = present.filter(s -> s.startsWith("He"));
        System.out.println("filter(starts 'He'):    " + filtered.orElse("filtered out"));

        // Chaining optionals
        Optional<String> city = findPersonByName(people, "Diana")
            .map(Person::city);
        System.out.println("Diana's city: " + city.orElse("unknown"));

        Optional<String> missingCity = findPersonByName(people, "Zara")
            .map(Person::city);
        System.out.println("Zara's city: " + missingCity.orElse("unknown"));

        // ----------------------------------------------------------------
        // 8. METHOD REFERENCES
        // ----------------------------------------------------------------
        System.out.println("\n=== Method References ===");

        // Static method reference
        List<String> words = List.of("hello", "world", "java");
        words.stream()
             .map(String::toUpperCase)   // instance method ref on receiver
             .forEach(System.out::println);

        // Constructor reference
        List<String> nums = List.of("1", "2", "3", "4", "5");
        List<Integer> parsed = nums.stream()
            .map(Integer::parseInt)       // static method ref
            .collect(Collectors.toList());
        System.out.println("Parsed ints: " + parsed);
    }

    // Helper
    static Optional<Person> findPersonByName(List<Person> people, String name) {
        return people.stream()
            .filter(p -> p.name().equals(name))
            .findFirst();
    }
}
