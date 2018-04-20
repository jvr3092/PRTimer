package com.jvrware.prtimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class SignUpActivity extends Activity {

    private EditText emailText, passwordText, confirmPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailText = (EditText) findViewById(R.id.signUpEmailText);
        passwordText = (EditText) findViewById(R.id.signUpPasswordText);
        confirmPasswordText = (EditText) findViewById(R.id.signUpConfirmPasswordText);
    }

    public void signUp(View v) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();
        if(password.equals(confirmPassword)) {
            BackgroundTask backgroundSignUp = new BackgroundTask(this);
            backgroundSignUp.execute("signUp", email, password);
        }
        else {
            Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_LONG).show();
        }
    }
}
