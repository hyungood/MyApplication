package com.example.user.myapplication;
public class Prom {
    String name;
    int score;
    String content;
    int suc;

//    public Prom(Long id, String gameId, int score, String content, int suc) {
//        this.id = id;
//        this.gameId = gameId;
//        this.score = score;
//        this.content = content;
//        this.suc = suc;
//    }



    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getContent() {
        return content;
    }

    public int getSuc() {
        return suc;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSuc(int suc) {
        this.suc = suc;
    }

}
