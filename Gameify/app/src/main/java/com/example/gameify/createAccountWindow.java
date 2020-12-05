package com.example.gameify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class createAccountWindow extends AppCompatActivity {

    private EditText et_name_accPage, et_surname_accPage, et_age_accPage;
    private EditText et_email_accPage, et_lang_accPage;
    private EditText et_username_accPage, et_password_accPage, et_re_password_accPage;
    private Button but_create_button_accPage;

    public static String pbname;
    public static String pbusername;

    private final String CREDENTIAL_SHARED_PREF = "our_shared_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_window);

        et_name_accPage = (EditText) findViewById(R.id.et_name_accPage);
        et_surname_accPage = (EditText) findViewById(R.id.et_surname_accPage);
        et_age_accPage = (EditText) findViewById(R.id.et_age_accPage);
        et_email_accPage = (EditText) findViewById(R.id.et_email_accPage);
        et_lang_accPage = (EditText) findViewById(R.id.et_lang_accPage);
        et_username_accPage = (EditText) findViewById(R.id.et_username_accPage);
        et_password_accPage = (EditText) findViewById(R.id.et_password_accPage);
        et_re_password_accPage = (EditText) findViewById(R.id.et_re_password_accPage);
        but_create_button_accPage = (Button) findViewById(R.id.but_create_button_accPage);

        but_create_button_accPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = et_username_accPage.getText().toString();
                String password = et_password_accPage.getText().toString();
                String re_password = et_re_password_accPage.getText().toString();
                String name =  et_name_accPage.getText().toString() + " " + et_surname_accPage.getText().toString();
                pbname = et_name_accPage.getText().toString() + " " + et_surname_accPage.getText().toString();
                pbusername = et_username_accPage.getText().toString();

                if (password != null && re_password != null && password.equalsIgnoreCase(re_password)){
                    SharedPreferences credentials = getSharedPreferences(CREDENTIAL_SHARED_PREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = credentials.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putString("name", name);
                    editor.commit();
                    createAccountWindow.this.finish();
                }else{
                    Toast.makeText(createAccountWindow.this, "Please check your passwords..", Toast.LENGTH_SHORT).show();
                    et_password_accPage.setText("");
                    et_re_password_accPage.setText("");
                }

            }
        });


    }
}