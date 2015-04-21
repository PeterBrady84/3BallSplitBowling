package model;

import db.MainProgramOperations;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Peter on 11/03/2015.
 */
public class Alley {


    private final MainProgramOperations progOps;
    private ResultSet rSet;
    private final ArrayList<Member> memList = new ArrayList<>();
    private final ArrayList<Staff> staffList = new ArrayList<>();
    private final ArrayList<Lane> laneList = new ArrayList<>();
    private final ArrayList<Stock> stockList = new ArrayList<>();
    private final ArrayList<Booking> bookingList = new ArrayList<>();
    private final ArrayList<BookingDetails> bookingDetailsList = new ArrayList<>();
    private final ArrayList<Payment> paymentsList = new ArrayList<>();
    private final ArrayList<TimeSlot> timeSlotList = new ArrayList<>();

    public Alley (MainProgramOperations po) {
        System.out.println("Inside : AlleyModel");

        this.progOps = po;
        rSet = progOps.getMembers();
        try {
            while (rSet.next()) {
                memList.add(new Member(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getString(4),
                        rSet.getString(5), rSet.getString(6), rSet.getString(7), rSet.getString(8), rSet.getString(9),
                        rSet.getInt(10)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        rSet = progOps.getStaff();
        try {
            while (rSet.next()) {
                staffList.add(new Staff(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getInt(4),
                        rSet.getString(5), rSet.getString(6), rSet.getString(7), rSet.getString(8)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        rSet = progOps.getLanes();
        try {
            while (rSet.next()) {
                laneList.add(new Lane(rSet.getInt(1), rSet.getString(2)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        rSet = progOps.getStock();
        try {
            while (rSet.next()) {
                stockList.add(new Stock(rSet.getInt(1), rSet.getString(2), rSet.getInt(3), rSet.getString(4)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        rSet = progOps.getTimeSlots();
        try {
            while (rSet.next()) {
                timeSlotList.add(new TimeSlot(rSet.getInt(1), rSet.getString(2)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        rSet = progOps.getBookings();
        try {
            while (rSet.next()) {
                bookingList.add(new Booking(rSet.getInt(1), rSet.getInt(2), rSet.getInt(3), rSet.getInt(4), rSet.getInt(5), rSet.getInt(6), rSet.getInt(7), rSet.getString(8), rSet.getString(9)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        rSet = progOps.getBookingDetails();
        try {
            while (rSet.next()) {
                bookingDetailsList.add(new BookingDetails(rSet.getInt(1), rSet.getInt(2), rSet.getInt(3), rSet.getDate(4)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        rSet = progOps.getPayments();
        try {
            while (rSet.next()) {
                paymentsList.add(new Payment(rSet.getInt(1), rSet.getInt(2), rSet.getDouble(3), rSet.getDouble(4), rSet.getString(5), rSet.getString(6)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Member> getMemberList()
    {
        System.out.println("Inside : getMemberList() in AlleyModel");
        return memList;
    }

    public ArrayList<Staff> getStaffList()
    {
        System.out.println("Inside : getStaffList() in AlleyModel");
        return staffList;
    }

    public ArrayList<Lane> getLaneList()
    {
        System.out.println("Inside : getLaneList() in AlleyModel");
        return laneList;
    }

    public ArrayList<Stock> getStockList()
    {
        System.out.println("Inside : getStockList() in AlleyModel");
        return stockList;
    }

    public ArrayList<Booking> getBookingList()
    {
        System.out.println("Inside : getBookingList() in AlleyModel");
        return bookingList;
    }

    public ArrayList<TimeSlot> getTimeSlotList()
    {
        System.out.println("Inside : getTimeSlotList() in AlleyModel");
        return timeSlotList;
    }

    public ArrayList<BookingDetails> getBookingDetailsList()
    {
        System.out.println("Inside : getBookingDetailsList() in AlleyModel");
        return bookingDetailsList;
    }

    public ArrayList<Payment> getPaymentsList()
    {
        System.out.println("Inside : getPaymentsList() in AlleyModel");
        return paymentsList;
    }

    public void addMember()
    {
        System.out.println("Inside : addMember() in AlleyModel");
        rSet = progOps.getMemLastRow();
        try {
            memList.add(new Member(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getString(4),
                    rSet.getString(5), rSet.getString(6), rSet.getString(7), rSet.getString(8), rSet.getString(9),
                    rSet.getInt(10)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateMember(Member m) {
        System.out.println("Inside : updateMember() in AlleyModel");
        for (int i = 0; i < memList.size(); i++) {
            if (memList.get(i).getId() == (m.getId()))
                memList.set(i, m);
        }
    }

    public void addStaffLastRow()
    {
        System.out.println("Inside : addStaffLastRow() in AlleyModel");
        rSet = progOps.getStaffLastRow();
        try {
            while (rSet.next()) {
                staffList.add(new Staff(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getInt(4),
                        rSet.getString(5), rSet.getString(6), rSet.getString(7), rSet.getString(8), rSet.getString(9),
                        rSet.getString(10), rSet.getString(11), rSet.getString(12), rSet.getString(13)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateStaff(Staff s) {
        System.out.println("Inside : updateStaff() in AlleyModel");
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getId() == (s.getId()))
                staffList.set(i, s);
        }
    }

    public void addStock()
    {
        System.out.println("Inside : addStock() in AlleyModel");
        rSet = progOps.getStockLastRow();
        try {
            memList.add(new Member(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getString(4),
                    rSet.getString(5), rSet.getString(6), rSet.getString(7), rSet.getString(8), rSet.getString(9),
                    rSet.getInt(10)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addBooking()
    {
        System.out.println("Inside : addBooking() in AlleyModel");
        rSet = progOps.getBookingLastRow();
        try {
            bookingList.add(new Booking(rSet.getInt(1), rSet.getInt(2), rSet.getInt(3), rSet.getInt(4), rSet.getInt(5),
                    rSet.getInt(6), rSet.getInt(7), rSet.getString(8), rSet.getString(9)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
