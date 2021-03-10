package com.example.brimossea;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ViewportTest {
    Viewport vp;

    @BeforeEach
    void setUp(){
        int x = 34;
        int y = 20;
        vp = new Viewport(x,y);
    }

    @AfterEach
    void tearDown(){
        vp = null;
    }

    @Test
    void setWorldCentre() throws Exception {
        int expectedX = 250;
        int expectedY = 250;
        vp.setWorldCentre(expectedX,expectedY);
        assertEquals(expectedX,vp.getCurrentViewportWorldCentre().x);
        assertEquals(expectedY,vp.getCurrentViewportWorldCentre().y);
    }

    @Test
    void getScreenWidth() throws Exception {
        int expected = 34;
        int fake = 12;
        assertEquals(expected,vp.getScreenWidth());
        assertNotEquals(fake,vp.getScreenWidth());
    }

    @Test
    void getScreenHeight() throws Exception {
        int expected = 20;
        int fake = 12;
        assertEquals(expected,vp.getScreenHeight());
        assertNotEquals(fake,vp.getScreenHeight());
    }

    @Test
    void getPixelsPerMetreX() throws Exception {
        int expectedX = vp.getScreenWidth()/ 32;
        assertEquals(expectedX,vp.getPixelsPerMetreX());
    }

    @Test
    void worldToScreen() throws Exception {
        int objectX = 10;
        int objectY = 30;
        int objectWidth = 2;
        int objectHeight = 2;
        int left = (int) (vp.getScreenWidth()/2 -
                ((vp.getCurrentViewportWorldCentre().x - objectX)
                        * vp.getPixelsPerMetreX()));
        int top =  (int) (vp.getScreenHeight()/2 -
                ((vp.getCurrentViewportWorldCentre().y - objectY)
                        * vp.getPixelsPerMetreY()));
        int right = (int) (left +
                (objectWidth *
                        vp.getPixelsPerMetreX()));

        int bottom = (int) (top +
                (objectHeight *
                        vp.getPixelsPerMetreY()));
        vp.worldToScreen(objectX,objectY,objectWidth,objectHeight);
        assertEquals(left,vp.getConvertedRect().left);
        assertEquals(top,vp.getConvertedRect().top);
        assertEquals(right,vp.getConvertedRect().right);
        assertEquals(bottom,vp.getConvertedRect().bottom);


    }

    @Test
    void clipObjects() throws Exception {
        int objectX = 10;
        int objectY = 30;
        int objectWidth = 2;
        int objectHeight = 2;


    }

    @Test
    void getMetresToShowX() throws Exception {
        int expected = 34;
        assertEquals(expected,vp.getMetresToShowX());
    }

    @Test
    void getMetresToShowY() throws Exception {
        int expected = 20;
        assertEquals(expected,vp.getMetresToShowY());
    }

    @Test
    void getCurrentViewportWorldCentre() throws Exception {
        int expected1 = 17;
        int expected2 = 10;
        vp.setWorldCentre(17,10);
        assertEquals(expected1,vp.getCurrentViewportWorldCentre().x);
        assertEquals(expected2,vp.getCurrentViewportWorldCentre().y);
    }

    @Test
    void updateViewport() throws Exception {
        float temp = vp.getCurrentViewportWorldCentre().x;
        vp.updateViewport();
        float expected = vp.getCurrentViewportWorldCentre().x;
        assertTrue(expected > temp);
    }

    @Test
    void getPixelsPerMetreY() throws Exception {
        int expected = 20/18;
        assertEquals(expected,vp.getPixelsPerMetreY());
    }
}