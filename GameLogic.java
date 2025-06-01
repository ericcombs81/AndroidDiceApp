package com.example.ericcombsdicegame;

import android.graphics.Color;
import android.view.View;

public class GameLogic {

    //*******INSTANCE VARIABLES*********************************************************************
    //**********************************************************************************************

    public static Player player;
    public static Player computer;
    public static String message;
    public static boolean gameStarted = false;
    public static boolean startOfTurn = true;
    public static boolean humanTurn = true;

    //*******INITIALIZE***************************************************************************
    //**********************************************************************************************

    public static void initialize() {

        MainActivity.dice1.setX(MainActivity.origDice1X);
        MainActivity.dice1.setY(MainActivity.origDice1Y);
        MainActivity.dice6.setX(MainActivity.origDice6X);
        MainActivity.dice6.setY(MainActivity.origDice6Y);
        player = new Player();
        computer = new Player();
        player.setIsComputer(false);
        computer.setIsComputer(true);
        message = new String("Press \"Roll\" to begin");
        Utilities.setAndDisplayMessage(message);
        Utilities.updateScoreBoard();
        MainActivity.resetButton.setVisibility(View.INVISIBLE);
        MainActivity.endTurnBtn.setVisibility(View.INVISIBLE);
        MainActivity.rollScore.setVisibility(View.INVISIBLE);
        MainActivity.miniGameButton.setVisibility(View.INVISIBLE);
        MainActivity.bulletsLeft.setVisibility(View.INVISIBLE);
        MainActivity.miniHits.setVisibility(View.INVISIBLE);
        MainActivity.fire.setVisibility(View.INVISIBLE);
        MainActivity.miniGameButton.setVisibility(View.INVISIBLE);
        MainActivity.player1Text.setBackgroundColor(Color.parseColor("#4E4D45"));
        MainActivity.player1Score.setBackgroundColor(Color.parseColor("#4E4D45"));
        MainActivity.goodjob.setVisibility(View.INVISIBLE);
        ComputerLogic.computerSpeed = 100;
        MainActivity.speed.setText("Computer Speed: Normal");
        }
    }
