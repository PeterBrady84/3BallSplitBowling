package db;

import gui.MainScreen;
import model.*;
import oracle.jdbc.pool.OracleDataSource;
import org.joda.time.DateTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Peter on 06/03/2015.
 */
public class MainProgramOperations {

    private PreparedStatement pStmt;
    private ResultSet rSet;
    private Connection conn;
    private java.util.Date juDate ;
    private DateTime dt;
    private String dateSelected;
    public static Staff user;
    //public DateAndCalendar;

    public MainProgramOperations() {
        conn = openDB();
    }


    public Connection openDB() {
        Scanner in = new Scanner(System.in);
        try {
            // Load the Oracle JDBC driver
            OracleDataSource ods = new OracleDataSource();

            System.out.println("Type Initials:\n(Lowercase. Eg: xy):");
            String val = in.nextLine();
            String user = "", pass = "";

            // Peter Brady Login
            if (val .equals("pb")) {
                user = "Peter";
                pass = "database";
            }
            // Luke Byrne Login
            else if (val .equals("lb")) {
                user = "system";
                pass = "passhr";
            }
            // Peter Connel Login
            else if (val .equals("pc")) {
                user = "hr";
                pass = "passhr";
            }
            // Dylan Byrne login
            else if (val .equals("db")) {
                user = "Dylan Byrne's Username";
                pass = "Dylan Byrne's Password";
            }

            ods.setURL("jdbc:oracle:thin:hr/hr@localhost:1521/XE");
            ods.setUser(user);
            ods.setPassword(pass);
            conn = ods.getConnection();
            System.out.println("connected.");
        } catch (Exception e) {
            System.out.print("Unable to load driver " + e);
            System.exit(1);
        }
        return conn;
    }

    public void closeDB() {
        try {
            conn.close();
            System.out.print("Connection closed");
        } catch (SQLException e) {
            System.out.print("Could not close connection ");
            e.printStackTrace();
        }
    }




