package com.example.gameify;

public class R6 extends Game {

    private String[] r6_ranks = {"Game Ranks", "Copper 5", "Copper 4", "Copper 3", "Copper 2", "Copper 1",
                            "Bronze 5", "Bronze 4", "Bronze 3", "Bronze 2", "Bronze 1",
                            "Silver 5", "Silver 4", "Silver 3", "Silver 2", "Silver 1",
                            "Gold 3", "Gold 2", "Gold 1",
                            "Platinum 3", "Platinum 2", "Platinum 1",
                            "Diamond", "Champions"};

    @Override
    public String getGameName() {
        return "Tom Clancy's Rainbow Six Siege";
    }

    public String[] getR6_ranks() {
        return r6_ranks;
    }

    public void setR6_ranks(String[] r6_ranks) {
        this.r6_ranks = r6_ranks;
    }
}
