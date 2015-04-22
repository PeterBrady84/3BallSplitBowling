package db;

import gui.MainScreen;
import model.*;
import oracle.jdbc.pool.OracleDataSource;
import org.joda.time.DateTime;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Peter on 06/03/2015.
 */
public class MainProgramOperations {

    private PreparedStatement pStmt;
    private ResultSet rSet;
    private Connection conn;
    private SimpleDateFormat format;
    private java.util.Date juDate;
    private DateTime dt;
    private Date dateSelected;
    public static Staff user;

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
            if (val.equals("pb")) {
                user = "Peter";
                pass = "database";
            }
            // Luke Byrne Login
            else if (val.equals("lb")) {
                user = "system";
                pass = "passhr";
            }
            // Peter Connel Login
            else if (val.equals("pc")) {
                user = "hr";
                pass = "passhr";
            }
            // Dylan Byrne login
            else if (val.equals("db")) {
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
            String queryString = "SELECT * FROM Members ORDER BY memberId";
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
        String sqlStatement = "SELECT * FROM Members ORDER BY memberId";
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

    public int lastMemberid() {
        System.out.println("Inside : getLastMemberId() in MainProgramOperations");
        rSet = getMemLastRow();
        int id = -1;
        try {
            id = rSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void addMember(Member m) {
        System.out.println("Inside : addMember() in MainProgramOperations");
        try {
            String addsql = "INSERT INTO members(memberId, lName, fName, gender, phone, email," +
                    "address, town, county, numVisits) values(memId_seq.nextVal, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
            pStmt = conn.prepareStatement(addsql);
            pStmt.setString(1, m.getlName());
            pStmt.setString(2, m.getfName());
            pStmt.setString(3, m.getGender());
            pStmt.setString(4, m.getPhone());
            pStmt.setString(5, m.getEmail());
            pStmt.setString(6, m.getAddress());
            pStmt.setString(7, m.getTown());
            pStmt.setString(8, m.getCounty());
            pStmt.executeUpdate();
        } catch (Exception se) {
            System.out.println("Error : " + se);
        }
    }

    public void updateMember(String i, String n, String l, String g, String p, String e, String a, String t, String c) {
        System.out.println("Inside : updateMember() in MainProgramOperations");
        try {
            String update = "UPDATE members SET fName = '" + n + "', lName = '" + l + "', gender = '" + g + "', phone = '"
                    + p + "', email = '" + e + "', address = '" + a + "', town = '" + t + "', county = '" + c + "'" +
                    "WHERE memberId = " + i;
            pStmt = conn.prepareStatement(update);
            pStmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public ResultSet searchMembers(String s) {
        System.out.println("Inside : searchMembers() in MainProgramOperations");
        String sqlStatement = "SELECT * FROM members WHERE " + s;
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }

    public Member findMemberByID(String id) {
        System.out.println("Inside : findMemberByID() in MainProgramOperations");
        String sqlStatement = "SELECT memberid, lName, fName, phone, email, address, town, county FROM Members WHERE memberid = " + id;
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();
            if (rSet != null && rSet.next()) {
                Member customer = new Member(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getString(4),
                        rSet.getString(5), rSet.getString(6), rSet.getString(7), rSet.getString(8));
                return customer;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return null;
    }

    public ResultSet deleteMember(String s) {
        System.out.println("Inside : deleteMembers() in MainProgramOperations");
        String sqlStatement = "DELETE FROM members WHERE " + s;
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }

    /////////////////For Reports///////////////////////////////////////////////////////
    public ResultSet getMember() {
        System.out.println("Inside : getMemberNumVisits() in MainProgramOperations");
        String sqlStatement = "SELECT fname, lname, numVisits, gender FROM Members ORDER BY numVisits";
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

    public ResultSet getGameType() {
        System.out.println("Inside : getGameType() in MainProgramOperations");
        String sqlStatement = "select BOOKINGTYPE,count(bookingtype)from bookings group by BOOKINGTYPE";
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
        dateSelected = MainScreen.dateSelected;
        String date;

        if (dateSelected == null) {
            juDate = new java.util.Date();
            dt = new DateTime(juDate);
            date = dt.toString("dd-MMM-yy").toUpperCase();
        } else {
            format = new SimpleDateFormat("dd-MMM-yy");
            date = format.format(dateSelected).toUpperCase();
        }
        try {
            String queryString = "SELECT s.staffId, lName, fName, s.bookings,  " +
                    "TO_CHAR(startTime, 'HH24:MI') AS STARTTIME, " +
                    "TO_CHAR(finishTime, 'HH24:MI') AS FINISHTIME, s.phone, s.email,s.username , s.password, " +
                    "s.securityQuestion, s.securityAnswer, s.admin " +
                    "FROM staff s, roster r WHERE s.staffId = r.staffId " +
                    "AND startTime LIKE '%" + date + "%'";

            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
        } catch (Exception e) {
            System.out.println("getStaff method used to populate the staffList......" + e);
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
            try {
                rSet = getStaffLastRow();
                int id = rSet.getInt("staffid");
                Timestamp time;
                final int ONE_WEEK = 7;
                juDate = new java.util.Date();
                dt = new DateTime(juDate);
                String idIn = Integer.toString(id);

                for (int i = 0; i < ONE_WEEK; i++) {
                    String insertString = "INSERT INTO roster(staffId, startTime, finishTime) values(? ,?, ?)";
                    pStmt = conn.prepareStatement(insertString);
                    pStmt.setInt(1, id);
                    String now = "";
                    String b = dt.toString("yyyy-MM-dd ");
                    now = "11:00:00";
                    b = b + now;
                    time = Timestamp.valueOf(b);
                    pStmt.setTimestamp(2, time);
                    //This is setting the finish time
                    b = dt.toString("yyyy-MM-dd ");
                    now = "16:00:00";
                    b = b + now;
                    time = Timestamp.valueOf(b);
                    pStmt.setTimestamp(3, time);
                    pStmt.executeQuery();
                    dt = dt.plusDays(1);
                }

            } catch (Exception e) {
                System.out.println("ROSTER NOT FILLING =======================================  " + e);
            }
        } catch (Exception se) {
            System.out.println("Not adding Staff--------------------------------------------" + se);
        }
    }


    public void updateStaffinDB(String i, String l, String n, String e, String p, String log, String pass, String q, String a) {
        System.out.println("Inside : updateStaffinDB() in MainProgramOperations");
        try {
            String update = "UPDATE staff SET fName = '" + n + "', lName = '" + l + "', email = '" + e + "', phone = '" + p
                    + "', username = '" + log + "', password = '" + pass + "', securityQuestion = '" + q + "', securityAnswer = '" + a + "' WHERE staffId = " + i;
            pStmt = conn.prepareStatement(update);
            pStmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("STAFF NOT UPDATED====================================            " + ex);
        }
    }

    public ResultSet searchStaff(String s) {
        System.out.println("Inside : searchStaff() in MainProgramOperations");
        String sqlStatement = "SELECT * FROM Staff WHERE " + s;
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();
            ;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }

    public Staff createUser(String s) {
        System.out.println("Inside : createUser() in MainProgramOperations");
        String sqlStatement = "SELECT staffId, lName, fName, bookings, admin, username FROM Staff WHERE username = '" + s + "'";
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();
            if (rSet != null && rSet.next()) {
                Staff user = new Staff(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getInt(4), rSet.getString(5), rSet.getString(6));
                return user;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return null;
    }

    public boolean isUniqueUsername(String verify) {
        System.out.println("Inside : isUniqueUsername() in MainProgramOperations");
        ArrayList<String> usernames = new ArrayList<>(10);
        String getPassword = "SELECT username FROM staff";
        try {
            pStmt = conn.prepareStatement(getPassword);
            rSet = pStmt.executeQuery();
            //rSet.next();
            int i = 0;
            while (rSet.next()) {
                usernames.add(rSet.getString(1));
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return usernames.contains(verify);
    }


    public boolean checkPass(String username, char[] password) {
        System.out.println("Inside : checkPass() in MainProgramOperations");
        String getPassword = "SELECT password FROM staff where username = '" + username + "'";
        boolean passwordsMatch = false;
        try {
            pStmt = conn.prepareStatement(getPassword);
            rSet = pStmt.executeQuery();
            rSet.next();
            passwordsMatch = Arrays.toString(password).equals(rSet.getString(1));
        } catch (Exception e) {
            System.out.println(e);
        }
        return passwordsMatch;
    }

    public ArrayList<String> queryLogin() {
        System.out.println("Inside : queryLogin() in MainProgramOperations");
        ArrayList<String> login = new ArrayList<>();
        try {
            String queryString = "SELECT username FROM staff";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                login.add(rSet.getString(1));
            }
        } catch (Exception e) {
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
            while (rSet.next()) {
                question = rSet.getString(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return question;
    }

    public String changePassword(char[] pw, String username) {
        System.out.println("Inside : changePassword() in MainProgramOperations");
        String ans = "";
        try {
            String queryString = "UPDATE staff SET password = '" + Arrays.toString(pw) + "' WHERE username = '" + username + "'";
            pStmt = conn.prepareStatement(queryString);
            pStmt.executeUpdate();
            String query2 = "SELECT securityAnswer FROM staff"
                    + " WHERE username = '" + username + "'";
            pStmt = conn.prepareStatement(query2);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                ans = rSet.getString(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ans;
    }

    public void deleteStaff(String user, int id) {
        System.out.println("Inside : deleteStaff() in MainProgramOperations");
        String ans = "";
        try {
            String queryString = "DELETE FROM staff WHERE username = '" + user + "' OR staffid = " + id;
            pStmt = conn.prepareStatement(queryString);
            pStmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    ///// End of Staff Queries ///////////////////////////////////


    ///// Beginning of Lane Queries ///////////////////////////////////
    public ResultSet getLanes() {
        System.out.println("Inside : getLanes() in MainProgramOperations");
        try {
            String queryString = "SELECT * FROM lane ORDER BY laneNumber";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rSet;
    }

    public int[] getLanesAvailable(String dateIn, String startTime, String endTime) {
        System.out.println("Inside : getLanesAvailable() in MainProgramOperations");
        int[] lanes = new int[16];
        int startId = 0, endId = 0;

        String getStartId = "SELECT timeSlotId " +
                "FROM timeSlots " +
                "WHERE timeDescription = '" + startTime + "'";
        try {
            pStmt = conn.prepareStatement(getStartId);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                startId = rSet.getInt(1);
            }
            rSet.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        String getEndId = "SELECT timeSlotId " +
                "FROM timeSlots " +
                "WHERE timeDescription = '" + endTime + "'";
        try {
            pStmt = conn.prepareStatement(getEndId);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                endId = rSet.getInt(1);
            }
            rSet.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        String getLanes = "SELECT DISTINCT laneNumber " +
                "FROM bookingDetails " +
                "WHERE laneNumber NOT IN " +
                "(SELECT laneNumber " +
                "FROM bookingDetails " +
                "WHERE bookingDate = '" + dateIn +
                "' AND timeSlotId BETWEEN " + startId +
                " AND " + endId +
                " GROUP BY laneNumber) " +
                "ORDER BY laneNumber";
        try {
            pStmt = conn.prepareStatement(getLanes);
            rSet = pStmt.executeQuery();
            int index = 0;
            while (rSet.next()) {
                lanes[index] = rSet.getInt(1);
                index++;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return lanes;
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
        System.out.println("Inside : addStock() in MainProgramOperations");
        try {
            String addsql = "INSERT INTO Stock(stockId, shoeSize, quantity, details) values(stockId_seq.nextVal, ?, ?, ?)";
            pStmt = conn.prepareStatement(addsql);
            pStmt.setString(1, s.getShoeSize());
            pStmt.setInt(2, s.getQuantity());
            pStmt.setString(3, s.getDetails());
            pStmt.executeUpdate();
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
        Booking add = b;
        System.out.println("Inside : addBooking() in MainProgramOperations\n" +
                "Booking = id is " + add.getId() + ", " + add.getMemId() + ", " + add.getStaffId());
        try {
            String addBooking = "INSERT INTO bookings(" +
                    "bookingId, " +
                    "memberId, " +
                    "staffId, " +
                    "numLanes, " +
                    "games_hours, " +
                    "numMembers, " +
                    "numPlayers, " +
                    "pricingPerHour, " +
                    "bookingType) " +
                    "VALUES(bookingId_seq.nextVal, ?, ?, ?, ?, ?, ?, ?, ?)";
            pStmt = conn.prepareStatement(addBooking);
            pStmt.setInt(1, b.getMemId());
            pStmt.setInt(2, b.getStaffId());
            pStmt.setInt(3, b.getNumLanes());
            pStmt.setInt(4, b.getHours_games());
            pStmt.setInt(5, b.getNumMembers());
            pStmt.setInt(6, b.getNumPlayers());
            pStmt.setString(7, b.getPricingPerHour());
            pStmt.setString(8, b.getBookingType());

            pStmt.executeUpdate();
        } catch (Exception se) {
            System.out.println(se);
        }
    }

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

    public ResultSet checkAvailability(String dateIn, String startTime, String endTime) {
        System.out.println("Inside : checkBookingAvailability() in MainProgramOperations");
        int startId = 0, endId = 0;
        String getStartId = "SELECT timeSlotId " +
                "FROM timeSlots " +
                "WHERE timeDescription = '" + startTime + "'";
        try {
            pStmt = conn.prepareStatement(getStartId);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                startId = rSet.getInt(1);
            }
            rSet.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        String getEndId = "SELECT timeSlotId " +
                "FROM timeSlots " +
                "WHERE timeDescription = '" + endTime + "'";
        try {
            pStmt = conn.prepareStatement(getEndId);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                endId = rSet.getInt(1);
            }
            rSet.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        String sqlStatement = "SELECT COUNT (DISTINCT laneNumber) " +
                "FROM bookingDetails " +
                "WHERE laneNumber NOT IN " +
                "(SELECT laneNumber " +
                "FROM bookingDetails " +
                "WHERE bookingDate = '" + dateIn + "' " +
                "AND timeSlotId BETWEEN " + startId +
                " AND " + endId +
                " GROUP BY laneNumber)";
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return rSet;
    }

    public int getNumHours(String startTime, String finishTime) {
        System.out.println("Inside : getNumHours() in MainProgramOperations");
        int hrs;
        int startId = 0, endId = 0;

        String getStartId = "SELECT timeSlotId " +
                "FROM timeSlots " +
                "WHERE timeDescription = '" + startTime + "'";
        try {
            pStmt = conn.prepareStatement(getStartId);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                startId = rSet.getInt(1);
            }
            rSet.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        String getEndId = "SELECT timeSlotId " +
                "FROM timeSlots " +
                "WHERE timeDescription = '" + finishTime + "'";
        try {
            pStmt = conn.prepareStatement(getEndId);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                endId = rSet.getInt(1);
            }
            rSet.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        hrs = endId - startId;
        return hrs;
    }

    public boolean isAvailable(String dateSelected, int numLanes, String startTime, String finishTime) {
        System.out.println("Inside : isAvailable() in MainProgramOperations");
        int startId = 0, endId = 0;

        String getStartId = "SELECT timeSlotId " +
                "FROM timeSlots " +
                "WHERE timeDescription = '" + startTime + "'";
        try {
            pStmt = conn.prepareStatement(getStartId);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                startId = rSet.getInt(1);
            }
            rSet.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        String getEndId = "SELECT timeSlotId " +
                "FROM timeSlots " +
                "WHERE timeDescription = '" + finishTime + "'";
        try {
            pStmt = conn.prepareStatement(getEndId);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                endId = rSet.getInt(1);
            }
            rSet.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        String countLanes = "select count (distinct LANENUMBER) from BOOKINGDETAILS WHERE LANENUMBER NOT IN\n" +
                "(select LANENUMBER from BOOKINGDETAILS where bookingdate = '" + dateSelected + "' and timeslotid " +
                "between " + startId + " and " + endId + " group by lanenumber)";
        try {
            pStmt = conn.prepareStatement(countLanes);
            ResultSet count;
            count = pStmt.executeQuery();
            int available = 0;
            while (rSet.next()) {
                available = count.getInt(1);
            }
            rSet.close();
            if (numLanes > available) {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("ERROR in checkAvailability(): " + ex.getMessage());
        }
        return true;
    }
///// End of Booking Queries ///////////////////////////////////


    ///// Beginning of BookingDetails Queries ///////////////////////////////////
    public ResultSet getBookingDetails() {
        System.out.println("Inside : getBookingDetails() in MainProgramOperations");
        try {
            String queryString = "SELECT * FROM bookingDetails " +
                    "ORDER BY bookingId, laneNumber, timeSlotId, bookingDate";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rSet;
    }

    public void addBookingDetails(BookingDetails bd) {
        System.out.println("Inside : addBookingDetails() in MainProgramOperations");
        try {
            String bookingDetails = "INSERT INTO bookingDetails(" +
                    "bookingId, " +
                    "laneNumber, " +
                    "timeSlotId, " +
                    "bookingDate) " +
                    "VALUES (?, ?, ?, ?)";
            pStmt = conn.prepareStatement(bookingDetails);
            pStmt.setInt(1, bd.getBookingId());
            pStmt.setInt(2, bd.getLaneNumber());
            pStmt.setInt(3, bd.getTimeSlotId());
            pStmt.setDate(4, bd.getBookingDate());

            pStmt.executeUpdate();
        } catch (Exception se) {
            System.out.println(se);
        }
    }
///// End of BookingDetails Queries ///////////////////////////////////


    ///// Beginning of Payment Queries ///////////////////////////////////
    public ResultSet getPayments() {
        System.out.println("Inside : getPayments() in MainProgramOperations");
        try {
            String queryString = "SELECT * FROM payments ORDER BY paymentId";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rSet;
    }

    public void addPayment(Payment pay) {
        System.out.println("Inside : addPayment() in MainProgramOperations");
        String sqlStatement = "INSERT INTO Payments (paymentid, bookingid, deposit, totalprice, fullyPaid, paymentmethod) values (?,?,?,?,?,?)";
        try {
            pStmt = conn.prepareStatement(sqlStatement);

            pStmt.setInt(1, pay.getPaymentId());
            pStmt.setInt(2, pay.getBookingId());
            pStmt.setDouble(3, pay.getDeposit());
            pStmt.setDouble(4, pay.getTotalPrice());
            pStmt.setString(5, pay.getFullyPaid());
            pStmt.setString(6, pay.getPaymentMethod());

            pStmt.executeQuery();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
///// End of Payment Queries ///////////////////////////////////


    ///// Beginning of TimeSlots Queries ///////////////////////////////////
    public ResultSet getTimeSlots() {
        System.out.println("Inside : getTimeSlots() in MainProgramOperations");
        try {
            String queryString = "SELECT * FROM timeSlots ORDER BY timeSlotId";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rSet;
    }

    public String getTimeDesc(int t) {
        System.out.println("Inside : getTimeDesc() in MainProgramOperations");
        String time = "";
        try {
            String queryString = "SELECT timeDescription FROM timeSlots WHERE timeSlotId = " + t;
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                time = rSet.getString(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return time;
    }

    public int[] getTimes(String start, String end) {
        System.out.println("Inside : getTimes() in MainProgramOperations");
        int[] count = new int[0];
        int size = 0;
        try {
            String queryString = "SELECT COUNT(timeSlotId) " +
                    "FROM timeSlots " +
                    "WHERE timeDescription " +
                    "BETWEEN '" + start + "'" +
                    "AND '" + end + "'";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                size = rSet.getInt(1);
            }
            rSet.close();

            String queryString2 = "SELECT timeSlotId " +
                    "FROM timeSlots " +
                    "WHERE timeDescription " +
                    "BETWEEN '" + start + "'" +
                    "AND '" + end + "'";
            pStmt = conn.prepareStatement(queryString2);
            rSet = pStmt.executeQuery();
            count = new int[size];
            int index = 0;
            while (rSet.next()) {
                count[index] = rSet.getInt(1);
                index++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return count;
    }
///// End of TimeSlots Queries ///////////////////////////////////


    ///// Beginning of Misc Queries ///////////////////////////////////
    public ResultSet getBookingDataForHomeTab(Date d) {
        System.out.println("Inside : getBookingDataForHomeTab() in MainProgramOperations");
        format = new SimpleDateFormat("dd-MMM-yy");
        String date = format.format(d);
        try {
            String queryString = "SELECT bd.laneNumber, bd.bookingId, bd.bookingDate,\n" +
                    "MIN(ts.timeDescription), MAX(ts.timeDescription)\n" +
                    "FROM bookingDetails bd\n" +
                    "INNER JOIN lane l ON bd.laneNumber = l.laneNumber\n" +
                    "INNER JOIN bookings b ON bd.bookingId = b.bookingId\n" +
                    "INNER JOIN timeSlots ts ON bd.timeSlotid = ts.timeSlotid\n" +
                    "WHERE bd.bookingDate = '" + date + "'\n" +
                    "GROUP BY bd.bookingId, bd.lanenumber, bd.bookingdate\n" +
                    "ORDER BY bd.bookingId, l.laneNumber";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rSet;
    }

    public ResultSet getBookingDataForBookingTab() {
        System.out.println("Inside : getBookingDataForBookingTab() in MainProgramOperations");
        try {
            String queryString = "SELECT l.laneNumber, m.lname, m.fname, bd.bookingDate,\n" +
                    "MIN(ts.timeDescription) \"STARTTIME\",\n" +
                    "MAX(ts.timeDescription) \"ENDTIME\", b.numplayers\n" +
                    "FROM bookingdetails bd\n" +
                    "INNER JOIN lane l ON bd.lanenumber = l.lanenumber\n" +
                    "INNER JOIN bookings b ON bd.bookingid = b.bookingid\n" +
                    "INNER JOIN timeslots ts ON bd.timeslotid = ts.timeslotid\n" +
                    "INNER JOIN members m ON b.memberId = m.memberid\n" +
                    "group by l.laneNumber, m.lname, m.fname, bd.bookingDate, b.numplayers\n" +
                    "ORDER BY bd.bookingdate, l.laneNumber";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rSet;
    }
}
///// End of TimeSlots Queries ///////////////////////////////////
