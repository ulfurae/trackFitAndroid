package com.example.ulfurae.ble1;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import entities.Exercise;


/**
 * Created by heidrunh on 2.3.2017.
 */

public class AddGoalActivity extends MenuActivity {

    private Spinner spinner;
    private static List<String> exercisesList = new ArrayList<String>();
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormat;
    private EditText fromDateEditText;
    private EditText toDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgoal);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        findViewsById();

        setDateTimeField();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //get the list of exercises from database for the dropdown list
        try {
            String url = Uri.parse("http://10.0.2.2:8080/getExercises")
                    .buildUpon()
                    .build().toString();

            String jsonString = FetchData.getUrlString(url);

            JSONArray jsonArray = new JSONArray(jsonString);
            exercisesList = FetchData.parseExercise(exercisesList, jsonArray);

            Log.i("Urlið", url);
            Log.i("FetchData","Received JSON: "+jsonString);
        } catch(IOException ioe) {
            Log.e("FetchData", "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e("FetchData","Failed to parse JSON", je);
        } catch(ParseException pe) {
            Log.e("FetchData", "Failed to parse date", pe);
        }

        String[] exercises = new String[exercisesList.size()];
        exercises = exercisesList.toArray(exercises);

        //dropdown list of exercises
        spinner = (Spinner)findViewById(R.id.exerciseSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, exercises);
        spinner.setAdapter(adapter);
    }

    private void findViewsById() {
        fromDateEditText = (EditText) findViewById(R.id.startDate);
        fromDateEditText.setInputType(InputType.TYPE_NULL);
        //fromDateEditText.requestFocus();

        toDateEditText = (EditText) findViewById(R.id.endDate);
        toDateEditText.setInputType(InputType.TYPE_NULL);

    }

    //fancy DatePicker
    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEditText.setText(dateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEditText.setText(dateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDateEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                if(e.getAction() == MotionEvent.ACTION_DOWN) {
                    fromDatePickerDialog.show();
                }
                return false;
            }
        });

        toDateEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                if(e.getAction() == MotionEvent.ACTION_DOWN) {
                    toDatePickerDialog.show();
                }
                return false;
            }
        });
    }

    //function for the Save Goal button
    public void addGoal(View view) {

        //get input arguments
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

        //get logged in user
        Bundle extras = getIntent().getExtras();
        String userName = extras.getString("Username");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //Construct URL query to send to database
        try {
            String url = Uri.parse("http://10.0.2.2:8080/addGoal?")
                    .buildUpon()
                    .appendQueryParameter("userName",userName)
                    .appendQueryParameter("exercise",exercise)
                    .appendQueryParameter("rep",stringRepetitions)
                    .appendQueryParameter("amount",stringgoalWeight)
                    .appendQueryParameter("startDate",stringstartDate)
                    .appendQueryParameter("endDate",stringendDate)
                    .appendQueryParameter("status", "Not completed")
                    .build().toString();
            Log.i("Urlið",url);
            String jsonString = FetchData.getUrlString(url);
            if(jsonString.equals("true")){
                Toast.makeText(getApplicationContext(), "Goal saved", Toast.LENGTH_SHORT).show();
                repetitionsTxt.setText("");
                goalWeightTxt.setText("");
                startDateTxt.setText("");
                endDateTxt.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Failed to save goal", Toast.LENGTH_SHORT).show();
            }

        } catch(IOException ioe) {
            Log.e("FetchData", "Failed to fetch items", ioe);
        }
    }

    //function for Goal Log button to go to Goal Log from Add Goal view
    public void goToViewGoals(View view) {

        //logged in mock user
        Intent intent = new Intent(this, ViewGoalActivity.class);
        intent.putExtra("Username","tester2");
        startActivity(intent);
    }
}
