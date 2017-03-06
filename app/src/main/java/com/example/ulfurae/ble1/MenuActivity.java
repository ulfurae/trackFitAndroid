package com.example.ulfurae.ble1;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import entities.User;
import entities.BMI;


import static com.example.ulfurae.ble1.FetchData.*;
import static com.example.ulfurae.ble1.FormulaActivity.*;

/**
 * Created by ulfurae on 18.2.2017.
 */

// A class that adds a menu in all activities
// All other activities extended this class to have the menu
public class MenuActivity extends AppCompatActivity {

    String loggedInUser = "tester2";

    // / Activity code here
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getTitle().toString()) {
            case "Home":
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case "Profile":
                viewProfile(item.getActionView());
                return true;
            case "Log out":
                // log out stuff
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Called when the user clicks profile_btn or 'Profile' in menu*/
    public void viewProfile(View view)  {

        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("Username",loggedInUser);
        startActivity(intent);

    }

}