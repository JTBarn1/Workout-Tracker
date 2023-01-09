package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class chooseSpecifcWorkout extends AppCompatActivity {

    //Initialising the LinearLayout and the values that are being grabbed from the previous class
    LinearLayout workoutList;
    int bodyPart;
    String bodyGroup;

    //These Arrays of Strings are used to determine what to show for each body part selected
    //The first string is used to sex the Textview with the name of the workout
    //The second array is used to find the image in Drawable associated with the exercise
    private String[] chestWorkouts = {"Bench Press", "Incline Bench", "Decline Bench", "Dumbell bench", "Dumbell Incline", "Dumbell Decline","Chest Fly"};
    private String[] chestImages = {"chest", "incline", "decline", "dumbellbench", "dumbellincline", "dumbelldecline", "fly"};
    private String[] backWorkouts = {"Lat Pulldown", "Seated Row", "Bent over Row", "Face Pull"};
    private String[] backImages = {"back", "seatedrow", "bentover", "facepull"};
    private String[] legWorkouts = {"Squat", "Deadlift", "Leg Press", "Bulgarian S.S","Calf Raise", "Leg Extension", "Leg Curl"};
    private String[] legImages = {"leg", "deadlift", "legpress","bulgarian", "calfraise", "legextension","legcurl"};
    private String[] armWorkouts = {"Dumbell Curl", "Preacher Curl", "Tricep Pushdown", "Skull Crushers"};
    private String[] armImages = {"arm", "preacher", "tricep", "skull"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_exercise);
        setTitle("Selection Page");

        //Finds the data passed over from the previous activity, and sets the Int and String value
        Intent i = getIntent();
        bodyPart = i.getIntExtra("Body Part", 1);
        bodyGroup= i.getStringExtra("Body Group");

        //Setting the LayoutList to the linearLayout in the xml file
        workoutList = findViewById(R.id.workout_select_list);

        //Switch Statement based on The bodyGroup integer
        // 1:Chest 2: Back 3:Legs 4:Arms
        // Calls the DrawWorkouts Method based on this number, and passes through the two specific body group String arrays to the method
        switch (bodyPart) {
            case 1:
                drawWorkouts(chestWorkouts,chestImages);
                break;
            case 2:
                drawWorkouts(backWorkouts,backImages);
                break;
            case 3:
                drawWorkouts(legWorkouts,legImages);
                break;
            case 4:
                drawWorkouts(armWorkouts,armImages);
                break;
        }
    }

//drawWorkouts method. Takes 2 strings as input
//Dynamically adds cardViews to the LinearLayout LayoutList
public void drawWorkouts(String[] names, String[] images){

    //Determines how many cardViews need to be generated based on number of exercise names in the array
    for(int k = 0; k<names.length; k++){

        //Initializing the CardView exercise_card.
        final View newWorkout = getLayoutInflater().inflate(R.layout.exercise_card,null,false);

        //Setting the button text to the exercise name
        Button b = newWorkout.findViewById(R.id.workout);
        String s = names[k];
        b.setText(s);

        //Sets image
        //We find the image to display by using the hardcoded string in the array at index k
        //Images stored in drawable have same name as these strings.
        ImageView i = newWorkout.findViewById(R.id.workout_image);
        String id_image = "@drawable/"+images[k];
        int id = getResources().getIdentifier(id_image, null, getPackageName());
        i.setImageResource(id);

        //When the Button is clicked, the class fillOut is called.
        //This allows the user to input the sets they completed during the workout.
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(chooseSpecifcWorkout.this, fillOut.class);
                //Passing through 2 Strings. the name of the Exercise, and the body part associated with it.
                //String s is declared when setting the text of the button
                i.putExtra("Exercise", s);
                i.putExtra("BodyPart", bodyGroup);
                startActivity(i);
            }
        });

        //Adding the now complete CardView to the LinearLayout
        workoutList.addView(newWorkout);
    }
}





}