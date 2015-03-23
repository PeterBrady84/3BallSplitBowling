package gui;

import db.MainProgramOperations;
import lib.ModifyDateAndTime;
import model.NumberValidator;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

/**
 * Created by Peter on 20/03/2015.
 */
public class CheckAvailabilityGUI implements ActionListener {

    private JDialog addD;
    private MainProgramOperations progOps;
    private ResultSet rSet;
    private UtilDateModel model;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;
    private JLabel dateLbl, timeLbl;
    private JTextField timeTxt;
    private JTextArea display;
    private JPanel addPanel, topPanel, bottomPanel;
    private JButton checkB, clearB;

    public CheckAvailabilityGUI (MainProgramOperations po) {
        System.out.println("Inside : CheckAvailabilityGUI");
        this.progOps = po;

        addD = new JDialog();
        addD.setTitle("Check For Availability");
        addD.setSize(new Dimension(300, 400));
        addD.setLocationRelativeTo(null);

        addPanel = new JPanel();
        addPanel.setLayout(new BorderLayout());

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Check Availability");
        addPanel.setBorder(titled);

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(8, 2));
        topPanel.setBackground(Color.white);

        dateLbl = new JLabel("Enter Date:");
        topPanel.add(dateLbl);

        model = new UtilDateModel();
        datePanel = new JDatePanelImpl(model);
        datePicker = new JDatePickerImpl(datePanel);
        topPanel.add(datePicker);

        //dateTxt = new JTextField(15);
        //topPanel.add(idTxt);

        timeLbl = new JLabel("Enter Time:");
        topPanel.add(timeLbl);
        timeTxt = new JTextField(15);
        topPanel.add(timeTxt);

        display = new JTextArea();
        topPanel.add(display);

        addPanel.add(topPanel, BorderLayout.NORTH);
        addPanel.setBackground(Color.white);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.setBackground(Color.white);

        checkB = new JButton("Check");
        checkB.addActionListener(this);
        bottomPanel.add(checkB);

        clearB = new JButton("Clear");
        clearB.addActionListener(this);
        bottomPanel.add(clearB);

        addPanel.add(bottomPanel, BorderLayout.SOUTH);

        addPanel.setBackground(Color.white);
        addD.add(addPanel);
        addD.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in CheckAvailabilityGUI");
        NumberValidator numValidator = new NumberValidator();
        if (e.getSource().equals(checkB)) {
            try {
                if (datePicker.getModel().getValue().equals("") || timeTxt.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Fields cannot be blank!\n" +
                                    "Please input all details.", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    ModifyDateAndTime mdt = new ModifyDateAndTime();
                    try {
                        String d = mdt.modifyJDatePickerLayout(datePicker.getModel().getValue().toString());
                        String t = timeTxt.getText();
                        if (numValidator.isNumeric(t) == false) {
                            String available = "";
                            rSet = progOps.checkAvailability(d, t);
                            try {
                                while (rSet.next()) {
                                    available += rSet.getString(1);
                                }
                                display.setText(available);
                            } catch (SQLException ex) {
                                System.out.println(e);
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null,
                                    "Time must be numeric", "ERROR", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (ParseException p) {
                        System.out.println(p);
                    }
                }
            }
            catch (NumberFormatException nf) {
                JOptionPane.showMessageDialog(null, "Wrong data format", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (e.getSource().equals(clearB)) {
            timeTxt.setText("");
        }
    }
}
