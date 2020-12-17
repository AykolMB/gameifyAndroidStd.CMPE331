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
                loadGameData();
                String out = "";
                int index = firstCheckUsername(username_bf);
                String csgoRank = getRank(0,index);
                String lolRank = getRank(1,index);
                String r6Rank = getRank(2,index);
                String gtaRank = getRank(3,index);
                out = outputSet(csgoRank, lolRank, r6Rank, gtaRank);
                tv_gamedata.setText(out);

            }
        });

}

    private String getRank(int i, int index) {
        String[] temp;
        String returnStatement;
        if (i == 0){
            temp = gameData.getRankCSGO();
            returnStatement = temp[index];
        }else if (i == 1){
            temp = gameData.getRankLOL();
            returnStatement = temp[index];
        }else if (i == 2){
            temp = gameData.getRankR6();
            returnStatement = temp[index];
        }else{
            temp = gameData.getRankGTA();
            returnStatement = temp[index];
        }
        return  returnStatement;
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

    private int firstCheckUsername(String username) {
        int index = 0;
        ArrayList<String> temp = gameData.getUsernameArrayList();
        for (int i = 0 ; i<temp.size() ; i++){
            if (temp.get(i).equalsIgnoreCase(username)){
                index = i;
            }
        }
        return index;
    }

    private void saveGameData() {
        SharedPreferences sharepref = getSharedPreferences("sharepref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharepref.edit();
        Gson gson = new Gson();
        String jsonUsername = gson.toJson(gameData.getUsernameArrayList());
        String jsonGameList = gson.toJson(gameData.getGameList());
        String jsonCSGO = gson.toJson(gameData.getRankCSGO());
        String jsonLOL = gson.toJson(gameData.getRankLOL());
        String jsonR6 = gson.toJson(gameData.getRankR6());
        String jsonGTA = gson.toJson(gameData.getRankGTA());
        editor.putString("usernameArrayList", jsonUsername);
        editor.putString("gameList", jsonGameList);
        editor.putString("csgoRank", jsonCSGO);
        editor.putString("lolRank", jsonLOL);
        editor.putString("r6Rank", jsonR6);
        editor.putString("gtaRank", jsonGTA);
        editor.apply();

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
    }
    
}
