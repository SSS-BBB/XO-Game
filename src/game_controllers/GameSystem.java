package game_controllers;

import frames.GamePanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static frames.GamePanel.*;

public class GameSystem {
    private boolean end = false;
    private int[] box = {0, 0};

    public void setBox(int[] box) {
        this.box = box;
    }

    public int[] checkBox(int x, int y) {

        // check x
        if (x >= startX && x <= x1) {
            box[0] = 1;
        }
        else if (x >= x1 && x <= x2) {
            box[0] = 2;
        }
        else if (x >= x2 && x <= endX) {
            box[0] = 3;
        }

        // check y
        if (y >= startY && y <= y1) {
            box[1] = 1;
        }
        else if (y >= y1 && y <= y2) {
            box[1] = 2;
        }
        else if (y >= y2 && y <= endY) {
            box[1] = 3;
        }

        return box;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int[] getBox() {
        return box;
    }

    public void resetBox() {
        box[0] = 0;
        box[1] = 0;
    }
}