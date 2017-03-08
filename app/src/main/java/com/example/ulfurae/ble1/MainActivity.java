package com.example.ulfurae.ble1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import entities.User;


public class MainActivity extends MenuActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, User> {
        @Override
        protected User doInBackground(Void... params) {
            System.out.println("HEEEE");
            try {
                final String url = "http://10.0.2.2:8080/profile";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                User user = restTemplate.getForObject(url, User.class);
                return user;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            System.out.println("HAAAA: " + user.getUsername());
            /*TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            greetingIdText.setText(greeting.getId());
            greetingContentText.setText(greeting.getContent()); */
        }

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

