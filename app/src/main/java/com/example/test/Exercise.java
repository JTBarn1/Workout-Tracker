package com.example.test;

/**
 * The Exercise class represents an exercise performed by a user. It includes information
 * such as the ID of the exercise, the number of sets, the weights and reps for each set,
 * the date on which the exercise was performed, and the body group that the exercise targets.
 */

public class Exercise {
    //Declcaring the fields for the class
    private String id, date, bodyGroup;
    private int sets, day, month, year;
    private int[] setWeights, setReps;


    //Constructor for the class
    //Takes 6 parameters: id, sets, weights, reps, date, and group
    public Exercise(String id, int sets, int[] weights, int[] reps, String date, String group){
        this.id = id;
        this.sets = sets;
        this.setWeights = weights.clone();
        this.setReps = reps.clone();
        this.date = date;
        this.bodyGroup= group;

        //Splits the date string into 3 parts
        String[] yearmonthday = date.split("/");

        //initialises the day, month, and year values from their respective parts in the array.
        day = Integer.parseInt(yearmonthday[0]);
        month = Integer.parseInt(yearmonthday[1]);
        year = Integer.parseInt(yearmonthday[2]);
    }

    //Getter methods
    public String getID(){
        return id;
    }
    public String getDate(){
        return date;
    }
    public String getBodyGroup(){return bodyGroup;}
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
