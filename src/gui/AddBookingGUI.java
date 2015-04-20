package gui;

import db.MainProgramOperations;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Peter on 19/03/2015.
 */
public class AddBookingGUI implements ActionListener {
    private JDialog addD;
    private MainProgramOperations progOps;
    private ArrayList<Booking> bookingList = new ArrayList<Booking>();
    private GuiElements ge;
    private MainScreen ms;
    private BookingTab bTab;
    int noLanes, noPlayers;
    private Staff user;
    private JPanel addPanel, bottomPanel;
    private JButton addB, clearB, cancel;

    public AddBookingGUI(MainScreen ms, BookingTab bt, MainProgramOperations po, ArrayList<Booking> b, Staff user, int nl, int np) {
        System.out.println("Inside : AddBookingGUI");
        this.bTab = bt;
        this.ms = ms;
        this.progOps = po;
        this.bookingList = b;
        this.noLanes = nl;
        this.noPlayers = np;
        this.user = user;

        addD = new JDialog();
        addD.setTitle("Add New Booking");
        addD.setSize(new Dimension(600, 400));
        addD.setLocationRelativeTo(null);

        ge = new GuiElements();
        addPanel = ge.bookingGui();
        ge.idTxt.setText(Integer.toString(bookingList.size() + 1));
        ge.staffIdTxt.setText(Integer.toString(user.getId()));
        ge.perGame.addActionListener(this);
        ge.perHour.addActionListener(this);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.setBackground(Color.white);

        addB = new JButton("Add");
        addB.addActionListener(this);
        bottomPanel.add(addB);

        clearB = new JButton("Clear");
        clearB.addActionListener(this);
        bottomPanel.add(clearB);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        bottomPanel.add(cancel);

        addPanel.add(bottomPanel, BorderLayout.SOUTH);

        addPanel.setBackground(Color.white);
        addD.add(addPanel);
        addD.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in AddBookingGUI");
        NumberValidator numValidator = new NumberValidator();
        if (e.getSource() == ge.perGame) {
            ge.noGamesTxt.setEditable(true);
        }
        else if (e.getSource() == ge.perHour) {
            ge.noGamesTxt.setText("");
            ge.noGamesTxt.setBackground(Color.LIGHT_GRAY);
            ge.noGamesTxt.setEditable(false);
        }
        if(e.getSource().equals(addB)) {
            try {
                if (ge.perGame.isSelected() && ge.noGamesTxt.getText().equals("") ) {
                    JOptionPane.showMessageDialog(null, "Fields cannot be blank!\n" +
                            "Please input all details.", "ERROR", JOptionPane.WARNING_MESSAGE);
                } else {
                    int bookingId = Integer.parseInt(ge.idTxt.getText()) - 1;
                    int memId = Integer.parseInt((ge.memberIdTxt.getText()));
                    int staffId = Integer.parseInt(ge.staffIdTxt.getText());
                    int games = 0;
                    String pricingPerHr = "";
                    if (ge.perGame.isSelected()) {
                        games = Integer.parseInt(ge.noGamesTxt.getText());
                        String pricingPerHour = "N";
                    }
                    else {
                        pricingPerHr = "Y";
                    }
                    String type = ge.typeCombo.getSelectedItem().toString();
                    if (numValidator.isNumeric(ge.noGamesTxt.getText())) {
                        Booking b = new Booking(bookingId, memId, staffId, noLanes, games, 1,  noPlayers, pricingPerHr, type);
                        //progOps.addBooking(b);
                        Alley a = new Alley(progOps);
                        a.addBooking();
                        bTab.refreshTable();
                        JOptionPane.showMessageDialog(null, "New Booking Data Saved");
                        //int bookingCount = LoginGUI.user.getBookings();
                        //bookingCount++;
                        //progOps.increaseBooking(bookingCount);
                        addD.setVisible(false);
                        ms.refreshTabbedPane(new Date());
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Only Id and Time Fields may be numeric", "ERROR", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (NumberFormatException nf) {
                JOptionPane.showMessageDialog(null, "Wrong data format", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (e.getSource().equals(clearB)) {
            ge.idTxt.setText("");
            ge.staffIdTxt.setText("");
            ge.laneTxt.setText("");
            ge.dateTxt.setText("");
            ge.startTimeTxt.setText("");
            ge.endTimeTxt.setText("");
        } else if (e.getSource().equals(cancel)) {
            addD.dispose();
        }
    }
}