package controller;

import javax.swing.*;
import java.awt.*;

/**
 * Created by User on 08/04/2015.
 */
public class WelcomeAnimation extends JPanel {

        //System.out.println("      #########################   In the welcome class  ##################");


        private JPanel hope;
        private Image pins, barrier;
        private int x ;
        private int y ;
        private boolean movingDown = true;
        private boolean finished;
        private boolean grabbed;

        public WelcomeAnimation(int width, int height) {

            x= 125;
            y= 0;

            final Thread animationThread;
            animationThread = new Thread(new Runnable() {
                public void run() {
                    while (!finished) {
                        repaint();
                        try {
                            Thread.sleep(15);
                            System.out.println("         --------------------                  INSIDE welcome animation --    y = " + y);
                        } catch (Exception ex) {
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
        System.out.println("In Repaint");
        super.paintComponent(g);
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
        Font text = new Font("Arial", Font.BOLD, 18);
        gg.setFont(text);
        gg.setColor(Color.YELLOW);

        y += 5;
        if(y>500)
            finished = true;

        System.out.println("is finished = "+isFinished());

    }

    /*public static void main(String[] args) {
        JFrame frame = new JFrame("Train Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(620, 600);
        frame.setLocationRelativeTo(null);
        frame.add(new WelcomeAnimation());
        frame.setVisible(true);
    }*/
}

