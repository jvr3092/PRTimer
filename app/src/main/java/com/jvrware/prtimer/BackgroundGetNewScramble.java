package com.jvrware.prtimer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundGetNewScramble extends AsyncTask<String, Void, String[]>{

    private Context context;
    private TextView scrambleTextView;
    private View[] cube;

    public BackgroundGetNewScramble(Context context, TextView scrambleTextView, View[] cube) {
        this.context = context;
        this.scrambleTextView = scrambleTextView;
        this.cube = cube;
    }

    @Override
    protected String[] doInBackground(String... strings) {
        String link = "https://prt-app.000webhostapp.com/insertTime.php";
        try {
            String email = strings[0];
            String puzzleType = strings[1];
            String solveType = strings[2];
            String scramble = strings[3];
            String time = strings[4];
            String status = strings[5];
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("puzzleType", "UTF-8") + "=" + URLEncoder.encode(puzzleType, "UTF-8");
            data += "&" + URLEncoder.encode("solveType", "UTF-8") + "=" + URLEncoder.encode(solveType, "UTF-8");
            data += "&" + URLEncoder.encode("scramble", "UTF-8") + "=" + URLEncoder.encode(scramble, "UTF-8");
            data += "&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8");
            data += "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        link = "https://prt-app.000webhostapp.com/getScramble.php";
        try {
            String[] scramble = new String[2];
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
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = null;
            jsonObject = jsonArray.getJSONObject(0);
            scramble[0] = jsonObject.getString("scramble");
            //jsonObject = jsonArray.getJSONObject(1);
            scramble[1] = jsonObject.getString("scrambledPuzzle");
            return scramble;
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    @Override
    protected void onPostExecute(String[] scramble) {
        scrambleTextView.setText(scramble[0]);
        for(int i = 0; i < 54; i++) {
            switch(scramble[1].charAt(i)) {
                case 'W':
                    cube[i].setBackgroundColor(Color.rgb(255, 255, 255));
                    break;
                case 'G':
                    cube[i].setBackgroundColor(Color.rgb(6, 214, 12));
                    break;
                case 'R':
                    cube[i].setBackgroundColor(Color.rgb(214, 6, 6));
                    break;
                case 'Y':
                    cube[i].setBackgroundColor(Color.rgb(252, 248, 34));
                    break;
                case 'B':
                    cube[i].setBackgroundColor(Color.rgb(35, 118, 252));
                    break;
                case 'O':
                    cube[i].setBackgroundColor(Color.rgb(255, 140, 0));
                    break;
            }
        }
    }
}
