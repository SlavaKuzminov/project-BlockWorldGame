package com.example.blockworldgame;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private TextView playerScoreTextView;
    private TextView computerScoreTextView;
    private Button startButton;

    private int[][] grid = new int[10][10];
    private int playerScore = 0;
    private int computerScore = 0;
    private boolean playerTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        playerScoreTextView = findViewById(R.id.playerScore);
        computerScoreTextView = findViewById(R.id.computerScore);
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(v -> startNewGame());
        createGrid();
    }

    private void createGrid() {
        gridLayout.removeAllViews();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                final int x = i;
                final int y = j;
                View cell = new View(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 110; // Ширина
                params.height = 110; // Высота
                params.rowSpec = GridLayout.spec(i, 1);
                params.columnSpec = GridLayout.spec(j, 1);
                cell.setLayoutParams(params);
                cell.setBackgroundColor(Color.LTGRAY);
                cell.setOnClickListener(v -> onCellClicked(x, y));
                gridLayout.addView(cell);
                grid[i][j] = 0; // 0 = пусто, 1 = игрок, 2 = компьютер
            }
        }
    }

    private void startNewGame() {
        playerScore = 0;
        computerScore = 0;
        playerTurn = true;
        createGrid();
        updateScores();
        grid[0][0] = 1;
        grid[9][9] = 2;
        updateCell(0, 0, Color.BLUE);
        updateCell(9, 9, Color.RED);
    }
    private void onCellClicked(int x, int y) {
        if (playerTurn && grid[x][y] == 0) {
            grid[x][y] = 1; // Игрок захватывает ячейку
            updateCell(x, y, Color.BLUE);
            playerScore++;
            playerTurn = false;
            updateScores();
            checkGameOver(); // Проверяем, закончилась ли игра после хода игрока
            computerTurn();
        }
    }


    // Метод для захвата соседних клеток
    private void captureAdjacentCells(int x, int y, int player) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (Math.abs(dx) + Math.abs(dy) == 1) { // Только горизонтально или вертикально
                    int newX = x + dx;
                    int newY = y + dy;
                    if (isValidMove(newX, newY) && player == 1) { // Проверяем только для игрока
                        grid[newX][newY] = 1; // Захватываем клетку
                        updateCell(newX, newY, Color.BLUE);
                        playerScore++;
                        // Рекурсивно захватываем соседние клетки
                        captureAdjacentCells(newX, newY, player);
                    }
                }
            }
        }
    }
    private void computerTurn() {
        // Логика для хода компьютера
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(10);
            y = random.nextInt(10);
        } while (grid[x][y] != 0);

        // Компьютер захватывает ячейку
        grid[x][y] = 2;
        updateCell(x, y, Color.RED);
        computerScore++;
        updateScores();

        checkGameOver(); // Проверяем, закончилась ли игра после хода компьютера

        // Передаём ход обратно к игроку
        playerTurn = true;
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10; // Проверка на границы поля
    }

    private void updateCell(int x, int y, int color) {
        View cell = gridLayout.getChildAt(x * 10 + y); // Получаем ячейку по индексу
        if (cell != null) {
            cell.setBackgroundColor(color);
        }
    }

    private void updateScores() {
        playerScoreTextView.setText("Player Score: " + playerScore);
        computerScoreTextView.setText("Computer Score: " + computerScore);
    }
    private void checkGameOver() {
        boolean gameOver = true;

        // Проверяем, есть ли еще доступные клетки
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (grid[i][j] == 0) {
                    if (canCapture(i, j)) {
                        gameOver = false;
                        break;
                    }
                }
            }
            if (!gameOver) break;
        }

        if (gameOver) {
            showGameOverDialog();
        }
    }

    // Метод для проверки, можно ли захватить клетку
    private boolean canCapture(int x, int y) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (Math.abs(dx) + Math.abs(dy) == 1) {
                    int newX = x + dx;
                    int newY = y + dy;
                    if (isValidMove(newX, newY) && (grid[newX][newY] == 1 || grid[newX][newY] == 2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void showGameOverDialog() {
        String winner;
        if (playerScore > computerScore) {
            winner = "Player Wins!";
        } else if (playerScore < computerScore) {
            winner = "Computer Wins!";
        } else {
            winner = "It's a Draw!";
        }

        String message = winner + "\nPlayer Score: " + playerScore + "\nComputer Score: " + computerScore;

        new android.app.AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> startNewGame())
                .setCancelable(false)
                .show();
    }
}