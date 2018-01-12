package com.example.mujahid.working_with_php_mysql;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText Father_name;
    EditText email;
    EditText phone;
    List<EditText> editLIst;
    Switch aSwitch;
    BroadcastReceiver DataInd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = findViewById(R.id.edit_name);
        Father_name = findViewById(R.id.edit_father_name);
        email = findViewById(R.id.edit_email);
        phone = findViewById(R.id.edit_phone);
        aSwitch = findViewById(R.id.dataOnindicator);

      editLIst = new ArrayList<>();
       editLIst.add(name);
       editLIst.add(Father_name);
       editLIst.add(email);
       editLIst.add(phone);

      DataInd = new BroadcastReceiver(){

           @Override
           public void onReceive(Context context, Intent intent) {
            if(isDataConnected(context)){
                aSwitch.setChecked(true);
            }else{
                aSwitch.setChecked(false);
            }
           }
       };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSave(View view) {
        Validator validator = new Validator(editLIst);
        if(isDataConnected(this)){
            if(validator.isValidateSucess()){
                saveOnBackgroud saveOnBackgroud = new saveOnBackgroud();
                saveOnBackgroud.execute(name.getText().toString(),Father_name.getText().toString(),email.getText().toString(),phone.getText().toString());
            }else {
                Toast.makeText(this,"Insert empty fields!",Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this,"Please on your moible data!",Toast.LENGTH_LONG).show();
        }

    }

    private boolean isDataConnected(Context context){
        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();

            isConnected = info != null && info.isConnectedOrConnecting();
        }
        return isConnected;
    }

    public void onResume(){
        super.onResume();
        registerReceiver(DataInd,new IntentFilter(Constants.DATA_INDICATOR));
    }

    public void onPause(){
        super.onPause();
        unregisterReceiver(DataInd);
    }

     class saveOnBackgroud extends AsyncTask<String, Void, String>{

        String add_data_url;

        protected void onPreExecute(){
            add_data_url = "http://mujahidprojects.000webhostapp.com/add_info.php";
        }

        @Override
        protected String doInBackground(String... args) {
            String name,email,phone,father_name;
            name = args[0];
            father_name = args[1];
            email = args[2];
            phone = args[3];

            try {
                URL url = new URL(add_data_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_String = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("father_name","UTF-8")+"="+URLEncoder.encode(father_name,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8");
                Log.d("Mim",data_String);
                bufferedWriter.write(data_String);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = connection.getInputStream();
                inputStream.close();
                connection.disconnect();

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();
            }

            return "One Row of Data Inserted";
        }

        protected void onProgressUpdate(){

        }

        protected void onPostExecute(String result){
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }
    }

}
