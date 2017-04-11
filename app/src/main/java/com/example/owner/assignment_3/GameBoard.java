package com.example.owner.assignment_3;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.animation.Animation;
import android.graphics.Canvas;
import android.graphics.Point;
import android.widget.ImageView;
import android.media.MediaPlayer;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import android.app.Activity;


import com.example.owner.assignment_3.model.UsernameDbHelper;

import java.util.Calendar;

import static com.example.owner.assignment_3.Utils.Utils.LEVEL;
import static com.example.owner.assignment_3.Utils.Utils.SHARED_PREF_FILENAME;
import static com.example.owner.assignment_3.Utils.Utils.TOTALFAILURES;
import static com.example.owner.assignment_3.Utils.Utils.USERID;
import static com.example.owner.assignment_3.Utils.Utils.USERNAME;
import static com.example.owner.assignment_3.Utils.Utils.W1L1FAILURES;
import static com.example.owner.assignment_3.Utils.Utils.W1L2FAILURES;
import static com.example.owner.assignment_3.Utils.Utils.W1L3FAILURES;
import static com.example.owner.assignment_3.Utils.Utils.W2L1FAILURES;
import static com.example.owner.assignment_3.Utils.Utils.W2L2FAILURES;
import static com.example.owner.assignment_3.Utils.Utils.W2L3FAILURES;
import static com.example.owner.assignment_3.Utils.Utils.dbHelper;
import static com.example.owner.assignment_3.model.UsernameContracts.TABLE_NAME;
import static com.example.owner.assignment_3.model.UsernameContracts.UsernameEntry.COL_POINTS;
import static com.example.owner.assignment_3.model.UsernameContracts.UsernameEntry._ID;

public class GameBoard extends Activity {


