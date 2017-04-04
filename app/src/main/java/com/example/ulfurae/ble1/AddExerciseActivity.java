package com.example.ulfurae.ble1;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ulfurae.ble1.entities.Exercise;
import com.example.ulfurae.ble1.entities.User;
import com.example.ulfurae.ble1.handlers.HTTPHandler;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by heidrunh on 4.3.2017.
 */

public class AddExerciseActivity extends MenuActivity{

    private Spinner spinner;
    private Bundle extras;
    List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexercise);
        extras = getIntent().getExtras();

        exercises = (List<Exercise>) extras.getSerializable("exercises");
        userLoggedIn = (User) extras.getSerializable("userLoggedIn");

        //dropdown list of exercises
        spinner = (Spinner) findViewById(R.id.exerciseSpinner);
        ArrayAdapter<Exercise> adapter = new ArrayAdapter<Exercise>(this, android.R.layout.simple_spinner_dropdown_item, exercises);
        spinner.setAdapter(adapter);

    }

    //function for the Save Exercise button
    public void addExercise(View view) {

        //get input arguments
        EditText repetitionsTxt = (EditText) findViewById(R.id.repetitions);
        String stringRepetitions = repetitionsTxt.getText().toString();

        EditText weightLiftedTxt = (EditText) findViewById(R.id.weightLifted);
        String stringWeightLifted = weightLiftedTxt.getText().toString();

        Spinner exerciseTxt = (Spinner) findViewById(R.id.exerciseSpinner);
        String exercise = exerciseTxt.getSelectedItem().toString();

        //get logged in user

        System.out.println(userLoggedIn.getId().toString());


        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //construct URL query to send to database
        try {
            String url = Uri.parse("http://10.0.2.2:8080/addExercise?")
                    .buildUpon()
                    .appendQueryParameter("userId",userLoggedIn.getId().toString())
                    .appendQueryParameter("goalID","0")
                    .appendQueryParameter("exercise",exercise)
                    .appendQueryParameter("rep",stringRepetitions)
                    .appendQueryParameter("amount",stringWeightLifted)
                    .build().toString();

            String jsonString = HTTPHandler.requestUrl(url);

            if(jsonString.equals("true")){
                Toast.makeText(getApplicationContext(), "Exercise saved", Toast.LENGTH_SHORT).show();
                repetitionsTxt.setText("");
                weightLiftedTxt.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Failed to save exercise", Toast.LENGTH_SHORT).show();
            }

        }
        catch(IOException ioe) {  Log.e("HTTPHandler", "Failed to fetch items", ioe);  }
    }

    //function for Exercise Log button to go to Exercise Log from Add Exercise view
    public void goToExerciseLog(View view) {

        //logged in mock user
        Intent intent = new Intent(this, ViewExerciseActivity.class);
        intent.putExtra("userLoggedIn", (Serializable) userLoggedIn);
        intent.putExtra("exercises", (Serializable) exercises);
        startActivity(intent);
    }
}
