package gui;

import db.MainProgramOperations;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Peter on 18/03/2015.
 */
class ForgotLoginGUI implements ActionListener, ItemListener {
    private final JDialog forgotD;
    private final JLabel questionTxt;
    private final JTextField ansTxt;
    private final JPasswordField passTxt, confPassTxt;
    private final JButton login, cancel;
    private final JComboBox <String> usersCombo;
    private String username;
    private final MainProgramOperations progOps;

    public ForgotLoginGUI(MainProgramOperations po) {
        System.out.println("Inside : ForgotLoginGUI");
        this.progOps = po;

        forgotD = new JDialog();
        forgotD.setTitle("Forgot Login");
        forgotD.setSize(300, 400);
        forgotD.setLocationRelativeTo(null);
        forgotD.setBackground(Color.white);
        forgotD.setResizable(false);

        //P1
        JPanel p1 = new JPanel(new BorderLayout());
        p1.setBackground(Color.WHITE);
        JLabel msgLbl = new JLabel("<html><br>Forgot your password?<br>Please enter the following details.</html>", SwingConstants.CENTER);
        p1.add(Box.createRigidArea(new Dimension(0, 50)));
        p1.add(msgLbl, BorderLayout.NORTH);

        //P2
        JPanel p2 = new JPanel(new GridLayout(8, 2));
        p2.setPreferredSize(new Dimension(200, 100));
        p2.setBackground(Color.WHITE);

        JLabel userLbl = new JLabel("Username: ");
        p2.add(userLbl);
        ArrayList<String> logins = progOps.queryLogin();
        usersCombo = new JComboBox<>();
        for (String login1 : logins) {
            usersCombo.addItem(login1);
        }
        usersCombo.setSelectedItem(0);
        usersCombo.setBackground(Color.WHITE);
        usersCombo.addItemListener(this);
        p2.add(usersCombo);

        JLabel questLbl = new JLabel("Question:");
        p2.add(questLbl);
        String quest = progOps.getSecQuestion(usersCombo.getSelectedItem().toString());
        questionTxt = new JLabel(quest + "?");
        p2.add(questionTxt);

        JLabel ansLbl = new JLabel("Answer: ");
        p2.add(ansLbl);
        ansTxt = new JTextField(15);
        p2.add(ansTxt);

        JLabel passLbl = new JLabel("New Password: ");
        p2.add(passLbl);
        passTxt = new JPasswordField(15);
        p2.add(passTxt);

        JLabel confPassLbl = new JLabel("Re-Type Password: ");
        p2.add(confPassLbl);
        confPassTxt = new JPasswordField(15);
        p2.add(confPassTxt);

        //P3
        JPanel p3 = new JPanel(new FlowLayout());
        p3.setPreferredSize(new Dimension(150, 50));
        p3.setBorder(BorderFactory.createEtchedBorder());
        //p3.add(Box.createRigidArea(new Dimension(40, 0)));
        p3.setBackground(Color.WHITE);

        login = new JButton("Login");
        login.addActionListener(this);
        p3.add(login);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        p3.add(cancel);

        //Adding Panels
        JPanel forgotPanel = new JPanel();
        forgotPanel.setLayout(new BorderLayout());
        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Forgot Login Details");
        forgotPanel.setBorder(titled);

        forgotPanel.setBackground(Color.white);
        forgotPanel.add(p1, BorderLayout.NORTH);
        forgotPanel.add(p2, BorderLayout.CENTER);
        forgotPanel.add(p3, BorderLayout.SOUTH);
        forgotD.add(forgotPanel);

        forgotD.setVisible(true);
    }

    private void updatePass() {
        System.out.println("Inside : updatePass() in ForgotLoginGUI");
        username = usersCombo.getSelectedItem().toString();
        char[] password = passTxt.getPassword();
        char[] confPassword = confPassTxt.getPassword();
        String answer = ansTxt.getText();
        String ans = progOps.changePassword(password, username);
        if (!Arrays.equals(password, confPassword)) {
            JOptionPane.showMessageDialog(null,
                    "Passwords do not match", "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        else if (!answer.equals(ans)) {
            JOptionPane.showMessageDialog(null,
                    "Incorrect Answer!", "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        else {
            progOps.changePassword(password, username);
            JOptionPane.showMessageDialog(null,"Password has been updated");
            forgotD.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("Inside : actionPerformed() in ForgotLoginGUI");
        if(ae.getSource()==login) {
            updatePass();
            forgotD.dispose();
        }
        if(ae.getSource()==cancel) {
            forgotD.dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent ae) {
        System.out.println("Inside : itemStateChanged() in ForgotLoginGui");
        questionTxt.setText(progOps.getSecQuestion(usersCombo.getSelectedItem().toString()));
    }
}