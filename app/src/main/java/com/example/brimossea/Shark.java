package com.example.brimossea;

import android.graphics.Rect;
import android.graphics.RectF;

public class Shark extends GameObject {
    final float HEIGHT;
    final float WIDTH;
    final float UPPER_BOUND;
    final float LOWER_BOUND;

    Shark(float worldStartX, float worldStartY, char type){
        HEIGHT = 3;
        WIDTH = 4;
        UPPER_BOUND = worldStartY - 4;
        LOWER_BOUND = worldStartY + 4;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setType(type);
        setMoves(true);
        setActive(true);


        // Choose a Bitmap
        setBitmapName("shark");
        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();
    }
    @Override
    public void update(long fps, float gravity, Viewport vp) {
        this.move(fps,vp);
        setRectHitbox();
    }

    public void move(long fps, Viewport vp){
        if (this.getWorldLocation().y < UPPER_BOUND || this.getWorldLocation().y > LOWER_BOUND){
            this.setyVelocity(getyVelocity() * -1);
        }

//        if (this.getWorldLocation().x < vp.getCurrentViewportWorldCentre().x - vp.getMetresToShowX()/2 + getWidth()/2){
//            this.setWorldLocationX(vp.getCurrentViewportWorldCentre().x - vp.getMetresToShowX()/2 + getWidth()/2);
//        }else if (this.getWorldLocation().x + getWidth() > vp.getCurrentViewportWorldCentre().x + vp.getMetresToShowX()/2){
//            this.setWorldLocationX(vp.getCurrentViewportWorldCentre().x + vp.getMetresToShowX()/2);
//        }
        if(getxVelocity() != 0) {
            this.setWorldLocationX(this.getWorldLocation().x + getxVelocity() / fps);
        }

        if(getyVelocity() != 0) {
            this.setWorldLocationY(this.getWorldLocation().y + getyVelocity() / fps);
        }
    }
}
