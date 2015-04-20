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
    private JComboBox comboBox1;
    private JRadioButton cardRadioButton;
    private JPanel rootPanel;
    private JTextField startTxt;
    private Booking book;
    private BookingDetails detail;
    private Member cust;
    private Payment pay;
    private MainProgramOperations progOps;
    private ArrayList timeslots;


    public PaymentGUI(Booking b, final ArrayList<BookingDetails> bd, Member mem, final Payment pay, MainProgramOperations po) {
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
        this.pay = pay;
        progOps = po;
        timeslots = bd;

        System.out.println("Booking passed into payment gui = "+b.getId()+",memid = "+b.getMemId()+", staffid = "+b.getStaffId());
        System.out.println("\tMember passed into payment gui , customerid = "+cust.getId()+",fname = "+cust.getfName()+", lname = "+cust.getEmail());

        customerTxt.setText(cust.getfName()+", "+cust.getlName());
        dateTxt.setText(String.valueOf(detail.getBookingDate()));
        lanesTxt.setText(String.valueOf(book.getNumLanes()));
        startTxt.setText(String.valueOf(detail.getTimeSlotId()));
        playersTxt.setText(String.valueOf(book.getNumPlayers()));
        priceTxt.setText("€ "+String.valueOf(pay.getTotalPrice()));

        final ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double depositAmt = Double.parseDouble(depositTxt.getText());
                if(e.getSource().equals(confirm)){
                    progOps.addBooking(book);
                    System.out.println("\t\t\t\t\t\t                Booking id = "+book.getId());
                    for(int i = 0; i<timeslots.size();i++){
                        progOps.addBookingDetails(bd.get(i));
                        System.out.println("      \t\t\t\t---------     Booking id of detail = " + bd.get(0).getBookingId());
                    }
                    progOps.addPayment(pay);
                }
            }
        };
        confirm.addActionListener(listener);
        back.addActionListener(listener);
    }
}
