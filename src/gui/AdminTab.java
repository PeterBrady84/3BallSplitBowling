package gui;

import db.MainProgramOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Peter on 06/03/2015.
 */
public class AdminTab extends JPanel implements ActionListener {

    JButton staff, games, members, accounts;
    private MainProgramOperations progOps;

    public AdminTab(MainProgramOperations po) {
        System.out.println("Inside : AdminTabGUI");
        this.progOps = po;
        this.setPreferredSize(new Dimension(780, 300));
        this.setBackground(Color.WHITE);
        ((FlowLayout)this.getLayout()).setVgap(0);

        refresh();
    }

    public void refresh() {
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
        accounts = new JButton("Financial Reports");
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

    /*public void fillCards() {
        cards.add(card1, "Card 1");
        cards.add(card2, "Card 2");
        cards.add(card3, "Card 3");
        then to flip to a different component:

        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, "Card 2");
        To navigate to the next component, you can use:

        cardLayout.next(cards);
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : actionPerformed() in AdminTabGUI");
        if (e.getSource() == staff) {
            //this.setVisible(false);;
            this.removeAll();
            this.add(new StaffReportsGUI(progOps));//Adding to content pane, not to Frame
            this.revalidate();
            this.repaint();
            printAll(getGraphics()); //Extort print all content
        }
        else if (e.getSource() == games) {
            //this.setVisible(true);;
            this.removeAll();
            this.add(new GamesReportGUI(this, progOps));//Adding to content pane, not to Frame
            this.revalidate();
            this.repaint();
            printAll(getGraphics());//Extort print all content
        }
        else if(e.getSource() == members){
            MembershipReportGUI mr = new MembershipReportGUI(progOps);
            this.removeAll();
            this.add(mr);//Adding to content pane, not to Frame
            this.revalidate();
            this.repaint();
            printAll(getGraphics());//Extort print all content
        }
        else {
            FinancialReportsGUI fr = new FinancialReportsGUI(progOps);
            this.removeAll();
            this.add(fr);//Adding to content pane, not to Frame
            this.revalidate();
            this.repaint();
            printAll(getGraphics());//Extort print all content
        }
    }


}