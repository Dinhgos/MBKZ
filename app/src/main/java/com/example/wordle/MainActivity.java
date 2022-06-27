package com.example.wordle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Class for main menu activity
 */
public class MainActivity extends AppCompatActivity {

    /**
     * create activity
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        Button switchToSecondActivity = findViewById(R.id.startBTN);
        switchToSecondActivity.setOnClickListener(view -> switchActivities(Second.class));

        Button openStat = findViewById(R.id.scoreBTN);
        openStat.setOnClickListener(view -> switchActivities(StatActivity.class));

        Button quit = findViewById(R.id.quitBTN);
        quit.setOnClickListener(view -> closeAPP());
    }

    /**
     * quit button handler
     * closes the app
     */
    private void closeAPP() {
        finish();
        System.exit(0);
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
}