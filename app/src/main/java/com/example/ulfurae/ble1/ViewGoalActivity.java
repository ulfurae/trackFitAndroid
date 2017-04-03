package com.example.ulfurae.ble1;

import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.example.ulfurae.ble1.entities.UserExercise;
import com.example.ulfurae.ble1.handlers.HTTPHandler;
import com.example.ulfurae.ble1.mappers.JsonMapper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heidrunh on 2.3.2017.
 */

public class ViewGoalActivity extends MenuActivity {

    private static List<UserExercise> userExercise = new ArrayList<UserExercise>();
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extras = getIntent().getExtras();
        String userName = extras.getString("Username");

        setContentView(R.layout.activity_goallog);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
/*
        //construct URL query to send to database
        try {
            String url = Uri.parse("http://10.0.2.2:8080/goalLog?")
                    .buildUpon()
                    .appendQueryParameter("userName",userName)
                    .build().toString();

            String jsonString = HTTPHandler.requestUrl(url);

            if(!jsonString.equals("null")){
                Log.i("HTTPHandler","Received JSON: "+jsonString);
                JSONArray jsonArray = new JSONArray(jsonString);
                System.out.println(jsonArray);
                userExercise = JsonMapper.parseUserExercise(userExercise, jsonArray);

                //Add exercise entries to the view
                addToTable(userExercise);
            } else {
                //Show the user that there was a failure getting the exercises
                Toast.makeText(getApplicationContext(), "Failed getting exercises", Toast.LENGTH_SHORT).show();
            }

        }
        catch(IOException ioe)   {  Log.e("HTTPHandler", "Failed to fetch items", ioe); }
        catch(JSONException je)  { Log.e("HTTPHandler","Failed to parse JSON", je); }
        catch(ParseException pe) { Log.e("HTTPHandler", "Failed to parse date", pe); }
*/
    }

    //Á eftir að útfæra
}
