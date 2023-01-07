package com.example.test;

public class Exercise {
    private String id, date, bodyGroup;
    private int sets, day, month, year;
    private int[] setWeights, setReps;

    public Exercise(String id, int sets, int[] weights, int[] reps, String date, String group){
        this.id = id;
        this.sets = sets;
        this.setWeights = weights.clone();
        this.setReps = reps.clone();
        this.date = date;
        this.bodyGroup= group;

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
