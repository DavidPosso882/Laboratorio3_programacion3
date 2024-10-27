package Punto_3;

import static java.lang.Thread.sleep;

public class TPrincipal extends Thread{

    private ConditionMonitor conditionMonitor;

    public TPrincipal(ConditionMonitor c) {
        conditionMonitor = c;
    }

    @Override
    public void run(){
        int value;
        while(true){
            value = (int)(Math.random()*100)+1;
            conditionMonitor.setCondition(value);
            System.out.println("se mando: "+value);
            try {
                sleep(value);
            }catch (InterruptedException e){break;}
        }
    }
}
