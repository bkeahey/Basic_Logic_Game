package com.example.owner.assignment_3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import static com.example.owner.assignment_3.Utils.Utils.SHARED_PREF_FILENAME;
import static com.example.owner.assignment_3.Utils.Utils.USERID;
import static com.example.owner.assignment_3.Utils.Utils.USERNAME;

public class WorldSelect extends AppCompatActivity {

    private Button btn1, btn2, btn3;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_select);

        btn1 = (Button) findViewById(R.id.worldOne);
        btn1.setOnClickListener(new myLstnr());

        btn2 = (Button) findViewById(R.id.worldTwo);
        btn2.setOnClickListener(new myLstnr2());

        btn3 =(Button)findViewById(R.id.report);
        btn3.setOnClickListener(new myLstnr3());

        sharedPreferences = getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_APPEND);
        editor=sharedPreferences.edit();
        //starts shared preferences and the editor

        Calendar cal= Calendar.getInstance();
        System.out.println("Current time =>" + cal.getTime());
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        //gets time

        editor.putString("Name","\n" + USERNAME + " logged on at " + mydate + "\n\n");
        editor.apply();
        //adds when user logs in to shared preferences and user log

    }

    public void ldrboard(View view) {
        Intent intent = new Intent(WorldSelect.this, Leaderboard.class);
        startActivity(intent);
        //takes user to leaderboard when leaderboard is selected
    }


    class myLstnr implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WorldSelect.this, LevelMenu.class);
            startActivity(intent);
            //takes user to level menu when world1 is selected
        }
    }

    private class myLstnr2 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent2 = new Intent(WorldSelect.this, LevelMenu2.class);
            startActivity(intent2);
            //takes user to level menu 2 when world 2 is selected
        }
    }

    public void logout(View view) {
        Intent intent = new Intent(WorldSelect.this, MainActivity.class);
        startActivity(intent);
        //sends user back to mainactivity when logout button is selected
    }

    private class myLstnr3 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent3 = new Intent(WorldSelect.this, ProgressReport.class);
            startActivity(intent3);
            //takes user to progress report page when this button is selected
        }
    }
}