    ///// Beginning of Member Queries ///////////////////////////////////
    public ResultSet getMembers() {
        System.out.println("Inside : getMembers() in MainProgramOperations");
        try {
            String queryString = "SELECT * FROM Members ORDER BY memId";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rSet;
    }

    public int getNumMems() {
        System.out.println("Inside : getNumMembers() in MainProgramOperations");
        int num = 0;
        try {
            String queryString = "SELECT count(*) FROM Members";

            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            if (rSet.next()) {
                num = rSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    public ResultSet getMemLastRow() {
        System.out.println("Inside : getMemberLastRow() in MainProgramOperations");
        String sqlStatement = "SELECT * FROM Members ORDER BY memId";
        try {
            pStmt = conn.prepareStatement(sqlStatement,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rSet = pStmt.executeQuery();
            rSet.last();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }

    public void addMember(Member m) {
        System.out.println("Inside : addMember() in MainProgramOperations");
        try {
            String addsql = "INSERT INTO members(memId, lName, fName, phone, email," +
                    "address, town, county, numVisits) values(memId_seq.nextVal, ?, ?, ?, ?, ?, ?, ?, 0)";
            pStmt = conn.prepareStatement(addsql);
            pStmt.setString(1, m.getlName());
            pStmt.setString(2, m.getfName());
            pStmt.setString(3, m.getPhone());
            pStmt.setString(4, m.getEmail());
            pStmt.setString(5, m.getAddress());
            pStmt.setString(6, m.getTown());
            pStmt.setString(7, m.getCounty());
            pStmt.executeUpdate();

            System.out.println("Member added to DB");
        } catch (Exception se) {
            System.out.println(se);
        }
    }

    public void updateMember(String i, String n, String l, String g, String p, String e, String a, String t, String c) {
        System.out.println("Inside : updateMember() in MainProgramOperations");
        try {
            String update = "UPDATE members SET fName = '" + n + "', lName = '" + l + "', gender = '" + g + "', phone = '"
                    + p + "', email = '" + e + "', address = '" + a + "', town = '" + t + "', county = '" + c + "'" +
                    "WHERE memId = " + i;
            pStmt = conn.prepareStatement(update);
            pStmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public ResultSet searchMembers(String s) {
        System.out.println("Inside : searchMembers() in MainProgramOperations");
        String sqlStatement = "SELECT * FROM Members WHERE " + s;
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();
            ;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }

    /////////////////For Reports///////////////////////////////////////////////////////
    public ResultSet getMember() {
        System.out.println("Inside : getMemberNumVisits() in MainProgramOperations");
        String sqlStatement = "SELECT fname,lname,numVisits,gender FROM Members ORDER BY numVisits";
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }


    public ResultSet getMemberGender() {
        System.out.println("Inside : getMemberGender() in MainProgramOperations");
        String sqlStatement = "select gender,count(*), sum(case when gender = 'M' then 1 else 0 end) MaleCount, sum(case when gender = 'F' then 1 else 0 end) FemaleCount from members group by gender";
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }

    public ResultSet getStaffBookings() {
        System.out.println("Inside : getStaffBookings() in MainProgramOperations");
        String sqlStatement = "select staffId,lname,bookings from staff order by BOOKINGS desc";
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }

    public ResultSet getStaffMembers() {
        System.out.println("Inside : getStaffBookings() in MainProgramOperations");
        String sqlStatement = "select staffId, lname,bookings from staff order by BOOKINGS desc";
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }





    ///// End of Member Queries ///////////////////////////////////


    ///// Beginning of Staff Queries ///////////////////////////////////

    //used to populate the staffList which can be then used to fillTable
    public ResultSet getStaff() {
        System.out.println("Inside : getStaff() in MainProgramOperations");

        dateSelected = MainScreen.calendarSelected;

        if(MainScreen.calendarSelected == null){
            juDate = new java.util.Date();
            dt = new DateTime(juDate);
            dateSelected = dt.toString("dd-MMM-yy");
            dateSelected = dateSelected.toUpperCase();
            System.out.println("calendarSelected == null so the date is set as *********************************");
        }
        //dateSelected = MainScreen.calendarSelected;

        System.out.println(dateSelected);
        try {
            String queryString = "select s.staffId, fname, lname, bookings ,TO_CHAR(starttime, 'HH24:MI') AS STARTTIME"+
                    ", TO_CHAR(finishtime, 'HH24:MI') AS FINISHTIME, " +
                    "phone, email From staff s ,roster r where s.staffId = r.staffId " +
                    "AND starttime LIKE '%"+dateSelected+"%'";

            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
        } catch (Exception e) {
            System.out.println("getStaff method used to populate the staffList......"+e);
        }
        return rSet;
    }

    public int getNumStaff() {
        System.out.println("Inside : getNumStaff() in MainProgramOperations");
        int num = 0;
        try {
            String queryString = "SELECT count(*) FROM Staff";

            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            if (rSet.next()) {
                num = rSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    public ResultSet getStaffLastRow() {
        System.out.println("Inside : getStaffLastRow() in MainProgramOperations");
        String sqlStatement = "SELECT * FROM Staff ORDER BY staffId";
        try {
            pStmt = conn.prepareStatement(sqlStatement,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rSet = pStmt.executeQuery();
            rSet.last();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }

    public void addStaff(Staff s) {
        System.out.println("Inside : addStaff() in MainProgramOperations");
        try {
            String addsql = "INSERT INTO staff(staffId, lname, fname, bookings, phone, email, username," +
                    "password, securityQuestion, securityAnswer) values(staffId_seq.nextVal, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pStmt = conn.prepareStatement(addsql);
            pStmt.setString(1, s.getlName());
            pStmt.setString(2, s.getfName());
            pStmt.setInt(3, 0);// when adding a new staff member they have ZERO bookings
            pStmt.setString(4, s.getPhone());
            pStmt.setString(5, s.getEmail());
            pStmt.setString(6, s.getLogin());
            pStmt.setString(7, s.getPassword());
            pStmt.setString(8, s.getSecQuestion());
            pStmt.setString(9, s.getSecAnswer());
            pStmt.executeUpdate();

            System.out.println("Staff added to DB");
            //after a staff member is added to the database they are assigned a roster for the week
            try{
                rSet = getStaffLastRow();
                int id  = rSet.getInt("staffid");
                Timestamp time;
                final int ONE_WEEK = 7;
                juDate = new java.util.Date();
                dt = new DateTime(juDate);
                //System.out.println("ID IS HERE ============================================" + id);
                String idIn = Integer.toString(id);
                //System.out.println("int id = "+id+"======================= Srtring idIN = "+idIn);

                for (int i = 0; i < ONE_WEEK; i++) {
                    String insertString = "INSERT INTO roster(staffId, startTime, finishTime) values(? ,?, ?)";
                    pStmt = conn.prepareStatement(insertString);
                    //System.out.println("INSIDE WEEK LOOP *********************************************  id = "+id);
                    pStmt.setInt(1,id);
                    String now = "";
                    String b = dt.toString("yyyy-MM-dd ");
                    now = "11:00:00";
                    b = b + now;
                    System.out.print((i + 1) + ". " + b);
                    time = Timestamp.valueOf(b);
                    pStmt.setTimestamp(2, time);
                    //This is setting the finish time
                    b = dt.toString("yyyy-MM-dd ");
                    now = "16:00:00";
                    b = b + now;
                    time = Timestamp.valueOf(b);
                    System.out.println("\t        " + b);
                    pStmt.setTimestamp(3, time);
                    pStmt.executeQuery();
                    dt = dt.plusDays(1);
                }

            }
            catch (Exception e) {
                System.out.println("ROSTER NOT FILLING =======================================  " + e);
            }
        } catch (Exception se) {
            System.out.println("Not adding Staff--------------------------------------------" + se);
        }
    }


    public void updateStaffinDB(String i, String n, String l, String e, String p, String log, String pass, String q, String a) {
        System.out.println("Inside : updateStaffinDB() in MainProgramOperations");
        System.out.println("Attempting update ---------------------------------------------------------------------------");
        try {
            String update = "UPDATE staff SET fName = '" + n + "', lName = '" + l + "', email = '" + e + "', phone = '" + p
                    + "', username = '" + log + "', password = '" + pass + "', securityQuestion = '" + q + "', securityAnswer = '" + a + "' WHERE staffId = " + i;
            pStmt = conn.prepareStatement(update);
            pStmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("STAFF NOT UPDATED====================================            "+ex);
        }
    }

    public ResultSet searchStaff(String s) {
        System.out.println("Inside : searchStaff() in MainProgramOperations");
        String sqlStatement = "SELECT * FROM Staff WHERE " + s;
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }
    public Staff createUser(String s) {
        System.out.println("Inside : createStaff() in MainProgramOperations");
        String sqlStatement = "SELECT staffid, fname, lname, bookings,admin, username  FROM Staff WHERE username = '" + s + "'";
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();
            if (rSet != null && rSet.next()) {
                Staff user = new Staff(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getInt(4), rSet.getString(5), rSet.getString(6));
                System.out.printf("USER returned = %d, %s,%s,%d,%s,%s\n",rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getInt(4), rSet.getString(5), rSet.getString(6));
                return user;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return null;
    }

    /*public ArrayList<String> staffLogin() {
        System.out.println("Inside : staffLogin() in MainProgramOperations");
        ArrayList<String> usernames = new ArrayList<String>();
        String queryString = "SELECT username FROM staff order by staffId";
        try {
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            while(rSet.next()) {
                usernames.add(rSet.getString(1));

                System.out.println("LIST OF log in resultset------------\n name:" +rSet.getString(1)
                +"\t Password = "+rSet.getString(2));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return usernames;
    }*/


    public boolean checkPass(String username, String password) {
        boolean passwordsMatch = false;
        System.out.println("Inside : checkPass() in MainProgramOperations");

        String getPassword = "SELECT password FROM staff where username = '"+username+"'";
        try {
            pStmt = conn.prepareStatement(getPassword);
            rSet = pStmt.executeQuery();
            rSet.next();
            if(password.equals(rSet.getString(1))) {
                passwordsMatch = true;
                System.out.println("       inside SELECT password FROM staff in checkPass method. Result set = "+rSet.getString(1));
                return passwordsMatch;
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return passwordsMatch;
    }

    public ArrayList<String> queryLogin() {
        System.out.println("Inside : queryLogin() in MainProgramOperations");
        ArrayList<String> login = new ArrayList<String>();
        try {
            String queryString = "SELECT username FROM staff";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                login.add(rSet.getString(1));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return login;
    }

    public String getSecQuestion(String a) {
        System.out.println("Inside : getSecQuestion() in MainProgramOperations");
        String question = "";
        try {
            String queryString = "SELECT securityQuestion FROM staff"
                    + " WHERE username = '" + a + "'";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            while(rSet.next()){
                question = rSet.getString(1);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return question;
    }

    public String changePassword(String pw, String username) {
        System.out.println("Inside : changePassword() in MainProgramOperations");
        String ans = "";
        try {
            String queryString = "UPDATE staff SET password = '" + pw + "' WHERE username = '" + username + "'";
            pStmt = conn.prepareStatement(queryString);
            pStmt.executeUpdate();
            String query2 = "SELECT securityAnswer FROM staff"
                    + " WHERE username = '" + username + "'";
            pStmt = conn.prepareStatement(query2);
            rSet = pStmt.executeQuery();
            while(rSet.next()){
                ans = rSet.getString(1);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return ans;
    }

    public void deleteStaff(String user, int id){
        System.out.println("Inside : deleteStaff() in MainProgramOperations");
        String ans = "";
        try {
            String queryString = "DELETE FROM staff WHERE username = '"+user+"' OR staffid = "+id;
            pStmt = conn.prepareStatement(queryString);
            pStmt.executeUpdate();

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    ///// End of Staff Queries ///////////////////////////////////

    ///// Beginning of Lane Queries ///////////////////////////////////
    public ResultSet getLanes() {
        System.out.println("Inside : getLanes() in MainProgramOperations");
        try {
            String queryString = "SELECT * FROM lane ORDER BY lanenumber";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rSet;
    }

    public int getNumLanes() {
        System.out.println("Inside : getNumLaness() in MainProgramOperations");
        int num = 0;
        try {
            String queryString = "SELECT count(*) FROM Lane";

            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            if (rSet.next()) {
                num = rSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }
    ///// End of Lane Queries ///////////////////////////////////

    ///// Beginning of Stock Queries ///////////////////////////////////
    public ResultSet getStock() {
        System.out.println("Inside : getStock() in MainProgramOperations");
        try {
            String queryString = "SELECT * FROM Stock ORDER BY stockId";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rSet;
    }

    public int getNumStock() {
        int num = 0;
        try {
            String queryString = "SELECT count(*) FROM Stock";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            if (rSet.next()) {
                num = rSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    public ResultSet getStockLastRow() {
        System.out.println("Inside : getStockLastRow() in MainProgramOperations");
        String sqlStatement = "SELECT * FROM Stock ORDER BY stockId";
        try {
            pStmt = conn.prepareStatement(sqlStatement,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rSet = pStmt.executeQuery();
            rSet.last();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }

    public void addStock(Stock s) {
        try {
            String addsql = "INSERT INTO Stock(stockId, shoeSize, quantity, details) values(stockId_seq.nextVal, ?, ?, ?)";
            pStmt = conn.prepareStatement(addsql);
            pStmt.setString(1, s.getShoeSize());
            pStmt.setInt(2, s.getQuantity());
            pStmt.setString(3, s.getDetails());
            pStmt.executeUpdate();

            System.out.println("Stock added to DB");
        } catch (Exception se) {
            System.out.println(se);
        }
    }

    public void updateStock(String i, String s, String d, int q) {
        System.out.println("Inside : updateStock() in MainProgramOperations");
        try {
            String update = "UPDATE stock SET shoeSize = '" + s + "', quantity = " + q + ", details = '"
                    + d + "' WHERE stockId = " + i;
            pStmt = conn.prepareStatement(update);
            pStmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public ResultSet searchStock(String s) {
        System.out.println("Inside : searchStock() in MainProgramOperations");
        String sqlStatement = "SELECT * FROM Stock WHERE " + s;
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();
            ;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }

    ///// Beginning of Booking Queries ///////////////////////////////////
    public ResultSet getBookings() {
        System.out.println("Inside : getBookings() in MainProgramOperations");
        try {
            String queryString = "SELECT * FROM bookings ORDER BY bookingId";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rSet;
    }

    public int getNumBookings() {
        System.out.println("Inside : getNumBookings() in MainProgramOperations");
        int num = 0;
        try {
            String queryString = "SELECT count(*) FROM Bookings";

            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            if (rSet.next()) {
                num = rSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    public ResultSet getBookingLastRow() {
        System.out.println("Inside : getBookingLastRow() in MainProgramOperations");
        String sqlStatement = "SELECT * FROM Bookings ORDER BY bookingId";
        try {
            pStmt = conn.prepareStatement(sqlStatement,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rSet = pStmt.executeQuery();
            rSet.last();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }

    public void addBooking(Booking b) {
        try {
            String insertString = "insert into bookings(\n" +
                    "bookingId , \n" +
                    "memId ,\n" +
                    "staffId ,\n" +
                    "numPlayers ,\n" +
                    "numlanes,\n" +
                    "games_hours ,\n" +
                    "fromDateTime , \n" +
                    "toDateTime ,\n" +
                    "numMembers ,\n" +
                    "paymentMethod,\n" +
                    "pricingPerHour ,\n" +
                    "fullypaid ,\n" +
                    "bookingType ,\n" +
                    "totalprice ,\n" +
                    "deposit) VALUES (bookingId_seq.nextVal, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pStmt = conn.prepareStatement(insertString);

            pStmt.setInt(1,b.getId());
            pStmt.setInt(2,b.getStaffId());
            pStmt.setInt(3,b.getNumPlayers());
            pStmt.setInt(4,b.getNumLanes());
            pStmt.setInt(5,b.getHours_games());
            pStmt.setTimestamp(6, Timestamp.valueOf(b.getFromDateTime()));
            pStmt.setTimestamp(7, Timestamp.valueOf(b.getToDateTime()));
            pStmt.setString(8, b.getPaymentMethod());
            if (b.isPricingPerHour())
                pStmt.setString(9,"Y");
            else
                pStmt.setString(9,"N");
            pStmt.setDouble(15,b.getTotalPrice());
            pStmt.setDouble(14,b.getDeposit());
            pStmt.executeQuery();

            String insertLanes = "INSERT INTO lane(lanenumber, bookingid, today, lanename, inuse, timeslot)values(?,?,?,?,?,?)";
            int laneNumber = new Random().nextInt(16)+1;
            PreparedStatement pStmt2 = conn.prepareStatement(insertLanes);
            for(int lanes = 0; lanes<b.getNumLanes(); lanes++) {
                int timeslot = b.assignTimeSlot(b.getFromDateTime());
                for(int index = 0;index<b.getHours_games()*4;index++){
                    String name = "lane " + laneNumber;
                    pStmt2.setInt(1, laneNumber);
                    pStmt2.setInt(2, b.getId());
                    System.out.print("\t"+name);
                    pStmt2.setTimestamp(3, Timestamp.valueOf(b.getBookingDate()));
                    pStmt2.setString(4, name);
                    pStmt2.setString(5, "N");
                    pStmt2.setInt(6, timeslot);
                    timeslot++;
                    if (laneNumber > 16)
                        laneNumber = 1;

                    pStmt2.executeQuery();

                    System.out.println("\tLane number: " + laneNumber + "\t timeslot = " + timeslot);
                }
                laneNumber++;
            }

        } catch (Exception e) {
            System.out.println("Problem adding booking + "+e);
        }
    }

   /* public void addBooking(Booking b) {
        System.out.println("Inside : addBooking() in MainProgramOperations");
        System.out.println(b.getFromDateTime());
        String start = b.getFromDateTime() + ":00";
        String end = b.getToDateTime() + ":00";
        try {
            String addsql = "INSERT INTO bookings (bookingId, memId, laneId, fromDateTime, toDateTime)" +
                    "VALUES (bookingId_seq.nextVal, ?, ?, ?, ?)";
            pStmt = conn.prepareStatement(addsql);
            pStmt.setInt(1, b.getMemId());
            pStmt.setInt(2, b.getLaneId());
            pStmt.setString(3, start);
            pStmt.setString(4, end);
            pStmt.executeUpdate();

            System.out.println("Booking added to DB");
        } catch (Exception se) {
            System.out.println(se);
        }
    }*/

    public void updateBooking(int b, int m, int l, String s, String e) {
        System.out.println("Inside : updateBooking() in MainProgramOperations");
        try {
            String update = "UPDATE bookings SET memId = " + m + ", laneId = " + l
                    + ", fromDateTime = '" + s + "', toDateTime = '" + e + "' WHERE memId = " + b;
            pStmt = conn.prepareStatement(update);
            pStmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public ResultSet searchBookings(String s) {
        System.out.println("Inside : searchBookings() in MainProgramOperations");
        String sqlStatement = "SELECT * FROM Bookings WHERE " + s;
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();
            ;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }

    public ResultSet checkAvailability(String dateIn, String timeIn) {
        System.out.println("Inside : checkBookingAvailability() in MainProgramOperations");
        System.out.println(dateIn + " " + timeIn);
        String sqlStatement = "SELECT l.lanename FROM lanes l WHERE NOT EXISTS (SELECT 1 FROM bookings b " +
                "WHERE b.laneId = l.laneId AND b.fromdatetime >= '" + dateIn + " " + timeIn + "') ORDER BY l.laneId";
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();
            ;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }
}
///// End of Booking Queries ///////////////////////////////////