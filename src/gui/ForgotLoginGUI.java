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

/**
 * Created by Peter on 18/03/2015.
 */
public class ForgotLoginGUI implements ActionListener, ItemListener {
    private JDialog forgotD;
    private JPanel forgotPanel, bottomPanel, p1, p2, p3;
    private JLabel msgLbl, ansLbl, userLbl, passLbl, confPassLbl, questLbl, questionTxt;
    private JTextField ansTxt;
    private JPasswordField passTxt, confPassTxt;
    private JButton login, cancel;
    private JComboBox <String> usersCombo;
    private ArrayList<String> logins;
    private String usrName;
    private MainProgramOperations progOps;

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
        p1 = new JPanel(new BorderLayout());
        p1.setBackground(Color.WHITE);
        msgLbl = new JLabel("<html><br>Forgot your password?<br>Please enter the following details.</html>", SwingConstants.CENTER);
        p1.add(Box.createRigidArea(new Dimension(0, 50)));
        p1.add(msgLbl, BorderLayout.NORTH);

        //P2
        p2 = new JPanel(new GridLayout(8, 2));
        p2.setPreferredSize(new Dimension(200, 100));
        p2.setBackground(Color.WHITE);

        userLbl = new JLabel("Username: ");
        p2.add(userLbl);
        logins = progOps.queryLogin();
        usersCombo = new JComboBox<String>();
        for (int i = 0; i < logins.size(); i++) {
            usersCombo.addItem(logins.get(i));
        }
        usersCombo.setSelectedItem(0);
        usersCombo.setBackground(Color.WHITE);
        usersCombo.addItemListener(this);
        p2.add(usersCombo);

        questLbl = new JLabel("Question:");
        p2.add(questLbl);
        String quest = progOps.getSecQuestion(usersCombo.getSelectedItem().toString());
        questionTxt = new JLabel(quest + "?");
        p2.add(questionTxt);

        ansLbl = new JLabel("Answer: ");
        p2.add(ansLbl);
        ansTxt = new JTextField(15);
        p2.add(ansTxt);

        passLbl = new JLabel("New Password: ");
        p2.add(passLbl);
        passTxt = new JPasswordField(15);
        p2.add(passTxt);

        confPassLbl = new JLabel("Re-Type Password: ");
        p2.add(confPassLbl);
        confPassTxt = new JPasswordField(15);
        p2.add(confPassTxt);

        //P3
        p3 = new JPanel(new FlowLayout());
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
        forgotPanel = new JPanel();
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

    public void updatePass() {
        System.out.println("Inside : updatePass() in ForgotLoginGUI");
        String password = passTxt.getText();
        String confPassword = confPassTxt.getText();
        String answer = ansTxt.getText();
        String ans = progOps.changePassword(password, usrName);
        if (!password.equals(confPassword)) {
            JOptionPane.showMessageDialog(null,
                    "Passwords do not match", "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        else if (!answer.equals(ans)) {
            JOptionPane.showMessageDialog(null,
                    "Incorrect Answer!", "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        else {
            progOps.changePassword(password, usrName);
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