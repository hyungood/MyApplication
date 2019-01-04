package com.example.user.myapplication;

public class p_data {
    private String id;
    private String name;
    private String phone;

    public p_data(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
