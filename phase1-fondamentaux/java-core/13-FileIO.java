/**
 * 13 - File I/O: reading/writing files, BufferedReader/Writer, NIO basics
 *
 * Topics covered:
 *  - Writing text with FileWriter and BufferedWriter
 *  - Reading text with FileReader and BufferedReader
 *  - Reading all lines with Files.readAllLines()
 *  - Writing with Files.write() and Files.writeString()
 *  - NIO Path and Files API
 *  - Walking a directory tree (Files.walk)
 *  - Appending to files
 *  - try-with-resources pattern for safe I/O
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

public class FileIO {

    // Use a temp directory so we don't pollute the repo
    private static final String BASE_DIR = System.getProperty("java.io.tmpdir") + "/java-fileio-demo";

    public static void main(String[] args) throws IOException {

        // Create working directory
        Path baseDir = Paths.get(BASE_DIR);
        Files.createDirectories(baseDir);
        System.out.println("Working directory: " + baseDir.toAbsolutePath());

        // ----------------------------------------------------------------
        // 1. WRITE WITH BufferedWriter (classic I/O)
        // ----------------------------------------------------------------
        System.out.println("\n=== Write with BufferedWriter ===");

        Path poem = baseDir.resolve("poem.txt");
        writeWithBufferedWriter(poem);
        System.out.println("Written: " + poem);

        // ----------------------------------------------------------------
        // 2. READ WITH BufferedReader (classic I/O)
        // ----------------------------------------------------------------
        System.out.println("\n=== Read with BufferedReader ===");
        readWithBufferedReader(poem);

        // ----------------------------------------------------------------
        // 3. NIO – Files.write() and Files.readAllLines()
        // ----------------------------------------------------------------
        System.out.println("\n=== NIO: Files.write / readAllLines ===");

        Path csvPath = baseDir.resolve("students.csv");
        writeCSV(csvPath);
        readCSVAndProcess(csvPath);

        // ----------------------------------------------------------------
        // 4. NIO – Files.writeString / Files.readString (Java 11+)
        // ----------------------------------------------------------------
        System.out.println("\n=== NIO: Files.writeString / readString ===");

        Path jsonPath = baseDir.resolve("config.json");
        String json = """
                {
                    "app": "MyApp",
                    "version": "1.0.0",
                    "debug": true
                }
                """;
        Files.writeString(jsonPath, json, StandardCharsets.UTF_8);
        System.out.println("Wrote config.json");

        String readJson = Files.readString(jsonPath, StandardCharsets.UTF_8);
        System.out.println("Read back:\n" + readJson);

        // ----------------------------------------------------------------
        // 5. APPEND TO FILE
        // ----------------------------------------------------------------
        System.out.println("=== Append to file ===");

        Path log = baseDir.resolve("app.log");
        appendToFile(log, "[INFO] Application started");
        appendToFile(log, "[INFO] Processing request #1");
        appendToFile(log, "[WARN] High memory usage detected");
        appendToFile(log, "[INFO] Request #1 completed");

        System.out.println("Log file content:");
        Files.readAllLines(log).forEach(System.out::println);

        // ----------------------------------------------------------------
        // 6. NIO PATH OPERATIONS
        // ----------------------------------------------------------------
        System.out.println("\n=== NIO Path operations ===");

        Path filePath = Paths.get(BASE_DIR, "poem.txt");
        System.out.println("Path:          " + filePath);
        System.out.println("File name:     " + filePath.getFileName());
        System.out.println("Parent:        " + filePath.getParent());
        System.out.println("Absolute:      " + filePath.toAbsolutePath());
        System.out.println("Exists:        " + Files.exists(filePath));
        System.out.println("Is regular:    " + Files.isRegularFile(filePath));
        System.out.println("Is directory:  " + Files.isDirectory(filePath));
        System.out.println("Size (bytes):  " + Files.size(filePath));

        // ----------------------------------------------------------------
        // 7. CREATE SUBDIRECTORY AND COPY FILE
        // ----------------------------------------------------------------
        System.out.println("\n=== Create dir + copy ===");

        Path backup = baseDir.resolve("backup");
        Files.createDirectories(backup);
        System.out.println("Created directory: " + backup);

        Path poemBackup = backup.resolve("poem.txt");
        Files.copy(poem, poemBackup, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Copied poem.txt -> backup/poem.txt");

        // ----------------------------------------------------------------
        // 8. WALK DIRECTORY TREE
        // ----------------------------------------------------------------
        System.out.println("\n=== Files.walk ===");

        try (Stream<Path> stream = Files.walk(baseDir)) {
            stream.forEach(p -> {
                String indent = "  ".repeat(baseDir.relativize(p).getNameCount());
                String type   = Files.isDirectory(p) ? "[DIR]  " : "[FILE] ";
                System.out.println(indent + type + p.getFileName());
            });
        }

        // ----------------------------------------------------------------
        // 9. DELETE FILE
        // ----------------------------------------------------------------
        System.out.println("\n=== Delete file ===");

        Path toDelete = baseDir.resolve("temp_delete_me.txt");
        Files.writeString(toDelete, "temporary");
        System.out.println("Created:  " + toDelete.getFileName());
        System.out.println("Exists:   " + Files.exists(toDelete));
        Files.deleteIfExists(toDelete);
        System.out.println("Deleted.  Exists: " + Files.exists(toDelete));

        // ----------------------------------------------------------------
        // 10. EXERCISE: Word count from a text file
        // ----------------------------------------------------------------
        System.out.println("\n=== Exercise: Word count ===");
        wordCount(poem);

        System.out.println("\nAll File I/O demos complete. Files are in: " + BASE_DIR);
    }

    // ----------------------------------------------------------------
    // HELPERS
    // ----------------------------------------------------------------

    /** Writes a multi-line poem using BufferedWriter */
    static void writeWithBufferedWriter(Path path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            writer.write("Roses are red,");
            writer.newLine();
            writer.write("Violets are blue,");
            writer.newLine();
            writer.write("Java is awesome,");
            writer.newLine();
            writer.write("And so are you!");
            writer.newLine();
        }
    }

    /** Reads a file line by line using BufferedReader */
    static void readWithBufferedReader(Path path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            int lineNum = 1;
            while ((line = reader.readLine()) != null) {
                System.out.printf("%2d: %s%n", lineNum++, line);
            }
        }
    }

    /** Writes CSV student data using NIO Files.write() */
    static void writeCSV(Path path) throws IOException {
        List<String> rows = List.of(
            "name,age,grade",
            "Alice,20,A",
            "Bob,22,B",
            "Charlie,21,A",
            "Diana,23,B",
            "Eve,20,C"
        );
        Files.write(path, rows, StandardCharsets.UTF_8);
        System.out.println("Wrote CSV to: " + path.getFileName());
    }

    /** Reads CSV, skips header, prints each student */
    static void readCSVAndProcess(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        System.out.println("Students from CSV:");
        lines.stream()
             .skip(1)  // skip header
             .map(row -> row.split(","))
             .forEach(parts -> System.out.printf("  Name=%-10s Age=%-4s Grade=%s%n",
                     parts[0], parts[1], parts[2]));
    }

    /** Appends a timestamped log entry to a file */
    static void appendToFile(Path path, String message) throws IOException {
        String entry = java.time.LocalDateTime.now()
                        + " " + message + System.lineSeparator();
        Files.writeString(path, entry,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    /** Counts words in a text file */
    static void wordCount(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        int totalWords = 0;
        int totalChars = 0;
        for (String line : lines) {
            String[] words = line.trim().split("\\s+");
            if (!line.isBlank()) totalWords += words.length;
            totalChars += line.length();
        }
        System.out.println("File: " + path.getFileName());
        System.out.println("Lines: " + lines.size());
        System.out.println("Words: " + totalWords);
        System.out.println("Chars: " + totalChars);
    }
}
