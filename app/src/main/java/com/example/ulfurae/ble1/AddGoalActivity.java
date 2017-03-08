package com.example.ulfurae.ble1;

import android.content.Intent;
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
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Exercise;


/**
 * Created by heidrunh on 2.3.2017.
 */

public class AddGoalActivity extends MenuActivity {

    private Spinner spinner;
    private static List<String> exercisesList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgoal);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            String url = Uri.parse("http://10.0.2.2:8080/getExercises")
                    .buildUpon()
                    .build().toString();
            Log.i("Urlið", url);
            String jsonString = FetchData.getUrlString(url);
            Log.i("FetchData","Received JSON: "+jsonString);
            JSONArray jsonArray = new JSONArray(jsonString);
            exercisesList = FetchData.parseExercise(exercisesList, jsonArray);
        } catch(IOException ioe) {
            Log.e("FetchData", "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e("FetchData","Failed to parse JSON", je);
        } catch(ParseException pe) {
            Log.e("FetchData", "Failed to parse date", pe);
        }

        String[] exercises = new String[exercisesList.size()];
        exercises = exercisesList.toArray(exercises);

        spinner = (Spinner)findViewById(R.id.exerciseSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, exercises);
        spinner.setAdapter(adapter);
    }

    public void addGoal(View view) {

        EditText repetitionsTxt = (EditText) findViewById(R.id.repetitions);
        String stringRepetitions = repetitionsTxt.getText().toString();

        EditText goalWeightTxt = (EditText) findViewById(R.id.goalWeight);
        String stringgoalWeight = goalWeightTxt.getText().toString();

        EditText startDateTxt = (EditText) findViewById(R.id.startDate);
        String stringstartDate = startDateTxt.getText().toString();

        EditText endDateTxt = (EditText) findViewById(R.id.endDate);
        String stringendDate = endDateTxt.getText().toString();

        Spinner exerciseTxt = (Spinner) findViewById(R.id.exerciseSpinner);
        String exercise = exerciseTxt.getSelectedItem().toString();

        Bundle extras = getIntent().getExtras();
        String userName = extras.getString("Username");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            String url = Uri.parse("http://10.0.2.2:8080/addGoal?")
                    .buildUpon()
                    .appendQueryParameter("userName",userName)
                    .appendQueryParameter("exercise",exercise)
                    .appendQueryParameter("rep",stringRepetitions)
                    .appendQueryParameter("amount",stringgoalWeight)
                    .appendQueryParameter("startDate",stringstartDate)
                    .appendQueryParameter("endDate",stringendDate)
                    .build().toString();
            Log.i("Urlið",url);
            String jsonString = FetchData.getUrlString(url);
            if(jsonString.equals("true")){
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                repetitionsTxt.setText("");
                goalWeightTxt.setText("");
                startDateTxt.setText("");
                endDateTxt.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }

        } catch(IOException ioe) {
            Log.e("FetchData", "Failed to fetch items", ioe);
        }
    }
}
