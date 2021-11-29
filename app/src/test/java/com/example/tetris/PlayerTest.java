package com.example.tetris;

import junit.framework.TestCase;

public class PlayerTest extends TestCase {

    public void testGetUsername() {
        Player testPlayer = new Player("valaki", "valami");
        assertEquals("valaki", testPlayer.getUsername());
    }

    /*public void testSetUsername() {

    }*/

    public void testGetPasswordHash() {
        Player testPlayer = new Player("valaki", "valami");
        assertEquals("valami", testPlayer.getPasswordHash());
    }

    public void testSetPasswordHash() {
    }

    public void testGetHighscore() {
        Player testPlayer = new Player("valaki", "valami");
        assertEquals(0, testPlayer.getHighscore());
    }

    public void testSetHighscore() {
        Player testPlayer = new Player("valaki", "valami");
        testPlayer.setHighscore(1000);
        assertEquals(1000, testPlayer.getHighscore());
    }
}