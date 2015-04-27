package gui;

import db.MainProgramOperations;
import model.*;
import org.joda.time.DateTime;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Peter on 21/04/2015.
 */
class PaymentsGUI implements ActionListener {

    private final JDialog payD;
    private final MainProgramOperations progOps;
    private MainScreen ms;
    private final Booking book;
    private Payment pay;
    private BookingTab bt;
    private final ArrayList<BookingDetails> timeSlots;
    private ArrayList<Payment> p;
    private JTextField depositTxt, remainingBalTxt;
    private final JRadioButton cash;
    private final JRadioButton card;
    private JComboBox<String> payType;
    private final JButton confirm;
    private final JButton cancel;
    private int quickPlay;
    private double deposit = 0, amount = 0;
    private String payMethod;

    public PaymentsGUI(MainScreen ms, Booking b, ArrayList<BookingDetails> bd, Member mem, BookingTab bt, MainProgramOperations po, int qp) {
        System.out.println("Inside : PaymentsGUI");
        this.book = b;
        this.ms = ms;
        this.progOps = po;
        BookingDetails detail = bd.get(0);
        this.bt = bt;
        this.timeSlots = bd;
        this.quickPlay = qp;

        if (quickPlay == 1) {
            p = new Alley(progOps).getPaymentsList();
            for (Payment aP : p) {
                if (aP.getBookingId() == book.getId()) {
                    this.pay = aP;
                }
            }
        }
        else  {
            this.pay = new Payment(b);
        }

        payD = new JDialog();
        payD.setTitle("Booking Receipt");
        payD.setSize(new Dimension(300, 440));
        payD.setLocationRelativeTo(null);
        payD.setResizable(false);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BorderLayout());

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Payment   (Page 3 / 3)");
        addPanel.setBorder(titled);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(13, 2));
        topPanel.setBackground(Color.white);

        JLabel detailsLbl = new JLabel("Booking Details");
        topPanel.add(detailsLbl);
        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        JLabel custLbl = new JLabel("Customer:");
        topPanel.add(custLbl);
        JTextField custTxt = new JTextField(15);
        custTxt.setBackground(Color.white);
        custTxt.setEditable(false);
        custTxt.setText(mem.getlName() + ", " + mem.getfName());
        topPanel.add(custTxt);

        JLabel dateLbl = new JLabel("Date Booked:");
        topPanel.add(dateLbl);
        JTextField dateTxt = new JTextField(15);
        dateTxt.setBackground(Color.white);
        dateTxt.setEditable(false);
        dateTxt.setText(new java.text.SimpleDateFormat("dd-MMM-yyyy").format(detail.getBookingDate()));
        topPanel.add(dateTxt);

        JLabel startLbl = new JLabel("Start Time:");
        topPanel.add(startLbl);
        JTextField startTxt = new JTextField(15);
        startTxt.setBackground(Color.white);
        startTxt.setEditable(false);
        startTxt.setText(progOps.getTimeDesc(detail.getTimeSlotId()));
        topPanel.add(startTxt);

        JLabel numberLanesLbl = new JLabel("Number of Lanes:");
        topPanel.add(numberLanesLbl);
        JTextField numberLanesTxt = new JTextField(15);
        numberLanesTxt.setBackground(Color.white);
        numberLanesTxt.setEditable(false);
        numberLanesTxt.setText(String.valueOf(book.getNumLanes()));
        topPanel.add(numberLanesTxt);

        JLabel numberPlayersLbl = new JLabel("Number of Players:");
        topPanel.add(numberPlayersLbl);
        JTextField numberPlayersTxt = new JTextField(15);
        numberPlayersTxt.setBackground(Color.white);
        numberPlayersTxt.setEditable(false);
        numberPlayersTxt.setText(String.valueOf(book.getNumPlayers()));
        topPanel.add(numberPlayersTxt);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        JLabel breakdownLbl = new JLabel("Price Breakdown");
        topPanel.add(breakdownLbl);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        if (quickPlay == 0) {
            JLabel depositLbl = new JLabel("Deposit:   10%");
            topPanel.add(depositLbl);
            depositTxt = new JTextField(15);
            depositTxt.setBackground(Color.white);
            deposit = pay.getTotalPrice() / 10;
            depositTxt.setText("\u20ac " + String.format("%,.2f", deposit));
            topPanel.add(depositTxt);

            JLabel totalPriceLbl = new JLabel("Total Price:");
            topPanel.add(totalPriceLbl);
            JTextField totalPriceTxt = new JTextField(15);
            totalPriceTxt.setBackground(Color.white);
            totalPriceTxt.setEditable(false);
            amount = pay.getTotalPrice();
            totalPriceTxt.setText("\u20ac " + String.format("%,.2f", amount));
            topPanel.add(totalPriceTxt);
        }

        if (quickPlay == 1) {
            JLabel depositLbl = new JLabel("Amount Due (Minus Deposit):");
            topPanel.add(depositLbl);
            depositTxt = new JTextField(15);
            depositTxt.setBackground(Color.white);
            depositTxt.setEditable(false);
            deposit = pay.getTotalPrice() - pay.getDeposit();
            System.out.println(pay.getTotalPrice() + ", " + pay.getDeposit());
            depositTxt.setText("\u20ac " + String.format("%,.2f", deposit));
            topPanel.add(depositTxt);

            JLabel remainingBalLbl = new JLabel("Amount Tendered:");
            topPanel.add(remainingBalLbl);
            remainingBalTxt = new JTextField(15);
            remainingBalTxt.setBackground(Color.white);
            remainingBalTxt.setText("\u20ac ");
            topPanel.add(remainingBalTxt);
        }

        if (quickPlay == 2) {
            JLabel depositLbl = new JLabel("Balance Due:");
            topPanel.add(depositLbl);
            depositTxt = new JTextField(15);
            depositTxt.setBackground(Color.white);
            depositTxt.setEditable(false);
            deposit = pay.getTotalPrice();
            depositTxt.setText("\u20ac " + String.format("%,.2f", deposit));
            topPanel.add(depositTxt);

            JLabel remainingBalLbl = new JLabel("Amount Tendered:");
            topPanel.add(remainingBalLbl);
            remainingBalTxt = new JTextField(15);
            remainingBalTxt.setBackground(Color.white);
            remainingBalTxt.setText("\u20ac ");
            topPanel.add(remainingBalTxt);
        }

        JLabel payMethodLbl = new JLabel("Payment Method:");
        topPanel.add(payMethodLbl);

        cash = new JRadioButton("Cash", true);
        card = new JRadioButton("Card");

        cash.setBackground(Color.white);
        topPanel.add(cash);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        card.setBackground(Color.white);
        topPanel.add(card);

        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(cash);
        paymentGroup.add(card);
        ActionListener payment = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cash.isSelected()) {
                    payType.setEnabled(false);
                    payMethod = "Cash";
                } else if (card.isSelected()) {
                    payType.setEnabled(true);
                    payMethod = payType.getSelectedItem().toString();
                }
            }
        };

        cash.addActionListener(payment);
        card.addActionListener(payment);

        topPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        payType = new JComboBox<>();
        payType.setBackground(Color.white);
        payType.setEnabled(false);
        topPanel.add(payType);
        // Populate the combobox list
        String[] CARDS = {"VISA", "Mastercard", "Laser"};
        for (String CARD : CARDS) {
            payType.addItem(CARD);
        }

        addPanel.add(topPanel, BorderLayout.NORTH);
        addPanel.setBackground(Color.white);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.setBackground(Color.white);

        confirm = new JButton("Confirm Payment");
        confirm.addActionListener(this);
        bottomPanel.add(confirm);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        bottomPanel.add(cancel);

        addPanel.add(bottomPanel, BorderLayout.SOUTH);

        addPanel.setBackground(Color.white);
        payD.add(addPanel);
        payD.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in PaymentsGUI");
        int i = 0;
        if (payMethod == null)
            payMethod = "Cash";
        if(e.getSource().equals(confirm)) {
            if (quickPlay == 0) {
                if (deposit < (amount / 10) ) {
                    JOptionPane.showMessageDialog(null, "Deposit can not be less than 10% of total!\n" +
                            "Please correct deposit amount.", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
                else if (depositTxt.getText() .equals("")) {
                    JOptionPane.showMessageDialog(null, "Deposit field can not be empty.\n" +
                            "Please correct deposit amount.", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    pay.setDeposit(deposit);
                    if (deposit < amount)
                        pay.setFullyPaid("N");
                    else if (deposit == amount)
                        pay.setFullyPaid("Y");
                    progOps.addBooking(book);
                    i = progOps.getBookingLastId();
                    pay.setBookingId(i);
                    for (BookingDetails timeSlot : timeSlots) {
                        progOps.addBookingDetails(i, timeSlot);
                    }
                    pay.setPaymentMethod(payMethod);
                    progOps.addPayment(pay);
                }
            }
            else if ((quickPlay == 1) || (quickPlay == 2)) {
                if (remainingBalTxt.getText().replaceAll("[\\D]", "").equals("")) {
                    JOptionPane.showMessageDialog(null, "Amount Tendered Cannot be Blank!\n" +
                            "Please input correct amount.", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
                //int due = Integer.parseInt(depositTxt.getText().replaceAll("[\\D]", ""));
                try {
                    amount = Integer.parseInt(remainingBalTxt.getText().replaceAll("[\\D]", ""));
                } catch (NumberFormatException nf) {
                    System.out.println(nf);
                }
                if (quickPlay == 1) {
                    if (deposit != amount)
                        JOptionPane.showMessageDialog(null, "Amount Paid does not match that due!\n" +
                                "Please input correct amount.", "ERROR", JOptionPane.WARNING_MESSAGE);
                    else
                        pay.setFullyPaid("Y");
                    progOps.updatePayment(pay.getFullyPaid(), book.getId());
                }
                if (quickPlay == 2) {
                    if (deposit != amount)
                        JOptionPane.showMessageDialog(null, "Amount Paid does not match that due!\n" +
                                "Please input correct amount.", "ERROR", JOptionPane.WARNING_MESSAGE);
                    else
                        pay.setFullyPaid("Y");
                    progOps.addBooking(book);
                    i = progOps.getBookingLastId();
                    pay.setBookingId(i);
                    for (BookingDetails timeSlot : timeSlots) {
                        progOps.addBookingDetails(i, timeSlot);
                    }
                    pay.setPaymentMethod(payMethod);
                    progOps.addPayment(pay);
                }
            }
            Alley a = new Alley(progOps);
            Date juDate = new Date();
            DateTime dt = new DateTime(juDate);
            Date dateSelected = dt.toDate();
            ms.refreshTabbedPane(dateSelected);
            if (quickPlay == 0)
                JOptionPane.showMessageDialog(null, "New Booking Added");
            else if (quickPlay == 1)
                JOptionPane.showMessageDialog(null, "Existing booking balance paid!");
            else if (quickPlay == 2)
                JOptionPane.showMessageDialog(null, "New Quick Play paid!");
            payD.dispose();
        }
        else if (e.getSource() .equals(cancel)) {
            payD.dispose();
        }
    }
}
