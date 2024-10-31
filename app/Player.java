package com.example.blockworldgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player {
    private float x, y; // Позиция игрока
    private float width, height; // Размеры игрока
    private float velocityY; // Вертикальная скорость
    private static final float GRAVITY = 0.5f; // Сила гравитации
    private static final float JUMP_STRENGTH = -15f; // Сила прыжка

    public Player() {
        x = 100; // Начальная позиция по X
        y = 500; // Начальная позиция по Y
        width = 50; // Ширина игрока
        height = 50; // Высота игрока
        velocityY = 0; // Начальная вертикальная скорость
    }

    public void update() {
        // Обновление позиции игрока
        y += velocityY; // Обновляем позицию по Y
        velocityY += GRAVITY; // Применяем гравитацию

        // Проверка на "землю"
        if (y > 500) { // Предположим, что 500 - это уровень земли
            y = 500; // Устанавливаем игрока на уровень земли
            velocityY = 0; // Сбрасываем скорость
        }
    }

    public void jump() {
        // Метод для прыжка
        if (y >= 500) { // Проверка, что игрок на земле
            velocityY = JUMP_STRENGTH; // Применяем силу прыжка
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        // Отрисовка игрока
        paint.setColor(Color.BLUE); // Цвет игрока
        canvas.drawRect(x, y, x + width, y + height, paint); // Рисуем прямоугольник
    }
}