package com.example.wordle;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * class for showing end game result
 */
public class EndGameActivity extends Activity {
    /** database */
    SQLHelper eventsData;

    /**
     * create activity
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_game);

        Button switchToSecondActivity = findViewById(R.id.stat_btn);
        switchToSecondActivity.setOnClickListener(view -> switchActivities(StatActivity.class));

        Button openStat = findViewById(R.id.new_game_btn);
        openStat.setOnClickListener(view -> switchActivities(Second.class));

        insertData();
        setBoard();
    }

    /**
     * switches to another activity
     * @param cl next activity
     */
    private void switchActivities(Class cl) {
        Intent switchActivityIntent = new Intent(this, cl);
        startActivity(switchActivityIntent);
        finish();
    }

    /**
     * sets up database for inserting data
     */
    private void insertData() {
        eventsData = new SQLHelper(this);
        int num = Second.getPosY();
        addEvent(num);
    }

    /**
     * closing activity
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        eventsData.close();
    }

    /**
     * inserts game result into database
     * @param numOfGuesses number of guesses
     */
    private void addEvent(int numOfGuesses) {
        boolean win = Second.isWin();
        if (!win) {
            numOfGuesses++;
        }

        SQLiteDatabase db = eventsData.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLHelper.GUESSES, numOfGuesses);
        db.insert(SQLHelper.TABLE, null, values);

        showResult(numOfGuesses);
    }

    /**
     * shows winning/losing message
     * @param numOfGuesses number of guesses
     */
    private void showResult(int numOfGuesses) {
        TextView result = findViewById(R.id.end_game_tv);
        if (numOfGuesses < 7) {
            result.setText(R.string.win);
        } else {
            result.setText(R.string.lose);
        }
    }

    /**
     * shows game board
     */
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

        Second.resetGame();
    }
}
