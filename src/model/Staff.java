package model;

/**
 * Created by Peter on 06/03/2015.
 */
public class Staff {

    private int id;
    private String fName;
    private String lName;
    private String phone;
    private String login;
    private String password;
    private String secQuestion;
    private String secAnswer;

    public Staff(int i, String f, String l, String p,
                  String log, String pass, String q, String a) {
        System.out.println("Inside : StaffModel");
        this.id = i;
        this.fName = f;
        this.lName = l;
        this.phone = p;
        this.login = log;
        this.password = pass;
        this.secQuestion = q;
        this.secAnswer = a;

    }

    public Staff(String f, String l, String p, String log, String pass, String q, String a) {
        System.out.println("Inside : StaffModel");
        this.fName = f;
        this.lName = l;
        this.phone = p;
        this.login = log;
        this.password = pass;
        this.secQuestion = q;
        this.secAnswer = a;
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
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
}
