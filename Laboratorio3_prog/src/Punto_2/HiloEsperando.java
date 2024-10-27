package Punto_2;

public class HiloEsperando implements Runnable{
    private final HiloPrincipal sincronizador;

    public HiloEsperando(HiloPrincipal sincronizador) {
        this.sincronizador = sincronizador;
    }
    //Asignamos la funcion al run
    @Override
    public void run() {
        sincronizador.esperarCuentaRegresiva();

    }
}
