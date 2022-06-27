package com.example.wordle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Main Class for main function
 */
public class MainActivity extends AppCompatActivity {

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

    private void closeAPP() {
        finish();
        System.exit(0);
    }

    private void switchActivities(Class cl) {
        Intent switchActivityIntent = new Intent(this, cl);
        startActivity(switchActivityIntent);
        finish();
    }
}