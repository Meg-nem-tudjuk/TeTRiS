package com.example.tetris;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * this is where the game takes place
 */
public class GameActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private static final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private static final int tileSize = screenWidth / 10;
    /**
     * the representation of the board
     */
    private LinearLayout[][] board = new LinearLayout[13][10];
    private Runnable uiThing, removeTask, updateTextView;
    private int score = 0;
    private TextView scoreTextView;
    private Timer timer;
    boolean ongoing = true;
    /**
     * the type of the current piece
     */
    private char currentPiece = 'x';
    /**
     * the position of the current piece's first tile and its rotation
     */
    private int firstX = -1, firstY = -1, rotation = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        relativeLayout = findViewById(R.id.relativeLayout);
        relativeLayout.getLayoutParams().height = tileSize * 13;

        Button rotate = findViewById(R.id.rotateButton);
        Button down = findViewById(R.id.downButton);
        Button left = findViewById(R.id.leftButton);
        Button right = findViewById(R.id.rightButton);
        scoreTextView = findViewById(R.id.score);

        uiThing = new Runnable() {
            @Override
            public void run() {
                disableTiles();
                addNewTiles();
            }
        };

        removeTask = new Runnable() {
            @Override
            public void run() {
                removeLines();
            }
        };

        updateTextView = new Runnable() {
            @Override
            public void run() {
                scoreTextView.setText("Pontszám: " + Integer.toString(score));
            }
        };

        addNewTiles();
        start();

        down.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (canDescend()) descend();
                else {
                    removeLines();
                    if (ongoing) {
                        score++;
                        scoreTextView.setText("Pontszám: " + Integer.toString(score));
                    }
                }
            }
        });

        rotate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (canRotate()) rotate();
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (canMoveLeft()) moveLeft();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (canMoveRight()) moveRight();
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timer.cancel();
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setPositiveButton("Folytatás", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        start();
                    }
                });
                builder.setTitle("Játék megállítva");
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    /**
     * places a new piece on the board
     */
    private void addNewTiles() {
        Random r = new Random();
        int n = r.nextInt(7);
        int c = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        rotation = 0;

        switch (n) {
            case 0: //I
                currentPiece = 'I';

                if (board[0][4] != null || board[1][4] != null || board[2][4] != null || board[3][4] != null) {
                    ongoing = false;
                    requestNewGame();
                    break;
                }

                firstX = 4;
                firstY = 0;

                board[0][4] = new LinearLayout(this);
                board[0][4].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[0][4].setX((float) 4 * tileSize);
                board[0][4].setY((float) 0);
                board[0][4].setBackgroundColor(c);
                relativeLayout.addView(board[0][4]);

                board[1][4] = new LinearLayout(this);
                board[1][4].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[1][4].setX((float) 4 * tileSize);
                board[1][4].setY((float) tileSize);
                board[1][4].setBackgroundColor(c);
                relativeLayout.addView(board[1][4]);

                board[2][4] = new LinearLayout(this);
                board[2][4].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[2][4].setX((float) 4 * tileSize);
                board[2][4].setY((float) 2 * tileSize);
                board[2][4].setBackgroundColor(c);
                relativeLayout.addView(board[2][4]);

                board[3][4] = new LinearLayout(this);
                board[3][4].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[3][4].setX((float) 4 * tileSize);
                board[3][4].setY((float) 3 * tileSize);
                board[3][4].setBackgroundColor(c);
                relativeLayout.addView(board[3][4]);
                break;
            case 1: //Z
                currentPiece = 'Z';

                if (board[0][4] != null || board[0][5] != null || board[1][5] != null || board[1][6] != null) {
                    ongoing = false;
                    requestNewGame();
                    break;
                }

                firstX = 4;
                firstY = 0;

                board[0][4] = new LinearLayout(this);
                board[0][4].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[0][4].setX((float) 4 * tileSize);
                board[0][4].setY((float) 0);
                board[0][4].setBackgroundColor(c);
                relativeLayout.addView(board[0][4]);

                board[0][5] = new LinearLayout(this);
                board[0][5].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[0][5].setX((float) 5 * tileSize);
                board[0][5].setY((float) 0);
                board[0][5].setBackgroundColor(c);
                relativeLayout.addView(board[0][5]);

                board[1][5] = new LinearLayout(this);
                board[1][5].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[1][5].setX((float) 5 * tileSize);
                board[1][5].setY((float) tileSize);
                board[1][5].setBackgroundColor(c);
                relativeLayout.addView(board[1][5]);

                board[1][6] = new LinearLayout(this);
                board[1][6].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[1][6].setX((float) 6 * tileSize);
                board[1][6].setY((float) tileSize);
                board[1][6].setBackgroundColor(c);
                relativeLayout.addView(board[1][6]);
                break;
            case 2: //S
                currentPiece = 'S';

                if (board[1][4] != null || board[1][5] != null || board[0][5] != null || board[0][6] != null) {
                    ongoing = false;
                    requestNewGame();
                    break;
                }

                firstX = 5;
                firstY = 0;

                board[1][4] = new LinearLayout(this);
                board[1][4].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[1][4].setX((float) 4 * tileSize);
                board[1][4].setY((float) tileSize);
                board[1][4].setBackgroundColor(c);
                relativeLayout.addView(board[1][4]);

                board[1][5] = new LinearLayout(this);
                board[1][5].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[1][5].setX((float) 5 * tileSize);
                board[1][5].setY((float) tileSize);
                board[1][5].setBackgroundColor(c);
                relativeLayout.addView(board[1][5]);

                board[0][5] = new LinearLayout(this);
                board[0][5].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[0][5].setX((float) 5 * tileSize);
                board[0][5].setY((float) 0);
                board[0][5].setBackgroundColor(c);
                relativeLayout.addView(board[0][5]);

                board[0][6] = new LinearLayout(this);
                board[0][6].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[0][6].setX((float) 6 * tileSize);
                board[0][6].setY((float) 0);
                board[0][6].setBackgroundColor(c);
                relativeLayout.addView(board[0][6]);
                break;
            case 3: //T
                currentPiece = 'T';

                if (board[0][5] != null || board[1][4] != null || board[1][5] != null || board[1][6] != null) {
                    ongoing = false;
                    requestNewGame();
                    break;
                }

                firstX = 5;
                firstY = 0;

                board[0][5] = new LinearLayout(this);
                board[0][5].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[0][5].setX((float) 5 * tileSize);
                board[0][5].setY((float) 0);
                board[0][5].setBackgroundColor(c);
                relativeLayout.addView(board[0][5]);

                board[1][4] = new LinearLayout(this);
                board[1][4].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[1][4].setX((float) 4 * tileSize);
                board[1][4].setY((float) tileSize);
                board[1][4].setBackgroundColor(c);
                relativeLayout.addView(board[1][4]);

                board[1][5] = new LinearLayout(this);
                board[1][5].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[1][5].setX((float) 5 * tileSize);
                board[1][5].setY((float) tileSize);
                board[1][5].setBackgroundColor(c);
                relativeLayout.addView(board[1][5]);

                board[1][6] = new LinearLayout(this);
                board[1][6].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[1][6].setX((float) 6 * tileSize);
                board[1][6].setY((float) tileSize);
                board[1][6].setBackgroundColor(c);
                relativeLayout.addView(board[1][6]);
                break;
            case 4: //L
                currentPiece = 'L';

                if (board[0][4] != null || board[1][4] != null || board[2][4] != null || board[2][5] != null) {
                    ongoing = false;
                    requestNewGame();
                    break;
                }

                firstX = 4;
                firstY = 0;

                board[0][4] = new LinearLayout(this);
                board[0][4].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[0][4].setX((float) 4 * tileSize);
                board[0][4].setY((float) 0);
                board[0][4].setBackgroundColor(c);
                relativeLayout.addView(board[0][4]);

                board[1][4] = new LinearLayout(this);
                board[1][4].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[1][4].setX((float) 4 * tileSize);
                board[1][4].setY((float) tileSize);
                board[1][4].setBackgroundColor(c);
                relativeLayout.addView(board[1][4]);

                board[2][4] = new LinearLayout(this);
                board[2][4].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[2][4].setX((float) 4 * tileSize);
                board[2][4].setY((float) 2 * tileSize);
                board[2][4].setBackgroundColor(c);
                relativeLayout.addView(board[2][4]);

                board[2][5] = new LinearLayout(this);
                board[2][5].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[2][5].setX((float) 5 * tileSize);
                board[2][5].setY((float) 2 * tileSize);
                board[2][5].setBackgroundColor(c);
                relativeLayout.addView(board[2][5]);
                break;
            case 5: //J
                currentPiece = 'J';

                if (board[0][5] != null || board[1][5] != null || board[2][5] != null || board[2][4] != null) {
                    ongoing = false;
                    requestNewGame();
                    break;
                }

                firstX = 5;
                firstY = 0;

                board[0][5] = new LinearLayout(this);
                board[0][5].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[0][5].setX((float) 5 * tileSize);
                board[0][5].setY((float) 0);
                board[0][5].setBackgroundColor(c);
                relativeLayout.addView(board[0][5]);

                board[1][5] = new LinearLayout(this);
                board[1][5].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[1][5].setX((float) 5 * tileSize);
                board[1][5].setY((float) tileSize);
                board[1][5].setBackgroundColor(c);
                relativeLayout.addView(board[1][5]);

                board[2][5] = new LinearLayout(this);
                board[2][5].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[2][5].setX((float) 5 * tileSize);
                board[2][5].setY((float) 2 * tileSize);
                board[2][5].setBackgroundColor(c);
                relativeLayout.addView(board[2][5]);

                board[2][4] = new LinearLayout(this);
                board[2][4].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[2][4].setX((float) 4 * tileSize);
                board[2][4].setY((float) 2 * tileSize);
                board[2][4].setBackgroundColor(c);
                relativeLayout.addView(board[2][4]);
                break;
            case 6: //O
                currentPiece = 'O';

                if (board[0][4] != null || board[1][4] != null || board[0][5] != null || board[1][5] != null) {
                    ongoing = false;
                    requestNewGame();
                    break;
                }

                board[0][4] = new LinearLayout(this);
                board[0][4].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[0][4].setX((float) 4 * tileSize);
                board[0][4].setY((float) 0);
                board[0][4].setBackgroundColor(c);
                relativeLayout.addView(board[0][4]);

                board[1][4] = new LinearLayout(this);
                board[1][4].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[1][4].setX((float) 4 * tileSize);
                board[1][4].setY((float) tileSize);
                board[1][4].setBackgroundColor(c);
                relativeLayout.addView(board[1][4]);

                board[0][5] = new LinearLayout(this);
                board[0][5].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[0][5].setX((float) 5 * tileSize);
                board[0][5].setY((float) 0);
                board[0][5].setBackgroundColor(c);
                relativeLayout.addView(board[0][5]);

                board[1][5] = new LinearLayout(this);
                board[1][5].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
                board[1][5].setX((float) 5 * tileSize);
                board[1][5].setY((float) tileSize);
                board[1][5].setBackgroundColor(c);
                relativeLayout.addView(board[1][5]);
                break;
        }
    }

    /**
     * starts the timer that moves down the current piece every second
     */
    private void start() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (canDescend()) descend();
                else {
                    GameActivity.this.runOnUiThread(removeTask);
                    if (ongoing) {
                        score++;
                        GameActivity.this.runOnUiThread(updateTextView);
                    }
                }
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    /**
     * returns whether the current piece can descend or not
     * @return whether the current piece can descend or not
     */
    private Boolean canDescend() {
        Boolean result = true;
        for (int i = 12; i >= 0; i--) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] != null && board[i][j].isEnabled() && (i == 12 || (board[i + 1][j] != null && !board[i + 1][j].isEnabled()))) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * descends the current piece
     */
    private void descend() {
        for (int i = 11; i >= 0; i--) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] != null && board[i][j].isEnabled()) {
                    board[i][j].setY(board[i][j].getY() + tileSize);
                    board[i + 1][j] = board[i][j];
                    board[i][j] = null;
                }
            }
        }
        firstY++;
    }

    /**
     * retrurns whether or not the current piece can be moved right
     * @return whether or not the current piece can be moved right
     */
    private Boolean canMoveRight(){
        boolean result = true;
        for (int j = 9; j >= 0; j--) {
            for (int i = 0; i < 13; i++) {
                if (board[i][j] != null && board[i][j].isEnabled() && (j == 9 || (board[i][j + 1] != null && !board[i][j + 1].isEnabled()))) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * moves the current piece right
     */
    private void moveRight() {
        for (int j = 8; j >= 0; j--) {
            for (int i = 0; i < 13; i++) {
                if (board[i][j] != null && board[i][j].isEnabled()) {
                    board[i][j].setX(board[i][j].getX() + tileSize);
                    board[i][j + 1] = board[i][j];
                    board[i][j] = null;
                }
            }
        }
        firstX++;
    }

    /**
     * returns whether or not the current piece can be moved left
     * @return whether or not the current piece can be moved left
     */
    private Boolean canMoveLeft() {
        boolean result = true;
        for (int j = 0; j <= 9; j++) {
            for (int i = 0; i < 13; i++) {
                if (board[i][j] != null && board[i][j].isEnabled() && (j == 0 || (board[i][j - 1] != null && !board[i][j - 1].isEnabled()))) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * moves the current piece left
     */
    private void moveLeft() {
        for (int j = 1; j <= 9; j++) {
            for (int i = 0; i < 13; i++) {
                if (board[i][j] != null && board[i][j].isEnabled()) {
                    board[i][j].setX(board[i][j].getX() - tileSize);
                    board[i][j - 1] = board[i][j];
                    board[i][j] = null;
                }
            }
        }
        firstX--;
    }

    /**
     * disables the pieces that have already reached the bottom
     */
    private void disableTiles() {
        for (int i = 12; i >= 0; i--) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] != null) board[i][j].setEnabled(false);
            }
        }
    }

    /**
     * removes completed lines
     */
    private void removeLines() {
        boolean removeCurrent;
        for (int i = 12; i > 0; i--) {
            removeCurrent = true;
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == null) {
                    removeCurrent = false;
                    break;
                }
            }
            if (removeCurrent) {
                for (int j = 0; j < 10; j++) {
                    relativeLayout.removeView(board[i][j]);
                    for (int k = i - 1; k >= 0; k--) {
                        if (board[k][j] != null) board[k][j].setY(board[k][j].getY() + tileSize);
                        board[k + 1][j] = board[k][j];
                        board[k][j] = null;
                    }
                }
                i++;
                score += 1000;
            }
        }
        disableTiles();
        addNewTiles();
    }

    /**
     * saves current highscore
     */
    private void saveHighscore() {
        GlobalClass.getLoggedInPlayer().setHighscore(score);
        String file = GlobalClass.load(this);
        String toSave = "";
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.length() > 0) {
                String[] splitLine = line.split("\\|");
                if (splitLine[0].equals(GlobalClass.getLoggedInPlayer().getUsername())) toSave += GlobalClass.getLoggedInPlayer().getUsername() + "|" + GlobalClass.getLoggedInPlayer().getPasswordHash() + "|" + score + "\n";
                else toSave += line + "\n";
            }
        }
        GlobalClass.save(this, toSave);
    }

    /**
     * requests a new game after losing
     */
    private void requestNewGame() {
        timer.cancel();
        if (score > GlobalClass.getLoggedInPlayer().getHighscore()) saveHighscore();
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                GameActivity.this.recreate();
            }
        });
        builder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setTitle("Vesztettél");
        builder.setMessage("Szeretnél új játékot kezdeni?");
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * returns whether or not the current piece can be rotated
     * @return whether or not the current piece can be rotated
     */
    private boolean canRotate() {
        switch (currentPiece) {
            case 'I':
                if (rotation == 0) {
                    if (firstX > 6) return false;
                    for (int i = firstX + 1; i < firstX + 4; i++) {
                        if (board[firstY][i] != null) return false;
                    }
                    return true;
                }
                if (firstY > 9) return false;
                for (int i = firstY + 1; i < firstY + 4; i++) {
                    if (board[i][firstX] != null) return false;
                }
                return true;
            case 'Z':
                if (rotation == 0) {
                    if (firstY > 10) return false;
                    if (board[firstY + 1][firstX] != null || board[firstY + 2][firstX] != null) return false;
                    return true;
                }
                if (firstX > 8) return false;
                if (board[firstY][firstX - 1] != null || board[firstY + 1][firstX + 1] != null) return false;
                return true;
            case 'S':
                if (rotation == 0) {
                    if (firstY > 10) return false;
                    if (board[firstY + 1][firstX + 1] != null || board[firstY + 2][firstX + 1] != null) return false;
                    return true;
                }
                if (firstX < 1) return false;
                if (board[firstY][firstX + 1] != null || board[firstY + 1][firstX - 1] != null) return false;
                return true;
            case 'T':
                switch (rotation) {
                    case 0:
                        if (firstY > 10) return false;
                        return board[firstY + 2][firstX] == null;
                    case 1:
                        if (firstX < 1) return false;
                        return board[firstY + 1][firstX - 1] == null;
                    case 2:
                        if (firstY < 1) return false;
                        return board[firstY - 1][firstX + 1] == null;
                    case 3:
                        if (firstX > 8) return false;
                        return board[firstY + 1][firstX + 1] == null;
                }
                break;
            case 'L':
                switch (rotation) {
                    case 0:
                        if (firstX > 7) return false;
                        if (board[firstY + 1][firstX + 1] != null || board[firstY + 1][firstX + 2] != null) return false;
                        return true;
                    case 1:
                        if (firstY < 1) return false;
                        if (board[firstY - 1][firstX] != null || board[firstY - 1][firstX + 1] != null || board[firstY + 1][firstX + 1] != null) return false;
                        return true;
                    case 2:
                        if (firstX > 7) return false;
                        if (board[firstY + 2][firstX] != null || board[firstY + 1][firstX + 2] != null || board[firstY + 2][firstX + 2] != null) return false;
                        return true;
                    case 3:
                        if (firstY < 1) return false;
                        if (board[firstY][firstX - 2] != null || board[firstY - 1][firstX - 2] != null) return false;
                        return true;
                }
                break;
            case 'J':
                switch (rotation) {
                    case 0:
                        if (firstX > 8) return false;
                        if (board[firstY + 1][firstX - 1] != null || board[firstY + 2][firstX + 1] != null) return false;
                        return true;
                    case 1:
                        if (firstY < 1) return false;
                        if (board[firstY - 1][firstX] != null || board[firstY - 1][firstX + 1] != null) return false;
                        return true;
                    case 2:
                        if (firstX > 7) return false;
                        if (board[firstY + 1][firstX + 1] != null || board[firstY + 1][firstX + 2] != null || board[firstY + 2][firstX + 2] != null) return false;
                        return true;
                    case 3:
                        if (firstY < 1) return false;
                        if (board[firstY + 1][firstX] != null || board[firstY + 1][firstX + 1] != null || board[firstY - 1][firstX] != null) return false;
                        return true;
                }
                break;
        }
        return true;
    }

    /**
     * rotates the current piece
     */
    private void rotate() {
        switch (currentPiece) {
            case 'I':
                if (rotation == 0) {
                    for (int i = 1; i < 4; i++) {
                        board[firstY + i][firstX].setY(board[firstY][firstX].getY());
                        board[firstY + i][firstX].setX(board[firstY][firstX].getX() + (i * tileSize));
                        board[firstY][firstX + i] = board[firstY + i][firstX];
                        board[firstY + i][firstX] = null;
                    }
                    rotation = 1;
                }
                else {
                    for (int i = 1; i < 4; i++) {
                        board[firstY][firstX + i].setX(board[firstY][firstX].getX());
                        board[firstY][firstX + i].setY(board[firstY][firstX].getY() + (i * tileSize));
                        board[firstY + i][firstX] = board[firstY][firstX + i];
                        board[firstY][firstX + i] = null;
                    }
                    rotation = 0;
                }
                break;
            case 'Z':
                if (rotation == 0) {
                    board[firstY][firstX].setY(board[firstY][firstX].getY() + tileSize);
                    board[firstY + 1][firstX + 2].setX(board[firstY][firstX].getX());
                    board[firstY + 1][firstX + 2].setY(board[firstY + 1][firstX + 2].getY() + tileSize);

                    board[firstY + 1][firstX] = board[firstY][firstX];
                    board[firstY][firstX] = null;
                    board[firstY + 2][firstX] = board[firstY + 1][firstX + 2];
                    board[firstY + 1][firstX + 2] = null;

                    rotation = 1;
                    firstX++;
                }
                else {
                    board[firstY + 1][firstX - 1].setY(board[firstY + 1][firstX - 1].getY() - tileSize);
                    board[firstY + 2][firstX - 1].setX(board[firstY + 2][firstX - 1].getX() + (2 * tileSize));
                    board[firstY + 2][firstX - 1].setY(board[firstY + 1][firstX].getY());

                    board[firstY][firstX - 1] = board[firstY + 1][firstX - 1];
                    board[firstY + 1][firstX - 1] = null;
                    board[firstY + 1][firstX + 1] = board[firstY + 2][firstX - 1];
                    board[firstY + 2][firstX - 1] = null;

                    rotation = 0;
                    firstX--;
                }
                break;
            case 'S':
                if (rotation == 0) {
                    board[firstY][firstX + 1].setY(board[firstY][firstX + 1].getY() + tileSize);
                    board[firstY + 1][firstX - 1].setX(board[firstY][firstX + 1].getX());
                    board[firstY + 1][firstX - 1].setY(board[firstY + 1][firstX - 1].getY() + tileSize);

                    board[firstY + 1][firstX + 1] = board[firstY][firstX + 1];
                    board[firstY][firstX + 1] = null;
                    board[firstY + 2][firstX + 1] = board[firstY + 1][firstX - 1];
                    board[firstY + 1][firstX - 1] = null;

                    rotation = 1;
                }
                else {
                    board[firstY + 1][firstX + 1].setY(board[firstY + 1][firstX + 1].getY() - tileSize);
                    board[firstY + 2][firstX + 1].setY(board[firstY + 1][firstX].getY());
                    board[firstY + 2][firstX + 1].setX(board[firstY + 2][firstX + 1].getX() - (2 * tileSize));

                    board[firstY][firstX + 1] = board[firstY + 1][firstX + 1];
                    board[firstY + 1][firstX + 1] = null;
                    board[firstY + 1][firstX - 1] = board[firstY + 2][firstX + 1];
                    board[firstY + 2][firstX + 1] = null;

                    rotation = 0;
                }
                break;
            case 'T':
                switch (rotation) {
                    case 0:
                        board[firstY + 1][firstX - 1].setX(board[firstY][firstX].getX());
                        board[firstY + 1][firstX - 1].setY(board[firstY + 1][firstX - 1].getY() + tileSize);

                        board[firstY + 2][firstX] = board[firstY + 1][firstX - 1];
                        board[firstY + 1][firstX - 1] = null;

                        rotation = 1;
                        break;
                    case 1:
                        board[firstY][firstX].setY(board[firstY + 1][firstX].getY());
                        board[firstY][firstX].setX(board[firstY][firstX].getX() - tileSize);

                        board[firstY + 1][firstX - 1] = board[firstY][firstX];
                        board[firstY][firstX] = null;

                        firstY++;
                        firstX--;
                        rotation = 2;
                        break;
                    case 2:
                        board[firstY][firstX + 2].setX(board[firstY][firstX + 1].getX());
                        board[firstY][firstX + 2].setY(board[firstY][firstX + 2].getY() - tileSize);

                        board[firstY - 1][firstX + 1] = board[firstY][firstX + 2];
                        board[firstY][firstX + 2] = null;

                        firstY--;
                        firstX++;
                        rotation = 3;
                        break;
                    case 3:
                        board[firstY + 2][firstX].setY(board[firstY + 1][firstX].getY());
                        board[firstY + 2][firstX].setX(board[firstY + 2][firstX].getX() + tileSize);

                        board[firstY + 1][firstX + 1] = board[firstY + 2][firstX];
                        board[firstY + 2][firstX] = null;

                        rotation = 0;
                        break;
                }
                break;
            case 'L':
                switch (rotation) {
                    case 0:
                        board[firstY + 2][firstX + 1].setY(board[firstY + 2][firstX + 1].getY() - tileSize);
                        board[firstY][firstX].setY(board[firstY + 1][firstX].getY());
                        board[firstY][firstX].setX(board[firstY][firstX].getX() + (2 * tileSize));

                        board[firstY + 1][firstX + 1] = board[firstY + 2][firstX + 1];
                        board[firstY + 2][firstX + 1] = null;
                        board[firstY + 1][firstX + 2] = board[firstY][firstX];
                        board[firstY][firstX] = null;

                        firstY++;
                        rotation = 1;
                        break;
                    case 1:
                        board[firstY][firstX].setY(board[firstY][firstX].getY() - tileSize);
                        board[firstY][firstX + 1].setY(board[firstY][firstX + 1].getY() - tileSize);
                        board[firstY][firstX + 2].setX(board[firstY][firstX + 2].getX() - tileSize);
                        board[firstY + 1][firstX].setX(board[firstY + 1][firstX].getX() + tileSize);

                        board[firstY - 1][firstX] = board[firstY][firstX];
                        board[firstY][firstX] = null;
                        board[firstY - 1][firstX + 1] = board[firstY][firstX + 1];
                        board[firstY][firstX + 1] = board[firstY][firstX + 2]; //xdxdxdxxdxdd
                        board[firstY][firstX + 2] = null;
                        board[firstY + 1][firstX + 1] = board[firstY + 1][firstX];
                        board[firstY + 1][firstX] = null;

                        firstY--;
                        rotation = 2;
                        break;
                    case 2:
                        board[firstY + 1][firstX + 1].setX(board[firstY + 1][firstX + 1].getX() + tileSize);
                        board[firstY + 2][firstX + 1].setX(board[firstY + 2][firstX + 1].getX() + tileSize);
                        board[firstY][firstX].setY(board[firstY][firstX].getY() + (2 * tileSize));
                        board[firstY][firstX + 1].setY(board[firstY][firstX + 1].getY() + (2 * tileSize));

                        board[firstY + 1][firstX + 2] = board[firstY + 1][firstX + 1];
                        board[firstY + 1][firstX + 1] = null;
                        board[firstY + 2][firstX + 2] = board[firstY + 2][firstX + 1];
                        board[firstY + 2][firstX + 1] = board[firstY][firstX + 1]; //xdxdxdxxdxdd
                        board[firstY][firstX + 1] = null;
                        board[firstY + 2][firstX] = board[firstY][firstX];
                        board[firstY][firstX] = null;

                        firstY++;
                        firstX += 2;
                        rotation = 3;
                        break;
                    case 3:
                        board[firstY][firstX].setX(board[firstY + 1][firstX - 2].getX());
                        board[firstY + 1][firstX].setX(board[firstY + 1][firstX - 2].getX());
                        board[firstY + 1][firstX].setY(board[firstY + 1][firstX].getY() - (2 * tileSize));

                        board[firstY][firstX - 2] = board[firstY][firstX];
                        board[firstY][firstX] = null;
                        board[firstY -1][firstX - 2] = board[firstY + 1][firstX];
                        board[firstY + 1][firstX] = null;

                        firstY--;
                        firstX -= 2;
                        rotation = 0;
                        break;
                }
                break;
            case 'J':
                switch (rotation) {
                    case 0:
                        board[firstY + 1][firstX].setX(board[firstY + 2][firstX - 1].getX());
                        board[firstY][firstX].setY(board[firstY + 2][firstX].getY());
                        board[firstY][firstX].setX(board[firstY][firstX].getX() + tileSize);

                        board[firstY + 1][firstX - 1] = board[firstY + 1][firstX];
                        board[firstY + 1][firstX] = null;
                        board[firstY + 2][firstX + 1] = board[firstY][firstX];
                        board[firstY][firstX] = null;

                        firstY++;
                        firstX--;
                        rotation = 1;
                        break;
                    case 1:
                        board[firstY + 1][firstX + 1].setY(board[firstY + 1][firstX + 1].getY() - (2 * tileSize));
                        board[firstY + 1][firstX + 2].setY(board[firstY + 1][firstX + 1].getY());
                        board[firstY + 1][firstX + 2].setX(board[firstY][firstX].getX());

                        board[firstY - 1][firstX + 1] = board[firstY + 1][firstX + 1];
                        board[firstY + 1][firstX + 1] = null;
                        board[firstY - 1][firstX] = board[firstY + 1][firstX + 2];
                        board[firstY + 1][firstX + 2] = null;

                        firstY--;
                        rotation = 2;
                        break;
                    case 2:
                        board[firstY + 2][firstX].setX(board[firstY + 2][firstX].getX() + (2 * tileSize));
                        board[firstY][firstX + 1].setY(board[firstY + 1][firstX].getY());
                        board[firstY][firstX].setY(board[firstY + 1][firstX].getY());
                        board[firstY][firstX].setX(board[firstY + 2][firstX].getX());

                        board[firstY + 2][firstX + 2] = board[firstY + 2][firstX];
                        board[firstY + 2][firstX] = null;
                        board[firstY + 1][firstX + 1] = board[firstY][firstX + 1];
                        board[firstY][firstX + 1] = null;
                        board[firstY + 1][firstX + 2] = board[firstY][firstX];
                        board[firstY][firstX] = null;

                        firstY++;
                        rotation = 3;
                        break;
                    case 3:
                        board[firstY + 1][firstX + 2].setX(board[firstY][firstX + 1].getX());
                        board[firstY][firstX].setY(board[firstY + 1][firstX + 2].getY());
                        board[firstY][firstX + 2].setX(board[firstY][firstX + 1].getX());
                        board[firstY][firstX + 2].setY(board[firstY][firstX + 2].getY() - tileSize);

                        board[firstY + 1][firstX + 1] = board[firstY + 1][firstX + 2];
                        board[firstY + 1][firstX + 2] = null;
                        board[firstY + 1][firstX] = board[firstY][firstX];
                        board[firstY][firstX] = null;
                        board[firstY - 1][firstX + 1] = board[firstY][firstX + 2];
                        board[firstY][firstX + 2] = null;

                        firstY--;
                        firstX++;
                        rotation = 0;
                        break;
                }
        }
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        finish();
    }
}