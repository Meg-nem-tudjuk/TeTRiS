package com.example.tetris;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class for operations used by multiple activities
 */
public class GlobalClass {
    private static final String FILE_NAME = "players.txt";
    /**
     * ArrayList of all players
     */
    private static ArrayList<Player> players = new ArrayList<Player>();
    /**
     * The player who's currently logged in
     */
    private static Player loggedInPlayer = null;

    /**
     * returns the ArrayList of all players
     * @return the ArrayList of all players
     */
    public static ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * adds a player to the list
     * @param player the player we want to add
     */
    public static void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * returns the player currently logged in
     * @return the player currently logged in
     */
    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    /**
     * sets the player currently logged in
     * @param player the player currently logged in
     */
    public static void setLoggedInPlayer(Player player) {
        loggedInPlayer = player;
    }

    /**
     * encodes the password in MD5
     * @param s the password
     * @return the encoded password
     */
    public static String hash(String s) {
        String MD5 = "MD5";
        try {
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMsgDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMsgDigest);
                while (h.length() < 2) h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * returns the list of players sorted by their highscores
     * @return the leaderboard
     */
    public static ArrayList<Player> getLeaderboard() {
        ArrayList<Player> p = new ArrayList<Player>(getPlayers());
        ArrayList<Integer> highscores = new ArrayList<Integer>();
        ArrayList<Player> leaderboard = new ArrayList<Player>();
        for (int i = 0; i < p.size(); i++) {
            highscores.add(p.get(i).getHighscore());
        }
        Collections.sort(highscores);
        Collections.reverse(highscores);
        for (int i = 0; i < highscores.size(); i++) {
            for (int j = 0; j < p.size(); j++) {
                if (highscores.get(i) == p.get(j).getHighscore()) {
                    leaderboard.add(p.get(j));
                    p.remove(j);
                    break;
                }
            }
        }
        return leaderboard;
    }

    /**
     * saves every player with their data
     * @param c context
     * @param s the data we want to save
     */
    public static void save(Context c, String s) {
        FileOutputStream fos = null;
        try {
            fos = c.openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(s.getBytes());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fos != null) {
                try {
                    fos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * reads the saved data
     * @param c context
     * @return the saved data
     */
    public static String load(Context c) {
        FileInputStream fis = null;
        try {
            fis = c.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            return sb.toString();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
