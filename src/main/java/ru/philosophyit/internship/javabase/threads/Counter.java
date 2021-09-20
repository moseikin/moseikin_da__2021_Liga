package ru.philosophyit.internship.javabase.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Counter {
    static AtomicInteger counter = new AtomicInteger(0);
    public static final int N_THREADS = 4;

    /// Перепишите код так, чтобы операция увеличения счетчика была синхронизируемой
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);
        executorService.submit(() -> counter.addAndGet(1));

        CompletableFuture<?>[] futures = IntStream.range(0, N_THREADS)
                .mapToObj(ignored -> runCounting(executorService))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures)
                .thenApply(ignored -> {
                    System.out.println(counter);
                    executorService.shutdown();
                    return null;
                });
    }

    static CompletableFuture<Void> runCounting(ExecutorService executorService) {
        return CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 1000000; i++) {
                Counter.counter.addAndGet(1);
            }
        }, executorService);
    }
}
