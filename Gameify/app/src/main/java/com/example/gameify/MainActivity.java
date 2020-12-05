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

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private TextView tv_gameify, tv_username, tv_user_password, tv_create_account;
    private EditText et_username, et_password;
    private CheckBox cb_robot;
    private Button but_login_button;
    final int CREATEACCOUNT = 7;

    public static ArrayList<String> user_list = new ArrayList<>();
    public static ArrayList<String> pass_list = new ArrayList<>();


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

        //default data - will be removed.
        user_list.add("admin");
        pass_list.add("123");

        tv_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.gameify.createAccountWindow.class);
                //startActivityForResult(intent, CREATEACCOUNT);
                startActivity(intent);
            }
        });



        but_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences credentials = getSharedPreferences(CREDENTIAL_SHARED_PREF, Context.MODE_PRIVATE);
                String str_username = credentials.getString("username",null);
                String str_password = credentials.getString("password",null);
                String str_name = credentials.getString("name",null);
                user_list.add(str_username);
                pass_list.add(str_password);
                String username_from_et = et_username.getText().toString();
                String password_from_et = et_password.getText().toString();

                brkfor:
                for (;;){
                    int index = 0;
                    brkwhile:
                    while(index < user_list.size()){
                        if (username_from_et.equalsIgnoreCase(user_list.get(index))){
                            index = index;
                            break brkwhile;
                        }else{
                            index++;
                        }
                    }

                    String list_username = user_list.get(index);
                    String list_password = pass_list.get(index);


                    if (list_username != null && username_from_et != null && list_username.equalsIgnoreCase(username_from_et)){
                        if (list_password != null && password_from_et != null && list_password.equalsIgnoreCase(password_from_et)){
                            if (cb_robot.isChecked()){
                                Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, com.example.gameify.afterLoginMainPage.class);
                                intent.putExtra("name", str_name);
                                intent.putExtra("username", str_username);
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
/*
                if (str_username != null && username_from_et != null && str_username.equalsIgnoreCase(username_from_et)){
                    if (str_password != null && password_from_et != null && str_password.equalsIgnoreCase(password_from_et)){
                        if (cb_robot.isChecked()){
                        Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, com.example.gameify.afterLoginMainPage.class);
                        intent.putExtra("name", str_name);
                        intent.putExtra("username", str_username);
                        startActivity(intent);}
                        else{
                            Toast.makeText(MainActivity.this, "Please verify, you're human.",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,"Login Failed!!",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
*/
        });


    }
}
