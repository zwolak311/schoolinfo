package com.schoolInfo.bartosz.schoolinfo.Rest;


import java.util.ArrayList;

public class ArrayIdAndToken {


    ArrayList<Integer> id = new ArrayList<>();
    String token;


    public ArrayList<Integer> getId() {
        return id;
    }

    public void setId(ArrayList<Integer> id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
