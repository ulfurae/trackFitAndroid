package com.example.ulfurae.ble1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ulfurae.ble1.handlers.HTTPHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import com.example.ulfurae.ble1.entities.BMI;
import com.example.ulfurae.ble1.entities.User;
import com.example.ulfurae.ble1.mappers.FormulaMapper;
import com.example.ulfurae.ble1.mappers.JsonMapper;

/**
 * Created by heidrunh on 26.2.2017.
 */

public class ProfileActivity extends MenuActivity {

    public User user = new User();

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
            String url = Uri.parse("http://10.0.2.2:8080/profile?")
                    .buildUpon()
                    .appendQueryParameter("name",userName)
                    .build().toString();

            String jsonString = HTTPHandler.requestUrl(url);
            JSONObject jsonBody = new JSONObject(jsonString);

            String name = jsonBody.getString("fullName");
            user = JsonMapper.parseUser(user, jsonBody);

            Log.i("Url", url);
            Log.i("HTTPHandler","Received JSON: " + jsonString);
            Log.i("HTTPHandler", "Received JSON Object: " + jsonBody);
        }
        catch(IOException ioe)   { Log.e("HTTPHandler", "Failed to fetch items", ioe); }
        catch(JSONException je)  {  Log.e("HTTPHandler","Failed to parse JSON", je); }
        catch(ParseException pe) { Log.e("HTTPHandler", "Failed to parse date", pe); }

        setContentView(R.layout.activity_profile);

        String isUserFound = user.getUsername();

        if(isUserFound != "null") {

            Button changeWeightButton = (Button) findViewById(R.id.changeweight_btn);
            changeWeightButton.setEnabled(true);

            BMI bmi = FormulaMapper.BMICalculate(user.getHeight(), user.getWeight());

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
        else {
            Toast.makeText(getApplicationContext(), "No user found", Toast.LENGTH_SHORT).show();
            Button changeWeightButton = (Button) findViewById(R.id.changeweight_btn);
            changeWeightButton.setEnabled(false);
        }

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
