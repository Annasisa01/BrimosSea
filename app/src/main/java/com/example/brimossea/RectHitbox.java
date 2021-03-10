package com.example.brimossea;

public class RectHitbox {
    private float top;
    private float left;
    private float bottom;
    private float right;

    boolean intersects(RectHitbox rectHitbox){
        boolean hit = false;
        if(this.right > rectHitbox.left
                && this.left < rectHitbox.right ){
            // Intersecting on x axis
            if(this.top < rectHitbox.bottom
                    && this.bottom > rectHitbox.top ){
                // Intersecting on y as well
                // Collision
                hit = true; }
        }
        return hit;
    }
    public void setTop(float top) {
        this.top = top;
    }
    public void setLeft(float left) {
        this.left = left;
    }
    public void setBottom(float bottom) {
        this.bottom = bottom;
    }
    public void setRight(float right) {
        this.right = right;
    }

    public float getTop() {
        return top;
    }

    public float getLeft() {
        return left;
    }

    public float getBottom() {
        return bottom;
    }

    public float getRight() {
        return right;
    }


}
