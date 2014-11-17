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


public class MyActivity extends Activity {
    private static final String LOG_TAG = MyActivity.class.getSimpleName();
    private ProgressBar progressBar;
    private TextView txt;
    private Button btn_polite;
    private Button btn_rude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Button btn = (Button) findViewById(R.id.button1);
        btn_polite = (Button) findViewById(R.id.button_polite);
        btn_rude = (Button) findViewById(R.id.button_rude);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new LongOperation().execute(7, 6, 5);
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txt = (TextView) findViewById(R.id.output);
    }

    private class LongOperation extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            txt.setText("Nothing has been done yet");
            progressBar.setProgress(0);
            btn_polite.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    LongOperation.this.cancel(false);
//                    flagCancelled = true; //goes to onPostExecute method
                }
            });
            btn_rude.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    LongOperation.this.cancel(true);
                }
            });
        }

        @Override
        protected String doInBackground(Integer... params) {
            for (int x : params) {
                for (int i = 0; i < x; i++) {
                    if(isCancelled()) { //Fix before presentation!
                        return "Politely interrupted";
                    }
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

        @Override
        protected void onCancelled(String result) {
            txt.setText(result);
        }

    }
}
