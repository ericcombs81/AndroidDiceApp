package com.example.ericcombsdicegame;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.os.Handler;

public class ComputerLogic {

    //*******INSTANCE VARIABLES*********************************************************************
    //**********************************************************************************************

    public static int computerSpeed = 100; //100
    public static boolean running = true;
    public static boolean rollAgain = false;
    public static int[] count;
    public static int points = 0;
    public static int newPoints = 0;
    public static boolean isComputersTurn = false;
    public static boolean gameOver = false;

    //*******START OF TURN**************************************************************************
    //*******computerTurnStart()
    //**********************************************************************************************

    public static void computerTurnStart(MainActivity mainActivity) {

        //Visually switch players and flag the start of computer's turn
        GameLogic.humanTurn = false;
        isComputersTurn = true;
        MainActivity.endTurnBtn.setVisibility(View.INVISIBLE);
        MainActivity.rollButton.setVisibility(View.INVISIBLE);
        MainActivity.player1Text.setBackgroundColor(Color.parseColor("#4E4D45"));
        MainActivity.player1Score.setBackgroundColor(Color.parseColor("#4E4D45"));
        MainActivity.computerText.setBackgroundColor(Color.parseColor("#FFEB3B"));
        MainActivity.computerScore.setBackgroundColor(Color.parseColor("#FFEB3B"));
        Utilities.points = 0;
        points = 0;
        Utilities.newPoints = 0;
        newPoints = 0;
        Utilities.setAndDisplayMessage("Computer needs " + (10000 - (GameLogic.computer.getScore())) + " points to win");
        MainActivity.rollScore.setText("");

        // This handler sets a delay after the computer roll
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    computerRoll(mainActivity);
                }
            }, (computerSpeed * 8)); // length of the delay
    }

    //*******ROLLING LOGIC**************************************************************************
    //*******computerRoll(), computerDecisions()
    //**********************************************************************************************

    //This is on a loop until the turn ends.  Roll, then make a decision to hold and roll, hold and quit, or quit.
    public static void computerRoll(MainActivity mainActivity) {
        mainActivity.playRollSound();
        GameLogic.startOfTurn = false;
        Utilities.rollButtonClicked(MainActivity.dice1, MainActivity.dice2, MainActivity.dice3, MainActivity.dice4, MainActivity.dice5, MainActivity.dice6);
        newPoints = 0;
        MainActivity.endTurnBtn.setVisibility(View.INVISIBLE);

        //This handler sets a pause after the computer rolls until it makes a decision and holds dice
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                computerDecisions(mainActivity);
            }
        }, (computerSpeed * 20)); // length of pause after roll until dice are visibly held
    }

    //Decide and execute: Hold and roll, Hold and end, or end
    public static void computerDecisions(MainActivity mainActivity) {

        rollAgain = true;

        // Make an array of the number of each value.  The array is called count[6]
        // It records the count (amount) of each value rolled.  Index zero is the number of 1's rolled from the 6 die.
        count = new int[6];
        int[] count = { 0, 0, 0, 0, 0, 0 };
        int totalCount = 0;

        if(!MainActivity.dice1.isHeld()) {
            int value = MainActivity.dice1.getDiceValue();
            count[value -1]++;
            totalCount++;
        }
        if(!MainActivity.dice2.isHeld()) {
            int value = MainActivity.dice2.getDiceValue();
            count[value -1]++;
            totalCount++;
        }
        if(!MainActivity.dice3.isHeld()) {
            int value = MainActivity.dice3.getDiceValue();
            count[value -1]++;
            totalCount++;
        }
        if(!MainActivity.dice4.isHeld()) {
            int value = MainActivity.dice4.getDiceValue();
            count[value -1]++;
            totalCount++;
        }
        if(!MainActivity.dice5.isHeld()) {
            int value = MainActivity.dice5.getDiceValue();
            count[value -1]++;
            totalCount++;
        }
        if(!MainActivity.dice6.isHeld()) {
            int value = MainActivity.dice6.getDiceValue();
            count[value -1]++;
            totalCount++;
        }

        if(!Utilities.thereArePoints()) {
            pauseThenEnd();
            return;
        }

        if(Utilities.checkOver()) {
            pauseThenEnd();
            return;
        }

        //large straight
        if (count[0] == 1 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1 && count[5] == 1) {
            selectAllOfThese(1);
            selectAllOfThese(2);
            selectAllOfThese(3);
            selectAllOfThese(4);
            selectAllOfThese(5);
            selectAllOfThese(6);
        }

        //small straight 1-5 double 1
        if (count[0] == 2 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1){
            selectAllOfThese(1);
            selectAllOfThese(2);
            selectAllOfThese(3);
            selectAllOfThese(4);
            selectAllOfThese(5);
        }

        //small straight 1-5 double 5
        if (count[0] == 1 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 2) {
            selectAllOfThese(1);
            selectAllOfThese(2);
            selectAllOfThese(3);
            selectAllOfThese(4);
            selectAllOfThese(5);
        }

        //small straight 2-6 double 5
        if (count[0] == 0 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 2) {
            selectAllOfThese(2);
            selectAllOfThese(3);
            selectAllOfThese(4);
            selectAllOfThese(5);
            selectAllOfThese(6);
        }

        //small straight 1-5, double 2
        if(count[0] == 1 && count[1] == 2 && count[2] == 1 && count[3] == 1 && count[4] == 1) {
            selectAllOfThese(1);
            selectOneOfThese(2);
            selectAllOfThese(3);
            selectAllOfThese(4);
            selectAllOfThese(5);
        }
        //small straight 1-5, double 3
        if(count[0] == 1 && count[1] == 1 && count[2] == 2 && count[3] == 1 && count[4] == 1) {
            selectAllOfThese(1);
            selectAllOfThese(2);
            selectOneOfThese(3);
            selectAllOfThese(4);
            selectAllOfThese(5);
        }
        //small straight 1-5, double 4
        if(count[0] == 1 && count[1] == 1 && count[2] == 1 && count[3] == 2 && count[4] == 1) {
            selectAllOfThese(1);
            selectAllOfThese(2);
            selectAllOfThese(3);
            selectOneOfThese(4);
            selectAllOfThese(5);
        }
        //small straight 2-6, double 2
        if(count[0] == 0 && count[1] == 2 && count[2] == 1 && count[3] == 1 && count[4] == 1 && count[5] == 1) {
            selectOneOfThese(2);
            selectAllOfThese(3);
            selectAllOfThese(4);
            selectAllOfThese(5);
            selectAllOfThese(6);
        }
        //small straight 2-6, double 3
        if(count[0] == 0 && count[1] == 1 && count[2] == 2 && count[3] == 1 && count[4] == 1 && count[5] == 1) {
            selectAllOfThese(2);
            selectOneOfThese(3);
            selectAllOfThese(4);
            selectAllOfThese(5);
            selectAllOfThese(6);
        }
        //small straight 2-6, double 4
        if(count[0] == 0 && count[1] == 1 && count[2] == 1 && count[3] == 2 && count[4] == 1 && count[5] == 1) {
            selectAllOfThese(2);
            selectAllOfThese(3);
            selectOneOfThese(4);
            selectAllOfThese(5);
            selectAllOfThese(6);
        }
        //small straight 2-6, double 6
        if(count[0] == 0 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1 && count[5] == 2) {
            selectAllOfThese(2);
            selectAllOfThese(3);
            selectAllOfThese(4);
            selectAllOfThese(5);
            selectOneOfThese(6);
        }

        //So, if there are points and we aren't over, lets hold some 5's and 1's
        selectAllOfThese(1);
        selectAllOfThese(5);
        // and if there are more than three of a kind, hold those dice
        for(int i =0; i<6; i++) {
            if(count[i] >= 3) {
                selectAllOfThese(i+1);
            }
        }

        //figure up how many points are now selected from the above logic. Record it.
        tally();
        //And once they are tallied, hold all selected dice for the next roll.
        holdAllThatAreSelected();

        //If all are held and the total is under 9000, roll again!
        if((MainActivity.dice1.isHeld() || MainActivity.dice1.isSelected()) &&
                (MainActivity.dice2.isHeld() || MainActivity.dice2.isSelected()) &&
                (MainActivity.dice3.isHeld() || MainActivity.dice3.isSelected()) &&
                (MainActivity.dice4.isHeld() || MainActivity.dice4.isSelected()) &&
                (MainActivity.dice5.isHeld() || MainActivity.dice5.isSelected()) &&
                (MainActivity.dice6.isHeld() || MainActivity.dice6.isSelected()) &&
                (GameLogic.computer.getScore() + points + newPoints < 9000)) {
            rollAgain = true;

        } else {

            //If not on the board, this is when to stop (800)
            if (!GameLogic.computer.getIsOnTheBoard()) {
                if (points + newPoints >= 800) {
                    rollAgain = false;
                    GameLogic.computer.setOnTheBoard(true);
                    GameLogic.computer.setScore(GameLogic.computer.getScore() + points);
                    Utilities.updateScoreBoard();
                }
                //if on the board, this is when to stop
            } else {
                if (points + newPoints >= 300) {
                    rollAgain = false;
                    GameLogic.computer.setOnTheBoard(true);
                    GameLogic.computer.setScore(GameLogic.computer.getScore() + points);
                    Utilities.updateScoreBoard();
                }
            }
        }

        if(rollAgain) {
            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                     newPoints=0;
                     computerRoll(mainActivity);
                }
            }, (computerSpeed * 20));

        } else {
            //This bit of code is important for keeping the display from resetting after computer win
            if(!gameOver) {
                pauseThenEnd();
            } else {
                gameOver = false;
            }
        }
    }

    //*******CALCULATIONS***************************************************************************
    //*******tally();
    //**********************************************************************************************

    //Totals the number of rolled points for the computer
    public static void tally() {
        int[] count = {0, 0, 0, 0, 0, 0};
        int totalCount = 0;
        boolean valid = true;
        newPoints = 0;

        //This logic will record the number of each value in the roll (using the count[] array)
        if(MainActivity.dice1.isSelected()) {
            int value = MainActivity.dice1.getDiceValue();
            count[value - 1]++;
            totalCount++;
        }
        if(MainActivity.dice2.isSelected()) {
            int value = MainActivity.dice2.getDiceValue();
            count[value - 1]++;
            totalCount++;
        }
        if(MainActivity.dice3.isSelected()) {
            int value = MainActivity.dice3.getDiceValue();
            count[value - 1]++;
            totalCount++;
        }
        if(MainActivity.dice4.isSelected()) {
            int value = MainActivity.dice4.getDiceValue();
            count[value - 1]++;
            totalCount++;
        }
        if(MainActivity.dice5.isSelected()) {
            int value = MainActivity.dice5.getDiceValue();
            count[value - 1]++;
            totalCount++;
        }
        if(MainActivity.dice6.isSelected()) {
            int value = MainActivity.dice6.getDiceValue();
            count[value - 1]++;
            totalCount++;
        }
        if (count[0] == 1 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1 && count[5] == 1) {
            newPoints += 3000;
        }
        // small straight 1-5 with no double 1's or 5's
        else if (count[0] == 1 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1) {
            newPoints += 1500;
        }
        // small straight 2-6 with no double 1's or 5's
        else if (count[0] == 0 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1 && count[5] == 1) {
            newPoints += 1500;
        }
        // small straight 1-5 with double 1
        else if (count[0] == 2 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1) {
            newPoints += 1600;
        }
        // small straight 1-5 with double 5
        else if (count[0] == 1 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 2) {
            newPoints += 1550;
        }
        // small straight 2-6 with double 5
        else if (count[0] == 0 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 2) {
            newPoints += 1550;
        }
        else {
            for (int i = 0; i < count.length; i++) {
                switch (count[i]) {
                    case 1:
                        if (i == 0) {
                            newPoints += 100;
                        } else if (i == 4) {
                            newPoints += 50;
                        } else {
                            valid = false;
                        }
                        break;
                    case 2:
                        if (i == 0) {
                            newPoints += 200;
                        } else if (i == 4) {
                            newPoints += 100;
                        } else {
                            valid = false;
                        }
                        break;
                    case 3:
                        if (i == 0) {
                            newPoints += 1000;
                        } else {
                            newPoints += 100 * (i + 1);
                        }
                        break;
                    case 4:
                        if (i == 0) {
                            newPoints += 2000;
                        } else {
                            newPoints += 200 * (i + 1);
                        }
                        break;
                    case 5:
                        if (i == 0) {
                            newPoints += 4000;
                        } else {
                            newPoints += 400 * (i + 1);
                        }
                        break;
                    case 6:
                        if (i == 0) {
                            newPoints += 8000;
                        } else {
                            newPoints += 800 * (i + 1);
                        }
                        break;
                }
            }
        }
        //Display the tally
        MainActivity.rollScore.setVisibility(View.VISIBLE);
        points += newPoints;
        newPoints = 0;
        MainActivity.rollScore.setText(String.valueOf(points));
    }

    //*******END TURN*******************************************************************************
    //*******computerTurnEnd(), pauseThenEnd()
    //**********************************************************************************************

    public static void computerTurnEnd() {
        //Visually switch players and flag the start of player's turn
        GameLogic.startOfTurn = true;
        Utilities.points = 0;
        Utilities.newPoints = 0;
        MainActivity.player1Text.setBackgroundColor(Color.parseColor("#FFEB3B"));
        MainActivity.player1Score.setBackgroundColor(Color.parseColor("#FFEB3B"));
        MainActivity.computerText.setBackgroundColor(Color.parseColor("#4E4D45"));
        MainActivity.computerScore.setBackgroundColor(Color.parseColor("#4E4D45"));
        MainActivity.rollButton.setVisibility(View.VISIBLE);
        MainActivity.endTurnBtn.setVisibility(View.INVISIBLE);

        if(GameLogic.player.isOnTheBoard()) {
            MainActivity.messageText.setText("Player 1 needs " + (10000 - (GameLogic.player.getScore())) + " points to win.");
        } else {
            MainActivity.messageText.setText("Player 1 needs 800 points to get on the board.");
        }
        GameLogic.startOfTurn = true;
        Utilities.noPoints = false;
        Utilities.deselectAll();
        Utilities.invalidateAll();
        GameLogic.humanTurn = true;
        MainActivity.rollScore.setText("");
        isComputersTurn = false;
        MainActivity.slower.setVisibility(View.VISIBLE);
        MainActivity.faster.setVisibility(View.VISIBLE);
    }

    public static void pauseThenEnd() {
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!gameOver) {
                    computerTurnEnd();
                } else {
                    //We want to kill the scheduled turn of player here
                    //This bypasses the normal end of turn to a desirable state
                    //for the end of the game if the computer wins
                    gameOver = false;
                    GameLogic.startOfTurn = true;
                    Utilities.points = 0;
                    Utilities.newPoints = 0;
                    MainActivity.player1Text.setBackgroundColor(Color.parseColor("#FFEB3B"));
                    MainActivity.player1Score.setBackgroundColor(Color.parseColor("#FFEB3B"));
                    MainActivity.computerText.setBackgroundColor(Color.parseColor("#4E4D45"));
                    MainActivity.computerScore.setBackgroundColor(Color.parseColor("#4E4D45"));
                    MainActivity.rollButton.setVisibility(View.INVISIBLE);
                    MainActivity.endTurnBtn.setVisibility(View.INVISIBLE);
                    GameLogic.startOfTurn = true;
                    Utilities.noPoints = false;
                    GameLogic.humanTurn = true;
                    MainActivity.rollScore.setText("");
                    isComputersTurn = false;
                    MainActivity.slower.setVisibility(View.INVISIBLE);
                    MainActivity.faster.setVisibility(View.INVISIBLE);
                }
            }
        }, (computerSpeed * 20));//this is a time calculation for the computer pause
    }

    //*******UTILITIES******************************************************************************
    //*******holdAllThatAreSelected(), selectAllOfThese(), selectOneOfThese()
    //**********************************************************************************************

    //Turns yellow selected dice Red and makes them unable to be clicked
    public static void holdAllThatAreSelected() {

        if(MainActivity.dice1.isSelected()) {
            MainActivity.dice1.setIsHeld(true);
        }
        if(MainActivity.dice2.isSelected()){
            MainActivity.dice2.setIsHeld(true);
        }
        if(MainActivity.dice3.isSelected()) {
            MainActivity.dice3.setIsHeld(true);
        }
        if(MainActivity.dice4.isSelected()) {
            MainActivity.dice4.setIsHeld(true);
        }
        if(MainActivity.dice5.isSelected()) {
            MainActivity.dice5.setIsHeld(true);
        }
        if(MainActivity.dice6.isSelected()) {
            MainActivity.dice6.setIsHeld(true);
        }
        Utilities.invalidateAll();
    }

    //Select all dice of a passed in value
    public static void selectAllOfThese(int value) {
        if(!MainActivity.dice1.isHeld() && MainActivity.dice1.getDiceValue() == value) {
            MainActivity.dice1.setIsSelected(true);
        }
        if(!MainActivity.dice2.isHeld() && MainActivity.dice2.getDiceValue() == value) {
            MainActivity.dice2.setIsSelected(true);
        }
        if(!MainActivity.dice3.isHeld() && MainActivity.dice3.getDiceValue() == value) {
            MainActivity.dice3.setIsSelected(true);
        }
        if(!MainActivity.dice4.isHeld() && MainActivity.dice4.getDiceValue() == value) {
            MainActivity.dice4.setIsSelected(true);
        }
        if(!MainActivity.dice5.isHeld() && MainActivity.dice5.getDiceValue() == value) {
            MainActivity.dice5.setIsSelected(true);
        }
        if(!MainActivity.dice6.isHeld() && MainActivity.dice6.getDiceValue() == value) {
            MainActivity.dice6.setIsSelected(true);
        }
        Utilities.invalidateAll();
    }

    //Select just one die of a passed in value
    public static void selectOneOfThese(int value) {
        if(!MainActivity.dice1.isHeld() && MainActivity.dice1.getDiceValue() == value) {
            MainActivity.dice1.setIsSelected(true);
            return;
        }
        if(!MainActivity.dice2.isHeld() && MainActivity.dice2.getDiceValue() == value) {
            MainActivity.dice2.setIsSelected(true);
            return;
        }
        if(!MainActivity.dice3.isHeld() && MainActivity.dice3.getDiceValue() == value) {
            MainActivity.dice3.setIsSelected(true);
            return;
        }
        if(!MainActivity.dice4.isHeld() && MainActivity.dice4.getDiceValue() == value) {
            MainActivity.dice4.setIsSelected(true);
            return;
        }
        if(!MainActivity.dice5.isHeld() && MainActivity.dice5.getDiceValue() == value) {
            MainActivity.dice5.setIsSelected(true);
            return;
        }
        if(!MainActivity.dice6.isHeld() && MainActivity.dice6.getDiceValue() == value) {
            MainActivity.dice6.setIsSelected(true);
            return;
        }
        Utilities.invalidateAll();
    }

}


