package game_controllers;

import java.util.Random;

public class BotSystem {

    int[][] selectedTurn;
    int turn;

    GameSystem game;
    public BotSystem(GameSystem game) {
        this.game = game;
        // row and column for each turn that is selected.
        selectedTurn = new int[5][2];
        turn = 0;
    }
    public int[] winOrDrawBot() {
        for (int i = 0; i < selectedTurn.length; i++) {
            for (int j = 0; j < selectedTurn[i].length; j++) {
                System.out.print(String.valueOf(selectedTurn[i][j]));
            }
            System.out.println();
        }
        int[] pos = new int[2];
        String[][] board = game.getBoard();
        int prevRow = 0;
        int prevCol = 0;
        if (turn > 0) {
            prevRow = selectedTurn[turn - 1][0];
            prevCol = selectedTurn[turn - 1][1];
        }

        // bot playing first
        if (game.getFirstTurn().equals(game.getBotSign())) {
            // 1
            if (turn == 0) {
                // play in the corner
                pos[0] = 2;
                pos[1] = 0;
            }
            // 2
//            else if (turn == 1) {
//                pos[0] = 2;
//                pos[1] = 2;
//            }
            // 3
            else {
                int[] middle = winningSituation();
                // win
                if (middle != null) {
                    pos[0] = middle[0];
                    pos[1] = middle[1];
                }
                else {
                    int[] randPos = new int[]{0, 2};
                    for (int i = 0; i < randPos.length; i++) {
                        for (int j = 0; j < randPos.length; j++) {
                            pos[0] = randPos[i];
                            pos[1] = randPos[j];
                            if (validCorner(pos[0], pos[1])) {
                                System.out.println(String.valueOf(pos[0]) + "," + String.valueOf(pos[1]));
                                selectedTurn[turn][0] = pos[0];
                                selectedTurn[turn][1] = pos[1];
                                turn++;
                                return pos;
                            }
                        }
                    }
                }
            }
        }
        selectedTurn[turn][0] = pos[0];
        selectedTurn[turn][1] = pos[1];
        turn++;

        return pos;
    }

    private int[] winningSituation() {
        String[][] board = game.getBoard();
        for (int i = 0; i < turn; i++) {
            for (int j = i + 1; j <= turn; j++) {
                int row1 = selectedTurn[i][0];
                int col1 = selectedTurn[i][1];
                int row2 = selectedTurn[j][0];
                int col2 = selectedTurn[j][1];
                int middleRow = (row1 + row2) / 2;
                int middleCol = (col1 + col2) / 2;
                if (board[middleRow][middleCol].equals(game.EMPTYSIGN) &&
                        board[row1][col1].equals(game.getBotSign()) && board[row2][col2].equals(game.getBotSign())) {
                    System.out.println("Win Bot");
                    return new int[]{middleRow, middleCol};
                }
            }
        }
        return null;
    }
    private boolean validCorner(int row, int col) {
        String[][] board = game.getBoard();
//        if (row == 2 && col == 2) {
//            return false;
//        }

        if (!board[row][col].equals(game.EMPTYSIGN)) {
            return false;
        }
        if (row == 0) {
            if (!board[row + 1][col].equals(game.EMPTYSIGN)) {
                return false;
            }
        }
        if (col == 0) {
            if (!board[row][col + 1].equals(game.EMPTYSIGN)) {
                return false;
            }
        }

        if ((selectedTurn[turn - 1][0] + 2 == row && selectedTurn[turn - 1][1] + 2 == col) ||
                (selectedTurn[turn - 1][0] + 2 == row && selectedTurn[turn - 1][1] - 2 == col) ||
                (selectedTurn[turn - 1][0] - 2 == row && selectedTurn[turn - 1][1] + 2 == col) ||
                (selectedTurn[turn - 1][0] - 2 == row && selectedTurn[turn - 1][1] - 2 == col)) {
            return false;
        }
        return true;
    }

    public int[] randomBot() {
        int[] pos = new int[2];
        Random rand = new Random();
        int botRow = rand.nextInt(game.getBoard().length);
        int botCol = rand.nextInt(game.getBoard()[0].length);
        // make sure that the row and column is not selected.
        while (!game.getBoard()[botRow][botCol].equals(game.EMPTYSIGN)) {
            botRow = rand.nextInt(game.getBoard().length);
            botCol = rand.nextInt(game.getBoard()[0].length);
        }
        pos[0] = botRow;
        pos[1] = botCol;
        return pos;
    }
}