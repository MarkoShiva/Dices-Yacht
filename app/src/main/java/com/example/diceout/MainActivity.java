package com.example.diceout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // TextView field to have result
    TextView rollResult;

    TextView scoreText;


    // Field to hold the score
    int score;

    // random instance
    Random rand;

    // die1 value
    int die1;
    int die2;
    int die3;
    int die4;
    int die5;

    // array dice values
    ArrayList<Integer> dices;

    // Array of images
    ArrayList<ImageView> diceImages;

    ImageView dice1Image;
    ImageView dice2Image;
    ImageView dice3Image;
    ImageView dice4Image;
    ImageView dice5Image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });
        // Set initial score
        score = 0;

        // Connect button and text view to the instance of the class
        rollResult = findViewById(R.id.rollResult);
        scoreText = findViewById(R.id.scoreView);

        // init random
        rand = new Random();

        // init the dice
        dices = new ArrayList<Integer>(5);

        // Assign three images to the Array of images
        diceImages = new ArrayList<ImageView>(5);
        dice1Image = findViewById(R.id.die1Image);
        dice2Image = findViewById(R.id.die2Image);
        dice3Image = findViewById(R.id.die3Image);
        dice4Image = findViewById(R.id.die4Image);
        dice5Image = findViewById(R.id.die5Image);

        diceImages.add(dice1Image);
        diceImages.add(dice2Image);
        diceImages.add(dice3Image);
        diceImages.add(dice4Image);
        diceImages.add(dice5Image);




        // Create greeting
        Toast.makeText(this, "Welcome to DiceOut", Toast.LENGTH_SHORT).show();
    }
    public void rollDice(View v){
        rollResult.setText("Clicked");

        // ToDo refactor to use the array instead of fields.
        // That is a wrong way of doing things
        // Roll dice
        die1 = rand.nextInt(6) + 1;
        die2 = rand.nextInt(6) + 1;
        die3 = rand.nextInt(6) + 1;
        die4 = rand.nextInt(6) + 1;
        die5 = rand.nextInt(6) + 1;
        // clear the array
        dices.clear();
        // populate the array
        dices.add(die1);
        dices.add(die2);
        dices.add(die3);
        dices.add(die4);
        dices.add(die5);

//        Collections.fill(dices, 5);

        // better use array access and get rid of not needed fields.
        // for some reason that do not work
//        dice.set(0, rand.nextInt(6) + 1);
//        dice.set(1, rand.nextInt(6) + 1);
//        dice.set(2, rand.nextInt(6) + 1);


        // Printing dice sides

        for (int dicesOfSet = 0; dicesOfSet < 5; dicesOfSet++){
            String imageName = "die_" + dices.get(dicesOfSet) + ".png";
            try {
                InputStream stream = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(stream, null);
                diceImages.get(dicesOfSet).setImageDrawable(d);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Checking dice scores
//        String msg = "You rolled " + die1 + " and a " + die2 + " and a " + die3;
        String msg = "You rolled " + dices.get(0) + " and a " + dices.get(1) + " and a " + dices.get(2);
        String msg1;
        ArrayList<Integer> results = new ArrayList<Integer>(Arrays.asList(new Integer[7]));
        Collections.fill(results, 0);
        System.out.println(results);
//        System.out.println(results.get(0) + " " + results.get(2));

        // We use array fro 1 to 6 to store amount of each number rolls
        // Here we loop through that array and assign a value of successful roll of a number
        for (int die: dices) {
            results.set(die, results.get(die) + 1);
            System.out.println("Value of " + die + " is rolled " + results.get(die) + " times.");
        }

        // We are finding maximum of same number scores. If scores are 5 or 4 we don't need
        // to check the rest
        int maximum = Collections.max(results);
        int num = results.indexOf(maximum);
        int rollScore;
        if (maximum == 5) {
            rollScore = 1500;
            msg1 = "You rolled all five of " + num + ". Yacht. You get " + rollScore + " points";
            score += rollScore;
        }
        else if (maximum == 4){
            rollScore = 1000;
            msg1 = "You rolled poker of " + num + ". You get " + rollScore + " points";
            score += rollScore;

        }else if (maximum == 3) {
            results.set(results.indexOf(maximum), 0);
            if (Collections.max(results) == 2){
                    rollScore = 750;
                    msg1 = "You rolled full house. You get " + rollScore + " points";
                    score += rollScore;
                }
            else {
                rollScore = 500;
                msg1 = "You rolled trilling of " + num + ". You get " + rollScore + " points";
                score += rollScore;
            }
        } else if (maximum == 2){
            results.set(results.indexOf(maximum), 0);
            if (Collections.max(results) == 2){
                rollScore = 100;
                msg1 = "You rolled a two pairs and get " + rollScore + " points";
                score += rollScore;
            }
            else{
                rollScore = 50;
                msg1 = "You rolled a pair and get "+ rollScore +" points";
                score += rollScore;
            }
        }
        else {
            msg1 = "Try again";
        }

        // Update the message
        rollResult.setText(msg1);
        scoreText.setText("Score: " + score);


//        int num = rand.nextInt(6) + 1;
//        String randValue = "Number generated " + num;
//        Toast.makeText(this, randValue, Toast.LENGTH_SHORT).show();
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
}
