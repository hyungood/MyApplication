package com.example.user.myapplication;

import java.util.List;

public class undoneCon {
    String message;
    List<undoneConL> data;
    String token;

    public String getMessage() {
        return message;
    }

    public List<undoneConL> getData() {
        return data;
    }
    public int getMySize(){
        return data.size();
    }
    public String getToken(){
        return token;
    }


}
