package com.example.dan.space;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Dan on 26/07/2015.
 */
public class Ship extends GameObject
{
    private Bitmap spritesheet;
    private boolean right;
    private boolean left;
    private int maxAccel = 5;
    public int health;
    public int score;

    private Animation animation = new Animation();

    public Ship(Bitmap bitmap, int w, int h, int numFrames)
    {
        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT - 150;
        dx = 0;

        height = h;
        width = w;

        health = 100;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = bitmap;

        for(int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);


    }

    public void moveRight(boolean b){right = b;}
    public void moveLeft(boolean b){left = b;}

    public void stopMoving()
    {
        dx = 0;
        moveRight(false);
        moveLeft(false);
    }

    public void update()
    {
        animation.update();

        if(right)
        {
            dx += 1;
        }
        if(left)
        {
            dx -= 1;
        }

        if(dx>maxAccel)
            dx = maxAccel;
        if(dx < -maxAccel)
            dx = -maxAccel;

        if(x > GamePanel.WIDTH - width)
        {
            x = GamePanel.WIDTH / 2;
            stopMoving();
            resetHealth();
            resetScore();
        }
        if(x < 0)
        {
            x = GamePanel.WIDTH / 2;
            stopMoving();
            resetHealth();
            resetScore();
        }
        x += dx*2;
    }

    public void resetHealth()
    {
        health = 100;
    }
    public void resetScore()
    {
        score = 0;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

}
