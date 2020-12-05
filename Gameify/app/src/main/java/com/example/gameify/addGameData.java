package com.example.gameify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class addGameData extends AppCompatActivity {

    public static String output_gamedata= "";

    private Spinner sp_gamedata;
    private Spinner sp_csgorankdata, sp_gta5rankdata;
    private TextView tv_total_gamedata;
    private Button but_adddata_button;
    private String[] game_array = {"", "CS-GO", "GTA5", "PUBG", "LOL", "TOM CLANCY'S RAINBOW SIX SIEGE", "VALORANT"};
    private String[] csgo_rank = {"", "Silver 1", "Silver 2", "Silver 3", "Silver 4", "Silver Elite", "Silver Elite Master",
                          "Gold Nova 1", "Gold Nova 2", "Gold Nova 3", "Gold Nova Master",
                          "Master Guardian 1", "Master Guardian 2", "Master Guardian Elite", "Distinguished Master Guardian",
                          "Legendary Eagle", "Legendary Eagle Master", "Supreme Master First Class", "The Global Elite"};
    private String[] gta5_rank = {"", "Lvl 1-25", "Lvl 26-50", "Lvl 51-75", "Lvl 76-100", "Lvl 101-125", "Lvl 126-135", "Lvl 136-8000"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game_data);

        sp_gamedata = (Spinner) findViewById(R.id.sp_gamedata);
        sp_csgorankdata = (Spinner) findViewById(R.id.sp_csgorankdata);
        sp_gta5rankdata = (Spinner) findViewById(R.id.sp_gta5rankdata);
        tv_total_gamedata = (TextView) findViewById(R.id.tv_total_gamedata);
        but_adddata_button = (Button) findViewById(R.id.but_adddata_button);

        ArrayAdapter aa_game = new ArrayAdapter(this, android.R.layout.simple_spinner_item, game_array);
        aa_game.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gamedata.setAdapter(aa_game);
        ArrayAdapter aa_csgorank = new ArrayAdapter(this, android.R.layout.simple_spinner_item, csgo_rank);
        aa_game.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_csgorankdata.setAdapter(aa_csgorank);
        ArrayAdapter aa_gta5rank = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gta5_rank);
        aa_game.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gta5rankdata.setAdapter(aa_gta5rank);

        but_adddata_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (output_gamedata.isEmpty()){
                    Toast.makeText(addGameData.this, "Please make sure, you make selections!!", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), afterLoginMainPage.class);
                    intent.putExtra("gamedata", output_gamedata);
                    startActivity(intent);
                    addGameData.this.finish();
                }
            }
        });

        sp_gamedata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                output_gamedata = parent.getItemAtPosition(position).toString();
                if (position == 1){
                    setVisibilityTrue(sp_csgorankdata);
                    setVisibilityFalse(sp_gta5rankdata);
                    sp_csgorankdata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            output_gamedata = output_gamedata + "\t" + (parent.getItemAtPosition(position).toString());
                            tv_total_gamedata.setText(output_gamedata);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                else if (position == 2){
                    setVisibilityTrue(sp_gta5rankdata);
                    setVisibilityFalse(sp_csgorankdata);
                    sp_gta5rankdata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            output_gamedata = output_gamedata + "\t" + (parent.getItemAtPosition(position).toString());
                            tv_total_gamedata.setText(output_gamedata);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setVisibilityFalse(Spinner selected_sp) {
        selected_sp.setVisibility(View.GONE);
    }

    private void setVisibilityTrue(Spinner selected_sp) {
        selected_sp.setVisibility(View.VISIBLE);
    }
}