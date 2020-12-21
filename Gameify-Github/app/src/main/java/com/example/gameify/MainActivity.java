package com.example.gameify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView tv_gameify, tv_username, tv_user_password, tv_create_account;
    private EditText et_username, et_password;
    private CheckBox cb_robot;
    Button but_login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_gameify = (TextView) findViewById(R.id.tv_gameify);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_user_password = (TextView) findViewById(R.id.tv_user_password);
        tv_create_account = (TextView) findViewById(R.id.tv_create_account);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        cb_robot = (CheckBox) findViewById(R.id.cb_robot);
        but_login_button = (Button) findViewById(R.id.but_login_button);

        // Silinecek
        tv_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                Intent intent =  new Intent(MainActivity.this, com.example.gameify.afterLoginMainPage.class);
                userAccount userAdmin = new userAccount("Admin","Admin","Admin","Admin","admin","123","123");
                userAccount.userAccountArrayList.add(userAdmin);
                gameData gd = new gameData("admin", "admin", "admin", "admin", "admin", "admin");
                gameData.allUserData.add(0,gd);
                int index = userAccount.getUserAccountArrayList().indexOf(userAdmin);
                intent.putExtra("index",index);
                saveData();
                startActivity(intent);

            }
        });

        tv_gameify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });

        tv_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.gameify.createAccountWindow.class);
                startActivity(intent);
            }
        });

        but_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                int index = 0;
                String username_from_et = et_username.getText().toString();
                String password_from_et = et_password.getText().toString();

                if (userAccount.getUserAccountArrayList().size() == 0){
                    Toast.makeText(MainActivity.this, "There is no entered data!!", Toast.LENGTH_SHORT).show();
                }else {
                    for (int i = 0; i < userAccount.getUserAccountArrayList().size(); i++) {
                        if (username_from_et.equalsIgnoreCase(userAccount.getUserAccountArrayList().get(i).getUsername())) {
                            if (password_from_et.equalsIgnoreCase(userAccount.getUserAccountArrayList().get(i).getPassword())) {
                                index = i;
                                String usernameList = userAccount.getUserAccountArrayList().get(index).getUsername();
                                String passwordList = userAccount.getUserAccountArrayList().get(index).getPassword();
                                if (cb_robot.isChecked()) {
                                    et_username.setText("");
                                    et_password.setText("");
                                    Toast.makeText(MainActivity.this, "Login Succesful!!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, com.example.gameify.afterLoginMainPage.class);
                                    intent.putExtra("index", index);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "Please verify you're human!!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Entered password is not matched username!!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Entered username is not matched any data!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userAccount.getUserAccountArrayList());
        editor.putString("userlist",json);
        editor.apply();

    }

    private void clearData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.remove("userlist");
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
