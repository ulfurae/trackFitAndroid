package com.example.ulfurae.ble1.mappers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

import com.example.ulfurae.ble1.entities.Exercise;
import com.example.ulfurae.ble1.entities.User;
import com.example.ulfurae.ble1.entities.UserExercise;

/**
 * Created by ulfurae on 30.3.2017.
 */

// A class to help map JSON data into java objects and lists
public class JsonMapper {


        // function to parse JSON fetched from database to java object User
        public static User parseUser(User user, JSONObject jsonBody)
                throws JSONException, ParseException {

            user.setUsername(jsonBody.getString("username"));
            user.setFullName(jsonBody.getString("fullName"));
            user.setBirthday(jsonBody.getString("birthday"));
            user.setHeight(jsonBody.getInt("height"));
            user.setWeight(jsonBody.getInt("weight"));
            user.setId(jsonBody.getLong("id"));

            return user;
        }

        // function to parse JSON fetched from database to java object UserExercise
        public static List<UserExercise> parseUserExercise(List<UserExercise> userExcList, JSONArray jsonArray)
                throws JSONException, ParseException {

            for (int i=0; i<jsonArray.length(); i++) {

                JSONObject userExc = jsonArray.getJSONObject(i);

                UserExercise newUserExc = new UserExercise();

                newUserExc.setUnit1(userExc.getInt("unit1"));
                newUserExc.setUnit2(userExc.getInt("unit2"));
                newUserExc.setExerciseID(userExc.getInt("exerciseID"));
                newUserExc.setDate(userExc.getString("date"));
                newUserExc.setId(userExc.getLong("id"));
                newUserExc.setUserID(userExc.getLong("userID"));
                newUserExc.setUserGoalID(userExc.getLong("userGoalID"));

                userExcList.add(newUserExc);

            }
            return userExcList;
        }

    // function to parse JSON fetched from database to java object UserExercise
    public static List<Exercise> parseExerciseList(List<Exercise> excList, JSONArray jsonArray)
            throws JSONException, ParseException {

        for (int i=0; i<jsonArray.length(); i++) {

            JSONObject exercise = jsonArray.getJSONObject(i);

            Exercise newExercise = new Exercise();

            newExercise.setId(exercise.getLong("id"));
            newExercise.setName(exercise.getString("name"));
            newExercise.settype(exercise.getString("type"));

            excList.add(newExercise);

        }
        return excList;
    }

        // function to parse JSON fetched from database to list (for dropdown list)
        public static List<String> parseExercise(List<String> exercises, JSONArray jsonArray)
                throws JSONException, ParseException {

            for (int i=0; i < jsonArray.length(); i++) {
                JSONObject oneObject = jsonArray.getJSONObject(i);
                String name = oneObject.getString("name");
                exercises.add(name);
            }

            return exercises;
        }


    }
