package com.example.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * The dataAnalyse class contains static utility methods for analyzing and modifying data.
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

    //GroupExercises method. Create a hashmap that contains the most recent three exercises for each exercise.
    public static HashMap<String, ArrayList<Exercise>> GroupExercises(ArrayList<Workout> workouts){

        //Declaring the hashmap
        HashMap<String, ArrayList<Exercise>> hashExercises = new HashMap<>();

        //Looping through the ArrayList of workouts
        for(int i = workouts.size()-1; i>0; i--){
            Workout w = workouts.get(i);

            //Looping through each workout's Exercises
            for( int j = 0; j < w.getExerciseList().size(); j++){

                //Setting the hashmap key
                String id = w.getExerciseList().get(j).getID();

                //Getting the Arraylist from the hashmap using the key
                ArrayList<Exercise> hashedList = hashExercises.get(id);

                //If no exercises of this type have been put in the hashmap yet, makes a new ArrayList and enters it
                if(hashedList == null){

                    //Making the list
                    ArrayList<Exercise> e = new ArrayList<>();
                    e.add(w.getExerciseList().get(j));

                    //Putting it into the hashmap
                    hashExercises.put(id,e);
                }
                //Otherwise, adds exercise to the list and puts it in the hashmap
                else {

                    //only lets the first tree exerises in hashmap
                    if(hashedList.size() <=3) {
                        hashedList.add(w.getExerciseList().get(j));

                        //Putting it into the hashmap
                        hashExercises.put(id, hashedList);
                    }
                }
            }
        }

        //Returning the hashmap
        return hashExercises;
    }

    //findMax method. Finds Highest theoretiacl max from all completed sets, and returns it.
    public static int findMax(ArrayList<Exercise> exercises) {
        //setting the one rep max at 0
        double max = 0;

        //loops through all exercises
        for(int i = 0; i< exercises.size(); i++){
            //If the Exercise is a common Exercise, gives a pre formulated one rep max.
            if(exercises.get(i).getCommonExercise() >=0)
                return (int) commonWorkoutsFormula(exercises.get(i).getSetWeights()[0], exercises.get(i).getCommonExercise());

            //finds theoretical max from every set completed on days workout, and finds the highest number
            for(int j = 0; j< exercises.get(i).getSetReps().length; j++){

                //Creating the max value
                //Double is used because we need decimal points
                double setMax = 0;

                // Calls the Bryzki formula with the Jth weight and rep from the Ith Exercise
                setMax = BrzyckiFormula(((double)exercises.get(i).getSetWeights()[j]),((double)exercises.get(i).getSetReps()[j]));
                if(setMax > max) max = setMax;
            }
        }

        //Returning the max and casting to integer
        return (int) max;
    }

    //BrzyckiFormula method. Takes the a weight and rep value and Returns a one rep max based off the Brzycki formula
    public static double BrzyckiFormula(double weight, double reps){
        //Returning the calculated max
        return (weight * (36/(37-reps)));
    }

    //commonWorkoutsFormula method. takes the common weight of the exercise and the common exercise,
    //And calculates the max based off that
    //Index of common workouts:
    //5x5: 0 4x8: 1
    public static double commonWorkoutsFormula(double weight, int Exercise){
        //Percentage value of one rep max based off the common workout
        double[] workoutFormulas = {0.82, 0.8};

        //Returns the weight divided by the percentage to get the one rep max
        return weight / workoutFormulas[Exercise];
    }

    //checkCommonworkouts method. Calls each common workout checker method, returns their respected value
    //Ex: 5x5 is 0, 4x8 is 1
    //If it doesn't have one, returns -1
    public static int checkCommonExercises(Exercise e){
        //Calls checker methods
        int fivebyfive = is5x5(e); int fourbyeight = is4x8(e);

        //Returns values, if there are any
        if(fivebyfive>=0) return fivebyfive;
        if(fourbyeight>=0) return fourbyeight;

        //Otherwise -1
        return -1;
    }

    //is5x5 method. if 5 sets have been entered, and each is the same weight and 5 reps,
    //A more accurate one rep max calculation can be done.
    public static int is5x5(Exercise e){
        //Checks to see if there are 5 sets
        if(e.getSets() == 5) {
            //Sets the value of the weight that should be consistent
            int startingWeight = e.getSetWeights()[0];

            //Loops through the sets, if they arent all the same weight anf 5 reps, return -1.
            for(int k = 0; k<e.getSetReps().length; k++)
                if(e.getSetReps()[k] != 5 || e.getSetWeights()[k] != startingWeight) return -1;

            //If it didn't fail, returns 0.
            return 0;
        }
        //Returns -1 if there weren't 5 sets.
        return -1;
    }

    //is 4x8 method. if 4 sets have been entered, and each is the same weight and 8 reps,
    //A more accurate one rep max calculation can be done.
    public static int is4x8(Exercise e){
        //Checks to see if there are 5 sets
        if(e.getSets() == 4) {
            //Sets the value of the weight that should be consistent
            int startingWeight = e.getSetWeights()[0];

            //Loops through the sets, if they arent all the same weight anf 5 reps, return -1.
            for(int k = 0; k<e.getSetReps().length; k++)
                if(e.getSetReps()[k] != 8 || e.getSetWeights()[k] != startingWeight) return -1;

            //If it didn't fail, returns 1.
            return 1;
        }
        //Returns -1 if there weren't 5 sets.
        return -1;
    }
}
