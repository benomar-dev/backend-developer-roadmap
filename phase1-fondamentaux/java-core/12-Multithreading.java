/**
 * 12 - Multithreading and Concurrency
 *
 * Topics covered:
 *  - Creating threads (extends Thread vs implements Runnable)
 *  - Thread lifecycle: NEW -> RUNNABLE -> BLOCKED/WAITING -> TERMINATED
 *  - Thread sleep, join, interrupt
 *  - Race conditions and synchronized methods/blocks
 *  - volatile keyword
 *  - ExecutorService (thread pool)
 *  - Callable and Future
 *  - AtomicInteger for thread-safe counters
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Multithreading {

    // ============================================================
    // 1. Creating threads by extending Thread
    // ============================================================
    static class CounterThread extends Thread {

        private final String label;
        private final int    count;

        CounterThread(String label, int count) {
            super(label);   // sets the thread name
            this.label = label;
            this.count = count;
        }

        @Override
        public void run() {
            for (int i = 1; i <= count; i++) {
                System.out.printf("[%s] count = %d%n", label, i);
                try {
                    Thread.sleep(50);  // pause 50 ms
                } catch (InterruptedException e) {
                    System.out.println("[" + label + "] Interrupted!");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("[" + label + "] done.");
        }
    }

    // ============================================================
    // 2. Runnable – preferred (separates task from thread)
    // ============================================================
    static class PrintTask implements Runnable {

        private final String message;
        private final int    times;

        PrintTask(String message, int times) {
            this.message = message;
            this.times   = times;
        }

        @Override
        public void run() {
            for (int i = 0; i < times; i++) {
                System.out.println("[" + Thread.currentThread().getName() + "] " + message + " #" + (i + 1));
            }
        }
    }

    // ============================================================
    // 3. Race condition demo (UNSAFE counter)
    // ============================================================
    static int unsafeCounter = 0;

    static void incrementUnsafe() {
        unsafeCounter++;    // NOT atomic: read-modify-write
    }

    // ============================================================
    // 4. Thread-safe counter using synchronized
    // ============================================================
    static class SafeCounter {

        private int count = 0;

        public synchronized void increment() {
            count++;
        }

        public synchronized int getCount() {
            return count;
        }
    }

    // ============================================================
    // 5. Producer-Consumer with wait/notify
    // ============================================================
    static class BoundedBuffer {

        private final List<Integer> buffer = new ArrayList<>();
        private final int capacity;

        BoundedBuffer(int capacity) { this.capacity = capacity; }

        public synchronized void produce(int item) throws InterruptedException {
            while (buffer.size() == capacity) {
                System.out.println("[Producer] Buffer full, waiting...");
                wait();
            }
            buffer.add(item);
            System.out.println("[Producer] Produced: " + item + " | buffer=" + buffer);
            notifyAll();
        }

        public synchronized int consume() throws InterruptedException {
            while (buffer.isEmpty()) {
                System.out.println("[Consumer] Buffer empty, waiting...");
                wait();
            }
            int item = buffer.remove(0);
            System.out.println("[Consumer] Consumed: " + item + " | buffer=" + buffer);
            notifyAll();
            return item;
        }
    }

    // ============================================================
    // 6. Callable – returns a result
    // ============================================================
    static class FibonacciTask implements Callable<Long> {

        private final int n;
        FibonacciTask(int n) { this.n = n; }

        @Override
        public Long call() {
            return fibonacci(n);
        }

        private long fibonacci(int n) {
            if (n <= 1) return n;
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    // ============================================================
    // Main
    // ============================================================
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // ----------------------------------------------------------------
        // 1. THREAD BY EXTENDING Thread
        // ----------------------------------------------------------------
        System.out.println("=== Thread (extends Thread) ===");

        CounterThread t1 = new CounterThread("Thread-A", 3);
        CounterThread t2 = new CounterThread("Thread-B", 3);

        t1.start();
        t2.start();

        t1.join();  // wait for t1 to finish
        t2.join();
        System.out.println("Both CounterThreads finished.\n");

        // ----------------------------------------------------------------
        // 2. THREAD WITH RUNNABLE
        // ----------------------------------------------------------------
        System.out.println("=== Thread (Runnable) ===");

        Thread thread3 = new Thread(new PrintTask("Hello", 3), "MyRunnable-1");
        Thread thread4 = new Thread(() -> {
            // Lambda as Runnable
            for (int i = 1; i <= 3; i++) {
                System.out.println("[Lambda] ping #" + i);
            }
        }, "Lambda-Thread");

        thread3.start();
        thread4.start();
        thread3.join();
        thread4.join();
        System.out.println("Runnable threads finished.\n");

        // ----------------------------------------------------------------
        // 3. RACE CONDITION (unsafe) vs SYNCHRONIZED (safe)
        // ----------------------------------------------------------------
        System.out.println("=== Race Condition vs Synchronized ===");

        int numThreads    = 100;
        int incrementsEach = 1000;

        // Unsafe: total will likely be less than numThreads * incrementsEach
        unsafeCounter = 0;
        List<Thread> unsafeThreads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            unsafeThreads.add(new Thread(() -> {
                for (int j = 0; j < incrementsEach; j++) incrementUnsafe();
            }));
        }
        unsafeThreads.forEach(Thread::start);
        for (Thread t : unsafeThreads) t.join();
        System.out.println("Unsafe counter  (expected " + (numThreads * incrementsEach) + "): " + unsafeCounter);

        // Safe: total will always be numThreads * incrementsEach
        SafeCounter safeCounter = new SafeCounter();
        List<Thread> safeThreads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            safeThreads.add(new Thread(() -> {
                for (int j = 0; j < incrementsEach; j++) safeCounter.increment();
            }));
        }
        safeThreads.forEach(Thread::start);
        for (Thread t : safeThreads) t.join();
        System.out.println("Safe counter    (expected " + (numThreads * incrementsEach) + "): " + safeCounter.getCount());

        // AtomicInteger – lock-free thread-safe counter
        AtomicInteger atomicCounter = new AtomicInteger(0);
        List<Thread> atomicThreads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            atomicThreads.add(new Thread(() -> {
                for (int j = 0; j < incrementsEach; j++) atomicCounter.incrementAndGet();
            }));
        }
        atomicThreads.forEach(Thread::start);
        for (Thread t : atomicThreads) t.join();
        System.out.println("Atomic counter  (expected " + (numThreads * incrementsEach) + "): " + atomicCounter.get());

        // ----------------------------------------------------------------
        // 4. EXECUTOR SERVICE – fixed thread pool
        // ----------------------------------------------------------------
        System.out.println("\n=== ExecutorService (fixed thread pool) ===");

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.printf("[%s] Executing task %d%n",
                        Thread.currentThread().getName(), taskId);
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                System.out.printf("[%s] Task %d done%n",
                        Thread.currentThread().getName(), taskId);
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("All tasks finished.\n");

        // ----------------------------------------------------------------
        // 5. CALLABLE AND FUTURE
        // ----------------------------------------------------------------
        System.out.println("=== Callable and Future ===");

        ExecutorService pool = Executors.newCachedThreadPool();
        List<Future<Long>> futures = new ArrayList<>();

        int[] fibInputs = {30, 35, 38, 40};
        for (int n : fibInputs) {
            futures.add(pool.submit(new FibonacciTask(n)));
        }

        for (int i = 0; i < fibInputs.length; i++) {
            try {
                long fib = futures.get(i).get(10, TimeUnit.SECONDS);
                System.out.printf("fibonacci(%2d) = %d%n", fibInputs[i], fib);
            } catch (TimeoutException e) {
                System.out.println("fibonacci(" + fibInputs[i] + ") timed out");
                futures.get(i).cancel(true);
            }
        }

        pool.shutdown();

        // ----------------------------------------------------------------
        // 6. PRODUCER-CONSUMER (brief demo)
        // ----------------------------------------------------------------
        System.out.println("\n=== Producer-Consumer ===");

        BoundedBuffer buffer = new BoundedBuffer(3);

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    buffer.produce(i);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    buffer.consume();
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("Producer-Consumer demo complete.");

        // ----------------------------------------------------------------
        // 7. THREAD INTERRUPTION
        // ----------------------------------------------------------------
        System.out.println("\n=== Thread Interruption ===");

        Thread longRunning = new Thread(() -> {
            System.out.println("[LongRunning] starting...");
            for (int i = 0; i < 100; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("[LongRunning] interrupted – stopping gracefully.");
                    return;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.out.println("[LongRunning] sleep interrupted.");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });

        longRunning.start();
        Thread.sleep(200);    // let it run a bit
        longRunning.interrupt();
        longRunning.join();
        System.out.println("LongRunning thread has stopped.");
    }
}
