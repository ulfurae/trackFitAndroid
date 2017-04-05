package com.example.ulfurae.ble1;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ulfurae.ble1.entities.Exercise;
import com.example.ulfurae.ble1.entities.User;
import com.example.ulfurae.ble1.entities.UserExercise;
import com.example.ulfurae.ble1.entities.UserGoal;
import com.example.ulfurae.ble1.handlers.HTTPHandler;
import com.example.ulfurae.ble1.mappers.JsonMapper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by heidrunh on 2.3.2017.
 */

public class ViewGoalActivity extends MenuActivity {

    private static List<UserGoal> userGoal = new ArrayList<UserGoal>();
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extras = getIntent().getExtras();
        userLoggedIn = (User) extras.getSerializable("userLoggedIn");

        setContentView(R.layout.activity_goallog);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //construct URL query to send to database
        try {
            String url = Uri.parse("http://10.0.2.2:8080/goalLog?")
                    .buildUpon()
                    .appendQueryParameter("userId",userLoggedIn.getId().toString())
                    .build().toString();

            String jsonString = HTTPHandler.requestUrl(url);

            if(!jsonString.equals("null")){
                Log.i("HTTPHandler","Received JSON: "+jsonString);
                JSONArray jsonArray = new JSONArray(jsonString);
                System.out.println(jsonArray);
                userGoal = JsonMapper.parseUserGoal(userGoal, jsonArray);

                //Add exercise entries to the view
                addToTable(userGoal);
            } else {
                //Show the user that there was a failure getting the exercises
                Toast.makeText(getApplicationContext(), "Failed getting goals", Toast.LENGTH_SHORT).show();
            }

        }
        catch(IOException ioe)   {  Log.e("HTTPHandler", "Failed to fetch items", ioe); }
        catch(JSONException je)  { Log.e("HTTPHandler","Failed to parse JSON", je); }
        catch(ParseException pe) { Log.e("HTTPHandler", "Failed to parse date", pe); }
    }


    public void addToTable(final List<UserGoal> userGoal) {
        TableLayout table = (TableLayout) findViewById(R.id.goalTable);


        for(int i = 0; i<userGoal.size();i++){
            final UserGoal uGoal = userGoal.get(i);

            TableRow row= new TableRow(this);
            if(i%2 != 0)row.setBackgroundColor(Color.LTGRAY);
            row.setMinimumHeight(120);
            row.setClickable(true);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ViewGoalEntryActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT );

            TableRow.LayoutParams anotherlp = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT );

            int leftMargin=10;
            int topMargin=10;
            int rightMargin=90;
            int bottomMargin=10;

            lp.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
            anotherlp.setMargins(70, topMargin, 5, bottomMargin);

            /*TextView goalStartDate = new TextView(this.getApplicationContext());
            goalStartDate.setLayoutParams(lp);
            goalStartDate.setText(uGoal.getStartDate());
            goalStartDate.setTextColor(0xFF000000);*/

            TextView goalEndDate = new TextView(this.getApplicationContext());
            goalEndDate.setLayoutParams(lp);
            goalEndDate.setText(uGoal.getEndDate());
            goalEndDate.setTextColor(0xFF000000);

            TextView goalExercise = new TextView(this.getApplicationContext());
            goalExercise.setLayoutParams(lp);
            changeGoalExerciseToName(goalExercise, uGoal);
            goalExercise.setTextColor(0xFF000000);

            TextView weight = new TextView(this.getApplicationContext());
            weight.setLayoutParams(anotherlp);
            weight.setText(Integer.toString(uGoal.getUnit2()));
            weight.setTextColor(Color.DKGRAY);

            TextView reps = new TextView(this.getApplicationContext());
            reps.setLayoutParams(lp);
            reps.setText(Integer.toString(uGoal.getUnit1()));
            reps.setTextColor(Color.DKGRAY);

            /*exercise.equals(uExercise.getExerciseID());
            TextView userExerciseId = new TextView(this.getApplicationContext());
            userExerciseId.setVisibility(View.INVISIBLE);
            userExerciseId.setText(Long.toString(uExercise.getId()));
            userExerciseId.setTextColor(0xFF000000);*/

            row.addView(goalEndDate);
            row.addView(goalExercise);
            row.addView(reps);
            row.addView(weight);
            //row.addView(userExerciseId);

            table.addView(row,i);
        }

        userGoal.clear();
    }

    public void changeGoalExerciseToName(TextView view, UserGoal uGoal) {
        List<Exercise> exercises = (List<Exercise>) extras.getSerializable("exercises");

        for (int j = 0; j < exercises.size(); j++) {
            if (exercises.get(j).getId() == uGoal.getExerciseID())
                view.setText(exercises.get(j).getName());
        }
    }
    // TODO NEW INTENT ÞEGAR ÝTT er á goal entry
}
