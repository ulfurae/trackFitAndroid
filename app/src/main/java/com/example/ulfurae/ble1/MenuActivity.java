package com.example.ulfurae.ble1;

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

    // Activity code here

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
                setContentView(R.layout.activity_main);
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
    public void viewProfile(View view) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        User user = new User();
        try {
            String url = Uri.parse("http://10.0.2.2:8080/profile?")
                    .buildUpon()
                    .appendQueryParameter("name","tester2")
                    .build().toString();
            Log.i("Urlið", url);
            String jsonString = FetchData.getUrlString(url);
            Log.i("FetchData","Received JSON: "+jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            Log.i("FetchData", "Received JSON Object: "+jsonBody);
            String name = jsonBody.getString("fullName");
            user = FetchData.parseUser(user, jsonBody);
        } catch(IOException ioe) {
            Log.e("FetchData", "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e("FetchData","Failed to parse JSON", je);
        }

        setContentView(R.layout.activity_profile);



        /*User testUser = new User("testUser", "ble", "Tester Testersson", new Date(), 180, 85);
        System.out.println("dewdw");
        System.out.println(testUser);*/

        BMI bmi = FormulaActivity.BMICalculate(user.getHeight(), user.getWeight());

        TextView nameTxt = (TextView) findViewById(R.id.name);
        nameTxt.setText(user.getFullName());
        //TextView birthdayTxt = (TextView) findViewById(R.id.birthday);
        //birthdayTxt.setText(Integer.toString(user.getBirthday().getDate()));
        TextView heightTxt = (TextView) findViewById(R.id.height);
        heightTxt.setText(Integer.toString(user.getHeight()));
        TextView weightTxt = (TextView) findViewById(R.id.weight);
        weightTxt.setText(Integer.toString(user.getWeight()));
        TextView bmiTxt = (TextView) findViewById(R.id.bmi);
        bmiTxt.setText(Double.toString(bmi.getBMIIndex()));
        TextView bmiIdealTxt = (TextView) findViewById(R.id.bmiIdeal);
        bmiIdealTxt.setText(bmi.getIdealWeight());
    }

}