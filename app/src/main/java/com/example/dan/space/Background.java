package com.example.dan.space;

import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * Created by Dan on 25/07/2015.
 */
public class Background
{

    private Bitmap image;
    private int x, y, width, height, dy;

    public Background(Bitmap bitmap, int x, int y, int w, int h, int speed)
    {
        image = bitmap;
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        dy = speed;
    }

    public void update()
    {
        y += dy;
        if(y > GamePanel.HEIGHT)
            y = 0;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
        if(y > 0)
        {
            canvas.drawBitmap(image, x, y - GamePanel.HEIGHT, null);
        }
    }
}
