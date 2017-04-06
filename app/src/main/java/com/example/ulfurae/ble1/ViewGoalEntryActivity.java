package com.example.ulfurae.ble1;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ulfurae.ble1.entities.Exercise;
import com.example.ulfurae.ble1.entities.User;
import com.example.ulfurae.ble1.entities.UserGoal;
import com.example.ulfurae.ble1.handlers.HTTPHandler;

import java.io.IOException;
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

        TextView goalIdTxt = (TextView) findViewById(R.id.goalId);
        goalIdTxt.setText(Long.toString(uGoal.getId()));
        TextView startDateTxt = (TextView) findViewById(R.id.goalStartDate);
        startDateTxt.setText(uGoal.getStartDate());
        TextView endDateTxt = (TextView) findViewById(R.id.goalEndDate);
        endDateTxt.setText(uGoal.getEndDate());
        TextView exerciseTxt = (TextView) findViewById(R.id.exerciseInEntry);
        changeGoalExerciseToName(exerciseTxt, uGoal);
        TextView repsTxt = (TextView) findViewById(R.id.repetitions);
        repsTxt.setText(Integer.toString(uGoal.getUnit1()));
        TextView weightTxt = (TextView) findViewById(R.id.weight);
        weightTxt.setText(Integer.toString(uGoal.getUnit2()));
        TextView goalStatusTxt = (TextView) findViewById(R.id.goalStatus);
        goalStatusTxt.setText(uGoal.getStatus());
        TextView typeTxt = (TextView) findViewById(R.id.exerciseType);
        changeGoalToType(typeTxt, uGoal);

    }

    public void goToGoalLog(View view) {
        Intent intent = new Intent(this, ViewGoalActivity.class);
        intent.putExtra("userLoggedIn", (Serializable) userLoggedIn);
        intent.putExtra("exercises", (Serializable) exercises);
        startActivity(intent);
    }

    public void deleteGoal(View view) {
        TextView goalIdTxt = (TextView) findViewById(R.id.goalId);
        String goalId = goalIdTxt.getText().toString();

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //construct URL query to send to database
        try {
            String url = Uri.parse("http://10.0.2.2:8080/deleteGoal?")
                    .buildUpon()
                    .appendQueryParameter("goalId", goalId)
                    .build().toString();

            // call url with HTTP request and put the result into jsonString
            String jsonString = HTTPHandler.requestUrl(url);

            if(jsonString.equals("true")){
                Toast.makeText(getApplicationContext(), "Goal deleted", Toast.LENGTH_SHORT).show();
                goToGoalLog(view);
            } else {
                Toast.makeText(getApplicationContext(), "Failed to delete goal", Toast.LENGTH_SHORT).show();
            }

        }
        catch(IOException ioe) {  Log.e("HTTPHandler", "Failed to fetch items", ioe);  }
    }

    /*public void changeGoalStatus(View view) {
        TextView goalIdTxt = (TextView) findViewById(R.id.goalId);
        String goalId = goalIdTxt.getText().toString();

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //construct URL query to send to database
        try {
            String url = Uri.parse("http://10.0.2.2:8080/changeGoalStatus?")
                    .buildUpon()
                    .appendQueryParameter("goalId", goalId)
                    .build().toString();

            String jsonString = HTTPHandler.requestUrl(url);

            if(jsonString.equals("true")){
                Toast.makeText(getApplicationContext(), "Status updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Failed to update status", Toast.LENGTH_SHORT).show();
            }

        }
        catch(IOException ioe) {  Log.e("HTTPHandler", "Failed to fetch items", ioe);  }

    }*/

    public void changeGoalExerciseToName(TextView view, UserGoal uGoal) {

        for (int j = 0; j < exercises.size(); j++) {
            if (exercises.get(j).getId() == uGoal.getExerciseID())
                view.setText(exercises.get(j).getName());
        }
    }

    public void changeGoalToType(TextView view, UserGoal uExercise) {

        for (int j=0;j < exercises.size() ;j++) {
            if (exercises.get(j).getId() == uExercise.getExerciseID())
                view.setText(exercises.get(j).gettype());
        }
    }
}
