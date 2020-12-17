package com.example.gameify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class createAccountWindow extends AppCompatActivity {

    private EditText et_name_accPage, et_surname_accPage;
    private EditText et_email_accPage;
    private EditText et_username_accPage, et_password_accPage, et_re_password_accPage;
    Button but_create_button_accPage;
    Spinner sp_age_accPage;
    private String[] age_data_array = new String[66];
    private String age_output_fromSpinner = "";

    userAccount def_user = new userAccount();

    private final String CREDENTIAL_SHARED_PREF = "our_shared_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_window);

        int age = 10;
        age_data_array[0] = "Age";
        for (int i=1 ; i<=65 ; i++){
            age_data_array[i] = String.valueOf(age);
            age++;
        }

        sp_age_accPage = (Spinner) findViewById(R.id.sp_age_accPage);
        et_name_accPage = (EditText) findViewById(R.id.et_name_accPage);
        et_surname_accPage = (EditText) findViewById(R.id.et_surname_accPage);
        et_email_accPage = (EditText) findViewById(R.id.et_email_accPage);
        et_username_accPage = (EditText) findViewById(R.id.et_username_accPage);
        et_password_accPage = (EditText) findViewById(R.id.et_password_accPage);
        et_re_password_accPage = (EditText) findViewById(R.id.et_re_password_accPage);
        but_create_button_accPage = (Button) findViewById(R.id.but_create_button_accPage);

        ArrayAdapter aa_age = new ArrayAdapter(this, android.R.layout.simple_spinner_item, age_data_array);
        aa_age.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_age_accPage.setAdapter(aa_age);


        sp_age_accPage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                if (position == 0){
                    tv.setTextColor(Color.GRAY);
                    age_output_fromSpinner = "";
                }else{
                    tv.setTextColor(Color.BLACK);
                    age_output_fromSpinner = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        but_create_button_accPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_name_accPage.getText().toString().trim();
                String surname = et_surname_accPage.getText().toString().trim();
                String age = age_output_fromSpinner;
                String email = et_email_accPage.getText().toString().trim();
                String username = et_username_accPage.getText().toString().trim();
                String password = et_password_accPage.getText().toString().trim();
                String re_password = et_re_password_accPage.getText().toString().trim();

                boolean status = check(name,surname,age,email,username,password,re_password);

                if (status){
                    if (password.equalsIgnoreCase(re_password)){
                        userAccount new_user = new userAccount(name, surname, age, email, username, password, re_password);
                        userAccount.userAccountArrayList.add(new_user);
                        saveData();
                        createAccountWindow.this.finish();
                    }else{
                        Toast.makeText(createAccountWindow.this, "Please check your passwords..", Toast.LENGTH_SHORT).show();
                        et_password_accPage.setText("");
                        et_re_password_accPage.setText("");
                    }
                }else {
                    Toast.makeText(createAccountWindow.this, "Please fill all information!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean check(String name, String surname, String age, String email, String username, String password, String re_password) {
        return !name.isEmpty() && !surname.isEmpty() && !age.isEmpty() && !email.isEmpty() && !username.isEmpty() && !password.isEmpty() && !re_password.isEmpty();
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userAccount.getUserAccountArrayList());
        editor.putString("userlist",json);
        editor.apply();

    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userlist",null);
        Type type = new TypeToken<ArrayList<userAccount>>() {}.getType();
        userAccount.setUserAccountArrayList(gson.fromJson(json, type));

        if (userAccount.getUserAccountArrayList() == null){
            new userAccount();  // will create arraylist
        }
    }

}