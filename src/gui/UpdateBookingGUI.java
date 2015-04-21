package gui;

import db.MainProgramOperations;
import model.Alley;
import model.NumberValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

/**
 * Created by Peter on 24/03/2015.
 */
class UpdateBookingGUI implements ActionListener

{
    private final JDialog addD;
    private final MainProgramOperations progOps;
    private final GuiElements ge;
    private final BookingTab bTab;
    private final JButton updateB;
    private final JButton clearB;
    private final JButton cancel;

    public UpdateBookingGUI(BookingTab bt, MainProgramOperations po, String search) {
        System.out.println("Inside : UpdateBookingGUI");
        this.bTab = bt;
        this.progOps = po;

        addD = new JDialog();
        addD.setTitle("Update Booking");
        addD.setSize(new Dimension(300, 400));
        addD.setLocationRelativeTo(null);

        ge = new GuiElements();
        JPanel updatePanel = ge.bookingGui();

        JPanel bottomPanel = new JPanel();
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

    private void fillFields(String search) {
        System.out.println("Inside : fillFields() in UpdateBookingGUI");
        try {
            ResultSet rSet = progOps.searchBookings(search);
            while (rSet.next()) {
                ge.idTxt.setText(Integer.toString(rSet.getInt(1)));
                ge.staffIdTxt.setText(Integer.toString(rSet.getInt(2) + 1));
                ge.laneTxt.setText(rSet.getString(3));
                ge.dateInTxt.setText(new java.text.SimpleDateFormat("dd-MMM-yyyy").format(rSet.getString(4)));
                ge.startTimeTxt.setText(new java.text.SimpleDateFormat("HH:mm").format(rSet.getString(4)));
                ge.endTimeTxt.setText(new java.text.SimpleDateFormat("HH:mm").format(rSet.getString(5)));
            }
        } catch (Exception ignored) {
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in UpdateBookingGUI");
        NumberValidator numValidator = new NumberValidator();
        if (e.getSource().equals(updateB)) {
            try {
                if (ge.laneTxt.getText().equals("") || ge.dateTxt.getText().equals("")
                        || ge.startTimeTxt.getText().equals("") || ge.endTimeTxt.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Fields cannot be blank!\n" +
                            "Please input all details.", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    String [] srt = ge.startTimeTxt.getText().split(" ");
                    String [] en = ge.endTimeTxt.getText().split(" ");
                    String date = ge.dateTxt.getText();
                    System.out.println(date + srt[0]);
                    String start = date + " " + srt[0];
                    String end = date + " " + en[0];
                    if (!numValidator.isNumeric(date) && !numValidator.isNumeric(start) && !numValidator.isNumeric(end)) {
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
