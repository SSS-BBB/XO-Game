import frames.GameFrame;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // frame
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameFrame frame = new GameFrame("XO Game");
            }
        });

    }
}