package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class fillOut extends AppCompatActivity {
    ArrayList<Exercise> exercises = new ArrayList<>();
    ArrayList<Workout> workouts = new ArrayList<>();
    LinearLayout LayoutList;
    EditText dateText;
    String name, date, bodyGroup;
    Boolean isEdit;
    Exercise e;
    int indexExercise,indexWorkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_out);
        Intent i = getIntent();

        exercises = localStore.readExerciseList(getApplicationContext());
        workouts = localStore.readList(getApplicationContext());

        name = i.getStringExtra("Exercise");
        bodyGroup = i.getStringExtra("BodyPart");
        isEdit = i.getBooleanExtra("isEdit", false);
        indexExercise = i.getIntExtra("IndexExercise", 0);
        indexWorkout = i.getIntExtra("IndexWorkout", 0);
        LayoutList = findViewById(R.id.set_list);

        if(isEdit) AddFilledExercise();

        dateText = findViewById(R.id.workoutDate);
        setTitle(name);
    }
    public void AddFilledExercise(){
        e = workouts.get(indexWorkout).getExerciseList().get(indexExercise);
        bodyGroup = e.getBodyGroup();
        name = e.getID();
        System.out.println(indexExercise);
        View date = findViewById(R.id.workoutDate);
        EditText dateEdit = (EditText) date;
        dateEdit.setText(e.getDate());

        for(int k = 0; k<e.getSetReps().length; k++) addFilledSet(e, k);
    }
    public void addFilledSet(Exercise w, int k){

        final View newSet = getLayoutInflater().inflate(R.layout.set_add,null,false);
        EditText setWeight = (EditText) newSet.findViewById(R.id.set_weight);
        setWeight.setText(String.valueOf(w.getSetWeights()[k]));
        EditText setReps = (EditText) newSet.findViewById(R.id.set_reps);
        setReps.setText(String.valueOf(w.getSetReps()[k]));
        ImageView setCLose = (ImageView) newSet.findViewById(R.id.set_remove);
        setCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(newSet);
            }
        });
        LayoutList.addView(newSet);
    }

    public void addSet(View v) {
        final View newSet = getLayoutInflater().inflate(R.layout.set_add,null,false);
        ImageView setCLose = (ImageView) newSet.findViewById(R.id.set_remove);
        setCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(newSet);
            }
        });
        LayoutList.addView(newSet);
    }

    private void removeView(View v){
        LayoutList.removeView(v);
    }

    public void todayCLick(View v){
        View date = findViewById(R.id.workoutDate);
        Calendar now = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(now.getTime());
        EditText dateEdit = (EditText) date;
        dateEdit.setText(currentDate);
    }
    public void save(View v){


        int sets = LayoutList.getChildCount();
        int[] setWeights = new int[sets];
        int[] setReps = new int[sets];
        boolean works = true;



        if(dateValid(dateText.getText().toString())) date = dateText.getText().toString();
        else works = false;

        for(int i=0; i<sets; i++){
            View setView = LayoutList.getChildAt(i);

            EditText setWeight = (EditText) setView.findViewById(R.id.set_weight);
            try{ setWeights[i] = Integer.parseInt(setWeight.getText().toString());}
                catch(NumberFormatException e) {works = false;}
                catch(NullPointerException e) {works = false;}

            EditText setRep = (EditText) setView.findViewById(R.id.set_reps);
            try{setReps[i] = Integer.parseInt(setRep.getText().toString());}
                catch(NumberFormatException e) {works = false;}
                catch(NullPointerException e) {works = false;}
        }


        if(sets==0) Toast.makeText(this, "Add Sets First", Toast.LENGTH_SHORT).show();

        else if(!works){
            Toast.makeText(this, "Enter Details Correctly", Toast.LENGTH_SHORT).show();;
        }

        else{
        e = new Exercise(name, sets, setWeights, setReps, date, bodyGroup);

        int i = dataAnalyse.checkIfWorkoutExists(e, workouts);
        if(i>=0){
            if (isEdit) workouts.get(i).editExercise(e, indexExercise);
            else workouts.get(i).addExercise(e);
        }
        else{
            Workout w = new Workout(e);
            workouts.add(w);
        }


        if (isEdit) exercises.set(indexExercise, e);
        else exercises.add(e);

        ArrayList<Exercise> sortedExercises = dataAnalyse.sortWorkouts(exercises);
        localStore.writeExerciseList(sortedExercises, getApplicationContext());
        localStore.writeList(workouts,getApplicationContext());
        for(int k = 0; k<workouts.size(); k++) workouts.get(k).print();
        Toast.makeText(this, "Exercise Successfully Saved", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean dateValid(String d) {
        System.out.println(dateText.getText().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        sdf.setLenient(true);
        try {
            Date da = sdf.parse(d);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

}