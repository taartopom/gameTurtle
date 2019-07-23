package com.sandra.turtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

/**
 * Creation par sandra le 19/07/19
 * @version 1.0
 * cette class s'occupe du l'ecran d'acceuil affiché en 5s
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread fil =  new Thread(){
            public void run(){
                try {
                    sleep(5000);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
                finally {
                    Intent mainIntent  = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        };
        fil.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }
}
