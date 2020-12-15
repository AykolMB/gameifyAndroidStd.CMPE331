package com.example.gameify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class addGame extends AppCompatActivity {

    private Spinner sp_CS, sp_LOL, sp_R6, sp_GTA;
    private CheckBox cb_CS, cb_LOL, cb_R6, cb_GTA;
    private Button but_save, but_update;

    CSGO game_csgo = new CSGO();
    LOL game_lol = new LOL();
    R6 game_r6 = new R6();
    GTA game_gta = new GTA();
    String[] csgolist = game_csgo.getCsgo_rank();
    String[] lollist = game_lol.getLol_ranks();
    String[] r6list = game_r6.getR6_ranks();
    String[] gtalist = game_gta.getGta5_rank();
    Map<String, String> gameDataMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        sp_CS = (Spinner) findViewById(R.id.sp_CSGO);
        sp_LOL = (Spinner) findViewById(R.id.sp_LOL);
        sp_R6 = (Spinner) findViewById(R.id.sp_R6);
        sp_GTA = (Spinner) findViewById(R.id.sp_GTA);
        cb_CS = (CheckBox) findViewById(R.id.cb_CSGO);
        cb_LOL = (CheckBox) findViewById(R.id.cb_LOL);
        cb_R6 = (CheckBox) findViewById(R.id.cb_R6SIEGE);
        cb_GTA = (CheckBox) findViewById(R.id.cb_GTA);
        but_save = (Button) findViewById(R.id.but_save);
        but_update = (Button) findViewById(R.id.but_update);
        sp_CS.setEnabled(false);
        sp_LOL.setEnabled(false);
        sp_R6.setEnabled(false);
        sp_GTA.setEnabled(false);

        String username = getIntent().getStringExtra("usernameToAddGame");

        int index = firstCheckUsername(username);

        adapter(sp_CS, csgolist);
        adapter(sp_LOL, lollist);
        adapter(sp_R6, r6list);
        adapter(sp_GTA, gtalist);

        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean status = true;
                if (cb_CS.isChecked()) {
                    if (sp_CS.getSelectedItemPosition() != 0) {
                        String getRankCSGO = sp_CS.getSelectedItem().toString();
                        gameData.putItemArrayList("CS-GO", index , getRankCSGO);
                        status = true;
                    } else {
                        Toast.makeText(addGame.this, "Please choose your rank/level", Toast.LENGTH_SHORT).show();
                        status = false;
                    }
                }else{
                    gameData.putItemArrayList("CS-GO", index , "");
                }
                if (cb_LOL.isChecked()) {
                    if (sp_LOL.getSelectedItemPosition() != 0) {
                        String getRankLOL = sp_LOL.getSelectedItem().toString();
                        gameData.putItemArrayList("LOL", index , getRankLOL);
                        status = true;
                    } else {
                        Toast.makeText(addGame.this, "Please choose your rank/level", Toast.LENGTH_SHORT).show();
                        status = false;
                    }
                }else {
                    gameData.putItemArrayList("LOL", index , "");
                }
                if (cb_R6.isChecked()) {
                    if (sp_R6.getSelectedItemPosition() != 0) {
                        String getRankR6 = sp_R6.getSelectedItem().toString();
                        gameData.putItemArrayList("R6", index , getRankR6);
                        status = true;
                    } else {
                        Toast.makeText(addGame.this, "Please choose your rank/level", Toast.LENGTH_SHORT).show();
                        status = false;
                    }
                }else{
                    gameData.putItemArrayList("R6", index , "");
                }
                if (cb_GTA.isChecked()) {
                    if (sp_GTA.getSelectedItemPosition() != 0) {
                        String getRankGTA = sp_GTA.getSelectedItem().toString();
                        gameData.putItemArrayList("GTA", index , getRankGTA);
                        status = true;
                    } else {
                        Toast.makeText(addGame.this, "Please choose your rank/level", Toast.LENGTH_SHORT).show();
                        status = false;
                    }
                }else{
                    gameData.putItemArrayList("GTA", index , "");
                }
                addGame.this.finish();

            }

        });
        // Checkbox and spinner
        {
            cb_CS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true) {
                        sp_CS.setEnabled(true);
                        sp_CS.setClickable(true);
                    } else {
                        sp_CS.setEnabled(false);
                        sp_CS.setClickable(false);
                        adapter(sp_CS, csgolist);
                    }
                }
            });

            cb_LOL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true) {
                        sp_LOL.setEnabled(true);
                        sp_LOL.setClickable(true);
                    } else {
                        sp_LOL.setEnabled(false);
                        sp_LOL.setClickable(false);
                        adapter(sp_LOL, lollist);
                    }
                }
            });

            cb_R6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true) {
                        sp_R6.setEnabled(true);
                        sp_R6.setClickable(true);
                    } else {
                        sp_R6.setEnabled(false);
                        sp_R6.setClickable(false);
                        adapter(sp_R6, r6list);
                    }
                }
            });

            cb_GTA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true) {
                        sp_GTA.setEnabled(true);
                        sp_GTA.setClickable(true);
                    } else {
                        sp_GTA.setEnabled(false);
                        sp_GTA.setClickable(false);
                        adapter(sp_GTA, gtalist);
                    }
                }
            });

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


    private void adapter(Spinner sp, String[] templist) {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, templist);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(aa);
    }

}