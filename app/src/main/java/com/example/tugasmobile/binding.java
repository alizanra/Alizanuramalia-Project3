package com.example.tugasmobile;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tugasmobile.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import kotlin.Suppress;
public class binding extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<String> userList;
    ArrayAdapter<String> listAdapter;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        class fetchData extends Thread {
            String data ="";
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {
                try {
                    URL url = new URL("https://json.link/xxxxxxxxxx.json");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;

                    while ((line = bufferedReader.readLine()) != null) {

                        data = data + line;
                    }

                    if (!data.isEmpty()) {
                        JSONObject jsonObject = new JSONObject(data);
                        JSONArray users = jsonObject.getJSONArray("Users");
                        userList.clear();
                        for (int i = 0; i < users.length(); i++) {

                            JSONObject names = users.getJSONObject(i);
                            String name = names.getString("name");
                            userList.add(name);
                        }

                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
            }
        }
    }

        }


