package Punto_4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProductorConsumidorDemo {
    public static void main(String[] args) {
        // creamos una cola compartida con capacidad para 10 elementos
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

        int maxProduccion = 20;

        Productor productor1 = new Productor(queue, maxProduccion);
        Productor productor2 = new Productor(queue, maxProduccion);
        Consumidor consumidor1 = new Consumidor(queue);
        Consumidor consumidor2 = new Consumidor(queue);

        Thread hiloProductor1 = new Thread(productor1, "Productor 1");
        Thread hiloProductor2 = new Thread(productor2, "Productor 2");
        Thread hiloConsumidor1 = new Thread(consumidor1, "Consumidor 1");
        Thread hiloConsumidor2 = new Thread(consumidor2, "Consumidor 2");

        hiloProductor1.start();
        hiloProductor2.start();
        hiloConsumidor1.start();
        hiloConsumidor2.start();

        // Se espera a que los productores terminen
        try {
            hiloProductor1.join();
            hiloProductor2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("El hilo principal fue interrumpido.");
        }

        // Espera un poco más para que los consumidores procesen los últimos elementos
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("El hilo principal fue interrumpido durante la espera final.");
        }

        consumidor1.detener();
        consumidor2.detener();

        // Se espera a que los consumidores terminen
        try {
            hiloConsumidor1.join(2000);
            hiloConsumidor2.join(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("El hilo principal fue interrumpido mientras esperaba a los consumidores.");
        }

        System.out.println("Programa terminado.");
    }
}
