package com.example.wordle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class EndGameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_game);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("board");
            System.out.println(value);
            //The key argument here must match that used in the other activity
        }
        
        setBoard();
    }

    private void setBoard() {
        TextView[][] tvs = Second.getTvs();
        TextView[][] board = new TextView[6][5];

        String resIdStr;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                resIdStr = "etv" + i + j;
                int resID = getResources().getIdentifier(resIdStr, "id", getPackageName());

                TextView tv = findViewById(resID);
                board[i][j] = tv;
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                String text = (String) tvs[i][j].getText();
                board[i][j].setText(text);

                if(tvs[i][j].getBackground().getConstantState().equals
                        (getResources().getDrawable(R.drawable.right_letter).getConstantState()))
                {
                    board[i][j].setBackgroundResource(R.drawable.right_letter);
                }
                else if(tvs[i][j].getBackground().getConstantState().equals
                        (getResources().getDrawable(R.drawable.contains_letter).getConstantState()))
                {
                    board[i][j].setBackgroundResource(R.drawable.contains_letter);
                }
                else if(tvs[i][j].getBackground().getConstantState().equals
                        (getResources().getDrawable(R.drawable.no_letter).getConstantState()))
                {
                    board[i][j].setBackgroundResource(R.drawable.no_letter);
                }
            }
        }
    }
}
