package com.example.ulfurae.ble1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import entities.BMI;
import entities.User;

/**
 * Created by heidrunh on 26.2.2017.
 */

public class ProfileActivity extends MenuActivity {

    public User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

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
        } catch(ParseException pe) {
            Log.e("FetchData", "Failed to parse date", pe);
        }

        setContentView(R.layout.activity_profile);



        /*User testUser = new User("testUser", "ble", "Tester Testersson", new Date(), 180, 85);
        System.out.println("dewdw");
        System.out.println(testUser);*/

        BMI bmi = FormulaActivity.BMICalculate(user.getHeight(), user.getWeight());


        TextView nameTxt = (TextView) findViewById(R.id.name);
        nameTxt.setText(user.getFullName());
        TextView birthdayTxt = (TextView) findViewById(R.id.birthday);
        birthdayTxt.setText(user.getBirthday());
        TextView heightTxt = (TextView) findViewById(R.id.height);
        heightTxt.setText(Integer.toString(user.getHeight()));
        TextView weightTxt = (TextView) findViewById(R.id.weight);
        weightTxt.setText(Integer.toString(user.getWeight()));
        TextView bmiTxt = (TextView) findViewById(R.id.bmi);
        bmiTxt.setText(Double.toString(bmi.getBMIIndex()));
        TextView bmiIdealTxt = (TextView) findViewById(R.id.bmiIdeal);
        bmiIdealTxt.setText(bmi.getIdealWeight());
    }

    /* Called when the user clicks changeweight_btn */
    public void viewChangeWeight(View view) {

        String userName = user.getUsername();
        int weight = user.getWeight();

        Intent intent = new Intent(this, ChangeProfileActivity.class);
        intent.putExtra("Username",userName);
        startActivity(intent);
    }


}
