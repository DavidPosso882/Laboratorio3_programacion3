package Punto_2;

import Punto_1.DownloadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CuentaRegresivaMain {

    public static void main(String[] args) throws InterruptedException {
        HiloPrincipal sincronizador = new HiloPrincipal();

        // Crear hilos que esperarán la cuenta regresiva
        Thread hilo1 = new Thread(new HiloEsperando(sincronizador), "Hilo 1");
        Thread hilo2 = new Thread(new HiloEsperando(sincronizador), "Hilo 2");
        Thread hilo3 = new Thread(new HiloEsperando(sincronizador), "Hilo 3");
        Thread hilo4 = new Thread(new HiloEsperando(sincronizador), "Hilo 4");

        // Iniciar los hilos que esperan
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();

        // Iniciar la cuenta regresiva en el hilo principal
        sincronizador.iniciarCuentaRegresiva();
    }

}
