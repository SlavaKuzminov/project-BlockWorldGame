package com.example.blockworldgame;

public class Cell {
    public enum CellState {
        EMPTY, PLAYER, COMPUTER, SPECIAL
    }

    private CellState state;

    public Cell() {
        this.state = CellState.EMPTY;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }
}
