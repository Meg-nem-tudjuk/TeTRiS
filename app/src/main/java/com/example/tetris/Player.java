package com.example.tetris;

/**
 * class for players
 * <p>
 *     This class lets you store players as an object
 * </p>
 */

public class Player {

    private final String username;
    private final String passwordHash;
    private int highscore;

    /**
     * Constructor
     * @param username the name chosen by the player when registering
     * @param passwordHash the payer's password encoded in MD5
     * @param highscore the player's highscore, which is 0 by default
     */
    public Player(String username, String passwordHash, int highscore) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.highscore = highscore;
    }

    /**
     *returns the player's username
     * @return the player's username
     */
    public String getUsername() {
        return username;
    }

    /**
     *returns the player's password hash
     * @return the player's password encoded in MD5
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     *returns the player's highscore
     * @return the player's highscore
     */
    public int getHighscore() {
        return highscore;
    }

    /**
     *sets the player's highscore
     * @param score the player's highscore
     */
    public void setHighscore(int score) {
        this.highscore = score;
    }
}
