package com.example.brimossea;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;

public class BSView extends SurfaceView implements Runnable {
    private boolean debugging = true;
    volatile boolean playing;
    Thread gameThread = null;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;
    private Context context;
    long startFrameTime;
    long timeThisFrame;
    long fps;

    // Our new engine classes
    private LevelManager lm;
    private Viewport vp;
    InputController ic;
    SoundManager sm;
    private PlayerState ps;




    public BSView(Context context, int screenWidth, int screenHeight) {
        super(context);
        this.context = context;
        ourHolder = getHolder();
        paint = new Paint();
        // Initialize the viewport
        vp = new Viewport(screenWidth, screenHeight);
        sm = new SoundManager();
        ps = new PlayerState();

        // Load the first level
        loadLevel("LevelCave", 0, 6);
    }

    public void loadLevel(String level, float px, float py) {
        lm = null;
        // Create a new LevelManager
        // Pass in a Context, screen details, level name
        // and player location
        lm = new LevelManager(context,
                vp.getPixelsPerMetreX(),
                vp.getScreenWidth(),
                ic, level, px, py);
        ic = new InputController(vp.getScreenWidth(),
                vp.getScreenHeight());

        PointF location = new PointF(px, py);
        ps.saveLocation(location);

        // Set the players location as the world centre
        vp.setWorldCentre(lm.gameObjects.get(lm.playerIndex)
                        .getWorldLocation().x+17-lm.gameObjects.get(lm.playerIndex)
                        .getWidth(),
                lm.gameObjects.get(lm.playerIndex)
                        .getWorldLocation().y);
    }

    @Override
    public void run() {
        while (playing){
            startFrameTime = System.currentTimeMillis();
            update();
            draw();
            // Calculate the fps this frame
            // We can then use the result to
            // time animations and movement.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
//            control();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (lm != null) {
            ic.handleInput(motionEvent, lm, sm, vp);
        }
        //invalidate();
        return true;
    }

    private void draw(){
        if (ourHolder.getSurface().isValid()){
            canvas = ourHolder.lockCanvas();

            // Rub out the last frame with arbitrary color
            paint.setColor(Color.argb(255, 0, 0, 255));
            canvas.drawColor(Color.argb(255, 0, 0, 255));

            // Draw all the GameObjects
            Rect toScreen2d = new Rect();
            // Draw a layer at a time
            for (int layer = -1; layer <= 1; layer++) {
                for (GameObject go : lm.gameObjects) {
                    //Only draw if visible and this layer
                    if (go.isVisible() && go.getWorldLocation().z == layer) {
                        toScreen2d.set(vp.worldToScreen
                                (go.getWorldLocation().x,
                                        go.getWorldLocation().y,
                                        go.getWidth(),
                                        go.getHeight()));
                        // Draw the appropriate bitmap
                        canvas.drawBitmap(
                                lm.bitmapsArray
                                        [lm.getBitmapIndex(go.getType())],
                                toScreen2d.left,
                                toScreen2d.top, paint);
                    }
                }
            }
            // Text for debugging
            if (debugging) {
                paint.setTextSize(32);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawText("fps:" + fps, 10, 60, paint);
                canvas.drawText("num objects:" +
                        lm.gameObjects.size(), 10, 80, paint);
                canvas.drawText("num clipped:" +
                        vp.getNumClipped(), 10, 100, paint);
                canvas.drawText("playerX:" +
                                lm.gameObjects.get(lm.playerIndex).
                                        getWorldLocation().x,
                        10, 120, paint);
                canvas.drawText("playerY:" +
                        lm.gameObjects.get(lm.playerIndex).
                                getWorldLocation().y,
                        10, 140, paint);
                canvas.drawText("X velocity:" +
                                lm.gameObjects.get(lm.playerIndex).getxVelocity(),
                        10, 180, paint);
                canvas.drawText("Y velocity:" +
                                lm.gameObjects.get(lm.playerIndex).getyVelocity(),
                        10, 200, paint);
                //for reset the number of clipped objects each frame
                vp.resetNumClipped();
            }// End if(debugging)

            //draw buttons
            paint.setColor(Color.argb(80, 255, 255, 255));
            ArrayList<Rect> buttonsToDraw;
            buttonsToDraw = ic.getButtons();
            for (Rect rect : buttonsToDraw) {
                RectF rf = new RectF(rect.left, rect.top, rect.right, rect.bottom);
                canvas.drawRoundRect(rf, 15f, 15f, paint);
            }
            //draw paused text
            if (!this.lm.isPlaying()) {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(120);
                canvas.drawText("Paused", vp.getScreenWidth() / 2,
                        vp.getScreenHeight() / 2, paint);
            }
                        ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void update() {
        for (GameObject go : lm.gameObjects) {
            if (go.isActive()) {
                // Clip anything off-screen
                if (!vp.clipObjects(go.getWorldLocation().x,
                        go.getWorldLocation().y,
                        go.getWidth(),
                        go.getHeight())) {
                    // Set visible flag to true
                    go.setVisible(true);
                    // check collisions with player
                    int hit = lm.player.checkCollisions(go.getHitbox());
                    if (hit > 0) {
                        //collision! Now deal with different types
                        switch (go.getType()) {
                            case '1':
                                sm.playSound("feeding");
                                go.setActive(false);
                                go.setVisible(false);
                                ps.gotCredit();
                                // Now restore state that was
                                // removed by collision detection
//                                if (hit != 2) {// Any hit except feet
//                                    lm.player.restorePreviousVelocity();
//                                }
                                break;
                            case 'e':
                                //extralife
                                go.setActive(false);
                                go.setVisible(false);
                                sm.playSound("life");
                                ps.addLife();
//                                if (hit != 2) {
//                                    lm.player.restorePreviousVelocity();
//                                }
                                break;
                            case 'b':
                                //shield
                                if (!ps.hasShield){
                                    ps.activateShield();
                                }
                                go.setWorldLocation(lm.player.getWorldLocation().x,lm.player.getWorldLocation().y,0);
                                break;
                            case 's':
                                //shark
                                go.setActive(false);
                                sm.playSound("shark");
                                ps.loseLife();
                                break;
                        }
                    }
                    if (lm.isPlaying()) {
                        // Run any un-clipped updates
                        go.update(fps, lm.gravity);
                    }
                } else {
                    // Set visible flag to false
                    go.setVisible(false);
                    // Now draw() can ignore them
                }
            }
        }

        if (lm.isPlaying()) {
            vp.updateViewport();
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        }catch (InterruptedException e){
            Log.e("error", "failed to pause thread");
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
