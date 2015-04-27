package gui;

import db.MainProgramOperations;
import model.*;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Peter on 23/04/2015.
 */
public class QuickPlayGUI implements ItemListener, ActionListener {

    private final JDialog quickD;
    private final MainProgramOperations progOps;
    private MainScreen ms;
    private BookingTab bt;
    private final ArrayList<Booking> bookingList;
    private final ArrayList<Member> memList;
    private final ArrayList<BookingDetails> timeslots;
    private final Staff user;
    private final JComboBox<String> startHr;
    private final JComboBox<String> startMin;
    private final JComboBox<String> endHr;
    private final JComboBox<String> endMin;
    private final JComboBox<Integer> noLanes;
    private final JTextField dateInTxt;
    private final JTextField startTimeTxt;
    private final JTextField endTimeTxt;
    private final JTextField playerTxt;
    private final JTextArea display;
    private final JButton create;
    private final JButton checkB;
    private final JButton clearB;
    private final JButton cancel;
    private final String [] HOURS = {"10 am", "11 am", "12 pm", "1 pm", "2 pm", "3 pm", "4 pm", "5 pm", "6 pm",
            "7 pm", "8 pm", "9 pm", "10 pm", "11 pm"};
    private final int [] HRS24 = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private final int [] LANES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

    public QuickPlayGUI(MainScreen ms, MainProgramOperations po, ArrayList<Booking> b, ArrayList<BookingDetails> bd, ArrayList<Member> m, Staff user) {
        System.out.println("Inside : QuickPlayGUI");
        this.ms = ms;
        this.progOps = po;
        this.bookingList = b;
        this.bt = bt;
        this.memList = m;
        this.user = user;

        timeslots = new ArrayList<>();

        quickD = new JDialog();
        quickD.setTitle("Quick Play");
        quickD.setSize(new Dimension(300, 500));
        quickD.setLocationRelativeTo(null);

        JPanel checkPanel = new JPanel();
        checkPanel.setLayout(new BorderLayout());

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Quick Play");
        checkPanel.setBorder(titled);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(9, 2));
        topPanel.setBackground(Color.white);

        JLabel dateLbl = new JLabel("Date:");
        topPanel.add(dateLbl);
        dateInTxt = new JTextField(15);
        dateInTxt.setText(new java.text.SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));
        dateInTxt.setBackground(Color.WHITE);
        dateInTxt.setEditable(false);
        topPanel.add(dateInTxt);

        JLabel startTime = new JLabel("Start Time:");
        topPanel.add(startTime);

        startHr = new JComboBox<>();
        startHr.setBackground(Color.white);
        topPanel.add(startHr);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        startMin = new JComboBox<>();
        startMin.setBackground(Color.white);
        topPanel.add(startMin);

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

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        endMin = new JComboBox<>();
        endMin.setBackground(Color.white);
        topPanel.add(endMin);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        endTimeTxt = new JTextField(15);
        endTimeTxt.setBackground(Color.WHITE);
        endTimeTxt.setEditable(false);
        topPanel.add(endTimeTxt);

        fillTimes();
        startHr.addItemListener(this);
        startMin.addItemListener(this);
        endHr.addItemListener(this);
        endMin.addItemListener(this);

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
        quickD.add(checkPanel);
        quickD.setVisible(true);
    }

    public void fillTimes() {
        int min = Integer.parseInt(new java.text.SimpleDateFormat("mm").format(new java.util.Date()));
        int hr = Integer.parseInt(new java.text.SimpleDateFormat("HH").format(new java.util.Date()));
        if (min > 44){
            hr++;
        }
        int index = 0;
        for (int i = 0; i < HRS24.length; i++) {
            if (hr == HRS24[i])
                index = i;
        }
        for (int i = index; i < HOURS.length -1; i ++) {
            startHr.addItem(HOURS[i]);
            endHr.addItem(HOURS[i + 1]);
        }
        // Populate the minuteComboBox list
        String[] MINUTES = {"00", "15", "30", "45"};
        for (String MINUTE1 : MINUTES) {
            startMin.addItem(MINUTE1);
            endMin.addItem(MINUTE1);
            Integer.parseInt(MINUTE1.replaceAll("[\\D]", ""));
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in CheckAvailabilityGUI");
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
                        ResultSet rSet = progOps.checkAvailability(d, st, et);
                        try {
                            while (rSet.next()) {
                                available = rSet.getInt(1);
                            }
                            if (available >= (Integer)(noLanes.getSelectedItem())) {
                                display.setText("\u2022 SUCCESS.\n\n" +
                                        "\u2022 Lanes Available: " + available + ".\n" +
                                        "\u2022 Lanes Required: " + noLanes.getSelectedItem() + ".\n" +
                                        "\u2022 Sufficient lanes available!");
                                create.setVisible(true);
                            }
                            else {
                                display.setText("\u2022 UNSUCCESSFUL.\n\n" +
                                        "Insufficient Lanes available!");
                                create.setVisible(false);
                            }
                        } catch (SQLException ex) {
                            System.out.println(e);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null,
                                "No of Players must be numeric", "ERROR", JOptionPane.WARNING_MESSAGE);
                        playerTxt.setText("");
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
            date = new Date();
            try {
                formatter = new SimpleDateFormat("dd-MMM-yy");
                date = formatter.parse(dateInTxt.getText());
            }
            catch (Exception exc) {
                System.out.println(exc);
            }
            int games_hours = progOps.getNumHours(startTimeTxt.getText(), endTimeTxt.getText());
            int bookingId = bookingList.size() + 1;
            Booking b = new Booking(bookingId, 1, user.getId(), lanes, games_hours, 1,
                    players, "Y", "Walk-In");
            for (int i = 0; i < (Integer)noLanes.getSelectedItem(); i ++) {
                int [] slots = progOps.getTimes(startTimeTxt.getText(), endTimeTxt.getText());

                for (int slot : slots) {
                    BookingDetails bd = new BookingDetails(bookingId, freeLanes[i], slot, date);
                    timeslots.add(bd);
                }
                PaymentsGUI p = new PaymentsGUI(ms, b, timeslots, memList.get(0), bt, progOps, 2);
            }
            quickD.dispose();
        }
        else if (e.getSource().equals(clearB)) {
            dateInTxt.setText(new java.text.SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));
            startHr.setSelectedIndex(0);
            startMin.setSelectedIndex(0);
            startTimeTxt.setText("");
            endHr.setSelectedIndex(0);
            endMin.setSelectedIndex(0);
            endTimeTxt.setText("");
            noLanes.removeAllItems();
            playerTxt.setText("");
            display.setText("\u2022 Please input number of players.\n\n" +
                    "\u2022 Then select 'Check'.\n\n" +
                    "\u2022 Calculates no of lanes required.");
            create.setVisible(false);
        }
        else if (e.getSource().equals(cancel)) {
            quickD.dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        System.out.println("Inside : itemStateChanged() in CheckAvailabilityGUI");
        for (int i = 0; i < HOURS.length; i ++) {
            if (HOURS[i] == startHr.getSelectedItem()) {
                startTimeTxt.setText(HRS24[i] + ":" + startMin.getSelectedItem().toString());
            }
            if (HOURS[i] == endHr.getSelectedItem()) {
                endTimeTxt.setText(HRS24[i] + ":" + endMin.getSelectedItem().toString());
            }
        }
    }
}