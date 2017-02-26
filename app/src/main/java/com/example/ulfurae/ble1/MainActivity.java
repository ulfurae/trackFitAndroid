package com.example.ulfurae.ble1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends MenuActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /* Called when the user clicks changeweight_btn */
    public void viewChangeWeight(View view) {

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }


    /* Called when the user clicks exerciselog_btn */
    public void viewExerciseLog(View view) {

        setContentView(R.layout.activity_exerciselog);


    }

    /* Called when the user clicks goallog_btn */
    public void viewGoalLog(View view) {

        setContentView(R.layout.activity_goallog);


    }

    /*Called when the user clicks addexercise_btn  */
    public void viewAddExercise(View view) {

        setContentView(R.layout.activity_addexercise);
    }


    /*Called when the user clicks addgoal_btn */
    public void viewAddGoal(View view) {

        setContentView(R.layout.activity_addgoal);
    }

}

