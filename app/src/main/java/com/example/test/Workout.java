package com.example.test;

import android.widget.Switch;

import java.util.ArrayList;

public class Workout {


    private int setsDone, totalWeightLifted;
    private int day, month, year;
    private String Date, bodyGroup;
    private ArrayList<Exercise> exerciseList;

    public Workout(ArrayList<Exercise> List) {
        this.exerciseList = List;

        // all exercises in one workout have the same date. it doesn't matter which one in the array is used to get the date
        // i picked the first instance because it is guaranteed that at least one exercise is in the inputted array.
        Date = exerciseList.get(0).getDate();
        day = exerciseList.get(0).getDay();
        month = exerciseList.get(0).getMonth();
        year = exerciseList.get(0).getYear();

        setsDone = findTotalSets();
        bodyGroup = findMostCommonBodyGroup();
        totalWeightLifted = findTotalweightLifted();
    }
    public Workout(Exercise e){
        this.exerciseList = new ArrayList<>();
        exerciseList.add(e);

        //this constructor is used when there are no other exercises for that date,
        // so using this exercise for the date is ok instead of the ArrayList
        Date = e.getDate();
        day = e.getDay();
        month = e.getMonth();
        year = e.getYear();

        setsDone = findTotalSets();
        bodyGroup = findMostCommonBodyGroup();
        totalWeightLifted = findTotalweightLifted();
    }
    public String findMostCommonBodyGroup(){
       int[] bodyPartExerciseCount = {0,0,0,0};
       String[] bodyParts = {"chest" , "back", "leg", "arm"};

       for(int i = 0; i<exerciseList.size(); i++)
           for(int j = 0; j<bodyParts.length; j++)
               if(exerciseList.get(i).getBodyGroup().equals(bodyParts[j]))
                   bodyPartExerciseCount[j]+=1;

       int maxIndex = 0;
       for(int k = 0; k<bodyPartExerciseCount.length; k++){
           if (bodyPartExerciseCount[k] > bodyPartExerciseCount[maxIndex]) maxIndex = k;
       }
       return bodyParts[maxIndex];
    }
    public int findTotalSets(){
        int sets = 0;
        for(int i = 0; i<exerciseList.size(); i++) sets+=exerciseList.get(i).getSets();
        return sets;
    }

    public int getNumOfExercises(){
        return exerciseList.size();
    }

    public int findTotalweightLifted(){
        int weight = 0;
        for(int i = 0; i<exerciseList.size(); i++)
            for(int j = 0; j<exerciseList.get(i).getSetWeights().length; j++) weight += exerciseList.get(i).getSetWeights()[j] * exerciseList.get(i).getSetReps()[j];


        return weight;
    }
    public void addExercise(Exercise e){
        exerciseList.add(e);
        setsDone = findTotalSets();
        bodyGroup = findMostCommonBodyGroup();
        totalWeightLifted = findTotalweightLifted();
    }
    public void editExercise(Exercise e, int index){
        exerciseList.set(index,e);

        setsDone = findTotalSets();
        bodyGroup = findMostCommonBodyGroup();
        totalWeightLifted = findTotalweightLifted();
    }
    public void print(){
        System.out.println("Date: "+Date+" Exercises: "+getNumOfExercises()+" Weight Lifted: "+totalWeightLifted);
    }


    public int getSetsDone() {return setsDone;}
    public int getTotalWeightLifted() {return totalWeightLifted;}
    public int getDay() {return day;}
    public int getMonth() {return month;}
    public int getYear() {return year;}
    public String getDate() {return Date;}
    public String getBodyGroup() {return bodyGroup;}
    public ArrayList<Exercise> getExerciseList() {return exerciseList;}
}
