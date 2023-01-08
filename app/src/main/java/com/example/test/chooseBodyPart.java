package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class chooseBodyPart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_body_part);
        setTitle("Selection Page");
    }

    //Calls method of user chooses to enter a chest exercise
    public void ChestButtonClick(View v) {
        Intent i = new Intent(this, chooseSpecifcWorkout.class);
        i.putExtra("Body Part", 1);
        i.putExtra( "Body Group", "chest");
        //Passes through 2 variables to the ChooseSpecificWorkout class.
        //Body Part is used to find the index of a hardcoded array
        //Body Proup is used to determine the Workouts primary body group (More details in the Workout class).
        startActivity(i);
    }

    //Calls method of user chooses to enter a back exercise
    public void BackButtonClick(View v){
        Intent i = new Intent(this, chooseSpecifcWorkout.class);
        i.putExtra("Body Part", 2);
        i.putExtra( "Body Group", "back");
        //Passes through 2 variables to the ChooseSpecificWorkout class.
        //Body Part is used to find the index of a hardcoded array
        //Body Proup is used to determine the Workouts primary body group (More details in the Workout class).
        startActivity(i);
    }

    //Calls method of user chooses to enter a leg exercise
    public void LegButtonClick(View v){
        Intent i = new Intent(this, chooseSpecifcWorkout.class);
        i.putExtra("Body Part", 3);
        i.putExtra( "Body Group", "leg");
        //Passes through 2 variables to the ChooseSpecificWorkout class.
        //Body Part is used to find the index of a hardcoded array
        //Body Proup is used to determine the Workouts primary body group (More details in the Workout class).
        startActivity(i);
    }

    //Calls method of user chooses to enter a arm exercise
        public void ArmButtonClick(View v){
        Intent i = new Intent(this, chooseSpecifcWorkout.class);
        i.putExtra("Body Part", 4);
        i.putExtra( "Body Group", "arm");
        //Passes through 2 variables to the ChooseSpecificWorkout class.
        //Body Part is used to find the index of a hardcoded array
        //Body Proup is used to determine the Workouts primary body group (More details in the Workout class).
        startActivity(i);
    }
}