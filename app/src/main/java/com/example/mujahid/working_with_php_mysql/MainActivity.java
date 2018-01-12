package com.example.mujahid.working_with_php_mysql;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

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
                Toast.makeText(this,"Save Success!",Toast.LENGTH_LONG).show();
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

}
