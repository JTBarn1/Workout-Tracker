package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class workoutView extends AppCompatActivity {

    //Initialising the LinearLayout and the ArrayList
    LinearLayout LayoutList;
    ArrayList<Workout> workouts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_view);
        setTitle("Previous Workouts");

        //Setting the LayoutList to the linearLayout in the xml file
        LayoutList=findViewById(R.id.complete_workout_list);

        //Grabbing the Arraylist from SharedPreferences
        workouts = localStore.readList(getApplicationContext());

        //Dynamically calls the method displayCards based on number of items in the ArrayList
        for(int k = 0; k< workouts.size(); k++) displayCards(workouts.get(k),k);
    }

    // Creates Cardview workout_display_card with a Workout class W and an integer I
    public void displayCards(Workout w, int i){
        final View newWorkout = getLayoutInflater().inflate(R.layout.workout_display_card,null,false);

        //Sets image
        //We find the image to display by using the bodyGroup String stored in the workout class
        //Images stored in drawable have same name as these strings
        ImageView bodyImage = (ImageView) newWorkout.findViewById(R.id.workout_image);
        String id_image = "@drawable/"+w.getBodyGroup();
        int id = getResources().getIdentifier(id_image, null, getPackageName());
        bodyImage.setImageResource(id);

        //Sets body part
        //Wue to the fact that the body group iss tore in an uncapitalized string,
        //We have to re Capitalize it.
        TextView bodyPart = (TextView) newWorkout.findViewById(R.id.workout_text_bodypart);
        String bp = w.getBodyGroup();
        //Re capitalizing the string
        bodyPart.setText(bp.substring(0, 1).toUpperCase() + bp.substring(1) + " Day");

        //Sets date
        TextView Date = (TextView) newWorkout.findViewById(R.id.workout_text_date);
        Date.setText(w.getDate());

        //Sets number of exercises
        TextView exercises = (TextView) newWorkout.findViewById(R.id.workout_text_exercises);
        exercises.setText(String.valueOf(w.getNumOfExercises()) + " exercise logged");

        //Sets Total weight lifted
        TextView weightLifted = (TextView) newWorkout.findViewById((R.id.workout_text_weightLifted));
        weightLifted.setText(String.valueOf(w.getTotalWeightLifted())+ " pounds lifted");

        ImageView exerciseView = (ImageView) newWorkout.findViewById(R.id.workout_expand);

        //When the ImageView is clicked, the class ExerciseView is called.
        //This allows the user to see each exercise they entered, in a neat list.
        exerciseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(workoutView.this, ExerciseView.class);
                //passing through the index of the Workout in the Arraylist<workout> being edited
                intent.putExtra("Index",  i);
                startActivity(intent);
            }
        });
        //Adding the now complete CardCiew to the LinearLayout
        LayoutList.addView(newWorkout);
    }

}