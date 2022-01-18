package com.example.tetris;

import junit.framework.TestCase;

public class GlobalClassTest extends TestCase {

    public void testAddPlayer() {
        Player testPlayer = new Player("valaki", "valami",0);
        GlobalClass.addPlayer(testPlayer);
        assertEquals(testPlayer, GlobalClass.getPlayers().get(GlobalClass.getPlayers().size() - 1));
    }

    public void testSetLoggedInPlayer() {
        Player testPlayer = new Player("valaki", "valami", 0);
        GlobalClass.setLoggedInPlayer(testPlayer);
        assertEquals(testPlayer, GlobalClass.getLoggedInPlayer());
    }

    public void testHash() {
        assertEquals("249d6c99eff6d0ef6c470e4254629323", GlobalClass.hash("valami"));
    }
}