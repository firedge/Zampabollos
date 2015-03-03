package com.fdgproject.firedge.zambabollos;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class FinishActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_finish);
        int id = getIntent().getExtras().getInt("id");
        int score = getIntent().getExtras().getInt("puntuacion");
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.ly_go);
        TextView tvText = (TextView)findViewById(R.id.tv_go);
        TextView tvScore = (TextView)findViewById(R.id.tv_goscr);
        if(id == 1){
            rl.setBackground(getResources().getDrawable(R.drawable.gameover1));
            tvText.setText(getResources().getString(R.string.gameover1));
        } else if(id == 2){
            rl.setBackground(getResources().getDrawable(R.drawable.gameover2));
            tvText.setText(getResources().getString(R.string.gameover2));
        }
        tvScore.setText(score+"");
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
