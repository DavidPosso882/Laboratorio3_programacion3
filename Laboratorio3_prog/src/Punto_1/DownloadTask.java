package Punto_1;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DownloadTask implements Runnable {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";

    private final String url;
    private final int downloadTime;

    public DownloadTask(String url) {
        this.url = url;
        this.downloadTime = new Random().nextInt(26) + 5; // Tiempo aleatorio entre 5 y 30 segundos
    }

    @Override
    public void run() {
        try {
            printWithTimestamp(ANSI_YELLOW + "Iniciando descarga desde: " + url + ANSI_RESET);
            for (int i = 1; i <= 10; i++) {
                TimeUnit.MILLISECONDS.sleep(downloadTime * 100);
                int percent = i * 10;
                printProgress(percent);
            }
            printWithTimestamp(ANSI_GREEN + "Descarga completada desde: " + url +
                    " (Tiempo: " + downloadTime + " segundos)" + ANSI_RESET);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            printWithTimestamp(ANSI_RED + "Descarga interrumpida desde: " + url + ANSI_RESET);
        }
    }

    private void printWithTimestamp(String message) {
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println("[" + timestamp + "] " + message);
    }

    private void printProgress(int percent) {
        System.out.println(url + " - Progreso: " + percent + "%");
    }
}
