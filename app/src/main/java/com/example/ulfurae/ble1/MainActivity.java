package com.example.ulfurae.ble1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import com.example.ulfurae.ble1.entities.Exercise;
import com.example.ulfurae.ble1.handlers.HTTPHandler;
import com.example.ulfurae.ble1.mappers.JsonMapper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends MenuActivity {



    private static List<Exercise> exercisesList = new ArrayList<Exercise>();
    private static Exercise[] exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_register);
        setContentView(R.layout.activity_main);
        exercisesList.clear();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //get the list of exercises from database for the dropdown list
        try {
            String url = Uri.parse("http://10.0.2.2:8080/getExercises").buildUpon() .build().toString();

            String jsonString = HTTPHandler.requestUrl(url);
            JSONArray jsonArray = new JSONArray(jsonString);
            //exercisesList = JsonMapper.parseExercise(exercisesList, jsonArray);

            exercisesList = JsonMapper.parseExerciseList(exercisesList, jsonArray);


            Log.i("HTTPHandler","Received JSON: " + jsonString);
        }
        catch (IOException ioe)   { Log.e("HTTPHandler", "Failed to fetch items", ioe); }
        catch (JSONException je)  { Log.e("HTTPHandler","Failed to parse JSON", je); }
        catch (ParseException pe) { Log.e("HTTPHandler", "Failed to parse date", pe); }

    }


    /* Called when the user clicks changeweight_btn */
    public void viewChangeWeight(View view) {

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }


    /* Called when the user clicks exerciselog_btn */
    public void viewExerciseLog(View view) {

        Intent intent = new Intent(this, ViewExerciseActivity.class);
        intent.putExtra("Username",loggedInUser);
        intent.putExtra("exercises", (Serializable) exercisesList);
        startActivity(intent);

    }

    /* Called when the user clicks goallog_btn */
    public void viewGoalLog(View view) {

        Intent intent = new Intent(this, ViewGoalActivity.class);
        intent.putExtra("Username",loggedInUser);
        intent.putExtra("exercises", (Serializable) exercisesList);
        startActivity(intent);


    }

    /*Called when the user clicks addexercise_btn  */
    public void viewAddExercise(View view) {
        Intent intent = new Intent(this, AddExerciseActivity.class);
        intent.putExtra("Username",loggedInUser);
        intent.putExtra("exercises",(Serializable) exercisesList);
        startActivity(intent);
    }


    /*Called when the user clicks addgoal_btn */
    public void viewAddGoal(View view) {
        Intent intent = new Intent(this, AddGoalActivity.class);
        intent.putExtra("Username",loggedInUser);
        intent.putExtra("exercises",(Serializable) exercisesList);
        startActivity(intent);
    }

}

