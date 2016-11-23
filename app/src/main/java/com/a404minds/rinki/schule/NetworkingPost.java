package com.a404minds.rinki.schule;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rinki on 12/11/16.
 */
public class NetworkingPost extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... data) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        int responseCode = 0;
        JSONObject responseData = new JSONObject();

        try {
            // Construct the URL
            URL url = new URL("http://10.0.2.2:3030/api" + data[0]);

            // Create the request to API, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            //urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            //urlConnection.setDoInput(true);
            //urlConnection.setDoOutput(true);

            JSONObject postData = new JSONObject(data[1]);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postData));
            writer.flush();
            writer.close();
            os.close();

            responseCode = urlConnection.getResponseCode();

            // Fetch response
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            responseData.put("code",Integer.toString(responseCode));
            responseData.put("data", response.toString());

        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseData.toString();
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

}
