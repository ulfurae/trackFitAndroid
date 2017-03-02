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
import java.util.Date;

import entities.User;

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

    public static User parseUser(User user, JSONObject jsonBody) throws JSONException {
        Log.i("Bla","Við erum hér");

        String name = jsonBody.getString("fullName");
        //String birthdayString = jsonBody.getString("birthday");
        int height = jsonBody.getInt("height");
        int weight = jsonBody.getInt("weight");

        //Date birthday = new Date(birthdayString);

        user.setFullName(name);
        //user.setBirthday(birthday);
        user.setHeight(height);
        user.setWeight(weight);

        return user;
    }

}
