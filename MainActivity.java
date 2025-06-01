package com.example.ericcombsdicegame;

import static com.example.ericcombsdicegame.R.id.goodjob;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //*******INSTANCE VARIABLES*********************************************************************
    //**********************************************************************************************

    //Dice Views
    static DiceView dice1;
    static DiceView dice2;
    static DiceView dice3;
    static DiceView dice4;
    static DiceView dice5;
    static DiceView dice6;

    //Buttons
    static Button rollButton;
    static Button rulesBtn;
    static Button rollOnBtn;
    static Button endTurnBtn;
    static Button resetButton;
    static Button miniGameButton;
    static Button up;
    static Button down;
    static Button faster;
    static Button slower;
    static Button fire;

    //TextViews
    static TextView player1Text;
    static TextView player1Score;
    static TextView computerText;
    static TextView computerScore;
    static TextView rollScore;
    static TextView messageText;
    static TextView speed;
    static TextView miniHits;
    static TextView bulletsLeft;

    //Misc
    static MediaPlayer mediaPlayer;
    static Context MainActivityContext;
    static ImageView goodjob;
    static int taps =0;

    //Mini Game Stuff
    public static Timer timer = new Timer();
    public static int imageX = 0, imageY = 0, dice1X = 0, dice1Y = 0;
    public static int velocity = 10;
    public static int directionX = 1, directionY = 1;
    static int screenX;
    static int screenY;
    static boolean miniGameStarted = false;
    static boolean bulletExists = false;
    public static View fireView;
    public static int bulletX = 0, bulletY=0;
    public static int bulletSpeed = 30;
    public static int origDice1X;
    public static int origDice1Y;
    public static int origDice6X;
    public static int origDice6Y;

    @SuppressLint("MissingInflatedId")
    @Override

    //*******ON CREATE********************************************************************************
    //**********************************************************************************************
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Dice
        dice1 = findViewById(R.id.diceView1);
        dice2 = findViewById(R.id.diceView2);
        dice3 = findViewById(R.id.diceView3);
        dice4 = findViewById(R.id.diceView4);
        dice5 = findViewById(R.id.diceView5);
        dice6 = findViewById(R.id.diceView6);

        origDice1X = (int) dice1.getX();
        origDice1Y = (int) dice1.getY();
        origDice6X = (int) dice6.getX();
        origDice6Y = (int) dice6.getY();

        // Initialize Buttons
        rollButton = findViewById(R.id.rollbtn);
        rulesBtn = findViewById(R.id.rulesBtn);
        endTurnBtn = findViewById(R.id.endTurnBtn);
        resetButton = findViewById(R.id.resetButton);
        miniGameButton = findViewById(R.id.miniGameButton);
        slower = findViewById(R.id.slower);
        faster = findViewById(R.id.faster);
        fire = findViewById(R.id.fire);


        // Initialize TextViews
        player1Text = findViewById(R.id.player1Text);
        player1Score = findViewById(R.id.player1Score);
        computerText = findViewById(R.id.computerText);
        computerScore = findViewById(R.id.computerScore);
        rollScore = findViewById(R.id.rollScore);
        messageText = findViewById(R.id.messageText);
        speed = findViewById(R.id.speed);
        miniHits = findViewById(R.id.hits);
        bulletsLeft = findViewById(R.id.bulletsLeft);

        //Initialize Misc
        goodjob = findViewById(R.id.goodjob);
        MainActivityContext = getApplicationContext();

        //*******Dice Listeners*********************************************************************
        //*******Calls diceClicked() when it is human's turn.  Doesn't work when computer's turn
        //******************************************************************************************

        dice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GameLogic.gameStarted && !GameLogic.startOfTurn && !ComputerLogic.isComputersTurn && !dice1.isHeld()) {
                    Utilities.diceClicked(dice1);
                }
            }
        });

        dice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GameLogic.gameStarted && !GameLogic.startOfTurn && !ComputerLogic.isComputersTurn && !dice2.isHeld()) {
                    Utilities.diceClicked(dice2);
                }
            }
        });

        dice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GameLogic.gameStarted && !GameLogic.startOfTurn && !ComputerLogic.isComputersTurn && !dice3.isHeld()) {
                    Utilities.diceClicked(dice3);
                }
            }
        });

        dice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GameLogic.gameStarted && !GameLogic.startOfTurn && !ComputerLogic.isComputersTurn && !dice4.isHeld()) {
                    Utilities.diceClicked(dice4);
                }
            }
        });

        dice5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GameLogic.gameStarted && !GameLogic.startOfTurn && !ComputerLogic.isComputersTurn && !dice5.isHeld()) {
                    Utilities.diceClicked(dice5);
                }
            }
        });

        dice6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GameLogic.gameStarted && !GameLogic.startOfTurn && !ComputerLogic.isComputersTurn && !dice6.isHeld()) {
                    Utilities.diceClicked(dice6);
                }
            }
        });

        //*******Button Listeners*******************************************************************
        //******************************************************************************************

        //Secret way to start new dice game during mini game
        miniHits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taps += 1;
                if(taps >= 7) {
                    taps = 0;
                    rollScore.setText("YOU WON!");
                    rollScore.setVisibility(View.VISIBLE);
                    miniHits.setVisibility(View.INVISIBLE);
                    bulletsLeft.setVisibility(View.INVISIBLE);
                    fire.setVisibility(View.INVISIBLE);
                    miniGameStarted = false;
                    dice6.setVisibility(View.INVISIBLE);
                    if(fireView != null) {
                        ViewGroup parent = (ViewGroup) fireView.getParent();
                        parent.removeView(fireView);
                        fireView = null;
                    }
                    miniGameButton.setText("Play Mini Game Again!");
                    miniGameButton.setVisibility(View.VISIBLE);
                    resetButton.setText("Play Dice Game Again!");
                    resetButton.setVisibility(View.VISIBLE);
                    MiniGame.mediaPlayer.stop();
                    MiniGame.mediaPlayer.release();
                    MiniGame.mediaPlayer = null;
                    goodjob.setVisibility(View.VISIBLE);
                    Utilities.gameOver = false;
                    GameLogic.gameStarted = false;
                    Utilities.reset();
                    resetButton.setVisibility(View.INVISIBLE);
                    goodjob.setVisibility(View.INVISIBLE);
                }
            }
        });

        //Secret way to start new mini game during dice game
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GameLogic.gameStarted) {
                    taps += 1;
                    if (taps >= 7) {
                        MiniGame.bulletsLeft = 40;
                        MiniGame.miniGameHitsLeft = 6;
                        velocity = 10;
                        MiniGame.initialize(MainActivityContext);
                        miniGameStarted = true;
                        dice6.setVisibility(View.VISIBLE);
                        goodjob.setVisibility(View.INVISIBLE);
                        taps = 0;
                    }
                }
            }
        });

        //Display rules
        rulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRulesDialog();
            }
        });

        //Speed up computer turns
        faster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.faster();
            }
        });

        //slow down computer turns
        slower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.slower();
            }
        });

        //Fire bullet in mini game.
        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fireView == null){
                    playPewPewSound();
                    MiniGame.bulletsLeft -= 1;
                    bulletsLeft.setText("Bullets Left: " + MiniGame.bulletsLeft);
                    // Create a circular view and add it to the ConstraintLayout
                    fireView = new View(MainActivity.this);

                    // Create a ShapeDrawable to draw a circle
                    ShapeDrawable circle = new ShapeDrawable(new OvalShape());
                    circle.getPaint().setColor(Color.YELLOW);

                    // Set the ShapeDrawable as the background of the view
                    fireView.setBackground(circle);

                    // Set layout parameters for the circle
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(10, 10);
                    layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
                    layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                    bulletX = (int) dice1.getX() + dice1.getWidth();
                    bulletY = (int) dice1.getY() + dice1.getHeight() / 2;
                    layoutParams.leftMargin = bulletX;
                    layoutParams.topMargin = bulletY;

                    // Add the circle to the ConstraintLayout
                    ConstraintLayout mainLayout = findViewById(R.id.myConstraintLayout);
                    mainLayout.addView(fireView, layoutParams);
                    bulletExists = true;
                }
            }
        });

        //Visible at the end of a dice game and end of mini game.  Starts mini game.
        miniGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiniGame.bulletsLeft = 40;
                MiniGame.miniGameHitsLeft = 6;
                velocity = 10;
                MiniGame.initialize(MainActivityContext);
                miniGameStarted = true;
                dice6.setVisibility(View.VISIBLE);
                goodjob.setVisibility(View.INVISIBLE);
            }
        });

        //Visible at end of dice game and end of mini game.  Restarts dice game.
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.gameOver = false;
                GameLogic.gameStarted = false;
                Utilities.reset();
                resetButton.setVisibility(View.INVISIBLE);
                goodjob.setVisibility(View.INVISIBLE);
            }
        });

        endTurnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.endRoundButtonPushed(MainActivity.this);
            }
        });

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!GameLogic.gameStarted) {
                    GameLogic.gameStarted = true;
                    player1Text.setBackgroundColor(Color.parseColor("#FFEB3B"));
                    player1Score.setBackgroundColor(Color.parseColor("#FFEB3B"));
                    Utilities.setAndDisplayMessage("Player 1 needs 10,000 points to win");
                    rollButton.setVisibility(View.INVISIBLE);
                    endTurnBtn.setVisibility(View.VISIBLE);
                }
                GameLogic.startOfTurn = false;
                playRollSound();
                Utilities.rollButtonClicked(dice1, dice2, dice3, dice4, dice5, dice6);
            }
        });

        //*******MINI GAME STUFF********************************************************************
        //******************************************************************************************
        final int FPS = 40; // frames per second for game loop
        TimerTask updateGame = new UpdateGameTask();
        timer.scheduleAtFixedRate(updateGame, 0, 1000/FPS);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenX = size.x;
        screenY = size.y;
        GameLogic.initialize();
    }

    //*******AUDIO**********************************************************************************
    //**********************************************************************************************

    public void playRollSound() {
        // Release any existing MediaPlayer resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // Create a new MediaPlayer instance
        mediaPlayer = MediaPlayer.create(this, R.raw.roll);

        // Start playing the sound
        mediaPlayer.start();
    }

    private void playPewPewSound() {
        // Release any existing MediaPlayer resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Create a new MediaPlayer instance
        mediaPlayer = MediaPlayer.create(this, R.raw.pewpew);

        // Start playing the sound
        mediaPlayer.start();
    }

    public static void playExplosionSound(Context context) {

        if(context != null) {
            // Release any existing MediaPlayer resources
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }

            // Create a new MediaPlayer instance
            mediaPlayer = MediaPlayer.create(context, R.raw.explosion);

            // Start playing the sound
            mediaPlayer.start();
        }
    }

    // Override onDestroy to release MediaPlayer resources when the activity is destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //*******GAME LOOP TIMER************************************************************************
    //**********************************************************************************************

    class UpdateGameTask extends TimerTask {

        @Override
        public void run() {

           //repeatedly call the MiniGame.loop at set intervals for the duration of the app
            MainActivity.this.runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    if(miniGameStarted) {
                        MiniGame.loop(MainActivityContext);
                    }
                }
            });
        }
    }

    //*******GAME RULES BUTTON**********************************************************************
    //**********************************************************************************************

    private void showRulesDialog() {
        // Create a ScrollView to make the content scrollable
        ScrollView scrollView = new ScrollView(this);

        // Create a TextView to hold your rules content
        TextView rulesTextView = new TextView(this);

        // Set padding for the TextView (adjust values as needed)
        rulesTextView.setPadding(30, 20, 20, 20);

        rulesTextView.setText(Html.fromHtml(
                "<b>Objective:</b> Be the first to score exactly 10,000 points without going over. <br>" +
                "<br><b>Taking a Turn:</b> <br>" +
                "Roll the dice to start your turn.  If you have any scoring die, you can either SELECT them by clicking on them, " +
                "or you can end your turn and bank your points. When you click a die to SELECT it, it will turn yellow. " +
                "If you have a valid selection, the total will be displayed above the dice.  If it is invalid, the message will tell " +
                "you that it is not a valid selection.  You can choose to roll again with a valid selection, and any additional points " +
                "will be added to the roll total.  Selected dice will turn red indicating that they are being HELD and will not be rolled.  " +
                "If all six dice are validly held/selected, you can roll again and all 6 dice will roll. <br> <br>" +
                "<b>Ending a Turn:</b> <br>" +
                "Press &quot;End Turn&quot; to end your turn.  If you had a valid selection, those points will be banked to your overall score." +
                "  If you did not have a valid selection, no points will be banked and it will be the computer's turn. <br><br>" +
                "<b>Scoring:</b> <br>" +
                "Large Straight = 3,000 = Roll 1-6<br>" +
                "Small Straight = 1,500 = Roll 1-5 or 2-6<br>" +
                "1's = 100 each<br>" +
                "5's = 50 each<br><br>" +
                "Three of a kind on one roll = dice value X 100 (example, three 5's = 500).  EXCEPTION: Three 1's = 1,000<br>" +
                "For each additional dice (over 3) of the same value, double the score.  Example: 4 5's = 500 X 2 = 1,000<br><br>" +
                "<b>Getting &quot;On The Board&quot;:</b><br>" +
                "At the very beginning of the game, you must score 800 or more points during one turn in order to get on the board.  " +
                "If you are not on the board, none of your points will bank.  If you are not on the score board, you should not " +
                "end your turn until you have at least 800 points.  After you are initially on the board, the 800 point restriction no " +
                "longer applies and you can bank any amount at the end of a turn.<br><br>" +
                "<b>Winning the Game:</b> <br>" +
                "Bank exactly 10,000 points without rolling over.  If you roll over 10,000, nothing is banked for that roll and it " +
                "becomes the computer's turn.<br><br>" +
                "<b>Mini Game:</b><br>" +
                "Upon finishing the game, you will have an option to play a mini game!  The leftmost die with shoot at the rightmost" +
                "die, which will be moving.  Fire 6 connecting shots before running out of bullets to win. <br><br>" +
                "<b>Mini Game &quot;Secret&quot;:</b><br>" +
                "A hidden way to start the mini game is to tap the bottom center of the dice game 7 times where it tells you the computer" +
                "speed.  This must be done AFTER a dice game has started.  To exit the mini game and restart a dice game, " +
                "tap the top left corner of the mini game 7 times where it lists the number of hits."));

        // Add the TextView to the ScrollView
        scrollView.addView(rulesTextView);

        // Create an AlertDialog with the ScrollView as its content
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Rules")
                .setView(scrollView)
                .setPositiveButton("OK", null);

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}