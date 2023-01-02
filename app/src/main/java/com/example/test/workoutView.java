package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class workoutView extends AppCompatActivity {
    LinearLayout LayoutList;
    ArrayList<Workout> workouts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_view);
        LayoutList=findViewById(R.id.complete_set_list);

        workouts = localStore.readList(getApplicationContext());
        for(int k = 0; k<workouts.size(); k++) fillsets(workouts.get(k),k);
        setTitle("Previous workouts");
    }
    public void fillsets(Workout w, int i){
        final View newWorkout = getLayoutInflater().inflate(R.layout.diaplay_workout,null,false);
        TextView Name = (TextView) newWorkout.findViewById(R.id.name);
        Name.setText(w.getID());
        TextView Date = (TextView) newWorkout.findViewById(R.id.date);
        Date.setText(w.getDate());
        TextView Sets = (TextView) newWorkout.findViewById(R.id.sets);
        Sets.setText(String.valueOf(w.getSets()));
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
        workouts.remove(i);
        localStore.writeList(workouts, getApplicationContext());
    }
    public void editWorkout(View v, int k){
        Intent i = new Intent(this, fillOut.class);
        String workout = workouts.get(k).getID();
        boolean edit = true;

        i.putExtra("Workout", workout);
        i.putExtra("Index", k);
        i.putExtra("isEdit", edit);
        startActivity(i);
    }
}