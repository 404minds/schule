package com.a404minds.rinki.schule;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rinki on 12/11/16.
 */
public class NetworkingGet extends AsyncTask<String, Void, String> {
    private Context context;

    public NetworkingGet(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... data) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        int responseCode = 0;
        JSONObject responseData = new JSONObject();

        try {
            // Construct the URL
            URL url = new URL("http://10.0.2.2:3030/api" + data[0]);

            SharedPrefs sharedPrefs = new SharedPrefs(this.context);
            String token = sharedPrefs.getPrefs("Auth_file", "token");

            // Create the request to API, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.connect();
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
            responseData.put("code", Integer.toString(responseCode));
            responseData.put("data", response.toString());

        }   catch (IOException e) {
             Log.e("PlaceholderFragment", "Error ", e);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return responseData.toString();
    }

}

