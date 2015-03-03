package com.fdgproject.firedge.zambabollos;

import android.graphics.Canvas;

/**
 * Created by Firedge on 03/02/2015.
 */
public class GameLoopThread extends Thread{

    private GameView vista;
    private boolean funcionando = false;
    private static final long FPS = 60;

    public GameLoopThread(GameView vj){
        this.vista = vj;
    }

    public void setFuncionando(boolean f){
        funcionando = f;
    }

    @Override
    public void run() {
        long inicio;
        long ticksPS = 1000 / FPS;
        long tiempoEspera;
        while (funcionando) {
            Canvas canvas = null;
            inicio = System.currentTimeMillis();
            try {
                canvas = vista.getHolder().lockCanvas();
                synchronized (vista.getHolder()) {
                    vista.draw(canvas);
                }
            } catch(Exception ex){

            } finally {
                if (canvas != null) {
                    vista.getHolder().
                            unlockCanvasAndPost(canvas);
                }
            }
            tiempoEspera = ticksPS -
                    (System.currentTimeMillis() - inicio);
            try {
                if (tiempoEspera > 0)
                    sleep(tiempoEspera);
                else
                    sleep(10);
            } catch (InterruptedException e) {}
        }
    }
}
