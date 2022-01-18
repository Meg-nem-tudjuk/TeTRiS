package com.example.tetris;

import junit.framework.TestCase;

public class PlayerTest extends TestCase {

    public void testGetUsername() {
        Player testPlayer = new Player("valaki", "valami", 0);
        assertEquals("valaki", testPlayer.getUsername());
    }

    public void testGetPasswordHash() {
        Player testPlayer = new Player("valaki", "valami", 0);
        assertEquals("valami", testPlayer.getPasswordHash());
    }

    public void testGetHighscore() {
        Player testPlayer = new Player("valaki", "valami", 0);
        assertEquals(0, testPlayer.getHighscore());
    }

    public void testSetHighscore() {
        Player testPlayer = new Player("valaki", "valami", 0);
        testPlayer.setHighscore(1000);
        assertEquals(1000, testPlayer.getHighscore());
    }
}