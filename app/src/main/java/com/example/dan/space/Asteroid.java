package com.example.dan.space;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;

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
    private int j = 0;

    public ArrayList<Trail> trail;

    public Asteroid(Bitmap bitmap, int x, int y, int w, int h)
    {

        trail = new ArrayList<Trail>();

        this.x = x;
        this.y = y;
        width = w;
        height = h;

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

        if(xSpeed<0)
            j = 5;
        else
            j = 0;
    }

    public void update()
    {
        x += xSpeed;
        y += ySpeed;

        trail.add(new Trail(x+j,y));
        if(trail.size() > 6)
        {
            trail.remove(0);
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
