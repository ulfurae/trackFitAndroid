package com.example.ulfurae.ble1.handlers;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by heidrunh on 2.3.2017.
 */

// A class that handles taking in strings and sending them with HTTP to a connection and processing
// the resulting bytes to a string and sending the results back
public class HTTPHandler {

    public static byte[] requestUrlBytes(String urlSpec) throws IOException {

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


    public static String requestUrl(String urlSpec) throws IOException {
        return new String(requestUrlBytes(urlSpec));
    }



}
