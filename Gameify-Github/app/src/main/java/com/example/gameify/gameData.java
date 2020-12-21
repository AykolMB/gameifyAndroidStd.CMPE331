package com.example.gameify;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class gameData {

    private String rCsgo;
    private String rLol;
    private String rR6;
    private String rGta;
    private String username;
    private String age;
    public static ArrayList<gameData> allUserData = new ArrayList<>();
    private static ArrayList<String> usernameArrayList = new ArrayList<>();

    public gameData(String username, String age, String rCsgo, String rLol, String rR6, String rGta) {
        this.rCsgo = rCsgo;
        this.rLol = rLol;
        this.rR6 = rR6;
        this.rGta = rGta;
        this.username = username;
        this.age = age;
    }

    public static ArrayList<gameData> getAllUserData() {
        return allUserData;
    }

    public static void setAllUserData(ArrayList<gameData> allUserData) {
        gameData.allUserData = allUserData;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getrCsgo() {
        return rCsgo;
    }

    public void setrCsgo(String rCsgo) {
        this.rCsgo = rCsgo;
    }

    public String getrLol() {
        return rLol;
    }

    public void setrLol(String rLol) {
        this.rLol = rLol;
    }

    public String getrR6() {
        return rR6;
    }

    public void setrR6(String rR6) {
        this.rR6 = rR6;
    }

    public String getrGta() {
        return rGta;
    }

    public void setrGta(String rGta) {
        this.rGta = rGta;
    }

    public gameData() {
    }

    public static void putUserArrayList(String username) {
        usernameArrayList.add(username);
    }

    public static ArrayList<String> getUsernameArrayList() {
        return usernameArrayList;
    }

    public static void setUsernameArrayList(ArrayList<String> usernameArrayList) {
        gameData.usernameArrayList = usernameArrayList;
    }

}
