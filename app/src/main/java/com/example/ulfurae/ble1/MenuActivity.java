package com.example.ulfurae.ble1;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import entities.User;
import entities.BMI;

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

        User testUser = new User("testUser", "ble", "Tester Testersson", new Date(), 180, 85);
        System.out.println("dewdw");
        System.out.println(testUser);

        BMI bmi = FormulaActivity.BMICalculate(testUser.getHeight(), testUser.getWeight());

        setContentView(R.layout.activity_profile);

        TextView nameTxt = (TextView) findViewById(R.id.name);
        nameTxt.setText(testUser.getFullName());
        TextView birthdayTxt = (TextView) findViewById(R.id.birthday);
        birthdayTxt.setText(Integer.toString(testUser.getBirthday().getDate()));
        TextView heightTxt = (TextView) findViewById(R.id.height);
        heightTxt.setText(Integer.toString(testUser.getHeight()));
        TextView weightTxt = (TextView) findViewById(R.id.weight);
        weightTxt.setText(Integer.toString(testUser.getWeight()));
        TextView bmiTxt = (TextView) findViewById(R.id.bmi);
        bmiTxt.setText(Double.toString(bmi.getBMIIndex()));
        TextView bmiIdealTxt = (TextView) findViewById(R.id.bmiIdeal);
        bmiIdealTxt.setText(bmi.getIdealWeight());
    }

}