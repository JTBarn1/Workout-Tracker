package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * The ExerciseView class is an Android activity that allows a user to view a list of previously
 * performed exercises within a workout. The activity displays the name, date, and number of sets for each exercise
 * in the list. The user can also delete or edit an exercise by clicking the corresponding button
 * next to each exercise.
 */

public class ExerciseView extends AppCompatActivity {

    //Declaring the Variables to be used later
    LinearLayout LayoutList;
    int index;
    ArrayList<Exercise> exercises = new ArrayList<>();
    ArrayList<Workout> workouts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_view);
        setTitle("Previous Exercises");

        //Setting the LayoutList to the linearLayout in the xml file
        LayoutList=findViewById(R.id.complete_set_list);

        //Setting the integer index from info passed through the previous activity
        Intent i  = getIntent();
        index = i.getIntExtra("Index", 0);

        //Grabbing the Arraylist of workouts form SharesPreferences, finding the workout being expanded,
        //and grabbing its ArrayList of Exercises
        workouts = localStore.readList(getApplicationContext());
        exercises = workouts.get(index).getExerciseList();

        //Dynamically calls the method fillSets based on number of items in the ArrayList
        for(int k = 0; k< exercises.size(); k++) fillsets(exercises.get(k),k);
    }

    //Creates a LinearLayout display_exercise using an Exercise e, and an Integer i as parameters.
    //Adds filled out LinearLayout to LayoutList
    public void fillsets(Exercise e, int i){
        final View newWorkout = getLayoutInflater().inflate(R.layout.display_exercise,null,false);

        //Sets the name of the set
        TextView Name = (TextView) newWorkout.findViewById(R.id.name);
        Name.setText(e.getID());

        //Sets the Date of the set
        TextView Date = (TextView) newWorkout.findViewById(R.id.date);
        Date.setText(e.getDate());

        //Sets the number of sets completed
        TextView Sets = (TextView) newWorkout.findViewById(R.id.sets);

        //checks number of Sets, if >1, changes String to a plural form
        String singularOrPlural;
        if(e.getSets() == 1) singularOrPlural = " exercise";
        else singularOrPlural = " exercises";
        Sets.setText(String.valueOf(e.getSets()) + singularOrPlural);

        ImageView setClose = (ImageView) newWorkout.findViewById(R.id.set_remove);

        //When the imageView is clicked, the exercise is deleted
        //Passes the index of the exercise to the removeView method
        setClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(newWorkout,i);
            }

        });

        ImageView setedit = (ImageView) newWorkout.findViewById(R.id.set_edit);

        //When the imageView is clicked, the exercise is Edited
        setedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editWorkout(newWorkout,i);
            }

        });

        //Adding the now complete LinearLayout to the LayoutList
        LayoutList.addView(newWorkout);
    }

    //RemoveView method. Removes the Exercise from the LinearLayout and the Workout
    public void removeView(View v, int i){

        //Removing it from the workout
        //Removes the ith exercise from the ArrayList
        workouts.get(index).removeExercise(i);

        //If all Exercises are removed, Delete workout
        if(workouts.get(index).getExerciseList().size() == 0) workouts.remove(index);

        //Saving the changes
        localStore.writeList(workouts, getApplicationContext());

        //Removing it from the LinearLayout
        LayoutList.removeView(v);
    }

    //editWorkout method. Uses the kth index of the workout's Arraylist of exercises to find the exercise to edit
    // sends parameters to the fillOut activity.
    public void editWorkout(View v, int k){
        Intent i = new Intent(this, fillOut.class);

        //Passes through the workout name
        i.putExtra("Workout", exercises.get(k).getID());

        //Passes through the index of the workout, and the exercise within the workout
        i.putExtra("IndexWorkout", index);
        i.putExtra("IndexExercise", k);

        //Passes through the information that this is an edit of an exercise
        i.putExtra("isEdit", true);
        startActivity(i);
    }
}