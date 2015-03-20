package gui;

import db.MainProgramOperations;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Peter on 06/03/2015.
 */
public class AdminTab extends JPanel {

    JButton staff, games, members, accounts;
    private MainProgramOperations progOps;

    public AdminTab(MainProgramOperations po) {
        System.out.println("Inside : AdminTabGUI");
        this.progOps = po;
        this.setPreferredSize(new Dimension(780, 300));
        this.setBackground(Color.WHITE);

        JPanel p1 = new JPanel();
        p1.setPreferredSize(new Dimension(400, 200));
        p1.setLayout(new BorderLayout());
        p1.setBackground(Color.WHITE);
        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(2, 2));
        p2.setPreferredSize(new Dimension(300, 200));
        p2.setBackground(Color.WHITE);
        staff = new JButton("Staff Reports");
        games = new JButton("Games Played Reports");
        members = new JButton("Membership Reports");
        accounts = new JButton("Financial Reports");
        p2.add(staff);
        p2.add(games);
        p2.add(members);
        p2.add(accounts);
        JPanel p3 = new JPanel();
        p3.setPreferredSize(new Dimension(780, 50));
        p3.setBackground(Color.WHITE);
        p1.add(p3, BorderLayout.NORTH);
        p1.add(p2, BorderLayout.CENTER);
        this.add(p1);
    }
}