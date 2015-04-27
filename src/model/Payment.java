package model;


/**
 * Created by Peter on 08/04/2015.
 */
public class Payment {

    private int paymentId;
    private int bookingId;
    private double deposit;
    private double totalPrice;
    private String fullyPaid;
    private String paymentMethod;

    //Constructor that fills JTable on booking tab
    public Payment(int p, int b, double d, double t, String f, String pm) {
        //System.out.println("Inside : PaymentModel");
        this.paymentId = p;
        this.bookingId = b;
        this.deposit = d;
        this.totalPrice = t;
        this.fullyPaid = f;
        this.paymentMethod = pm;
    }

    public Payment(Booking b) {
        paymentId = b.getId();
        bookingId = b.getId();
        if(b.getPricingPerHour().equals("Y"))
            totalPrice = Booking.getPRICE_HOUR() * b.getNumPlayers() * b.getHours_games();
        else
            totalPrice = (b.getNumPlayers() * b.getHours_games()) * Booking.getPRICE_GAME();
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int p) {
        this.paymentId = p;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int b) {
        this.bookingId = b;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double d) {
        this.deposit = d;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double t) {
        this.totalPrice = t;
    }

    public String getFullyPaid() {
        return fullyPaid;
    }

    public void setFullyPaid(String f) {
        this.fullyPaid = f;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String pm) {
        this.paymentMethod = pm;
    }
}
