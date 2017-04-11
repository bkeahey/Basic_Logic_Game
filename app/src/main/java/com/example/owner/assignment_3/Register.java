package com.example.owner.assignment_3;

import android.content.ContentValues;
import android.content.Intent;
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

import static com.example.owner.assignment_3.Utils.Utils.TOTALFAILURES;
import static com.example.owner.assignment_3.Utils.Utils.USERID;
import static com.example.owner.assignment_3.Utils.Utils.dbHelper;
import static com.example.owner.assignment_3.model.UsernameContracts.TABLE_NAME;
import static com.example.owner.assignment_3.model.UsernameContracts.UsernameEntry.COL_POINTS;
import static com.example.owner.assignment_3.model.UsernameContracts.UsernameEntry.COL_USERNAME;

public class Register extends AppCompatActivity {

    private Button btn;
    private EditText username;
    List<String> users = new ArrayList<>();
    List<Integer> points = new ArrayList<>();
    List<Integer> temps = new ArrayList<>();
    //arrays used in checkRecord

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn = (Button) findViewById(R.id.reg_btn2);
        btn.setOnClickListener(new myLstnr4());
        username = (EditText) findViewById(R.id.edt_txt_reg);

        dbHelper = new UsernameDbHelper(this);

    }

    private class myLstnr4 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            createUsername();
            //calls createUsername method which then takes you back to mainActivity if successful
        }
    }

    public void createUsername() {

        int length=username.length();
        //Checks length of username
        boolean alreadyExists=checkRecord();
        //If username exists returns true
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(alreadyExists) {
            //if the username exists displays this message
            AlertDialog exists = new AlertDialog.Builder(Register.this).create();
            exists.setMessage("This username is already taken");
            exists.show();
        }
        else if(length==0){
            //if username is too short displays this message
            AlertDialog exists = new AlertDialog.Builder(Register.this).create();
            exists.setMessage("This username is not long enough");
            exists.show();
        }
        else {
            //creates username if it doesn't exist and meets length requirement
            values.put(COL_USERNAME, username.getText().toString());

            //Inserts a new row into the db
            long newRowId = db.insert(TABLE_NAME, null, values);
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
            //takes user back to mainActivity when register is successful
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


        for (int i = 0; i < users.size(); i++) {


            if ((username.getText().toString().equals(users.get(i)))) {
                USERID = temps.get(i);
                TOTALFAILURES = points.get(i);

                return true;
            }
        }
        return false;
    }
}

