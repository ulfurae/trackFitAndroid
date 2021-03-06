package com.example.ulfurae.ble1;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ulfurae.ble1.entities.Exercise;
import com.example.ulfurae.ble1.entities.User;
import com.example.ulfurae.ble1.entities.UserGoal;
import com.example.ulfurae.ble1.handlers.HTTPHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.example.ulfurae.ble1.entities.UserExercise;
import com.example.ulfurae.ble1.mappers.JsonMapper;

/**
 * Created by heidrunh on 2.3.2017.
 */

public class ViewExerciseActivity extends MenuActivity {

    private static List<UserExercise> userExercise = new ArrayList<UserExercise>();
    Bundle extras;
    List<Exercise>  exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extras = getIntent().getExtras();
        userLoggedIn = (User) extras.getSerializable("userLoggedIn");
        exercises = (List<Exercise>) extras.getSerializable("exercises");

        setContentView(R.layout.activity_exerciselog);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //construct URL query to send to database
        try {
            String url = Uri.parse("http://10.0.2.2:8080/exerciseLog?")
                    .buildUpon()
                    .appendQueryParameter("userId",userLoggedIn.getId().toString())
                    .build().toString();

            // call url with HTTP request and put the result into jsonString
            String jsonString = HTTPHandler.requestUrl(url);

            // if json string is NOT empty, then success
            if (!jsonString.isEmpty()) {
                Log.i("HTTPHandler","Received JSON: "+jsonString);
                JSONArray jsonArray = new JSONArray(jsonString);

                userExercise = JsonMapper.parseUserExercise(userExercise, jsonArray);

                //Add exercise entries to the view
                addToTable(userExercise);

            } else {
                //Show the user that there was a failure getting the exercises
                Toast.makeText(getApplicationContext(), "Failed getting exercises", Toast.LENGTH_SHORT).show();
            }

        }
        catch(IOException ioe)   {  Log.e("HTTPHandler", "Failed to fetch items", ioe); }
        catch(JSONException je)  { Log.e("HTTPHandler","Failed to parse JSON", je); }
        catch(ParseException pe) { Log.e("HTTPHandler", "Failed to parse date", pe); }
    }

    /**
     * Function takes the user's exercise entries and adds them to the view
     * @param userExercise are the exercise entries that belong to the user
     */
    public void addToTable(final List<UserExercise> userExercise) {
        TableLayout table = (TableLayout) findViewById(R.id.exerciseTable);


        for(int i = 0; i<userExercise.size();i++){
            final UserExercise uExercise = userExercise.get(i);

            TableRow row= new TableRow(this);
            if(i%2 != 0)row.setBackgroundColor(Color.LTGRAY);
            row.setMinimumHeight(120);
            row.setClickable(true);
            row.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    showExerciseEntry(uExercise);
                }
            });
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT );

            int leftMargin=10;
            int topMargin=10;
            int rightMargin=100;
            int bottomMargin=10;

            lp.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

            TextView exerciseDate = new TextView(this.getApplicationContext());
            exerciseDate.setLayoutParams(lp);
            SpannableString content = new SpannableString(uExercise.getDate());
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            exerciseDate.setText(content);
            exerciseDate.setTextColor(Color.BLUE);

            TextView exercise = new TextView(this.getApplicationContext());
            exercise.setLayoutParams(lp);
            exercise.setText(changeExerciseToName(uExercise));
            exercise.setTextColor(Color.DKGRAY);

            TextView weight = new TextView(this.getApplicationContext());
            weight.setLayoutParams(lp);
            weight.setText(Integer.toString(uExercise.getUnit2()));
            weight.setTextColor(Color.DKGRAY);

            TextView reps = new TextView(this.getApplicationContext());
            reps.setLayoutParams(lp);
            //SpannableString spannableWeight = new SpannableString(Integer.toString(uExercise.getUnit2()));
            //spannableWeight.setSpan(new UnderlineSpan(), 0, spannableWeight.length(), 0);
            reps.setText(Integer.toString(uExercise.getUnit1()));
            reps.setTextColor(Color.DKGRAY);

            exercise.equals(uExercise.getExerciseID());
            TextView userExerciseId = new TextView(this.getApplicationContext());
            userExerciseId.setVisibility(View.INVISIBLE);
            userExerciseId.setText(Long.toString(uExercise.getId()));
            userExerciseId.setTextColor(0xFF000000);

            row.addView(exerciseDate);
            row.addView(exercise);
            row.addView(reps);
            row.addView(weight);
            row.addView(userExerciseId);

            table.addView(row,i);
        }

        userExercise.clear();
    }

    /**
     * Function that makes a pop-up dialog window with more detailed information about
     * exercise entries that are clicked
     * @param userExercise contains all information about  each exercise entry
     */
    public void showExerciseEntry(final UserExercise userExercise) {

        final AlertDialog alertDialog = new AlertDialog.Builder(ViewExerciseActivity.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.from(this).inflate(R.layout.activity_viewexerciseentry, null);

        Button close = (Button) dialogView.findViewById(R.id.buttonClose);
        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        Button delete = (Button) dialogView.findViewById(R.id.buttonDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deleteEntry(userExercise);
            }
        });


        TextView exerciseDate = (TextView) dialogView.findViewById(R.id.exerciseEntryDate);
        exerciseDate.setText(userExercise.getDate());

        TextView exercise = (TextView) dialogView.findViewById(R.id.exerciseInEntry);
        exercise.setText(changeExerciseToName(userExercise));

        TextView type = (TextView) dialogView.findViewById(R.id.typeInEntry);
        changeExerciseToType(type, userExercise);

        TextView weight = (TextView) dialogView.findViewById(R.id.weight);
        weight.setText(Integer.toString(userExercise.getUnit2()));

        TextView reps = (TextView) dialogView.findViewById(R.id.repetitions);
        reps.setText(Integer.toString(userExercise.getUnit1()));

        alertDialog.setView(dialogView);

        alertDialog.show();
    }

    public String changeExerciseToName( UserExercise uExercise) {

        for (int j = 0; j < exercises.size(); j++) {
            if (exercises.get(j).getId() == uExercise.getExerciseID())
                return exercises.get(j).getName();

        }
        return null;
    }

    public void changeExerciseToType(TextView view, UserExercise uExercise) {

        for (int j=0;j < exercises.size() ;j++) {
            if (exercises.get(j).getId() == uExercise.getExerciseID())
                view.setText(exercises.get(j).gettype());
        }
    }

    public void deleteEntry(UserExercise userExercise) {
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //construct URL query to send to database
        try {
            String url = Uri.parse("http://10.0.2.2:8080/deleteExercise?")
                    .buildUpon()
                    .appendQueryParameter("exerciseId",userExercise.getId().toString())
                    .build().toString();

            String jsonString = HTTPHandler.requestUrl(url);

            if(jsonString.equals("true")){
                Toast.makeText(getApplicationContext(), "Exercise deleted", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            } else {
                Toast.makeText(getApplicationContext(), "Failed to delete exercise", Toast.LENGTH_SHORT).show();
            }

        }
        catch(IOException ioe) {  Log.e("HTTPHandler", "Failed to fetch items", ioe);  }
    }

}
