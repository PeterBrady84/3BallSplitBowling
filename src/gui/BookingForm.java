package gui;

import db.MainProgramOperations;
import model.*;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created by User on 17/04/2015.
 */
public class BookingForm extends JFrame implements ItemListener, ActionListener {

    private JPanel rootPanel;
    private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;
    private JTextField fnameTxt;
    private JTextField lnameTxt;
    private JTextField phoneTxt;
    private JTextField emailTxt;
    private JTextField addres1Txt;
    private JTextField address2Txt;
    private JComboBox startHr;
    private JComboBox startMin;
    private JComboBox endHr;
    private JComboBox endMin;
    private JRadioButton groupRadioButton;
    private JRadioButton partyRadioButton;
    private JComboBox counties;
    private JButton cancelButton;
    private JButton checkAvailabilityButton;
    private JButton calculatePriceButton;
    private JRadioButton perHourRadioButton;
    private JRadioButton perGameRadioButton;
    private JTextField startTimeTxt;
    private JTextField endTimeTxt;
    private JComboBox startMins;
    private JComboBox startHrs;
    private JComboBox finishHrs;
    private JComboBox finishMins;
    private String bookingType;
    private boolean isMember, formComplete;
    private MainProgramOperations progOps;
    private CheckAvailabilityGUI check;
    private JDatePanelImpl mainDatePanel;
    private JDatePickerImpl mainDatePicker;
    private UtilDateModel model;
    private java.util.Date juDate ;
    private DateTime dt;
    private final int [] HRS24 = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private BookingTab bTab;
    private MainScreen ms;
    private ArrayList<Booking> bookingList = new ArrayList<Booking>();
    private ArrayList<BookingDetails> timeslots;
    private int noLanes, noPlayers, memberid, games_hours;
    private String county , perHour;
    private Staff user;
    private Member customer;
    private int userid;

