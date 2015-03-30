package model;

/**
 * Created by Peter on 11/03/2015.
 */
public class Booking {

    private int id;
    private int memId;
    private int laneId;
    private String fromDateTime;
    private String toDateTime;

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