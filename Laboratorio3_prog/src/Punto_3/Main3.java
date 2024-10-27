package Punto_3;

import static java.lang.Thread.sleep;

public class Main3 {

    public static void main(String[] args) {
        ConditionMonitor c = new ConditionMonitor();


        Thread t1 = new T1(c);
        Thread t2 = new T2(c);
        Thread t3 = new T3(c);
        Thread tPrincipal = new TPrincipal(c);

        t1.start();
        t2.start();
        t3.start();
        tPrincipal.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            tPrincipal.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
