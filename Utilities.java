package com.example.ericcombsdicegame;

import android.view.View;

public class Utilities {


    //*******INSTANCE VARIABLES*********************************************************************
    //**********************************************************************************************

    //Point Values
    public static int points = 0;
    public static int newPoints = 0;
    private static int carryOver = 0;
    private static boolean carryOverPresent = false;
    public static boolean noPoints = false;
    private static int rolledPoints = 0; // for checking points of a roll to see if player rolled over

    //Not used, but I deleted it and went down a two hour rabbit hole, then just put it back for good looks
    private static int numberCarryOverDice = 0;

    public static boolean rolledOver = false;
    public static boolean gameOver = false;
    public static boolean veryBeginning = true;

    //*******ROLLS**********************************************************************************
    //*******rollButtonClicked(), rollDice()
    //**********************************************************************************************


    public static void rollButtonClicked(DiceView dice1, DiceView dice2, DiceView dice3, DiceView dice4, DiceView dice5, DiceView dice6) {
        rollButtonVisible(false);

        //This is unused.  It grabs the XY of Dice1 upon initialization in case
        //I add up/down functionality to it in the mini game later on.  I want
        //Dice1 to get shot at and have to dodge.  Then you can use these calculations
        //to put it back in it's correct place when the dice game resumes
        if(veryBeginning) {
            MainActivity.origDice1X = (int) MainActivity.dice1.getX();
            MainActivity.origDice1Y = (int) MainActivity.dice1.getY();
            MainActivity.origDice6X = (int) MainActivity.dice6.getX();
            MainActivity.origDice6Y = (int) MainActivity.dice6.getY();
            veryBeginning = false;
        }

        if(GameLogic.startOfTurn) {
            MainActivity.rollScore.setText("");
        }
        rolledOver = false;
        points += newPoints;
        newPoints = 0;

        endTurnButtonVisible(true);

        if(GameLogic.startOfTurn) {
            // Roll the dice that aren't selected and redraw
            if(!dice1.isSelected()) {
                rollDice(dice1);
            }
            if(!dice2.isSelected()) {
                rollDice(dice2);
            }
            if(!dice3.isSelected()) {
                rollDice(dice3);
            }
            if(!dice4.isSelected()) {
                rollDice(dice4);
            }
            if(!dice5.isSelected()) {
                rollDice(dice5);
            }
            if(!dice6.isSelected()) {
                rollDice(dice6);
            }
            points = 0;
            carryOver = 0;
            carryOverPresent = false;
            GameLogic.startOfTurn = false;
            //if this ISN'T the start of a turn
        } else {
            if(ComputerLogic.isComputersTurn) {
                ComputerLogic.points += ComputerLogic.newPoints;
                ComputerLogic.newPoints = 0;
                invalidateAll();
            }
            points  += newPoints;
            newPoints = 0;
            invalidateAll();

            //If you have successfully used all 6 die and want to roll all again
            if(!ComputerLogic.isComputersTurn) {
                if((dice1.isHeld() || dice1.isSelected()) &&
                    (dice2.isHeld() || dice2.isSelected()) &&
                    (dice3.isHeld() || dice3.isSelected()) &&
                    (dice4.isHeld() || dice4.isSelected()) &&
                    (dice5.isHeld() || dice5.isSelected()) &&
                    (dice6.isHeld() || dice6.isSelected()) &&
                    isValidSelection()) {
                deselectAll();
                MainActivity.rollScore.setText(String.valueOf(points));
                }
            }

            //Same code as above, just for computer's turn
            //If computer has successfully used all 6 die and wants to roll all again
            if((dice1.isHeld() || dice1.isSelected()) &&
                    (dice2.isHeld() || dice2.isSelected()) &&
                    (dice3.isHeld() || dice3.isSelected()) &&
                    (dice4.isHeld() || dice4.isSelected()) &&
                    (dice5.isHeld() || dice5.isSelected()) &&
                    (dice6.isHeld() || dice6.isSelected()) &&
                    ComputerLogic.isComputersTurn) {
                deselectAll();
                ComputerLogic.points = ComputerLogic.points + ComputerLogic.newPoints;
                ComputerLogic.newPoints = 0;
                MainActivity.rollScore.setText(String.valueOf(ComputerLogic.points));
            }


            // Roll the dice that aren't selected or held and redraw
            if (!dice1.isSelected() && !dice1.isHeld()) {
                rollDice(dice1);
            } else {
                dice1.setIsHeld(true);
                dice1.setIsSelected(false);
            }
            if (!dice2.isSelected() && !dice2.isHeld()) {
                rollDice(dice2);
            } else {
                dice2.setIsHeld(true);
                dice2.setIsSelected(false);
            }
            if (!dice3.isSelected() && !dice3.isHeld()) {
                rollDice(dice3);
            } else {
                dice3.setIsHeld(true);
                dice3.setIsSelected(false);
            }
            if (!dice4.isSelected() && !dice4.isHeld()) {
                rollDice(dice4);
            } else {
                dice4.setIsHeld(true);
                dice4.setIsSelected(false);
            }
            if (!dice5.isSelected() && !dice5.isHeld()) {
                rollDice(dice5);
            } else {
                dice5.setIsHeld(true);
                dice5.setIsSelected(false);
            }
            if (!dice6.isSelected() && !dice6.isHeld()) {
                rollDice(dice6);
            } else {
                dice6.setIsHeld(true);
                dice6.setIsSelected(false);
            }
        }

        if(!thereArePoints()) {
            MainActivity.rollScore.setText("No Points");
            noPoints = true;
            rollButtonVisible(false);
            if(!MainActivity.dice1.isHeld()) {
                invalidateAll();
            }
            if(!MainActivity.dice2.isHeld()) {
                invalidateAll();
            }
            if(!MainActivity.dice3.isHeld()) {
                invalidateAll();
            }
            if(!MainActivity.dice4.isHeld()) {
                invalidateAll();
            }
            if(!MainActivity.dice5.isHeld()) {
                invalidateAll();
            }
            if(!MainActivity.dice6.isHeld()) {
                invalidateAll();
            }
            points = 0;
            GameLogic.startOfTurn = true;
        }
        checkOver();
    }

