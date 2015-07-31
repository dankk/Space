package com.example.dan.space;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.Random;

/**
 * Created by Dan on 26/07/2015.
 */
public class Asteroid extends GameObject
{
    private Bitmap spritesheet;
    private Random rand = new Random();
    private int xSpeed;
    private int ySpeed;

    public Asteroid(Bitmap bitmap, int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        width = w;
        height = h;

        xSpeed = rand.nextInt(10) - 5;
        ySpeed = rand.nextInt(20) + 10;

        spritesheet = bitmap;
    }

    public void update()
    {
        y += ySpeed;
        x += xSpeed;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(spritesheet, x, y, null);
    }

    public int getXSpeed()
    {
        return xSpeed;
    }
    public int getYSpeed()
    {
        return ySpeed;
    }
}
