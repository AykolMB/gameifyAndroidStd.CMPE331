package com.example.gameify;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
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
import java.util.TreeMap;

import javax.crypto.Mac;

public class afterLoginMainPage extends AppCompatActivity {


    Button but_find, but_gamedata_button_afterLogPage;
    TextView tv_logout, tv_welcome, tv_username, tv_gamedata;
    TextView tv_refreshgamedata;

    private static String name_bf;
    private static String username_bf;
    private static String age_bf;
    public static String total_gamedata = "Your game data:\n";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_main_page);
        but_find = (Button) findViewById(R.id.but_find_button_afterLogPage);
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
        age_bf = userAccount.getUserAccountArrayList().get(index).getAge();

        if (!gameData.getUsernameArrayList().contains(username_bf)){
            gameData.putUserArrayList(username_bf);
        }

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

        but_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(afterLoginMainPage.this, com.example.gameify.findBuddy.class);
                intent.putExtra("usernameToGameRank", username_bf);
                intent.putExtra("ageToGameRank", age_bf);
                startActivity(intent);
            }
        });
        but_gamedata_button_afterLogPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGameData();
                Intent intent = new Intent(afterLoginMainPage.this , addGame.class);
                intent.putExtra("usernameToAddGame", username_bf);
                intent.putExtra("ageToAddGame", age_bf);
                startActivity(intent);
            }
        });

        tv_refreshgamedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                loadGameData();
                String out = "";
                int index = allDataUsernameChecker(username_bf);
                String csgoRank = gameData.getAllUserData().get(index).getrCsgo();
                String lolRank = gameData.getAllUserData().get(index).getrLol();
                String r6Rank = gameData.getAllUserData().get(index).getrR6();
                String gtaRank = gameData.getAllUserData().get(index).getrGta();
                out = outputSet(csgoRank, lolRank, r6Rank, gtaRank);
                tv_gamedata.setText(out);

            }
        });

}

    private String outputSet(String csgoRank, String lolRank, String r6Rank, String gtaRank) {
        String output = "Your game data:\n";
        if (csgoRank == null && lolRank == null && r6Rank == null && gtaRank == null) {
            output = output;
        } else {
            if (!csgoRank.equalsIgnoreCase("") || csgoRank != null) {
                output = output + "CS-GO\t\t Rank: " + csgoRank + "\n";
            }
            if (!lolRank.equalsIgnoreCase("") || lolRank != null) {
                output = output + "LOL\t\t\t\t\t Rank: " + lolRank + "\n";
            }
            if (!r6Rank.equalsIgnoreCase("") || r6Rank != null) {
                output = output + "R6\t\t\t\t\t\t\t Rank: " + r6Rank + "\n";
            }
            if (!gtaRank.equalsIgnoreCase("") || gtaRank != null) {
                output = output + "GTA\t\t\t\t\t Rank: " + gtaRank + "\n";
            }
        }
        return output;
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

    private int allDataUsernameChecker(String username) {
        int a = 0;
        ArrayList<gameData> temp = gameData.getAllUserData();
        if (temp != null) {
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getUsername().equalsIgnoreCase(username)) {
                    a = i;
                    break;
                }
            }
        }
        return a;
    }

    private void loadGameData() {
        SharedPreferences sharepref = getSharedPreferences("sharepref", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonUsername = sharepref.getString("usernameArrayList", null);
        String jsonGameList = sharepref.getString("gameList", null);
        String jsonCSGO = sharepref.getString("csgoRank", null);
        String jsonLOL = sharepref.getString("lolRank", null);
        String jsonR6 = sharepref.getString("r6Rank", null);
        String jsonGTA = sharepref.getString("gtaRank", null);
        String jsonAllUserData = sharepref.getString("jsonAllUserData", null);
        Type typeSpecial = new TypeToken<ArrayList<gameData>>() {
        }.getType();
        /*
        Type typeArrayList = new TypeToken<ArrayList<String>>() {
        }.getType();
        Type typeArray = new TypeToken<String[]>() {
        }.getType();
        gameData.setUsernameArrayList(gson.fromJson(jsonUsername, typeArrayList));
        gameData.setGameList(gson.fromJson(jsonGameList, typeArray));
        gameData.setRankCSGO(gson.fromJson(jsonCSGO, typeArray));
        gameData.setRankLOL(gson.fromJson(jsonLOL, typeArray));
        gameData.setRankR6(gson.fromJson(jsonR6, typeArray));
        gameData.setRankGTA(gson.fromJson(jsonGTA, typeArray));
        */
        gameData.setAllUserData(gson.fromJson(jsonAllUserData, typeSpecial));

    }

}
