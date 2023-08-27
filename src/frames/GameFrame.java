package frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameFrame extends JFrame {
    public static int WIDTH = 750;
    public static int HEIGHT = 750;

    GamePanel panel;
    public GameFrame(String title) {
        panel = new GamePanel();

        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // stop the program when click the x button on the right corner.
        this.setSize(WIDTH, HEIGHT+10);

        this.add(panel);

        this.setResizable(false); // cannot resize the this
        this.setVisible(true); // show this
        // this.getContentPane().setBackground(new Color(0, 0, 0)); // can use both rgb value and hex code.
        this.setLocationRelativeTo(null); // center the frame
    }
}