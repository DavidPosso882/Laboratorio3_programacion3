package Punto_1;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class DescargasConcurrentes {
    private static final int DESCARGAS_TOTALES = 50; // Total de descargas que debemos realizar
    private static final int DESCARGAS_CONCURRENTES = 10; // Número máximo de descargas que haremos

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Iniciando programa de descargas concurrentes");

        // Ejecución de las descargas usando tres tipos diferentes de pools de hilos
        ejecutarConFixedThreadPool();
        ejecutarConCachedThreadPool();
        ejecutarConScheduledThreadPool();

        System.out.println("Programa finalizado");
    }

    private static void ejecutarConFixedThreadPool() throws InterruptedException {
        System.out.println("\n--- Usando FixedThreadPool ---");
        // FixedThreadPool: Es un pool con un número fijo de hilos (CONCURRENT_DOWNLOADS)
        ExecutorService executor = Executors.newFixedThreadPool(DESCARGAS_CONCURRENTES);
        // Ejecuta las descargas usando el pool fijo
        ejecutarDescargas(executor, "FixedThreadPool");
    }

    private static void ejecutarConCachedThreadPool() throws InterruptedException {
        System.out.println("\n--- Usando CachedThreadPool ---");
        // CachedThreadPool: Es un pool que ajusta dinámicamente el número de hilos
        ExecutorService executor = Executors.newCachedThreadPool();
        // Ejecuta las descargas usando el pool dinámico
        ejecutarDescargas(executor, "CachedThreadPool");
    }

    private static void ejecutarConScheduledThreadPool() throws InterruptedException {
        System.out.println("\n--- Usando ScheduledThreadPool ---");
        // ScheduledThreadPool: Es un pool que permite programar tareas con ciertp retraso o periodicidad
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(DESCARGAS_CONCURRENTES);
        // Ejecuta las descargas usando el pool de programación
        ejecutarDescargas(executor, "ScheduledThreadPool");
    }

    private static void ejecutarDescargas(ExecutorService executor, String poolType) throws InterruptedException {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(); // Cola de tareas de descarga
        List<Future<?>> tareasActivas = new ArrayList<>(); // Lista para rastrear tareas activas
        long tiempoInicio = System.currentTimeMillis(); // Marca de tiempo inicial para medir duración

        // Se añaden todas las descargas a la cola de trabajo
        for (int i = 0; i < DESCARGAS_TOTALES; i++) {
            String url = "https://ejemplo.com/archivo" + (i + 1) + ".zip"; // Genera URL para cada descarga simulada
            workQueue.add(new DownloadTask(url)); // Añade la tarea de descarga a la cola
        }

        int contadorDescargas = 0; // Contador para las descargas completadas

        // Bucle principal para gestionar las descargas
        while (contadorDescargas < DESCARGAS_TOTALES) {
            // Iniciar nuevas tareas si hay espacio disponible y tareas pendientes en la cola
            while (tareasActivas.size() < DESCARGAS_CONCURRENTES && !workQueue.isEmpty()) {
                Runnable task = workQueue.poll(); // Extrae la siguiente tarea de la cola
                if (task != null) {
                    tareasActivas.add(executor.submit(task)); // Aquí se envía la tarea al pool y luego la añade a las tareas activas
                }
            }

            // Se revisa y eliminan tareas completadas de la lista
            for (int i = tareasActivas.size() - 1; i >= 0; i--) {
                Future<?> futuro = tareasActivas.get(i);
                if (futuro.isDone()) { // Verifica si la tarea ha finalizado
                    tareasActivas.remove(i); // Elimina la tarea completada de la lista
                    contadorDescargas++; // Incrementa el contador de tareas completadas
                }
            }

            // Se hace una pequeña pausa para evitar sobrecargar la CPU en cada ciclo
            TimeUnit.MILLISECONDS.sleep(100);
        }

        // Termina el pool de hilos de forma ordenada
        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.MINUTES)) { // Espera a que termine dentro del tiempo límite
            System.err.println("El executor no terminó en el tiempo esperado");
            executor.shutdownNow(); // Forza el cierre del executor si no termina a tiempo
        }

        long tiempoFinal = System.currentTimeMillis(); // Marca de tiempo final para la duración
        imprimirInfo(poolType, tiempoInicio, tiempoFinal); // Imprime un resumen de la ejecución
    }

    private static void imprimirInfo(String tipoPool, long tiempoInicial, long tiempoFinal) {
        long duracion = (tiempoFinal - tiempoInicial) / 1000; // Calcula la duración en segundos
        System.out.println("\nResumen de " + tipoPool + ":");
        System.out.println("Total de descargas completadas: " + DESCARGAS_TOTALES);
        System.out.println("Tiempo total de ejecución: " + duracion + " segundos");
        System.out.println("Promedio de tiempo por descarga: " + (duracion / DESCARGAS_TOTALES) + " segundos");
    }
}

