package controller;

import javax.swing.*;
import java.awt.*;

import static com.sun.deploy.uitoolkit.impl.awt.Applet2ImageFactory.createImage;

/**
 * Created by User on 05/04/2015.
 *
 *
 */

import javax.swing.*;
import java.awt.*;

public class TrainDemo {



    public static void main(String[] args) {
        JFrame frame = new JFrame("Train Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(620, 600);
        frame.setLocationRelativeTo(null);
        frame.add(new TrainCanvas());
        frame.setVisible(true);
    }

}

class TrainCanvas extends JComponent {

    private Image pins, barrier;
    private int x = 0;
    private int y = 0;
    private boolean movingDown = true;
    private boolean finished;
    private boolean grabbed;

    public TrainCanvas() {
        final Thread animationThread = new Thread(new Runnable() {
            public void run() {

                while (!finished) {
                    repaint();
                    try {
                        Thread.sleep(15);
                    } catch (Exception ex) {
                    }
                }

            }
        });

        animationThread.start();
    }

    public void paintComponent(Graphics g) {
        Graphics2D gg = (Graphics2D) g;
        ImageIcon p = new ImageIcon("C:/Users/User/Documents/GitBowling/3BallSplitBowling/src/lib/bowling-pins-600-300x300.png");
        ImageIcon b = new ImageIcon("C:/Users/User/Documents/GitBowling/3BallSplitBowling/src/lib/files/barrier.png");
        barrier = b.getImage();
        pins = p.getImage();
        if (y >= 0) {
            grabbed = false;
        }

        gg.drawImage(pins, x, y - 600, this);
        gg.drawImage(barrier, x, y - 300, this);
        Font text = new Font("Arial", Font.BOLD, 12);
        gg.setFont(text);
        gg.setColor(Color.YELLOW);
        gg.drawString("WELCOME", x + 150, y - 260);

        y += 5;
        if (y > 600)
            finished = true;
        System.out.println("Value of x = " + x + "\tValue of y = " + y + "\tMoving = " + movingDown + "\tFinished = " + finished);
    }

    /*public void run() {

        while (!finished) {
            repaint();
            try {
                Thread.sleep(15);
            } catch (Exception ex) {
            }
        }
    }*/

}
