package com.jvrware.prtimer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class BackgroundProfileData extends AsyncTask<String, Void, String[]> {

    private Context context;
    private String email;

    BackgroundProfileData(Context context) {
        this.context = context;
    }

    @Override
    protected String[] doInBackground(String... strings) {
        this.email = strings[0];
        return strings;
        //return new String[0];
    }

    @Override
    protected void onPostExecute(String[] profileData) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("email", profileData[0]);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}
