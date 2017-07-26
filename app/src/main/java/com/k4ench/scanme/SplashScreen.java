package com.k4ench.scanme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.hanks.htextview.HTextView;

public class SplashScreen extends AppCompatActivity {
    HTextView hTextView;
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    private final int SPLASH_DISPLA = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        hTextView=(HTextView) findViewById(R.id.text2);
        Shader shader=new LinearGradient(0,0,0,hTextView.getTextSize(),Color.RED,Color.BLUE,Shader.TileMode.CLAMP);
        hTextView.getPaint().setShader(shader);
        hTextView.animateText("HKL Scanner Math");
        final SharedPreferences settings=getSharedPreferences("prefs",0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean firstRun=settings.getBoolean("firstRun",false);
                if(!firstRun)
                {
                    SharedPreferences.Editor editor=settings.edit();
                    editor.putBoolean("firstRun",true);
                    editor.apply();
                    String img1="data";
                    Intent i=new Intent(SplashScreen.this,SplashActivity.class);
                    i.putExtra("btn",img1);
                    startActivity(i);

                }else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent mainIntent = new Intent(SplashScreen.this, MainActivity1.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(mainIntent);

                        }
                    }, SPLASH_DISPLAY_LENGTH);
                }

            }
        }, SPLASH_DISPLA);

    }
}
