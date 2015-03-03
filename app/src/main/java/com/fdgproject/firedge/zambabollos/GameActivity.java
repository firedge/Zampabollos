package com.fdgproject.firedge.zambabollos;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class GameActivity extends Activity {

    private GameView gv = null;
    private final String SCORE = "com.firedge.score";
    private TextView tv;
    private BackgroundSound music;
    private static MediaPlayer mp;
    private int puntuacion, actual;
    private String [] sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_game);
        //Añadir el canvas al layout de activitymain
        final RelativeLayout prin = (RelativeLayout)findViewById(R.id.ly_main);
        gv = new GameView(this);
        prin.addView(gv);
        View v = LayoutInflater.from(this).inflate(R.layout.top_bar, null, false);
        prin.addView(v);
        tv = (TextView)findViewById(R.id.tv_score);
        SharedPreferences sharedPref = getSharedPreferences("preferencias", MODE_PRIVATE);
        puntuacion = sharedPref.getInt("score", 0);
        String s = sharedPref.getString("sound", "on,on");
        sound = s.split(",");
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receptor, new IntentFilter(SCORE));
        if(sound[0].equals("on")) {
            music = new BackgroundSound();
            music.execute();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sound[0].equals("on")) {
            mp.stop();
            mp.release();
        }
    }

    //BroadcastReceiver
    private BroadcastReceiver receptor= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            actual = bundle.getInt("actual");
            if(sound[1].equals("on") && actual!=0) {
                new BurpSound().execute();
            }
            tv.setText(""+actual);
            if(puntuacion < actual){
                SharedPreferences sharedPref = getSharedPreferences("preferencias", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("score", actual);
                editor.commit();
            }
        }
    };

    /*********************************************************************************/
    /*                                 Hebra Musica                                  */
    /*********************************************************************************/

    private class BurpSound extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            MediaPlayer player = MediaPlayer.create(GameActivity.this, R.raw.burp);
            player.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class BackgroundSound extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            mp = MediaPlayer.create(GameActivity.this, R.raw.music);
            mp.setLooping(true);
            mp.start();
            return null;
        }
    }

}
