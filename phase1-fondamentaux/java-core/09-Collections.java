/**
 * 09 - Collections Framework: List, Set, Map, Queue
 *
 * Topics covered:
 *  - ArrayList and LinkedList
 *  - HashSet and TreeSet
 *  - HashMap and TreeMap
 *  - ArrayDeque (as Stack and Queue)
 *  - Iterating collections
 *  - Collections utility class
 *  - Exercise: student name management with ArrayList
 */

import java.util.*;
import java.util.stream.Collectors;

public class Collections {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. ARRAYLIST
        // ----------------------------------------------------------------
        System.out.println("=== ArrayList ===");

        List<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Apple");        // duplicates allowed
        fruits.add(1, "Avocado");   // insert at index 1

        System.out.println("List: " + fruits);
        System.out.println("Size: " + fruits.size());
        System.out.println("Get index 2: " + fruits.get(2));
        System.out.println("Contains 'Banana': " + fruits.contains("Banana"));
        System.out.println("Index of 'Apple': " + fruits.indexOf("Apple"));

        fruits.remove("Apple");           // removes first occurrence
        fruits.set(0, "Apricot");         // replace element at index 0

        System.out.println("After remove+set: " + fruits);

        // Iterating
        System.out.print("forEach: ");
        fruits.forEach(f -> System.out.print(f + " "));
        System.out.println();

        System.out.print("Iterator: ");
        Iterator<String> it = fruits.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();

        // Sorting
        java.util.Collections.sort(fruits);
        System.out.println("Sorted: " + fruits);

        java.util.Collections.sort(fruits, java.util.Comparator.reverseOrder());
        System.out.println("Reverse sorted: " + fruits);

        // ----------------------------------------------------------------
        // 2. LINKEDLIST (also implements Deque)
        // ----------------------------------------------------------------
        System.out.println("\n=== LinkedList ===");

        LinkedList<Integer> linked = new LinkedList<>(List.of(3, 1, 4, 1, 5, 9, 2, 6));
        System.out.println("LinkedList: " + linked);

        linked.addFirst(0);
        linked.addLast(7);
        System.out.println("After addFirst(0) addLast(7): " + linked);
        System.out.println("peekFirst: " + linked.peekFirst());
        System.out.println("peekLast:  " + linked.peekLast());

        linked.removeFirst();
        linked.removeLast();
        System.out.println("After removeFirst + removeLast: " + linked);

        // ----------------------------------------------------------------
        // 3. HASHSET
        // ----------------------------------------------------------------
        System.out.println("\n=== HashSet ===");

        Set<String> colorSet = new HashSet<>();
        colorSet.add("Red");
        colorSet.add("Blue");
        colorSet.add("Green");
        colorSet.add("Red");     // duplicate – silently ignored
        colorSet.add("Yellow");

        System.out.println("HashSet (no guaranteed order): " + colorSet);
        System.out.println("Contains 'Blue': " + colorSet.contains("Blue"));
        System.out.println("Size: " + colorSet.size());

        // Set operations
        Set<Integer> setA = new HashSet<>(List.of(1, 2, 3, 4, 5));
        Set<Integer> setB = new HashSet<>(List.of(4, 5, 6, 7, 8));

        Set<Integer> union = new HashSet<>(setA);
        union.addAll(setB);
        System.out.println("Union " + setA + " | " + setB + " = " + union);

