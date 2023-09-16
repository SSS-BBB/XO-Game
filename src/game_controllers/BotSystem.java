package game_controllers;

import java.util.Random;

public class BotSystem {

    int[][] selectedTurn;
    int turn;

    GameSystem game;

    public String getMySign() {
        return mySign;
    }

    public void setMySign(String mySign) {
        this.mySign = mySign;
    }

    public String getOpponentSign() {
        return opponentSign;
    }

    public void setOpponentSign(String opponentSign) {
        this.opponentSign = opponentSign;
    }

    private String mySign;
    private String opponentSign;
    public BotSystem(GameSystem game, String mySign, String opponentSign) {
        this.game = game;
        this.mySign = mySign;
        this.opponentSign = opponentSign;
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

        int[] middle = winOrBlock("win");
        // win
        if (middle != null) {
            pos[0] = middle[0];
            pos[1] = middle[1];
            selectedTurn[turn][0] = pos[0];
            selectedTurn[turn][1] = pos[1];
            turn++;
            return pos;
        }
        // block
        int[] block = winOrBlock("block");
        if (block != null) {
            pos[0] = block[0];
            pos[1] = block[1];
            selectedTurn[turn][0] = pos[0];
            selectedTurn[turn][1] = pos[1];
            turn++;
            return pos;
        }

        // bot playing first
        if (game.getFirstTurn().equals(mySign)) {
            // 1
            if (turn == 0) {
                // play in the corner
                pos[0] = 2;
                pos[1] = 0;
            }
            // 2
            else if (turn == 1 && board[1][1].equals(opponentSign)) {
                pos[0] = 0;
                pos[1] = 2;
            }
        }
        // opponent playing first
        else if (game.getFirstTurn().equals(opponentSign)) {
            // 1
            if (turn == 0) {
                // middle board
                if (board[1][1].equals(game.EMPTYSIGN)) {
                    pos[0] = 1;
                    pos[1] = 1;
                    selectedTurn[turn][0] = pos[0];
                    selectedTurn[turn][1] = pos[1];
                    turn++;
                    return pos;
                }
            }
        }
        int[] cornerPos = cornerPos();
        if (cornerPos != null) {
            pos[0] = cornerPos[0];
            pos[1] = cornerPos[1];
            selectedTurn[turn][0] = pos[0];
            selectedTurn[turn][1] = pos[1];
            turn++;
            return pos;
        }
        return pos;
    }

    private int[] winOrBlock(String winOrBlock) {
        String[][] board = game.getBoard();
        String signToLook = mySign;
        if (winOrBlock.equals("block")) {
            signToLook = opponentSign;
        }

        // DangerousPos1, DangerousPos2, PostoBlock
        int[][][] dangerousPos = {
                // horizontal
                {{0, 0}, {0, 1}, {0, 2}},
                {{0, 0}, {0, 2}, {0, 1}},
                {{0, 1}, {0, 2}, {0, 0}},
                {{1, 0}, {1, 1}, {1, 2}},
                {{1, 0}, {1, 2}, {1, 1}},
                {{1, 1}, {1, 2}, {1, 0}},
                {{2, 0}, {2, 1}, {2, 2}},
                {{2, 0}, {2, 2}, {2, 1}},
                {{2, 1}, {2, 2}, {2, 0}},

                // vertical
                {{0, 0}, {1, 0}, {2, 0}},
                {{0, 0}, {2, 0}, {1, 0}},
                {{1, 0}, {2, 0}, {0, 0}},
                {{0, 1}, {1, 1}, {2, 1}},
                {{0, 1}, {2, 1}, {1, 1}},
                {{1, 1}, {2, 1}, {0, 1}},
                {{0, 2}, {1, 2}, {2, 2}},
                {{0, 2}, {2, 2}, {1, 2}},
                {{1, 2}, {2, 2}, {0, 2}},

                // diagonal
                // top right to bottom left
                {{0, 2}, {1, 1}, {2, 0}},
                {{0, 2}, {2, 0}, {1, 1}},
                {{1, 1}, {2, 0}, {0, 2}},
                // top left to bottom right
                {{0, 0}, {1, 1}, {2, 2}},
                {{0, 0}, {2, 2}, {1, 1}},
                {{1, 1}, {2, 2}, {0, 0}},
        };

        for (int i = 0; i < dangerousPos.length; i++) {
            int[] pos1 = dangerousPos[i][0];
            int[] pos2 = dangerousPos[i][1];
            int[] blockPos = dangerousPos[i][2];

            if (board[pos1[0]][pos1[1]].equals(signToLook) &&
                    board[pos2[0]][pos2[1]].equals(signToLook) &&
                    board[blockPos[0]][blockPos[1]].equals(game.EMPTYSIGN)) {
                return blockPos;
            }
        }

        return null;
    }

    private int[] cornerPos() {
        int[] cornerPos = new int[]{0, 0};
        int[] randPos = new int[]{0, 2};
        for (int i = 0; i < randPos.length; i++) {
            for (int j = 0; j < randPos.length; j++) {
                cornerPos[0] = randPos[i];
                cornerPos[1] = randPos[j];
                if (validCorner(cornerPos[0], cornerPos[1])) {
                    System.out.println(String.valueOf(cornerPos[0]) + "," + String.valueOf(cornerPos[1]));
                    return  cornerPos;
                }
            }
        }
        return null;
    }

//    private int[] winningSituation() {
//        String[][] board = game.getBoard();
//        for (int i = 0; i < turn; i++) {
//            for (int j = i + 1; j <= turn; j++) {
//                int row1 = selectedTurn[i][0];
//                int col1 = selectedTurn[i][1];
//                int row2 = selectedTurn[j][0];
//                int col2 = selectedTurn[j][1];
//                int middleRow = (row1 + row2) / 2;
//                int middleCol = (col1 + col2) / 2;
//                if (board[middleRow][middleCol].equals(game.EMPTYSIGN) &&
//                        board[row1][col1].equals(mySign) && board[row2][col2].equals(mySign)) {
//                    System.out.println("Win Bot");
//                    return new int[]{middleRow, middleCol};
//                }
//            }
//        }
//        return null;
//    }
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

        if (turn > 0) {
            if ((selectedTurn[turn - 1][0] + 2 == row && selectedTurn[turn - 1][1] + 2 == col) ||
                    (selectedTurn[turn - 1][0] + 2 == row && selectedTurn[turn - 1][1] - 2 == col) ||
                    (selectedTurn[turn - 1][0] - 2 == row && selectedTurn[turn - 1][1] + 2 == col) ||
                    (selectedTurn[turn - 1][0] - 2 == row && selectedTurn[turn - 1][1] - 2 == col)) {
                return false;
            }
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