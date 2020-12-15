package com.example.gameify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class gameData {

    private static Map<String, Map > userGameDataMap=new HashMap<>();
    private static ArrayList<Map> arrayList  = new ArrayList<>();

    public gameData() {
    }

    public static ArrayList<Map> getArrayList() {

        return arrayList;
    }

    public static void setArrayList(ArrayList<Map> arrayList) {
        gameData.arrayList = arrayList;
    }

    public static Map<String, Map > getUserGameDataMap() {
        return userGameDataMap;
    }

    public static void setUserGameDataMap(Map<String, Map> userGameDataMap) {
        gameData.userGameDataMap = userGameDataMap;
        arrayList.add(userGameDataMap);
    }
}
