package com.example.wordle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * Class for main game function.
 */
public class Second extends Activity {
    /** list of already guessed words */
    private static String[] words;
    /** buffer for game input */
    private static char[] buffer;
    /** x index of board */
    private static int posX = 0;
    /** y index of board */
    private static int posY = 0;
    /** board of letters */
    private static TextView[][] tvs;
    /** list of allowed guesses */
    private static final String[] ALLOWED = new String[10657];
    /** list of answers */
    private static final String[] ANSWERS = new String[2315];
    /** one random word from ANSWERS list */
    private static String SOLUTION;
    /** true = correct guess / false = answer not guessed */
    private static boolean win = false;
    /** list of correct buttons */
    private static ArrayList<Button> btnList = new ArrayList<>();

    /**
     * create activity
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);

        Button backButton = findViewById(R.id.button_second);
        backButton.setOnClickListener(view -> mainMenu());

        initialize();
    }

    /**
     * clears game and switches back to main menu
     */
    private void mainMenu() {
        resetGame();
        switchActivities(MainActivity.class);
    }

    /**
     * disable back button
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * initialize game after start
     */
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
        //setContentView(R.layout.fragment_second);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                resIdStr = "tv" + i + j;
                int resID = getResources().getIdentifier(resIdStr, "id", getPackageName());

                TextView tv = findViewById(resID);
                tvs[i][j] = tv;
            }
        }

        loadData();
        pickWord();
    }

    /**
     * pick a random word as solution from ANSWERS list
     */
    private void pickWord() {
        int rNum = (int) (Math.random() * 2314);
        SOLUTION = ANSWERS[rNum].toUpperCase();
//        SOLUTION = "LEAST";
        System.out.println(SOLUTION);

    }

    /**
     * reads data from assets and then creates ANSWERS and ALLOWED list
     */
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

    /**
     * switches activitys
     * @param cl next activity
     */
    private void switchActivities(Class cl) {
        Intent switchActivityIntent = new Intent(this, cl);
        startActivity(switchActivityIntent);
        finish();
    }

    /**
     * main function of every button
     * gets char from button then inserts char into buffer
     * @param v view of the button pressed
     */
    public void charBTN(View v) {
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        char c = buttonText.charAt(0);

        insertChar(c);
    }

    /**
     * handles inserting the character into buffer and displaying it on the board
     * @param c inserted character
     */
    private void insertChar(char c) {
        if (posX >= 5) {
//            Toast.makeText(getApplicationContext(),"Out of space for char.", Toast.LENGTH_SHORT).show();
            return;
        }

        tvs[posY][posX].setText(Character.toString(c));

        buffer[posX] = c;
        posX++;
    }

    /**
     * creates a five letter word from inserted characters and
     * checks if word is allowed to guess
     * @param view button
     */
    public void submitWord(View view) {
        if (posY >= 6 || buffer[4] == '0' || posX != 5) {
//            Toast.makeText(getApplicationContext(),"Do nothing", Toast.LENGTH_SHORT).show();
            return;
        }

        String tmpWord = "";
        for (char c : buffer) {
            tmpWord += Character.toString(c);
        }

        boolean conAl = Arrays.asList(ALLOWED).contains(tmpWord.toLowerCase(Locale.ROOT));
        boolean conAn = Arrays.asList(ANSWERS).contains(tmpWord.toLowerCase(Locale.ROOT));
        if (!conAl && !conAn) {
//            Toast.makeText(getApplicationContext(),"Invalid word.", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < posY; i++) {
            if (words[i].equals(tmpWord)) {
//                Toast.makeText(getApplicationContext(),"Word already guessed.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        words[posY] = tmpWord;
//        Toast.makeText(getApplicationContext(),words[posY], Toast.LENGTH_SHORT).show();
        checkMatches();
        resetWord();
    }

    /**
     * move pointer to next line after a word has been guessed
     */
    private void resetWord() {
        for (int i = 0; i < 5; i++) {
            buffer[i] = '0';
        }
        posY++;
        posX = 0;
    }

    /**
     * checks matches of guessed word and the answer, then
     * it colors the board and buttons accordingly to the matches
     */
    private void checkMatches() {
        Button btn;
        String resIdStr;
        int resID;
        for (int j = 0; j < 5; j++) {
            tvs[posY][j].setBackgroundResource(R.drawable.no_letter);

            resIdStr = Character.toLowerCase(buffer[j]) + "BTN";
            resID = getResources().getIdentifier(resIdStr, "id", getPackageName());
            btn = findViewById(resID);
            btn.setBackgroundResource(R.drawable.no_letter);
        }

        // 0 solutin / 1 guess
        boolean[][] indexes = new boolean[][]{{false,false,false,false,false},
                {false,false,false,false,false}};

        for (int i = 0; i < 5; i++) {
            char solCh = SOLUTION.charAt(i);
            if (buffer[i] == solCh) {
                indexes[0][i] = true;
                indexes[1][i] = true;
                tvs[posY][i].setBackgroundResource(R.drawable.right_letter);

                resIdStr = Character.toLowerCase(solCh) + "BTN";
                resID = getResources().getIdentifier(resIdStr, "id", getPackageName());
                btn = findViewById(resID);
                btn.setBackgroundResource(R.drawable.right_letter);
                btnList.add(btn);
            }
        }

        checkWin(indexes);

        for (int i = 0; i < 5; i++) {
            if (!indexes[0][i]) {
                char solCh = SOLUTION.charAt(i);

                for (int j = 0; j < 5; j++) {
                    if (solCh == buffer[j]) {
                        tvs[posY][j].setBackgroundResource(R.drawable.contains_letter);

                        resIdStr = Character.toLowerCase(solCh) + "BTN";
                        resID = getResources().getIdentifier(resIdStr, "id", getPackageName());
                        btn = findViewById(resID);
                        btn.setBackgroundResource(R.drawable.contains_letter);

                        indexes[0][i] = true;
                        indexes[1][j] = true;
                        break;
                    }
                }
            }
        }

        for (Button button : btnList) {
            button.setBackgroundResource(R.drawable.right_letter);
        }

        checkLose();
    }

    /**
     * checks if it is a lost game
     */
    private void checkLose() {
        if (posY == 5) {
            switchActivities(EndGameActivity.class);
        }
    }

    /**
     * checks if it is a won game
     * @param indexes array of matches
     */
    private void checkWin(boolean[][] indexes) {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            if (indexes[0][i] && indexes[1][i]) {
                counter++;
            }
        }

        if (counter == 5) {
            win = true;
            switchActivities(EndGameActivity.class);
        }
    }

    /**
     * resets all values for next game
     */
    public static void resetGame() {
        words = new String[6];
        for (int i = 0; i < 6; i++) {
            words[i] = "";
        }

        buffer = new char[5];
        for (int i = 0; i < 5; i++) {
            buffer[i] = '0';
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                tvs[i][j].setText(R.string.empty);
                tvs[i][j].setBackgroundResource(R.drawable.textview_border);
            }
        }

        posY = 0;
        posX = 0;
        win = false;
        btnList = new ArrayList<>();
    }

    /**
     * removes a letter from buffer and board
     * @param view button
     */
    public void removeLetter(View view) {
        if (posX > 0) {
            posX--;
            tvs[posY][posX].setText(" ");
        }
    }

    public static TextView[][] getTvs() {
        return tvs;
    }

    public static int getPosY() {
        return posY;
    }

    public static boolean isWin() {
        return win;
    }
}
