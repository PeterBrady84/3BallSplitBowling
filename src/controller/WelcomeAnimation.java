package controller;

import javax.swing.*;
import java.awt.*;

/**
 * Created by User on 08/04/2015.
 */
public class WelcomeAnimation extends JPanel {

        private final int x ;
        private int y ;
        private boolean finished;

    public WelcomeAnimation() {

            x= 125;
            y= 0;

            final Thread animationThread;
            animationThread = new Thread(new Runnable() {
                public void run() {
                    while (!finished) {
                        repaint();
                        try {
                            Thread.sleep(15);
                        } catch (Exception ignored) {
                        }
                    }
                }
            });
            animationThread.setDaemon(true);
            animationThread.start();
        }

    public boolean isFinished() {
        return finished;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;
        ImageIcon p = new ImageIcon("src/lib/files/bowling-pins-600-300x300.png");
        ImageIcon b = new ImageIcon("src/lib/files/barrier.png");

        Image barrier = b.getImage();
        Image pins = p.getImage();
        if(y>=0){
            boolean grabbed = false;
        }
        gg.drawImage(pins, x, y - 600, this);
        gg.drawImage(barrier, x, y - 300, this);
        Font text = new Font("Arial", Font.BOLD, 18);
        gg.setFont(text);
        gg.setColor(Color.YELLOW);

        y += 5;
        if(y>600)
            finished = true;
    }
}

