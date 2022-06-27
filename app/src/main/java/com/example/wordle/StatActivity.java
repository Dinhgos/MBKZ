package com.example.wordle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StatActivity extends Activity {
    SQLHelper eventsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat);

        eventsData = new SQLHelper(this);
        Cursor cursor = getEvents();
        showEvents(cursor);

        Button openStat = findViewById(R.id.stat_back_btn);
        openStat.setOnClickListener(view -> switchActivities(MainActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventsData.close();
    }

    private void switchActivities(Class cl) {
        Intent switchActivityIntent = new Intent(this, cl);
        startActivity(switchActivityIntent);
        finish();
    }

    private Cursor getEvents() {
        SQLiteDatabase db = eventsData.getReadableDatabase();
        Cursor cursor = db.query(SQLHelper.TABLE, null, null, null, null,
                null, null);

        return cursor;
    }

    private void showEvents(Cursor cursor) {
        double[] data = new double[7];
        double games = 0;

        while (cursor.moveToNext()) {
//            int id = cursor.getInt(0);
            int guesses = cursor.getInt(1);
            data[guesses - 1]++;
            games++;
//            ret.append(id + ": " + guesses + "\n");
        }

        TextView tv = findViewById(R.id.numGamesTV);
        tv.setText(String.valueOf((int)games));

        double loseRate = data[6] / games;
        double winrate = (1 - (loseRate)) * 100;
        tv = findViewById(R.id.numWinRateTV);
        tv.setText((int) winrate + " %");

        for (int i = 0; i < 7; i++) {
            int id = (i+1);
            String resIdStr = "progressBar" + id;
            int resID = getResources().getIdentifier(resIdStr, "id", getPackageName());
            ProgressBar pb = findViewById(resID);
            double progress = (data[i]/games) * 100;
            pb.setProgress((int) progress);
        }
    }
}
