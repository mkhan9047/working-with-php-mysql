package com.example.mujahid.working_with_php_mysql;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mujahid on 1/12/2018.
 */

public class Validator {
    List<EditText> data = new ArrayList<>();

    public Validator(List<EditText> texts){
        data = texts;
    }

    public  boolean isValidateSucess(){
        int count = 0;
        for(EditText text:data){

            if(text.getText().length()>0){
                count++;
            }
        }
        return !(count < data.size());
    }

}
