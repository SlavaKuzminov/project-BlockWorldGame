package com.example.blockworldgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean isPlaying;
    private Paint paint;
    private List<Block> blocks;
    private Player player;

    public GameView(Context context) {
        super(context);
        paint = new Paint();
        blocks = new ArrayList<>();
        player = new Player();
        generateInitialBlocks(); // Генерация начальных блоков
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        player.update(); // Update the player's state

        // Generate new blocks
        generateBlocks();

        // Check for collisions
        checkCollisions();

        // Update the game state
        if (player.getY() > getHeight()) {
            // Game over
            isPlaying = false;
        }
    }

    private void generateBlocks() {
        // Generate new blocks at random positions
        if (blocks.size() < 10) {
            float x = (float) (Math.random() * (getWidth() - 100));
            float y = (float) (Math.random() * (getHeight() - 200));
            float width = 100;
            float height = 20;
            int color = Color.RED;
            blocks.add(new Block(x, y, width, height, color));
        }
    }

    private void checkCollisions() {
        // Check for collisions between the player and blocks
        for (Block block : blocks) {
            if (isColliding(player, block)) {
                // Collision detected
                player.setY(block.getY() - player.getHeight());
                player.setVelocityY(0);
                break;
            }
        }
    }

    private boolean isColliding(Player player, Block block) {
        // Check for collision between the player and block
        return player.getX() < block.getX() + block.getWidth() &&
                player.getX() + player.getWidth() > block.getX() &&
                player.getY() < block.getY() + block.getHeight() &&
                player.getY() + player.getHeight() > block.getY();
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.WHITE); // Фон

            // Отрисовка блоков
            for (Block block : blocks) {
                block.draw(canvas, paint);
            }

            // Отрисовка игрока
            player.draw(canvas, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            thread.sleep(17); // Около 60 FPS
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    private void generateInitialBlocks() {
        // Генерация начальных блоков
        for (int i = 0; i < 5; i++) {
            float x = (float) (Math.random() * (getWidth() - 100));
            float y = 500 - (i * 100); // Расположение блоков по Y
            float width = 100;
            float height = 20;
            int color = Color.RED;
            blocks.add(new Block(x, y, width, height, color));
        }
    }

    private void generateBlocks() {
        // Генерация новых блоков по мере продвижения игрока
        if (blocks.size() < 10) { // Ограничиваем количество блоков
            float x = (float) (Math.random() * (getWidth() - 100));
            float y = (float) (Math.random() * (getHeight() - 200)); // Случайная позиция по Y
            float width = 100;
            float height = 20;
            int color = Color.RED;
            blocks.add(new Block(x, y, width, height, color));
        }
    }

    /*
    private void checkCollisions() {
        // Проверка столкновений между игроком и блоками
        for (Block block : blocks) {
            if (isColliding(player, block)) {
                // Логика столкновения (например, предотвратить дальнейшее падение игрока)
                player.setY(block.getY() - player.getHeight()); // Устанавливаем игрока на верх блока
                player.setVelocityY(0); // Сбрасываем вертикальную скорость
                break; // Прерываем цикл, если столкновение произошло
            }
        }
    }

    private boolean isColliding(Player player, Block block) {
        // Проверка на столкновение
        return player.getX() < block.getX() + block.getWidth() &&
                player.getX() + player.getWidth() > block.getX() &&
                player.getY() < block.getY() + block.getHeight() &&
                player.getY() + player.getHeight() > block.getY();
    }*/
}