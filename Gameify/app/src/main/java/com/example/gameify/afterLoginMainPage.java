package com.example.gameify;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import javax.crypto.Mac;

public class afterLoginMainPage extends AppCompatActivity {


    Button but_find, but_gamedata_button_afterLogPage;
    TextView tv_logout, tv_welcome, tv_username, tv_gamedata;
    TextView tv_refreshgamedata;

    private static String name_bf;
    private static String username_bf;
    public static String total_gamedata = "Your game data:\n";

    @SuppressLint("SetTextI18n")
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

        loadData();
        int index = getIntent().getIntExtra("index", 0);
        name_bf = userAccount.getUserAccountArrayList().get(index).getName() + " " + userAccount.getUserAccountArrayList().get(index).getSurname();
        username_bf = userAccount.getUserAccountArrayList().get(index).getUsername();

        tv_welcome.setText("Welcome, " + name_bf + "!!");
        tv_username.setText("Your username: "+username_bf);
        tv_gamedata.setText(total_gamedata);

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(afterLoginMainPage.this, MainActivity.class);
                Toast.makeText(afterLoginMainPage.this, "Log out Successful", Toast.LENGTH_SHORT).show();
                afterLoginMainPage.this.finish();
                startActivity(intent);
            }
        });

        but_gamedata_button_afterLogPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(afterLoginMainPage.this , addGame.class);
                intent.putExtra("usernameToAddGame", username_bf);
                startActivity(intent);
            }
        });

        tv_refreshgamedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();

                String out = "Games:\n";
                ArrayList<Map> tempArray = gameData.getArrayList();
                int size = tempArray.size();
                if (!(size == 0)){
                for (int i = 0 ; i<tempArray.size() ; i++){
                    Map<String , Map> tempMap = tempArray.get(i);
                    for (Map.Entry m : tempMap.entrySet()){
                        if (m.getKey().toString().equalsIgnoreCase(username_bf)){
                            Map<String , String > tempMapInner = (Map<String, String>) m.getValue();
                            for (Map.Entry mInner : tempMapInner.entrySet()){
                                String gameName = mInner.getKey().toString();
                                String gameRank = mInner.getValue().toString();
                                out = out + "\n" + gameName + "-:-" + gameRank;
                            }
                        }
                    }
                }
                }else {
                    Toast.makeText(afterLoginMainPage.this,"No data",Toast.LENGTH_SHORT).show();
                }
                tv_gamedata.setText(out);
            }
        });
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
