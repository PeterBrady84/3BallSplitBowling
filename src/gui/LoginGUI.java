package gui;

import controller.WelcomeAnimation;
import db.MainProgramOperations;
import model.Alley;
import model.Staff;

import javax.swing.*;
import javax.swing.border.Border;
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
    private final JPanel loginPanel;
    private final JPanel welcomePanel;
    private final JPanel detailsPanel;
    private final JPanel bottomPanel;
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
        loginPanel = new JPanel();
        loginPanel.setLayout(new BorderLayout());
        loginPanel.setPreferredSize(new Dimension(300, 400));
        loginPanel.setBorder(BorderFactory.createEtchedBorder());
        loginPanel.setBackground(Color.WHITE);

        welcomePanel = new JPanel();
        welcomePanel.setLayout(new FlowLayout());
        welcomePanel.setBackground(Color.WHITE);

        Border etched = BorderFactory.createEtchedBorder();
        welcomePanel.setBorder(etched);

        JTextField welcome = new JTextField("Welcome");
        Font font = new Font(Font.SERIF, Font.BOLD | Font.ITALIC, 55);
        welcome.setFont(font);
        welcome.setBackground(Color.WHITE);
        welcome.setEditable(false);
        welcome.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        welcomePanel.add(welcome, BorderLayout.NORTH);

        detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(7, 2));
        detailsPanel.setBackground(Color.white);
        Font font1 = new Font("Arial", Font.BOLD, 14);

        etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Login Details");
        detailsPanel.setBorder(titled);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        JLabel userLbl = new JLabel("Username:");
        userLbl.setFont(font1);
        detailsPanel.add(userLbl);
        userTxt = new JTextField(15);
        userTxt.setFont(font1);
        userTxt.setBackground(Color.white);
        detailsPanel.add(userTxt);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(font1);
        detailsPanel.add(passLbl);
        passTxt = new JPasswordField(15);
        passTxt.setFont(font1);
        passTxt.setBackground(Color.white);
        detailsPanel.add(passTxt);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        login = new JButton("Login");
        login.setFont(font1);
        detailsPanel.add(login);
        login.addActionListener(this);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.setBackground(Color.WHITE);

        forgot = new JButton("Forget Login");
        forgot.setFont(font1);
        bottomPanel.add(forgot);
        forgot.addActionListener(this);

        exit = new JButton("Exit");
        exit.setFont(font1);
        bottomPanel.add(exit);
        exit.addActionListener(this);

        loginPanel.add(welcomePanel, BorderLayout.NORTH);
        loginPanel.add(detailsPanel, BorderLayout.CENTER);
        loginPanel.add(bottomPanel, BorderLayout.SOUTH);

        //P3
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
        p4.add(loginPanel, BorderLayout.WEST);
        p4.add(p3, BorderLayout.EAST);
        p4.setBackground(Color.WHITE);
        this.add(p4);

        this.getRootPane().setDefaultButton(login);

        setVisible(true);
    }

    public boolean login() {
        return progOps.checkPass(userTxt.getText(), passTxt.getPassword());
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println("Inside : actionPerformed() in LoginGUI");
        if(ae.getSource()==login) {
            if (userTxt.getText().equals("") || passTxt.getPassword().length == 0) {
                JOptionPane.showMessageDialog(null,
                        "Fields cannot be blank!\n" +
                                "Please input all details.", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
            else if (login()) {
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
                        a.getBookingList(), a.getBookingDetailsList(),
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
