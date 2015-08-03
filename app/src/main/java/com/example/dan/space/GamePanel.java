package com.example.dan.space;

import android.app.Activity;
import android.appwidget.AppWidgetHost;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Dan on 25/07/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = 420;
    public static final int HEIGHT = 856;
    public Background bg, rSide, lSide;
    public Ship ship;
    public Health health;
    float scaleY;
    float scaleX;
    private long asteroidStartTimer;
    private Random rand = new Random();
    private ArrayList<Asteroid> asteroids;
    private MainThread thread;

    public GamePanel(Context context)
    {
        super(context);

        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.stars1), 0, 0,
                GamePanel.WIDTH, GamePanel.HEIGHT, 5);
        lSide = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.side), 0, 0, 10, GamePanel.HEIGHT, 10);
        rSide = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.side), WIDTH - 10, 0, 10, GamePanel.HEIGHT, 10);

        ship = new Ship(BitmapFactory.decodeResource(getResources(),R.drawable.ship), 50, 50, 5);

        health = (new Health(BitmapFactory.decodeResource(getResources(), R.drawable.health),
                WIDTH/2, -30, 15, 15));
        asteroids = new ArrayList<Asteroid>();

        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)

    {
        int x = (int) event.getX();

        if(x < (WIDTH/2)*scaleX)
        {
            ship.moveRight(false);
            ship.moveLeft(true);
            System.out.println("left");
        }
        else
        {
            System.out.println("right");
            ship.moveLeft(false);
            ship.moveRight(true);

        }
        ship.playing = true;

        return super.onTouchEvent(event);
    }

    public boolean collision(GameObject a, GameObject b)
    {
        if(Rect.intersects(a.getRectangle(), b.getRectangle()))
        {
            return true;
        }
        return false;
    }

    public void update()
    {
        if(ship.playing)
        {
            bg.update();
            ship.update();

            long asteroidElapsed = (System.nanoTime() - asteroidStartTimer) / 1000000;

            if (asteroidElapsed > 500) {

                asteroids.add(new Asteroid(BitmapFactory.decodeResource(getResources(), R.drawable.asteroid),
                        (int) (rand.nextDouble() * WIDTH), -30, 20, 20));

                asteroidStartTimer = System.nanoTime();
                ship.score++;
            }

            for (int i = 0; i < asteroids.size(); i++) {

                asteroids.get(i).update();

                if (collision(asteroids.get(i), ship)) {
                    if(ship.shield == 0)
                    {
                        ship.resetGame();
                    }
                    else
                    {
                        ship.shield -= 25;
                        asteroids.remove(i);
                    }
                }


                if (asteroids.get(i).getY() > HEIGHT + 30) {
                    asteroids.remove(i);
                }
            }

            if(ship.score % 50 == 0 && ship.score > 0)
            {
                health.dy = 20;
            }

            if(collision(health, ship))
            {
                health.resetHealth();
                if(ship.shield < 100)
                    ship.shield += 25;
            }

            if(health.getY() > HEIGHT + 30)
            {
                health.resetHealth();
            }


            if (!ship.playing)
            {
                asteroids.clear();
                health.resetHealth();
            }

            health.update();
            rSide.update();
            lSide.update();

        }

    }


    @Override
    public void draw(Canvas canvas)
    {
        scaleX = getWidth()/(WIDTH*1.f);
        scaleY = getHeight()/(HEIGHT*1.f);

        if(canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleX, scaleY);

            bg.draw(canvas);
            rSide.draw(canvas);
            lSide.draw(canvas);
            ship.draw(canvas);
            health.draw(canvas);

            for(Asteroid a: asteroids)
                a.draw(canvas);

            drawText(canvas);
            canvas.restoreToCount(savedState);
        }
    }

    public void drawText(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        canvas.drawText("Shield: " + ship.shield + "%", 50, HEIGHT - 50, paint);
        canvas.drawText("Score: " + ship.score, WIDTH - 150, HEIGHT - 50, paint);
        canvas.drawText("Best Score: " + ship.best, WIDTH - 150, 20, paint);

    }
}