        Set<Integer> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);
        System.out.println("Intersection = " + intersection);

        Set<Integer> difference = new HashSet<>(setA);
        difference.removeAll(setB);
        System.out.println("Difference A - B = " + difference);

        // ----------------------------------------------------------------
        // 4. TREESET (sorted, no duplicates)
        // ----------------------------------------------------------------
        System.out.println("\n=== TreeSet ===");

        TreeSet<String> treeSet = new TreeSet<>(java.util.Comparator.naturalOrder());
        treeSet.addAll(List.of("Banana", "Apple", "Cherry", "Avocado", "Date"));

        System.out.println("TreeSet (sorted): " + treeSet);
        System.out.println("First: " + treeSet.first());
        System.out.println("Last:  " + treeSet.last());
        System.out.println("HeadSet before 'Cherry': " + treeSet.headSet("Cherry"));
        System.out.println("TailSet from 'Cherry':  " + treeSet.tailSet("Cherry"));

        // ----------------------------------------------------------------
        // 5. HASHMAP
        // ----------------------------------------------------------------
        System.out.println("\n=== HashMap ===");

        Map<String, Integer> wordCount = new HashMap<>();
        String sentence = "the quick brown fox jumps over the lazy dog the fox";
        for (String word : sentence.split(" ")) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        System.out.println("Word counts: " + wordCount);
        System.out.println("Count of 'the': " + wordCount.get("the"));
        System.out.println("Count of 'cat': " + wordCount.getOrDefault("cat", 0));

        // Iterating entries
        System.out.println("Entries:");
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            System.out.printf("  %-10s -> %d%n", entry.getKey(), entry.getValue());
        }

        // putIfAbsent, computeIfAbsent
        wordCount.putIfAbsent("cat", 1);
        wordCount.merge("fox", 10, Integer::sum);   // add 10 to existing count
        System.out.println("After merge 'fox' +10: " + wordCount.get("fox"));

        // ----------------------------------------------------------------
        // 6. TREEMAP (sorted by key)
        // ----------------------------------------------------------------
        System.out.println("\n=== TreeMap ===");

        TreeMap<String, Integer> scores = new TreeMap<>();
        scores.put("Charlie", 85);
        scores.put("Alice",   92);
        scores.put("Bob",     78);
        scores.put("Diana",   95);
        scores.put("Eve",     88);

        System.out.println("TreeMap (key-sorted): " + scores);
        System.out.println("firstKey: " + scores.firstKey());
        System.out.println("lastKey:  " + scores.lastKey());

        // ----------------------------------------------------------------
        // 7. ARRAYDEQUE – Stack and Queue
        // ----------------------------------------------------------------
        System.out.println("\n=== ArrayDeque as Queue (FIFO) ===");

        Queue<String> queue = new ArrayDeque<>();
        queue.offer("Task-1");
        queue.offer("Task-2");
        queue.offer("Task-3");
        System.out.println("Queue: " + queue);
        System.out.println("peek: " + queue.peek());
        System.out.println("poll: " + queue.poll());
        System.out.println("After poll: " + queue);

        System.out.println("\n=== ArrayDeque as Stack (LIFO) ===");
        Deque<String> stack = new ArrayDeque<>();
        stack.push("Page-1");
        stack.push("Page-2");
        stack.push("Page-3");
        System.out.println("Stack: " + stack);
        System.out.println("peek: " + stack.peek());
        System.out.println("pop:  " + stack.pop());
        System.out.println("After pop: " + stack);

        // ----------------------------------------------------------------
        // 8. COLLECTIONS UTILITY CLASS
        // ----------------------------------------------------------------
        System.out.println("\n=== Collections utility methods ===");

        List<Integer> numbers = new ArrayList<>(List.of(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5));
        System.out.println("Original:   " + numbers);
        System.out.println("Max:        " + java.util.Collections.max(numbers));
        System.out.println("Min:        " + java.util.Collections.min(numbers));
        System.out.println("Frequency 5:" + java.util.Collections.frequency(numbers, 5));

        java.util.Collections.sort(numbers);
        System.out.println("Sorted:     " + numbers);

        java.util.Collections.shuffle(numbers, new Random(42));
        System.out.println("Shuffled:   " + numbers);

        java.util.Collections.reverse(numbers);
        System.out.println("Reversed:   " + numbers);

        List<Integer> unmodifiable = java.util.Collections.unmodifiableList(numbers);
        try {
            unmodifiable.add(99);
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify unmodifiable list: " + e.getClass().getSimpleName());
        }

        // ----------------------------------------------------------------
        // 9. EXERCISE: Student Management with ArrayList
        // ----------------------------------------------------------------
        System.out.println("\n=== Exercise: Student Name Management ===");
        studentManagementExercise();
    }

    // ----------------------------------------------------------------
    // EXERCISE
    // ----------------------------------------------------------------
    static void studentManagementExercise() {
        List<String> students = new ArrayList<>();

        // Add students
        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Eve",
                          "Frank", "Grace", "Henry", "Ivy", "Jack"};
        for (String name : names) students.add(name);

        System.out.println("All students: " + students);

        // Sort alphabetically
        java.util.Collections.sort(students);
        System.out.println("Sorted:       " + students);

        // Find students whose names start with a specific letter
        char letter = 'A';
        List<String> filtered = students.stream()
            .filter(name -> name.startsWith(String.valueOf(letter)))
            .collect(Collectors.toList());
        System.out.println("Names starting with '" + letter + "': " + filtered);

        // Remove a student
        students.remove("Charlie");
        System.out.println("After removing Charlie: " + students);

        // Check if student exists
        System.out.println("Contains 'Alice': " + students.contains("Alice"));
        System.out.println("Contains 'Charlie': " + students.contains("Charlie"));

        // Count students
        System.out.println("Total students: " + students.size());

        // Use a Set to find unique first letters
        Set<Character> firstLetters = new TreeSet<>();
        for (String student : students) {
            firstLetters.add(student.charAt(0));
        }
        System.out.println("Unique first letters: " + firstLetters);
    }
}
