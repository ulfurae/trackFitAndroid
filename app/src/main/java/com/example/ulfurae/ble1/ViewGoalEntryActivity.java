package com.example.ulfurae.ble1;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ulfurae.ble1.entities.UserGoal;

/**
 * Created by heidrunh on 5.4.2017.
 */

public class ViewGoalEntryActivity extends MenuActivity {

    Bundle extras;
    UserGoal uGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgoalentry);
        extras = getIntent().getExtras();

        uGoal = (UserGoal) extras.getSerializable("UserGoal");

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
}
