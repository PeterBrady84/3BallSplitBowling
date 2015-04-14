package model;

/**
 * Created by Peter on 11/03/2015.
 */
public class Booking {

    public static final double PRICE_HOUR = 10;
    public static final double PRICE_GAME = 7.5;

    private int id;
    private int memId;
    private int staffId;
    private int numLanes;
    private int hours_games;
    private int numMembers ;
    private int numPlayers ;
    private String pricingPerHour;
    private String bookingType;

//Constructor that fills JTable on booking tab
    public Booking(int i, int m, int st, int l, int hg, int nm, int np, String ph, String bt) {
        System.out.println("Inside : BookingModel");
        this.id = i;
        this.memId = m;
        this.staffId = st;
        this.numLanes = l;
        this.hours_games = hg;
        this.numMembers = nm;
        this.numPlayers = np;
        this.pricingPerHour = ph;
        this.bookingType = bt;
    }

    public Booking(int m, int st, int l, int hg, int nm, int np, String ph, String bt) {
        System.out.println("Inside : BookingModel");
        this.memId = m;
        this.staffId = st;
        this.numLanes = l;
        this.hours_games = hg;
        this.numMembers = nm;
        this.numPlayers = np;
        this.pricingPerHour = ph;
        this.bookingType = bt;
    }

    public static double getPRICE_HOUR() {
        return PRICE_HOUR;
    }

    public static double getPRICE_GAME() {
        return PRICE_GAME;
    }

    public int getId() {
        return id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getMemId() {
        return memId;
    }

    public void setMemId(int m) {
        this.memId = m;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int s) {
        this.staffId = s;
    }

    public int getNumLanes() {
        return numLanes;
    }

    public void setNumLanes(int nl) {
        this.numLanes = nl;
    }

    public int getHours_games() {
        return hours_games;
    }

    public void setHours_games(int hg) {
        this.hours_games = hg;
    }

    public int getNumMembers() {
        return numMembers;
    }

    public void setNumMembers(int nm) {
        this.numMembers = nm;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int np) {
        this.numPlayers = np;
    }

    public String getPricingPerHour() {
        return pricingPerHour;
    }

    public void setPricingPerHour(String ph) {
        this.pricingPerHour = ph;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bt) {
        this.bookingType = bt;
    }
}