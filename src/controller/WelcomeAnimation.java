package controller;

import javax.swing.*;
import java.awt.*;

/**
 * Created by User on 08/04/2015.
 */
public class WelcomeAnimation extends JComponent {

    private Image pins, barrier;
    private int x = 0;
    private int y = 0;
    private boolean movingDown = true;
    private boolean finished;
    private boolean grabbed;

    public WelcomeAnimation() {
        Thread animationThread = new Thread(new Runnable() {
            public void run() {

                while (!finished) {
                    System.out.println("         --------------------                  in thw while loop "+y);
                    repaint();
                    try {
                        Thread.sleep(15);
                        System.out.println("         --------------------                  INSIDE welcome animation k    y =  "+y);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        animationThread.start();
    }

    public void beginOperations()
    {
        Thread animationThread = new Thread(new Runnable() {
            public void run() {

                while (!finished) {
                    //repaint();
                    try {
                        Thread.sleep(150);
                        System.out.println("         --------------------                  INSIDE welcome animation k    y =  "+y);
                    } catch (Exception ex) {
                    }
                }
            }


        });

        animationThread.start();

    }

    public void paintComponent(Graphics g) {
        System.out.println("In Repaint");
        Graphics2D gg = (Graphics2D) g;
        ImageIcon p = new ImageIcon("C:/Users/User/Documents/GitBowling/3BallSplitBowling/src/lib/bowling-pins-600-300x300.png");
        ImageIcon b = new ImageIcon("C:/Users/User/Documents/GitBowling/3BallSplitBowling/src/lib/files/barrier.png");


        barrier = b.getImage();
        pins = p.getImage();
        if(y>=0){
            grabbed = false;
        }
        System.out.println("In Repaint 2");
        gg.drawImage(pins, x, y - 600, this);
        gg.drawImage(barrier, x, y-300, this);
        Font text = new Font("Arial", Font.BOLD, 12);
        gg.setFont(text);
        gg.setColor(Color.YELLOW);
        gg.drawString("WELCOME", x + 150, y - 260);
        System.out.println("In Repaint 3");
        y += 5;
        if(y>600)
            finished = true;
        System.out.println("Value of x = " + x + "\tValue of y = " + y + "\tMoving = " + movingDown + "\tFinished = " + finished);
    }


   /* public void run() {
        while (!finished) {
            System.out.println("                        --------------------                  INSIDE welcome animation     y =  "+y);
            repaint();
            try {
                Thread.sleep(15);
            } catch (Exception ex) {
            }
        }
    }*/

    public static void main(String[] args) {
        JFrame frame = new JFrame("Train Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(620, 600);
        frame.setLocationRelativeTo(null);
        frame.add(new WelcomeAnimation());
        frame.setVisible(true);
    }
}

