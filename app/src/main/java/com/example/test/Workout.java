package com.example.test;


import java.util.ArrayList;

/**
 * The Workout class represents a single workout session that a user has completed.
 * It stores information such as the date of the workout, the body part that was trained,
 * the total number of sets completed, and the total weight lifted. The Workout class also
 * Stores an ArrayList of exercises.
 */

public class Workout {

    //Declcaring the fields for the class
    private int setsDone, totalWeightLifted;
    private int day, month, year;
    private String Date, bodyGroup;
    private ArrayList<Exercise> exerciseList;

    //Constructor class. Takes an ArrayList of Exercises as its input
    public Workout(ArrayList<Exercise> List) {
        this.exerciseList = List;

        //All exercises in one workout have the same date. it doesn't matter which one in the array is used to get the date
        //I picked the first instance because it is guaranteed that at least one exercise is in the inputted array.
        //Sets date
        Date = exerciseList.get(0).getDate();
        day = exerciseList.get(0).getDay();
        month = exerciseList.get(0).getMonth();
        year = exerciseList.get(0).getYear();

        //Calls methods to get data.
        setsDone = findTotalSets();
        bodyGroup = findMostCommonBodyGroup();
        totalWeightLifted = findTotalweightLifted();
    }
    // Constructor class. Takes an Exercise as its input.
    public Workout(Exercise e){
        this.exerciseList = new ArrayList<>();
        exerciseList.add(e);

        //this constructor is used when there are no other exercises for that date,
        //so using this exercise for the date is ok instead of the ArrayList,like in the previous constructor
        Date = e.getDate();
        day = e.getDay();
        month = e.getMonth();
        year = e.getYear();

        //Calls methods to get data.
        setsDone = findTotalSets();
        bodyGroup = findMostCommonBodyGroup();
        totalWeightLifted = findTotalweightLifted();
    }

    //findMostCommonBodyGroup method. finds most common body Part and returns the corresponding String.
    // im not sure if  like the way i do this, i want a future version to have the option to display multiple body groups a the same time.
    //Example: a chest/ back day or a Back/ Bicep day would just be displayed as a chest day or a back day.
    public String findMostCommonBodyGroup(){

       //Array of integers that is used to count the most common body part
       int[] bodyPartExerciseCount = {0,0,0,0};

       //hardcoded Array or Strings with the respective body groups
       // these Strings will be used to display the images in drawable in the workoutView activity.
       String[] bodyParts = {"chest" , "back", "leg", "arm"};

       //For loop that goes through the list of exercises
       //Checks every body part in the array for a match
       //If there is one, the counter for the integer array goes up one
       for(int i = 0; i<exerciseList.size(); i++)
           for(int j = 0; j<bodyParts.length; j++)
               if(exerciseList.get(i).getBodyGroup().equals(bodyParts[j]))
                   bodyPartExerciseCount[j]+=1;


       int maxIndex = 0;
       //A second for loop that finds the largest index of the bodyPartExerciseCountArray
       for(int k = 0; k<bodyPartExerciseCount.length; k++){
           if (bodyPartExerciseCount[k] > bodyPartExerciseCount[maxIndex]) maxIndex = k;
       }

       //Returns the most common body part
       return bodyParts[maxIndex];
    }

    //findTotalSets method. adds up all completed sets from each exercise and returns the value.
    public int findTotalSets(){
        int sets = 0;
        for(int i = 0; i<exerciseList.size(); i++) sets+=exerciseList.get(i).getSets();
        return sets;
    }

    //findTotalweightLifted method. Adds up every exercise's multiplied sets value
    //ex. the multiplied value for  200 lbs for 5 reps = 1000
    public int findTotalweightLifted(){
        int weight = 0;

        //goes through every exercise
        for(int i = 0; i<exerciseList.size(); i++)

            //gets the multiplied set value for each set
            for(int j = 0; j<exerciseList.get(i).getSetWeights().length; j++) weight += exerciseList.get(i).getSetWeights()[j] * exerciseList.get(i).getSetReps()[j];

        //returns total weight
        return weight;
    }

    //addExercise method. Adds new Evercise e to the Arraylist of exercises.
    public void addExercise(Exercise e){
        exerciseList.add(e);

        //Calls methods to get data.
        setsDone = findTotalSets();
        bodyGroup = findMostCommonBodyGroup();
        totalWeightLifted = findTotalweightLifted();
    }

    //editExercise method. exits existing Evercise e at it's index in the Arraylist of exercises.
    public void editExercise(Exercise e, int index){
        exerciseList.set(index,e);

        //Calls methods to get data.
        setsDone = findTotalSets();
        bodyGroup = findMostCommonBodyGroup();
        totalWeightLifted = findTotalweightLifted();
    }

    //removeExercise method. Removes ith exercise in exerciseList
    public void removeExercise(int i){
        exerciseList.remove(i);

        //Calls methods to get data.
        setsDone = findTotalSets();
        bodyGroup = findMostCommonBodyGroup();
        totalWeightLifted = findTotalweightLifted();
    }

    //Getters
    public int getNumOfExercises(){
        return exerciseList.size();
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
