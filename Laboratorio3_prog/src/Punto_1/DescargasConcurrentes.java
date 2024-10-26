package Punto_1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DescargasConcurrentes {
    private static final int TOTAL_DOWNLOADS = 50;
    private static final int CONCURRENT_DOWNLOADS = 10;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Iniciando programa de descargas concurrentes");

        // Ejecutar con diferentes tipos de pools
        runWithFixedThreadPool();
        runWithCachedThreadPool();
        runWithScheduledThreadPool();
    }

    private static void runWithFixedThreadPool() throws InterruptedException {
        System.out.println("\n--- Usando FixedThreadPool ---");
        ExecutorService executor = Executors.newFixedThreadPool(CONCURRENT_DOWNLOADS);
        runDownloads(executor);
    }

    private static void runWithCachedThreadPool() throws InterruptedException {
        System.out.println("\n--- Usando CachedThreadPool ---");
        ExecutorService executor = Executors.newCachedThreadPool();
        runDownloads(executor);
    }

    private static void runWithScheduledThreadPool() throws InterruptedException {
        System.out.println("\n--- Usando ScheduledThreadPool ---");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(CONCURRENT_DOWNLOADS);
        runDownloads(executor);
    }

    private static void runDownloads(ExecutorService executor) throws InterruptedException {
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < TOTAL_DOWNLOADS; i++) {
            String url = "https://ejemplo.com/archivo" + (i + 1) + ".zip";
            futures.add(executor.submit(new DownloadTask(url)));
        }

        // Esperar a que todas las tareas se completen (equivalente a Thread.join())
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
            System.err.println("El executor no terminó en el tiempo esperado");
            executor.shutdownNow();
        }
    }
}
