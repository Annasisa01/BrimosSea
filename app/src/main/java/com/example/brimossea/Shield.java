package com.example.brimossea;

public class Shield extends GameObject{
    Shield(float worldStartX, float worldStartY, char type){
        final float HEIGHT = 3;
        final float WIDTH = 3;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setType(type);
        // Choose a Bitmap
        setBitmapName("shield");
        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();
    }
    @Override
    public void update(long fps, float gravity, Viewport vp) {

    }

    public String toString() {
        return "isActive: "+isActive() +", isVisible: "+ isVisible();
    }
}
