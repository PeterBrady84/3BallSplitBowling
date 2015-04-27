package gui;

import db.MainProgramOperations;
import model.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Peter on 19/03/2015.
 */
class AddBookingGUI implements ActionListener {
    private final JDialog addD;
    private MainScreen ms;
    private BookingTab bt;
    private CheckAvailabilityGUI check;
    private final MainProgramOperations progOps;
    private final ArrayList<Booking> bookingList;
    private final ArrayList<BookingDetails> timeSlots;
    private final JRadioButton isMem;
    private final JRadioButton notMem;
    private final JRadioButton groupBooking;
    private final JRadioButton partyBooking;
    private final JRadioButton perHour;
    private final JRadioButton perGame;
    private JTextField fNameTxt, lNameTxt, phoneTxt, emailTxt, addTxt, townTxt;
    private JComboBox<String> county;
    private final JButton calcPrice;
    private final JButton cancelB;
    private String bookingType, isPerHour;
    private boolean isMember;
    private final int noLanes;
    private final int noPlayers;
    private int games_hours;
    private int memberId;
    private final int userId;
    private Member customer;

    public AddBookingGUI(MainScreen ms, Staff user, BookingTab bt, MainProgramOperations po,
                         ArrayList<Booking> b, int games_hrs, int nl, int np, ArrayList<BookingDetails> times) {
        System.out.println("Inside : AddBookingGUI");
        this.progOps = po;
        this.ms = ms;
        this.bt = bt;
        this.bookingList = b;
        this.noLanes = nl;
        this.noPlayers = np;
        this.timeSlots = times;
        this.games_hours = games_hrs;

        memberId = -1;
        userId = user.getId();

        addD = new JDialog();
        addD.setTitle("Add Booking");
        addD.setSize(new Dimension(300, 500));
        addD.setLocationRelativeTo(null);
        addD.setResizable(false);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BorderLayout());

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Add Booking   (Page 2 / 3)");
        addPanel.setBorder(titled);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(16, 2));
        topPanel.setBackground(Color.white);

        JLabel isMemLbl = new JLabel("Existing Member:");
        topPanel.add(isMemLbl);

        isMem = new JRadioButton("Yes");
        notMem = new JRadioButton("No", true);

        isMem.setBackground(Color.white);
        topPanel.add(isMem);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        notMem.setBackground(Color.white);
        topPanel.add(notMem);

        ButtonGroup isMemGroup = new ButtonGroup();
        isMemGroup.add(isMem);
        isMemGroup.add(notMem);
        ActionListener member = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (notMem.isSelected()) {
                    isMember = false;
                    fNameTxt.setText("");
                    lNameTxt.setText("");
                    phoneTxt.setText("");
                    emailTxt.setText("");
                    addTxt.setText("");
                    townTxt.setText("");
                    county.setSelectedItem(1);
                } else if (isMem.isSelected()){
                    try {
                        isMember = true;
                        String id = JOptionPane.showInputDialog("Enter Member id:");
                        customer = progOps.findMemberByID(id);
                        fNameTxt.setText(customer.getfName());
                        fNameTxt.setEditable(false);
                        lNameTxt.setText(customer.getlName());
                        lNameTxt.setEditable(false);
                        phoneTxt.setText(customer.getPhone());
                        phoneTxt.setEditable(false);
                        emailTxt.setText(customer.getEmail());
                        emailTxt.setEditable(false);
                        addTxt.setText(customer.getAddress());
                        addTxt.setEditable(false);
                        townTxt.setText(customer.getTown());
                        townTxt.setEditable(false);
                        county.setSelectedItem(customer.getCounty());
                        county.setEnabled(false);
                        memberId = customer.getId();
                    }
                    catch (NullPointerException np){
                        notMem.setSelected(true);
                    }
                }
            }
        };
        notMem.addActionListener(member);
        isMem.addActionListener(member);

        JLabel bookingLbl = new JLabel("Booking Type:");
        topPanel.add(bookingLbl);

        partyBooking = new JRadioButton("Party", true);
        groupBooking = new JRadioButton("Group");

        partyBooking.setBackground(Color.white);
        topPanel.add(partyBooking);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        groupBooking.setBackground(Color.white);
        topPanel.add(groupBooking);

        ButtonGroup bookingGroup = new ButtonGroup();
        bookingGroup.add(partyBooking);
        bookingGroup.add(groupBooking);
        ActionListener booking = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (partyBooking.isSelected()) {
                    bookingType = "Party";
                } else if (groupBooking.isSelected())
                    bookingType = "Group";
            }
        };
        partyBooking.addActionListener(booking);
        groupBooking.addActionListener(booking);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        JLabel custLbl = new JLabel("Customer Details:");
        topPanel.add(custLbl);
        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        JLabel fNameLbl = new JLabel("First Name:");
        topPanel.add(fNameLbl);
        fNameTxt = new JTextField(15);
        fNameTxt.setBackground(Color.white);
        topPanel.add(fNameTxt);

        JLabel lNameLbl = new JLabel("Last Name:");
        topPanel.add(lNameLbl);
        lNameTxt = new JTextField(15);
        lNameTxt.setBackground(Color.white);
        topPanel.add(lNameTxt);

        JLabel phoneLbl = new JLabel("Phone:");
        topPanel.add(phoneLbl);
        phoneTxt = new JTextField(15);
        phoneTxt.setBackground(Color.white);
        topPanel.add(phoneTxt);

        JLabel emailLbl = new JLabel("Email:");
        topPanel.add(emailLbl);
        emailTxt = new JTextField(15);
        emailTxt.setBackground(Color.white);
        topPanel.add(emailTxt);

        JLabel addLbl = new JLabel("Address:");
        topPanel.add(addLbl);
        addTxt = new JTextField(15);
        addTxt.setBackground(Color.white);
        topPanel.add(addTxt);

        JLabel townLbl = new JLabel("Town / City:");
        topPanel.add(townLbl);
        townTxt = new JTextField(15);
        townTxt.setBackground(Color.white);
        topPanel.add(townTxt);

        JLabel countyLbl = new JLabel("County:");
        topPanel.add(countyLbl);
        county = new JComboBox<>();
        county.setBackground(Color.white);
        topPanel.add(county);
        String[] COUNTIES = {"Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork", "Derry",
                "Donegal", "Down", "Dublin", "Fermanagh", "Galway", "Kerry", "Kildare", "Kilkenny", "Laois",
                "Leitrim", "Limerick", "Longford", "Louth", "Mayo", "Meath", "Monaghan", "Offaly", "Roscommon",
                "Sligo", "Tipperary", "Tyrone", "Waterford", "Westmeath", "Wexford", "Wicklow"};
        for (String COUNTY : COUNTIES) {
            county.addItem(COUNTY);
        }

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        JLabel priceLbl = new JLabel("Pricing Type:");
        topPanel.add(priceLbl);

        perHour = new JRadioButton("Per Hour", true);
        perGame = new JRadioButton("Per Game");

        perHour.setBackground(Color.white);
        topPanel.add(perHour);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        perGame.setBackground(Color.white);
        topPanel.add(perGame);

        ButtonGroup pricingGroup = new ButtonGroup();
        pricingGroup.add(perHour);
        pricingGroup.add(perGame);
        isPerHour = "Y";
        ActionListener pricing = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (perGame.isSelected()) {
                    try {
                        String games = JOptionPane.showInputDialog("Enter amount of games:");
                        games_hours = Integer.parseInt(games);
                        isPerHour = "N";
                    }
                    catch (NumberFormatException nf) {
                        perHour.setSelected(true);
                    }
                } else if (perHour.isSelected()) {
                    isPerHour = "Y";
                }
            }
        };
        perGame.addActionListener(pricing);
        perHour.addActionListener(pricing);

        addPanel.add(topPanel, BorderLayout.NORTH);
        addPanel.setBackground(Color.white);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.setBackground(Color.white);

        calcPrice = new JButton("Calculate Price");
        calcPrice.addActionListener(this);
        bottomPanel.add(calcPrice);

        cancelB = new JButton("Cancel");
        cancelB.addActionListener(this);
        bottomPanel.add(cancelB);

        addPanel.add(bottomPanel, BorderLayout.SOUTH);

        addPanel.setBackground(Color.white);
        addD.add(addPanel);
        addD.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in AddBookingGUI");
        EmailValidator emailValidator = new EmailValidator();
        NumberValidator numValidator = new NumberValidator();
        if (e.getSource().equals(calcPrice)) {
            if ((fNameTxt.getText().equals("")) || (lNameTxt.getText().equals("")) ||
                    ((phoneTxt.getText().equals("")) || (emailTxt.getText().equals("")))) {
                JOptionPane.showMessageDialog(null, "Name, phone or email fields cannot be blank.", "ERROR", JOptionPane.WARNING_MESSAGE);
            }else if (emailValidator.validate(emailTxt.getText())) {
                JOptionPane.showMessageDialog(null, "Email address is not valid", "ERROR", JOptionPane.WARNING_MESSAGE);
            } else if (!numValidator.isNumeric(fNameTxt.getText()) && !numValidator.isNumeric(lNameTxt.getText()) && numValidator.isNumeric(phoneTxt.getText())
                    && !numValidator.isNumeric(emailTxt.getText())) {
                if (!isMember) {
                    customer = new Member(fNameTxt.getText(), lNameTxt.getText(), phoneTxt.getText(), emailTxt.getText(),
                            addTxt.getText(), townTxt.getText(), county.getSelectedItem().toString());
                    progOps.addMember(customer);
                    memberId = progOps.lastMemberid();
                }
                int id = bookingList.size() + 1;
                if (bookingType == null)
                    bookingType = "Group";
                Booking b = new Booking(id, memberId, userId, noLanes, games_hours, 1,
                        noPlayers, isPerHour, bookingType);
                PaymentsGUI receipt = new PaymentsGUI(ms, b, timeSlots, customer, bt, progOps, 0);
                addD.dispose();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Only Phone Field may be numeric", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (e.getSource() .equals(cancelB)) {
            addD.dispose();
        }
    }
}