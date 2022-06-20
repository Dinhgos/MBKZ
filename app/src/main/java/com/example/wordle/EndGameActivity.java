package com.example.wordle;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TextView;

public class EndGameActivity extends Activity {
    SQLHelper eventsData;

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

        Button switchToSecondActivity = findViewById(R.id.stat_btn);
        switchToSecondActivity.setOnClickListener(view -> switchActivities(StatActivity.class));

        Button openStat = findViewById(R.id.new_game_btn);
        openStat.setOnClickListener(view -> switchActivities(Second.class));

        insertData();
//        eventsData = new SQLHelper(this);
//        addEvent(5);
//        Cursor cursor = getEvents();
//        showEvents(cursor);
        
        setBoard();
    }

    private void switchActivities(Class cl) {
        Intent switchActivityIntent = new Intent(this, cl);
        startActivity(switchActivityIntent);
        finish();
    }

    private void insertData() {
        eventsData = new SQLHelper(this);
        int num = Second.getPosY();
        addEvent(num);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventsData.close();
    }

    private void addEvent(int numOfGuesses) {
        SQLiteDatabase db = eventsData.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLHelper.GUESSES, numOfGuesses);
        db.insert(SQLHelper.TABLE, null, values);

    }

    private Cursor getEvents() {
        SQLiteDatabase db = eventsData.getReadableDatabase();
        Cursor cursor = db.query(SQLHelper.TABLE, null, null, null, null,
                null, null);

        //startManagingCursor(cursor);
        return cursor;
    }

    private void showEvents(Cursor cursor) {
        StringBuilder ret = new StringBuilder("Saved Events:\n\n");
        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            long guesses = cursor.getLong(1);
            // ret.append(id + ": " + time + ": " + title + "\n");
            ret.append(id + ": "+ guesses + "\n");
        }
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

        Second.resetGame();
    }
}
