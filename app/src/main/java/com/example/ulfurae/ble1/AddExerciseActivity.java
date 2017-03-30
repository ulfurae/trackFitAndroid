package com.example.ulfurae.ble1;

import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heidrunh on 2.3.2017.
 */

public class AddExerciseActivity extends MenuActivity{

    private Spinner spinner;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexercise);
        extras = getIntent().getExtras();

        //dropdown list of exercises
        spinner = (Spinner)findViewById(R.id.exerciseSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, extras.getStringArray("exercises"));
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

        String userName = extras.getString("Username");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //construct URL query to send to database
        try {
            String url = Uri.parse("http://10.0.2.2:8080/addExercise?")
                    .buildUpon()
                    .appendQueryParameter("userName",userName)
                    .appendQueryParameter("goalID","0")
                    .appendQueryParameter("exercise",exercise)
                    .appendQueryParameter("rep",stringRepetitions)
                    .appendQueryParameter("amount",stringWeightLifted)
                    .build().toString();
            Log.i("Urlið", url);
            String jsonString = FetchData.getUrlString(url);
            if(jsonString.equals("true")){
                Toast.makeText(getApplicationContext(), "Exercise saved", Toast.LENGTH_SHORT).show();
                repetitionsTxt.setText("");
                weightLiftedTxt.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Failed to save exercise", Toast.LENGTH_SHORT).show();
            }

        } catch(IOException ioe) {
            Log.e("FetchData", "Failed to fetch items", ioe);
        }
    }

    //function for Exercise Log button to go to Exercise Log from Add Exercise view
    public void goToExerciseLog(View view) {

        //logged in mock user
        Intent intent = new Intent(this, ViewExerciseActivity.class);
        intent.putExtra("Username","tester2");
        startActivity(intent);
    }
}
