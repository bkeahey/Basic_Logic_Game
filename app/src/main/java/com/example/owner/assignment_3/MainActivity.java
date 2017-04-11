package com.example.owner.assignment_3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.owner.assignment_3.model.UsernameContracts;
import com.example.owner.assignment_3.model.UsernameDbHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.owner.assignment_3.Utils.Utils.SHARED_PREF_FILENAME;
import static com.example.owner.assignment_3.Utils.Utils.TOTALFAILURES;
import static com.example.owner.assignment_3.Utils.Utils.USERID;
import static com.example.owner.assignment_3.Utils.Utils.USERNAME;
import static com.example.owner.assignment_3.Utils.Utils.dbHelper;
import static com.example.owner.assignment_3.model.UsernameContracts.TABLE_NAME;
import static com.example.owner.assignment_3.model.UsernameContracts.UsernameEntry.COL_POINTS;
import static com.example.owner.assignment_3.model.UsernameContracts.UsernameEntry.COL_USERNAME;

/*
Runs on the nexus 5 emulator
 */
public class MainActivity extends AppCompatActivity {
    Button playgame, reg;
    EditText username;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<String> users=new ArrayList<>();
    List<Integer> points=new ArrayList<>();
    List<Integer> temps=new ArrayList<>();
    //arrays created to check username and points in the checkRecord function

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_APPEND);
        editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
        //sharedPreferences created for this page
        //sharedPreferences is cleared when you go back to this page starting a new user log

        playgame = (Button)findViewById(R.id.playGame);
        playgame.setOnClickListener(new myListr());

        reg=(Button)findViewById(R.id.reg_btn);
        reg.setOnClickListener(new myListr2());

        username=(EditText)findViewById(R.id.un_ma);

        dbHelper=new UsernameDbHelper(this);
    }

    class myListr implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            boolean correct = checkRecord();
            if(correct){
                USERNAME=username.getText().toString();
                Intent intent = new Intent(MainActivity.this, WorldSelect.class);
                startActivity(intent);
                //sends user to world select page when the username is in the database
            }
            else
            {
                AlertDialog invalid = new AlertDialog.Builder(MainActivity.this).create();
                invalid.setMessage("Invalid Username");
                invalid.show();
                //sends message to user if the username isn't correct
            }
        }
    }

    private class myListr2 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent2 = new Intent(MainActivity.this, Register.class);
            startActivity(intent2);
            //takes user to register page if button is clicked
        }
    }

    public boolean checkRecord() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] projection = {
                UsernameContracts.UsernameEntry._ID,
                COL_USERNAME,
                COL_POINTS
        };

        Cursor cursor = db.query(
                TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        String temp = "";
        String tempStr = "";
        String tempStr2 = "";

        while (cursor.moveToNext()) {

            temp = cursor.getString(cursor.getColumnIndexOrThrow(String.valueOf(UsernameContracts.UsernameEntry._ID)));
            temps.add(Integer.parseInt(temp));

            tempStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_USERNAME));
            users.add(tempStr);

            tempStr2 = cursor.getString(cursor.getColumnIndexOrThrow(COL_POINTS));
            points.add(Integer.parseInt(tempStr2));

        }
        cursor.close();

        for(int i=0; i <users.size(); i++)
        {


            if((username.getText().toString().equals(users.get(i))))
            {
                USERID = temps.get(i);
                TOTALFAILURES = points.get(i);
                //gets userid and the total number of failures

                return true;
            }
        }
        return false;
    }
}
