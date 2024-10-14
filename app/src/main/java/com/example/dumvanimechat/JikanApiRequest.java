package com.example.dumvanimechat;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JikanApiRequest extends AsyncTask<String, Void, String> {
    private JikanApiListener listener;
    public JikanApiRequest(JikanApiListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String queryUrl = params[0];
//dumv sai api free
        try {
            URL url = new URL(queryUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener != null) {
            listener.onRequestCompleted(result);
        }
    }

    public interface JikanApiListener {
        void onRequestCompleted(String result);
    }
}

