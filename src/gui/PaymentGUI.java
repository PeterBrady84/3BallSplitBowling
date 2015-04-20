package gui;

import db.MainProgramOperations;
import model.Booking;
import model.BookingDetails;
import model.Member;
import model.Payment;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by User on 19/04/2015.
 */
public class PaymentGUI extends JFrame{
    private JTextField depositTxt;
    private JTextField priceTxt;
    private JTextField customerTxt;
    private JTextField dateTxt;
    private JTextField lanesTxt;
    private JTextField playersTxt;
    private JButton confirm;
    private JButton back;
    private JLabel customer;
    private JLabel reservstionDate;
    private JLabel lanes;
    private JLabel players;
    private JLabel deposit;
    private JLabel price;
    private JRadioButton cashRadioButton;
    private JComboBox cardTypeBox;
    private JRadioButton cardRadioButton;
    private JPanel rootPanel;
    private JTextField startTxt;
    private Booking book;
    private BookingDetails detail;
    private Member cust;
    private Payment pay;
    private MainProgramOperations progOps;
    private ArrayList timeslots;
    private String payMethod;


    public PaymentGUI(Booking b, final ArrayList<BookingDetails> bd, Member mem,  MainProgramOperations po) {
        super("Booking Receipt");
        this.setSize(600,750);
        setResizable(false);
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        book = b;
        cust = mem;
        detail = bd.get(0);
        //this.pay = pay;
        pay = new Payment(b);
        progOps = po;
        timeslots = bd;

        System.out.println("Booking passed into payment gui = "+b.getId()+",memid = "+b.getMemId()+", staffid = "+b.getStaffId());
        System.out.println("\tMember passed into payment gui , customerid = "+cust.getId()+",fname = "+cust.getfName()+", lname = "+cust.getEmail());

        customerTxt.setText(cust.getfName() + ", " + cust.getlName());
        dateTxt.setText(String.valueOf(detail.getBookingDate()));
        lanesTxt.setText(String.valueOf(book.getNumLanes()));
        startTxt.setText(String.valueOf(detail.getTimeSlotId()));
        playersTxt.setText(String.valueOf(book.getNumPlayers()));
        priceTxt.setText("€ "+String.valueOf(pay.getTotalPrice()));

        final ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(confirm)){
                    double deposit = Double.parseDouble(depositTxt.getText().toString());
                    double total = pay.getTotalPrice();
                    pay.setDeposit(deposit);
                    if(deposit<total)
                        pay.setFullyPaid("N");
                    else if((deposit==total))
                        pay.setFullyPaid("Y");
                    progOps.addBooking(book);
                    System.out.println("\t\t\t\t\t\t                Booking id = "+book.getId());
                    for(int i = 0; i<timeslots.size();i++){
                        progOps.addBookingDetails(bd.get(i));
                        System.out.println("      \t\t\t\t---------     Booking id of detail = " + bd.get(0).getBookingId());
                    }
                    pay.setPaymentMethod(payMethod);

                    progOps.addPayment(pay);
                }
            }
        };

        confirm.addActionListener(listener);
        back.addActionListener(listener);


        ActionListener listener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(cashRadioButton)){
                    payMethod = "Cash";
                }
                else{
                    cardTypeBox.setEnabled(true);
                    payMethod = cardTypeBox.getSelectedItem().toString();
                }
            }
        };
        cashRadioButton.addActionListener(listener1);
        cardRadioButton.addActionListener(listener1);
    }


}
