package com.example.ulfurae.ble1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import entities.User;

/**
 * Created by heidrunh on 4.3.2017.
 */

public class ChangeProfileActivity extends MenuActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeprofile);
    }

    /* Called when the user clicks confirmchangeprofile_btn */
    public void changeProfile(View view) {

        Bundle extras = getIntent().getExtras();
        String userName = extras.getString("Username");

        EditText weightTxt = (EditText) findViewById(R.id.editProfileResult);
        String stringWeight = weightTxt.getText().toString();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        User user = new User();
        try {
            String url = Uri.parse("http://10.0.2.2:8080/changeProfile?")
                    .buildUpon()
                    .appendQueryParameter("weight",stringWeight)
                    .appendQueryParameter("userName",userName)
                    .build().toString();
            Log.i("Urlið", url);
            String jsonString = FetchData.getUrlString(url);
            Log.i("FetchData","Received JSON: "+jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            Log.i("FetchData", "Received JSON Object: "+jsonBody);
            user = FetchData.parseUser(user, jsonBody);
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } catch(IOException ioe) {
            Log.e("FetchData", "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e("FetchData","Failed to parse JSON", je);
        } catch(ParseException pe) {
            Log.e("FetchData", "Failed to parse date", pe);
        }

    }
}
