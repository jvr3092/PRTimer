package com.jvrware.prtimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class OptionActivity extends Activity {

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        email = getIntent().getStringExtra("email");
    }

    public void profile(View v) {

    }

    public void timer(View v) {
        BackgroundTimerData backgroundTimerData = new BackgroundTimerData(this);
        backgroundTimerData.execute(email);
    }

    public void onlinePractice(View v) {

    }

    public void stats(View v) {

    }
}
