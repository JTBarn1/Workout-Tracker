package com.example.test;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The dataAnalyse class contains static utility methods for analyzing data.
 */

public class dataAnalyse {

    //SortWorkouts method. Sorts the ArrayList with a custom Comparator
    public static ArrayList<Workout> sortWorkouts(ArrayList<Workout> w){

        //Sorts the ArrayList
        w.sort(new Comparator<Workout>() {
            @Override

            //Custom comparator. Sorts by comparing largest non equal date.
            //ex: if years and month are equal, sorts by days.
            //if value compared is less than 0, w2 is younger. if >0, w2 is older
            public int compare(Workout w1, Workout w2) {
                if(w1.getYear()!= w2.getYear()) return w1.getYear()-w2.getYear();
                else if(w1.getMonth()!=w2.getMonth()) return w1.getMonth()- w2.getMonth();
                else return w1.getDay()-w2.getDay();
            }
        });
        //Returns sorted list,
        return w;
    }

    //checkIfWorkoutExists method. Sees if the Exercise Parameter's date matches any of the workout
    //If it does, it returns the index. If it doesn't, it returns -1
    public static int checkIfWorkoutExists(Exercise e, ArrayList<Workout> workouts){
        //My goal is to eventually make this binary search
        for(int k = 0; k<workouts.size(); k++) if(e.getDate().equals(workouts.get(k).getDate())) return k;
        return -1;
    }
}
