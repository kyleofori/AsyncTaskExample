package com.detroitlabs.kyleofori.asynctaskexample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.System;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class MyActivity extends Activity implements OnClickListener {

    public Button btn;
    public ProgressBar elBar;
    public TextView txt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        btn = (Button) findViewById(R.id.button1);
        // because we implement OnClickListener we only have to pass "this"
        // (much easier)
        btn.setOnClickListener(this);
        elBar = (ProgressBar) findViewById(R.id.progressBar);
        txt = (TextView) findViewById(R.id.output);
    }

    public void onClick(View view) {
        // detect the view that was "clicked"
        switch (view.getId()) {
            case R.id.button1:
                new LongOperation().execute(5);
                break;
        }
    }

    private class LongOperation extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            elBar.setProgress(0);
        }

        @Override
        protected String doInBackground(Integer... params) {
            for (int x : params) {
                for (int i = 0; i < x; i++) {
                    try {
                        Thread.sleep(1000);
                        publishProgress(i);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                }
            }
            return "Executed all"; //What's returned will be the argument for onPostExecute().
        }

        @Override
        protected void onPostExecute(String result) {
            txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is up to you
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            try {
                elBar.incrementProgressBy(1);
                txt.setText("Executing a task...");
            } catch (IllegalArgumentException e) {
            }
        }
    }
}
