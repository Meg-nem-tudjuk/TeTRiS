package com.example.tetris;

public class Player {

    private int id;
    private String username;
    private String passwordHash;
    private int highscore = 0;

    public Player(String username, String passwordHash) {
        id = 0; //TODO: calculate id
        this.username = username;
        this.passwordHash = passwordHash;
        this.id = GlobalClass.getPlayers().size();
    }

    public String getUsername() {
        return username;
    }

    /*public void setUsername(String username) {
        this.username = username;
    }*/

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int score) {
        this.highscore = score;
    }
}
