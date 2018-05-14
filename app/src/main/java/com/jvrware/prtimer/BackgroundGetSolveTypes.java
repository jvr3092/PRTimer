package com.jvrware.prtimer;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class BackgroundGetSolveTypes extends AsyncTask<String, Void, ArrayList<String>> {

    private Context context;
    private Spinner solveTypeSpinner;

    public BackgroundGetSolveTypes(Context context, Spinner solveTypeSpinner) {
        this.context = context;
        this.solveTypeSpinner = solveTypeSpinner;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        String email = strings[0];
        String puzzleType = strings[1];
        String link = "https://prt-app.000webhostapp.com/solveType.php";
        List<String> list = new ArrayList<String>();
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("puzzleType", "UTF-8") + "=" + URLEncoder.encode(puzzleType, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
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
            String[] array = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                array[i] = jsonObject.getString("solveType");
            }
            for(int i = 0; i < array.length; i++) {
                list.add(array[i]);
            }
            return (ArrayList<String>) list;
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
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> solveType) {
        ArrayAdapter<String> arrayAdapterSolveType = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, solveType);
        arrayAdapterSolveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        solveTypeSpinner.setAdapter(arrayAdapterSolveType);
        solveTypeSpinner.setSelection(arrayAdapterSolveType.getPosition("Cube"));
    }
}