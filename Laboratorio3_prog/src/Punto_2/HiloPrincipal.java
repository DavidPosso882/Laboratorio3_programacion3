package Punto_2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class HiloPrincipal {

    public HiloPrincipal() {
    }

    private static final int INICIO_CUENTA_REGRESIVA = 10; // Valor inicial de la cuenta
    private int contador = INICIO_CUENTA_REGRESIVA; // contador que irá disminuyendo
    private final ReentrantLock bloqueo = new ReentrantLock(); // Bloqueo para sincronización
    private final Condition condicion = bloqueo.newCondition(); // Condición para que los hilos esperen

    // Método para iniciar la cuenta regresiva
    public void iniciarCuentaRegresiva() {
        bloqueo.lock(); // poner el bloqueo para sincronizar
        try {
            while (contador > 0) {
                System.out.println("Cuenta regresiva: " + contador);
                contador=contador-1;
                Thread.sleep(1000); // Esperar 1 segundo entre cada número
            }
            System.out.println("Cuenta regresiva: 0");
            condicion.signalAll(); // Notificar a todos los hilos que pueden comenzar
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            bloqueo.unlock(); // Liberar el bloqueo
        }
    }

    // Método para que los hilos esperen hasta que la cuenta regresiva llegue a 0
    public void esperarCuentaRegresiva() {
        bloqueo.lock(); // Adquirir el bloqueo
        try {
            while (contador > 0) {
                condicion.await();
            }
            System.out.println(Thread.currentThread().getName() + " ha comenzado a ejecutarse.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            bloqueo.unlock();
        }
    }
}
