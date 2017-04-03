package com.example.ulfurae.ble1;

import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.example.ulfurae.ble1.handlers.HTTPHandler;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    //function for the Register button
    public void register(View view) {
        final EditText fullname = (EditText) findViewById(R.id.fullname);
        final EditText username = (EditText) findViewById(R.id.Rusername);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText birthday = (EditText) findViewById(R.id.birthday);
        final EditText height = (EditText) findViewById(R.id.height);
        final EditText weight = (EditText) findViewById(R.id.weight);
        final int bmi = 23; //placeholder

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //construct URL query to send to database
        try {
            String url = Uri.parse("http://10.0.2.2:8080/registerUser?")
                    .buildUpon()
                    .appendQueryParameter("userName", username.getText().toString())
                    .appendQueryParameter("pass", password.getText().toString())
                    .appendQueryParameter("fullName", fullname.getText().toString())
                    .appendQueryParameter("birthday", birthday.getText().toString())
                    .appendQueryParameter("height", height.getText().toString())
                    .appendQueryParameter("weight", weight.getText().toString())
                    .appendQueryParameter("bmi", String.valueOf(bmi))
                    .build().toString();

            String jsonString = HTTPHandler.requestUrl(url);

            if (jsonString.equals("true")) {
                Toast.makeText(getApplicationContext(), "User saved", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), "Failed to register user", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException ioe) {
            Log.e("HTTPHandler", "Failed to fetch items", ioe);
        }
    }
}

