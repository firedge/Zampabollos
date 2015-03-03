package com.fdgproject.firedge.zambabollos;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Firedge on 20/02/2015.
 */
public class Food {
    private Bitmap bmp;
    private static int ySpeed;
    private int height, width;
    private int maxWidth, maxHeight;
    private int x, y;

    private int frameActual=0;
    private static final int COLUMNAS = 8;
    private static final int FILAS = 6;
    private int tipo;

    public Food(Bitmap bmp, int width, int height) {
        this.bmp = bmp;
        maxWidth = width;
        maxHeight = height;
        this.height = bmp.getHeight()/FILAS;
        this.width = bmp.getWidth()/COLUMNAS;
        this.x = (int)(Math.random()*(maxWidth - this.width*3)+this.width);
        this.y = 0;
        ySpeed = 5;
        tipo = getAnimationRow();
    }

    public void dibujar(Canvas canvas) {
        movimiento();
        int origenx = frameActual * width;
        int origeny = tipo * height;
        Rect origen = new Rect(origenx, origeny, origenx + width, origeny + height);
        Rect destino = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, origen, destino, null);
    }

    private int getAnimationRow() {
        return (int)(Math.random()*6);
    }

    public void setySpeed(int speed){
        this.ySpeed = speed;
    }

    private void movimiento(){
        frameActual = ++frameActual % COLUMNAS;
        y = y + ySpeed;
    }

    public Rect getBounds() {
        return new Rect(x, y, x+width, y+height);
    }

    public boolean colisiona(Player p){
        if(tipo == 4)
            return false;
        return Rect.intersects(this.getBounds(), p.getBounds());
    }

    public boolean colisionaVerdura(Player p){
        return Rect.intersects(this.getBounds(), p.getBounds());
    }

    public boolean outRange(){
        return y > maxHeight && tipo != 4;
    }
}
