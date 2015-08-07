package com.example.dan.space;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Dan on 8/5/2015.
 */
public class Shield
{
    private int x;
    private int y;
    private int width;
    private int height;
    public boolean active;
    private Bitmap spritesheet;

    public Shield(Bitmap bitmap, int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        width = w;
        height = h;

        spritesheet = bitmap;

        active = true;
    }

    public void update(int newX, int newY)
    {
        x = newX;
        y = newY;
    }

    public void draw(Canvas canvas)
    {
        if(active)
        {
            canvas.drawBitmap(spritesheet, x, y, null);
        }
    }
}
