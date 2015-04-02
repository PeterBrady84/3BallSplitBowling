package model;

/**
 * Created by Peter on 11/03/2015.
 */
public class Booking {

    public static final double PRICE_HOUR = 10;
    public static final double PRICE_GAME = 7.5;

    private int id;
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

    public Booking(int id, int memId, String fromDateTime,
                   String toDateTime, int numLanes, double deposit,
                   double totalPrice, int hours_games, int numMembers,
                   int numPlayers, boolean paid, String paymentMethod,
                   boolean pricingPerHour, String bookingType) {
        this.id = id;
        this.memId = memId;
        //this.laneId = laneId;
        this.fromDateTime = fromDateTime;
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
}