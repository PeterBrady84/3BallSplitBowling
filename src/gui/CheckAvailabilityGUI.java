package gui;

import db.MainProgramOperations;
import controller.ModifyDateAndTime;
import model.NumberValidator;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by Peter on 20/03/2015.
 */
public class CheckAvailabilityGUI implements ActionListener, ItemListener {

    private JDialog addD;
    private MainProgramOperations progOps;
    private ResultSet rSet;
    private UtilDateModel model;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;
    private JFormattedTextField dateInTxt;
    private JLabel dateLbl, startTime, endTime, playerLbl, laneLbl;
    private JComboBox startHr, startMin, endHr, endMin, noLanes;
    private JTextField startTimeTxt, endTimeTxt, playerTxt;
    private JTextArea display;
    private JPanel checkPanel, topPanel, bottomPanel;
    private JButton checkB, clearB, cancel;
    private final int [] HRS24 = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private final String [] HOURS = {"10 am", "11 am", "12 pm", "1 pm", "2 pm", "3 pm", "4 pm", "5 pm", "6 pm",
            "7 pm", "8 pm", "9 pm", "10 pm", "11 pm"};
    private final String [] MINUTES = {"00", "15", "30", "45"};
    private final int [] LANES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

    public CheckAvailabilityGUI (MainProgramOperations po) {
        System.out.println("Inside : CheckAvailabilityGUI");
        this.progOps = po;

        addD = new JDialog();
        addD.setTitle("Check For Availability");
        addD.setSize(new Dimension(300, 500));
        addD.setLocationRelativeTo(null);

        checkPanel = new JPanel();
        checkPanel.setLayout(new BorderLayout());

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Check Availability");
        checkPanel.setBorder(titled);

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(10, 2));
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

        JPanel midPanel = new JPanel();
        midPanel.setBackground(Color.WHITE);
        JScrollPane sp = new JScrollPane();
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(250, 120));
        sp.setBackground(Color.WHITE);
        sp.add(display);
        midPanel.add(sp);

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
                        String available = "";
                        rSet = progOps.checkAvailability(d, st);
                        try {
                            while (rSet.next()) {
                                available += rSet.getString(1) + "\n";
                            }
                            display.setText(available);
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
        else if (e.getSource().equals(clearB)) {
            startTimeTxt.setText("");
            endTimeTxt.setText("");
        }
        else if (e.getSource().equals(cancel)) {
            addD.dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        System.out.println("Inside : itemStateChanged() for Bookings in GuiElements");
        startTimeTxt.setText(HRS24[startHr.getSelectedIndex()] + ":" + startMin.getSelectedItem().toString());
        endTimeTxt.setText(HRS24[endHr.getSelectedIndex()] + ":" + endMin.getSelectedItem().toString());
    }
}
