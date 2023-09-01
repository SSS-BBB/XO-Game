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
    private int[][] lineWinPos = new int[2][2]; // ((x1, y1), (x2, y2))

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

    public boolean checkBox(int x, int y) {

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

        if (board[currentInput[0] - 1][currentInput[1] - 1].equals(EMPTYSIGN)) {
            board[currentInput[0] - 1][currentInput[1] - 1] = currentTurn;
            return true;
        }

        return false;
    }

    public void checkWin() {
        // check for horizontal win
        for (int row = 0; row < board.length; row++) {
            if (!board[row][0].equals(EMPTYSIGN)) {
                if (board[row][0].equals(board[row][1]) && board[row][0].equals(board[row][2])) {
                    winner = board[row][0];
                    end = true;
                    setLineWinPos(row, 0, row, 2, "h");
                    return;
                }
            }
        }

        // check for vertical win
        for (int col = 0; col < board[0].length; col++) {
            if (!board[0][col].equals(EMPTYSIGN)) {
                if (board[0][col].equals(board[1][col]) && board[0][col].equals(board[2][col])) {
                    winner = board[0][col];
                    end = true;
                    setLineWinPos(0, col, 2, col, "v");
                    return;
                }
            }
        }

        // check for diagonal win
        // top left to bottom right
        if (!board[0][0].equals(EMPTYSIGN)) {
            if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
                winner = board[0][0];
                end = true;
                setDiagonalWinPos("tl");
                return;
            }
        }

        // top right to bottom left
        if (!board[0][2].equals(EMPTYSIGN)) {
            if (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])) {
                winner = board[0][2];
                end = true;
                setDiagonalWinPos("tr");
                return;
            }
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

    public void setDiagonalWinPos(String direction) {
        // top left to bottom right
        if (direction.equalsIgnoreCase("tl")) {
            lineWinPos[0][0] = startX;
            lineWinPos[0][1] = startY;
            lineWinPos[1][0] = endX;
            lineWinPos[1][1] = endY;
        }
        // top right to bottom left
        else {
            lineWinPos[0][0] = endX;
            lineWinPos[0][1] = startY;
            lineWinPos[1][0] = startX;
            lineWinPos[1][1] = endY;
        }
    }

    public void setLineWinPos(int row1, int col1, int row2, int col2, String direction) {
        if (row1 == 0) {
            // horizontal
            if (direction.equalsIgnoreCase("h")) {
                lineWinPos[0][1] = (startY + y1) / 2;
            }
            // vertical
            else {
                lineWinPos[0][1] = startY;
            }
        }
        // horizontal only
        else if (row1 == 1) {
            lineWinPos[0][1] = (y1 + y2) / 2;
        }
        // horizontal only
        else if (row1 == 2) {
            lineWinPos[0][1] = (y2 + endY) / 2;
        }

        if (col1 == 0) {
            // horizontal
            if (direction.equalsIgnoreCase("h")) {
                lineWinPos[0][0] = startX;
            }
            // vertical
            else {
                lineWinPos[0][0] = (startX + x1) / 2;
            }

        }
        // vertical only
        else if (col1 == 1) {
            lineWinPos[0][0] = (x1 + x2) / 2;
        }
        // vertical only
        else if (col1 == 2) {
            lineWinPos[0][0] = (x2 + endX) / 2;
        }

        // horizontal only
        if (row2 == 0) {
            lineWinPos[1][1] = (startY + y1) / 2;
        }
        // horizontal only
        else if (row2 == 1) {
            lineWinPos[1][1] = (y1 + y2) / 2;
        }
        else if (row2 == 2) {
            // horizontal
            if (direction.equalsIgnoreCase("h")) {
                lineWinPos[1][1] = (y2 + endY) / 2;
            }
            // vertical
            else {
                lineWinPos[1][1] = endY;
            }

        }

        // vertical only
        if (col2 == 0) {
            lineWinPos[1][0] = (startX + x1) / 2;
        }
        // vertical only
        else if (col2 == 1) {
            lineWinPos[1][0] = (x1 + x2) / 2;
        }
        else if (col2 == 2) {
            // horizontal
            if (direction.equalsIgnoreCase("h")) {
                lineWinPos[1][0] = endX;
            }
            // vertical
            else {
                lineWinPos[1][0] = (x2 + endX) / 2;
            }
        }
    }

    public int[][] getLineWinPos() {
        return this.lineWinPos;
    }
}