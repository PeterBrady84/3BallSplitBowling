package model;

import java.util.Date;

/**
 * Created by Peter on 08/04/2015.
 */
public class BookingDetails {

    private int bookingId;
    private int laneNumber;
    private int timeSlotId;
    private Date bookingDate;

    //Constructor that fills JTable on booking tab
    public BookingDetails(int b, int l, int t, Date d) {
        //System.out.println("Inside : BookingDetailsModel");
        this.bookingId = b;
        this.laneNumber = l;
        this.timeSlotId = t;
        this.bookingDate = d;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int b) {
        this.bookingId = b;
    }

    public int getLaneNumber() {
        return laneNumber;
    }

    public void setLaneNumber(int l) {
        this.laneNumber = l;
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int t) {
        this.timeSlotId = t;
    }

    public java.sql.Date getBookingDate() {
        java.sql.Date sqlDate = new java.sql.Date(bookingDate.getTime());
        return sqlDate;
    }

    public void setBookingDate(Date d) {
        this.bookingDate = d;
    }
}

