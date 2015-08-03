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
    private int maxAccel = 6;
    public int shield;
    public int score;
    public int best;
    public boolean playing;

    private Animation animation = new Animation();

    public Ship(Bitmap bitmap, int w, int h, int numFrames)
    {
        x = (GamePanel.WIDTH / 2) - w/2;
        y = GamePanel.HEIGHT - 150;
        dx = 0;

        height = h;
        width = w;

        shield = 100;

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
            dx += 2;
        }
        if(left)
        {
            dx -= 2;
        }

        if(dx>maxAccel)
            dx = maxAccel;
        if(dx < -maxAccel)
            dx = -maxAccel;

        if(x > GamePanel.WIDTH || x < 0 - width)
        {
            resetGame();
        }

        x += dx*2;
    }

    public void resetGame()
    {
        playing = false;

        if(score > best)
            best = score;

        x = (GamePanel.WIDTH / 2) - width/2;
        stopMoving();
        resetHealth();
        resetScore();
    }

    public void resetHealth()
    {
        shield = 100;
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
