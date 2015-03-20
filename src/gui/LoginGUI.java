package gui;

import db.MainProgramOperations;
import model.Alley;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Peter on 18/03/2015.
 */
public class LoginGUI extends JFrame implements ActionListener {

    private MainProgramOperations progOps;
    private JPanel p1, p2, p3, p4, sub1, sub2, sub3;
    private JLabel userLbl, passLbl, bowl, header, help;
    private JButton login, forgot, exit;
    private JTextField userTxt;
    private JPasswordField passTxt;

    public LoginGUI(MainProgramOperations po) {
        System.out.println("Inside : LoginGUI");
        this.progOps = po;

        this.setTitle("3-Ball-Strike Bowling");
        this.setSize(850, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        p1 = new JPanel();
        p1.setPreferredSize(new Dimension (700, 100));
        p1.setLayout(new BorderLayout());
        add(p1, BorderLayout.NORTH);

        ImageIcon logo = new ImageIcon("src/lib/files/bray_bowl.png");
        bowl = new JLabel(logo);
        p1.add(bowl, BorderLayout.WEST);

        header = new JLabel("3-Ball-Strike Bowling System", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(40.0f));
        p1.add(header, BorderLayout.CENTER);

        ImageIcon logo2 = new ImageIcon("src/lib/files/bowling_help.png");
        help = new JLabel(logo2);
        p1.add(help, BorderLayout.EAST);

        //P2
        p2 = new JPanel(new BorderLayout());
        p2.setPreferredSize(new Dimension(300, 430));
        p2.setBackground(Color.red);
        p2.setBorder(BorderFactory.createLineBorder(Color.black));

        //p2 sub1
        sub1 = new JPanel();
        sub1.setPreferredSize(new Dimension(200, 100));

        //p2 - sub2
        sub2 = new JPanel();
        sub2.setPreferredSize(new Dimension(300, 330));

        userLbl = new JLabel("Username:");
        passLbl = new JLabel("Password:");
        userTxt = new JTextField(10);
        passTxt = new JPasswordField(10);

        login = new JButton("LOGIN");
        login.addActionListener(this);

        forgot = new JButton("Forgot Login Details");
        forgot.addActionListener(this);

        exit = new JButton("Exit");
        exit.addActionListener(this);

        sub2.add(Box.createRigidArea(new Dimension(30,0)));
        sub2.add(userLbl);
        sub2.add(userTxt);
        sub2.add(Box.createRigidArea(new Dimension(70,0)));
        sub2.add(Box.createRigidArea(new Dimension(30,0)));
        sub2.add(passLbl);
        sub2.add(passTxt);
        sub2.add(Box.createRigidArea(new Dimension(70,0)));
        sub2.add(Box.createRigidArea(new Dimension(250,20)));
        sub2.add(Box.createRigidArea(new Dimension(100,0)));
        sub2.add(login);
        sub2.add(Box.createRigidArea(new Dimension(70,0)));
        sub2.add(Box.createRigidArea(new Dimension(30,0)));
        sub2.add(forgot);
        sub2.add(Box.createRigidArea(new Dimension(250,20)));
        sub2.add(exit);


        //p2 sub3
        sub3 = new JPanel();
        sub3.setPreferredSize(new Dimension(300, 100));
        sub3.add(Box.createRigidArea(new Dimension(0,98)));
        sub3.add(Box.createRigidArea(new Dimension(50,0)));

        // p2 adding sub panels
        p2.add(sub1, BorderLayout.NORTH);
        p2.add(sub2, BorderLayout.CENTER);
        p2.add(sub3, BorderLayout.SOUTH);

        //P2
        p3 = new JPanel();
        p3.setPreferredSize(new Dimension(420, 310));
        p3.setBorder(BorderFactory.createLineBorder(Color.black));

        ImageIcon mainImage = new ImageIcon("src/lib/files/bowling.png");
        JLabel bowling = new JLabel(mainImage);
        p3.add(bowling, BorderLayout.CENTER);

        //Adding Panels
        p4 = new JPanel();
        p4.setLayout(new FlowLayout());
        p4.add(p2, BorderLayout.WEST);
        p4.add(p3, BorderLayout.EAST);
        this.add(p4);

        setVisible(true);
    }

    public boolean login() {
        System.out.println("Inside : login() in LoginGUI");
        boolean login = false;
        ArrayList<String> pass = progOps.staffLogin();
        for (int i = 0; i < pass.size(); i++) {
            if (pass.get(i).equals(userTxt.getText()) && pass.get(i + 1).equals(passTxt.getText())) {
                login = true;
            }
        }
        return login;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("Inside : actionPerformed() in LoginGUI");
        if(ae.getSource()==login) {
            if(login()==true) {
                Alley a = new Alley(progOps);
                MainScreen ms = new MainScreen(a.getMemberList(), a.getStaffList(), a.getStockList(), a.getBookingList(), a.getLaneList(), progOps);
                this.setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(null, "ACCESS DENIED\nIncorrect username or password!", "ERROR",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
        if(ae.getSource()==forgot) {
            ForgotLoginGUI fl = new ForgotLoginGUI(progOps);
        }
        if(ae.getSource()==exit) {
            progOps.closeDB();
            System.exit(0);
        }
    }
}
