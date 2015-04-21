package gui;

import db.MainProgramOperations;
import model.Booking;
import model.BookingDetails;
import model.NumberValidator;
import model.Staff;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Peter on 20/03/2015.
 */
class CheckAvailabilityGUI implements ActionListener, ItemListener {

    private final JDialog addD;
    private final MainProgramOperations progOps;
    private final ArrayList<Booking> bookingList;
    private final ArrayList<BookingDetails> timeslots;
    private final Staff user;
    private UtilDateModel model;
    private final JFormattedTextField dateInTxt;
    private final JComboBox<String> startHr;
    private final JComboBox<String> startMin;
    private final JComboBox<String> endHr;
    private final JComboBox<String> endMin;
    private final JComboBox<Integer> noLanes;
    private final JTextField startTimeTxt;
    private final JTextField endTimeTxt;
    private final JTextField playerTxt;
    private final JTextArea display;
    private final JButton create;
    private final JButton checkB;
    private final JButton clearB;
    private final JButton cancel;
    private final int [] HRS24 = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private final int [] LANES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    private int games_hours;

    public CheckAvailabilityGUI(MainProgramOperations po, ArrayList<Booking> b, Staff user) {
        System.out.println("Inside : CheckAvailabilityGUI");
        this.progOps = po;
        this.bookingList = b;
        this.user = user;

        timeslots = new ArrayList<>();

        addD = new JDialog();
        addD.setTitle("Check For Availability");
        addD.setSize(new Dimension(300, 500));
        addD.setLocationRelativeTo(null);

        JPanel checkPanel = new JPanel();
        checkPanel.setLayout(new BorderLayout());

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Check Availability");
        checkPanel.setBorder(titled);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(9, 2));
        topPanel.setBackground(Color.white);

