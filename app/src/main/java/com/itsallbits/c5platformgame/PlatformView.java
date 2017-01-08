package com.itsallbits.c5platformgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by matt on 1/7/17.
 */

public class PlatformView extends SurfaceView implements Runnable{

    private boolean debugging = true;
    private volatile boolean running;
    private Thread gameThread = null;

    //for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    Context context;
    long startFrameTime;
    long timeThisFrame;
    long fps;

    //our new engine class
    private LevelManager lm;
    private Viewport vp;
    InputController ic;

    PlatformView(Context context,int screenWidth,int screenHeight) {
        super(context);
        this.context = context;

        //initialze our drawing objects
        ourHolder = getHolder();
        paint = new Paint();

        //Initialize the viewport
        vp = new Viewport(screenWidth,screenHeight);

        //load the first level
        loadLevel("LevelCave",15,2);
    }

    @Override
    public void run() {
        while(running) {
            startFrameTime = System.currentTimeMillis();

            update();
            draw();

            //Calculate the fps this frame
            //We can then use the result to time animations and movement.

            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if(timeThisFrame >= 1){
                fps = 1000 / timeThisFrame;
            }
        }
    }

    private void update() {
        for(GameObject go : lm.gameObjects) {
            if(go.isActive()) {
                //Clip anything off-screen
                if(!vp.clipObjects(go.getWorldLocation().x,go.getWorldLocation().y,go.getWidth(),go.getHeight())) {
                    //set visible flag to true
                    go.setVisible(true);
                } else {
                    //set visible flag to false
                    go.setVisible(false);
                    //now draw() can ignore them
                }
            }
        }

    }

    private void draw() {
        if(ourHolder.getSurface().isValid()) {
            //First we ock the area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();

            //Rub out the last frame with arbitrary color
            paint.setColor(Color.argb(255,0,0,255));
            canvas.drawColor(Color.argb(255,0,0,255));

            Rect toScreen2d = new Rect();

            //Draw a layer at a time
            for(int layer = -1; layer <= 1; layer++) {
                for(GameObject go : lm.gameObjects) {
                    //only draw if visible and this layer
                    if(go.isVisible() && go.getWorldLocation().z == layer) {
                        toScreen2d.set(vp.worldToScreen(go.getWorldLocation().x,go.getWorldLocation().y,go.getWidth(),go.getHeight()));

                        //draw the appropriate bitmap
                        canvas.drawBitmap(lm.bitmapsArray[lm.getBitmapIndex(go.getType())],toScreen2d.left, toScreen2d.top, paint);
                    }
                }
            }

            if(debugging) {
             paint.setTextSize(16);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255,255,255,255));
                canvas.drawText("fps:" + fps, 10, 60, paint);

                canvas.drawText("num objects: " + lm.gameObjects.size(), 10, 80, paint);
                canvas.drawText("num clipped: " + vp.getNumClipped(), 10, 100, paint);
                canvas.drawText("playerX: " + lm.gameObjects.get(lm.playerIndex).getWorldLocation().x, 10, 120, paint);
                canvas.drawText("playerY: " + lm.gameObjects.get(lm.playerIndex).getWorldLocation().y, 10, 140, paint);

                //for reset the number of clipped objects each frame
                vp.resetNnumClipped();

            }

            //unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch(InterruptedException e) {
            Log.e("error", "failed to pause thread");
        }
    }

    //Make a new thread and start it
    //Execution moves to our run method
    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void loadLevel(String level, float px, float py) {

        lm = null;

        //Create a new LevelManager
        //pass in a context, screen details, level name , and player location
        lm = new LevelManager(context, vp.getPixelsPerMeterX(), vp.getScreenWidth(), ic, level, px, py);
        ic = new InputController(vp.getScreenWidth(),vp.getScreenHeight());

        //set the players location as the world center
        vp.setWorldCenter(lm.gameObjects.get(lm.playerIndex).getWorldLocation().x, lm.gameObjects.get(lm.playerIndex).getWorldLocation().y);


    }
}
