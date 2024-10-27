package Punto_3;

public class T3 extends Thread{
    private ConditionMonitor conditionMonitor;

    public T3(ConditionMonitor c) {
        conditionMonitor = c;
    }

    @Override
    public void run() {
        while (true) {
            conditionMonitor.waitForCondition();
            System.out.println("El hilo 3 puede continuar, con la condicion: " + conditionMonitor.condition);
        }
    }
}
