package model;

/**
 * Created by Peter on 06/03/2015.
 */
public class Member {

    private int id;
    private String lName;
    private String fName;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private String town;
    private String county;
    private int numVisits;

    public Member(int i, String l, String f, String g, String p,
                  String e, String a, String t, String c, int n) {
        System.out.println("Inside : MemberModel");
        this.id = i;
        this.lName = l;
        this.fName = f;
        this.gender = g;
        this.phone = p;
        this.email = e;
        this.address = a;
        this.town = t;
        this.county = c;
        this.numVisits = n;
    }

    public Member(String l, String f, String g, String p,
                  String e, String a, String t, String c) {
        System.out.println("Inside : MemberModel");
        this.lName = l;
        this.fName = f;
        this.gender = g;
        this.phone = p;
        this.email = e;
        this.address = a;
        this.town = t;
        this.county = c;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public int getNumVisits() {
        return numVisits;
    }

    public void setNumVisits(int numVisits) {
        this.numVisits = numVisits;
    }
}