    StartDraggingLsntr myStartDraggingLsnr;
    EndDraggingLsntr myEndDraggingLsntr;
    Button playBtn, btn1, btn2, btn3, btn4;
    ImageView panda;
    String direction = "";
    String direction2 = "";
    String direction3 = "";
    String direction4 = "";
    Animation correct;
    String level;
    String[] solution = new String[4];
    MediaPlayer music;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String incomingMsg = getIntent().getStringExtra(LEVEL);
        level = incomingMsg;
        dbHelper=new UsernameDbHelper(this);
        sharedPreferences = getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_APPEND);
        editor = sharedPreferences.edit();


        if (level.equals("One")) {
            setContentView(R.layout.activity_game_board);
            solution[0] = "right";
            solution[1] = "down";
            solution[2] = "right";
            solution[3] = " ";
            correct = AnimationUtils.loadAnimation(GameBoard.this, R.anim.levelonesolution);

        } else if (level.equals("Two")) {
            setContentView(R.layout.activity_game_board2);
            solution[0] = "down";
            solution[1] = "right";
            solution[2] = "up";
            solution[3] = "right";
            correct = AnimationUtils.loadAnimation(GameBoard.this, R.anim.leveltwosolution);

        } else if (level.equals("Three")) {

            setContentView(R.layout.activity_game_board3);
            solution[0] = "right";
            solution[1] = "down";
            solution[2] = "left";
            solution[3] = "down";
            correct = AnimationUtils.loadAnimation(GameBoard.this, R.anim.levelthreesolution);

        } else if (level.equals("world2One")) {

            setContentView(R.layout.activity_game_board4);
            solution[0] = "right";
            solution[1] = "down";
            solution[2] = "right";
            solution[3] = "up";
            correct = AnimationUtils.loadAnimation(GameBoard.this, R.anim.world2onesolution);

        } else if (level.equals("world2Two")) {

            setContentView(R.layout.activity_game_board5);
            solution[0] = "down";
            solution[1] = "right";
            solution[2] = "up";
            solution[3] = "right";
            correct = AnimationUtils.loadAnimation(GameBoard.this, R.anim.world2twosolution);

        } else if (level.equals("world2Three")) {

            setContentView(R.layout.activity_game_board6);
            solution[0] = "down";
            solution[1] = "right";
            solution[2] = "down";
            solution[3] = "right";
            correct = AnimationUtils.loadAnimation(GameBoard.this, R.anim.world2threesolution);

        }


        myStartDraggingLsnr = new StartDraggingLsntr();
        myEndDraggingLsntr = new EndDraggingLsntr();

        playBtn = (Button) findViewById(R.id.playBtn);

        findViewById(R.id.upBtn).setOnLongClickListener(myStartDraggingLsnr);
        findViewById(R.id.downBtn).setOnLongClickListener(myStartDraggingLsnr);
        findViewById(R.id.leftBtn).setOnLongClickListener(myStartDraggingLsnr);
        findViewById(R.id.rightBtn).setOnLongClickListener(myStartDraggingLsnr);
        //Connects the buttons for the arrows to their listener

        findViewById(R.id.Btn1).setOnDragListener(myEndDraggingLsntr);
        findViewById(R.id.Btn2).setOnDragListener(myEndDraggingLsntr);
        findViewById(R.id.Btn3).setOnDragListener(myEndDraggingLsntr);
        findViewById(R.id.Btn4).setOnDragListener(myEndDraggingLsntr);
        //Connects buttons to a drag listener

        btn1 = (Button) findViewById(R.id.Btn1);
        btn2 = (Button) findViewById(R.id.Btn2);
        btn3 = (Button) findViewById(R.id.Btn3);
        btn4 = (Button) findViewById(R.id.Btn4);

        playBtn.setOnClickListener(new MyLstr());
        panda = (ImageView) findViewById(R.id.PandaPic);

        music = MediaPlayer.create(GameBoard.this, R.raw.elevatormusic);
        music.setLooping(true);
        music.start();
        //Sets the music and loops it
    }

    public void playMusic(View view) {
        music.start();
    }

    public void setRect(View view) {
        SharedXYValues.drawingMode = "RECT";
        String description;
        description = "sequence: " + findViewById(R.id.Btn1).getContentDescription() + ", "
                + findViewById(R.id.Btn2).getContentDescription() + ", "
                + findViewById(R.id.Btn3).getContentDescription() + ", "
                + findViewById(R.id.Btn4).getContentDescription();
    }


    public void exit(View view) {
        if (level.equals("world2One") || level.equals("world2Two") || level.equals("world2Three")) {
            Intent intent = new Intent(GameBoard.this, LevelMenu2.class);
            startActivity(intent);
        } else if (level.equals("One") || level.equals("Two") || level.equals("Three")) {
            Intent intent2 = new Intent(GameBoard.this, LevelMenu.class);
            startActivity(intent2);
        }
        //Back button returns you to the world you were in
    }

    class MyLstr implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            direction = btn1.getContentDescription().toString();
            direction2 = btn2.getContentDescription().toString();
            direction3 = btn3.getContentDescription().toString();
            direction4 = btn4.getContentDescription().toString();

            if (solution[0].equals(direction) && solution[1].equals(direction2) && solution[2].equals(direction3) && solution[3].equals(direction4)) {
                panda.startAnimation(correct);
                Toast.makeText(GameBoard.this, "Good job!", Toast.LENGTH_LONG).show();
                //shows this message if the user input the correct answer

                Calendar cal = Calendar.getInstance();
                System.out.println("Current time => " + cal.getTime());
                String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                //gets the time

                editor.putString(level, USERNAME + " beat Level " + level + ": " + mydate + "\n\n");
                editor.apply();
                //updates the shared preferences and progress report log with the level beat and when it was beat

            } else {
                AlertDialog incorrect = new AlertDialog.Builder(GameBoard.this).create();
                incorrect.setMessage("Incorrect answer , try again");
                incorrect.show();
                //When the users answer is incorrect this message is displayed

                TOTALFAILURES += 1;
                if (level.equals("One")) {
                    W1L1FAILURES += 1;
                } else if (level.equals("Two")) {
                    W1L2FAILURES += 1;
                } else if (level.equals("Three")) {
                    W1L3FAILURES += 1;
                } else if (level.equals("world2One")) {
                    W2L1FAILURES += 1;
                } else if (level.equals("world2Two")) {
                    W2L2FAILURES += 1;
                } else if (level.equals("world2Three")) {
                    W2L3FAILURES += 1;
                }
                //Adds 1 to the amount of failures depending on the level the user is on

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COL_POINTS, String.valueOf(TOTALFAILURES));
                db.update(TABLE_NAME, values, _ID + " = ?", new String[]{String.valueOf(USERID)});
                //updates the amount of failures(points) in the database
            }
        }
    }


    private class EndDraggingLsntr implements View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                ((Button) view).setBackground(((Button) event.getLocalState()).getBackground());
                ((Button) view).setContentDescription(((Button) event.getLocalState()).getContentDescription());
                //drag listener that listens to to when the drag ends
            }

            return true;
        }
    }

    private class StartDraggingLsntr implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view) {
            WithDraggingShadow shadow = new WithDraggingShadow(view);
            ClipData data = ClipData.newPlainText("", "");
            view.startDrag(data, shadow, view, 0);
            return false;
            //drag listener that listens to when the drag starts
        }
    }


    private class WithDraggingShadow extends View.DragShadowBuilder {
        public WithDraggingShadow(View view) {
            super(view);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            super.onDrawShadow(canvas);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
        //shadow while dragging
        }
    }
}


