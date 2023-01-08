package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.*;
import android.os.Bundle;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
    }

    //Calls method when add new exercise button is pressed.
    public void workoutButtonClick(View v){
        Intent i = new Intent(this, chooseBodyPart.class);
        startActivity(i);
    }
    //Calls method when view previous exercises button is pressed.
    public void viewButtonClick(View v){
        Intent i = new Intent(this, workoutView.class);
        startActivity(i);
    }

    //Calls method when calculate one rep max button is pressed.
        public void MaxClick(View v){
        Intent i = new Intent(this, oneRepMax.class);
        startActivity(i);
    }
}