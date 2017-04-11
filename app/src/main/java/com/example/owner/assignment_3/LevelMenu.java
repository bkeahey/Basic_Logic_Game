package com.example.owner.assignment_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.owner.assignment_3.Utils.Utils.LEVEL;


public class LevelMenu extends AppCompatActivity {

    Button one,two,three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_menu);

        one = (Button)findViewById(R.id.levelOne);
        two = (Button)findViewById(R.id.levelTwo);
        three = (Button)findViewById(R.id.levelThree);
        one.setOnClickListener(new Mylistr());
        two.setOnClickListener(new Mylistr2());
        three.setOnClickListener(new Mylistr3());
        //Buttons are connected from xml and listeners are set
    }

    class Mylistr implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent1 = new Intent(LevelMenu.this,GameBoard.class);
            intent1.putExtra(LEVEL,"One");
            startActivity(intent1);
            //sends user to level they select in this case level 1
        }
    }

    class Mylistr2 implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent2 = new Intent(LevelMenu.this,GameBoard.class);
            intent2.putExtra(LEVEL,"Two");
            startActivity(intent2);
        }
    }

    class Mylistr3 implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent3 = new Intent(LevelMenu.this,GameBoard.class);
            intent3.putExtra(LEVEL,"Three");
            startActivity(intent3);
        }
    }

    public void back(View view) {
        Intent intent = new Intent(LevelMenu.this, WorldSelect.class);
        startActivity(intent);
        //back button sends user back to world select page
    }
}

