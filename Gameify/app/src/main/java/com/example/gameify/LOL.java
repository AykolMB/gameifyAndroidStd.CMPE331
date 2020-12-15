package com.example.gameify;

public class LOL extends Game{

    private String[] lol_ranks = {"Game Ranks","Iron 4", "Iron 3","Iron 2","Iron 1",
                            "Bronze 4", "Bronze 3", "Bronze 2", "Bronze 1",
                            "Silver 4", "Silver 3", "Silver 2", "Silver 1",
                            "Gold 4", "Gold 3", "Gold 2", "Gold 1",
                            "Platinum 4", "Platinum 3", "Platinum 2", "Platinum 1",
                            "Diamond 4", "Diamond 3", "Diamond 2", "Diamond 1",
                            "Master", "GrandMaster", "Challenger"};


    @Override
    public String getGameName() {
        return "League of Legends";
    }

    public String[] getLol_ranks() {
        return lol_ranks;
    }

    public void setLol_ranks(String[] lol_ranks) {
        this.lol_ranks = lol_ranks;
    }
}
