package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class ExerciseView extends AppCompatActivity {
    LinearLayout LayoutList;
    int index;
    ArrayList<Exercise> exercises = new ArrayList<>();
    ArrayList<Workout> workouts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_view);
        LayoutList=findViewById(R.id.complete_set_list);

        Intent i  = getIntent();
        index = i.getIntExtra("Index", 0);

        workouts = localStore.readList(getApplicationContext());
        exercises = workouts.get(index).getExerciseList();
        for(int k = 0; k< exercises.size(); k++) fillsets(exercises.get(k),k);
        setTitle("Previous Exercises");
    }
    public void fillsets(Exercise e, int i){
        final View newWorkout = getLayoutInflater().inflate(R.layout.display_exercise,null,false);
        TextView Name = (TextView) newWorkout.findViewById(R.id.name);
        Name.setText(e.getID());
        TextView Date = (TextView) newWorkout.findViewById(R.id.date);
        Date.setText(e.getDate());
        TextView Sets = (TextView) newWorkout.findViewById(R.id.sets);
        Sets.setText(String.valueOf(e.getSets()));
        ImageView workoutCLose = (ImageView) newWorkout.findViewById(R.id.set_remove);
        ImageView workoutEdit = (ImageView) newWorkout.findViewById(R.id.set_edit);
        workoutCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(newWorkout,i);
            }

        });
        workoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editWorkout(newWorkout,i);
            }

        });
        LayoutList.addView(newWorkout);
    }
    public void removeView(View v, int i){
        LayoutList.removeView(v);
        workouts.get(index).getExerciseList().remove(i);
        localStore.writeList(workouts, getApplicationContext());
    }
    public void editWorkout(View v, int k){
        Intent i = new Intent(this, fillOut.class);
        String workout = exercises.get(k).getID();
        boolean edit = true;

        i.putExtra("Workout", workout);
        i.putExtra("IndexWorkout", index);
        i.putExtra("IndexExercise", k);
        i.putExtra("isEdit", edit);
        startActivity(i);
    }
}