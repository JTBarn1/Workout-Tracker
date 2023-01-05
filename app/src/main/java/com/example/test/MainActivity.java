package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.*;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
    }
    public void workoutButtonClick(View v){
        Intent i = new Intent(this, chooseBodyPart.class);
        startActivity(i);
    }
    public void viewButtonClick(View v){
        Intent i = new Intent(this, workoutView.class);
        startActivity(i);
    }
    public void MaxClick(View v){
        Intent i = new Intent(this, oneRepMax.class);
        startActivity(i);
    }
}