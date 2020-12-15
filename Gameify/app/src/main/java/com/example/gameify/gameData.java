package com.example.gameify;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class gameData {

    private static ArrayList<String> usernameArrayList = new ArrayList<>();
    private static String[] gameList = {"CS-GO", "LOL", "R6", "GTA"};
    private static String[] rankCSGO = new String[150];
    private static String[] rankLOL = new String[150];
    private static String[] rankR6 = new String[150];
    private static String[] rankGTA = new String[150];


    public gameData() {
    }

    public static void putUserArrayList(String username) {
        usernameArrayList.add(username);
    }

    public static void putItemArrayList(String gameName, int index, String rank) {
        if (gameName.equalsIgnoreCase(gameList[0])) {
            rankCSGO[index] = rank;
        } else if (gameName.equalsIgnoreCase(gameList[1])) {
            rankLOL[index] = rank;
        } else if (gameName.equalsIgnoreCase(gameList[2])) {
            rankR6[index] = rank;
        } else if (gameName.equalsIgnoreCase(gameList[3])) {
            rankGTA[index] = rank;
        }
    }

    public static ArrayList<String> getUsernameArrayList() {
        return usernameArrayList;
    }

    public static void setUsernameArrayList(ArrayList<String> usernameArrayList) {
        gameData.usernameArrayList = usernameArrayList;
    }

    public static String[] getGameList() {
        return gameList;
    }

    public static void setGameList(String[] gameList) {
        gameData.gameList = gameList;
    }

    public static String[] getRankCSGO() {
        return rankCSGO;
    }

    public static void setRankCSGO(String[] rankCSGO) {
        gameData.rankCSGO = rankCSGO;
    }

    public static String[] getRankLOL() {
        return rankLOL;
    }

    public static void setRankLOL(String[] rankLOL) {
        gameData.rankLOL = rankLOL;
    }

    public static String[] getRankR6() {
        return rankR6;
    }

    public static void setRankR6(String[] rankR6) {
        gameData.rankR6 = rankR6;
    }

    public static String[] getRankGTA() {
        return rankGTA;
    }

    public static void setRankGTA(String[] rankGTA) {
        gameData.rankGTA = rankGTA;
    }
}
