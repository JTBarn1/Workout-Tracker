package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class chooseSpecifcWorkout extends AppCompatActivity {
    LinearLayout workoutList;
    int bodyPart;
    String bodyGroup;

    private String[] chestWorkouts = {"Bench Press", "Incline Bench", "Decline Bench"};
    private String[] chestImages = {"chest", "chest", "chest"};
    private String[] backWorkouts = {"Squat", "Deadlift", "Leg Press"};
    private String[] backImages = {"leg", "leg", "leg"};
    private String[] legWorkouts = {"Lat Pulldown", "Cable Row", "Bent over Row"};
    private String[] legImages = {"back", "back", "back"};
    private String[] armWorkouts = {"Dumbell Curl", "Preacher Curl", "Tricep Pushdown"};
    private String[] armImages = {"arm", "arm", "arm"};
    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_exercise);
        setTitle("Selection Page");
        Intent i = getIntent();

        bodyPart = i.getIntExtra("Body Part", 1);
        bodyGroup= i.getStringExtra("Body Group");

        workoutList = findViewById(R.id.workout_select_list);
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

public void drawWorkouts(String[] names, String[] images){
    for(int k = 0; k<names.length; k++){
        final View newWorkout = getLayoutInflater().inflate(R.layout.exercise_card,null,false);
        Button b = newWorkout.findViewById(R.id.workout);
        String s = names[k];
        b.setText(s);
        ImageView i = newWorkout.findViewById(R.id.workout_image);
        String id_image = "@drawable/"+images[k];
        int id = getResources().getIdentifier(id_image, null, getPackageName());
        i.setImageResource(id);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(chooseSpecifcWorkout.this, fillOut.class);
                i.putExtra("Exercise",  s);
                i.putExtra("BodyPart", bodyGroup);
                startActivity(i);
            }
        });
        workoutList.addView(newWorkout);
    }
}





}