package com.example.blockworldgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class GameView extends View {
    private Cell[][] grid;
    private Paint paint;

    public GameView(Context context) {
        super(context);
        grid = new Cell[10][10];
        paint = new Paint();
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cellSize = getWidth() / 10;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Cell cell = grid[i][j];
                switch (cell.getState()) {
                    case EMPTY:
                        paint.setColor(Color.LTGRAY);
                        break;
                    case PLAYER:
                        paint.setColor(Color.BLUE);
                        break;
                    case COMPUTER:
                        paint.setColor(Color.RED);
                        break;
                    case SPECIAL:
                        paint.setColor(Color.GREEN);
                        break;
                }
                canvas.drawRect(j * cellSize, i * cellSize, (j + 1) * cellSize, (i + 1) * cellSize, paint);
            }
        }
    }

    public Cell getCell(int x, int y) {
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            return grid[y][x];
        }
        return null; // Возвращаем null, если координаты вне границ
    }

    public void updateCell(int x, int y, Cell.CellState state) {
        grid[x][y].setState(state);
        invalidate(); // Перерисовываем вид
    }
}
