package frames;

import game_controllers.GameSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

    public GamePanel() {
        this.setPreferredSize(new Dimension(GameFrame.WIDTH, GameFrame.HEIGHT));
        this.addMouseListener(this);
        system = new GameSystem();
    }

    @Override
    public void paintComponent(Graphics g) {

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
            if (system.getBox()[0] != 0 && system.getBox()[1] != 0) {
                // x
                int move = 20;
                int l1x1 = 0;
                int l1y1 = 0;
                int l1x2 = 0;
                int l1y2 = 0;

                int l2x1 = 0;
                int l2y1 = 0;
                int l2x2 = 0;
                int l2y2 = 0;

                if (system.getBox()[0] == 1) {
                    l1x1 = startX + move;
                    l1x2 = x1 - move;

                    l2x1 = startX + move;
                    l2x2 = x1 - move;
                }
                else if (system.getBox()[0] == 2) {
                    l1x1 = x1 + move;
                    l1x2 = x2 - move;

                    l2x1 = x1 + move;
                    l2x2 = x2 - move;
                }
                else {
                    l1x1 = x2 + move;
                    l1x2 = endX - move;

                    l2x1 = x2 + move;
                    l2x2 = endX - move;
                }

                if (system.getBox()[1] == 1) {
                    l1y1 = startY + move;
                    l1y2 = y1 - move;

                    l2y1 = y1 - move;
                    l2y2 = startY + move;
                }
                else if (system.getBox()[1] == 2) {
                    l1y1 = y1 + move;
                    l1y2 = y2 - move;

                    l2y1 = y2 - move;
                    l2y2 = y1 + move;
                }
                else {
                    l1y1 = y2 + move;
                    l1y2 = endY - move;

                    l2y1 = endY - move;
                    l2y2 = y2 + move;
                }

                g2d.setStroke(new BasicStroke(thick));
                g2d.drawLine(l1x1, l1y1, l1x2, l1y2);
                g2d.drawLine(l2x1, l2y1, l2x2, l2y2);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        system.checkBox(e.getX(), e.getY());
        System.out.println(String.valueOf(system.getBox()[0]) + ", " + String.valueOf(system.getBox()[1]));
        repaint();
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