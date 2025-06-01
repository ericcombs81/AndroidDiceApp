package com.example.ericcombsdicegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.content.Context;

import java.util.Random;

// DiceView.java
public class DiceView extends View {

    //*******INSTANCE VARIABLES*********************************************************************
    //**********************************************************************************************

    private int value;
    private boolean isSelected;
    private boolean isHeld;

    //*******CONSTRUCTORS***************************************************************************
    //**********************************************************************************************

    // Constructor
    public DiceView(Context context) {
        super(context);
        value = new Random().nextInt(6) + 1;
        isSelected = false;
        isHeld = false;
    }

    public DiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        value = new Random().nextInt(6) + 1;
        isSelected = false;
        isHeld = false;
    }

    //*******GETTERS AND SETTERS***************************************************************************
    //**********************************************************************************************


    public int getDiceValue() {
        return value;
    }

    public boolean isSelected(){
        return isSelected;
    }

    public boolean isHeld() {
        return isHeld;
    }

    public void setIsHeld(boolean h) {
        this.isHeld = h;
    }

    public void setIsSelected(boolean s) {
        isSelected = s;
    }

    public void setDiceValue(int value) {
        this.value = value;
    }

    public boolean isAvailable() {
        if(!this.isHeld() && !this.isSelected()) {
            return true;
        } else {
            return false;
        }
    }

    //*******ON DRAW********************************************************************************
    //**********************************************************************************************

    // Override the onDraw method to handle drawing
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Dice background colors for when they are redrawn (invalidate())
        if(Utilities.noPoints) {
            canvas.drawColor(Color.parseColor("#4E4D45"));
        } else if(Utilities.rolledOver) {
            canvas.drawColor(Color.parseColor("#4E4D45"));
        }
        else if(isSelected) {
            canvas.drawColor(Color.parseColor("#FFEB3B"));
        } else if(!GameLogic.gameStarted){
            canvas.drawColor(Color.parseColor("#4E4D45"));
        } else if(GameLogic.startOfTurn) {
            canvas.drawColor(Color.parseColor("#4E4D45"));
        } else if(isHeld()) {
            canvas.drawColor(Color.RED);
        }
        else {
            canvas.drawColor(Color.WHITE);
        }

        // Your drawing code for the dice goes here
        // Example: Drawing a simple six-sided dice
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        int width = getWidth();
        int height = getHeight();

        // Draw the dice outline
        canvas.drawRect(0, 0, width, height, paint);

        // Draw the dots for a standard dice
        int dotSize = 10;
        int dotMargin = 10;

        //Draw the dots on the dice according to the random value rolled
        switch (value) {
            case 1:
                drawDot(canvas, width / 2, height / 2);
                break;
            case 2:
                drawDot(canvas, width / 4, height / 4);
                drawDot(canvas, 3 * width / 4, 3 * height / 4);
                break;
            case 3:
                drawDot(canvas, width / 4, height / 4);
                drawDot(canvas, 3 * width / 4, 3 * height / 4);
                drawDot(canvas, width / 2, height / 2);
                break;
            case 4:
                drawDot(canvas, width / 4, height / 4);
                drawDot(canvas, 3 * width / 4, 3 * height / 4);
                drawDot(canvas, width / 4, 3 * height / 4);
                drawDot(canvas, 3 * width / 4, height / 4);
                break;
            case 5:
                drawDot(canvas, width / 4, height / 4);
                drawDot(canvas, 3 * width / 4, 3 * height / 4);
                drawDot(canvas, width / 4, 3 * height / 4);
                drawDot(canvas, 3 * width / 4, height / 4);
                drawDot(canvas, width / 2, height / 2);
                break;
            case 6:
                drawDot(canvas, width / 4, height / 4);
                drawDot(canvas, 3 * width / 4, 3 * height / 4);
                drawDot(canvas, width / 4, 3 * height / 4);
                drawDot(canvas, 3 * width / 4, height / 4);
                drawDot(canvas, width / 4, height / 2);
                drawDot(canvas, 3 * width / 4, height / 2);

                break;
        }
    }

    //draws....dots
    private void drawDot(Canvas canvas, float x, float y) {

        int dotSize = 10;
        int dotMargin = 10;
        Paint dotPaint = new Paint();
        dotPaint.setColor(Color.BLACK);
        dotPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, dotSize, dotPaint);
    }
}
