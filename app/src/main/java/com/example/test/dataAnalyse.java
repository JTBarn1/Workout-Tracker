package com.example.test;

import java.util.ArrayList;
import java.util.Comparator;

public class dataAnalyse {

    public static ArrayList<Workout> sortWorkouts(ArrayList<Workout> w){
        w.sort(new Comparator<Workout>() {
            @Override
            public int compare(Workout w1, Workout w2) {
                if(w1.getYear()!= w2.getYear()) return w1.getYear()-w2.getYear();
                else if(w1.getMonth()!=w2.getMonth()) return w1.getMonth()- w2.getMonth();
                else return w1.getDay()-w2.getDay();
            }
        });
        return w;
    }
}
