package Punto_4;

import java.util.concurrent.BlockingQueue;


public class Consumidor implements Runnable {
    // Cola compartida de donde el consumidor tomará los números
    private BlockingQueue<Integer> queue;
    // Esta variable es para controlar si el consumidor debe seguir ejecutándose
    private boolean corriendo;

    // Constructor de la clase Consumidor
    public Consumidor(BlockingQueue<Integer> queue) {
        this.queue = queue;
        this.corriendo = true;
    }


    @Override
    public void run() {
        try {
            // Mientras running sea true, el consumidor seguirá consumiendo
            while (corriendo) {
                // Intenta tomar un número de la cola (si está vacía, esperará)
                Integer numero = queue.take();
                System.out.println(Thread.currentThread().getName() + " consumió: " + numero);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            // Por si el hilo es interrumpido
            System.out.println(Thread.currentThread().getName() + " fue interrumpido.");
        }
        System.out.println(Thread.currentThread().getName() + " ha terminado.");
    }

    // Este metodo sirve para detener el consumidor
    public void detener() {
        corriendo = false;
    }
}

