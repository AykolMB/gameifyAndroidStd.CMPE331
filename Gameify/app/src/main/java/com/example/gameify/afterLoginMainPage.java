package com.example.gameify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.crypto.Mac;

public class afterLoginMainPage extends AppCompatActivity {


    private Button but_find, but_gamedata_button_afterLogPage;
    private TextView tv_logout, tv_welcome, tv_username, tv_gamedata;
    private TextView tv_refreshgamedata;

    private static String name_bf = createAccountWindow.pbname;
    private static String username_bf = createAccountWindow.pbusername;
    public static String total_gamedata = "Your game data:\n";

    private final String CREDENTIAL_SHARED_PREF = "our_shared_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_main_page);

        but_find = (Button) findViewById(R.id.but_login_button);
        but_gamedata_button_afterLogPage = (Button) findViewById(R.id.but_gamedata_button_afterLogPage);
        tv_logout = (TextView) findViewById(R.id.tv_logout_afterLogPage);
        tv_welcome = (TextView) findViewById(R.id.tv_welcome_afterLogPage);
        tv_username = (TextView) findViewById(R.id.tv_username_afterLogPage);
        tv_gamedata = (TextView) findViewById(R.id.tv_game_data_afterLogPage);
        tv_refreshgamedata = (TextView) findViewById(R.id.tv_refreshgamedata);

        Intent intent = new Intent(getApplication(), MainActivity.class);


        tv_welcome.setText("Welcome, " + name_bf + "!!");
        tv_username.setText("Your username: "+username_bf);
        tv_gamedata.setText(total_gamedata);



        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(afterLoginMainPage.this, com.example.gameify.MainActivity.class);
                Toast.makeText(afterLoginMainPage.this, "Log out Successful", Toast.LENGTH_SHORT).show();
                afterLoginMainPage.this.finish();
                startActivity(intent);

            }
        });

        but_gamedata_button_afterLogPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(afterLoginMainPage.this , com.example.gameify.addGameData.class);
                startActivity(intent);
            }
        });

        tv_refreshgamedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_welcome.setText("Welcome, " + name_bf + "!!");
                tv_username.setText("Your username: "+username_bf);
                String oldData = total_gamedata;
                Intent intent =getIntent();
                String data = intent.getStringExtra("gamedata");
                if(data != null){
                    total_gamedata =oldData + "\n" + data;
                    tv_gamedata.setText(total_gamedata); }
                else{
                    Toast.makeText(afterLoginMainPage.this, "There is no new data!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}