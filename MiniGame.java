package com.example.ericcombsdicegame;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.media.MediaPlayer;

public class MiniGame {

    //*******INSTANCE VARIABLES*********************************************************************
    //**********************************************************************************************

    public static int miniGameHitsLeft;
    public static int bulletsLeft;
    public static MediaPlayer mediaPlayer;
    private static MediaPlayer mp2;

    //*******INITIALIZE*****************************************************************************
    //**********************************************************************************************

    public static void initialize(Context context) {
       if(mp2 != null) {
           mp2.release();
           mp2 = null;
       }
        miniGameHitsLeft = 10; //10
        bulletsLeft = 28; // 28
        Utilities.holdAll();
        Utilities.invalidateAll();
        MainActivity.miniHits.setVisibility(View.VISIBLE);
        MainActivity.bulletsLeft.setVisibility(View.VISIBLE);
        //MainActivity.dice1.setVisibility(View.INVISIBLE);
        MainActivity.dice2.setVisibility(View.INVISIBLE);
        MainActivity.dice3.setVisibility(View.INVISIBLE);
        MainActivity.dice4.setVisibility(View.INVISIBLE);
        MainActivity.dice5.setVisibility(View.INVISIBLE);
        //MainActivity.dice6.setVisibility(View.INVISIBLE);
        MainActivity.player1Text.setVisibility(View.INVISIBLE);
        MainActivity.player1Score.setVisibility(View.INVISIBLE);
        MainActivity.computerScore.setVisibility(View.INVISIBLE);
        MainActivity.computerText.setVisibility(View.INVISIBLE);
        MainActivity.rollScore.setVisibility(View.INVISIBLE);
        MainActivity.rollButton.setVisibility(View.INVISIBLE);
        MainActivity.endTurnBtn.setVisibility(View.INVISIBLE);
        MainActivity.rulesBtn.setVisibility(View.INVISIBLE);
        MainActivity.resetButton.setVisibility(View.INVISIBLE);
        MainActivity.messageText.setVisibility(View.INVISIBLE);
        MainActivity.miniGameButton.setVisibility(View.INVISIBLE);
        MainActivity.slower.setVisibility(View.INVISIBLE);
        MainActivity.faster.setVisibility(View.INVISIBLE);
        MainActivity.speed.setVisibility(View.INVISIBLE);
        MainActivity.imageX = (int) MainActivity.dice6.getX();
        MainActivity.imageY = (int) MainActivity.dice6.getY();
        MainActivity.dice1X = (int) MainActivity.dice1.getX();
        MainActivity.dice1Y = (int) MainActivity.dice1.getY();
        MainActivity.miniHits.setText("Hits Left: " + miniGameHitsLeft);
        MainActivity.bulletsLeft.setText("Bullets Left: " + bulletsLeft);
        MainActivity.fire.setVisibility(View.VISIBLE);
        mp2 = MediaPlayer.create(context, R.raw.saber);
        mp2.start();
        mediaPlayer = MediaPlayer.create(context, R.raw.music);
        mediaPlayer.setLooping(true);
        MainActivity.taps = 0;
        mediaPlayer.start();
    }

    //*******MAIN LOOP (Called continuously from MainActivity Timer*********************************
    //**********************************************************************************************

    public static void loop(Context context) {

        //imageX is the 6th die (far right).  This code makes it move
        MainActivity.imageX += (MainActivity.velocity * MainActivity.directionX);
        MainActivity.imageY += (MainActivity.velocity * MainActivity.directionY);
        MainActivity.dice6.setX(MainActivity.imageX);
        MainActivity.dice6.setY(MainActivity.imageY);

        if ((MainActivity.imageX + MainActivity.dice6.getWidth()) > MainActivity.screenX || MainActivity.imageX < MainActivity.screenX / 2.1) {
            MainActivity.directionX = MainActivity.directionX * -1;
        }
        if ((MainActivity.imageY + MainActivity.dice6.getHeight()) > MainActivity.screenY || MainActivity.imageY < 0) {
            MainActivity.directionY = MainActivity.directionY * -1;
        }

        //Check for collisions between the bullets and the enemy die
        if(MainActivity.bulletExists == true ) {
            if(MainActivity.fireView.getX() <= MainActivity.screenX) {
                MainActivity.fireView.setX(MainActivity.fireView.getX() + MainActivity.bulletSpeed);
                checkForCollision(context);
            } else {
                MainActivity.bulletExists = false;
                ViewGroup parent = (ViewGroup) MainActivity.fireView.getParent();
                parent.removeView(MainActivity.fireView);
                MainActivity.fireView = null;
                if(MiniGame.bulletsLeft == 0) {
                    MiniGame.youLost();
                }
            }
        }
    }

