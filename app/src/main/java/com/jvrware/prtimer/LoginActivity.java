package com.jvrware.prtimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends Activity {

    EditText emailText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);

        emailText.setText(getIntent().getStringExtra("email"));
        passwordText.setText(getIntent().getStringExtra("password"));
    }

    public void signIn(View v) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        BackgroundTask backgroundSignIn = new BackgroundTask(this);
        backgroundSignIn.execute("signIn", email, password);
    }

    public void signUpButton(View v) {
        Intent signUp = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(signUp);
    }
}
