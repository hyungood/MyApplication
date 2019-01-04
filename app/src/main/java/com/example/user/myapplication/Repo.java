package com.example.user.myapplication;

import java.util.List;

public class Repo {
    String message;
    List<Item> data;

    public String getMessage() { return message; }
    public List<Item> getData() {
        return data;
    }
    public int getSize(){
        return data.size();
    }

}

