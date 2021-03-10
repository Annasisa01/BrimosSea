package com.example.brimossea;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStateTest {

    private PlayerState newState;

    @BeforeEach
    void setUp() {
        newState = new PlayerState();
    }

    @AfterEach
    void tearDown() {
        newState = null;
    }

    @org.junit.jupiter.api.Test
    void saveLocation() throws Exception {

    }

    @Test
    void getLives() throws Exception {
        int expected1 = 3;
        int expected2 = 4;
        int expected3 = 2;
        int actual = newState.getLives();
        assertEquals(expected1,actual);
        newState.addLife();
        actual = newState.getLives();
        assertEquals(expected2,actual);
        newState.loseLife();
        newState.loseLife();
        actual = newState.getLives();
        assertEquals(expected3,actual);
    }

    @Test
    void gotCreditGetCredit() throws Exception {
        int expected2 = 5;
        int expected1 = 0;
        int actual = newState.getCredits();
        assertEquals(expected1,actual);
        newState.gotCredit();
        actual = newState.getCredits();
        assertEquals(expected2,actual);
    }


    @Test
    void loseLife() throws Exception {
        int expected1 = 2;
        int expected2 = 1;
        newState.loseLife();
        int actual = newState.getLives();
        assertEquals(expected1,actual);
        newState.loseLife();
        actual = newState.getLives();
        assertEquals(expected2,actual);
    }

    @Test
    void addLife() throws Exception {
        int expected1 = 4;
        int expected2 = 5;
        newState.addLife();
        int actual = newState.getLives();
        assertEquals(expected1,actual);
        newState.addLife();
        actual = newState.getLives();
        assertEquals(expected2,actual);
    }

    @Test
    void activateShield() throws Exception {
        assertFalse(newState.hasShield);
        newState.activateShield();
        assertTrue(newState.hasShield);
    }

    @Test
    void deactivateShield() throws Exception {
        assertFalse(newState.hasShield);
        newState.activateShield();
        assertTrue(newState.hasShield);
        newState.deactivateShield();
        assertFalse(newState.hasShield);
    }

    @Test
    void getShieldStartTime() throws Exception {
//        There is no way to accurately test this method because System.currentTimeMillis() would excute when we call activateShield()
//        and System.currentTimeMillis() would have changed before we can save a test value(actual)
    }

}