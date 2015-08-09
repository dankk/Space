package com.example.dan.space;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
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
    private int j;
    private ArrayList<Trail> trail;

    public Asteroid(Bitmap bitmap, int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        width = w;
        height = h;

        trail = new ArrayList<Trail>();

        ySpeed = rand.nextInt(15) + 10;

        if(x < GamePanel.WIDTH /2)
        {
            xSpeed = ySpeed/5;
        }
        else
        {
            xSpeed = -(ySpeed/5);
        }
        spritesheet = bitmap;

        for(int i = 0; i < 6; i++)
        {
            trail.add(new Trail(x*i,y+i));
        }

    }

    public void update()
    {
        x += xSpeed;
        y += ySpeed;

        if(xSpeed<0)
            j = 7;
        else
            j = 2;

        for(int i = 0; i < 6; i++)
        {
            trail.get(i).update((x-(xSpeed*i)+j), y-(i*10));
        }
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(spritesheet, x, y, null);
        for(Trail t: trail)
        {
            t.draw(canvas);
        }
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
