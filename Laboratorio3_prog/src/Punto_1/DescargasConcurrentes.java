package Punto_1;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class DescargasConcurrentes {
    private static final int TOTAL_DOWNLOADS = 50;
    private static final int CONCURRENT_DOWNLOADS = 10;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Iniciando programa de descargas concurrentes");

        runWithFixedThreadPool();
        runWithCachedThreadPool();
        runWithScheduledThreadPool();

        System.out.println("Programa finalizado");
    }

    private static void runWithFixedThreadPool() throws InterruptedException {
        System.out.println("\n--- Usando FixedThreadPool ---");
        ExecutorService executor = Executors.newFixedThreadPool(CONCURRENT_DOWNLOADS);
        runDownloads(executor, "FixedThreadPool");
    }

    private static void runWithCachedThreadPool() throws InterruptedException {
        System.out.println("\n--- Usando CachedThreadPool ---");
        ExecutorService executor = Executors.newCachedThreadPool();
        runDownloads(executor, "CachedThreadPool");
    }

    private static void runWithScheduledThreadPool() throws InterruptedException {
        System.out.println("\n--- Usando ScheduledThreadPool ---");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(CONCURRENT_DOWNLOADS);
        runDownloads(executor, "ScheduledThreadPool");
    }

    private static void runDownloads(ExecutorService executor, String poolType) throws InterruptedException {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        List<Future<?>> activeFutures = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        // Crear todas las tareas de descarga
        for (int i = 0; i < TOTAL_DOWNLOADS; i++) {
            String url = "https://ejemplo.com/archivo" + (i + 1) + ".zip";
            workQueue.add(new DownloadTask(url));
        }

        int completedTasks = 0;

        while (completedTasks < TOTAL_DOWNLOADS) {
            // Iniciar nuevas tareas si hay espacio y tareas pendientes
            while (activeFutures.size() < CONCURRENT_DOWNLOADS && !workQueue.isEmpty()) {
                Runnable task = workQueue.poll();
                if (task != null) {
                    activeFutures.add(executor.submit(task));
                }
            }

            // Verificar tareas completadas
            for (int i = activeFutures.size() - 1; i >= 0; i--) {
                Future<?> future = activeFutures.get(i);
                if (future.isDone()) {
                    activeFutures.remove(i);
                    completedTasks++;
                }
            }

            // Pequeña pausa para no saturar la CPU
            TimeUnit.MILLISECONDS.sleep(100);
        }

        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
            System.err.println("El executor no terminó en el tiempo esperado");
            executor.shutdownNow();
        }

        long endTime = System.currentTimeMillis();
        printSummary(poolType, startTime, endTime);
    }

    private static void printSummary(String poolType, long startTime, long endTime) {
        long duration = (endTime - startTime) / 1000;
        System.out.println("\nResumen de " + poolType + ":");
        System.out.println("Total de descargas completadas: " + TOTAL_DOWNLOADS);
        System.out.println("Tiempo total de ejecución: " + duration + " segundos");
        System.out.println("Promedio de tiempo por descarga: " + (duration / TOTAL_DOWNLOADS) + " segundos");
    }
}
