package gui;

import db.MainProgramOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Peter on 06/03/2015.
 */
class AdminTab extends JPanel implements ActionListener {

    private final JButton staff;
    private final JButton games;
    private final JButton members;
    private final MainProgramOperations progOps;

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
        staff.addActionListener(this);
        games = new JButton("Games Played Reports");
        games.addActionListener(this);
        members = new JButton("Membership Reports");
        members.addActionListener(this);
        JButton accounts = new JButton("Financial Reports");
        accounts.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : actionPerformed() in AdminTabGUI");
        if (e.getSource() == staff){
        //this.setVisible(false);
            this.removeAll();
        this.add(new StaffReportsGUI(progOps));//Adding to content pane, not to Frame
        repaint();
        printAll(getGraphics());//Extort print all content
         }
        else if(e.getSource() == games){
            this.setVisible(false);
            this.removeAll();
            this.add(new GamesReportGUI(progOps));//Adding to content pane, not to Frame
            repaint();
            printAll(getGraphics());//Extort print all content
        }
        else if(e.getSource() == members){
            this.removeAll();
            this.add(new MembershipReportGUI(progOps));//Adding to content pane, not to Frame
            repaint();
            printAll(getGraphics());//Extort print all content
        }
        else {
            this.removeAll();
            this.add(new FinancialReportsGUI(progOps));//Adding to content pane, not to Frame
            repaint();
            printAll(getGraphics());//Extort print all content
        }
    }


}