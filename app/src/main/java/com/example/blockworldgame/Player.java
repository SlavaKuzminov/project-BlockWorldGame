package com.example.blockworldgame;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Cell> occupiedCells;

    public Player(String name) {
        this.name = name;
        this.occupiedCells = new ArrayList<>();
    }

    public void occupyCell(Cell cell) {
        occupiedCells.add(cell);
        cell.setState(name.equals("Player") ? Cell.CellState.PLAYER : Cell.CellState.COMPUTER);
    }

    public int getOccupiedCellsCount() {
        return occupiedCells.size();
    }

    public List<Cell> getOccupiedCells() {
        return occupiedCells;
    }

    public String getName() {
        return name;
    }
}
