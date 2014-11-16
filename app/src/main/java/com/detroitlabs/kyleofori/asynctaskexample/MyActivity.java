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



    Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        btn = (Button) findViewById(R.id.button1);
        // because we implement OnClickListener we only have to pass "this"
        // (much easier)
        btn.setOnClickListener(this);
    }

    public void onClick(View view) {
        // detect the view that was "clicked"
        switch (view.getId()) {
            case R.id.button1:
                new LongOperation().execute(25, 50, 75, 100);
                break;
        }
    }

    private class LongOperation extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... params) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                    publishProgress();
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            TextView txt = (TextView) findViewById(R.id.output);
            txt.setText("Executed"); // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is up to you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Integer... values) {
            try {
                ProgressBar elBar = (ProgressBar) findViewById(R.id.progressBar);
                elBar.setProgress(0);
            } catch (IllegalArgumentException e) {
            }
        }
    }
}
