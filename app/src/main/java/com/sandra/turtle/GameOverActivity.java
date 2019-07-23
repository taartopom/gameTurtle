package com.sandra.turtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Creation par sandra le 21/07/19
 * @version 1.0
 * Cette activite gere la vue et le layout de l'cran game over
 * ici je vais permettre de rejouer et d'afficher votre score
 */
public class GameOverActivity extends AppCompatActivity {
    private Button btnRejouer;
    private TextView txtScoreTotal;
    private String score;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        score = getIntent().getExtras().get("score").toString();

        txtScoreTotal = findViewById(R.id.txtScoreTotal);


        btnRejouer = findViewById(R.id.btnRejouer);

        // ajout de l'ecouteur sur le bouton Rejouer
        btnRejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent =  new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(mainIntent
                );

            }
        });

        txtScoreTotal.setText("Score = " + score);
    }
}
