package com.example.test;

import java.util.ArrayList;
import java.util.Comparator;

public class dataAnalyse {

    public static ArrayList<Exercise> sortWorkouts(ArrayList<Exercise> e){
        e.sort(new Comparator<Exercise>() {
            @Override
            public int compare(Exercise w1, Exercise w2) {
                if(w1.getYear()!= w2.getYear()) return w1.getYear()-w2.getYear();
                else if(w1.getMonth()!=w2.getMonth()) return w1.getMonth()- w2.getMonth();
                else return w1.getDay()-w2.getDay();
            }
        });
        return e;
    }
    public static int checkIfWorkoutExists(Exercise e, ArrayList<Workout> workouts){
        //eventually make this binary search
        for(int k = 0; k<workouts.size(); k++) if(e.getDate().equals(workouts.get(k).getDate())) return k;
        return -1;
    }
}
