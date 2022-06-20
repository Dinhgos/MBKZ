package com.example.wordle;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;

/**
 * Main Class for main function
 */
public class MainActivity extends AppCompatActivity {

//    private String[] words;
//    private char[] buffer;
//    private static int posX = 0;
//    private static int posY = 0;
//    private static TextView[][] tvs;
//    private static final String[] ALLOWED = new String[10657];
//    private static final String[] ANSWERS = new String[2315];
//    private static String SOLUTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        Button switchToSecondActivity = findViewById(R.id.startBTN);
        switchToSecondActivity.setOnClickListener(view -> switchActivities());

//        initialize();
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, Second.class);
        startActivity(switchActivityIntent);
        finish();
    }

//    private void initialize() {
//        words = new String[6];
//        for (int i = 0; i < 6; i++) {
//            words[i] = "";
//        }
//
//        buffer = new char[5];
//        for (int i = 0; i < 5; i++) {
//            buffer[i] = '0';
//        }
//
//        tvs = new TextView[6][5];
//        String resIdStr;
//        //setContentView(R.layout.fragment_second);
//
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < 5; j++) {
//                resIdStr = "tv" + i + j;
//                int resID = getResources().getIdentifier(resIdStr, "id", getPackageName());
//
//                TextView tv = findViewById(resID);
//                tvs[i][j] = tv;
//            }
//        }
//
//        loadData();
//        pickWord();
//    }

//    private void pickWord() {
//        int rNum = (int) (Math.random() * 2314);
//        SOLUTION = ANSWERS[rNum].toUpperCase();
//        System.out.println(SOLUTION);
//    }
//
//    private void loadData() {
//        BufferedReader reader;
//        try {
//            reader = new BufferedReader(new InputStreamReader(getAssets().open("wordle-allowed-guesses.txt"), StandardCharsets.UTF_8));
//
//            String mLine;
//            int counter = 0;
//            while ((mLine = reader.readLine()) != null) {
//                ALLOWED[counter] = mLine;
//                counter++;
//            }
//        } catch (IOException e) {
//            System.out.println("Could not read data from wordle-allowed-guesses.txt.");
//            System.exit(1);
//        }
//
//        try {
//            reader = new BufferedReader(new InputStreamReader(getAssets().open("wordle-answers-alphabetical.txt"), StandardCharsets.UTF_8));
//
//            String mLine;
//            int counter = 0;
//            while ((mLine = reader.readLine()) != null) {
//                ANSWERS[counter] = mLine;
//                counter++;
//            }
//        } catch (IOException e) {
//            System.out.println("Could not read data from wordle-answers-alphabetical.txt.");
//            System.exit(1);
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    public void charBTN(View v) {
//        Button b = (Button)v;
//        String buttonText = b.getText().toString();
//        char c = buttonText.charAt(0);
//
//        insertChar(c);
//    }
//
//    private void insertChar(char c) {
//        if (posX >= 5 && posY >= 6) {
//            return;
//        }
//
//        tvs[posY][posX].setText(Character.toString(c));
//
//        buffer[posX] = c;
//        posX++;
//    }
//
//    public void submitWord(View view) {
//        if (posY >= 6 || buffer[4] == '0') {
//            return;
//        }
//
//        String tmpWord = "";
//        for (char c : buffer) {
//            tmpWord += Character.toString(c);
//        }
//
//        boolean conAl = Arrays.asList(ALLOWED).contains(tmpWord.toLowerCase(Locale.ROOT));
//        boolean conAn = Arrays.asList(ANSWERS).contains(tmpWord.toLowerCase(Locale.ROOT));
//        if (!conAl && !conAn) {
//            return;
//        }
//
//        for (int i = 0; i < posY; i++) {
//            if (words[i].equals(tmpWord)) {
//                return;
//            }
//        }
//
//        words[posY] = tmpWord;
//        checkMatches();
//        resetWord();
//        //Toast.makeText(getApplicationContext(),tmpWord,Toast.LENGTH_SHORT).show();
//    }
//
//    private void resetWord() {
//        for (int i = 0; i < 5; i++) {
//            buffer[i] = '0';
//        }
//        posY++;
//        posX = 0;
//    }
//
//    private void checkMatches() {
//        for (int j = 0; j < 5; j++) {
//            tvs[posY][j].setBackgroundResource(R.drawable.no_letter);
//        }
//
//        // 0 solutin / 1 guess
//        boolean[][] indexes = new boolean[][]{{false,false,false,false,false},
//                                              {false,false,false,false,false}};
//
//        for (int i = 0; i < 5; i++) {
//            char solCh = SOLUTION.charAt(i);
//            if (buffer[i] == solCh) {
//                indexes[0][i] = true;
//                indexes[1][i] = true;
//                tvs[posY][i].setBackgroundResource(R.drawable.right_letter);
//            }
//        }
//
//        for (int i = 0; i < 5; i++) {
//            if (!indexes[0][i]) {
//                char solCh = SOLUTION.charAt(i);
//
//                for (int j = 0; j < 5; j++) {
//                    if (solCh == buffer[j]) {
//                        tvs[posY][j].setBackgroundResource(R.drawable.contains_letter);
//                        indexes[0][i] = true;
//                        indexes[1][j] = true;
//                        break;
//                    }
//                }
//            }
//        }
//
//        checkWin(indexes);
//    }
//
//    private void checkWin(boolean[][] indexes) {
//        int counter = 0;
//        for (int i = 0; i < 5; i++) {
//            if (indexes[0][i] && indexes[1][i]) {
//                counter++;
//            }
//        }
//
//        if (counter == 5) {
//            endGame("You Win!!!");
//        }
//    }
//
//    private void endGame(String msg) {
//        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            System.out.println("NO SLEEP!!!");
//        }
//        resetGame();
//    }
//
//    private void resetGame() {
////        pickWord();
//        words = new String[6];
//        for (int i = 0; i < 6; i++) {
//            words[i] = "";
//        }
//
//        buffer = new char[5];
//        for (int i = 0; i < 5; i++) {
//            buffer[i] = '0';
//        }
//
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < 5; j++) {
//                tvs[i][j].setText(R.string.empty);
//                tvs[i][j].setBackgroundResource(R.drawable.textview_border);
//            }
//        }
//
//        posY = -1;
//        posX = 0;
//    }
//
//    public void removeLetter(View view) {
//        if (posX > 0) {
//            posX--;
//            tvs[posY][posX].setText(" ");
//        }
//    }
}