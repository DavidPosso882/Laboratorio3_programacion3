package Punto_4;

import java.util.Random;
import java.util.concurrent.BlockingQueue;


public class Productor implements Runnable {
    // Cola compartida donde el productor pondrá los números
    private BlockingQueue<Integer> queue;
    private int maxProduccion;
    private Random random;

    // Constructor de la clase Productor
    public Productor(BlockingQueue<Integer> queue, int maxProduction) {
        this.queue = queue;
        this.maxProduccion = maxProduction;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            // Aquí se producen los números hasta alcanzar maxProduccion
            for (int i = 0; i < maxProduccion; i++) {
                // Generar un número aleatorio entre 1 y 100
                int numero = random.nextInt(100) + 1;
                // Poner el número en la cola (si la cola está llena, esperará)
                queue.put(numero);
                System.out.println(Thread.currentThread().getName() + " produjo: " + numero);
                // Espera los 500 milisegundos antes de producir el siguiente número
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " fue interrumpido.");
        }
        System.out.println(Thread.currentThread().getName() + " ha terminado de producir.");
    }
}
