package com.fdgproject.firedge.zambabollos;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;


public class SettingActivity extends Activity {

    private Switch sw_sound, sw_effects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_setting);
        SharedPreferences sharedPref = getSharedPreferences("preferencias", MODE_PRIVATE);
        String s = sharedPref.getString("sound", "on,on");
        String [] sound = s.split(",");
        sw_sound = (Switch)findViewById(R.id.sw_sound);
        sw_effects = (Switch)findViewById(R.id.sw_effects);
        if(sound[0].equals("on")){
            sw_sound.setChecked(true);
        } else {
            sw_sound.setChecked(false);
        }
        if(sound[1].equals("on")){
            sw_effects.setChecked(true);
        } else {
            sw_effects.setChecked(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        String s = "";
        if(sw_sound.isChecked()){
            s+="on,";
        } else {
            s+="off,";
        }
        if(sw_effects.isChecked()){
            s+="on";
        } else {
            s+="off";
        }
        SharedPreferences sharedPref = getSharedPreferences("preferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("sound", s);
        editor.commit();
    }
}
