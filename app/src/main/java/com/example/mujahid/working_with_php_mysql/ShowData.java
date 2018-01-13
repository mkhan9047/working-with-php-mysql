package com.example.mujahid.working_with_php_mysql;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ShowData extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<contact> contacts;
    String jsonString;
    String finalString;
    JSONArray array;
    JSONObject object;
    RecycleviewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        recyclerView = findViewById(R.id.recycle);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        contacts = new ArrayList<>();

        new getJsonBackground().execute();


    }


    class getJsonBackground extends AsyncTask<String, Void, String>{
        String jsonUrl;
        protected void onPreExecute(){
        jsonUrl  = "http://mujahidprojects.000webhostapp.com/jsons.php";
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(jsonUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder builder = new StringBuilder();
                while ((jsonString = reader.readLine())!=null){
                builder.append(jsonString).append("\n");
                }

                reader.close();
                inputStream.close();
                connection.disconnect();
                return builder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return null;
        }
        protected void onPostExecute(String result){
            try {
                object = new JSONObject(result);
                array = object.getJSONArray("server resoponser");
                int count = 0;
                String name, father_name, email, phone;
                while(count<array.length()){
                    JSONObject jsonObject = array.getJSONObject(count);
                    name = jsonObject.getString("name");
                    father_name = jsonObject.getString("father_name");
                    email = jsonObject.getString("email");
                    phone = jsonObject.getString("phone");
                    contact a = new contact(name,father_name,email,phone);
                    contacts.add(a);
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
            adapter = new RecycleviewAdapter(contacts);
            recyclerView.setAdapter(adapter);

        }

    }

}
