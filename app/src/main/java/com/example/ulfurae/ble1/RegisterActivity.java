package com.example.ulfurae.ble1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.example.ulfurae.ble1.handlers.HTTPHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setDateTimeField();

    }

    //function for the Register button
    public void register(View view) {
        final EditText fullname = (EditText) findViewById(R.id.fullname);
        final EditText username = (EditText) findViewById(R.id.Rusername);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText birthday = (EditText) findViewById(R.id.birthday);
        final EditText height = (EditText) findViewById(R.id.height);
        final EditText weight = (EditText) findViewById(R.id.weight);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //construct URL query to send to database
        try {
            String url = Uri.parse("http://10.0.2.2:8080/registerUser?")
                    .buildUpon()
                    .appendQueryParameter("username", username.getText().toString())
                    .appendQueryParameter("password", password.getText().toString())
                    .appendQueryParameter("fullName", fullname.getText().toString())
                    .appendQueryParameter("birthday", birthday.getText().toString())
                    .appendQueryParameter("height", height.getText().toString())
                    .appendQueryParameter("weight", weight.getText().toString())
                    .build().toString();

            String jsonString = HTTPHandler.requestUrl(url);

            if (jsonString.equals("true")) {
                Toast.makeText(getApplicationContext(), "User saved, login now", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, LoginActivity.class);

                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), "Failed to register user", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException ioe) {
            Log.e("HTTPHandler", "Failed to fetch items", ioe);
        }
    }

    private DatePickerDialog userBD;

    private void setDateTimeField() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        final EditText userBDEditText = (EditText) findViewById(R.id.birthday);
        Calendar newCalendar = Calendar.getInstance();
        userBD = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                userBDEditText.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        userBDEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    userBD.show();
                }
                return false;
            }
        });
    }
}

