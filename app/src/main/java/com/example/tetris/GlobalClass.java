package com.example.tetris;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class GlobalClass {
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Player loggedInPlayer = null;

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void addPlayer(Player player) {
        players.add(player);
    }

    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player player) {
        loggedInPlayer = player;
    }

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
}
