package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

/**
 * Created by Peter on 10/03/2015.
 */
public class GuiElements implements ItemListener {

    public JTextField idTxt, fNameTxt, lNameTxt, genTxt, phoneTxt, emailTxt, addTxt, townTxt, visTxt,
            loginTxt, secAnsTxt, confSecAnsTxt, sizeTxt, detailsTxt, qtyTxt, memIdTxt, nameTxt, laneTxt, dateTxt, startTimeTxt, endTimeTxt;
    public JPasswordField passwordTxt, confPassTxt;
    public JComboBox<String> coCombo, quest, startHr, startMin, endHr, endMin;
    final int [] HRS24 = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};


    public GuiElements() {
    }

    public JPanel membersGui() {
        System.out.println("Inside : membersGui() in GuiElements");
        final String[] COUNTY = {"Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork", "Derry",
                "Donegal", "Down", "Dublin", "Fermanagh", "Galway", "Kerry", "Kildare", "Kilkenny", "Laois",
                "Leitrim", "Limerick", "Longford", "Louth", "Mayo", "Meath", "Monaghan", "Offaly", "Roscommon",
                "Sligo", "Tipperary", "Tyrone", "Waterford", "Westmeath", "Wexford", "Wicklow"};
        JLabel idLbl, fNameLbl, lNameLbl, genLbl, phoneLbl, emailLbl, addLbl, townLbl, coLbl, visLbl;
        JPanel addPanel, topPanel;

        addPanel = new JPanel();
        addPanel.setLayout(new BorderLayout());

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Member Details");
        addPanel.setBorder(titled);

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(10, 2));
        topPanel.setBackground(Color.white);

        idLbl = new JLabel("Member ID No:");
        topPanel.add(idLbl);
        idTxt = new JTextField(15);
        idTxt.setEditable(false);
        idTxt.setBackground(Color.white);
        topPanel.add(idTxt);

        fNameLbl = new JLabel("First Name:");
        topPanel.add(fNameLbl);
        fNameTxt = new JTextField(15);
        topPanel.add(fNameTxt);

        lNameLbl = new JLabel("Surname:");
        topPanel.add(lNameLbl);
        lNameTxt = new JTextField(15);
        topPanel.add(lNameTxt);

        genLbl = new JLabel("Gender:");
        topPanel.add(genLbl);
        genTxt = new JTextField(15);
        topPanel.add(genTxt);

        phoneLbl = new JLabel("Phone No:");
        topPanel.add(phoneLbl);
        phoneTxt = new JTextField(15);
        topPanel.add(phoneTxt);

        emailLbl = new JLabel("Email:");
        topPanel.add(emailLbl);
        emailTxt = new JTextField(15);
        topPanel.add(emailTxt);

        addLbl = new JLabel("Address:");
        topPanel.add(addLbl);
        addTxt = new JTextField(15);
        topPanel.add(addTxt);

        townLbl = new JLabel("Town / City:");
        topPanel.add(townLbl);
        townTxt = new JTextField(15);
        topPanel.add(townTxt);

        coLbl = new JLabel("County:");
        topPanel.add(coLbl);
        coCombo = new JComboBox<String>();
        coCombo.setBackground(Color.white);
        topPanel.add(coCombo);
        // Populate the combobox list
        for (int i = 0; i < COUNTY.length; i++) {
            coCombo.addItem(COUNTY[i]);
        }

        visLbl = new JLabel("No Of Visits:");
        topPanel.add(visLbl);
        visTxt = new JTextField(15);
        visTxt.setEditable(false);
        visTxt.setBackground(Color.white);
        topPanel.add(visTxt);

        addPanel.add(topPanel, BorderLayout.NORTH);
        addPanel.setBackground(Color.white);

        return addPanel;
    }

    public JPanel staffGui() {
        System.out.println("Inside : staffGui() in GuiElements");
        String[] secQs={"Who am i?", "Favourite place?", "First pets name?"};
        JLabel idLbl, fNameLbl, lNameLbl, emailLbl, phoneLbl, loginLbl, passLbl, confLbl, secQuestionLbl, secAnsLbl, confSecAnsLbl;
        JPanel addPanel, topPanel;

        addPanel = new JPanel();
        addPanel.setLayout(new BorderLayout());

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Staff Details");
        addPanel.setBorder(titled);

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(11, 2));
        topPanel.setBackground(Color.white);

        idLbl = new JLabel("Staff ID No:");
        topPanel.add(idLbl);
        idTxt = new JTextField(15);
        idTxt.setEditable(false);
        idTxt.setBackground(Color.white);
        topPanel.add(idTxt);

        fNameLbl = new JLabel("First Name:");
        topPanel.add(fNameLbl);
        fNameTxt = new JTextField(15);
        topPanel.add(fNameTxt);

        lNameLbl = new JLabel("Surname:");
        topPanel.add(lNameLbl);
        lNameTxt = new JTextField(15);
        topPanel.add(lNameTxt);


        phoneLbl = new JLabel("Phone No:");
        topPanel.add(phoneLbl);
        phoneTxt = new JTextField(15);
        topPanel.add(phoneTxt);

        emailLbl = new JLabel("Email:");
        topPanel.add(emailLbl);
        emailTxt = new JTextField(15);
        topPanel.add(emailTxt);

        loginLbl = new JLabel("Login:");
        topPanel.add(loginLbl);
        loginTxt = new JTextField(15);
        /*loginTxt.setEditable(false);
        loginTxt.setBackground(Color.white);*/
        topPanel.add(loginTxt);

        passLbl = new JLabel("Password:");
        topPanel.add(passLbl);
        passwordTxt = new JPasswordField(15);
        topPanel.add(passwordTxt);

        confLbl = new JLabel("Confirm Password:");
        topPanel.add(confLbl);
        confPassTxt = new JPasswordField(15);
        topPanel.add(confPassTxt);

        secQuestionLbl = new JLabel("Security Question:");
        topPanel.add(secQuestionLbl);
        quest = new JComboBox<String>(secQs);
        quest.setBackground(Color.white);
        topPanel.add(quest);

        secAnsLbl = new JLabel("Security Answer:");
        topPanel.add(secAnsLbl);
        secAnsTxt = new JTextField(15);
        topPanel.add(secAnsTxt);

        confSecAnsLbl = new JLabel("Confirm Answer:");
        topPanel.add(confSecAnsLbl);
        confSecAnsTxt = new JTextField(15);
        topPanel.add(confSecAnsTxt);

        addPanel.add(topPanel, BorderLayout.NORTH);
        addPanel.setBackground(Color.white);

        return addPanel;
    }

    public JPanel stockGui() {
        System.out.println("Inside : stockGui() in GuiElements");
        JLabel idLbl, sizeLbl, detailsLbl, qtyLbl;
        JPanel addPanel, topPanel;

        addPanel = new JPanel();
        addPanel.setLayout(new BorderLayout());

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Stock Details");
        addPanel.setBorder(titled);

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 2));
        topPanel.setBackground(Color.white);

        idLbl = new JLabel("Stock ID No:");
        topPanel.add(idLbl);
        idTxt = new JTextField(15);
        idTxt.setEditable(false);
        idTxt.setBackground(Color.white);
        topPanel.add(idTxt);

        sizeLbl = new JLabel("Shoe Size:");
        topPanel.add(sizeLbl);
        sizeTxt = new JTextField(15);
        topPanel.add(sizeTxt);

        detailsLbl = new JLabel("Details:");
        topPanel.add(detailsLbl);
        detailsTxt = new JTextField(15);
        topPanel.add(detailsTxt);

        qtyLbl = new JLabel("Quantity:");
        topPanel.add(qtyLbl);
        qtyTxt = new JTextField(15);
        topPanel.add(qtyTxt);

        addPanel.add(topPanel, BorderLayout.NORTH);
        addPanel.setBackground(Color.white);

        return addPanel;
    }

    public JPanel bookingGui() {
        System.out.println("Inside : bookingGui() in GuiElements");
        JLabel idLbl, memIdLbl, nameLbl, laneLbl, dateLbl, startTime, endTime;
        final String [] HOURS = {"10 am", "11 am", "12 pm", "1 pm", "2 pm", "3 pm", "4 pm", "5 pm", "6 pm", "7 pm", "8 pm", "9 pm", "10 pm", "11 pm"};
        final String [] MINUTES = {"00", "15", "30", "45"};
        JPanel addPanel, topPanel;

        addPanel = new JPanel();
        addPanel.setLayout(new BorderLayout());

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Booking Details");
        addPanel.setBorder(titled);

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(11, 2));
        topPanel.setBackground(Color.white);

        idLbl = new JLabel("Booking ID No:");
        topPanel.add(idLbl);
        idTxt = new JTextField(15);
        idTxt.setEditable(false);
        idTxt.setBackground(Color.white);
        topPanel.add(idTxt);

        memIdLbl = new JLabel("Member Id");
        topPanel.add(memIdLbl);
        memIdTxt = new JTextField(15);
        topPanel.add(memIdTxt);

        nameLbl = new JLabel("Name:");
        topPanel.add(nameLbl);
        nameTxt = new JTextField(15);
        topPanel.add(nameTxt);

        laneLbl = new JLabel("Lanes:");
        topPanel.add(laneLbl);
        laneTxt = new JTextField(15);
        topPanel.add(laneTxt);

        dateLbl = new JLabel("Dates:");
        topPanel.add(dateLbl);
        dateTxt = new JTextField(new java.text.SimpleDateFormat("dd-MMM-yy").format(new java.util.Date()), 15);
        topPanel.add(dateTxt);

        startTime = new JLabel("Start Time:");
        topPanel.add(startTime);

        startHr = new JComboBox<String>();
        startHr.setBackground(Color.white);
        topPanel.add(startHr);
        // Populate the hourComboBox list
        for (int i = 0; i < HOURS.length; i++) {
            startHr.addItem(HOURS[i]);
        }
        startHr.addItemListener(this);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        
        startMin = new JComboBox<String>();
        startMin.setSize(25, startMin.getPreferredSize().height);
        startMin.setBackground(Color.white);
        topPanel.add(startMin);
        // Populate the hourComboBox list
        for (int i = 0; i < MINUTES.length; i++) {
            startMin.addItem(MINUTES[i]);
        }
        startMin.addItemListener(this);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        startTimeTxt = new JTextField(new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date()), 15);
        startTimeTxt.setBackground(Color.WHITE);
        startTimeTxt.setEditable(false);
        topPanel.add(startTimeTxt);

        endTime = new JLabel("End Time:");
        topPanel.add(endTime);

        endHr = new JComboBox<String>();
        endHr.setBackground(Color.white);
        topPanel.add(endHr);
        // Populate the hourComboBox list
        for (int i = 0; i < HOURS.length; i++) {
            endHr.addItem(HOURS[i]);
        }
        endHr.addItemListener(this);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        endMin = new JComboBox<String>();
        endMin.setBackground(Color.white);
        topPanel.add(endMin);
        // Populate the hourComboBox list
        for (int i = 0; i < MINUTES.length; i++) {
            endMin.addItem(MINUTES[i]);
        }
        endMin.addItemListener(this);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        endTimeTxt = new JTextField(new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date()), 15);
        topPanel.add(endTimeTxt);

        addPanel.add(topPanel, BorderLayout.NORTH);
        addPanel.setBackground(Color.white);

        return addPanel;
    }

    @Override
    public void itemStateChanged(ItemEvent ae) {
        System.out.println("Inside : itemStateChanged() for Bookings in GuiElements");
        startTimeTxt.setText(HRS24[startHr.getSelectedIndex()] + ":" + startMin.getSelectedItem().toString());

        endTimeTxt.setText(HRS24[endHr.getSelectedIndex()] + ":" + endMin.getSelectedItem().toString());
    }
}