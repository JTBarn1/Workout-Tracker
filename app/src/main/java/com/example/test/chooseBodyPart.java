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

    public void ChestButtonClick(View v) {
        Intent i = new Intent(this, chooseSpecifcWorkout.class);
        i.putExtra("Body Part", 1);
        startActivity(i);
    }
    public void BackButtonClick(View v){
        Intent i = new Intent(this, chooseSpecifcWorkout.class);
        i.putExtra("Body Part", 2);
        startActivity(i);
    }
    public void LegButtonClick(View v){
        Intent i = new Intent(this, chooseSpecifcWorkout.class);
        i.putExtra("Body Part", 3);
        startActivity(i);
    }
    public void ArmButtonClick(View v){
        Intent i = new Intent(this, chooseSpecifcWorkout.class);
        i.putExtra("Body Part", 4);
        startActivity(i);
    }
}