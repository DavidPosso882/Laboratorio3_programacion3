package Punto_3;

public class ConditionMonitor {

    public int condition = 0;

    public synchronized void waitForCondition() {
        while (condition%5 != 0 && condition != 0) {

                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                notifyAll();
        }
    }

    public synchronized void setCondition(int value) {
        condition = value;
        if (condition%5 == 0) {
            notify();
        }
    }
}
