package Punto_1;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DownloadTask implements Runnable {
    // Esto da color para la salida en consola y así poder diferenciar los diferentes procesos de una manera más estetica
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";

    private final String url; // URL de descarga
    private final int downloadTime; // Variable para simular el tiempo que tarda en descaegarse

    public DownloadTask(String url) {
        this.url = url;
        // Tiempo de descarga aleatorio que va de entre 5 y 30 segundos
        this.downloadTime = new Random().nextInt(26) + 5;
    }

    @Override
    public void run() {
        try {
            // Imprime mensaje de inicio de descarga con timestamp
            printWithTimestamp(ANSI_YELLOW + "Iniciando descarga desde: " + url + ANSI_RESET);
            // Simula la descarga con un total de 10 pasos, actualizando progreso
            for (int i = 1; i <= 10; i++) {
                TimeUnit.MILLISECONDS.sleep(downloadTime * 200); // Pausa en cada paso
                int percent = i * 10; // Calcula el porcentaje completado
                printProgress(percent); // Muestra el progreso de descarga en la consola
            }
            // Imprime mensaje de finalización de descarga con la duración respectiva
            printWithTimestamp(ANSI_GREEN + "Descarga completada desde: " + url +
                    " (Tiempo: " + downloadTime + " segundos)" + ANSI_RESET);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restablece el estado de interrupción del hilo
            // Mensaje si la descarga es interrumpida
            printWithTimestamp(ANSI_RED + "Descarga interrumpida desde: " + url + ANSI_RESET);
        }
    }

    private void printWithTimestamp(String message) {
        // Imprime un mensaje con el tiempo actual
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println("[" + timestamp + "] " + message);
    }

    private void printProgress(int percent) {
        // Imprime el porcentaje completado de la descarga
        System.out.println(url + " - Progreso: " + percent + "%");
    }
}