    //*******COLLISIONS*****************************************************************************
    //**********************************************************************************************

    public static void checkForCollision(Context context) {
        // we will start with collision = false and change it if it does happen
        boolean collision = false;
        // find the bullet
        int bulletX = (int) MainActivity.fireView.getX();
        int bulletY = (int) MainActivity.fireView.getY();
        //find the enemy
        int enemyX = (int) MainActivity.dice6.getX();
        int enemyY = (int) MainActivity.dice6.getY();
        int enemyWidth = MainActivity.dice6.getWidth();
        int enemyHeight = MainActivity.dice6.getHeight();

        //See if the bullet and enemy occupy the same space
        if(bulletX >= enemyX - enemyWidth/2 - 5 && bulletX <= enemyX + enemyWidth/2 + 5 && bulletY >= enemyY - enemyWidth/2 - 5 && bulletY <= enemyY + enemyWidth/2 + 5 ){
            collision = true;
        }

        if(collision) {
            collisionEvent(context);
        }
    }

    //Play explosion sound, destroy bullet, speed up the enemy by 1, decrease hit counter, check for win
    public static void collisionEvent(Context context) {
        MainActivity.playExplosionSound(context);
        MainActivity.bulletExists = false;
        ViewGroup parent = (ViewGroup) MainActivity.fireView.getParent();
        parent.removeView(MainActivity.fireView);
        MainActivity.fireView = null;
        MainActivity.velocity += 1;
        miniGameHitsLeft -= 1;
        MainActivity.miniHits.setText("Hits Left: " + miniGameHitsLeft);
        if(miniGameHitsLeft == 0) {
            youWon();
        }
    }

    //*******END MINIGAME***************************************************************************
    //*******youWon(), youLost()
    //**********************************************************************************************

    //You are the champion, my friend.  You kept on fighting, till the end.
    public static void youWon() {
        MainActivity.rollScore.setText("YOU WON!");
        MainActivity.rollScore.setVisibility(View.VISIBLE);
        MainActivity.miniHits.setVisibility(View.INVISIBLE);
        MainActivity.bulletsLeft.setVisibility(View.INVISIBLE);
        MainActivity.fire.setVisibility(View.INVISIBLE);
        MainActivity.miniGameStarted = false;
        MainActivity.dice6.setVisibility(View.INVISIBLE);
        if(MainActivity.fireView != null) {
            ViewGroup parent = (ViewGroup) MainActivity.fireView.getParent();
            parent.removeView(MainActivity.fireView);
            MainActivity.fireView = null;
        }
        //Give players a choice of what game to play next, and stop the epic soundtrack
        //Display very cheesy "Good Job!" graphic because the assignment requires it.
        MainActivity.miniGameButton.setText("Play Mini Game Again!");
        MainActivity.miniGameButton.setVisibility(View.VISIBLE);
        MainActivity.resetButton.setText("Play Dice Game Again!");
        MainActivity.resetButton.setVisibility(View.VISIBLE);
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        MainActivity.goodjob.setVisibility(View.VISIBLE);
    }

    //Give players a choice of what game to play next, and stop the epic soundtrack
    public static void youLost(){

        //That last bullet might hit and you could still win
        //Wait until it is gone.  Then check the number of hits
        while(MainActivity.fireView != null) {

        }
        //Then see if the last bullet landed
        if(miniGameHitsLeft == 0) {
            youWon();
            return;
        }
        //No luck.  Last bullet missed!
        MainActivity.rollScore.setText("YOU LOST!");
        MainActivity.rollScore.setVisibility(View.VISIBLE);
        MainActivity.miniGameButton.setText("Play Mini Game Again!");
        MainActivity.miniGameButton.setVisibility(View.VISIBLE);
        MainActivity.resetButton.setText("Play Dice Game Again!");
        MainActivity.resetButton.setVisibility(View.VISIBLE);
        MainActivity.fire.setVisibility(View.INVISIBLE);
        MainActivity.dice6.setVisibility(View.INVISIBLE);
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
