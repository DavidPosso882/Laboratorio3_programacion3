package Punto_1;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DownloadTask implements Runnable {
    private final String url;
    private final Random random;

    public DownloadTask(String url) {
        this.url = url;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            System.out.println("Iniciando descarga desde: " + url);
            // Simula el tiempo de descarga entre 5 y 30 segundos
            int downloadTime = random.nextInt(26) + 5;
            TimeUnit.SECONDS.sleep(downloadTime);
            System.out.println("Descarga completada desde: " + url + " (Tiempo: " + downloadTime + " segundos)");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Descarga interrumpida desde: " + url);
        }
    }
}
