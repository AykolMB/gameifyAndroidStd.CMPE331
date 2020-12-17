package com.example.gameify;

public class GTA extends Game{

    public GTA() {
    }

    private String[] gta5_rank = {"Game Levels", "Lvl 1-25", "Lvl 26-50", "Lvl 51-75", "Lvl 76-100", "Lvl 101-125", "Lvl 126-135", "Lvl 136-8000"};


    @Override
    public String getGameName() {
        return "GTA 5";
    }

    public String[] getGta5_rank() {
        return gta5_rank;
    }

    public void setGta5_rank(String[] gta5_rank) {
        this.gta5_rank = gta5_rank;
    }
}
