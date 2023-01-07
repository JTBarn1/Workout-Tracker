package com.example.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class localStore {
    private static final String list_key = "list key";
    private static final String list_key2 = "list key2";
    public static void writeExerciseList(ArrayList<Exercise> w, Context c){
        Gson gson = new Gson();
        String workoutString = gson.toJson(w);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(list_key, workoutString);
        editor.apply();
    }
    public static ArrayList<Exercise> readExerciseList(Context c){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        String workoutString = sp.getString(list_key, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Exercise>>() {}.getType();
        ArrayList<Exercise> exercises = gson.fromJson(workoutString, type);
        if(exercises == null) exercises = new ArrayList<>();
        return exercises;
    }
    public static void writeList(ArrayList<Workout> w, Context c){
        Gson gson = new Gson();
        String workoutString = gson.toJson(w);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(list_key2, workoutString);
        editor.apply();
    }
    public static ArrayList<Workout> readList(Context c){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        String workoutString = sp.getString(list_key2, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Workout>>() {}.getType();
        ArrayList<Workout> exercises = gson.fromJson(workoutString, type);
        if(exercises == null) exercises = new ArrayList<>();
        return exercises;
    }
}
