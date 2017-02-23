package com.example.ulfurae.ble1;

import android.os.Bundle;
import android.view.View;


public class MainActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    /* Called when the user clicks exerciselog_btn */
    public void viewExerciseLog(View view) {

        //    UserExercise testUserExercise = new UserExercise("testUser", "ble", "Tester Testersson", new Date(), 180, 85);
        //    System.out.println("dewdw");
        //    System.out.println(testUser);

        setContentView(R.layout.activity_exerciselog);


    }

    /* Called when the user clicks exerciselog_btn */
    public void viewGoalLog(View view) {

        //    UserExercise testUserExercise = new UserExercise("testUser", "ble", "Tester Testersson", new Date(), 180, 85);
        //    System.out.println("dewdw");
        //    System.out.println(testUser);

        setContentView(R.layout.activity_goallog);


    }

    public void viewAddExercise(View view) {

        setContentView(R.layout.activity_addexercise);
    }


    public void viewAddGoal(View view) {

        setContentView(R.layout.activity_addgoal);
    }
}

