package com.example.ulfurae.ble1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ulfurae.ble1.handlers.HTTPHandler;

import java.io.IOException;

import com.example.ulfurae.ble1.entities.User;

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

        //construct URL query to send to database
        User user = new User();
        try {
            String url = Uri.parse("http://10.0.2.2:8080/changeProfile?")
                    .buildUpon()
                    .appendQueryParameter("weight",stringWeight)
                    .appendQueryParameter("userName",userName)
                    .build().toString();

            String jsonString = HTTPHandler.requestUrl(url);

            if(jsonString.equals("true")){
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.putExtra("Username",userName);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        }
        catch(IOException ioe) { Log.e("HTTPHandler", "Failed to fetch items", ioe); }

    }
}
