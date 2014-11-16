package com.detroitlabs.kyleofori.asynctaskexample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class MyActivity extends Activity implements OnClickListener {

    private ProgressBar elBar;
    private TextView txt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Button btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(this);
        elBar = (ProgressBar) findViewById(R.id.progressBar);
        txt = (TextView) findViewById(R.id.output);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                new LongOperation().execute(7, 6, 5);
                break;
        }
    }

    private class LongOperation extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            txt.setText("Nothing has been done yet");
        }

        @Override
        protected String doInBackground(Integer... params) {
            for (int x : params) {
                for (int i = 0; i < x; i++) {
                    try {
                        Thread.sleep(1000);
                        publishProgress(i+1);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                }
            }
            return "Executed all";
        }

        @Override
        protected void onPostExecute(String result) {
            txt.setText(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            try {
                elBar.setProgress(values[0]);
                txt.setText("Executing a task...");
            } catch (IllegalArgumentException e) {
            }
        }
    }
}
