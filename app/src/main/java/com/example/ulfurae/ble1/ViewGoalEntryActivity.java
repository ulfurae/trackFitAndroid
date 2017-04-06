package com.example.ulfurae.ble1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ulfurae.ble1.entities.Exercise;
import com.example.ulfurae.ble1.entities.User;
import com.example.ulfurae.ble1.entities.UserGoal;

import java.io.Serializable;
import java.util.List;

/**
 * Created by heidrunh on 5.4.2017.
 */

public class ViewGoalEntryActivity extends MenuActivity {

    Bundle extras;
    UserGoal uGoal;
    List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgoalentry);
        extras = getIntent().getExtras();
        userLoggedIn = (User) extras.getSerializable("userLoggedIn");
        uGoal = (UserGoal) extras.getSerializable("UserGoal");
        exercises = (List<Exercise>) extras.getSerializable("exercises");

        TextView startDateTxt = (TextView) findViewById(R.id.goalStartDate);
        startDateTxt.setText(uGoal.getStartDate());
        TextView endDateTxt = (TextView) findViewById(R.id.goalEndDate);
        endDateTxt.setText(uGoal.getEndDate());
        TextView exerciseTxt = (TextView) findViewById(R.id.exerciseInEntry);
        exerciseTxt.setText(Integer.toString(uGoal.getExerciseID()));
        TextView repsTxt = (TextView) findViewById(R.id.repetitions);
        repsTxt.setText(Integer.toString(uGoal.getUnit1()));
        TextView weightTxt = (TextView) findViewById(R.id.weight);
        weightTxt.setText(Integer.toString(uGoal.getUnit2()));
        TextView goalStatusTxt = (TextView) findViewById(R.id.goalStatus);
        goalStatusTxt.setText(uGoal.getStatus());

    }

    public void goToGoalLog(View view) {
        Intent intent = new Intent(this, ViewGoalActivity.class);
        intent.putExtra("userLoggedIn", (Serializable) userLoggedIn);
        intent.putExtra("exercises", (Serializable) exercises);
        startActivity(intent);
    }
}
