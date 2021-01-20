package com.example.brimossea;

public class Ocean extends GameObject{


    Ocean(float worldStartX, float worldStartY, char type,int changeBackground){
        final float HEIGHT = 20;
        final float WIDTH = 32;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setType(type);
        // Choose a Bitmap
        if (changeBackground == 0){
            setBitmapName("ocean_left");
        }
        else {
            setBitmapName("ocean_right");
        }
        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();
    }
    @Override
    public void update(long fps, float gravity, Viewport vp) {

    }
}