    public BookingForm(final Staff user, MainScreen ms, BookingTab bt, final CheckAvailabilityGUI check, MainProgramOperations po, ArrayList<Booking> b,
                       final int games_hrs, int nl, int np, ArrayList<BookingDetails> times){
        super("Booking Form");
        this.setSize(600,750);
        this.bTab = bt;
        this.ms = ms;
        this.progOps = po;
        this.bookingList = b;
        System.out.println("\n\n            BOOKING LIST SIZE = "+bookingList.size());
        this.noLanes = nl;
        this.noPlayers = np;
        timeslots = times;
        this.games_hours = games_hrs;

        memberid = -1;
        this.user = user;
        userid = user.getId();
        System.out.println("\n\n User id = "+userid);

        setResizable(false);
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        this.check = check;
        this.progOps = po;
        model = new UtilDateModel();

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(partyRadioButton)){
                    bookingType = "Party";
                }
                else
                    bookingType = "Group";
            }
        };
        partyRadioButton.addActionListener(listener);
        groupRadioButton.addActionListener(listener);

        ActionListener listener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(noRadioButton)){
                    isMember = false;
                    fnameTxt.setText("");
                    fnameTxt.setEditable(true);
                    lnameTxt.setText("");
                    lnameTxt.setEditable(true);
                    phoneTxt.setText("");
                    phoneTxt.setEditable(true);
                    emailTxt.setText("");
                    emailTxt.setEditable(true);
                    addres1Txt.setText("");
                    addres1Txt.setEditable(true);
                    address2Txt.setText("");
                    address2Txt.setEditable(true);
                    counties.setSelectedItem(1);
                    counties.setEditable(true);
                }
                else{
                    isMember = true;
                    String id = JOptionPane.showInputDialog("Enter Member id:");
                    customer = progOps.findMemberByID(id);
                    fnameTxt.setText(customer.getfName());
                    fnameTxt.setEditable(false);
                    lnameTxt.setText(customer.getlName());
                    lnameTxt.setEditable(false);
                    phoneTxt.setText(customer.getPhone());
                    phoneTxt.setEditable(false);
                    emailTxt.setText(customer.getEmail());
                    emailTxt.setEditable(false);
                    addres1Txt.setText(customer.getAddress());
                    addres1Txt.setEditable(false);
                    address2Txt.setText(customer.getTown());
                    address2Txt.setEditable(false);
                    counties.setSelectedItem(customer.getCounty());
                    counties.setEditable(false);
                    county = counties.getSelectedItem().toString();
                    memberid = customer.getId();
                    }
            }
        };
        noRadioButton.addActionListener(listener1);
        noRadioButton.setSelected(true);
        noRadioButton.addItemListener(this);
        yesRadioButton.addActionListener(listener1);
        yesRadioButton.addItemListener(this);

        ActionListener listener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(cancelButton)){
                    CheckAvailabilityGUI back = check;
                }
                else if(e.getSource().equals(calculatePriceButton)){
                    if ((fnameTxt.getText().equals("")) && ((phoneTxt.getText().equals("")) ||
                            (emailTxt.getText().equals("")))){
                        JOptionPane.showMessageDialog(null, "Name,phone or email cannot be blank.", "ERROR", JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        if ((!noRadioButton.isSelected() && !yesRadioButton.isSelected()) &&
                                (!groupRadioButton.isSelected() && !partyRadioButton.isSelected()) &&
                                (!perGameRadioButton.isSelected() && !perHourRadioButton.isSelected())) {
                            JOptionPane.showMessageDialog(null, "Unselected items. Check selection buttons!", "ERROR", JOptionPane.WARNING_MESSAGE);
                            System.out.println("Proceed to calculate price");
                        } else {
                            if(!isMember){
                                System.out.println("              NOT        A       MEMBER");
                                customer = new Member(fnameTxt.getText().toString(), lnameTxt.getText().toString(), phoneTxt.getText().toString(),
                                        emailTxt.getText().toString(), addres1Txt.getText().toString(), address2Txt.getText().toString(),
                                        county);
                                progOps.addMember(customer);
                                memberid= progOps.lastMemberid();
                            }
                            int id = bookingList.size() + 1;

                            Booking b = new Booking(id, memberid, userid, noLanes, games_hours, 1,
                                    noPlayers, perHour, bookingType);
                            PaymentGUI receipt = new PaymentGUI(b, timeslots, customer, progOps);
                        }
                    }
                        }
                }
        };
        cancelButton.addActionListener(listener2);
        calculatePriceButton.addActionListener(listener2);
        //checkAvailabilityButton.addActionListener(listener2);

        /*startHrs.addItemListener(this);
        startMins.addItemListener(this);
        finishHrs.addItemListener(this);
        finishMins.addItemListener(this);*/

        perHour = "N";
        ActionListener listener3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(perGameRadioButton)){
                    String games = JOptionPane.showInputDialog("Enter amount of games:");
                    games_hours = Integer.parseInt(games);
                    perHour = "Y";
                }
                else if (e.getSource().equals(perGameRadioButton)){
                    perHour = "N";
                }

            }
        };
        perHourRadioButton.addActionListener(listener3);
        perGameRadioButton.addActionListener(listener3);
    }

    //@Override
    /*public void actionPerformed(ActionEvent e)  {
        *//*System.out.println("Inside : itemStateChanged() for Bookings in GuiElements");
        startTimeTxt.setText(HRS24[startHrs.getSelectedIndex()] + ":" + startMins.getSelectedItem().toString());
        endTimeTxt.setText(HRS24[finishHrs.getSelectedIndex()] + ":" + finishMins.getSelectedItem().toString());*//*
        if(e.getSource().equals(calculatePriceButton)) {
            if ((!fnameTxt.getText().equals("")) && (!lnameTxt.getText().equals("")) && (!phoneTxt.getText().equals("")) &&
                    (!emailTxt.getText().equals("")) && (!addres1Txt.getText().equals("")) && (!county.equals(""))
                    && (!address2Txt.getText().equals("")) && (noRadioButton.isSelected() || yesRadioButton.isSelected()) &&
                    (groupRadioButton.isSelected() || partyRadioButton.isSelected()) &&
                    (perGameRadioButton.isSelected() || perHourRadioButton.isSelected())) {
                Booking b = new Booking(bookingList.size()+1, memberid, LoginGUI.user.getId(), noLanes, games_hours, 1,
                        noPlayers, perHour, bookingType);
                PaymentGUI receipt = new PaymentGUI(b,customer);
            }
            else if(e.getSource().equals(cancelButton)){
                //retrun to checkgui
            }
        }
    }*/

    public static void main(String[] args) {
        MainProgramOperations po = new MainProgramOperations();
        //BookingForm sample = new BookingForm(po);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here

        mainDatePanel = new JDatePanelImpl(model);
        //mainDatePicker = new JDatePickerImpl(mainDatePanel);
        /*juDate = (Date) mainDatePicker.getModel().getValue();
        dt = new DateTime(juDate);*/
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void setUser(Staff user) {
        this.user = user;
    }
}
