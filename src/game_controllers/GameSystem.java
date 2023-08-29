package game_controllers;

import java.util.Arrays;

import static frames.GamePanel.*;

public class GameSystem {
    private boolean end = false;
    private final int[] currentInput = {0, 0};
    private String currentTurn = "X";

    /*
           {
             {O, X, O}.
             {X, O, O},
             {O, X, X}
           }
        */
    private String[][] board = new String[3][3];
    private final String EMPTYSIGN = "-";

    private String winner = EMPTYSIGN;

    public GameSystem() {
        resetBoard();
    }

    public void resetBoard() {
        // board = new String[][]{{"E", "E", "E"}, {"E", "E", "E"}, {"E", "E", "E"}}; // E stands for Empty
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = EMPTYSIGN;
            }
        }
    }

    public int[] checkBox(int x, int y) {

        // check x (column)
        if (x >= startX && x <= x1) {
            currentInput[1] = 1;
        }
        else if (x >= x1 && x <= x2) {
            currentInput[1] = 2;
        }
        else if (x >= x2 && x <= endX) {
            currentInput[1] = 3;
        }

        // check y (row)
        if (y >= startY && y <= y1) {
            currentInput[0] = 1;
        }
        else if (y >= y1 && y <= y2) {
            currentInput[0] = 2;
        }
        else if (y >= y2 && y <= endY) {
            currentInput[0] = 3;
        }

        board[currentInput[0] - 1][currentInput[1] - 1] = currentTurn;
        return currentInput;
    }

    public void checkWin() {
        // check for horizontal win
        for (int row = 0; row < board.length; row++) {
            if (!board[row][0].equals(EMPTYSIGN)) {
                if (board[row][0].equals(board[row][1]) && board[row][0].equals(board[row][2])) {
                    winner = board[row][0];
                    end = true;
                    return;
                }
            }
        }

        // check for vertical win
        for (int col = 0; col < board[0].length; col++) {

        }

        end = false;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int[] getCurrentInput() {
        return currentInput;
    }

    public void resetInput() {
        currentInput[0] = 0;
        currentInput[1] = 0;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }

    public String[][] getBoard() {
        return board;
    }

    public String getWinner() {
        return winner;
    }
}