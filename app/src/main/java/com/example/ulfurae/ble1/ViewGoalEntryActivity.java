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
        TextView goalIdTxt = (TextView) findViewById(R.id.goalId);
        goalIdTxt.setText(Long.toString(uGoal.getId()));

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
}
