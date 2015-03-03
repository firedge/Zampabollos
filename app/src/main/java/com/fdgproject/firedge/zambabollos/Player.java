package com.fdgproject.firedge.zambabollos;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Firedge on 20/02/2015.
 */
public class Player {
    private Bitmap bmp;
    private int xSpeed;
    private int height, width;
    private int maxWidth, maxHeight;
    private int x, y;

    private int frameActual=0;
    private static final int COLUMNAS = 8;
    private static final int FILAS = 3;

    public Player(Bitmap bmp, int width, int height) {
        this.bmp = bmp;
        maxWidth = width;
        maxHeight = height;
        this.height = bmp.getHeight()/FILAS;
        this.width = bmp.getWidth()/COLUMNAS;
        this.x = Math.round(maxWidth/2 - this.width/2);
        this.y = Math.round(maxHeight - this.height);
        xSpeed = 0;
    }

    public void dibujar(Canvas canvas) {
        movimiento();
        int origenx = frameActual * width;
        int origeny = getAnimationRow() * height;
        Rect origen = new Rect(origenx, origeny, origenx + width, origeny + height);
        Rect destino = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, origen, destino, null);
    }

    private int getAnimationRow() {
        if(xSpeed > 1)
            return 1;
        else if(xSpeed < -1)
            return 2;
        else
            return 0;
    }

    public void setxSpeed(int speed){
        this.xSpeed = speed;
    }

    private void movimiento(){
        frameActual = ++frameActual % COLUMNAS;
        if (x > maxWidth - width - xSpeed || x + xSpeed < 0) {
            xSpeed = 0;
        }
        x = x + xSpeed;
    }

    public Rect getBounds() {
        return new Rect(x+width/2, y+height/8, x+width-width/4, y+height-height/2);
    }
}
