package gui;

import db.MainProgramOperations;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Peter on 19/03/2015.
 */
public class AddBookingGUI implements ActionListener {
    private JDialog addD;
    private MainProgramOperations progOps;
    private ArrayList<Booking> bookingList = new ArrayList<Booking>();
    private GuiElements ge;
    private BookingTab bTab;
    private JPanel addPanel, bottomPanel;
    private JButton addB, clearB;

    public AddBookingGUI(BookingTab bt, MainProgramOperations po, ArrayList<Booking> b) {
        System.out.println("Inside : AddBookingGUI");
        this.bTab = bt;
        this.progOps = po;
        this.bookingList = b;

        addD = new JDialog();
        addD.setTitle("Add New Booking");
        addD.setSize(new Dimension(300, 400));
        addD.setLocationRelativeTo(null);

        ge = new GuiElements();
        addPanel = ge.bookingGui();
        ge.idTxt.setText(Integer.toString(bookingList.size() + 1));

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

        addPanel.add(bottomPanel, BorderLayout.SOUTH);

        addPanel.setBackground(Color.white);
        addD.add(addPanel);
        addD.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in AddBookingGUI");
        //EmailValidator emailValidator = new EmailValidator();
        NumberValidator numValidator = new NumberValidator();
        if (e.getSource().equals(addB)) {
            try {
                if (ge.memIdTxt.getText().equals("") || ge.laneTxt.getText().equals("") || ge.dateTxt.getText().equals("")
                        || ge.startTimeTxt.getText().equals("") || ge.endTimeTxt.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Fields cannot be blank!\n" +
                                    "Please input all details.", "ERROR", JOptionPane.WARNING_MESSAGE);
                } else {
                    int memId = Integer.parseInt(ge.memIdTxt.getText()) - 1;
                    String sName = ge.nameTxt.getText();
                    int lane = Integer.parseInt(ge.laneTxt.getText());
                    String date = ge.dateTxt.getText();
                    String start = date + " " + ge.startTimeTxt.getText();
                    String end = date + " " + ge.endTimeTxt.getText();
                    if (numValidator.isNumeric(date) == false && numValidator.isNumeric(start) == false && numValidator.isNumeric(end) == false) {
                        Booking b = new Booking(memId, lane, start, end );
                        progOps.addBooking(b);
                        Alley a = new Alley(progOps);
                        a.addBooking();
                        bTab.refreshTable();
                        JOptionPane.showMessageDialog(null, "New Booking Data Saved");
                        addD.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Only Id and Time Fields may be numeric", "ERROR", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }catch (NumberFormatException nf) {
                JOptionPane.showMessageDialog(null, "Wrong data format", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (e.getSource().equals(clearB)) {
            ge.memIdTxt.setText("");
            ge.nameTxt.setText("");
            ge.laneTxt.setText("");
            ge.dateTxt.setText("");
            ge.startTimeTxt.setText("");
            ge.endTimeTxt.setText("");
        }
    }
}
