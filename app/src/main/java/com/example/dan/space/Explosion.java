package com.example.dan.space;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public Explosion(Bitmap bitmap, int x, int y, int w, int h, int numFrames)
    {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;

        spritesheet = bitmap;

        Bitmap[] image = new Bitmap[numFrames];

        for(int i = 0; i < image.length; i++)
        {
            if(i%5==0 && i>0)
                row++;

            image[i] = Bitmap.createBitmap(spritesheet, (i-(5*row)), row*height, width, height);

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
}
