package com.jvrware.prtimer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class ProfileActivity extends Activity {

    String email;
    EditText emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email = getIntent().getStringExtra("email");

        emailText = (EditText) findViewById(R.id.emailText);
        //emailText.setText(email);
    }
}
