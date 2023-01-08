package com.example.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This class is used to store and retrieve workout data from SharedPreferences.
 * SharedPreferences allows data to be stored persistently on the device,
 * even if the app is closed or the device is turned off.
 */

public class localStore {
    //Key that allows us to access Stored List
    private static final String workout_list_key = "list key2";

    //writeList method. Converts an ArrayList of Workouts to a String and Stores it in SharedPreferences.
    public static void writeList(ArrayList<Workout> w, Context c){

        //I used gson to convert the ArrayList to a String.
        //It does this through toJson()
        Gson gson = new Gson();
        String workoutString = gson.toJson(w);

        //instantiating Shared Preferences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);

        //Instantiating the editor
        SharedPreferences.Editor editor = sp.edit();

        //Putting the String into SHaredPreferences using the key
        editor.putString(workout_list_key, workoutString);
        editor.apply();
    }
    public static ArrayList<Workout> readList(Context c){
        //instantiating Shared Preferences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);

        //String that contains the ArrayList
        String workoutString = sp.getString(workout_list_key, "");

        //I used gson to convert the String to an ArrayList.
        //It does this through fromJson()
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Workout>>() {}.getType();
        ArrayList<Workout> exercises = gson.fromJson(workoutString, type);

        //For first time app users, they won't have anything in SharedPreferences, so they are returned an empty list
        if(exercises == null) exercises = new ArrayList<>();

        //Returns ArrayList
        return exercises;
    }
}
