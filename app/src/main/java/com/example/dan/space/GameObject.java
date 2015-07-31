package com.example.dan.space;

import android.graphics.Rect;

/**
 * Created by Dan on 26/07/2015.
 */
public class GameObject
{
    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;

    public int getX()
    {
        return x;
    }
    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }
    public void setY(int y)
    {
        this.y = y;
    }

    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }

    public Rect getRectangle()
    {
        return new Rect(x, y, x+width, y+height);
    }

}
