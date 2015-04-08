package model;

/**
 * Created by Peter on 06/03/2015.
 */
public class Staff {

    private int id;
    private String fName;
    private String lName;
    private String phone;
    private int bookings;
    private String username;
    private String password;
    private String email;
    private String secQuestion;
    private String secAnswer;
    private String start;
    private String finish;
    private boolean admin;


    public Staff(int id, String fName, String lName, int bookings, String access, String username) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.bookings = bookings;
        admin = false;
        if(access.equals("Y"))
            admin = true;
        this.username = username;
    }

    //constructor for updating Staff
    public Staff(int id, String fName, String lName, String phone, String email, String login, String password, String secQuestion, String secAnswer) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.email = email;
        this.username = login;
        this.password = password;
        this.secQuestion = secQuestion;
        this.secAnswer = secAnswer;
    }

    // constructor that creates a staff object
    //used when adding new Staff to the system
    public Staff(String f, String l,  String p, String email, String log, String pass, String q, String a) {
        System.out.println("Inside : StaffModel");
        this.fName = f;
        this.lName = l;
        this.phone = p;
        this.email = email;
        this.username = log;
        this.password = pass;
        this.secQuestion = q;
        this.secAnswer = a;
    }
    //Constructor retrieving from DB
    public Staff(int id, String fName, String lName, int bookings, String start, String finish, String phone, String email) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.bookings = bookings;
        this.email = email;
        this.start = start;
        this.finish = finish;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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
