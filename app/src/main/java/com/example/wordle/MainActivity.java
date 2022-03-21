package com.example.wordle;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.wordle.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;
import java.util.SortedMap;

/**
 * Main Class for main function
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private String[] words;
    private char[] buffer;
    private static int posX = 0;
    private static int posY = 0;
    private static TextView[][] tvs;
    private static final String[] ALLOWED = new String[10657];
    private static final String[] ANSWERS = new String[2315];
    private static String SOLUTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.wordle.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        initialize();
    }

    private void initialize() {
        words = new String[6];
        for (int i = 0; i < 6; i++) {
            words[i] = "";
        }

        buffer = new char[5];
        for (int i = 0; i < 5; i++) {
            buffer[i] = '0';
        }

        tvs = new TextView[6][5];
        String resIdStr;
        setContentView(R.layout.fragment_second);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                resIdStr = "tv" + i + j;
                int resID = getResources().getIdentifier(resIdStr, "id", getPackageName());

                TextView tv = (TextView)findViewById(resID);
                tvs[i][j] = tv;
            }
        }

        loadData();
        pickWord();
    }

    private void pickWord() {
        int rNum = (int) (Math.random() * 2314);
        SOLUTION = ANSWERS[rNum];
    }

    private void loadData() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("wordle-allowed-guesses.txt"), StandardCharsets.UTF_8));

            String mLine;
            int counter = 0;
            while ((mLine = reader.readLine()) != null) {
                ALLOWED[counter] = mLine;
                counter++;
            }
        } catch (IOException e) {
            System.out.println("Could not read data from wordle-allowed-guesses.txt.");
            System.exit(1);
        }

        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("wordle-answers-alphabetical.txt"), StandardCharsets.UTF_8));

            String mLine;
            int counter = 0;
            while ((mLine = reader.readLine()) != null) {
                ANSWERS[counter] = mLine;
                counter++;
            }
        } catch (IOException e) {
            System.out.println("Could not read data from wordle-answers-alphabetical.txt.");
            System.exit(1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void charBTN(View v) {
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        char c = buttonText.charAt(0);

        insertChar(c);
    }

    private void insertChar(char c) {
        if (posY >= 6 || posX >= 5) {
            return;
        }

        tvs[posY][posX].setText(Character.toString(c));

        buffer[posX] = c;
        posX++;
    }

    public void submitWord(View view) {
        if (posY >= 6 || buffer[4] == '0') {
            return;
        }

        String tmpWord = "";
        for (char c : buffer) {
            tmpWord += Character.toString(c);
        }
        words[posY] = tmpWord;

        boolean conAl = Arrays.asList(ALLOWED).contains(tmpWord.toLowerCase(Locale.ROOT));
        boolean conAn = Arrays.asList(ANSWERS).contains(tmpWord.toLowerCase(Locale.ROOT));
        if (!conAl && !conAn) {
            return;
        }
        System.out.println(tmpWord);
        for (int i = 0; i < 5; i++) {
            System.out.println(words[i]);
            System.out.println(words[i].equals(tmpWord));

            if (words[i].equals(tmpWord)) {
                return;
            }
        }

        //checkMatches();
        resetWord();
        //Toast.makeText(getApplicationContext(),tmpWord,Toast.LENGTH_SHORT).show();
    }

    private void resetWord() {
        for (int i = 0; i < 5; i++) {
            buffer[i] = '0';
        }
        posY++;
        posX = 0;
    }

    private void checkMatches() {
        for (int i = 0; i < 5; i++) {
            char c = SOLUTION.charAt(i);
            if (buffer[i] == c) {
                tvs[posY][i].setBackgroundResource(R.drawable.right_letter);
            }

            boolean containsLetter = false;

            for (int j = 0; j < 5; j++) {
                if (buffer[j] == c) {
                    containsLetter = true;
                    break;
                }
            }

            if (containsLetter) {
                tvs[posY][i].setBackgroundResource(R.drawable.contains_letter);
            }
        }
    }

    public void removeLetter(View view) {
        if (posX > 0) {
            posX--;
            tvs[posY][posX].setText(" ");
        }
    }
}