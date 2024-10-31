package com.example.blockworldgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Block {
    private float x, y; // Позиция блока
    private float width, height; // Размеры блока
    private int color; // Цвет блока

    public Block(float x, float y, float width, float height, int color) {
        this.x = x; // Устанавливаем позицию по X
        this.y = y; // Устанавливаем позицию по Y
        this.width = width; // Устанавливаем ширину блока
        this.height = height; // Устанавливаем высоту блока
        this.color = color; // Устанавливаем цвет блока
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color); // Устанавливаем цвет для рисования
        canvas.drawRect(x, y, x + width, y + height, paint); // Рисуем прямоугольник
    }

    // Метод для получения позиции блока по Y
    public float getY() {
        return y;
    }

    // Метод для получения позиции блока по X
    public float getX() {
        return x;
    }

    // Метод для получения ширины блока
    public float getWidth() {
        return width;
    }

    // Метод для получения высоты блока
    public float getHeight() {
        return height;
    }
}