package com.jvrware.prtimer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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

public class TimerActivity extends Activity {

    String email, scramble, scrambledPuzzle;
    Spinner puzzleTypeSpinner, solveTypeSpinner;
    TextView scrambleTextView, timeTextView;
    View[] cube = new View[54];
    Chronometer chronometer;
    Thread threadChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        email = getIntent().getStringExtra("email");
        scramble = getIntent().getStringExtra("scramble");
        scrambledPuzzle = getIntent().getStringExtra("scrambledPuzzle");
        ArrayList<String> puzzleData = getIntent().getStringArrayListExtra("puzzleData");

        ArrayAdapter<String> arrayAdapterPuzzleType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, puzzleData);
        arrayAdapterPuzzleType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        puzzleTypeSpinner = (Spinner) findViewById(R.id.puzzleType);
        puzzleTypeSpinner.setAdapter(arrayAdapterPuzzleType);
        puzzleTypeSpinner.setSelection(arrayAdapterPuzzleType.getPosition("3x3x3"));

        solveTypeSpinner = (Spinner) findViewById(R.id.solveType);
        BackgroundGetSolveTypes backgroundGetSolveTypes = new BackgroundGetSolveTypes(this, solveTypeSpinner);
        backgroundGetSolveTypes.execute(email, puzzleTypeSpinner.getSelectedItem().toString());

        scrambleTextView = (TextView) findViewById(R.id.scramble);

        cube[0] = (View) findViewById(R.id.cube1);
        cube[1] = (View) findViewById(R.id.cube2);
        cube[2] = (View) findViewById(R.id.cube3);
        cube[3] = (View) findViewById(R.id.cube4);
        cube[4] = (View) findViewById(R.id.cube5);
        cube[5] = (View) findViewById(R.id.cube6);
        cube[6] = (View) findViewById(R.id.cube7);
        cube[7] = (View) findViewById(R.id.cube8);
        cube[8] = (View) findViewById(R.id.cube9);
        cube[9] = (View) findViewById(R.id.cube10);
        cube[10] = (View) findViewById(R.id.cube11);
        cube[11] = (View) findViewById(R.id.cube12);
        cube[12] = (View) findViewById(R.id.cube13);
        cube[13] = (View) findViewById(R.id.cube14);
        cube[14] = (View) findViewById(R.id.cube15);
        cube[15] = (View) findViewById(R.id.cube16);
        cube[16] = (View) findViewById(R.id.cube17);
        cube[17] = (View) findViewById(R.id.cube18);
        cube[18] = (View) findViewById(R.id.cube19);
        cube[19] = (View) findViewById(R.id.cube20);
        cube[20] = (View) findViewById(R.id.cube21);
        cube[21] = (View) findViewById(R.id.cube22);
        cube[22] = (View) findViewById(R.id.cube23);
        cube[23] = (View) findViewById(R.id.cube24);
        cube[24] = (View) findViewById(R.id.cube25);
        cube[25] = (View) findViewById(R.id.cube26);
        cube[26] = (View) findViewById(R.id.cube27);
        cube[27] = (View) findViewById(R.id.cube28);
        cube[28] = (View) findViewById(R.id.cube29);
        cube[29] = (View) findViewById(R.id.cube30);
        cube[30] = (View) findViewById(R.id.cube31);
        cube[31] = (View) findViewById(R.id.cube32);
        cube[32] = (View) findViewById(R.id.cube33);
        cube[33] = (View) findViewById(R.id.cube34);
        cube[34] = (View) findViewById(R.id.cube35);
        cube[35] = (View) findViewById(R.id.cube36);
        cube[36] = (View) findViewById(R.id.cube37);
        cube[37] = (View) findViewById(R.id.cube38);
        cube[38] = (View) findViewById(R.id.cube39);
        cube[39] = (View) findViewById(R.id.cube40);
        cube[40] = (View) findViewById(R.id.cube41);
        cube[41] = (View) findViewById(R.id.cube42);
        cube[42] = (View) findViewById(R.id.cube43);
        cube[43] = (View) findViewById(R.id.cube44);
        cube[44] = (View) findViewById(R.id.cube45);
        cube[45] = (View) findViewById(R.id.cube46);
        cube[46] = (View) findViewById(R.id.cube47);
        cube[47] = (View) findViewById(R.id.cube48);
        cube[48] = (View) findViewById(R.id.cube49);
        cube[49] = (View) findViewById(R.id.cube50);
        cube[50] = (View) findViewById(R.id.cube51);
        cube[51] = (View) findViewById(R.id.cube52);
        cube[52] = (View) findViewById(R.id.cube53);
        cube[53] = (View) findViewById(R.id.cube54);

        setScrambleAndCube();

        timeTextView = (TextView) findViewById(R.id.time);
        timeTextView.setText("0.000");
    }

    public void setScrambleAndCube() {
        scrambleTextView.setText(scramble);
        for(int i = 0; i < 54; i++) {
            switch(scrambledPuzzle.charAt(i)) {
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

    public void setTimer(View v) {
        if(chronometer == null) {
            chronometer = new Chronometer(this);
            threadChronometer = new Thread(chronometer);
            threadChronometer.start();
            chronometer.start();
        }
        else {
            String time = Long.toString(chronometer.stop());
            chronometer = null;
            BackgroundGetNewScramble backgroundGetNewScramble = new BackgroundGetNewScramble(this, scrambleTextView, cube);
            backgroundGetNewScramble.execute(email, puzzleTypeSpinner.getSelectedItem().toString(), solveTypeSpinner.getSelectedItem().toString(), scramble, time, "private");
        }
    }

    public void updateTimer(final String time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timeTextView.setText(time);
            }
        });
    }
}
