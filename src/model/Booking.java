package model;

import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Peter on 11/03/2015.
 */
public class Booking {

    public static final double PRICE_HOUR = 10;
    public static final double PRICE_GAME = 7.5;

    private int id;
    private int staffId;
    private int memId;
    private int laneId;
    private String fromDateTime;
    private String toDateTime;
    private int numLanes;
    private double deposit ;
    private double totalPrice ;
    private int hours_games;
    private int numMembers ;
    private int numPlayers ;
    private boolean paid ;
    private String paymentMethod;
    private boolean pricingPerHour;
    private String bookingType;
    private final int  SLOTS_PER_HOUR = 4;
    private ArrayList<String> times;
    private DateTime dt;
    private String bookingDate;
    private static int laneNumber;

//Constructor that fills JTable on booking tab
    public Booking(int i, int m, int l, String s, String e) {
        //System.out.println("Inside : BookingModel");
        this.memId = m;
        this.laneId = l;
        this.fromDateTime = s;
        this.toDateTime = e;
    }

    public Booking(int m, int l, String s, String e) {
        System.out.println("Inside : BookingModel");
        this.memId = m;
        this.laneId = l;
        this.fromDateTime = s;
        this.toDateTime = e;
    }



    public Booking(int id, int staffId, int memId, String fromDateTime,
                   String toDateTime, int numLanes, double deposit,
                   double totalPrice, int hours_games, int numMembers,
                   int numPlayers, boolean paid, String paymentMethod,
                   boolean pricingPerHour, String bookingType) {
        //this code is used to fill an array with times that are used to find the corresponding timeslot
        ArrayList<String> times = new ArrayList<String>();
        String []minutes = {":00",":15",":30",":45"};
        String slot = "";
        final int HOURS_OPEN = 12;
        for(int hour = 12; hour< HOURS_OPEN+12;hour++){
            for (String min : minutes) {
                slot = hour + min;
                times.add(slot);
            }
        }
        String start = dt.toString("yyyy-MM-dd ");
        bookingDate = start + "00:00:00";
        this.id = id;
        this.staffId=staffId;//use user.getid() to assign this value
        this.memId = memId;
        //this.laneId = laneId;
        this.fromDateTime = fromDateTime;//this should be set from the GUIelements startTimeTxt.getText value
        this.toDateTime = toDateTime;
        this.numLanes = numLanes;
        this.deposit = deposit;
        this.totalPrice = totalPrice;
        this.hours_games = hours_games;
        this.numMembers = numMembers;
        this.numPlayers = numPlayers;
        this.paid = paid;
        this.paymentMethod = paymentMethod;
        this.pricingPerHour = pricingPerHour;
        this.bookingType = bookingType;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStaffId() {
        return staffId;
    }

    public int getMemId() {
        return memId;
    }

    public void setMemId(int memId) {
        this.memId = memId;
    }

    public int getLaneId() {
        return laneId;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    public String getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(String fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public String getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(String toDateTime) {
        this.toDateTime = toDateTime;
    }

    public int getNumLanes() {
        return numLanes;
    }

    public double getDeposit() {
        return deposit;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getHours_games() {
        return hours_games;
    }

    public int getNumMembers() {
        return numMembers;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public boolean isPaid() {
        return paid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isPricingPerHour() {
        return pricingPerHour;
    }

    public String getBookingType() {
        return bookingType;
    }

    public DateTime getDt() {
        return dt;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public static int getLaneNumber() {
        return laneNumber;
    }

    public int assignTimeSlot(String selectedDate) {
        int timeslot = 0;
        ArrayList <String> times = getTimes();
        for(String time:times){
            if(fromDateTime.equals(time))
                timeslot=times.indexOf(time)+1;
        }
        return timeslot;
    }

    public ArrayList<String> getTimes() {
        return times;
    }
}