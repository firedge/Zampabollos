package com.fdgproject.firedge.zambabollos;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
    }


    public void play(View v){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void hs(View v){
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

    public void setting(View v){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

}
