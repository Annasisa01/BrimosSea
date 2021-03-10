package com.example.brimossea;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Player extends GameObject{
    final float MAX_X_VELOCITY = 5;
    final float MAX_Y_VELOCITY = 5;
    boolean isPressingRight = false;
    boolean isPressingLeft = false;
    boolean isPressingUp = false;
    boolean isPressingDown = false;

    RectHitbox rectHitboxBottom;
    RectHitbox rectHitboxTop;
    RectHitbox rectHitboxLeft;
    RectHitbox rectHitboxRight;

    private int shieldStrength;

    public Player(Context context, float worldStartX, float worldStartY, int pixelsPerMetre){
        final float HEIGHT = 2;
        final float WIDTH = 2;

        setHeight(HEIGHT); // 2 metre tall
        setWidth(WIDTH); // 2 metre wide

        // Standing still to start with
        setxVelocity(0);
        setyVelocity(0);
        setFacing(RIGHT);

        // Now for the player's other attributes
        // Our game engine will use these
        setMoves(true);
        setActive(true);
        setVisible(true);

        setType('p');

        // Choose a Bitmap
        // This is a sprite sheet with multiple frames
        // of animation. So it will look silly until we animate it
        // In chapter 6.
        setBitmapName("fish");

        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 0);

        rectHitboxBottom = new RectHitbox();
        rectHitboxTop = new RectHitbox();
        rectHitboxLeft = new RectHitbox();
        rectHitboxRight = new RectHitbox();
    }

    @Override
    public void update(long fps, float gravity, Viewport vp) {
        if (isPressingRight) {
            this.setxVelocity(MAX_X_VELOCITY);
        } else if (isPressingLeft) {
            this.setxVelocity(-MAX_X_VELOCITY);
        } else if (isPressingUp){
            this.setyVelocity(-MAX_Y_VELOCITY);
        } else if (isPressingDown){
            this.setyVelocity(MAX_Y_VELOCITY);
        } else {
            this.setxVelocity(3);
            this.setyVelocity(0);
        }

        this.move(fps, vp);

        // Update all the hitboxes to the new location
        // Get the current world location of the player
        // and save them as local variables we will use next
        Vector2Point5D location = getWorldLocation();
        float lx = location.x;
        float ly = location.y;
        // Update player right hitbox
        rectHitboxRight.setTop(ly + getHeight() * .2f);
        rectHitboxRight.setLeft(lx + getWidth() * .8f);
        rectHitboxRight.setBottom(ly + getHeight() * .8f);
        rectHitboxRight.setRight(lx + getWidth() * .7f);
    }

    public int checkCollisions(RectHitbox rectHitbox) {
        int collided = 0;// No collision
        // The right
        if (this.rectHitboxRight.intersects(rectHitbox)) {
            // Right has collided
            // Move player just to left of current hitbox
//            this.setWorldLocationX(rectHitbox.left - getWidth() * .8f);
            collided = 1;
        }
        return collided;
    }

    public void setPressingRight(boolean isPressingRight) {
        this.isPressingRight = isPressingRight;
    }
    public void setPressingLeft(boolean isPressingLeft) {
        this.isPressingLeft = isPressingLeft;
    }
    public void setPressingUp(boolean isPressingUp) {
        this.isPressingUp = isPressingUp;
    }
    public void setPressingDown(boolean isPressingDown) {
        this.isPressingDown = isPressingDown;
    }

    public void move(long fps, Viewport vp){
        if (this.getWorldLocation().y < vp.getCurrentViewportWorldCentre().y - vp.getMetresToShowY()/2 + getHeight()/2){
            this.setWorldLocationY(vp.getCurrentViewportWorldCentre().y - vp.getMetresToShowY()/2 + getHeight()/2);
        }else if (this.getWorldLocation().y + getHeight() > vp.getMetresToShowY()-2){
            this.setWorldLocationY(vp.getMetresToShowY()-4);
        }
        if (this.getWorldLocation().x < vp.getCurrentViewportWorldCentre().x - vp.getMetresToShowX()/2 + getWidth()/2){
            this.setWorldLocationX(vp.getCurrentViewportWorldCentre().x - vp.getMetresToShowX()/2 + getWidth()/2);
        }else if (this.getWorldLocation().x + getWidth() > vp.getCurrentViewportWorldCentre().x + vp.getMetresToShowX()/2){
            this.setWorldLocationX(vp.getCurrentViewportWorldCentre().x + vp.getMetresToShowX()/2);
        }
        if(getxVelocity() != 0) {
            this.setWorldLocationX(this.getWorldLocation().x + getxVelocity() / fps);
        }

        if(getyVelocity() != 0) {
            this.setWorldLocationY(this.getWorldLocation().y + getyVelocity() / fps);
        }
    }
    }
