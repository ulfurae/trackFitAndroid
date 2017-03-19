package com.example.ulfurae.ble1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import entities.UserExercise;

/**
 * Created by heidrunh on 2.3.2017.
 */

public class ViewExerciseActivity extends MenuActivity {

    private static List<UserExercise> userExercise = new ArrayList<UserExercise>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        String userName = extras.getString("Username");

        setContentView(R.layout.activity_exerciselog);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //construct URL query to send to database
        try {
            String url = Uri.parse("http://10.0.2.2:8080/exerciseLog?")
                    .buildUpon()
                    .appendQueryParameter("userName",userName)
                    .build().toString();
            Log.i("Urlið", url);
            String jsonString = FetchData.getUrlString(url);
            if(!jsonString.equals("null")){
                Log.i("FetchData","Received JSON: "+jsonString);
                JSONArray jsonArray = new JSONArray(jsonString);
                System.out.println(jsonArray);
                userExercise = FetchData.parseUserExercise(userExercise, jsonArray);

                //Add exercise entries to the view
                addToTable(userExercise);
            } else {
                //Show the user that there was a failure getting the exercises
                Toast.makeText(getApplicationContext(), "Failed getting exercises", Toast.LENGTH_SHORT).show();
            }

        } catch(IOException ioe) {
            Log.e("FetchData", "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e("FetchData","Failed to parse JSON", je);
        } catch(ParseException pe) {
            Log.e("FetchData", "Failed to parse date", pe);
        }
    }

    /**
     * Function takes the user's exercise entries and adds them to the view
     * @param userExercise are the exercise entries that belong to the user
     */
    public void addToTable(final List<UserExercise> userExercise){
        TableLayout table = (TableLayout) findViewById(R.id.exerciseTable);

        for(int i = 0; i<userExercise.size();i++){
            final UserExercise uExercise = userExercise.get(i);

            TableRow row= new TableRow(this);
            row.setClickable(true);
            row.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    showExerciseEntry(uExercise);
                }
            });
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT );

            int leftMargin=10;
            int topMargin=10;
            int rightMargin=250;
            int bottomMargin=10;

            lp.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

            TextView exerciseDate = new TextView(this.getApplicationContext());
            exerciseDate.setLayoutParams(lp);
            exerciseDate.setText(uExercise.getDate());

            TextView exercise = new TextView(this.getApplicationContext());
            exercise.setLayoutParams(lp);
            exercise.setText(Integer.toString(uExercise.getExerciseID()));

            TextView weight = new TextView(this.getApplicationContext());
            weight.setLayoutParams(lp);
            weight.setText(Integer.toString(uExercise.getUnit2()));

            TextView userExerciseId = new TextView(this.getApplicationContext());
            userExerciseId.setVisibility(View.INVISIBLE);
            userExerciseId.setText(Long.toString(uExercise.getId()));


            row.addView(exerciseDate);
            row.addView(exercise);
            row.addView(weight);
            row.addView(userExerciseId);

            table.addView(row,i);
        }

        userExercise.clear();
    }

    /**
     * Function makes a pop-up dialog with more detailed information about a certain exercise entry
     * @param userExercise contains all information about  a certain exercise entry
     */
    public void showExerciseEntry(UserExercise userExercise) {
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
                //Á eftir að útfæra
            }
        });


        TextView exerciseDate = (TextView) dialogView.findViewById(R.id.exerciseEntryDate);
        exerciseDate.setText(userExercise.getDate());

        TextView exercise = (TextView) dialogView.findViewById(R.id.exerciseInEntry);
        exercise.setText(Long.toString(userExercise.getExerciseID()));

        TextView weight = (TextView) dialogView.findViewById(R.id.weight);
        weight.setText(Integer.toString(userExercise.getUnit2()));

        TextView reps = (TextView) dialogView.findViewById(R.id.repetitions);
        reps.setText(Integer.toString(userExercise.getUnit1()));

        alertDialog.setView(dialogView);

        alertDialog.show();
    }
}