    public static void rollDice(DiceView diceView) {

        // Generate a random value between 1 and 6
        int newValue = (int) (Math.random() * 6) + 1;

        // Set the new value for the DiceView
        diceView.setDiceValue(newValue);

        // Redraw the DiceView
        diceView.invalidate();
    }

    //*******CLICKS*********************************************************************************
    //*******diceClicked(), endRoundButtonPushed()
    //**********************************************************************************************

    //By MainActivity logic, this only activates if it is the human player's turn
    public static void diceClicked(DiceView die) {

        //toggle selected and redraw
        die.setIsSelected(!die.isSelected());
        die.invalidate();

        if(isValidSelection()) {
            rollButtonVisible(true);
            MainActivity.rollScore.setText(String.valueOf(points + newPoints));
        } else {
            rollButtonVisible(false);
            MainActivity.rollScore.setText("Invalid selection");
        }
    }

    public static void endRoundButtonPushed(MainActivity mainActivity) {
        GameLogic.humanTurn = false;
        ComputerLogic.isComputersTurn = true;
        noPoints=false;
        unHoldAll();
        rolledOver = false;
        invalidateAll();
        MainActivity.slower.setVisibility(View.INVISIBLE);
        MainActivity.faster.setVisibility(View.INVISIBLE);

        if(!isValidSelection()) {
            deselectAll();
            carryOver = 0;
            carryOverPresent = false;
            numberCarryOverDice = 0;
            points = 0;
            newPoints = 0;
            updateScoreBoard();
            ComputerLogic.computerTurnStart(mainActivity);
            return;
        }
        // if current player hits exactly 10000
        else if (GameLogic.player.getScore() + points + newPoints == 10000) {
            endGame();
            return;
        }

        //You will only reach here if the ending player had a valid selection and hasn't won yet

        //If finishing player was on the board
        if (isValidSelection() && GameLogic.player.isOnTheBoard()) {
            GameLogic.player.setScore(GameLogic.player.getScore() + points + newPoints);
            updateScoreBoard();
            carryOverPresent = true;
            numberCarryOverDice = 0;
            carryOver = points + newPoints;
            int numberHeld = 0;
            deselectAll();
            ComputerLogic.computerTurnStart(mainActivity);
            return;
        }
        // if finishing player was not on the board
        else {
            if (isValidSelection() && points + newPoints >= 800) {
                GameLogic.player.setScore(GameLogic.player.getScore() + points + newPoints);
                GameLogic.player.setOnTheBoard(true);
                updateScoreBoard();
                carryOverPresent = true;
                numberCarryOverDice = 0;
                carryOver = points + newPoints;
                deselectAll();
                ComputerLogic.computerTurnStart(mainActivity);
                return;
            } else {
                deselectAll();
                ComputerLogic.computerTurnStart(mainActivity);
                return;
            }

        }
    }

