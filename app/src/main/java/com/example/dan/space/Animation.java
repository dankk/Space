package com.example.dan.space;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Dan on 28/07/2015.
 */
public class Animation
{
    private Bitmap[] frames;
    public int currentFrame;
    private long startTime;
    private long delay;
    private long elapsed;
    private boolean playedOnce;

    public void setFrames(Bitmap[] frames)
    {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    public void setDelay(int i)
    {
        delay = i;
    }

    public void update()
    {
        elapsed = (System.nanoTime() - startTime)/100000;

        if(elapsed > delay)
        {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length)
        {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public Bitmap getImage()
    {
        return frames[currentFrame];
    }

    public boolean playedOnce()
    {
        return playedOnce;
    }

}
