package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class oneRepMax extends AppCompatActivity {
    ArrayList<Workout> workouts = new ArrayList<>();
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_rep_max);
        workouts = localStore.readList(getApplicationContext());
        text = findViewById(R.id.maxText);
        int max = findMax("Bench Press",3);
        text.setText(String.valueOf(max));
    }
    public int findMax(String s, int samplesize){
        ArrayList<Workout> dataWorkouts = new ArrayList<>();
        int size = samplesize;

        //finds most recent workouts
        for(int k = workouts.size()-1; k>0; k--){
            if (size == 0) break;
            if(workouts.get(k).getID().equals(s)){
                dataWorkouts.add(workouts.get(k));
                size--;
            }
        }
        if(size > 0) return 0;

        double[] maxes = new double[samplesize];
        for(int i = 0; i<dataWorkouts.size(); i++){
            //finds theoretical max from every set completed on days workout, adds highest max to
            // the array of doubles maxes
            double max = 0;
            for(int j = 0; j<dataWorkouts.get(i).getSetReps().length; j++){
                // Brzycki formula
                double weight = dataWorkouts.get(i).getSetWeights()[j];
                double reps = dataWorkouts.get(i).getSetReps()[j];
                double setMax = (weight * (36/(37-reps)));
                System.out.println(max + " " + setMax);
                if(setMax > max) max = setMax;
            }
            maxes[i] = max;
        }
        // finds average of all maxes in array maxes
        int sum = 0;

        for(int k = 0; k<samplesize; k++){
            System.out.println(maxes[k]);
            sum += maxes[k];
        }

        return sum/samplesize;
    }
}