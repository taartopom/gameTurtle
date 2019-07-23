package com.sandra.turtle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Creation par Sandra le 19/07/19
 * @version 1.0
 * cette class permet de faire nager notre belle tortue de haut en bas
 * permet de deplacer sa bonne nourriture de droite a gauche
 * permet de deplacer les dechets de l'ocean de droite a gauche
 */

public class NagerView extends View {
    private Bitmap turtle[] = new Bitmap[2];

    private int turtleX = 10;
    private int turtleY;
    private int turtleSpeed;

    private Bitmap bouffe;
    private int bouffeX, bouffeY, bouffeSpeed = 16;

    private Bitmap notbouffe;
    private int notbouffeX, notbouffeY, notbouffeSpeed = 20;

    private int score, lifeTurtle;


    private Bitmap gamebackground;
    private Paint scorePaint =  new Paint();
    private Bitmap vie[] =  new Bitmap[2];

    private int canvasWidth, canvasHeight;
    private boolean touch = false;

    /**
     * pour gerer la premier tortue sur la vue
     * @param context
     */
    public NagerView(Context context) {
        super(context);
        // affichage de notre petite tortue
        turtle[0] = BitmapFactory.decodeResource(getResources(), R.drawable.turtle);
        turtle[1] = BitmapFactory.decodeResource(getResources(), R.drawable.turtlered);


        //affichage de l'ecran de fond
        gamebackground = BitmapFactory.decodeResource(getResources(), R.drawable.gamebackground);

        //affichage de sa bonne et sa mauvaise nourriture
        bouffe =  BitmapFactory.decodeResource(getResources(),R.drawable.bouffe);
        notbouffe =  BitmapFactory.decodeResource(getResources(),R.drawable.notbouffe);

        // creation du score avec style
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        //affichage des coeurs de vie
        vie[0] =  BitmapFactory.decodeResource(getResources(), R.drawable.heartfull);
        vie[1] =  BitmapFactory.decodeResource(getResources(), R.drawable.heartempty);


        //parametre par default
        turtleY = 500;
        score =  0;
        lifeTurtle = 3;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight =  canvas.getHeight();

        canvas.drawBitmap(gamebackground, 0,0,null);

        int minTurtleY =  turtle[0].getHeight();
        int maxTurtleY =  canvasHeight - turtle[0].getHeight() * 3;

        // pour les deplacements de la tortue de haut en bas
        turtleY =  turtleY + turtleSpeed;
        if(turtleY < minTurtleY){
            turtleY =  minTurtleY;
        }
        if(turtleY > maxTurtleY){
            turtleY =  maxTurtleY;
        }

        turtleSpeed =  turtleSpeed +2;

        if(touch){
            canvas.drawBitmap(turtle[1],turtleX,turtleY,null);
            touch =  false;
        }
        else{
            canvas.drawBitmap(turtle[0], turtleX, turtleY, null);
        }
/*-----------------------------------------------------------------------------------------------*/
        /**
         * les conditions du jeu
         * 1- si elle mange de la bonne bouffe , elle 10points
         * 2.a- si elle mange des dechets elle permet 20point
         * 2.b- si elle mange des dechets elle perd une vie ( coeur)
         * 2.c- si elle perd tous ses coeurs , la partie est fini
         */
        if (eatBouffeChecker(bouffeX, bouffeY)){
            score =  score + 10;
            bouffeX = - 100;
        }
        if(eatBadBouffeChercker(notbouffeX,notbouffeY)){
            score =  score - 20;
            notbouffeX = -100;
            lifeTurtle--;
            if(lifeTurtle == 0){
                Toast.makeText(getContext(),"Game Over", Toast.LENGTH_SHORT).show();

                Intent gameOverIntent = new Intent(getContext(),GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score);
                getContext().startActivity(gameOverIntent);

            }

        }
/*-----------------------------------------------------------------------------------------------*/
        //Les interactions sur la vue
/*-----------------------------------------------------------------------------------------------*/
        // pour le deplacement de la bonne nourriture
        bouffeX =  bouffeX - bouffeSpeed;
        if (bouffeX < 0){
            bouffeX =  canvasWidth + 21;
            bouffeY =  (int) Math.floor(Math.random() * (maxTurtleY - minTurtleY) + minTurtleY);
        }

        //pour le deplacement des dechets
        notbouffeX = notbouffeX - notbouffeSpeed;
        if (notbouffeX <0){
            notbouffeX = canvasWidth + 21;
            notbouffeY = (int)Math.floor((Math.random()* (maxTurtleY - minTurtleY) + minTurtleY));
        }

        // Affichage du score
        canvas.drawText("Score : "+ score,20, 60, scorePaint);


        /*//positionnement des coeurs les uns a coté des autres sur la vue avant la boucle
        canvas.drawBitmap(vie[0], 500,10,null);
        canvas.drawBitmap(vie[0], 550,10,null);
        canvas.drawBitmap(vie[0], 600,10,null);*/


        //la perte des coeurs dans une boucle for pour
        for (int i=0; i<3; i++ ){
            int x  = (int)(580 + vie[0].getWidth() * 1.5 * 1);
            int y = 10;
            if (i < lifeTurtle){
                canvas.drawBitmap(vie[0], x, y, null);
            }
            else{
                canvas.drawBitmap(vie[1], x, y, null);
            }
        }

        // taille du score sur la vue
        canvas.drawText("Score : "+ score,20, 60, scorePaint);



        canvas.drawBitmap(bouffe, bouffeX, bouffeY, null);
        canvas.drawBitmap(notbouffe, notbouffeX, notbouffeY, null);

    }
/*-----------------------------------------------------------------------------------------------*/
    //Evenement
/*-----------------------------------------------------------------------------------------------*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch =  true;
            turtleSpeed =  -22;
        }
        return true;
    }
/*-----------------------------------------------------------------------------------------------*/
    //Methodes
/*-----------------------------------------------------------------------------------------------*/

    //pour le fait de manger de la bonne bouffe

    /**
     * cette methodes permet de detecter la position de notre tortue et de sa nourriture
     * @return true si la position est identique
     * @return false si non, donc notre tortue a rate sa proie
     */
    public boolean eatBouffeChecker(int x,int y){
        if(turtleX < x && x < (turtleX + turtle[0].getWidth()) && turtleY < y && y < ( turtleY + turtle[0].getHeight())){
            return true;
        }
        return false;
    }

    /**
     * cette methodes detecte la position de la tortue et des déchets l'eau
     * @return true si la tortue a mangé un déchet
     * @return false si elle l'a évité
     */
    public boolean eatBadBouffeChercker(int x, int y){
        if(turtleX < x && x < (turtleX + turtle[0].getHeight()) && turtleY < y && y < ( turtleY + turtle[0].getHeight())){
            return true;
        }
        return  false;
    }

}
