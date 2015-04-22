package gui;

import controller.WelcomeAnimation;
import db.MainProgramOperations;
import model.Alley;
import model.Staff;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Peter on 18/03/2015.
 */
public class LoginGUI extends JFrame implements ActionListener {

    private final MainProgramOperations progOps;
    private final JPanel p1;
    private final JButton help;
    private final JButton login;
    private final JButton forgot;
    private final JButton exit;
    private final JTextField userTxt;
    private final JPasswordField passTxt;
    public static Staff user;

    public LoginGUI(MainProgramOperations po) {
        System.out.println("Inside : LoginGUI");
        Date dateSelected = new Date();
        this.progOps = po;
        this.setBackground(Color.WHITE);

        this.setTitle("3-Ball-Strike Bowling");
        this.setSize(850, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setBackground(Color.WHITE);

        p1 = new JPanel();
        p1.setPreferredSize(new Dimension(700, 100));
        p1.setLayout(new BorderLayout());
        p1.setBackground(Color.WHITE);
        add(p1, BorderLayout.NORTH);

        ImageIcon logo = new ImageIcon("src/lib/files/bray_bowl.png");
        JLabel bowl = new JLabel(logo);
        p1.add(bowl, BorderLayout.WEST);

        JLabel header = new JLabel("3-Ball-Strike Bowling System", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(40.0f));
        p1.add(header, BorderLayout.CENTER);

        ImageIcon logo2 = new ImageIcon("src/lib/files/bowling_help.png");
        help = new JButton(logo2);
        help.setMargin(new Insets(0, 0, 0, 0));
        help.setBackground(Color.WHITE);
        help.setBorder(null);
        help.setContentAreaFilled(false);
        help.setOpaque(true);
        p1.add(help, BorderLayout.EAST);
        help.addActionListener(this);

        //P2
        JPanel p2 = new JPanel(new BorderLayout());
        p2.setPreferredSize(new Dimension(300, 430));
        p2.setBackground(Color.WHITE);
        p2.setBorder(BorderFactory.createLineBorder(Color.black));

        //p2 sub1
        JPanel sub1 = new JPanel();
        sub1.setPreferredSize(new Dimension(200, 100));
        sub1.setBackground(Color.WHITE);

        //p2 - sub2
        JPanel sub2 = new JPanel();
        sub2.setPreferredSize(new Dimension(300, 330));
        sub2.setBackground(Color.WHITE);

        JLabel userLbl = new JLabel("Username:");
        JLabel passLbl = new JLabel("Password:");
        userTxt = new JTextField("user1");
        passTxt = new JPasswordField("password");

        login = new JButton("LOGIN");
        login.addActionListener(this);

        forgot = new JButton("Forgot Login Details");
        forgot.addActionListener(this);

        exit = new JButton("Exit");
        exit.addActionListener(this);

        sub2.add(Box.createRigidArea(new Dimension(30, 0)));
        sub2.add(userLbl);
        sub2.add(userTxt);
        sub2.add(Box.createRigidArea(new Dimension(70, 0)));
        sub2.add(Box.createRigidArea(new Dimension(30, 0)));
        sub2.add(passLbl);
        sub2.add(passTxt);
        sub2.add(Box.createRigidArea(new Dimension(70, 0)));
        sub2.add(Box.createRigidArea(new Dimension(250, 20)));
        sub2.add(Box.createRigidArea(new Dimension(100, 0)));
        sub2.add(login);
        sub2.add(Box.createRigidArea(new Dimension(70, 0)));
        sub2.add(Box.createRigidArea(new Dimension(30, 0)));
        sub2.add(forgot);
        sub2.add(Box.createRigidArea(new Dimension(250, 20)));
        sub2.add(exit);


        //p2 sub3
        JPanel sub3 = new JPanel();
        sub3.setPreferredSize(new Dimension(300, 100));
        sub3.add(Box.createRigidArea(new Dimension(0, 98)));
        sub3.add(Box.createRigidArea(new Dimension(50, 0)));
        sub3.setBackground(Color.WHITE);

        // p2 adding sub panels
        p2.add(sub1, BorderLayout.NORTH);
        p2.add(sub2, BorderLayout.CENTER);
        p2.add(sub3, BorderLayout.SOUTH);

        //P2
        JPanel p3 = new JPanel();
        p3.setPreferredSize(new Dimension(420, 310));
        p3.setBorder(BorderFactory.createLineBorder(Color.black));
        p3.setBackground(Color.WHITE);

        ImageIcon mainImage = new ImageIcon("src/lib/files/bowling.png");
        JLabel bowling = new JLabel(mainImage);
        p3.add(bowling, BorderLayout.CENTER);

        //Adding Panels
        JPanel p4 = new JPanel();
        p4.setLayout(new FlowLayout());
        p4.add(p2, BorderLayout.WEST);
        p4.add(p3, BorderLayout.EAST);
        p4.setBackground(Color.WHITE);
        this.add(p4);

        setVisible(true);
    }

    public boolean login() {
        return progOps.checkPass(userTxt.getText(), passTxt.getPassword());
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println("Inside : actionPerformed() in LoginGUI");
        if(ae.getSource()==login) {
            if(login()) {
                p1.setVisible(false);
                int width = this.getWidth();
                int height = this.getHeight();
                //JPanel animation;
                Color tranparent = new Color(0,0,0,0);
                WelcomeAnimation animation = new WelcomeAnimation();
                animation.setPreferredSize(new Dimension(width, height));
                this.add(animation);

                setVisible(true);

                JOptionPane pane = new JOptionPane(null, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, new ImageIcon("src/lib/files/bowlingpins.png"));
                pane.setMessage("Proceed");
                pane.setPreferredSize(new Dimension(75, 90)); // Configure
                JDialog dialog = pane.createDialog("Welcome");
                dialog.setLocation(545, 255);  // added!
                dialog.setVisible(true);

                Staff user = progOps.createUser(userTxt.getText());
                Alley a = new Alley(progOps);

                MainScreen ms = new MainScreen(user, a.getMemberList(), a.getStaffList(), a.getStockList(),
                        a.getBookingList(),
                        progOps);
                this.setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(null, "ACCESS DENIED\nIncorrect username or password!", "ERROR",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
        else if(ae.getSource()==forgot) {
            ForgotLoginGUI fl = new ForgotLoginGUI(progOps);
        }
        else if (ae.getSource() == help) {
            if (Desktop.isDesktopSupported()) {
                try {
                    File helpPDF = new File("src/lib/files/helpManuals/login.pdf");
                    Desktop.getDesktop().open(helpPDF);
                } catch (IOException ex) {
                    System.out.println("Unable to open PDFs");
                }
            }
        }
        if(ae.getSource()==exit) {
            progOps.closeDB();
            System.exit(0);
        }
    }
}
