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
        Thread animationThread = new Thread(new Runnable() {
            public void run() {
                while (!finished) {
                    repaint();
                    try {Thread.sleep(15);} catch (Exception ex) {}
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

    /*@Override
    public void run() {
        while (!finished) {
            repaint();
            try {Thread.sleep(15);} catch (Exception ex) {}
        }
    }*/
}
/*public class PinAnimation extends JPanel implements Runnable{

    private Image pins;
    private Graphics graph;
    private Image dbImage;
    private boolean goingdown;
    private int x, y;
    private JPanel pane;


    public PinAnimation(){
        this.pane = pane;
        //Load images
        ImageIcon i = new ImageIcon("C:/Users/User/Documents/GitBowling/3BallSplitBowling/src/lib/bowling-pins-600-300x300.jpg");
        pins = i.getImage();
        x = 0;
        y = 0;
        run();
    }

    public void paint(Graphics g){
        dbImage = createImage(600, 300);
        graph = dbImage.getGraphics();
        paintComponent(graph);
        g.drawImage(dbImage, 0,0, this);
    }

    public void paintComponent(Graphics g){
        g.drawImage(pins,x,y,this);
        repaint();
    }

    *//*public void move(){
        y++;
        if(y>=300)
            goingdown = false;
    }*//*

    @Override
    public void run() {
        try {
            while (goingdown) {
                y++;
                if(y>=300)
                    goingdown = false;
                Thread.sleep(1000);

            }
        }
        catch (Exception e){
            System.out.println("error "+e);
        }

    }

    public static void main(String args[]){
        PinAnimation pin = new PinAnimation();
        Thread a = new Thread(pin);
        a.run();
    }


}*/
