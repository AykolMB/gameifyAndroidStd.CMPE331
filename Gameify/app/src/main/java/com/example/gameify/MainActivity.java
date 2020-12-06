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

    private TextView tv_gameify, tv_username, tv_user_password, tv_create_account;
    private EditText et_username, et_password;
    private CheckBox cb_robot;
    private Button but_login_button;
    final int CREATEACCOUNT = 7;

    //public static ArrayList<String> user_list = new ArrayList<>();
    //public static ArrayList<String> pass_list = new ArrayList<>();

    private final String CREDENTIAL_SHARED_PREF = "our_shared_pref";

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

                String username_from_et = et_username.getText().toString();
                String password_from_et = et_password.getText().toString();

                brkfor:
                for (;;){

                    if (userAccount.userAccountArrayList.isEmpty()){
                        Toast.makeText(MainActivity.this, "Data not found!!", Toast.LENGTH_SHORT).show();
                        break brkfor;
                    }

                    int index = 0;

                    if (index == 0 && userAccount.getUserAccountArrayList().get(index) == null){
                        Toast.makeText(MainActivity.this, "No account for entered username!!",Toast.LENGTH_SHORT).show();
                        break brkfor;
                    }

                    brkwhile:
                    while(index < userAccount.userAccountArrayList.size()){
                        if (index == userAccount.userAccountArrayList.size()){
                            index = 0;
                            Toast.makeText(MainActivity.this, "There is no account for entered username", Toast.LENGTH_SHORT).show();
                            break brkfor;
                        }
                        else if (username_from_et.equalsIgnoreCase(userAccount.userAccountArrayList.get(index).getUsername())){
                            index = index;
                            break brkwhile;
                        }else{
                            index++;
                        }

                    }

                    String list_username = userAccount.getUserAccountArrayList().get(index).getUsername();
                    String list_password = userAccount.getUserAccountArrayList().get(index).getPassword();

                    if (list_username != null && username_from_et != null && list_username.equalsIgnoreCase(username_from_et)){
                        if (list_password != null && password_from_et != null && list_password.equalsIgnoreCase(password_from_et)){
                            if (cb_robot.isChecked()){
                                Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, com.example.gameify.afterLoginMainPage.class);
                                intent.putExtra("index" , index);
                                startActivity(intent);
                                break brkfor;}
                            else{
                                Toast.makeText(MainActivity.this, "Please verify, you're human.",Toast.LENGTH_SHORT).show();
                                break brkfor;
                            }
                        }else {
                            Toast.makeText(MainActivity.this, "Login Failed!!",Toast.LENGTH_SHORT).show();
                            break brkfor;
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Login Failed!!",Toast.LENGTH_SHORT).show();
                        break brkfor;
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
        String json = gson.toJson(userAccount.getUserAccountArrayList());
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
