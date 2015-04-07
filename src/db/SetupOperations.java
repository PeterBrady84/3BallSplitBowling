package db;

import model.Booking;
import oracle.jdbc.pool.OracleDataSource;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Peter on 06/03/2015.
 */
public class SetupOperations {
    private final int ONE_WEEK = 7;
    private final int ONE_MONTH = 30;

    private Connection conn = null;
    private PreparedStatement pStmt = null;
    private Statement stmt = null;
    private ResultSet rSet;
    private java.util.Date juDate ;
    private DateTime dt;

    public SetupOperations()
    {
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

    public void dropTables() {
        System.out.println("Checking for existing tables.");

        try {
            // Get a Statement object.
            stmt = conn.createStatement();
            try {
                // Drop the Staff and Roster table.
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
            try {
                stmt.execute("DROP TABLE stock");
                System.out.println("Stock table dropped.");
                stmt.execute("DROP SEQUENCE stockId_seq");
                System.out.println("Stock Sequence dropped.");
            }
            catch (SQLException ex) {
                System.out.println("stock not found.");
            }
            try {
                stmt.execute("DROP TABLE lane");
                System.out.println("Lanes table dropped.");
                //
            }
            catch (SQLException ex) {
                System.out.println("Lanes not found.");
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
            try {
                // Drop the Members table.
                stmt.execute("DROP TABLE members");
                System.out.println("Members table dropped.");
            }
            catch (SQLException ex) {
                System.out.println("Members Data not found.");
            }
            try {
                // Drop the Members table.

                stmt.execute("DROP SEQUENCE memId_seq");
                System.out.println("Member Sequence dropped.");
            }
            catch (SQLException ex) {
                System.out.println("Members sequence not found.");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void createTables() {
        createMembers();
        createStaff();
        createRosterTable();
        //createLanes();
        createStock();
        createBookings();
    }

    public void createMembers() {
        try
        {
            System.out.println("Inside Create Members Method");
            // Create a Table
            String create = "CREATE TABLE members " +
                    "(memId NUMBER PRIMARY KEY, lName VARCHAR(40), fName VARCHAR(40), " +
                    "gender CHAR, phone VARCHAR(40), email VARCHAR(50), address VARCHAR(50), " +
                    "town VARCHAR(50), county VARCHAR(20), numVisits NUMBER)";
            pStmt = conn.prepareStatement(create);
            pStmt.executeUpdate(create);

            // Creating a sequence
            String createseq = "create sequence memId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createseq);
            pStmt.executeUpdate(createseq);

            // Insert data into table
            String insertString = "INSERT INTO members(memId, lName, fName, gender, phone, email," +
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
        }
        catch (SQLException e)
        {
            System.out.print("SQL Exception " + e);
            System.exit(1);
        }
    }

    //Method to create and populate Staff Table(pc)
    public void createStaff() {
        try {
            System.out.println("Inside createStaff Method");
            // Create a Table
            String createStaff = "CREATE TABLE staff " +
                    "(staffId NUMBER , lname VARCHAR(40), fname VARCHAR(40), " + "bookings NUMBER(4)," +
                    "phone VARCHAR(40), username VARCHAR(40),email VARCHAR(40), password VARCHAR(40), " +
                    "securityQuestion  VARCHAR(40), securityAnswer  VARCHAR(40), admin CHAR," +
                    "PRIMARY KEY (staffId))";
            pStmt = conn.prepareStatement(createStaff);
            System.out.println("Attempting to create staff ");
            pStmt.executeUpdate(createStaff);
            System.out.println("staff table created");
            System.out.println("Attempting to create staffid sequence");
            String createseq = "create sequence staffId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createseq);
            pStmt.executeUpdate(createseq);
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
        } catch (SQLException e) {
            System.out.print("SQL Exception " + e);
            System.exit(1);
        }
        //getBookingCount();
    }

    public void setBookingCount(){
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
        }
            catch (SQLException e) {
                System.out.print("NOT UPDATING STAFF BOOKINGS : SQL Exception " + e);
                System.exit(1);
            }

    }

    //Create Table Roster(PC)
    public void createRosterTable()
    {
        try {
            Timestamp time;
            juDate = new java.util.Date();
            dt = new DateTime(juDate);
            System.out.println("Inside createRoster Method");
            // Create a Table
            String createRoster = "CREATE TABLE roster " +
                    "(staffId NUMBER , startTime DATE, finishTime DATE," +
                    "PRIMARY KEY (staffId, startTime),FOREIGN KEY (staffId) REFERENCES Staff(staffId)" +
                    "ON DELETE CASCADE)";

            pStmt = conn.prepareStatement(createRoster);

            pStmt.executeUpdate(createRoster);

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
                    String now = "";
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
                System.out.println("Staff "+fnames[j-1]+" rostered");
            }


        }
        catch (SQLException e)
        {
            System.out.print("SQL Exception " + e);
            System.exit(1);
        }
    }


    public void createStock() {
        try {
            System.out.println("Inside Create Stock Method");
            // Create a Table
            String create = "CREATE TABLE stock " +
                    "(stockId NUMBER PRIMARY KEY, shoeSize VARCHAR2(30), quantity NUMBER, details VARCHAR2(30))";
            pStmt = conn.prepareStatement(create);
            pStmt.executeUpdate(create);

            // Creating a sequence
            String createseq = "create sequence stockId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createseq);
            pStmt.executeUpdate(createseq);

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

        } catch (SQLException e) {
            System.out.print("SQL Exception " + e);
            System.exit(1);
        }
    }

    public void createBookings() {
        try {
            System.out.println("Inside Create Bookings Method");
            // Create a Table
            String create = "CREATE TABLE bookings(\n" +
                    "bookingId NUMBER(3) PRIMARY KEY NOT NULL, \n" +
                    "memId NUMBER(4), \n" +
                    "staffId NUMBER(2),\n" +
                    "numlanes NUMBER(2),\n" +
                    "fromDateTime TIMESTAMP, \n" +
                    "toDateTime TIMESTAMP, \n" +
                    "deposit DECIMAL (6,2),\n" +
                    "totalprice DECIMAL (6,2),\n" +
                    "games_hours NUMBER(2),\n" +
                    "numMembers NUMBER(2),\n" +
                    "numPlayers NUMBER(2),\n" +
                    "fullypaid CHAR,\n" +
                    "paymentMethod VARCHAR2(10),\n" +
                    "pricingPerHour CHAR,\n" +
                    "bookingtype VARCHAR2(6),\n" +
                    "FOREIGN KEY (memId) REFERENCES members (memId),\n" +
                    "FOREIGN KEY (staffId) REFERENCES staff (staffId))";
            pStmt = conn.prepareStatement(create);
            pStmt.executeUpdate(create);

            String addLanes = "CREATE TABLE lane(\n" +
                    "laneNumber NUMBER, \n" +
                    "bookingId NUMBER(3),\n" +
                    "today TIMESTAMP,\n" +
                    "laneName VARCHAR(10), \n" +
                    "inUse CHAR(2),\n" +
                    "timeSlot INTEGER, " +
                    "PRIMARY KEY (laneNumber,timeslot,today)," +
                    "FOREIGN KEY (bookingId) REFERENCES bookings (bookingId))";

            pStmt = conn.prepareStatement(addLanes);
            pStmt.executeUpdate(addLanes);
            //System.out.println("past create bookings");
            // Creating a booking sequence
            String createseq = "create sequence bookingId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createseq);
            pStmt.executeUpdate(createseq);

        }
        catch (SQLException e)
        {
            System.out.print("Problem creating the booking table: " + e);
            //System.exit(1);
        }

            // Insert data into table
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

            String insertLanes = "INSERT INTO lane(lanenumber, bookingid, today, lanename, inuse, timeslot)values(?,?,?,?,?,?)";

            PreparedStatement pStmt2 = conn.prepareStatement(insertLanes);

            //Inserting values using a loop to populate bookings table for one month
            String[] startTimes = {":00:00", ":15:00", ":30:00", ":45:00"};
            //double deposits[] = {5.50,10.0,7.50,20};
            String[] paymentMethod = {"VISA", "Mastercard", "cash"};
            juDate = new java.util.Date();
            dt = new DateTime(juDate);
            Timestamp time, bookingDate;
            Random ran = new Random();
            int random, memberID, numLanes, hours_games, numPlayers, numMembers, staffID;
            int numBookingsForToday;
            int bookingCounter = 1;
            int laneNumber = 1;
            double deposit;
            double totalPrice;
            final int MAX_PLAYERS = 6;
            String fullyPaid, payMethod, pricedPerHour;
            String bookingType;
            final int SLOTS_PER_HOUR = 4;
            //Loop to populate array of times that can be compared to find the matching timeslot
            ArrayList<String> times = new ArrayList<String>();
            String []minutes = {":00",":15",":30",":45"};
            String slot = "";
            final int HOURS_OPEN = 12;
            for(int hour = 12; hour< HOURS_OPEN+12;hour++){
                for (String min : minutes) {
                    slot = hour + min;
                    times.add(slot);
                }
            }

            //loop to fill table for one month
            for (int i = 0; i < ONE_MONTH; i++) {
                //random num of bookings for one day
                numBookingsForToday = ran.nextInt(3)+3;
                //System.out.println("\n\nNEW DAY "+(i+1)+"    =======================\n==================\nNumber of Bookings for " + dt + " is " + numBookingsForToday + "   --------------- \n  ");
                //loop to add a booking to a day
                for (int bookingsMadeToday = 0; bookingsMadeToday < numBookingsForToday; bookingsMadeToday++) {
                    //System.out.println("----------------- BOOKING "+ bookingCounter + "----------------------------");

                    //randomly assign a memid to a booking
                    memberID = ran.nextInt(10)+1;
                    pStmt.setInt(1, memberID);
                    //randomly assign a staffid to a booking
                    staffID = ran.nextInt(7) + 1;
                    pStmt.setInt(2, staffID);
                    //randomly assign number of players
                    numPlayers = ran.nextInt(16) + 1;
                    pStmt.setInt(3, numPlayers);
                    // assign numLanes according to the number of players
                    numLanes = ((int) Math.ceil(numPlayers / MAX_PLAYERS))+1;
                    pStmt.setInt(4, numLanes);
                    //randomly assign a startTime to a booking
                    String start = dt.toString("yyyy-MM-dd ");
                    bookingDate = Timestamp.valueOf(start+"00:00:00");
                    random = ran.nextInt(12)+11;
                    String now = random+startTimes[ran.nextInt(startTimes.length)];
                    start = start + now;
                    //System.out.println("Start time: "+start);
                    time = Timestamp.valueOf(start);
                    pStmt.setTimestamp(6, time);

                    //randomly assign how many hours or games the booking will be for
                    hours_games = ran.nextInt(3) + 1;
                    pStmt.setInt(5, hours_games);

                    // finishtime will be starttime plus number of hours
                    dt = DateTime.parse(start,
                            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
                    dt = dt.plusHours(hours_games);
                    start = dt.toString("yyyy-MM-dd HH:mm:ss");
                    //System.out.println("finishtime: "+start);
                    time = Timestamp.valueOf(start);
                    pStmt.setTimestamp(7, time);

                    numMembers = numPlayers/12;
                    pStmt.setInt(8, numMembers);

                    payMethod = paymentMethod[ran.nextInt(paymentMethod.length)];
                    pStmt.setString(9, payMethod);

                    random = ran.nextInt(1);
                    if(random == 0) {
                        pricedPerHour = "Y";
                    }
                    else
                        pricedPerHour = "N";
                    pStmt.setString(10, pricedPerHour);

                    random = ran.nextInt(2);

                    if(random == 0) {
                        fullyPaid = "N";
                    }
                    else
                        fullyPaid = "Y";

                    bookingType = "";
                    random = ran.nextInt(3);
                    switch(random){
                        case 0:  bookingType = "Group";
                            break;
                        case 1:  bookingType = "Party";
                            break;
                        case 2:  bookingType = "Walkin";
                            deposit = 0;
                            fullyPaid = "Y";
                            break;
                    }
                    //System.out.println("BOOKING TYPE = "+bookingType);
                    pStmt.setString(11, fullyPaid);
                    //System.out.println("FULLY PAID = "+fullyPaid);
                    pStmt.setString(12,bookingType);

                    if(pricedPerHour.equals("Y")) {
                        totalPrice = Booking.PRICE_HOUR * (numPlayers * numLanes * hours_games);
                    }else
                        totalPrice = Booking.PRICE_GAME*(numPlayers* hours_games);

                   // System.out.println("Number of lanes = "+numLanes+"\nHOURS_GAMES = "+hours_games);
                    //System.out.println("TOTAL PRICE = " + totalPrice);
                    pStmt.setDouble(13,totalPrice);

                    deposit = totalPrice/10;
                    //System.out.println("\tdeposit = "+deposit);
                    pStmt.setDouble(14, deposit);

                    pStmt.executeQuery();

                    System.out.println("Booking created now adding lanes to booking");

                    //Loop to assign lanes to the booking
                    for(int lanes = 0; lanes<numLanes; lanes++) {
                        int timeslot = assignTimeSlot(times, now);
                        for(int index = 0;index<hours_games*SLOTS_PER_HOUR;index++){
                            String name = "lane " + laneNumber;
                            pStmt2.setInt(1, laneNumber);
                            pStmt2.setInt(2, bookingCounter);
                            System.out.print("\t"+name);
                            pStmt2.setTimestamp(3, bookingDate);
                            pStmt2.setString(4, name);
                            pStmt2.setString(5, "N");
                            pStmt2.setInt(6, timeslot);
                            timeslot++;
                            if (laneNumber > 16)
                                laneNumber = 1;

                            pStmt2.executeQuery();

                            //System.out.println("\tLane number: " + laneNumber + "\t timeslot = " + timeslot);
                        }
                        laneNumber++;
                    }
                    System.out.println("LANES ASSIGNED");
                    System.out.println(bookingCounter);
                    bookingCounter++;
                    System.out.println(bookingCounter);

                }
                dt=dt.plusDays(1);
            }
        }catch (SQLException e)
        {
            System.out.print("DID NOT CREATE A BOOKING :  SQL Exception " + e);
        }

        setBookingCount();

    }

    public int assignTimeSlot(ArrayList list, String selectedTime) {
        int timeslot = 0;
        ArrayList <String> times = list;
        for(String time:times){
            if(selectedTime.equals(time))
              timeslot=times.indexOf(time)+1;
        }
        return timeslot;
    }

    public void queryTables() {
        queryMembers();
        queryStaff();
        queryLanes();
        queryStock();
        queryBookings();
    }

    public void queryMembers() {
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
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void queryStaff() {
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
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void queryLanes() {
        try {
            String queryString = "SELECT * FROM lane";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            System.out.println("Lanes Table");
            while (rSet.next()){
                System.out.println(rSet.getInt(1) + " " + rSet.getString(2) + " " + rSet.getInt(3));
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void queryStock() {
        try {
            String queryString = "SELECT * FROM stock";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            System.out.println("Stock Table");
            while (rSet.next()){
                System.out.println(rSet.getInt(1) + " " + rSet.getString(2) + " " + rSet.getInt(3)
                        + " " + rSet.getString(4));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void queryBookings() {
        try {
            String queryString = "SELECT * FROM bookings";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            System.out.println("Bookings Table");
            while (rSet.next()){
                System.out.println(rSet.getInt(1) + " " + rSet.getInt(2) + " " + rSet.getInt(3)
                        + " " + rSet.getString(4) + " " + rSet.getString(5));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }



    public static void main(String args[]) {
        SetupOperations setup = new SetupOperations();
        setup.dropTables();
        setup.createTables();
        setup.queryTables();
        setup.closeDB();
    }
}