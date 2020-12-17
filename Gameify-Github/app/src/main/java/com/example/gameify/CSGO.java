package com.example.gameify;

import java.util.Arrays;

public class CSGO extends Game{

    private String[] csgo_rank = {"Game Ranks", "Silver 1", "Silver 2", "Silver 3", "Silver 4", "Silver Elite", "Silver Elite Master",
            "Gold Nova 1", "Gold Nova 2", "Gold Nova 3", "Gold Nova Master",
            "Master Guardian 1", "Master Guardian 2", "Master Guardian Elite", "Distinguished Master Guardian",
            "Legendary Eagle", "Legendary Eagle Master", "Supreme Master First Class", "The Global Elite"};

    @Override
    public String getGameName() {
        return "Counter Strike - Global Offensive";
    }

    @Override
    public void setGameRank(String gameRank) {
        super.setGameRank(gameRank);
    }

    @Override
    public String getGameRank() {
        return super.getGameRank();
    }

    public String[] getCsgo_rank() {
        return csgo_rank;
    }

    public void setCsgo_rank(String[] csgo_rank) {
        this.csgo_rank = csgo_rank;
    }

}
