package com.example.user.myapplication;

public class s_data {
    private int puzzle;
    private int tetris;
    private int shape;
    private int ordering;

    public s_data(int puzzle, int tetris, int shape, int ordering) {
        this.puzzle = puzzle;
        this.tetris = tetris;
        this.shape = shape;
        this.ordering = ordering;
    }

    public int getPuzzle() {
        return puzzle;
    }

    public int getTetris() {
        return tetris;
    }

    public int getShape() {
        return shape;
    }

    public int getOrdering() {
        return ordering;
    }
}
