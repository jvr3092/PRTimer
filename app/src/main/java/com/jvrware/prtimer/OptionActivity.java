package com.jvrware.prtimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OptionActivity extends Activity {

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        email = getIntent().getStringExtra("email");
    }

    public void profile(View v) {
        //BackgroundProfileData backgroundProfileData = new BackgroundProfileData(this);
        //backgroundProfileData.execute(email);
        Intent intent = new Intent(OptionActivity.this, ProfileActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void timer(View v) {
        BackgroundPuzzleTimerData backgroundPuzzleTimerData = new BackgroundPuzzleTimerData(this);
        backgroundPuzzleTimerData.execute(email);
    }

    public void onlinePractice(View v) {

    }

    public void stats(View v) {

    }
}
