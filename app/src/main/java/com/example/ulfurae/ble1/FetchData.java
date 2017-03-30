package com.example.ulfurae.ble1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import entities.Exercise;
import entities.User;
import entities.UserExercise;

/**
 * Created by heidrunh on 2.3.2017.
 */

public class FetchData {

    private static final String TAG = "FetchData";

    public static byte[] getUrlBytes(String urlSpec) throws IOException {

        URL url = new URL(urlSpec);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = conn.getInputStream();

            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(conn.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];

            while((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();

            return out.toByteArray();
        }
        finally { conn.disconnect(); }
    }


    public static String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }



    // function to parse JSON fetched from database to java object User
    public static User parseUser(User user, JSONObject jsonBody) throws JSONException, ParseException {

        user.setUsername(jsonBody.getString("username"));
        user.setFullName(jsonBody.getString("fullName"));
        user.setBirthday(jsonBody.getString("birthday"));
        user.setHeight(jsonBody.getInt("height"));
        user.setWeight(jsonBody.getInt("weight"));

        return user;
    }

    // function to parse JSON fetched from database to java object UserExercise
    public static List<UserExercise> parseUserExercise(List<UserExercise> userExcList, JSONArray jsonArray) throws JSONException, ParseException {

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

    // function to parse JSON fetched from database to list (for dropdown list)
    public static List<String> parseExercise(List<String> exercises, JSONArray jsonArray) throws JSONException, ParseException {

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject oneObject = jsonArray.getJSONObject(i);
            String name = oneObject.getString("name");
            exercises.add(name);
        }

        return exercises;
    }


}
