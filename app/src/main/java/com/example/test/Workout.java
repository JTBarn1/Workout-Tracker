package com.example.test;

import java.util.Date;

public class Workout {
    private String id, date;
    private int sets, day, month, year;
    private int[] setWeights, setReps;

    public Workout( String id, int sets, int[] weights, int[] reps, String date){
        this.id = id;
        this.sets = sets;
        this.setWeights = weights.clone();
        this.setReps = reps.clone();
        this.date = date;

        String[] yearmonthday = date.split("/");
        day = Integer.parseInt(yearmonthday[0]);
        month = Integer.parseInt(yearmonthday[1]);
        year = Integer.parseInt(yearmonthday[2]);
    }
    public String getID(){
        return id;
    }
    public String getDate(){
        return date;
    }
    public int getSets(){
        return sets;
    }
    public int getDay(){return day;}
    public int getMonth(){return month;}
    public int getYear(){return year;}
    public int[] getSetWeights(){
        return setWeights;
    }
    public int[] getSetReps(){
        return setReps;
    }
}
