package model;

/**
 * Created by Peter on 06/03/2015.
 */
public class Staff {

    private int id;
    private String lName;
    private String fName;
    private int bookings;
    private String phone;
    private String username;
    private char [] password;
    private String email;
    private String secQuestion;
    private String secAnswer;
    private String start;
    private String finish;
    private boolean admin;


    public Staff(int id, String lName, String fName, int bookings, String start, String finish, String phone, String username, String email,
                 char [] password, String secQuestion, String secAnswer, String access) {
        //System.out.println("Inside : StaffModel");
        this.id = id;
        this.lName = lName;
        this.fName = fName;
        this.bookings = bookings;
        this.start = start;
        this.finish = finish;
        this.phone = phone;
        this.username = username;
        this.email = email;
        this.password = password;
        this.secQuestion = secQuestion;
        this.secAnswer = secAnswer;
        admin = access.equals("Y");
    }

    //constructor for updating Staff
    public Staff(int id, String lName, String fName, String phone, String login, String email, char [] password, String secQuestion, String secAnswer) {
        this.id = id;
        this.lName = lName;
        this.fName = fName;
        this.phone = phone;
        this.username = login;
        this.email = email;
        this.password = password;
        this.secQuestion = secQuestion;
        this.secAnswer = secAnswer;
    }

    // constructor that creates a staff object
    //used when adding new Staff to the system
    public Staff(String f, String l,  String p, String email, String log, char [] pass, String q, String a, String access) {
        //System.out.println("Inside : StaffModel");
        this.lName = l;
        this.fName = f;
        this.phone = p;
        this.username = log;
        this.email = email;
        this.password = pass;
        this.secQuestion = q;
        this.secAnswer = a;
        admin = access.equals("Y");
    }
    //Constructor retrieving from DB
    public Staff(int id, String lName, String fName, int bookings, String start, String finish, String phone, String email) {
        this.id = id;
        this.lName = lName;
        this.fName = fName;
        this.bookings = bookings;
        this.start = start;
        this.finish = finish;
        this.phone = phone;
        this.email = email;
    }

    // Create User in Login GUI
    public Staff(int id, String lName, String fName, int bookings, String access, String username) {
        this.id = id;
        this.lName = lName;
        this.fName = fName;
        this.bookings = bookings;
        admin = access.equals("Y");
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogin() {
        return username;
    }

    public void setLogin(String username) {
        this.username = username;
    }

    public char [] getPassword() {
        return password;
    }

    public void setPassword(char [] password) {
        this.password = password;
    }

    public String getSecQuestion() {
        return secQuestion;
    }

    public void setSecQuestion(String secQuestion) {
        this.secQuestion = secQuestion;
    }

    public String getSecAnswer() {
        return secAnswer;
    }

    public void setSecAnswer(String secAnswer) {
        this.secAnswer = secAnswer;
    }

    public int getBookings() {
        return bookings;
    }

    public void increaseBookings() {
        this.bookings++;
    }

    public boolean isAdmin() {
        return admin;
    }
}
