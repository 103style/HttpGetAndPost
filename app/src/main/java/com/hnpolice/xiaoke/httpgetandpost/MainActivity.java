package com.hnpolice.xiaoke.httpgetandpost;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.get)
    Button get;
    @InjectView(R.id.post)
    Button post;
    @InjectView(R.id.show)
    TextView show;

    String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });
    }


    private void post() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");

                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    bufferedWriter.write("keyfrom=kekekekke&key=1021792329&type=data&doctype=json&version=1.1&q=question");
                    bufferedWriter.flush();
                    writer.close();

                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, "utf-8");
                    BufferedReader br = new BufferedReader(isr);

                    String line;
                    res = "";
                    while ((line = br.readLine()) != null) {
                        res = res + line;
                        Log.e("line = ", line);
                    }
                    br.close();
                    isr.close();
                    is.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return res;
            }

            @Override
            protected void onPostExecute(String s) {
                show.setText(s);
            }
        }.execute("http://fanyi.youdao.com/openapi.do");
    }

    private void get() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    URLConnection connection = url.openConnection();
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    res = "";
                    while ((line = br.readLine()) != null) {
                        res = res + line;
                        Log.e("line = ", line);
                    }
                    br.close();
                    isr.close();
                    is.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return res;
            }

            @Override
            protected void onPostExecute(String s) {
                show.setText(s);
            }
        }.execute("http://fanyi.youdao.com/openapi.do?keyfrom=kekekekke&key=1021792329&type=data&doctype=json&version=1.1&q=question");
    }
}
