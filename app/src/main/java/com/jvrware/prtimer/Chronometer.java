package com.jvrware.prtimer;

import android.content.Context;

public class Chronometer implements Runnable {

    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLIS_TO_HOURS = 3600000;

    private Context context;
    private long startTime, stopTime;
    private boolean isRunning;

    public Chronometer(Context context) {
        this.context = context;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        isRunning = true;
    }

    public long stop() {
        stopTime = System.currentTimeMillis() - startTime;
        isRunning = false;
        return stopTime;
    }

    @Override
    public void run() {
        while(isRunning) {
            long since = System.currentTimeMillis() - startTime;
            int milliseconds = (int) (since % 1000);
            int seconds = (int) ((since / 1000) %60);
            int minutes = (int) ((since / MILLIS_TO_MINUTES) % 60);
            int hours = (int) ((since / MILLIS_TO_HOURS));

            if(since < 10000) {
                ((TimerActivity)context).updateTimer(String.format("%01d.%03d", seconds, milliseconds));
            }
            else if(since < 60000) {
                ((TimerActivity)context).updateTimer(String.format("%02d.%03d", seconds, milliseconds));
            }
            else if(since < 600000) {
                ((TimerActivity)context).updateTimer(String.format("%01d:%02d.%03d", minutes, seconds, milliseconds));
            }
            else if(since < 3600000) {
                ((TimerActivity)context).updateTimer(String.format("%02d:%02d.%03d", minutes, seconds, milliseconds));
            }
            else if(since < 36000000) {
                ((TimerActivity)context).updateTimer(String.format("%01d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds));
            }
            else {
                ((TimerActivity)context).updateTimer(String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds));
            }

            try {
                Thread.sleep(15);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
