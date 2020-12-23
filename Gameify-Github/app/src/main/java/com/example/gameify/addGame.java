package com.example.gameify;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
    private Button but_save, but_update, but_reset;

    CSGO game_csgo = new CSGO();
    LOL game_lol = new LOL();
    R6 game_r6 = new R6();
    GTA game_gta = new GTA();
    String[] csgolist = game_csgo.getCsgo_rank();
    String[] lollist = game_lol.getLol_ranks();
    String[] r6list = game_r6.getR6_ranks();
    String[] gtalist = game_gta.getGta5_rank();
    private static String username;
    private static String age;


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
        but_reset = (Button) findViewById(R.id.but_reset);
        sp_CS.setEnabled(false);
        sp_LOL.setEnabled(false);
        sp_R6.setEnabled(false);
        sp_GTA.setEnabled(false);

        username = getIntent().getStringExtra("usernameToAddGame");
        age = getIntent().getStringExtra("ageToAddGame");

        adapter(sp_CS, csgolist);
        adapter(sp_LOL, lollist);
        adapter(sp_R6, r6list);
        adapter(sp_GTA, gtalist);

        loadData();

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


        but_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indexOfArraylist = allDataUsernameChecker(username);
                boolean status = checkUp(indexOfArraylist);
                if (status) {
                    saveData();
                    addGame.this.finish();
                } else {
                    Toast.makeText(addGame.this, "Please choose your rank/level for selected games..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        but_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indexOfArraylist = allDataUsernameChecker(username);
                boolean statusClean = statusClean(indexOfArraylist);
                if (statusClean) {
                    Toast.makeText(addGame.this, "Your game data is resetted..", Toast.LENGTH_SHORT).show();
                    saveData();
                    addGame.this.finish();
                }
            }
        });

        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indexOfArraylist = allDataUsernameChecker(username);
                checkYesorNo(indexOfArraylist);
            }
        });


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

    private boolean checkUp(int index) {
        boolean status = true;
        String getRankCSGO = gameData.getAllUserData().get(index).getrCsgo();
        String getRankLOL = gameData.getAllUserData().get(index).getrLol();
        String getRankR6 = gameData.getAllUserData().get(index).getrR6();
        String getRankGTA = gameData.getAllUserData().get(index).getrGta();
        gameData.getAllUserData().remove(index);
        if (cb_CS.isChecked()) {
            if (sp_CS.getSelectedItemPosition() != 0) {
                getRankCSGO = sp_CS.getSelectedItem().toString();
                status = true;
            } else {
                status = false;
            }
        }
        if (cb_LOL.isChecked()) {
            if (sp_LOL.getSelectedItemPosition() != 0) {
                getRankLOL = sp_LOL.getSelectedItem().toString();
                status = true;
            } else {
                status = false;
            }
        }
        if (cb_R6.isChecked()) {
            if (sp_R6.getSelectedItemPosition() != 0) {
                getRankR6 = sp_R6.getSelectedItem().toString();
                status = true;
            } else {
                status = false;
            }
        }
        if (cb_GTA.isChecked()) {
            if (sp_GTA.getSelectedItemPosition() != 0) {
                getRankGTA = sp_GTA.getSelectedItem().toString();
                status = true;
            } else {
                status = false;
            }
        }

        gameData gd = new gameData(username, age, getRankCSGO, getRankLOL, getRankR6, getRankGTA);
        gameData.getAllUserData().add(index, gd);
        return status;
    }

    private boolean statusClean(int index) {
        clearGameData(index);
        return true;
    }

    private void checkYesorNo(int index) {
        AlertDialog.Builder altdial = new AlertDialog.Builder(addGame.this);
        altdial.setMessage("If your choice is YES, this will reset your old data. To add/change data use UPDATE button.\nAre you sure?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean status = check(index);
                        if (status) {
                            saveData();
                            addGame.this.finish();
                        } else {
                            Toast.makeText(addGame.this, "Please choose your rank/level for selected games..", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = altdial.create();
        alertDialog.setTitle("Confirmation");
        alertDialog.show();
    }

    private boolean check(int index) {
        boolean status = true;
        String getRankCSGO = "";
        String getRankLOL = "";
        String getRankR6 = "";
        String getRankGTA = "";
        if (cb_CS.isChecked()) {
            if (sp_CS.getSelectedItemPosition() != 0) {
                getRankCSGO = sp_CS.getSelectedItem().toString();
                status = true;
            } else {
                status = false;
            }
        }
        if (cb_LOL.isChecked()) {
            if (sp_LOL.getSelectedItemPosition() != 0) {
                getRankLOL = sp_LOL.getSelectedItem().toString();
                status = true;
            } else {
                status = false;
            }
        }
        if (cb_R6.isChecked()) {
            if (sp_R6.getSelectedItemPosition() != 0) {
                getRankR6 = sp_R6.getSelectedItem().toString();
                status = true;
            } else {
                status = false;
            }
        }
        if (cb_GTA.isChecked()) {
            if (sp_GTA.getSelectedItemPosition() != 0) {
                getRankGTA = sp_GTA.getSelectedItem().toString();
                status = true;
            } else {
                status = false;
            }
        }

        gameData.getAllUserData().add(index,new gameData(username, age, getRankCSGO, getRankLOL, getRankR6, getRankGTA));
        return status;

    }


    private void adapter(Spinner sp, String[] templist) {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, templist);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(aa);
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userAccount.getUserAccountArrayList());
        editor.putString("userlist",json);
        String jsonAllUserData = gson.toJson(gameData.getAllUserData());
        editor.putString("jsonAllUserData", jsonAllUserData);
        editor.apply();

    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userlist",null);
        Type type = new TypeToken<ArrayList<userAccount>>() {}.getType();
        userAccount.setUserAccountArrayList(gson.fromJson(json, type));
        String jsonAllUserData = sharedPreferences.getString("jsonAllUserData", null);
        Type typeSpecial = new TypeToken<ArrayList<gameData>>() {
        }.getType();
        gameData.setAllUserData(gson.fromJson(jsonAllUserData, typeSpecial));
    }

    private void clearGameData(int indexOfUser){
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        ArrayList<gameData> temp = gameData.getAllUserData();
        temp.remove(indexOfUser);
        String jsonTemp = gson.toJson(temp);
        editor.putString("jsonAllUserData", jsonTemp);
        editor.apply();
    }
}