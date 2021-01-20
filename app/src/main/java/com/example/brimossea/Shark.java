package com.example.brimossea;

public class Shark extends GameObject {
    Shark(float worldStartX, float worldStartY, char type){
        final float HEIGHT = 3;
        final float WIDTH = 4;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setType(type);
        // Choose a Bitmap
        setBitmapName("shark");
        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();
    }
    @Override
    public void update(long fps, float gravity) {

    }
}
