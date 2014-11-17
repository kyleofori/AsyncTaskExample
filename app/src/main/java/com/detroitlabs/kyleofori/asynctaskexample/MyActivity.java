package com.detroitlabs.kyleofori.asynctaskexample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class MyActivity extends Activity implements OnClickListener {
    private static final String LOG_TAG = MyActivity.class.getSimpleName();
    private ProgressBar progressBar;
    private TextView txt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Button btn = (Button) findViewById(R.id.button1);
        Button btn_polite = (Button) findViewById(R.id.button_polite);
        Button btn_rude = (Button) findViewById(R.id.button_rude);
        btn.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txt = (TextView) findViewById(R.id.output);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                new LongOperation().execute(7, 6, 5);
                break;
            case R.id.button_polite:
                LongOperation.cancel(false);
                break;
            case R.id.button_rude:
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
                progressBar.setProgress(values[0]);
                txt.setText("Executing a task...");
            } catch (IllegalArgumentException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }


    }
}
