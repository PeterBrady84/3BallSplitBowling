package gui;

import db.MainProgramOperations;
import model.*;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import org.joda.time.DateTime;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Peter on 24/03/2015.
 */
public class UpdateBookingGUI implements ActionListener, ItemListener

{
    private JDialog updateD;
    private MainProgramOperations progOps;
    private MainScreen ms;
    private BookingTab bt;
    private ArrayList<Booking> bookingList;
    private ArrayList<BookingDetails> timeslots;
    private ResultSet rSet;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;
    private JFormattedTextField dateInTxt;
    private JLabel dateLbl, startTime, endTime, playerLbl, laneLbl;
    private JComboBox startHr, startMin, endHr, endMin, noLanes;
    private JTextField startTimeTxt, endTimeTxt, playerTxt;
    private JTextArea display;
    private JPanel checkPanel, topPanel, bottomPanel;
    private final int [] HRS24 = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private final String [] HOURS = {"10 am", "11 am", "12 pm", "1 pm", "2 pm", "3 pm", "4 pm", "5 pm", "6 pm",
            "7 pm", "8 pm", "9 pm", "10 pm", "11 pm"};
    private final String [] MINUTES = {"00", "15", "30", "45"};
    private final int [] LANES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    private int games_hours;
    private String s;
    private JButton updateB, clearB, cancel, checkB;

    public UpdateBookingGUI (BookingTab bt, MainScreen ms, MainProgramOperations po, ArrayList<Booking> b, String s) {
        System.out.println("Inside : UpdateBookingGUI");
        this.progOps = po;
        this.ms = ms;
        this.bt = bt;
        this.bookingList = b;

        timeslots = new ArrayList<BookingDetails>();

        updateD = new JDialog();
        updateD.setTitle("Edit Booking");
        updateD.setSize(new Dimension(300, 500));
        updateD.setLocationRelativeTo(null);

        checkPanel = new JPanel();
        checkPanel.setLayout(new BorderLayout());

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Edit Booking Details");
        checkPanel.setBorder(titled);

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(9, 2));
        topPanel.setBackground(Color.white);

