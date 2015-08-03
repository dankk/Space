package com.example.dan.space;

import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * Created by Dan on 8/2/2015.
 */
public class Health extends GameObject
{
    private Bitmap spritesheet;
    public int dy;

    public Health(Bitmap bitmap, int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        width = w;
        height = h;

        spritesheet = bitmap;
    }

    public void resetHealth()
    {
        dy = 0;
        y = -30;
    }


    public void update()
    {
        y += dy;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(spritesheet, x, y, null);
    }

}
