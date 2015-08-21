package com.example.dan.space;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Dan on 8/2/2015.
 */
public class Explosion
{
    private Bitmap spritesheet;
    private int x;
    private int y;
    private int width;
    private int height;
    private int row;
    public boolean played = false;
    private Animation animation = new Animation();

    public Explosion(Bitmap bitmap, int x, int y, int w, int h, int numFrames, int framesPerRow)
    {
        this.x = x;
        this.y = y;
        width = w;
        height = h;

        spritesheet = bitmap;

        Bitmap[] image = new Bitmap[numFrames];

        for(int i = 0; i < image.length; i++)
        {
            if(i%framesPerRow==0 && i>0)
                row++;

            image[i] = Bitmap.createBitmap(spritesheet, (i-(framesPerRow*row))*width, row*height, width, height);

            //image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);

        }
        animation.setFrames(image);
        animation.setDelay(10);

    }

    public void draw(Canvas canvas)
    {
        if(!animation.playedOnce())
            canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    public void update()
    {
        if(!animation.playedOnce())
        {
            animation.update();
        }
        else
        {
            played = true;
        }

    }

    public boolean playedOnce()
    {
        return animation.playedOnce();
    }
}
