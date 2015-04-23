package db;

import model.Booking;
import oracle.jdbc.pool.OracleDataSource;
import org.joda.time.DateTime;

import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Peter on 06/03/2015.
 */
class SetupOperations {
    private final int ONE_WEEK = 7;
    private static int numSlots;
    private Connection conn = null;
    private PreparedStatement pStmt;
    private ResultSet rSet;
    private java.util.Date juDate ;
    private DateTime dt;
    //private JPasswordField = new JPasswordField ("password");

    private SetupOperations()
    {
        conn = openDB();
    }

    private Connection openDB() {
        Scanner in = new Scanner(System.in);
        try {
            // Load the Oracle JDBC driver
            OracleDataSource ods = new OracleDataSource();

            System.out.println("Type Initials:\n(Lowercase. Eg: xy):");
            String val = in.nextLine();
            String user = "", pass = "";

            // Peter Brady Login
            switch (val) {
                case "pb":
                    user = "Peter";
                    pass = "database";
                    break;
                // Luke Byrne Login
                case "lb":
                    user = "system";
                    pass = "passhr";
                    break;
                // Peter Connel Login
                case "pc":
                    user = "hr";
                    pass = "passhr";
                    break;
                // Dylan Byrne login
                case "db":
                    user = "Dylan Byrne's Username";
                    pass = "Dylan Byrne's Password";
                    break;
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

    private void closeDB() {
        try {
            conn.close();
            System.out.print("Connection closed");
        } catch (SQLException e) {
            System.out.print("Could not close connection ");
            e.printStackTrace();
        }
    }

    private void dropTables() {
        System.out.println("Checking for existing tables.");

        try {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Drop the Payments Table and Payments Sequence
            try {
                stmt.execute("DROP TABLE payments");
                System.out.println("Payments Table dropped.");
            }
            catch (SQLException ex) {
                System.out.println("Payments not found.");
            }
            try {
                stmt.execute("DROP SEQUENCE paymentId_seq");
                System.out.println("Payment Sequence dropped.");
            }
            catch (SQLException ex) {
                System.out.println("Payment Sequence not found.");
            }

            // Drop the BookingDetails Table and bookingDetails Sequence
            try {
                stmt.execute("DROP TABLE bookingDetails");
                System.out.println("BookingDetails Table dropped.");
            }
            catch (SQLException ex) {
                System.out.println("BookingDetails not found.");
            }
            try {
                stmt.execute("DROP SEQUENCE BookingDetailsId_seq");
                System.out.println("BookingDetails Sequence dropped.");
            }
            catch (SQLException ex) {
                System.out.println("BookingDetails Sequence not found.");
            }

            // Drop the Bookings Table and Bookings Sequence
            try {
                stmt.execute("DROP TABLE bookings");
                System.out.println("Bookings table dropped.");
            }
            catch (SQLException ex) {
                System.out.println("bookings table not found.");
            }
            try {
                stmt.execute("DROP SEQUENCE bookingId_seq");
                System.out.println("Booking Sequence dropped");
            }
            catch (SQLException ex) {
                System.out.println("Booking Sequence not found.");
            }

            // Drop TimeSlots Table and TimeSlot Sequence
            try {
                stmt.execute("DROP TABLE timeSlots");
                System.out.println("TimeSlots table dropped.");
            }
            catch (SQLException ex) {
                System.out.println("TimeSlots not found.");
            }
            try {
                stmt.execute("DROP SEQUENCE TimeSlotId_seq");
                System.out.println("TimeSlot Sequence dropped.");
            }
            catch (SQLException ex) {
                System.out.println("TimeSlot Sequence not found.");
            }

            // Drop the Lane Table and Lane Sequence
            try {
                stmt.execute("DROP TABLE lane");
                System.out.println("Lanes table dropped.");
            }
            catch (SQLException ex) {
                System.out.println("Lanes not found.");
            }
            try {
                stmt.execute("DROP SEQUENCE LaneNumber_seq");
                System.out.println("LaneNumber Sequence dropped.");
            }
            catch (SQLException ex) {
                System.out.println("LaneNumber Sequence not found.");
            }

            // Drop Stock Table and Stock Sequence
            try {
                stmt.execute("DROP TABLE stock");
                System.out.println("Stock table dropped.");
                stmt.execute("DROP SEQUENCE stockId_seq");
                System.out.println("Stock Sequence dropped.");
            }
            catch (SQLException ex) {
                System.out.println("Stock not found.");
            }
            // Drop the Staff & Roster table table.
            try {
                stmt.execute("DROP TABLE roster");
                System.out.println("Roster table dropped.");
            }
            catch (SQLException ex) {
                System.out.println("Roster table not found.");
            }
            try {
                stmt.execute("DROP TABLE staff");
                System.out.println("Staff table dropped.");
            }
            catch (SQLException ex) {
                System.out.println("staff table not found.");
            }
            try {
                stmt.execute("DROP SEQUENCE StaffId_seq");
                System.out.println("Sequence dropped.");
            }
            catch (SQLException ex) {
                System.out.println("staff Sequence not found.");
            }

            // Drop the Members table and Sequence
            try {
                stmt.execute("DROP TABLE members");
                System.out.println("Members table dropped.");
            }
            catch (SQLException ex) {
                System.out.println("Members Data not found.");
            }
            try {
                stmt.execute("DROP SEQUENCE memId_seq");
                System.out.println("Member Sequence dropped.");
            }
            catch (SQLException ex) {
                System.out.println("Members sequence not found.");
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void createTables() {
        createMembers();
        createStaff();
        createRosterTable();
        createStock();
        createLanes();
        createTimeSlots();
        createBookings();
        createPayments();
        createBookingDetails();
    }

    private void createMembers() {
        try
        {
            System.out.println("Inside Create Members Method");
            // Create a Table
            String create = "CREATE TABLE members " +
                    "(memberId NUMBER PRIMARY KEY, lName VARCHAR(40), fName VARCHAR(40), " +
                    "gender CHAR, phone VARCHAR(40), email VARCHAR(50), address VARCHAR(50), " +
                    "town VARCHAR(50), county VARCHAR(20), numVisits NUMBER)";
            pStmt = conn.prepareStatement(create);
            pStmt.executeUpdate(create);
            pStmt.close();

            // Creating a sequence
            String createseq = "create sequence memId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createseq);
            pStmt.executeUpdate(createseq);
            pStmt.close();

            // Insert data into table
            String insertString = "INSERT INTO members(memberId, lName, fName, gender, phone, email," +
                    "address, town, county, numVisits) values(memId_seq.nextVal, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pStmt = conn.prepareStatement(insertString);

            // Member 1
            pStmt.setString(1, "McGuinness");
            pStmt.setString(2, "Jo");
            pStmt.setString(3, "F");
            pStmt.setString(4, "045899304");
            pStmt.setString(5, "jmcguinness@email.com");
            pStmt.setString(6, "29 Oak Lawn");
            pStmt.setString(7, "Tullamore");
            pStmt.setString(8, "Offaly");
            pStmt.setInt(9, 5);
            pStmt.executeQuery();

            // Member 2
            pStmt.setString(1, "Cummins");
            pStmt.setString(2, "Wesley");
            pStmt.setString(3, "M");
            pStmt.setString(4, "040429020");
            pStmt.setString(5, "wcummins@email.com");
            pStmt.setString(6, "Ballin Hill");
            pStmt.setString(7, "The Curragh");
            pStmt.setString(8, "Kildare");
            pStmt.setInt(9, 2);
            pStmt.executeQuery();

            // Member 3
            pStmt.setString(1, "Moran");
            pStmt.setString(2, "Thomas");
            pStmt.setString(3, "M");
            pStmt.setString(4, "045481127");
            pStmt.setString(5, "tmoran@email.com");
            pStmt.setString(6, "3 Stone Wall");
            pStmt.setString(7, "Ardee");
            pStmt.setString(8, "Louth");
            pStmt.setInt(9, 1);
            pStmt.executeQuery();

            // Member 4
            pStmt.setString(1, "Glennon");
            pStmt.setString(2, "Eamonn");
            pStmt.setString(3, "M");
            pStmt.setString(4, "04784054");
            pStmt.setString(5, "eglennon@email.com");
            pStmt.setString(6, "38 River Place");
            pStmt.setString(7, "Clondalkin");
            pStmt.setString(8, "Dublin 22");
            pStmt.setInt(9, 7);
            pStmt.executeQuery();

            // Member 5
            pStmt.setString(1, "Brady");
            pStmt.setString(2, "Peter");
            pStmt.setString(3, "M");
            pStmt.setString(4, "0871234567");
            pStmt.setString(5, "pbrady@email.com");
            pStmt.setString(6, "Oldcourt");
            pStmt.setString(7, "Blessington");
            pStmt.setString(8, "Wicklow");
            pStmt.setInt(9, 9);
            pStmt.executeQuery();

            // Member 6
            pStmt.setString(1, "Brannigan");
            pStmt.setString(2, "Dinny");
            pStmt.setString(3, "M");
            pStmt.setString(4, "0871852865");
            pStmt.setString(5, "dbrannigan@email.com");
            pStmt.setString(6, "River Side");
            pStmt.setString(7, "Portarlington");
            pStmt.setString(8, "Laois");
            pStmt.setInt(9, 2);
            pStmt.executeQuery();

            // Member 7
            pStmt.setString(1, "McDonnell");
            pStmt.setString(2, "Maureen");
            pStmt.setString(3, "F");
            pStmt.setString(4, "0876546567");
            pStmt.setString(5, "mmcdonnell@email.com");
            pStmt.setString(6, "6 Carrig");
            pStmt.setString(7, "Gort");
            pStmt.setString(8, "Galway");
            pStmt.setInt(9, 1);
            pStmt.executeQuery();

            // Member 8
            pStmt.setString(1, "O'Sullivan");
            pStmt.setString(2, "Nora");
            pStmt.setString(3, "F");
            pStmt.setString(4, "0879856786");
            pStmt.setString(5, "nosullivan@email.com");
            pStmt.setString(6, "102 Any Rd");
            pStmt.setString(7, "Bray");
            pStmt.setString(8, "Wicklow");
            pStmt.setInt(9, 5);
            pStmt.executeQuery();

            // Member 9
            pStmt.setString(1, "Lynch");
            pStmt.setString(2, "Sean");
            pStmt.setString(3, "M");
            pStmt.setString(4, "0873956774");
            pStmt.setString(5, "slynch@email.com");
            pStmt.setString(6, "34 Sea View");
            pStmt.setString(7, "Bray");
            pStmt.setString(8, "Wicklow");
            pStmt.setInt(9, 9);
            pStmt.executeQuery();

            // Member 10
            pStmt.setString(1, "Fallon");
            pStmt.setString(2, "Majella");
            pStmt.setString(3, "F");
            pStmt.setString(4, "0871986334");
            pStmt.setString(5, "mfallon@email.com");
            pStmt.setString(6, "Skibber");
            pStmt.setString(7, "Camolin");
            pStmt.setString(8, "Wexford");
            pStmt.setInt(9, 9);
            pStmt.executeQuery();

            pStmt.close();
        }
        catch (SQLException e)
        {
            System.out.print("SQL Exception " + e);
            System.exit(1);
        }
    }

    //Method to create and populate Staff Table(pc)
    private void createStaff() {
        try {
            System.out.println("Inside createStaff Method");
            // Create a Table
            String createStaff = "CREATE TABLE staff " +
                    "(staffId NUMBER , lname VARCHAR(40), fname VARCHAR(40), " + "bookings NUMBER(4)," +
                    "phone VARCHAR(40), username VARCHAR(40),email VARCHAR(40), password VARCHAR(40), " +
                    "securityQuestion  VARCHAR(40), securityAnswer  VARCHAR(40), admin VARCHAR(2), " +
                    "PRIMARY KEY (staffId))";
            pStmt = conn.prepareStatement(createStaff);
            System.out.println("Attempting to create staff ");
            pStmt.executeUpdate(createStaff);
            pStmt.close();

            System.out.println("staff table created");
            System.out.println("Attempting to create staffid sequence");
            String createseq = "create sequence staffId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createseq);
            pStmt.executeUpdate(createseq);
            pStmt.close();

            System.out.println("staffid sequence created");
            // Insert data into Staff table
            String insertString = "INSERT INTO staff(staffId, lname, fname, bookings, phone, username, email, " +
                    "password, securityQuestion, " +
                    "securityAnswer, admin) values(staffId_seq.NextVal, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            String[] fnames = {"Joe", "Mary", "Frank", "Vera", "Barry", "Adu", "Pablo"};
            String[] lnames = {"Byrne", "Hummins", "Hogan", "Cooke", "Murphy", "Panesh", "Byrne"};
            String[] phoneList = {"012457735", "087244555", "01245741", "05422157", "01245441", "0832454477", "085757133"};
            String[] emailList = {"byrner@hotmail.com", "Hummins@gmail.com", "Hogan@hotmail.com", "Cooke@gmail.com", "Murph@hotmail.comt", "Panesh@hotmail.com", "Byrne@hotmail.com"};
            Random ran = new Random();
            int random;

            pStmt = conn.prepareStatement(insertString);
            for (int i = 0; i < fnames.length; i++) {
                //STAFF NUMBER 1
                random = ran.nextInt(50);
                pStmt.setString(1, fnames[i]);
                pStmt.setString(2, lnames[i]);
                pStmt.setInt(3, 0);
                pStmt.setString(4, phoneList[i]);
                pStmt.setString(5, "user" + (i + 1));
                pStmt.setString(6, emailList[i]);
                pStmt.setString(7, "password");
                pStmt.setString(8, "who am i");
                pStmt.setString(9, "Peter");
                pStmt.setString(10, "N");
                if (i == 0)
                    pStmt.setString(10, "Y");
                pStmt.executeQuery();

                System.out.println("-----------------Staff " + (i + 1) + " created");
            }
            pStmt.close();
        } catch (SQLException e) {
            System.out.print("SQL Exception " + e);
            System.exit(1);
        }
        //getBookingCount();
    }

    private void setBookingCount(){
        try {
            String countBookings = "UPDATE staff SET bookings = ? WHERE staffid = ?";
            String getCount = "select count(staffid), staffid from bookings group by staffid";

            pStmt = conn.prepareStatement(getCount);
            PreparedStatement pStmtUpdate = conn.prepareStatement(countBookings);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                pStmtUpdate.setInt(1, rSet.getInt(1));
                pStmtUpdate.setString(2, rSet.getString(2));
                System.out.println("OUTPUT RESULT SET WHEN COUNTING BOOKINGS      ========----------------");
                pStmtUpdate.executeQuery();
            }
            pStmt.close();
            rSet.close();
        }
        catch (SQLException e) {
            System.out.print("NOT UPDATING STAFF BOOKINGS : SQL Exception " + e);
            System.exit(1);
        }
    }

    //Create Table Roster(PC)
    private void createRosterTable()
    {
        try {
            Timestamp time;
            juDate = new java.util.Date();
            dt = new DateTime(juDate);
            System.out.println("Inside createRoster Method");
            // Create a Table
            String createRoster = "CREATE TABLE roster " +
                    "(staffId NUMBER , startTime TIMESTAMP, finishTime TIMESTAMP," +
                    "PRIMARY KEY (staffId, startTime),FOREIGN KEY (staffId) REFERENCES Staff(staffId)" +
                    "ON DELETE CASCADE)";

            pStmt = conn.prepareStatement(createRoster);
            pStmt.executeUpdate(createRoster);
            pStmt.close();

            String [] starts = {"10:00:00","11:00:00","12:00:00","13:00:00","14:00:00"};
            String [] finishes = {"18:00:00","20:00:00","22:00:00","23:00:00","23:30:00"};
            System.out.println("inputting values in roster");
            // Insert data into Roster table
            String [] fnames = {"Joe","Mary","Frank","Vera","Barry","Adu","Pablo"};

            for (int j = 1; j < fnames.length+1; j++) {
                juDate = new java.util.Date();
                dt = new DateTime(juDate);
                for (int i = 0; i < ONE_WEEK; i++){
                    String insertString = "INSERT INTO roster(staffId, startTime, finishTime) values(?, ?, ?)";
                    pStmt = conn.prepareStatement(insertString);
                    String now;
                    String b = dt.toString("yyyy-MM-dd ");
                    now = starts[new Random().nextInt(starts.length)];
                    b = b + now;
                    System.out.print((i + 1) + ". " + b);
                    time = Timestamp.valueOf(b);

                    pStmt.setInt(1, j);
                    pStmt.setTimestamp(2, time);
                    //This is setting the finish time
                    b = dt.toString("yyyy-MM-dd ");
                    now = finishes[new Random().nextInt(finishes.length)];
                    b = b + now;
                    time = Timestamp.valueOf(b);
                    System.out.println("\t        " + b);
                    pStmt.setTimestamp(3, time);
                    pStmt.executeQuery();
                    dt = dt.plusDays(1);
                }
                pStmt.close();
                System.out.println("Staff "+fnames[j-1]+" rostered");
            }
        }
        catch (SQLException e)
        {
            System.out.print("SQL Exception " + e);
            System.exit(1);
        }
    }


    private void createStock() {
        try {
            System.out.println("Inside Create Stock Method");
            // Create a Table
            String create = "CREATE TABLE stock " +
                    "(stockId NUMBER PRIMARY KEY, shoeSize VARCHAR2(30), quantity NUMBER, details VARCHAR2(30))";
            pStmt = conn.prepareStatement(create);
            pStmt.executeUpdate(create);
            pStmt.close();

            // Creating a sequence
            String createseq = "create sequence stockId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createseq);
            pStmt.executeUpdate(createseq);
            pStmt.close();

            // Insert data into table
            String insertString = "INSERT INTO stock(stockId, shoeSize, quantity, details)" +
                    "values(stockId_seq.nextVal, ?, ?, ?)";
            pStmt = conn.prepareStatement(insertString);

            // Stock 1
            pStmt.setString(1, "Size 3");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 2
            pStmt.setString(1, "Size 4");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 3
            pStmt.setString(1, "Size 5");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 4
            pStmt.setString(1, "Size 6");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 5
            pStmt.setString(1, "Size 7");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 6
            pStmt.setString(1, "Size 8");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 7
            pStmt.setString(1, "Size 9");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 8
            pStmt.setString(1, "Size 10");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 9
            pStmt.setString(1, "Size 11");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 10
            pStmt.setString(1, "Size 12");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 11
            pStmt.setString(1, "Size 3");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 12
            pStmt.setString(1, "Size 4");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 13
            pStmt.setString(1, "Size 5");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 14
            pStmt.setString(1, "Size 6");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 15
            pStmt.setString(1, "Size 7");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 16
            pStmt.setString(1, "Size 8");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 17
            pStmt.setString(1, "Size 9");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 18
            pStmt.setString(1, "Size 10");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 19
            pStmt.setString(1, "Size 11");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 20
            pStmt.setString(1, "Size 12");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            pStmt.close();
        } catch (SQLException e) {
            System.out.print("SQL Exception " + e);
            System.exit(1);
        }
    }

    private void createLanes() {
        System.out.println("Inside CreateLanes Method");
        try {
            String addLanes = "CREATE TABLE lane(" +
                    "laneNumber NUMBER(2) PRIMARY KEY NOT NULL, " +
                    "description VARCHAR(10))";

            pStmt = conn.prepareStatement(addLanes);
            pStmt.executeUpdate(addLanes);
            pStmt.close();

            // Creating a LaneNumber Sequence
            String createSeq = "CREATE SEQUENCE LaneNumber_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createSeq);
            pStmt.executeUpdate(createSeq);
            pStmt.close();

            // Insert Values into Lane Table
            // and assign timeSlots to each Lane
            String insertTimeSlots = "INSERT INTO lane (" +
                    "laneNumber, description) " +
                    "values (laneNumber_seq.nextVal, ?)";
            pStmt = conn.prepareStatement(insertTimeSlots);

            int NUMBER_LANES = 16;
            for (int i = 0; i < NUMBER_LANES; i++) {
                pStmt.setString(1, "Lane " + (i + 1));
                pStmt.execute();
            }
            pStmt.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private void createTimeSlots() {
        System.out.println("Inside Create TimeSlots Method");
        try {
            // Create a TimeSlots Table
            String timeSlots = "CREATE TABLE timeSlots(" +
                    "timeSlotId NUMBER(2) PRIMARY KEY NOT NULL, " +
                    "timeDescription VARCHAR(10))";

            pStmt = conn.prepareStatement(timeSlots);
            pStmt.executeUpdate(timeSlots);
            pStmt.close();

            // Creating a TimeSlot sequence
            String createSeq = "create sequence timeSlotId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createSeq);
            pStmt.executeUpdate(createSeq);
            pStmt.close();

            // Insert Values into TimeSlot Table
            // With times from 10.00 am to 12:00 pm
            String insertTimeSlots = "INSERT INTO timeSlots (" +
                    "timeSlotId, timeDescription) VALUES (timeSlotId_seq.nextVal, ?)";
            pStmt = conn.prepareStatement(insertTimeSlots);

            String[] minutes = {":00", ":15", ":30", ":45"};
            int HOURS_OPEN = 14;
            for (int i = 0; i < HOURS_OPEN; i++) {
                for (String min : minutes) {
                    String timeDesc = (i + 10) + min;
                    pStmt.setString(1, timeDesc);
                    pStmt.execute();
                }
            }
            numSlots = getNumberSlots();
            pStmt.close();
        }
        catch (SQLException e) {
            System.out.print(e);
        }
    }

    private void createBookings() {
        System.out.println("Inside Create Bookings Method");
        try {
            // Create Bookings Table
            String create = "CREATE TABLE bookings(" +
                    "bookingId NUMBER(3) PRIMARY KEY NOT NULL, " +
                    "memberId NUMBER(4), " +
                    "staffId NUMBER(2), " +
                    "numLanes NUMBER(2), " +
                    "games_hours NUMBER(2), " +
                    "numMembers NUMBER(2), " +
                    "numPlayers NUMBER(2), " +
                    "pricingPerHour VARCHAR(2), " +
                    "bookingType VARCHAR(7), " +
                    "FOREIGN KEY (memberId) REFERENCES members (memberId)," +
                    "FOREIGN KEY (staffId) REFERENCES staff (staffId))";
            pStmt = conn.prepareStatement(create);
            pStmt.executeUpdate(create);
            pStmt.close();

            //System.out.println("past create bookings");
            // Creating a booking sequence
            String createseq = "create sequence bookingId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createseq);
            pStmt.executeUpdate(createseq);
            pStmt.close();
        }
        catch (SQLException e) {
            System.out.print("Problem creating the booking table: " + e);
            //System.exit(1);
        }
    }

    private void createBookingDetails() {
        System.out.println("Inside Create BookingDetails Method");
        try {
            String details = "CREATE TABLE bookingDetails(" +
                    "bookingId NUMBER(3), " +
                    "laneNumber NUMBER(2), " +
                    "timeSlotId NUMBER(2), " +
                    "bookingDate DATE, " +
                    "PRIMARY KEY (bookingId, laneNumber, timeSlotId), " +
                    "FOREIGN KEY (bookingId) REFERENCES bookings (bookingId), " +
                    "FOREIGN KEY (laneNumber) REFERENCES lane (laneNumber), " +
                    "FOREIGN KEY (timeSlotId) REFERENCES timeSlots (timeSlotId))";

            pStmt = conn.prepareStatement(details);
            pStmt.executeUpdate(details);
            pStmt.close();

            // Creating a BookingDetails sequence
            String createSeq = "CREATE SEQUENCE bookingDetailsId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createSeq);
            pStmt.executeUpdate(createSeq);
            pStmt.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private void createPayments() {
        System.out.println("Inside Create Payments Method");
        try {
            // Create a TimeSlots Table
            String timeSlots = "CREATE TABLE payments(" +
                    "paymentId NUMBER(3) PRIMARY KEY NOT NULL, " +
                    "bookingId NUMBER(3), " +
                    "deposit DECIMAL(6, 2), " +
                    "totalPrice DECIMAL(6, 2), " +
                    "fullyPaid VARCHAR(2), " +
                    "paymentMethod VARCHAR(12), " +
                    "FOREIGN KEY (bookingId) REFERENCES bookings (bookingId))";
            pStmt = conn.prepareStatement(timeSlots);
            pStmt.executeUpdate(timeSlots);
            pStmt.close();

            // Creating a TimeSlot sequence
            String createSeq = "create sequence paymentId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createSeq);
            pStmt.executeUpdate(createSeq);
            pStmt.close();
        }
        catch (SQLException e) {
            System.out.print(e);
        }
    }


    private void populateBookings() {
        System.out.println("Inside populateBookings Method");

        //double deposits[] = {5.50,10.0,7.50,20};
        String[] startTimes = {":00", ":15", ":30", ":45"};
        String[] paymentMethod = {"VISA", "Mastercard", "Cash"};
        juDate = new java.util.Date();
        dt = new DateTime(juDate);
        String bookingDate, startTime;
        Random ran = new Random();
        int random, memberID, numLanes, hours_games, numPlayers, numMembers, staffID, numBookingsForToday;
        int bookingCounter = 1;
        int laneNumber = 1;
        double deposit, totalPrice;
        final int MAX_PLAYERS = 6;
        String fullyPaid, payMethod, pricedPerHour, bookingType;
        final int SLOTS_PER_HOUR = 4;
        //Loop to populate array of times that can be compared to find the matching timeslot
        //times = getTimeSlots();

        try{
            // SQL to create Bookings Table
            String bookingsInsert = "INSERT INTO bookings(" +
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

            // SQL to create BookingDetailsTable
            String bookingDetails = "INSERT INTO bookingDetails(" +
                    "bookingId, " +
                    "laneNumber, " +
                    "timeSlotId, " +
                    "bookingDate) " +
                    "VALUES (?, ?, ?, ?)";

            for (int i = 0; i < ONE_WEEK; i++) {

                //Inserting values using a loop to populate bookings table for one month
                //loop to fill table for one month

                //random num of bookings for one day
                numBookingsForToday = ran.nextInt(6)+3;
                System.out.println("\nNEW DAY "+(i+1)+"\n====================\n====================\nNumber of Bookings for " + dt.toString("dd-MMM-yyyy") + " is " + numBookingsForToday);

                //loop to add a booking to a day
                for (int bookingsMadeToday = 0; bookingsMadeToday < numBookingsForToday; bookingsMadeToday++) {
                    System.out.println("----------------- BOOKING " + bookingCounter + "----------------------------");

                    pStmt = null;
                    pStmt = conn.prepareStatement(bookingsInsert);

                    // Randomly assign a memberId
                    memberID = ran.nextInt(10)+1;
                    // Randomly assign a staffId
                    staffID = ran.nextInt(7) + 1;
                    // Randomly assign a Number of Players
                    numPlayers = ran.nextInt(16) + 1;
                    // Assign a Number of Lanes, based on Number of Players
                    numLanes = ((int) Math.ceil(numPlayers / MAX_PLAYERS))+1;
                    // Randomly assign Hours or Games Played
                    hours_games = ran.nextInt(3) + 1;
                    // Assign a Number of Members, based on Number of Players
                    numMembers = numPlayers/12;
                    // Randomly assign if Priced per Hour
                    random = ran.nextInt(1);
                    if(random == 0)
                        pricedPerHour = "Y";
                    else
                        pricedPerHour = "N";
                    // Randomly assign if Fully Paid
                    random = ran.nextInt(2);
                    if(random == 0)
                        fullyPaid = "N";
                    else
                        fullyPaid = "Y";
                    // Randomly assign a Booking Type
                    bookingType = "";
                    random = ran.nextInt(3);
                    switch(random){
                        case 0:  bookingType = "Group";
                            break;
                        case 1:
                            bookingType = "Party";
                            break;
                        case 2:  bookingType = "Walk-In";
                            fullyPaid = "Y";
                            break;
                    }
                    // Randomly assign PaymentMethod from Array
                    payMethod = paymentMethod[ran.nextInt(paymentMethod.length)];
                    // Calculate Total Price
                    if(pricedPerHour.equals("Y"))
                        totalPrice = Booking.PRICE_HOUR * (numPlayers * numLanes * hours_games);
                    else
                        totalPrice = Booking.PRICE_GAME*(numPlayers* hours_games);
                    // Calculate Deposit based on Total Price
                    deposit = totalPrice/10;
                    // Randomly assign a Starting Time
                    bookingDate = dt.toString("dd-MMM-yyyy");
                    startTime = dt.toString("dd-MMM-yyyy ");
                    random = ran.nextInt(12) + 11;
                    String now = random + startTimes[ran.nextInt(startTimes.length)];
                    startTime += now;
                    System.out.println("Start time: " + startTime);



                    // Add Random Data to BOOKINGS TABLE
                    pStmt.setInt(1, memberID);
                    pStmt.setInt(2, staffID);
                    pStmt.setInt(3, numLanes);
                    pStmt.setInt(4, hours_games);
                    pStmt.setInt(5, numMembers);
                    pStmt.setInt(6, numPlayers);
                    pStmt.setString(7, pricedPerHour);
                    pStmt.setString(8, bookingType);
                    pStmt.executeQuery();
                    pStmt.close();

                    System.out.println("Booking created now adding lanes to booking");


                    //Loop to assign lanes to the booking
                    int bookingId = getBookingId();
                    for(int l = 0; l < numLanes; l++) {
                        int timeSlot = getTimeSlots(now) + 1;
                        for (int s = 0; (s < hours_games * SLOTS_PER_HOUR) && (s < numSlots); s ++) {
                            try {
                                PreparedStatement pStmt2;
                                pStmt2 = conn.prepareStatement(bookingDetails);

                                System.out.println("Now " + now);
                                System.out.println("*** Booking Id " + bookingId);
                                System.out.println("*** Lane Number " + laneNumber);
                                System.out.println("*** Start Time " + startTime);
                                System.out.println("*** Booking Date " + bookingDate);
                                if (timeSlot > 56) {
                                    timeSlot = 56;
                                }
                                System.out.println("*** TimeSlot " + timeSlot);

                                java.util.Date date2 = new SimpleDateFormat("dd-MMM-yyyy").parse(bookingDate);
                                java.sql.Date sqlDate = new java.sql.Date(date2.getTime());
                                System.out.println("*** SQL DATE " + sqlDate);

                                pStmt2.setInt(1, bookingId);
                                pStmt2.setInt(2, laneNumber);
                                pStmt2.setInt(3, timeSlot);
                                pStmt2.setDate(4, sqlDate);
                                pStmt2.executeQuery();
                                pStmt2.close();
                                timeSlot++;
                                if (laneNumber > 16)
                                    laneNumber = 1;
                            } catch (Exception e) {
                                System.out.println("HERE" + e);
                            }
                        }
                        laneNumber++;
                        if (laneNumber > 16)
                            laneNumber = 1;
                    }
                    System.out.println("LANES ASSIGNED");

                    String paymentInsert = "INSERT INTO payments(" +
                            "paymentId, " +
                            "bookingId, " +
                            "deposit, " +
                            "totalPrice, " +
                            "fullyPaid, " +
                            "paymentMethod)" +
                            "VALUES(paymentId_seq.nextVal, ?, ?, ?, ?, ?)";
                    PreparedStatement pStmt3 = conn.prepareStatement(paymentInsert);
                    pStmt3.setInt(1, bookingId);
                    pStmt3.setDouble(2, deposit);
                    pStmt3.setDouble(3, totalPrice);
                    pStmt3.setString(4, fullyPaid);
                    pStmt3.setString(5, payMethod);
                    pStmt3.executeQuery();
                    pStmt3.close();

                    bookingCounter++;
                }
                dt=dt.plusDays(1);
            }
        }catch (SQLException e)
        {
            System.out.println("DID NOT CREATE A BOOKING :  SQL Exception " + e);
        }
        setBookingCount();
    }

    private int getBookingId() {
        System.out.println("Inside : getBookingId() in SetupOperations");
        String sqlStatement = "SELECT count(*) FROM Bookings";
        int count = 0;
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                count = rSet.getInt(1);
            }
            rSet.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return count;
    }

    private int getNumberSlots() {
        System.out.println("Inside : getNumberSlots() in SetupOperations");
        String sqlStatement = "SELECT count(*) FROM timeSlots";
        int count = 0;
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                count = rSet.getInt(1);
            }
            rSet.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return count;
    }



    private int getTimeSlots(String selectedTime) {
        System.out.println("Inside : getTimeSlots() in SetupOperations");
        //ArrayList<String> times = new ArrayList<>();
        int timeSlot = 0;
        String sqlStatement = "SELECT * FROM timeSlots ORDER BY timeSlotId";
        try {
            pStmt = conn.prepareStatement(sqlStatement);
            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                if (selectedTime.equals(rSet.getString(2))) {
                    timeSlot = rSet.getInt(1);
                }
            }
            rSet.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return timeSlot;
    }

    private void queryTables() {
        queryMembers();
        queryStaff();
        queryLanes();
        queryStock();
        queryBookings();
    }

    private void queryMembers() {
        try {
            String queryString = "SELECT * FROM members";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            System.out.println("Members Table");
            while (rSet.next()){
                System.out.println(rSet.getInt(1) + " " + rSet.getString(2) + " " + rSet.getString(3)
                        + " " + rSet.getString(4) + " " + rSet.getString(5) + " " + rSet.getString(6)
                        + " " + rSet.getString(7) + " " + rSet.getString(8) + " " + rSet.getString(9)
                        + " " + rSet.getInt(10));
            }
            rSet.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    private void queryStaff() {
        try {
            String queryString = "SELECT * FROM staff";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            System.out.println("Staff Table");
            while (rSet.next()){
                System.out.println(rSet.getInt(1) + " " + rSet.getString(2) + " " + rSet.getString(3)
                        + " " + rSet.getString(4) + " " + rSet.getString(5) + " " + rSet.getString(6)
                        + " " + rSet.getString(7) + " " + rSet.getString(8));
            }
            rSet.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    private void queryLanes() {
        try {
            String queryString = "SELECT * FROM lane";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            System.out.println("Lanes Table");
            while (rSet.next()){
                System.out.println(rSet.getInt(1) + " " + rSet.getString(2));
            }
            rSet.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    private void queryStock() {
        try {
            String queryString = "SELECT * FROM stock";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            System.out.println("Stock Table");
            while (rSet.next()){
                System.out.println(rSet.getInt(1) + " " + rSet.getString(2) + " " + rSet.getInt(3)
                        + " " + rSet.getString(4));
            }
            rSet.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private void queryBookings() {
        try {
            String queryString = "SELECT * FROM bookings";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            System.out.println("Bookings Table");
            while (rSet.next()){
                System.out.println(rSet.getInt(1) + " " + rSet.getInt(2) + " " + rSet.getInt(3)
                        + " " + rSet.getString(4) + " " + rSet.getString(5));
            }
            rSet.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }



    public static void main(String args[]) {
        SetupOperations setup = new SetupOperations();
        setup.dropTables();
        setup.createTables();
        setup.populateBookings();
        setup.queryTables();
        setup.closeDB();
    }
}