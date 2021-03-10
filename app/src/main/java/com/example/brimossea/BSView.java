package com.example.brimossea;

import android.content.Context;
import android.content.Intent;
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
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;

public class BSView extends SurfaceView implements Runnable {
    private boolean debugging = true;
    volatile boolean playing = true;
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




    public BSView(Context context, int screenWidth, int screenHeight,String level) {
        super(context);
        this.context = context;
        // Initialize our drawing objects
        ourHolder = getHolder();
        paint = new Paint();
        // Initialize the viewport
        vp = new Viewport(screenWidth, screenHeight);
        sm = new SoundManager();
        ps = new PlayerState();

        // Load the first level
        loadLevel(level, 0, 6);
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

        // Set the players location as the world centre
        vp.setWorldCentre(lm.gameObjects.get(lm.playerIndex)
                        .getWorldLocation().x+vp.getMetresToShowX()/2-lm.gameObjects.get(lm.playerIndex)
                        .getWidth()/2,
                vp.getMetresToShowY()/2 - lm.player.getHeight()/2);
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
        return true;
    }

    private void draw(){
        if (ourHolder.getSurface().isValid()){
            //First we lock the area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();

            // Rub out the last frame with arbitrary color
            paint.setColor(Color.argb(255, 255, 0, 255));
            canvas.drawColor(Color.argb(255, 255, 0, 255));

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
                        paint.setColor(Color.argb(255, 255, 0, 255));
                        // Draw the appropriate bitmap
                        if (ps.shield == go){
                            paint.setAlpha(100);
                        }
                        else {
                            paint.setAlpha(255);
                        }
                        canvas.drawBitmap(
                                lm.bitmapsArray
                                        [lm.getBitmapIndex(go.getType())],
                                toScreen2d.left,
                                toScreen2d.top, paint);
                    }
                }
            }

            int topSpace = vp.getPixelsPerMetreY() / 4;
            int iconSize = vp.getPixelsPerMetreX();
            int padding = vp.getPixelsPerMetreX() / 2;
//            int centring = vp.getPixelsPerMetreY() / 12;
            paint.setTextSize(vp.getPixelsPerMetreY());
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(Color.argb(100, 0, 0, 0));
            canvas.drawRect(0,0,iconSize * 7.0f, topSpace*2 + iconSize,paint);
            paint.setColor(Color.argb(255, 255, 255, 0));
            canvas.drawBitmap(lm.getBitmap('e'), 0, topSpace, paint);
            canvas.drawText("" + ps.getLives(), (iconSize * 1) + padding,
                    (iconSize) , paint);
            canvas.drawBitmap(lm.getBitmap('1'), (iconSize * 1.5f) + padding,
                    topSpace, paint);
            canvas.drawText("" + ps.getCredits(), (iconSize * 3.5f) + padding
                    * 2, (iconSize) , paint);


            // Text for debugging
//            if (debugging) {
                paint.setTextSize(32);
                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawText("playerX:" +
                                lm.gameObjects.get(lm.playerIndex).
                                        getWorldLocation().x,
                        300, 120, paint);
//            }// End if(debugging)

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
            if (go instanceof Player && go.getWorldLocation().x + go.getWidth() >= lm.mapWidth ){
                endGame(ps.getCredits());
            }
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
                                break;
                            case 'e':
                                //extralife
                                go.setActive(false);
                                go.setVisible(false);
                                sm.playSound("life");
                                ps.addLife();
                                break;
                            case 'b':
                                //shield
                                if (!ps.hasShield){
                                    ps.activateShield();
                                    ps.setShield(go);
                                }
                                break;
                            case 's':
                                //shark
                                go.setActive(false);
//                                sm.playSound("shark");
                                if (!ps.hasShield){
                                    ps.loseLife();
                                }
                                if(ps.getLives() <= 0) {
                                    endGame(ps.getCredits());
                                    break;
                                }
                                break;
                        }
                    }

                    if (go == ps.shield){
                        float newX = (go.getWidth() - lm.player.getWidth()) / 2;
                        float newY = (go.getHeight() - lm.player.getHeight()) / 2;
                        go.setWorldLocation(lm.player.getWorldLocation().x - newX, lm.player.getWorldLocation().y - newY,0);
                        if (System.currentTimeMillis()-ps.getShieldStartTime() >= ps.SHIELD_TIMEOUT){
                            ps.deactivateShield();
                        }
                    }

                    if (lm.isPlaying()) {
                        // Run any un-clipped updates
                        go.update(fps, lm.gravity,vp);
                    }
                    } else {
                    // Set visible flag to false
                    go.setVisible(false);
                    // Now draw() can ignore them
                    }
            }
        }

        if (lm.isPlaying() && vp.getCurrentViewportWorldCentre().x + vp.getMetresToShowX()/2 + lm.player.getWidth()/2 <= lm.mapWidth) {
            vp.updateViewport();
        }
    }


    //Clean up our thread if the game is interrupted
    public void pause() {
        playing = false;
        try {
            gameThread.join();
            sm.stopSound("background");
        }catch (InterruptedException e){
            Log.e("error", "failed to pause thread");
        }
    }


    // Make a new thread and start it
    // Execution moves to our run method
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    //This method starts the endActivity and passes the score information to the next activity
    public void endGame(int current) {
        //Check if the previous score was greater than the current score
        if(current > Records.currentHighScore) {
            Records.currentHighScore = current;
        }
        Records.currentScore = current;
        playing = false;
        Intent intent = new Intent(getContext(), EndActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("highScore", Records.currentHighScore);
        intent.putExtra("currentScore", Records.currentScore);
        intent.putExtra("currentLevel", lm.getLevel());
        intent.putExtra("lives", ps.getLives());
        getContext().startActivity(intent);


    }
}
