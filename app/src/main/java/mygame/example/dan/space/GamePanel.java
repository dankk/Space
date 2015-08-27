package mygame.example.dan.space;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Random;

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
    public Shield shield;
    float scaleY;
    float scaleX;
    private long asteroidStartTimer;
    private Random rand = new Random();
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Explosion> explosions;
    private Explosion endExplosion;
    private MainThread thread;
    public static int best;
    MediaPlayer bgMusic;
    SoundPool soundPool;
    int explodeId;
    int repairSound;
    private boolean startTouch;
    public static boolean drawEndExplosion;


    public GamePanel(Context context)
    {
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        setFocusable(true);

    }



    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        thread.stopRun();
        soundPool.release();
        bgMusic.release();

        asteroids.clear();
        asteroids = null;
        explosions.clear();
        explosions = null;
        //System.out.println("cleanup");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        scaleX = getWidth()/(WIDTH*1.f);
        scaleY = getHeight()/(HEIGHT*1.f);

        thread.setRunning(true);
        thread.start();

        SharedPreferences prefs = this.getContext().getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        ship.best = prefs.getInt("key", 0); //0 is the default value

        bgMusic = MediaPlayer.create(this.getContext(), R.raw.bgmusic5);
        bgMusic.setLooping(true);

        bgMusic.start();

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.stars1), 0, 0,
                GamePanel.WIDTH, GamePanel.HEIGHT, 5);
        lSide = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.side), 0, 0, 10, GamePanel.HEIGHT, 10);
        rSide = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.side), WIDTH - 10, 0, 10, GamePanel.HEIGHT, 10);

        ship = new Ship(BitmapFactory.decodeResource(getResources(),R.drawable.ship2), 50, 50, 5);

        shield = new Shield(BitmapFactory.decodeResource(getResources(), R.drawable.shield),
                ship.getX() - 12, ship.getY() - 7, 75, 60);

        asteroids = new ArrayList<Asteroid>();
        explosions = new ArrayList<Explosion>();

        health = (new Health(BitmapFactory.decodeResource(getResources(), R.drawable.health),
                WIDTH/2, -30, 15, 15));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            AudioAttributes aa = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(aa)
                    .build();

        }
        else
        {
            soundPool = new SoundPool(5,AudioManager.STREAM_MUSIC,1);
        }

        repairSound = soundPool.load(getContext(), R.raw.shieldrepair, 1);
        explodeId = soundPool.load(getContext(),  R.raw.explosion, 1);



    }

    @Override
    public boolean onTouchEvent(MotionEvent event)

    {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if(!ship.playing)
        {
            if (x > ((WIDTH / 2) - 100) * scaleX && x < ((WIDTH / 2) + 100) * scaleX && y > ((HEIGHT / 2) - 100) * scaleY && y < ((HEIGHT / 2) + 100) * scaleY) {
                ship.playing = true;
                startTouch = true;
            }

        }

        if (ship.playing && !drawEndExplosion)
        {
            /*if(x == WIDTH/2){
                ship.stopMoving();
            }*/
            if (x < (WIDTH / 2) * scaleX) {
                ship.moveRight(false);
                ship.moveLeft(true);
                //System.out.println("left");
            }
            else
            {
                //System.out.println("right");
                ship.moveLeft(false);
                ship.moveRight(true);

            }
        }

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

    private void drawEndExplosion(boolean b)
    {
        drawEndExplosion = b;
    }

    public void update()
    {
        if(ship.playing)
        {
            if(startTouch){
                ship.stopMoving();
                startTouch = false;
            }

            if(drawEndExplosion)
            {
                endExplosion.update();
                if(endExplosion.playedOnce())
                    ship.resetGame();
            }

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

                if(asteroids.get(i).getY() > HEIGHT - 200)
                {
                    if (collision(asteroids.get(i), ship)) {

                        soundPool.play(explodeId,1,1,1,0,1);

                        if (ship.shield == 0) {
                            if(!drawEndExplosion) {
                                endExplosion = new Explosion(BitmapFactory.decodeResource(getResources(), R.drawable.explosion),
                                        ship.getX() + 5, ship.getY() - 10, 50, 50, 25, 5);
                                ship.stopMoving();
                                drawEndExplosion(true);
                                //ship.resetGame();
                            }
                        }
                        else
                        {
                            ship.shield -= 25;
                            explosions.add(new Explosion(BitmapFactory.decodeResource(getResources(), R.drawable.explosion2),
                                    asteroids.get(i).getX(), asteroids.get(i).getY(), 30, 20, 8, 8));
                            asteroids.remove(i);
                        }
                    }
                }

                if (asteroids.get(i).getY() > HEIGHT + 30) {
                    asteroids.get(i).trail.clear();
                    asteroids.get(i).trail = null;
                    asteroids.remove(i);
                }

            }


            for(int i = 0; i < explosions.size(); i++)
            {
                explosions.get(i).update();
            }

            if(ship.score % 50 == 0 && ship.score > 0)
            {
                health.dy = 10;
            }

            if(collision(health, ship))
            {
                health.resetHealth();
                if(ship.shield < 100)
                {
                    ship.shield += 25;
                    soundPool.play(repairSound,1,1,1,0,1);
                }
            }

            if(health.getY() > HEIGHT + 30)
            {
                health.resetHealth();
            }


            if (!ship.playing)
            {
                asteroids.clear();
                explosions.clear();
                health.resetHealth();
                best = ship.best;

                SharedPreferences prefs = this.getContext().getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("key", ship.best);
                editor.commit();
            }

            health.update();
            rSide.update();
            lSide.update();


            shield.active = !(ship.shield == 0);

           /* if(ship.shield == 0)
            {
                shield.active = false;
            }
            else
            {
                shield.active = true;
            }*/

            shield.update(ship.getX() - 12, ship.getY() - 7);
        }

    }


    @Override
    public void draw(Canvas canvas)
    {
        if(canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleX, scaleY);

            bg.draw(canvas);
            rSide.draw(canvas);
            lSide.draw(canvas);
            ship.draw(canvas);
            health.draw(canvas);
            shield.draw(canvas, ship.shield / 25);

            if(drawEndExplosion)
                endExplosion.draw(canvas);



            for(Asteroid a: asteroids)
            {
                a.draw(canvas);
            }


            for(Explosion e: explosions)
            {
                if(!e.played)
                    e.draw(canvas);
            }


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

        if(!ship.playing)
        {
            canvas.drawText("TOUCH HERE TO START", (WIDTH/2) - 100, HEIGHT/2, paint);
        }

        canvas.drawText("Shield: " + ship.shield + "%", 50, HEIGHT - 50, paint);
        canvas.drawText("Score: " + ship.score, WIDTH - 150, HEIGHT - 50, paint);
        canvas.drawText("Best Score: " + ship.best, WIDTH - 150, 20, paint);

    }

}
