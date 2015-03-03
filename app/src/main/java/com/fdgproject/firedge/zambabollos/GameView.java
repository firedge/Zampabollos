package com.fdgproject.firedge.zambabollos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Firedge on 03/02/2015.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    private Context cntx;
    private Bitmap bmp, bmf;
    private SensorManager sensorManager = null;
    private GameLoopThread hebraJuego;
    private Player player = null;
    private ArrayList<Food> comida = new ArrayList<Food>();
    private float pulse = 0;
    private final String SCORE = "com.firedge.score";
    private int puntuacion = 0;
    private int dificultad = 0;

    public GameView(Context context) {
        super(context);
        cntx = context;
        getHolder().addCallback(this);
        hebraJuego = new GameLoopThread(this);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.fat);
        bmf = BitmapFactory.decodeResource(getResources(), R.drawable.food);
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_NORMAL);
    }

    /*********************************************************************************/
    /*                        Metodos de la clase SurfaceView                        */
    /*********************************************************************************/

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //Background
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        Rect source = new Rect(0, 0, bm.getWidth(), bm.getHeight());
        Rect bitmapRect = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.drawBitmap(bm, source, bitmapRect, null);
        //--->
        if(player!=null)
            player.dibujar(canvas);

        for(Food f:comida){
            f.dibujar(canvas);
        }

        pulse++;
        if (pulse > 100 - dificultad) {
            pulse = 0;
            comida.add(new Food(bmf, this.getWidth(), this.getHeight()));
            if (puntuacion%2 == 0 && dificultad < 90){
                dificultad++;
            }
        }

        for(int i = 0; i<comida.size(); i++){
            if(comida.get(i).colisiona(player)){
                comida.remove(i);
                puntuacion++;
                Intent intent = new Intent(SCORE);
                intent.putExtra("actual", puntuacion);
                cntx.sendBroadcast(intent);
            }
            if(comida.get(i).outRange()){
                gameover(1);
            } else if (comida.get(i).colisionaVerdura(player)){
                gameover(2);
            }
        }

    }

    /*********************************************************************************/
    /*                               Métodos auxiliares                              */
    /*********************************************************************************/

    private void gameover(int id){
        comida = new ArrayList<Food>();
        int score = puntuacion;
        puntuacion = 0;
        pulse = 0;
        dificultad = 0;
        Intent intent = new Intent(SCORE);
        intent.putExtra("actual", puntuacion);
        cntx.sendBroadcast(intent);
        intent = new Intent(cntx, FinishActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("puntuacion", score);
        cntx.startActivity(intent);
    }

    /*********************************************************************************/
    /*                               Interfaz Callback                               */
    /*********************************************************************************/

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (!hebraJuego.isAlive()) {
            hebraJuego = new GameLoopThread(this);
        }
        hebraJuego.setFuncionando(true);
        hebraJuego.start();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        player = new Player(bmp, this.getWidth(), this.getHeight());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean reintentar = true;
        hebraJuego.setFuncionando(false);
        while (reintentar) {
            try {
                hebraJuego.join();
                reintentar = false;
            } catch (InterruptedException e) {
            }
        }
        sensorManager.unregisterListener(this);
    }

    /*********************************************************************************/
    /*                             Interacción pantalla                              */
    /*********************************************************************************/

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (getHolder()) {
            player.setxSpeed(Math.round(event.values[1]*5));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.v("AccuracyChanged", sensor.toString()+", "+i);
    }

}
