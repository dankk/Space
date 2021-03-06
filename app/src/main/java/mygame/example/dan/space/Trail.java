package mygame.example.dan.space;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Dan on 8/8/2015.
 */
public class Trail
{
    private int x;
    private int y;
    private int r;
    private Random rand = new Random();

    Paint paint = new Paint();

    public Trail(int x, int y)
    {
        this.x = x;
        this.y = y;
        r = 1;
    }

    public void update(int newX, int newY)
    {
        x = newX;
        y = newY;
    }

    public void draw(Canvas canvas)
    {
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(x+rand.nextInt(5), y-5, r, paint);
        canvas.drawCircle(x+rand.nextInt(5)+5, y-5, r, paint);
        canvas.drawCircle(x+rand.nextInt(5)+10, y-5, r, paint);
    }
}
