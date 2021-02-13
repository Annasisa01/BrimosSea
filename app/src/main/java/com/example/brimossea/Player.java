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
        //update the player feet hitbox
        rectHitboxBottom.top = ly + getHeight() * .95f;
        rectHitboxBottom.left = lx + getWidth() * .2f;
        rectHitboxBottom.bottom = ly + getHeight() * .98f;
        rectHitboxBottom.right = lx + getWidth() * .8f;
        // Update player head hitbox
        rectHitboxTop.top = ly;
        rectHitboxTop.left = lx + getWidth() * .4f;
        rectHitboxTop.bottom = ly + getHeight() * .2f;
        rectHitboxTop.right = lx + getWidth() * .6f;
        // Update player left hitbox
        rectHitboxLeft.top = ly + getHeight() * .2f;
        rectHitboxLeft.left = lx + getWidth() * .2f;
        rectHitboxLeft.bottom = ly + getHeight() * .8f;
        rectHitboxLeft.right = lx + getWidth() * .3f;
        // Update player right hitbox
        rectHitboxRight.top = ly + getHeight() * .2f;
        rectHitboxRight.left = lx + getWidth() * .8f;
        rectHitboxRight.bottom = ly + getHeight() * .8f;
        rectHitboxRight.right = lx + getWidth() * .7f;
    }

    public int checkCollisions(RectHitbox rectHitbox) {
        int collided = 0;// No collision
        // The left
        if (this.rectHitboxLeft.intersects(rectHitbox)) {
            // Left has collided
            // Move player just to right of current hitbox
//            this.setWorldLocationX(rectHitbox.right - getWidth() * .2f);
            collided = 1;
        }
        // The right
        if (this.rectHitboxRight.intersects(rectHitbox)) {
            // Right has collided
            // Move player just to left of current hitbox
//            this.setWorldLocationX(rectHitbox.left - getWidth() * .8f);
            collided = 1;
        }
        // The feet
        if (this.rectHitboxBottom.intersects(rectHitbox)) {
            // Feet have collided
            // Move feet to just above current hitbox
//            this.setWorldLocationY(rectHitbox.top - getHeight());
            collided = 2;
        }
        // Now the head
        if (this.rectHitboxTop.intersects(rectHitbox)) {
            // Head has collided. Ouch!
            // Move head to just below current hitbox bottom
//            this.setWorldLocationY(rectHitbox.bottom);
            collided = 3;
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

    public void restorePreviousVelocity() {
            if (getFacing() == LEFT) {
//                isPressingLeft = true;
                setxVelocity(MAX_X_VELOCITY);
            } else {
//                isPressingRight = true;
                setxVelocity(-MAX_X_VELOCITY);
            }
    }


    public int getShieldStrength() {
        return shieldStrength;
    }

    public void reduceShieldStrength(){
        shieldStrength --;
    }

    public void move(long fps, Viewport vp){
        if (this.getWorldLocation().y < vp.getCurrentViewportWorldCentre().y - vp.getMetresToShowY()/2 + getHeight()/2){
            this.setWorldLocationY(vp.getCurrentViewportWorldCentre().y - vp.getMetresToShowY()/2 + getHeight()/2);
        }else if (this.getWorldLocation().y + getHeight() > vp.getMetresToShowY()-2){
            this.setWorldLocationY(vp.getMetresToShowY()-4);
        }
        else if(getxVelocity() != 0) {
            this.setWorldLocationX(this.getWorldLocation().x + getxVelocity() / fps);
        }

        if(getyVelocity() != 0) {
            this.setWorldLocationY(this.getWorldLocation().y + getyVelocity() / fps);
        }
    }
    }
