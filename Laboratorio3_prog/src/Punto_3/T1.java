package Punto_3;

public class T1 extends Thread{
    private ConditionMonitor conditionMonitor;

    public T1(ConditionMonitor c) {
        conditionMonitor = c;
    }

    @Override
    public void run() {
        while (true) {
            conditionMonitor.waitForCondition();
            System.out.println("El hilo 1 puede continuar, con la condicion: " + conditionMonitor.condition);
            conditionMonitor.condition++;
        }
    }
}