        dateLbl = new JLabel("Date:");
        topPanel.add(dateLbl);
        datePanel = new JDatePanelImpl(new UtilDateModel());
        datePicker = new JDatePickerImpl(datePanel);
        dateInTxt = datePicker.getJFormattedTextField();
        dateInTxt.setText(new java.text.SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));
        dateInTxt.setBackground(Color.WHITE);
        topPanel.add(datePicker);

        startTime = new JLabel("Start Time:");
        topPanel.add(startTime);

        startHr = new JComboBox<>();
        startHr.setBackground(Color.white);
        topPanel.add(startHr);
        // Populate the hourComboBox list
        for (int i = 0; i < HOURS.length; i++) {
            startHr.addItem(HOURS[i]);
        }
        startHr.addItemListener(this);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        startMin = new JComboBox<>();
        startMin.setSize(25, startMin.getPreferredSize().height);
        startMin.setBackground(Color.white);
        topPanel.add(startMin);
        // Populate the hourComboBox list
        for (int i = 0; i < MINUTES.length; i++) {
            startMin.addItem(MINUTES[i]);
        }
        startMin.addItemListener(this);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        startTimeTxt = new JTextField(15);
        startTimeTxt.setBackground(Color.WHITE);
        startTimeTxt.setEditable(false);
        topPanel.add(startTimeTxt);

        endTime = new JLabel("End Time:");
        topPanel.add(endTime);

        endHr = new JComboBox<>();
        endHr.setBackground(Color.white);
        topPanel.add(endHr);
        // Populate the hourComboBox list
        for (int i = 0; i < HOURS.length; i++) {
            endHr.addItem(HOURS[i]);
        }
        endHr.addItemListener(this);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        endMin = new JComboBox<>();
        endMin.setBackground(Color.white);
        topPanel.add(endMin);
        // Populate the hourComboBox list
        for (int i = 0; i < MINUTES.length; i++) {
            endMin.addItem(MINUTES[i]);
        }
        endMin.addItemListener(this);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        endTimeTxt = new JTextField(15);
        endTimeTxt.setBackground(Color.WHITE);
        endTimeTxt.setEditable(false);
        topPanel.add(endTimeTxt);

        playerLbl = new JLabel("No of Players:");
        topPanel.add(playerLbl);
        playerTxt = new JTextField(15);
        playerTxt.setBackground(Color.white);
        topPanel.add(playerTxt);

        laneLbl = new JLabel("No Of Lanes:");
        topPanel.add(laneLbl);
        noLanes = new JComboBox<>();
        noLanes.setBackground(Color.white);
        topPanel.add(noLanes);
        noLanes.addItemListener(this);

        display = new JTextArea();
        DefaultCaret caret = (DefaultCaret)display.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        display.setBackground(Color.WHITE);
        Font font = new Font(Font.SERIF, Font.BOLD, 14);
        display.setFont(font);
        display.setText("\u2022 Please input number of players.\n\n" +
                "\u2022 Then select 'Check'.\n\n" +
                "\u2022 Calculates no of lanes required.");

        JPanel midPanel = new JPanel();
        midPanel.setBackground(Color.WHITE);
        JScrollPane sp = new JScrollPane();
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(250, 100));
        sp.getViewport().setBackground(Color.WHITE);
        sp.setViewportView(display);
        midPanel.add(sp);

        updateB = new JButton("Update Booking");
        midPanel.add(updateB);
        updateB.setVisible(false);
        updateB.addActionListener(this);

        checkPanel.add(topPanel, BorderLayout.NORTH);
        checkPanel.setBackground(Color.WHITE);

        checkPanel.add(midPanel, BorderLayout.CENTER);

        bottomPanel = new JPanel();
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
        updateD.add(checkPanel);
        updateD.setVisible(true);

        fillFields(s);
    }

    public void fillFields(String s) {
        System.out.println("Inside : fillFields() in UpdateBookingGUI");
        this.s = s;
        try {
            rSet = progOps.searchBookings(s);
            rSet.next();
            dateInTxt.setText(new java.text.SimpleDateFormat("dd-MMM-yyyy").format(rSet.getDate(1)));
            playerTxt.setText(rSet.getString(2));
            startTimeTxt.setText(rSet.getString(3));
            endTimeTxt.setText(rSet.getString(4));
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in UpdateBookingGUI");
        NumberValidator numValidator = new NumberValidator();
        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MMM-yyy").parse(dateInTxt.getText());
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        cal.set(Calendar.MILLISECOND, 0);
        Date selected = cal.getTime();
        Date now = new java.util.Date();
        if (e.getSource().equals(checkB)) {
            try {
                if (dateInTxt.getText().equals("") || startTimeTxt.getText().equals("")
                        || endTimeTxt.getText().equals("") || playerTxt.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Fields cannot be blank!\n" +
                                    "Please input all details.", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
                else if (selected.before(now)){
                    JOptionPane.showMessageDialog(null,
                            "Selected date cannot be in the past!\n" +
                                    "Please fix the date.", "ERROR", JOptionPane.WARNING_MESSAGE);
                    dateInTxt.setText(new java.text.SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));
                }
                else if (endHr.getSelectedIndex() <= startHr.getSelectedIndex()) {
                    JOptionPane.showMessageDialog(null,
                            "END TIME cannot be the same, or before START TIME!\n" +
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
                        rSet = progOps.checkAvailability(d, st, et);
                        try {
                            while (rSet.next()) {
                                available = rSet.getInt(1);
                            }
                            if (available >= (Integer)(noLanes.getSelectedItem())) {
                                display.setText("\u2022 SUCCESS.\n\n" +
                                        "\u2022 Lanes Available: " + available + ".\n" +
                                        "\u2022 Lanes Required: " + noLanes.getSelectedItem() + ".\n" +
                                        "\u2022 Sufficient lanes available!");
                                updateB.setVisible(true);
                            }
                            else {
                                display.setText("\u2022 UNSUCCESSFUL.\n\n" +
                                        "Insufficient Lanes available!");
                                updateB.setVisible(false);
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
                JOptionPane.showMessageDialog(null, "Wrong data format 1", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (e.getSource().equals(updateB)) {
            int id = Integer.parseInt(s.replaceAll("[\\D]", ""));
            DateFormat formatter ;
            date = new Date();
            try {
                formatter = new SimpleDateFormat("dd-MMM-yy");
                date = formatter.parse(dateInTxt.getText());
            }
            catch (Exception exc) {
                System.out.println(exc);
            }
            try {
                System.out.println("here");
                if (startTimeTxt.getText().equals("") || endTimeTxt.getText().equals("") || playerTxt.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Fields cannot be blank!\n" +
                            "Please input all details.", "ERROR", JOptionPane.WARNING_MESSAGE);

                }

                int[] freeLanes = progOps.getLanesAvailable(dateInTxt.getText(), startTimeTxt.getText(), endTimeTxt.getText());

                for (int i = 0; i < (Integer) noLanes.getSelectedItem(); i++) {
                    int[] slots = progOps.getTimes(startTimeTxt.getText(), endTimeTxt.getText());
                    games_hours = progOps.getNumHours(startTimeTxt.getText(), endTimeTxt.getText());
                    for (int slot : slots) {
                        BookingDetails bd = new BookingDetails(id, freeLanes[i], slot, date);
                        timeslots.add(bd);
                    }
                }

                progOps.updateBooking(id, (Integer) noLanes.getSelectedItem(), Integer.parseInt(playerTxt.getText()));
                int i = 0;
                for (BookingDetails timeSlot : timeslots) {
                    System.out.println("Time slot");
                    progOps.updateBookingDetails(timeSlot, i);
                    i ++;
                }
                JOptionPane.showMessageDialog(null, "Booking Details Updated", "Booking Updated",
                        JOptionPane.INFORMATION_MESSAGE);
                Alley a = new Alley(progOps);
                Date juDate = new Date();
                DateTime dt = new DateTime(juDate);
                Date dateSelected = dt.toDate();
                ms.refreshTabbedPane(dateSelected);
                ms.setIndex(1);
                updateD.dispose();

            }catch (NumberFormatException nf) {
                JOptionPane.showMessageDialog(null, "Wrong data format", "ERROR", JOptionPane.WARNING_MESSAGE);
            }

        }

        else if (e.getSource().equals(clearB)) {
            startTimeTxt.setText("");
            endTimeTxt.setText("");
            noLanes.removeAllItems();
            playerTxt.setText("");
            display.setText("");
            updateB.setVisible(false);
        }
        else if (e.getSource().equals(cancel)) {
            updateD.dispose();
        }
    }


    public void itemStateChanged(ItemEvent e) {
        System.out.println("Inside : itemStateChanged() for Bookings in GuiElements");
        startTimeTxt.setText(HRS24[startHr.getSelectedIndex()] + ":" + startMin.getSelectedItem().toString());
        endTimeTxt.setText(HRS24[endHr.getSelectedIndex()] + ":" + endMin.getSelectedItem().toString());
    }
}