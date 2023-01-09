package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class oneRepMax extends AppCompatActivity {

    //Declaring the ArrayList and the LinearLayout
    ArrayList<Workout> workouts;
    LinearLayout LayoutList;

    //Declaring the EditTexts and the textView
    EditText enteredWeght, enteredReps;
    TextView result;

    HashMap<String, ArrayList<Exercise>> recentExercises = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_rep_max);
        setTitle("One Rep Max Calculator");

        //Setting the EditTexts and textviews to their respected values in the xml
        enteredWeght = (EditText) findViewById(R.id.Weight);
        enteredReps = (EditText) findViewById(R.id.reps);
        result = (TextView) findViewById(R.id.result);

        //Grabbing the ArrayList from SharedPreferences
        workouts = localStore.readList(getApplicationContext());

        //Calling a method that returns a hashmap of all Exercise grouped by their id.
        //Only the Three most recent exercises for each Id are included in this hashmap
        recentExercises = dataAnalyse.GroupExercises(workouts);

        //Setting the LayoutList to the linearLayout in the xml file
        LayoutList = findViewById(R.id.max_list);

        //Finds and displays max for each element in the hashmap
        recentExercises.forEach((id,exercises) -> {
            if(exercises.size() >= 2) displaymax(id,exercises);
        });

    }

    //displayMax method. Creates a LinearLayout from the cml file onerepmax and adds it to the LayoutList
    //Also calculates a one rep max from the best set from all the exercises in the arraylist.
    public void displaymax(String id, ArrayList<Exercise> exercises){
        final View newMax = getLayoutInflater().inflate(R.layout.onerepmax,null,false);

        //Setting the name of the specific exercise's max
        TextView bodyPart = (TextView) newMax.findViewById(R.id.max_body_part);
        bodyPart.setText(id + ":");

        //Calculating the one rep max, using the ArrayList of Exercises
        int oneRepMax = dataAnalyse.findMax(exercises);

        //Setting the one rep max, after calculating it
        TextView max = (TextView) newMax.findViewById(R.id.max_max);
        max.setText(String.valueOf(oneRepMax) + " Pounds");

        //Adding to LayoutList
        LayoutList.addView(newMax);
    }

    //calculateEnteredMax method. Takes entered weight and rep values and displays a calculated one rep max
    //Called when the calculate button is pressed
    public void calculateEnteredMax(View v){

        //Getting the weight and rep values and checking if they are valid
        //Doubles are used because it is required for the one rep max calculation
        double weight = checkValid(enteredWeght.getText().toString());
        double reps = checkValid(enteredReps.getText().toString());

        //Checks to see if either of the entered values were not valid
        //If they were not valid, tells user to enter details correctly
        if(weight <0 || reps <0) Toast.makeText(this, "Enter Details Correctly", Toast.LENGTH_SHORT).show();

        //Sets the result textView to the calculated one rep max of the entered values
        //Max is casted to an integer because it looks cleaner without decimal points
        else result.setText(String.valueOf((int)dataAnalyse.BrzyckiFormula(weight,reps)));
    }

    //checkValid method. Checks if String entered can be safely parsed into a Double.
    //If not, returns -1.
    public double checkValid(String s) {
        double result;

        //Try Catch to see if parsing throws any errors
        try{ result = Double.parseDouble(s);}
        catch (NumberFormatException e) {return -1;}
        catch (NullPointerException e) {return -1;}

        //Returns result
        return result;
    }
}