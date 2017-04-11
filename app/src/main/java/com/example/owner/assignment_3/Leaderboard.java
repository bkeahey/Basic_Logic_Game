package com.example.owner.assignment_3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.owner.assignment_3.model.UsernameContracts;
import com.example.owner.assignment_3.model.UsernameDbHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.owner.assignment_3.Utils.Utils.dbHelper;
import static com.example.owner.assignment_3.model.UsernameContracts.TABLE_NAME;
import static com.example.owner.assignment_3.model.UsernameContracts.UsernameEntry.COL_POINTS;
import static com.example.owner.assignment_3.model.UsernameContracts.UsernameEntry.COL_USERNAME;

public class Leaderboard extends AppCompatActivity {

    private Button back;
    private TextView leaderboard;
    List<String> failures = new ArrayList<>();
    List<String> usernames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        leaderboard = (TextView) findViewById(R.id.ldboard);
        back = (Button) findViewById(R.id.back_btn_lb);
        back.setOnClickListener(new lbLstnr());

        dbHelper = new UsernameDbHelper(this);
        checkRecord();
        //checks the record as the page is called to create the leaderboard


    }

    private class lbLstnr implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Leaderboard.this, WorldSelect.class);
            startActivity(intent);
            //back button takes you back to the world select screen
        }
    }

    public void checkRecord() {
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

        String tempStr = "";
        String tempStr2 = "";

        while (cursor.moveToNext()) {

            tempStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_USERNAME));
            usernames.add(tempStr);

            tempStr2 = cursor.getString(cursor.getColumnIndexOrThrow(COL_POINTS));
            failures.add(tempStr2);
        }
        cursor.close();



        for(int i=0; i <usernames.size(); i++)
        {
            leaderboard.append(failures.get(i) + "                                               ");
            leaderboard.append(usernames.get(i) +'\n');
            //prints the username and amount of failures on the leaderboard
        }
        }
    }