    //*******CALCULATIONS***************************************************************************
    //*******checkOver(), isValidSelection(), thereArePoints()
    //**********************************************************************************************

    //This method checks if someone rolled over 10,000.  If so, it returns true
    //Also, it detects if someone won, and calls methods to end the game.
    public static boolean checkOver() {

        //i.e., if the dice were rolled and valid point combos exist.
        //The next big chunk will detect this.
        if(thereArePoints()) {

            int[] count = { 0, 0, 0, 0, 0, 0 };
            int totalCount = 0;
            rolledPoints = 0;

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
            if (totalCount == 0) {

            } else if (count[0] == 1 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1 && count[5] == 1) {
                rolledPoints += 3000;
            }
            // small straight 1-5 with no double 1's or 5's
            else if (count[0] == 1 && count[1] >= 1 && count[2] >= 1 && count[3] >= 1 && count[4] == 1) {
                rolledPoints += 1500;
            }
            // small straight 2-6 with no double 1's or 5's
            else if (count[0] == 0 && count[1] >= 1 && count[2] >= 1 && count[3] >= 1 && count[4] == 1 && count[5] >= 1) {
                rolledPoints += 1500;
            }
            // small straight 1-5 with double 1
            else if (count[0] == 2 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1) {
                rolledPoints += 1600;
            }
            // small straight 1-5 with double 5
            else if (count[0] == 1 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 2) {
                rolledPoints += 1550;
            }
            // small straight 2-6 with double 5
            else if (count[0] == 0 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 2) {
                rolledPoints += 1550;
            }
            else {
                for (int i = 0; i < count.length; i++) {
                    switch (count[i]) {
                        case 1:
                            if (i == 0) {
                                rolledPoints += 100;
                            } else if (i == 4) {
                                rolledPoints += 50;
                            } else {

                            }
                            break;
                        case 2:
                            if (i == 0) {
                                rolledPoints += 200;
                            } else if (i == 4) {
                                rolledPoints += 100;
                            } else {

                            }
                            break;
                        case 3:
                            if (i == 0) {
                                rolledPoints += 1000;
                            } else {
                                rolledPoints += 100 * (i + 1);
                            }
                            break;
                        case 4:
                            if (i == 0) {
                                rolledPoints += 2000;
                            } else {
                                rolledPoints += 200 * (i + 1);
                            }
                            break;
                        case 5:
                            if (i == 0) {
                                rolledPoints += 4000;
                            } else {
                                rolledPoints += 400 * (i + 1);
                            }
                            break;
                        case 6:
                            if (i == 0) {
                                rolledPoints += 8000;
                            } else {
                                rolledPoints += 800 * (i + 1);
                            }
                            break;
                    }
                }
            }

            //Now see who is playing...human or computer.
            int computerScore = GameLogic.computer.getScore() + ComputerLogic.points + rolledPoints;
            int playerScore = GameLogic.player.getScore() + points + rolledPoints;
            int checkedScore;
            if(GameLogic.humanTurn) {
                checkedScore = playerScore;
            } else {
                checkedScore = computerScore;
            }
            System.out.println(checkedScore);

            //Someone just won.  End the game.
            if(checkedScore == 10000) {
                if(GameLogic.humanTurn) {
                    GameLogic.player.setScore(10000);
                } else {
                    GameLogic.computer.setScore(10000);
                }
                ComputerLogic.gameOver=true;
                endGame();
            }

            //Someone just rolled over 10000 points.  End their turn.
            if (checkedScore > 10000) {
                //change message
                MainActivity.messageText.setText("Over 10,000 points!");
                //make roll button invisible
                rollButtonVisible(false);
                //hold all dice
                deselectAll();
                GameLogic.startOfTurn = true;
                MainActivity.rollScore.setText(String.valueOf(points + rolledPoints));
                rolledOver = true;
                invalidateAll();
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    //Checks to see if the selected (yellow) dice form a valid scoring selection
    public static boolean isValidSelection() {
        int[] count = {0, 0, 0, 0, 0, 0};
        int totalCount = 0;
        boolean valid = true;
        newPoints = 0;

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
        if (totalCount == 0) {
            valid = false;
        } else if (count[0] == 1 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1 && count[5] == 1) {
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

        //Display the number of points selected.
        MainActivity.rollScore.setVisibility(View.VISIBLE);
        MainActivity.rollScore.setText(String.valueOf(newPoints));
        return valid;
    }

    //After a roll, this checks to see if there exists scoring dice
    public static boolean thereArePoints() {

        boolean thereArePoints = false;

        int[] count = { 0, 0, 0, 0, 0, 0 };
        int totalCount = 0;
        rolledPoints = 0;

        if(MainActivity.dice1.isAvailable()) {
            int value = MainActivity.dice1.getDiceValue();
            count[value - 1]++;
            totalCount++;
        }

        if(MainActivity.dice2.isAvailable()) {
            int value = MainActivity.dice2.getDiceValue();
            count[value - 1]++;
            totalCount++;
        }

        if(MainActivity.dice3.isAvailable()) {
            int value = MainActivity.dice3.getDiceValue();
            count[value - 1]++;
            totalCount++;
        }

        if(MainActivity.dice4.isAvailable()) {
            int value = MainActivity.dice4.getDiceValue();
            count[value - 1]++;
            totalCount++;
        }

        if(MainActivity.dice5.isAvailable()) {
            int value = MainActivity.dice5.getDiceValue();
            count[value - 1]++;
            totalCount++;
        }

        if(MainActivity.dice6.isAvailable()) {
            int value = MainActivity.dice6.getDiceValue();
            count[value - 1]++;
            totalCount++;
        }

        if (totalCount == 0) {

        } else if (count[0] == 1 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1 && count[5] == 1) {
            rolledPoints += 3000;
        }
        // small straight 1-5 with no double 1's or 5's
        else if (count[0] == 1 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1) {
            rolledPoints += 1500;
        }
        // small straight 2-6 with no double 1's or 5's
        else if (count[0] == 0 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1 && count[5] == 1) {
            rolledPoints += 1500;
        }
        // small straight 1-5 with double 1
        else if (count[0] == 2 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1) {
            rolledPoints += 1600;
        }
        // small straight 1-5 with double 5
        else if (count[0] == 1 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 2) {
            rolledPoints += 1550;
        }
        // small straight 2-6 with double 5
        else if (count[0] == 0 && count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 2) {
            rolledPoints += 1550;
        }

        else {
            for (int i = 0; i < count.length; i++) {
                switch (count[i]) {
                    case 1:
                        if (i == 0) {
                            rolledPoints += 100;
                        } else if (i == 4) {
                            rolledPoints += 50;
                        } else {

                        }
                        break;
                    case 2:
                        if (i == 0) {
                            rolledPoints += 200;
                        } else if (i == 4) {
                            rolledPoints += 100;
                        } else {

                        }
                        break;
                    case 3:
                        if (i == 0) {
                            rolledPoints += 1000;
                        } else {
                            rolledPoints += 100 * (i + 1);
                        }
                        break;
                    case 4:
                        if (i == 0) {
                            rolledPoints += 2000;
                        } else {
                            rolledPoints += 200 * (i + 1);
                        }
                        break;
                    case 5:
                        if (i == 0) {
                            rolledPoints += 4000;
                        } else {
                            rolledPoints += 400 * (i + 1);
                        }
                        break;
                    case 6:
                        if (i == 0) {
                            rolledPoints += 8000;
                        } else {
                            rolledPoints += 800 * (i + 1);
                        }
                        break;
                }
            }
        }
        if(rolledPoints > 0) {
            thereArePoints = true;
        }
        else {
            thereArePoints = false;
        }

        return thereArePoints;
    }

    //*******MISC UTILITIES*************************************************************************
    //*******invalidateAll(), setAndDisplayMessage(), updateScoreBoard(), unHoldAll(),
    //*******deselectAll(), rollButtonVisible(), endTurnButtonVisible(), endGame()
    //**********************************************************************************************

    //Otherwise known as redraw the dice
    public static void invalidateAll() {
        MainActivity.dice1.invalidate();
        MainActivity.dice2.invalidate();
        MainActivity.dice3.invalidate();
        MainActivity.dice4.invalidate();
        MainActivity.dice5.invalidate();
        MainActivity.dice6.invalidate();
    }

    public static void setAndDisplayMessage(String message) {
        MainActivity.messageText.setText(message);
    }

    public static void updateScoreBoard() {
        MainActivity.player1Score.setText(String.valueOf(GameLogic.player.getScore()));
        MainActivity.computerScore.setText(String.valueOf(GameLogic.computer.getScore()));
    }

    public static void unHoldAll() {
        MainActivity.dice1.setIsHeld(false);
        MainActivity.dice2.setIsHeld(false);
        MainActivity.dice3.setIsHeld(false);
        MainActivity.dice4.setIsHeld(false);
        MainActivity.dice5.setIsHeld(false);
        MainActivity.dice6.setIsHeld(false);
    }

    public static void holdAll() {
        MainActivity.dice1.setIsHeld(true);
        MainActivity.dice2.setIsHeld(true);
        MainActivity.dice3.setIsHeld(true);
        MainActivity.dice4.setIsHeld(true);
        MainActivity.dice5.setIsHeld(true);
        MainActivity.dice6.setIsHeld(true);
    }

    public static void deselectAll() {
        MainActivity.dice1.setIsSelected(false);
        MainActivity.dice2.setIsSelected(false);
        MainActivity.dice3.setIsSelected(false);
        MainActivity.dice4.setIsSelected(false);
        MainActivity.dice5.setIsSelected(false);
        MainActivity.dice6.setIsSelected(false);
        unHoldAll();
        invalidateAll();
    }

    public static void rollButtonVisible(boolean b) {
        if(!b) {
            MainActivity.rollButton.setVisibility(View.INVISIBLE);
        } else {
            MainActivity.rollButton.setVisibility(View.VISIBLE);
        }
    }

    public static void endTurnButtonVisible(boolean b) {
        if(!b) {
            MainActivity.endTurnBtn.setVisibility(View.INVISIBLE);
        } else {
            MainActivity.endTurnBtn.setVisibility(View.VISIBLE);
        }
    }

    //Makes the computer faster during its turns
    public static void faster() {
        if(ComputerLogic.computerSpeed == 400) {
            ComputerLogic.computerSpeed = 200;
            MainActivity.speed.setText("Computer Speed: Very Slow");
        }
        else if(ComputerLogic.computerSpeed == 200) {
            ComputerLogic.computerSpeed = 150;
            MainActivity.speed.setText("Computer Speed: Slow");
        }
        else if(ComputerLogic.computerSpeed == 150) {
            ComputerLogic.computerSpeed = 100;
            MainActivity.speed.setText("Computer Speed: Normal");
        }
        else if(ComputerLogic.computerSpeed == 100) {
            ComputerLogic.computerSpeed = 80;
            MainActivity.speed.setText("Computer Speed: Quick");
        }
        else if(ComputerLogic.computerSpeed == 80) {
            ComputerLogic.computerSpeed = 50;
            MainActivity.speed.setText("Computer Speed: Fast");
        }
        else if(ComputerLogic.computerSpeed == 50) {
            ComputerLogic.computerSpeed = 1;
            MainActivity.speed.setText("Computer Speed: The Flash");
        }
        else if(ComputerLogic.computerSpeed == 1) {
            ComputerLogic.computerSpeed = 1;
            MainActivity.speed.setText("Computer Speed: The Flash");
        }
    }

    //makes the computer slower during its turns.
    public static void slower() {
        if(ComputerLogic.computerSpeed == 1) {
            ComputerLogic.computerSpeed = 50;
            MainActivity.speed.setText("Computer Speed: The Flash");
        }
        else if(ComputerLogic.computerSpeed == 50) {
            ComputerLogic.computerSpeed = 80;
            MainActivity.speed.setText("Computer Speed: Quick");
        }
        else if(ComputerLogic.computerSpeed == 80) {
            ComputerLogic.computerSpeed = 100;
            MainActivity.speed.setText("Computer Speed: Normal");
        }
        else if(ComputerLogic.computerSpeed == 100) {
            ComputerLogic.computerSpeed = 150;
            MainActivity.speed.setText("Computer Speed: Slow");
        }
        else if(ComputerLogic.computerSpeed == 150) {
            ComputerLogic.computerSpeed = 200;
            MainActivity.speed.setText("Computer Speed: Very Slow");
        }
        else if(ComputerLogic.computerSpeed == 200) {
            ComputerLogic.computerSpeed = 400;
            MainActivity.speed.setText("Computer Speed: Dying Snail");
        }
        else if(ComputerLogic.computerSpeed == 400) {
            ComputerLogic.computerSpeed = 400;
            MainActivity.speed.setText("Computer Speed: Dying Snail");
        }
    }

    //....end....game....do I really need to tell you that?
    public static void endGame() {
        updateScoreBoard();
        if(GameLogic.player.getScore() == 10000) {
            setAndDisplayMessage("Player 1 WINS!");
            MainActivity.rollScore.setText("10,000!!!");
        } else {
            setAndDisplayMessage("Computer wins.");
            MainActivity.rollScore.setText("10,000");
        }
        endTurnButtonVisible(false);
        rollButtonVisible(false);
        MainActivity.dice1.setIsHeld(true);
        MainActivity.dice2.setIsHeld(true);
        MainActivity.dice3.setIsHeld(true);
        MainActivity.dice4.setIsHeld(true);
        MainActivity.dice5.setIsHeld(true);
        MainActivity.dice6.setIsHeld(true);
        invalidateAll();
        gameOver = true;
        MainActivity.resetButton.setVisibility(View.VISIBLE);
        MainActivity.miniGameButton.setVisibility(View.VISIBLE);
    }

    //Resets the dice game to start again from the beginning
    public static void reset() {
        points = 0;
        rolledPoints = 0;
        GameLogic.player.setScore(0);
        GameLogic.computer.setScore(0);
        GameLogic.player.setOnTheBoard(false);
        GameLogic.computer.setOnTheBoard(false);
        updateScoreBoard();
        unHoldAll();
        deselectAll();
        invalidateAll();
        GameLogic.initialize();
        MainActivity.rollButton.setVisibility(View.VISIBLE);
        gameOver = false;
        MainActivity.player1Text.setVisibility(View.VISIBLE);
        MainActivity.player1Score.setVisibility(View.VISIBLE);
        MainActivity.computerText.setVisibility(View.VISIBLE);
        MainActivity.computerScore.setVisibility(View.VISIBLE);
        MainActivity.dice1.setVisibility(View.VISIBLE);
        MainActivity.dice2.setVisibility(View.VISIBLE);
        MainActivity.dice3.setVisibility(View.VISIBLE);
        MainActivity.dice4.setVisibility(View.VISIBLE);
        MainActivity.dice5.setVisibility(View.VISIBLE);
        MainActivity.dice6.setVisibility(View.VISIBLE);
        MainActivity.messageText.setVisibility(View.VISIBLE);
        MainActivity.rulesBtn.setVisibility(View.VISIBLE);
        MainActivity.slower.setVisibility(View.VISIBLE);
        MainActivity.faster.setVisibility(View.VISIBLE);
        MainActivity.rollButton.setVisibility(View.VISIBLE);
        MainActivity.speed.setVisibility(View.VISIBLE);
    }
}
