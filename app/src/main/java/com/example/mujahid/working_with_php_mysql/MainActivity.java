package com.example.mujahid.working_with_php_mysql;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText Father_name;
    EditText email;
    EditText phone;
    List<EditText> editLIst;
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

      editLIst = new ArrayList<>();
       editLIst.add(name);
       editLIst.add(Father_name);
       editLIst.add(email);
       editLIst.add(phone);

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
        if(validator.isValidateSucess()){
            Toast.makeText(this,"Save Success!",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Insert empty fields!",Toast.LENGTH_LONG).show();
        }
    }
}
