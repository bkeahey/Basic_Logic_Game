package com.example.owner.assignment_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.owner.assignment_3.Utils.Utils.LEVEL;

public class LevelMenu2 extends AppCompatActivity {

    Button world2One,world2Two,world2Three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_menu2);

        world2One = (Button)findViewById(R.id.levelOne2);
        world2Two = (Button)findViewById(R.id.levelTwo2);
        world2Three = (Button)findViewById(R.id.levelThree2);
        world2One.setOnClickListener(new Mylstr());
        world2Two.setOnClickListener(new Mylstr2());
        world2Three.setOnClickListener(new Mylstr3());
        //Buttons are connected from xml and then their listeners are set
    }

    class Mylstr implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent1 = new Intent(LevelMenu2.this,GameBoard.class);
            intent1.putExtra(LEVEL,"world2One");
            startActivity(intent1);
            //Sends user to level they selected in this case world 2 level 1


        }
    }

    class Mylstr2 implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent2 = new Intent(LevelMenu2.this,GameBoard.class);
            intent2.putExtra(LEVEL,"world2Two");
            startActivity(intent2);

        }
    }


    class Mylstr3 implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent3 = new Intent(LevelMenu2.this,GameBoard.class);
            intent3.putExtra(LEVEL,"world2Three");
            startActivity(intent3);

        }
    }

    public void back(View view) {
        Intent intent = new Intent(LevelMenu2.this, WorldSelect.class);
        startActivity(intent);
        //back button sends user back to world select menu
    }
}
