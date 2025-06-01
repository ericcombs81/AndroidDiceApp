package com.example.ericcombsdicegame;

public class Player {

    private int score;
    private boolean onTheBoard;
    private boolean isCarryingOver = false;
    private boolean isComputer;

    //*******CONSTRUCTOR****************************************************************************
    //**********************************************************************************************

    public Player() {
        this.score = 0; // to debug endgame, set to 9800
        this.onTheBoard = false; // to debug endgame, set to true
    }

    //*******GETTERS and SETTERS********************************************************************
    //**********************************************************************************************

    public void setScore(int score) {

        this.score = score;
    }

    public void setOnTheBoard(boolean onTheBoard) {

        this.onTheBoard = onTheBoard;
    }

    public int getScore() {

        return score;
    }

    public boolean getIsCarryingOver() {
        return isCarryingOver;
    }

    public void setIsCarryingOver(boolean isHe) {

        this.isCarryingOver = isHe;
    }

    public boolean isOnTheBoard() {

        return onTheBoard;
    }

    public boolean getIsComputer() {

        return isComputer;
    }

    public void setIsComputer(boolean c) {

        this.isComputer = c;
    }

    public boolean getIsOnTheBoard() {
        return isOnTheBoard();
    }
}