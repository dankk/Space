package com.example.dan.space;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

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
    public ArrayList<Trail> trails;

    public Asteroid(Bitmap bitmap, int x, int y, int w, int h, ArrayList<Trail> trails)
    {
        this.x = x;
        this.y = y;
        width = w;
        height = h;

        this.trails = trails;

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
    }

    public void update()
    {
        x += xSpeed;
        y += ySpeed;

        for(int i = 0; i < trails.size(); i++)
        {
            trails.get(i).update((x-(xSpeed*i))+5, y-(i*10));
        }
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
