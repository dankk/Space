package com.example.dan.space;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Dan on 8/6/2015.
 */
public class Trail {

    private int x;
    private int y;
    private int width;
    private int height;

    private Bitmap spritesheet;

    public Trail(Bitmap bitmap, int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;

        width = w;
        height = h;

        spritesheet = bitmap;

    }

    public void update(int newX, int newY)
    {
        x = newX;
        y = newY;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(spritesheet, x, y, null);
    }
}
