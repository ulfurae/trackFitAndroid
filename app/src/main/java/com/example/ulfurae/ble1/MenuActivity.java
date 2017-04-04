package com.example.ulfurae.ble1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.ulfurae.ble1.entities.User;

import java.io.Serializable;

/**
 * Created by ulfurae on 18.2.2017.
 */

// A class that adds a menu (in top right corner) in all activities
// All other activities extended this class to have the menu
public class MenuActivity extends AppCompatActivity {

    String loggedInUser = "tester2";

    User userLoggedIn;

    //
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
                intent.putExtra("userLoggedIn",(Serializable) userLoggedIn);
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
        intent.putExtra("userLoggedIn",(Serializable) userLoggedIn);
        startActivity(intent);

    }



}