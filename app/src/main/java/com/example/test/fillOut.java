package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * The fillOut class is an Android activity that allows a user to enter information about
 * an exercise. The activity displays a form for the user to enter the details of the exercise,
 * including the date, number of sets, weights and reps for each set.
 */

public class fillOut extends AppCompatActivity {

    //Initializing the ArrayList and the Exercise to be created/edited
    ArrayList<Workout> workouts = new ArrayList<>();
    Exercise e;

    //Initializing the LinearLayout and the editText
    LinearLayout LayoutList;
    EditText dateText;

    //Initializing the Strings, Integers, And booleans
    String name, date, bodyGroup;
    Boolean isEdit;
    int indexExercise,indexWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_out);

        //Grabbing the Arraylist from SharedPreferences
        workouts = localStore.readList(getApplicationContext());

        //Setting the listed parameters from info passed through from other Activities
        //NOTE: IsEdit, indexExercise, and indexWorkout are only passed through when editing an exercise
        //name and bodyGroup are only passed through when entering a new exercise
        Intent i = getIntent();
        bodyGroup = i.getStringExtra("BodyPart");
        isEdit = i.getBooleanExtra("isEdit", false);
        indexExercise = i.getIntExtra("IndexExercise", 0);
        indexWorkout = i.getIntExtra("IndexWorkout", 0);

        //Grabbing the passed through name and setting the title of the activity based off the name
        //When editing an exercise, the name is set in the method AddFilledExercise.
        name = i.getStringExtra("Exercise");
        setTitle(name);

        //Setting these parameters to their respected values in the xml file
        LayoutList = findViewById(R.id.set_list);
        dateText = findViewById(R.id.workoutDate);

        //If the fill out page is being used to edit an exercise, the previously inputted sets are filled
        //This is done in the addFilledExercise method
        if(isEdit) AddFilledExercise();
    }

    //AddFilledExercise method.
    //This method instantiates the Exercise e, and sets the parameters bodyGroup and name based off of e's properties
    //this method also sets the TextView dateEdit's text to the Exercise's date
    //This method dynamically calls the method addFilledSet, which draws a pre filled set to the linearLayout.
    public void AddFilledExercise(){
        //Instantiating Exercise e using the index of the workout, and the index of the exercise inside the workout.
        e = workouts.get(indexWorkout).getExerciseList().get(indexExercise);

        //Setting title
        setTitle(e.getID());

        //Setting the parameters from properties from e
        bodyGroup = e.getBodyGroup();
        name = e.getID();

        //Setting the date in the xml file
        View date = findViewById(R.id.workoutDate);
        EditText dateEdit = (EditText) date;
        dateEdit.setText(e.getDate());

        //Dynamically calls the AddFilledSet method for every set stored in the Exercise's array of Sets.
        for(int k = 0; k<e.getSetReps().length; k++) addFilledSet(e, k);
    }

    //addFilledSet method. Takes 2 parameters, An Exercise exercise, and an integer k
    // Creates and fills the linearLayout set_add, and adds it to the LinearLayout
    // Gets the weight and rep values by grabbing the kth index of the arrays setWeights and setReps
    public void addFilledSet(Exercise exercise, int k){
        final View newSet = getLayoutInflater().inflate(R.layout.set_add,null,false);

        //Sets the weight of the set
        EditText setWeight = (EditText) newSet.findViewById(R.id.set_weight);
        setWeight.setText(String.valueOf(exercise.getSetWeights()[k]));

        //Sets the reps done during the set
        EditText setReps = (EditText) newSet.findViewById(R.id.set_reps);
        setReps.setText(String.valueOf(exercise.getSetReps()[k]));

        //When the imageView is clicked, the set is deleted
        ImageView setCLose = (ImageView) newSet.findViewById(R.id.set_remove);
        setCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(newSet);
            }
        });

        //Adding the filled out linearLayout to LayoutList
        LayoutList.addView(newSet);
    }

    //addSet method. Is called when the plus button is pressed, and creates an empty set_add LinearLayout
    public void addSet(View v) {
        final View newSet = getLayoutInflater().inflate(R.layout.set_add,null,false);

        //When the imageView is clicked, the set is deleted
        ImageView setCLose = (ImageView) newSet.findViewById(R.id.set_remove);
        setCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(newSet);
            }
        });

        //Adding the linearLayout to LayoutList
        LayoutList.addView(newSet);
    }

    //Removes the set from the LinearLayout LayoutList
    private void removeView(View v){
        LayoutList.removeView(v);
    }

    //todayClick method. Called when the today button is clicked.
    //sets the date EditText to today's date in a DD/MM/YY format.
    public void todayCLick(View v){
        View date = findViewById(R.id.workoutDate);

        //Creating the DD/MM/YY String
        Calendar now = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(now.getTime());

        //Setting the date
        EditText dateEdit = (EditText) date;
        dateEdit.setText(currentDate);
    }

    //save method. Called when the save button is clicked
    //Either adds the exercise to a workout, or edits the exercise, and locally stores the ArrayList of workouts in SharedPreferences.
    //Checks to see of the text that was inputted was valid.
    //Since this is an Early beta version, Text to number recognition is not yet available
    //Same applies to a drop down calendar to select the date
    public void save(View v){
        boolean valid = true;

        //Finds how many sets have been created, based off number of children in the LinearLayout
        //Creates two Arrays with the length of the sets
        int sets = LayoutList.getChildCount();
        int[] setWeights = new int[sets];
        int[] setReps = new int[sets];

        //Checks to see if the entered date is valid, and won't cause any errors.
        if(dateValid(dateText.getText().toString())) date = dateText.getText().toString();
        else valid = false;

        //Tests every inputted set and rep to see if they are valid inputs
        //Sets the previously created Arrays with their respected values
        for(int i=0; i<sets; i++){

            //Grabs the ith set
            View setView = LayoutList.getChildAt(i);

            //Checks if the inputted weight is valid, and sets the ith index of setWeights with its value.
            EditText setWeight = (EditText) setView.findViewById(R.id.set_weight);
            try{ setWeights[i] = Integer.parseInt(setWeight.getText().toString());}
                catch(NumberFormatException e) {valid = false;}
                catch(NullPointerException e) {valid = false;}

            //Checks if the inputted rep number is valid, and sets the ith index of setReps with its value.
            EditText setRep = (EditText) setView.findViewById(R.id.set_reps);
            try{setReps[i] = Integer.parseInt(setRep.getText().toString());}
                catch(NumberFormatException e) {valid = false;}
                catch(NullPointerException e) {valid = false;}
        }

        //If there are no inputted sets, nothing is saved, and the user is told to add sets.
        if(sets==0) Toast.makeText(this, "Add Sets First", Toast.LENGTH_SHORT).show();

        //If any input was found to be invalid, nothing is saved, and the user is told to fix the issue.
        else if(!valid){
            Toast.makeText(this, "Enter Details Correctly", Toast.LENGTH_SHORT).show();;
        }

        //if it passes the above checks, The Exercise is saved.
        else{

        //Instantiating the New exercise using the inputted data and the recently created Arrays
        e = new Exercise(name, sets, setWeights, setReps, date, bodyGroup);

        //Sets integer as the index of the workout with the same date as the exercise
        //If there isn't one, i = -1
        int i = dataAnalyse.checkIfWorkoutExists(e, workouts);

        // If there is a workout with the same date. add the exercise to it.
        if(i>=0){

            //If the fill out page is editing a exercise, replace the exercise with the new one
            if (isEdit) workouts.get(i).editExercise(e, indexExercise);

            //Otherwise add the exercise to the workout.
            else workouts.get(i).addExercise(e);
        }

        //Otherwise, Create a new workout with the Exercise e
        else{

            //Creates new workout and adds it to the ArrayList
            Workout w = new Workout(e);
            workouts.add(w);
        }

        //Sorts the ArrayList by date
        workouts = dataAnalyse.sortWorkouts(workouts);

        //Writing the ArrayList of workouts to SharedPreferences
        localStore.writeList(workouts,getApplicationContext());

        //Lets user know data is saved.
        Toast.makeText(this, "Exercise Successfully Saved", Toast.LENGTH_SHORT).show();
        }
    }

    //dateValid Method. Checks if String can be parsed in the DD/MM/YY format.
    //If it can, it returns true.
    public boolean dateValid(String d) {

        //Sees if the String can be turned into a SimpleDateFormat with the DD/MM/YY pattern
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        sdf.setLenient(true);

        //If it cannot parse, the date is not valid, and the method returns false.
        try {
            Date da = sdf.parse(d);
        } catch (ParseException e) {
            return false;
        }

        //Otherwise returns true
        return true;
    }

}