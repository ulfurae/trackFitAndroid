package com.example.ulfurae.ble1;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ulfurae.ble1.entities.User;
import com.example.ulfurae.ble1.handlers.HTTPHandler;
import com.example.ulfurae.ble1.mappers.JsonMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

/*        final EditText username = (EditText) findViewById(R.id.Lusername);
        final EditText password = (EditText) findViewById(R.id.password);
        final Button btLogin = (Button) findViewById(R.id.btLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });*/

    }

    //function for the Register button
    public void login(View view) {

        final EditText username = (EditText) findViewById(R.id.Lusername);
        final EditText password = (EditText) findViewById(R.id.password);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //construct URL query to send to database
        try {
            String url = Uri.parse("http://10.0.2.2:8080/login?")
                    .buildUpon()
                    .appendQueryParameter("username", username.getText().toString())
                    .appendQueryParameter("password", password.getText().toString())
                    .build().toString();

            // call url with HTTP request and put the result into jsonString
            String jsonString = HTTPHandler.requestUrl(url);

            // if json string is NOT empty, then success
            if (!jsonString.isEmpty()) {
                Log.i("JSON STRING -", jsonString);
                Toast.makeText(getApplicationContext(), "User logged in", Toast.LENGTH_SHORT).show();
                JSONObject jsonBody = new JSONObject(jsonString);
                User loggedInUser = new User();
                loggedInUser = JsonMapper.parseUser(loggedInUser, jsonBody);

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("userLoggedIn",(Serializable) loggedInUser);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), "Failed to login user", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException ioe) {
            Log.e("HTTPHandler", "Failed to fetch items", ioe);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*Called when the user clicks addgoal_btn */
    public void viewRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
