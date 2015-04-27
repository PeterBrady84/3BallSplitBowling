package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Peter on 10/03/2015.
 */
class GuiElements implements ItemListener {

    public JTextField idTxt;
    public JTextField fNameTxt;
    public JTextField lNameTxt;
    public JTextField phoneTxt;
    public JTextField emailTxt;
    public JTextField addTxt;
    public JTextField townTxt;
    public JTextField visTxt;
    public JTextField loginTxt;
    public JTextField secAnsTxt;
    public JTextField confSecAnsTxt;
    public JTextField sizeTxt;
    public JTextField detailsTxt;
    public JTextField qtyTxt;
    public JTextField staffIdTxt;
    public JTextField nameTxt;
    public JTextField laneTxt;
    public JTextField dateTxt;
    public JTextField startTimeTxt;
    public JTextField endTimeTxt;
    public JFormattedTextField dateInTxt;
    public JPasswordField passwordTxt, confPassTxt;
    public JRadioButton male;
    public JRadioButton female;
    public JComboBox<String> coCombo;
    public JComboBox<String> quest;
    private JComboBox<String> startHr;
    private JComboBox<String> startMin;
    private JComboBox<String> endHr;
    private JComboBox<String> endMin;
    private final int [] HRS24 = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};


    public GuiElements() {
    }

    public JPanel membersGui() {
        System.out.println("Inside : membersGui() in GuiElements");
        final String[] COUNTY = {"Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork", "Derry",
                "Donegal", "Down", "Dublin", "Fermanagh", "Galway", "Kerry", "Kildare", "Kilkenny", "Laois",
                "Leitrim", "Limerick", "Longford", "Louth", "Mayo", "Meath", "Monaghan", "Offaly", "Roscommon",
                "Sligo", "Tipperary", "Tyrone", "Waterford", "Westmeath", "Wexford", "Wicklow"};
        JLabel idLbl, fNameLbl, lNameLbl, genLbl, phoneLbl, emailLbl, addLbl, townLbl, coLbl, visLbl;
        ButtonGroup genderGroup;
        JPanel addPanel, topPanel;

        addPanel = new JPanel();
        addPanel.setLayout(new BorderLayout());

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Member Details");
        addPanel.setBorder(titled);

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(11, 2));
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

        male = new JRadioButton("Male", true);
        male.setBackground(Color.white);
        topPanel.add(male);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        female = new JRadioButton("Female", true);
        female.setBackground(Color.white);
        topPanel.add(female);

        genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

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
        coCombo = new JComboBox<>();
        coCombo.setBackground(Color.white);
        topPanel.add(coCombo);
        // Populate the combobox list
        for (String aCOUNTY : COUNTY) {
            coCombo.addItem(aCOUNTY);
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
        quest = new JComboBox<>(secQs);
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

    @Override
    public void itemStateChanged(ItemEvent ae) {
        System.out.println("Inside : itemStateChanged() for Bookings in GuiElements");
        startTimeTxt.setText(HRS24[startHr.getSelectedIndex()] + ":" + startMin.getSelectedItem().toString());
        endTimeTxt.setText(HRS24[endHr.getSelectedIndex()] + ":" + endMin.getSelectedItem().toString());
    }



}