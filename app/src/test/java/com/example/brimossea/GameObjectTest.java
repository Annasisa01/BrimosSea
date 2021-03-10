package com.example.brimossea;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class GameObjectTest {

    GameObject go;
    @BeforeEach
    void setUp() {
         go = Mockito.mock(
                GameObject.class,
                Mockito.CALLS_REAL_METHODS
        );
        System.out.println( "Width is " + go.getWidth());

    }

    @AfterEach
    void tearDown() {
        go = null;
    }

    @Test
    void update() throws Exception {
        assertEquals(0, go.getWidth());
        System.out.println( "Width is " + go.getWidth());

    }

    @Test
    void move() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void getBitmapName() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void prepareBitmap() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void getWorldLocation() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void setWorldLocation() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void setBitmapName() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void getWidth() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void getHeight() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void setWidth() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void setHeight() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void isActive() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void isVisible() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void setVisible() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void getType() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void setType() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void setFacing() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void getxVelocity() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void setxVelocity() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void getyVelocity() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void setyVelocity() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void setMoves() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void setActive() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void setRectHitbox() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void getHitbox() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void setWorldLocationY() throws Exception {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void setWorldLocationX() throws Exception {
        //Arrange

        //Act

        //Assert
    }


    @Test
    void testAdd(){

    }

    @Test
    void testHasValue(){

    }
}