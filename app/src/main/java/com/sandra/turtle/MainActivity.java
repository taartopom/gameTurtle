package com.sandra.turtle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * creation par sandra le 19/07/19
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {
    private NagerView gameView;
    private Handler maitre = new Handler();
    private final static long Interval =  30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView =  new NagerView(this);
        setContentView(gameView);

        Timer timer =  new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               maitre.post(new Runnable() {
                   @Override
                   public void run() {
                       gameView.invalidate();
                   }
               });
            }
        }, 0, Interval);
    }
}
