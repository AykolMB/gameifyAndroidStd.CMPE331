package com.example.gameify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class findBuddy extends AppCompatActivity {
    private TextView tv_userName, tv_csgoRank, tv_lolRank, tv_r6Rank, tv_gtaRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_buddy);

        loadGameData();
        String username = getIntent().getStringExtra("usernameToGameRank");
        int indexUsername = firstCheckUsername(username);
        tv_userName = (TextView) findViewById(R.id.tv_userName);
        tv_userName.setText(tv_userName.getText() + username);
        tv_csgoRank = (TextView) findViewById(R.id.tv_csgoRank);
        tv_lolRank = (TextView) findViewById(R.id.tv_lolRank);
        tv_r6Rank = (TextView) findViewById(R.id.tv_r6Rank);
        tv_gtaRank = (TextView) findViewById(R.id.tv_gtaRank);

        firstEntry(indexUsername);


    }

    private void firstEntry(int index) {
        String csgoRank = getRank(0,index);
        String lolRank = getRank(1,index);
        String r6Rank = getRank(2,index);
        String gtaRank = getRank(3,index);

        if (!csgoRank.equalsIgnoreCase("") ) {
            tv_csgoRank.setVisibility(View.VISIBLE);
            tv_csgoRank.setText("CS-GO Rank : " + csgoRank);
        }else{
            tv_csgoRank.setVisibility(View.GONE);
        }
        if (!lolRank.equalsIgnoreCase("")){
            tv_lolRank.setVisibility(View.VISIBLE);
            tv_lolRank.setText("LOL Rank : " + lolRank);
        }else{
            tv_lolRank.setVisibility(View.GONE);
        }
        if (!r6Rank.equalsIgnoreCase("")){
            tv_r6Rank.setVisibility(View.VISIBLE);
            tv_r6Rank.setText("R6 Rank : " + r6Rank);
        }else {
            tv_r6Rank.setVisibility(View.GONE);
        }
        if (!gtaRank.equalsIgnoreCase("")){
            tv_gtaRank.setVisibility(View.VISIBLE);
            tv_gtaRank.setText("GTA Rank : " + gtaRank);
        }else {
            tv_gtaRank.setVisibility(View.GONE);
        }

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
        if (returnStatement == null){
            return "null";
        }
        return  returnStatement;
    }

    private int firstCheckUsername(String username) {
        int index = 0;
        ArrayList<String> uAtemp = gameData.getUsernameArrayList();
        for (int i = 0; i < uAtemp.size(); i++) {
            if (uAtemp.get(i).equalsIgnoreCase(username)) {
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