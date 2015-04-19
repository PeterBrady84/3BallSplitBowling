package model;

/**
 * Created by Peter on 19/03/2015.
 */
public class Stock {

    private int id;
    private String shoeSize;
    private int quantity;
    private String details;

    public Stock(int i, String s, int q, String d) {
        //System.out.println("Inside : StockModel");
        this.id = i;
        this.shoeSize = s;
        this.quantity = q;
        this.details = d;
    }

    public Stock(String s, int q, String d) {
        this.shoeSize = s;
        this.quantity = q;
        this.details = d;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(String shoeSize) {
        this.shoeSize = shoeSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}