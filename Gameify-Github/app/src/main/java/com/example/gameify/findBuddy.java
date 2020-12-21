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
    private TextView tv_userName, tv_csgoRank, tv_lolRank, tv_r6Rank, tv_gtaRank, tv_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_buddy);

        loadGameData();
        String username = getIntent().getStringExtra("usernameToGameRank");
        int indexUsername = allDataUsernameChecker(username);
        tv_userName = (TextView) findViewById(R.id.tv_userName);
        tv_userName.setText(tv_userName.getText() + username);
        tv_csgoRank = (TextView) findViewById(R.id.tv_csgoRank);
        tv_lolRank = (TextView) findViewById(R.id.tv_lolRank);
        tv_r6Rank = (TextView) findViewById(R.id.tv_r6Rank);
        tv_gtaRank = (TextView) findViewById(R.id.tv_gtaRank);
        tv_age = (TextView) findViewById(R.id.tv_age);
        String age = gameData.getAllUserData().get(indexUsername).getAge();
        tv_age.setText(tv_age.getText() + age);

        firstEntry(indexUsername);


    }

    private void firstEntry(int index) {

        String csgoRank = gameData.getAllUserData().get(index).getrCsgo();
        String lolRank = gameData.getAllUserData().get(index).getrLol();
        String r6Rank = gameData.getAllUserData().get(index).getrR6();
        String gtaRank = gameData.getAllUserData().get(index).getrGta();

        if (!csgoRank.equalsIgnoreCase("")) {
            tv_csgoRank.setVisibility(View.VISIBLE);
            tv_csgoRank.setText("CS-GO Rank : " + csgoRank);
        } else {
            tv_csgoRank.setVisibility(View.GONE);
        }
        if (!lolRank.equalsIgnoreCase("")) {
            tv_lolRank.setVisibility(View.VISIBLE);
            tv_lolRank.setText("LOL Rank : " + lolRank);
        } else {
            tv_lolRank.setVisibility(View.GONE);
        }
        if (!r6Rank.equalsIgnoreCase("")) {
            tv_r6Rank.setVisibility(View.VISIBLE);
            tv_r6Rank.setText("R6 Rank : " + r6Rank);
        } else {
            tv_r6Rank.setVisibility(View.GONE);
        }
        if (!gtaRank.equalsIgnoreCase("")) {
            tv_gtaRank.setVisibility(View.VISIBLE);
            tv_gtaRank.setText("GTA Rank : " + gtaRank);
        } else {
            tv_gtaRank.setVisibility(View.GONE);
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