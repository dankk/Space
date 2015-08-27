package mygame.example.dan.space;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Dan on 25/07/2015.
 */
public class MainThread extends Thread
{
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    public static Canvas canvas;

    private boolean running = false;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run()
    {
        //FPS stuff
        int FPS = 25;
        double averageFPS;
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        int framesSkipped;
        int maxFramesSkipped = 5;
        long targetTime = 1000/FPS;

        while(running) {
            canvas = null;
            startTime = System.currentTimeMillis();
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    framesSkipped = 0;
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);

                    timeMillis = System.currentTimeMillis() - startTime;
                    waitTime = targetTime - timeMillis;

                    if(waitTime > 0)
                    {
                        try
                        {
                            Thread.sleep(waitTime);
                        }catch (Exception e){}
                    }

                    /*while(waitTime < 0 && framesSkipped < maxFramesSkipped)
                    {
                        this.gamePanel.update();
                        waitTime += targetTime;
                        framesSkipped++;
                    }*/
                }
            } catch (Exception e) {}
            finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {e.printStackTrace();}
                }
            }



            totalTime += System.currentTimeMillis() - startTime;
            frameCount++;
            if(frameCount == FPS)
            {
                averageFPS = 1000/(totalTime/frameCount);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }

    }

    public void stopRun(){
        setRunning(false);
    }

    public boolean isRunning(){
        return running;
    }

    public void setRunning(boolean b)
    {
        running = b;
    }
}
