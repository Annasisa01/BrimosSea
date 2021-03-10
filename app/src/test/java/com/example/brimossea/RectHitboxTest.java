package com.example.brimossea;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectHitboxTest {
//You need to comment this thing.
    RectHitbox rectHitbox;
    @BeforeEach
    void setUp() {
        rectHitbox = new RectHitbox();
        rectHitbox.setLeft(10);
        rectHitbox.setBottom(70);
        rectHitbox.setRight(30);
        rectHitbox.setTop(10);
    }

    @AfterEach
    void tearDown() {
        rectHitbox = null;
    }

    @Test
    void intersects() throws Exception {
        RectHitbox testRect = new RectHitbox();
        testRect.setLeft(10);
        testRect.setBottom(70);
        testRect.setRight(30);
        testRect.setTop(10);
        assertTrue(rectHitbox.intersects(testRect));

        testRect.setLeft(30);
        testRect.setBottom(70);
        testRect.setRight(30);
        testRect.setTop(10);
        assertFalse(rectHitbox.intersects(testRect));
    }

    @Test
    void setTop() throws Exception {
        float expected = 30;
        float fake = 3;
        rectHitbox.setTop(expected);
        assertEquals(expected,rectHitbox.getTop());
        assertNotEquals(fake,rectHitbox.getTop());
    }

    @Test
    void setLeft() throws Exception {
        float expected = 10;
        float fake = 15;
        rectHitbox.setLeft(expected);
        assertEquals(expected,rectHitbox.getLeft());
        assertNotEquals(fake,rectHitbox.getLeft());
    }

    @Test
    void setBottom() throws Exception {
        float expected = 40;
        float fake = 5;
        rectHitbox.setBottom(expected);
        assertEquals(expected,rectHitbox.getBottom());
        assertNotEquals(fake,rectHitbox.getBottom());
    }

    @Test
    void setRight() throws Exception {
        float expected = 50;
        float fake = 35;
        rectHitbox.setRight(expected);
        assertEquals(expected,rectHitbox.getRight());
        assertNotEquals(fake,rectHitbox.getRight());
    }

    @Test
    void getRight() throws Exception{
        float expResult = 1000;
        float fake = 300;
        rectHitbox.setRight(1000);
        float result = rectHitbox.getRight();
        assertEquals(expResult, result);
        assertNotEquals(fake,result);
    }

    @Test
    void getLeft() throws Exception{
        float expResult = 700;
        float fake = 300;
        rectHitbox.setLeft(700);
        float result = rectHitbox.getLeft();
        assertEquals(expResult, result);
        assertNotEquals(fake,result);
    }

    @Test
    void getTop() throws Exception{
        float expResult = 700;
        float fake = 300;
        rectHitbox.setTop(700);
        float result = rectHitbox.getTop();
        assertEquals(expResult, result);
        assertNotEquals(fake,result);
    }

    @Test
    void getBottom() throws Exception{
        float expResult = 700;
        float fake = 300;
        rectHitbox.setLeft(700);
        float result = rectHitbox.getLeft();
        assertEquals(expResult, result);
        assertNotEquals(fake,result);
    }
}