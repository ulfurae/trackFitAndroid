package com.example.ulfurae.ble1;

import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import entities.UserExercise;

/**
 * Created by heidrunh on 2.3.2017.
 */

public class ViewExerciseActivity extends MenuActivity {

    UserExercise uExercise = new UserExercise();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        String userName = extras.getString("Username");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            String url = Uri.parse("http://10.0.2.2:8080/exerciseLog?")
                    .buildUpon()
                    .appendQueryParameter("name",userName)
                    .build().toString();
            Log.i("Urlið", url);
            String jsonString = FetchData.getUrlString(url);
            Log.i("FetchData","Received JSON: "+jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            Log.i("FetchData", "Received JSON Object: "+jsonBody);
            String name = jsonBody.getString("fullName");
    //        uExercise = FetchData.parseUserExercise();
        } catch(IOException ioe) {
            Log.e("FetchData", "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e("FetchData","Failed to parse JSON", je);
        } catch(ParseException pe) {
            Log.e("FetchData", "Failed to parse date", pe);
        }

        setContentView(R.layout.activity_exerciselog);
    }
}
