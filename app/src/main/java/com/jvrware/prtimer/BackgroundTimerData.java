package com.jvrware.prtimer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BackgroundTimerData extends AsyncTask<String, Void, ArrayList<String>> {

    Context context;
    private String email;

    BackgroundTimerData(Context context) {
        this.context = context;
    }
    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        List<String> list = new ArrayList<String>();
        this.email = strings[0];
        list.add(email);
        String link = "https://prt-app.000webhostapp.com/puzzleType.php";
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = null;
            String[] array = new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                array[i] = jsonObject.getString("puzzleType");
            }
            for(int i = 0; i < array.length; i++) {
                list.add(array[i]);
            }
            return (ArrayList<String>) list;
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> timerData) {
        Intent intent = new Intent(context, TimerActivity.class);
        intent.putStringArrayListExtra("timerData", timerData);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}