        JLabel dateLbl = new JLabel("Date:");
        topPanel.add(dateLbl);
        JDatePanelImpl datePanel = new JDatePanelImpl(new UtilDateModel());
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
        dateInTxt = datePicker.getJFormattedTextField();
        dateInTxt.setText(new java.text.SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));
        dateInTxt.setBackground(Color.WHITE);
        topPanel.add(datePicker);

        JLabel startTime = new JLabel("Start Time:");
        topPanel.add(startTime);

        startHr = new JComboBox<>();
        startHr.setBackground(Color.white);
        topPanel.add(startHr);
        // Populate the hourComboBox list
        String[] HOURS = {"10 am", "11 am", "12 pm", "1 pm", "2 pm", "3 pm", "4 pm", "5 pm", "6 pm",
                "7 pm", "8 pm", "9 pm", "10 pm", "11 pm"};
        for (String HOUR1 : HOURS) {
            startHr.addItem(HOUR1);
        }
        startHr.addItemListener(this);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        startMin = new JComboBox<>();
        startMin.setSize(25, startMin.getPreferredSize().height);
        startMin.setBackground(Color.white);
        topPanel.add(startMin);
        // Populate the hourComboBox list
        String[] MINUTES = {"00", "15", "30", "45"};
        for (String MINUTE1 : MINUTES) {
            startMin.addItem(MINUTE1);
        }
        startMin.addItemListener(this);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        startTimeTxt = new JTextField(15);
        startTimeTxt.setBackground(Color.WHITE);
        startTimeTxt.setEditable(false);
        topPanel.add(startTimeTxt);

        JLabel endTime = new JLabel("End Time:");
        topPanel.add(endTime);

        endHr = new JComboBox<>();
        endHr.setBackground(Color.white);
        topPanel.add(endHr);
        // Populate the hourComboBox list
        for (String HOUR : HOURS) {
            endHr.addItem(HOUR);
        }
        endHr.addItemListener(this);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        endMin = new JComboBox<>();
        endMin.setBackground(Color.white);
        topPanel.add(endMin);
        // Populate the hourComboBox list
        for (String MINUTE : MINUTES) {
            endMin.addItem(MINUTE);
        }
        endMin.addItemListener(this);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        endTimeTxt = new JTextField(15);
        endTimeTxt.setBackground(Color.WHITE);
        endTimeTxt.setEditable(false);
        topPanel.add(endTimeTxt);

        JLabel playerLbl = new JLabel("No of Players:");
        topPanel.add(playerLbl);
        playerTxt = new JTextField(15);
        playerTxt.setBackground(Color.white);
        topPanel.add(playerTxt);

        JLabel laneLbl = new JLabel("No Of Lanes:");
        topPanel.add(laneLbl);
        noLanes = new JComboBox<>();
        noLanes.setBackground(Color.white);
        topPanel.add(noLanes);
        noLanes.addItemListener(this);

        display = new JTextArea();
        DefaultCaret caret = (DefaultCaret)display.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        display.setBackground(Color.WHITE);

        JPanel midPanel = new JPanel();
        midPanel.setBackground(Color.WHITE);
        JScrollPane sp = new JScrollPane();
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(250, 100));
        sp.getViewport().setBackground(Color.WHITE);
        sp.setViewportView(display);
        midPanel.add(sp);

        create = new JButton("Create Booking");
        midPanel.add(create);
        create.setVisible(false);
        create.addActionListener(this);

        checkPanel.add(topPanel, BorderLayout.NORTH);
        checkPanel.setBackground(Color.WHITE);

        checkPanel.add(midPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.setBackground(Color.WHITE);

        checkB = new JButton("Check");
        checkB.addActionListener(this);
        bottomPanel.add(checkB);

        clearB = new JButton("Clear");
        clearB.addActionListener(this);
        bottomPanel.add(clearB);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        bottomPanel.add(cancel);

        checkPanel.add(bottomPanel, BorderLayout.SOUTH);

        checkPanel.setBackground(Color.white);
        addD.add(checkPanel);
        addD.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in CheckAvailabilityGUI");
        NumberValidator numValidator = new NumberValidator();
        if (e.getSource().equals(checkB)) {
            try {
                if (dateInTxt.getText().equals("") || startTimeTxt.getText().equals("")
                        || endTimeTxt.getText().equals("") || playerTxt.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Fields cannot be blank!\n" +
                                    "Please input all details.", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
                else if (endHr.getSelectedIndex() < startHr.getSelectedIndex()) {
                    JOptionPane.showMessageDialog(null,
                            "END TIME cannot be before START TIME!\n" +
                                    "Please fix the times.", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    String d = dateInTxt.getText();
                    String st = startTimeTxt.getText();
                    String et = endTimeTxt.getText();
                    if (numValidator.isNumeric(playerTxt.getText())) {
                        // Populate the No of Lanes ComboBox list
                        noLanes.removeAllItems();
                        int laneIndex = (int) Math.ceil((double) Integer.parseInt(playerTxt.getText()) / 6) - 1;
                        for (int i = laneIndex; i < LANES.length; i++) {
                            noLanes.addItem(LANES[i]);
                        }
                        int available = 0;
                        ResultSet rSet = progOps.checkAvailability(d, st, et);
                        try {
                            while (rSet.next()) {
                                available = rSet.getInt(1);
                            }
                            display.setText("Lanes Available: " + available +
                                    "\nLanes Required: " + noLanes.getSelectedItem() + "\n\n");
                                    if (available >= (Integer)(noLanes.getSelectedItem())) {
                                        display.append("SUCCESS, there are lanes available!");
                                        create.setVisible(true);
                                    }
                                    else {
                                        display.append("Insufficient Lanes available!");
                                        create.setVisible(false);
                                    }
                        } catch (SQLException ex) {
                            System.out.println(e);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null,
                                "No of Players must be numeric", "ERROR", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
            catch (NumberFormatException nf) {
                JOptionPane.showMessageDialog(null, "Wrong data format", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (e.getSource() == create) {
            int lanes = (Integer)noLanes.getSelectedItem();
            int players = Integer.parseInt(playerTxt.getText());
            int [] freeLanes = progOps.getLanesAvailable(dateInTxt.getText(), startTimeTxt.getText(), endTimeTxt.getText());
            DateFormat formatter ;
            Date date = new Date();
            try {
                formatter = new SimpleDateFormat("dd-MMM-yy");
                date = formatter.parse(dateInTxt.getText());
            }
            catch (Exception exc) {
                System.out.println(exc);
            }
            for (int i = 0; i < (Integer)noLanes.getSelectedItem(); i ++) {
                int [] slots = progOps.getTimes(startTimeTxt.getText(), endTimeTxt.getText());
                games_hours = progOps.getNumHours(startTimeTxt.getText(), endTimeTxt.getText());
                for (int slot : slots) {
                    BookingDetails bd = new BookingDetails(progOps.getNumBookings() + 1, freeLanes[i], slot, date);
                    //progOps.addBookingDetails(bd);
                    timeslots.add(bd);
                }
            }
            AddBookingGUI reserve = new AddBookingGUI(user, progOps, bookingList, games_hours, lanes, players, timeslots);
            addD.dispose();
        }
        else if (e.getSource().equals(clearB)) {
            startTimeTxt.setText("");
            endTimeTxt.setText("");
            noLanes.removeAllItems();
            playerTxt.setText("");
            display.setText("");
            create.setVisible(false);
        }
        else if (e.getSource().equals(cancel)) {
            addD.dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        System.out.println("Inside : itemStateChanged() in CheckAvailabilityGUI");
        startTimeTxt.setText(HRS24[startHr.getSelectedIndex()] + ":" + startMin.getSelectedItem().toString());
        endTimeTxt.setText(HRS24[endHr.getSelectedIndex()] + ":" + endMin.getSelectedItem().toString());
    }
}
