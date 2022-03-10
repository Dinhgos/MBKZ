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
import android.widget.Toast;

/**
 * Main Class for main function
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private String words[];
    private char[] buffer;
    private static int posX = 0;
    private static int posY = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.wordle.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

    private void test1234(View v) {
        if (posY >= 6 || buffer[4] == '0') {
            return;
        }

        String tmpWord = "";

        for (char c : buffer) {
            tmpWord += Character.toString(c);
        }

        words[posY] = tmpWord;

        Context c = v.getContext();

        Toast.makeText(c,tmpWord, Toast.LENGTH_SHORT).show();
    }

    public void charBTN(View v) {
        Context c1 = v.getContext();
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        char c = buttonText.charAt(0);
        Toast.makeText(c1, buttonText, Toast.LENGTH_SHORT).show();

        insertChar(c);
    }

    private void insertChar(char c) {
        if (posY >= 6) {
            return;
        }

        buffer[posX] = c;
        posX++;

        if (posX >= 5) {
            posX = 0;
            posY++;
        }
    }

    public void test123(View view) {
        if (posY >= 6 || buffer[4] == '0') {
            return;
        }

        String tmpWord = "";

        for (char c : buffer) {
            tmpWord += Character.toString(c);
        }

        words[posY] = tmpWord;

        Context c = view.getContext();

        Toast.makeText(c,tmpWord, Toast.LENGTH_SHORT).show();
    }


    public void test55(View v) {
        System.out.println("test55");
    }
}