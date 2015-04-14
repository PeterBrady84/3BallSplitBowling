package controller;

import javax.swing.*;
import java.awt.*;

/**
 * Created by User on 08/04/2015.
 */
public class WelcomeAnimation extends JPanel implements Runnable {

    private JPanel setup;
    private Image pins, barrier;
    private int x = 0;
    private int y = 0;
    private boolean movingDown = true;
    private boolean finished;
    private boolean grabbed;



    public WelcomeAnimation() {
        setup = new JPanel();
        add(setup);
        run();
    }

    public void paintComponent(Graphics g) {
        Graphics2D gg = (Graphics2D) g;
        ImageIcon p = new ImageIcon("C:/Users/User/Documents/GitBowling/3BallSplitBowling/src/lib/bowling-pins-600-300x300.png");
        ImageIcon b = new ImageIcon("C:/Users/User/Documents/GitBowling/3BallSplitBowling/src/lib/files/barrier.png");
        barrier = b.getImage();
        pins = p.getImage();
        if(y>=0){
            grabbed = false;
        }
        gg.drawImage(pins, x, y-600, this);
        //gg.setColor(Color.BLACK);
        gg.drawImage(barrier, x, y-300, this);
        //gg.fillRect(x, y+75, 400, 30);
        Font text = new Font("Arial", Font.BOLD, 12);
        gg.setFont(text);
        gg.setColor(Color.YELLOW);
        gg.drawString("WELCOME",x+150,y-260);

        y+=5;
        if(y>600)
            finished = true;
        System.out.println("Value of x = " + x + "\tValue of y = " + y + "\tMoving = " + movingDown + "\tFinished = " + finished);
    }

    @Override
    public void run() {
        add(setup);
        while (!finished) {
            repaint();
            try {Thread.sleep(15);} catch (Exception ex) {}
        }
    }
}

