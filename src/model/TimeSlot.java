package model;

/**
 * Created by Peter on 08/04/2015.
 */
public class TimeSlot {

    private int timeSlotId;
    private String timeDesc;

    public TimeSlot(int i, String d) {
        //System.out.println("Inside : timeSlotModel");
        this.timeSlotId = i;
        this.timeDesc = d;
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int i) {
        this.timeSlotId = i;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String t) {
        this.timeDesc = t;
    }
}
