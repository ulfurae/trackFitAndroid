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
        }finally { conn.disconnect(); }
    }

    public static String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public static User parseUser(User user, JSONObject jsonBody) throws JSONException, ParseException {
        Log.i("Parse","User");

        String userName = jsonBody.getString("username");
        String name = jsonBody.getString("fullName");
        String birthday = jsonBody.getString("birthday");
        int height = jsonBody.getInt("height");
        int weight = jsonBody.getInt("weight");

        user.setUsername(userName);
        user.setFullName(name);
        user.setBirthday(birthday);
        user.setHeight(height);
        user.setWeight(weight);

        return user;
    }

    public static List<UserExercise> parseUserExercise(String jsonString) throws JSONException, ParseException {
        Log.i("Parse","UserExercise");

        List<UserExercise> uExercise = new ArrayList<>();
        JSONArray jsonResults = new JSONArray(jsonString);

        for (int i=0; i<jsonResults.length(); i++) {
            JSONObject jsonResult = jsonResults.getJSONObject(i);
            uExercise.add(new UserExercise(
                    jsonResult.getLong("userGoalID"),
                    jsonResult.getLong("userID"),
                    jsonResult.getLong("exerciseID"),
                    jsonResult.getInt("unit1"),
                    jsonResult.getInt("unit2"),
                    jsonResult.getDate("date")
            ));
        }
    }

    public static List<String> parseExercise(List<String> exercises, JSONArray jsonArray) throws JSONException, ParseException {
        Log.i("Parse","Exercise");
        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject oneObject = jsonArray.getJSONObject(i);
            String name = oneObject.getString("name");
            exercises.add(name);
        }

        return exercises;
    }

}
