package gui;

import db.MainProgramOperations;
import controller.ModifyDateAndTime;
import model.Alley;
import model.Booking;
import model.NumberValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Peter on 24/03/2015.
 */
public class UpdateBookingGUI implements ActionListener

{
    private JDialog addD;
    private MainProgramOperations progOps;
    private ArrayList<Booking> bookingList = new ArrayList<Booking>();
    private ResultSet rSet;
    private GuiElements ge;
    private BookingTab bTab;
    private JPanel updatePanel, bottomPanel;
    private JButton updateB, clearB, cancel;

    public UpdateBookingGUI (BookingTab bt, MainProgramOperations po, ArrayList<Booking> b, String search) {
        System.out.println("Inside : UpdateBookingGUI");
        this.bTab = bt;
        this.progOps = po;
        this.bookingList = b;

        addD = new JDialog();
        addD.setTitle("Update Booking");
        addD.setSize(new Dimension(300, 400));
        addD.setLocationRelativeTo(null);

        ge = new GuiElements();
        updatePanel = ge.bookingGui();

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.setBackground(Color.white);

        updateB = new JButton("Update");
        updateB.addActionListener(this);
        bottomPanel.add(updateB);

        clearB = new JButton("Clear");
        clearB.addActionListener(this);
        bottomPanel.add(clearB);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        bottomPanel.add(cancel);

        updatePanel.add(bottomPanel, BorderLayout.SOUTH);

        updatePanel.setBackground(Color.white);
        addD.add(updatePanel);
        addD.setVisible(true);

        fillFields(search);
    }

    public void fillFields(String search) {
        System.out.println("Inside : fillFields() in UpdateBookingGUI");
        ModifyDateAndTime mdt = new ModifyDateAndTime();
        try {
            rSet = progOps.searchBookings(search);
            while (rSet.next()) {
                ge.idTxt.setText(Integer.toString(rSet.getInt(1)));
                ge.memIdTxt.setText(Integer.toString(rSet.getInt(2) + 1));
                //ge.nameTxt.setText(rSet.getString(3));
                ge.laneTxt.setText(rSet.getString(3));
                ge.dateInTxt.setText(mdt.modifyDateLayout(rSet.getString(4)));
                ge.startTimeTxt.setText(mdt.modifyTimeLayout(rSet.getString(4)));
                ge.endTimeTxt.setText(mdt.modifyTimeLayout(rSet.getString(5)));
            }
        } catch (Exception e) {
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in UpdateBookingGUI");
        NumberValidator numValidator = new NumberValidator();
        if (e.getSource().equals(updateB)) {
            try {
                if (ge.memIdTxt.getText().equals("") || ge.laneTxt.getText().equals("") || ge.dateTxt.getText().equals("")
                        || ge.startTimeTxt.getText().equals("") || ge.endTimeTxt.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Fields cannot be blank!\n" +
                            "Please input all details.", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    int memId = Integer.parseInt(ge.memIdTxt.getText()) - 1;
                    int lane = Integer.parseInt(ge.laneTxt.getText());
                    String [] srt = ge.startTimeTxt.getText().split(" ");
                    String [] en = ge.endTimeTxt.getText().split(" ");
                    String date = ge.dateTxt.getText();
                    System.out.println(date + srt[0]);
                    String start = date + " " + srt[0];
                    String end = date + " " + en[0];
                    if (numValidator.isNumeric(date) == false && numValidator.isNumeric(start) == false && numValidator.isNumeric(end) == false) {
                        /**Booking b = new Booking(memId, lane, start, end );
                        progOps.addBooking(b);**/
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
        else if (e.getSource() .equals(cancel)) {
            addD.dispose();
        }
    }
}
