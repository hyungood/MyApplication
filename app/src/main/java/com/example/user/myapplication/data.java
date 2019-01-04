package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;

public class data {
    private String id;
    private String name;
    private int pwd;
    private String phone;
    private Date birth;
    private int num_f;

    public data(){ }

    public data(String id, String name, int pwd, String phone, Date birth, int num_f) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.phone = phone;
        this.birth = birth;
        this.num_f = num_f;
    }

    public String getdata() {return id; }
    public String getname() {return name;}
    public int pwd() {return pwd;}
    public String getphone() {return phone; }
    public Date getBirth() {return birth;}
    public int getNum_f() {return num_f;}

}