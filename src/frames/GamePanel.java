package frames;

import game_controllers.GameSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

// The coordinates of the frame include the top bar, so in order to avoid confusion we need to create a panel.
public class GamePanel extends JPanel implements MouseListener {

    GameSystem system;

    // line coordinates
    public static int w = GameFrame.WIDTH;
    public static int h = GameFrame.HEIGHT;
    public static int startX = (int) Math.round(0.05 * w);
    public static int endX = (int) Math.round(0.95 * w);
    public static int y1 = (int) Math.round(0.33 * h);
    public static int y2 = (int) Math.round(0.66 * h);
    public static int line_size_h = endX - startX; // horizontal line size
    public static int line_dis = y2 - y1; // the distance between two horizontal lines

    public static int x1 = (int) Math.round(startX + (0.33*line_size_h));
    public static int x2 = (int) Math.round(endX - (0.33 * line_size_h));
    public static int startY = y1 - line_dis;
    public static int endY = y2 + line_dis;

    public static int thick = 3;

    private JLabel winnerLabel;
    private JLabel turnLabel;
    private boolean clicked = true;

    public GamePanel() {
        this.setPreferredSize(new Dimension(GameFrame.WIDTH, GameFrame.HEIGHT));
        this.addMouseListener(this);
        this.setLayout(null);
        system = new GameSystem();

//        JLabel label = new JLabel("My name is Suphawit.");
//        label.setBounds(0, 0, w, h);
//        label.setFont(new Font("Arial", Font.PLAIN, 24));
//        label.setHorizontalAlignment(JLabel.CENTER);
//        label.setVerticalAlignment(JLabel.CENTER);
//        add(label);

        winnerLabel = new JLabel();
        winnerLabel.setBounds(0, 0, w, h);
        winnerLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        winnerLabel.setForeground(Color.RED);
        winnerLabel.setHorizontalAlignment(JLabel.CENTER);
        winnerLabel.setVerticalAlignment(JLabel.CENTER);
        winnerLabel.setVisible(false);
        add(winnerLabel);

        turnLabel = new JLabel("Turn: " + system.getCurrentTurn());
        turnLabel.setBounds(0, 0, w, h);
        turnLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        turnLabel.setHorizontalAlignment(JLabel.CENTER);
        turnLabel.setVerticalAlignment(JLabel.TOP);
        add(turnLabel);
    }

    @Override
    public void paintComponent(Graphics g) {
        // make sure to paint only after mouse was clicked
        if (!clicked) {
            return;
        }
        super.paintComponent(g); // clear the screen
        clicked = false;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(thick));


        if (!system.isEnd()) {

            // horizontal lines
            g2d.drawLine(startX, y1, endX, y1);
            g2d.drawLine(startX, y2, endX, y2);

            // vertical lines
            g2d.drawLine(x1, startY, x1, endY);
            g2d.drawLine(x2, startY, x2, endY);


            // draw X or O
            for (int row = 0; row < system.getBoard().length; row++) {
                for (int col = 0; col < system.getBoard()[row].length; col++) {
                    drawSign(row, col, g2d);
                }
            }
            turnLabel.setText("Turn: " + system.getCurrentTurn());
            showBoard(system.getBoard());
            System.out.println("-------------------");
            system.checkWin();

            if (system.isEnd()) {
                winnerLabel.setText("The winner is " + system.getWinner());
                winnerLabel.setVisible(true);
                turnLabel.setVisible(false);
            }
        }
    }

    public void drawSign(int row, int col, Graphics2D g2d) {
        int margin = 40;
        if (system.getBoard()[row][col].equals("X")) {
            // x sign
            int l1x1 = 0;
            int l1y1 = 0;
            int l1x2 = 0;
            int l1y2 = 0;

            int l2x1 = 0;
            int l2y1 = 0;
            int l2x2 = 0;
            int l2y2 = 0;

            // col 1
            if (col == 0) {
                l1x1 = startX + margin;
                l1x2 = x1 - margin;

                l2x1 = startX + margin;
                l2x2 = x1 - margin;
            }

            // col 2
            else if (col == 1) {
                l1x1 = x1 + margin;
                l1x2 = x2 - margin;

                l2x1 = x1 + margin;
                l2x2 = x2 - margin;
            }

            // col 3
            else {
                l1x1 = x2 + margin;
                l1x2 = endX - margin;

                l2x1 = x2 + margin;
                l2x2 = endX - margin;
            }

            // row 1
            if (row == 0) {
                l1y1 = startY + margin;
                l1y2 = y1 - margin;

                l2y1 = y1 - margin;
                l2y2 = startY + margin;
            }

            // row 2
            else if (row == 1) {
                l1y1 = y1 + margin;
                l1y2 = y2 - margin;

                l2y1 = y2 - margin;
                l2y2 = y1 + margin;
            }

            // row 3
            else {
                l1y1 = y2 + margin;
                l1y2 = endY - margin;

                l2y1 = endY - margin;
                l2y2 = y2 + margin;
            }

            g2d.setStroke(new BasicStroke(thick));
            g2d.drawLine(l1x1, l1y1, l1x2, l1y2);
            g2d.drawLine(l2x1, l2y1, l2x2, l2y2);
        }
        else if (system.getBoard()[row][col].equals("O")) {
            // O sign
            int x_center = 0;
            int y_center = 0;
            int d = ((int) Math.round(line_size_h * 0.33)) - margin; // diameter
            // int r = ((int) Math.round((x1 - startX) * 0.5)) - margin; // radius

            // col 1
            if (col == 0) {
                x_center = (startX + x1) / 2;
            }

            // col 2
            else if (col == 1) {
                x_center = (x1 + x2) / 2;
            }

            // col 3
            else {
                x_center = (x2 + endX) / 2;
            }

            // row 1
            if (row == 0) {
                y_center = (startY + y1) / 2;
            }

            // row 2
            else if (row == 1) {
                y_center = (y1 + y2) / 2;
            }

            // row 3
            else {
                y_center = (y2 + endY) / 2;
            }

            // draw circle
            g2d.drawOval(x_center - d/2, y_center - d/2, d, d);

        }
    }

    public void showBoard(String[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                System.out.print(board[row][col]);
            }
            System.out.println();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!system.isEnd()) {
            system.checkBox(e.getX(), e.getY());
            // System.out.println(String.valueOf(system.getCurrentInput()[0]) + ", " + String.valueOf(system.getCurrentInput()[1]));
            clicked = true;
            if (system.getCurrentTurn() == "X") {
                system.setCurrentTurn("O");
            }
            else {
                system.setCurrentTurn("X");
            }

            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}