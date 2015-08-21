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
    private Bitmap[] image;

    public Shield(Bitmap bitmap, int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        width = w;
        height = h;

        image = new Bitmap[5];

        for(int i = 0; i < 5; i++)
        {
            image[i] = Bitmap.createBitmap(bitmap, i * width, 0, width, height);
        }

        active = true;
    }

    public void update(int newX, int newY)
    {
        x = newX;
        y = newY;
    }

    public void draw(Canvas canvas, int cur)
    {
        if(active)
        {
            canvas.drawBitmap(image[cur], x, y, null);
        }
    }
}
