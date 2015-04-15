package gui;

import db.MainProgramOperations;
import model.*;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import org.joda.time.DateTime;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by Peter on 06/03/2015.
 */
public class MainScreen extends JFrame implements ActionListener {

    private JPanel p1, p2, p3, jp1, jp2, jp3, jp4, jp5, jp6;
    private JLabel bowl, header, help, button, loggedIn;
    private JButton checkAvailability, changeUser, logout;
    private JTabbedPane jtp;
    private MainProgramOperations progOps;
    private BookingTab bt;
    private ArrayList<Member> memList;
    private ArrayList<Staff> staffList;
    private ArrayList<Stock> stockList;
    private ArrayList<Booking> bookingList = new ArrayList<Booking>();
    private ArrayList<Lane> laneList;
    private ArrayList<TimeSlot> timeSlotList;
    private ArrayList<BookingDetails> bookingDetailsList;
    private ArrayList<Payment> paymentsList;
    private UtilDateModel model;
    public JDatePanelImpl mainDatePanel;
    public JDatePickerImpl mainDatePicker;
    public JFormattedTextField dateInTxt;
    public static Date dateSelected;
    private java.util.Date juDate ;
    private DateTime dt;
    private Staff user;

    public MainScreen(Staff user, ArrayList<Member> m, ArrayList<Staff> s, ArrayList<Stock> st, ArrayList<Booking> b,
                      ArrayList<Lane> l, ArrayList<TimeSlot> t, ArrayList<BookingDetails> bd, ArrayList<Payment> p, MainProgramOperations po) {
        System.out.println("Inside : MainScreenGUI");

        /*boolean administrator;
        UserLogged = user;
        if (user.isAdmin()) administrator = true;
        else administrator = false;*/

        this.progOps = po;
        this.memList = m;
        this.staffList = s;
        this.stockList = st;
        this.bookingList = b;
        this.laneList = l;
        this.timeSlotList = t;
        this.bookingDetailsList = bd;
        this.paymentsList = p;
        this.user = user;

        //This the calendar panel for the mainscreen which sets a date in dd-MMM-yy format
        //String dateSelected can be used by all the tabs for all DB queries.
        model = new UtilDateModel();
        mainDatePanel = new JDatePanelImpl(model);
        mainDatePicker = new JDatePickerImpl(mainDatePanel);
        juDate = (Date) mainDatePicker.getModel().getValue();
        dt = new DateTime(juDate);

        this.dateSelected = dt.toDate();

        setTitle("3-Ball-Strike Bowling");
        setSize(850, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(Color.WHITE);

        // Add panels to Frame
        // Add Panel 1
        p1 = new JPanel();
        p1.setPreferredSize(new Dimension(700, 100));
        p1.setLayout(new BorderLayout());
        p1.setBackground(Color.WHITE);
        add(p1, BorderLayout.NORTH);

        ImageIcon logo = new ImageIcon("src/lib/files/bray_bowl.png");
        bowl = new JLabel(logo);
        p1.add(bowl, BorderLayout.WEST);

        header = new JLabel("3-Ball-Strike Bowling System", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(40.0f));
        p1.add(header, BorderLayout.CENTER);

        ImageIcon logo2 = new ImageIcon("src/lib/files/bowling_help.png");
        help = new JLabel(logo2);
        p1.add(help, BorderLayout.EAST);


        // Add Panel 2
        p2 = new JPanel();
        p2.setPreferredSize(new Dimension(800, 400));
        p2.setBackground(Color.WHITE);

        createTabbedPane(dateSelected);

        p2.add(jtp, BorderLayout.CENTER);

        if(!user.isAdmin()){
            jtp.setEnabledAt(4, false);
            jtp.setEnabledAt(5, false);
            jtp.setToolTipTextAt(4,"Staff tab DISABLED: \nContact admin for access");
            jtp.setToolTipTextAt(5, "TAB DISABLED: \nADMIN access ONLY!!");
        }
        add(p2);

        p3 = new JPanel();
        p3.setPreferredSize(new Dimension(800, 100));
        p3.setBackground(Color.WHITE);

        String userID = "USERNAME: "+user.getUsername();
        System.out.println(userID);
        loggedIn = new JLabel(userID);
        p3.add(loggedIn);

        /*
         Code for Date Setter at Top of page
          */
        mainDatePicker.addActionListener(this);
        dateInTxt = mainDatePicker.getJFormattedTextField();
        dateInTxt.setText(new java.text.SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));
        dateInTxt.setBackground(Color.LIGHT_GRAY);
        Font font = new Font(Font.SERIF, Font.BOLD, 16);
        dateInTxt.setFont(font);
        dateInTxt.setHorizontalAlignment(JTextField.CENTER);
        dateInTxt.setBorder(new LineBorder(Color.DARK_GRAY));

        p3.add(mainDatePicker);

        checkAvailability = new JButton("Check For Availability");
        checkAvailability.addActionListener(this);
        p3.add(checkAvailability);

        logout = new JButton("Logout");
        logout.addActionListener(this);
        p3.add(logout);

        ImageIcon icon = new ImageIcon("src/lib/files/quck_play.png");
        button = new JLabel(icon);
        p3.add(button);

        add(p3, BorderLayout.SOUTH);
        this.setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure to exit this program?", "Close Program?",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    progOps.closeDB();
                    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                }
            }
        });
    }

    public JTabbedPane createTabbedPane(Date d) {
        System.out.println("Inside : createTabbedPane() in MainScreenGUI");
        this.dateSelected = d;

        // Panel for Home Tab
        jp1 = new HomeTab(dateSelected, bookingList, bookingDetailsList, memList, timeSlotList, laneList, progOps);
        jp1.setPreferredSize(new Dimension(800, 310));
        jp1.setBackground(Color.WHITE);

        // Panel for Book Tab
        jp2 = new JPanel();
        bt = new BookingTab(this, dateSelected, bookingList, memList, laneList, progOps, user);
        jp2.add(bt);
        jp2.setPreferredSize(new Dimension(800, 310));
        jp2.setBackground(Color.WHITE);

        // Panel for Members Tab
        jp3 = new JPanel();
        jp3.add(new MemberTab(memList, progOps));
        jp3.setPreferredSize(new Dimension(800, 310));
        jp3.setBackground(Color.WHITE);

        // Panel for Stock Tab
        jp4 = new JPanel();
        jp4.add(new StockTab(stockList, progOps));
        jp4.setPreferredSize(new Dimension(800, 310));
        jp4.setBackground(Color.WHITE);

        // Panel for Staff Tab
        jp5 = new JPanel();
        jp5.add(new StaffTab(dateSelected, staffList, progOps));
        jp5.setPreferredSize(new Dimension(800, 310));
        jp5.setBackground(Color.WHITE);

        // Panel for Administrator Tab
        jp6 = new JPanel();
        jp6.add(new AdminTab(progOps));
        jp6.setPreferredSize(new Dimension(800, 310));
        jp6.setBackground(Color.WHITE);

        jtp = new JTabbedPane();
        jtp.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Home</body></html>", jp1);
        jtp.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Book</body></html>", jp2);
        jtp.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Members</body></html>", jp3);
        jtp.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Stock</body></html>", jp4);
        jtp.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Staff</body></html>", jp5);
        jtp.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Administrator</body></html>", jp6);

        return jtp;
    }

    public void refreshTabbedPane (Date d) {
        System.out.println("Inside : refreshTabbedPane() in MainScreenGUI");
        this.dateSelected = d;
        Alley a = new Alley(progOps);
        p2.removeAll();
        p2.add(createTabbedPane(d));
        p2.revalidate();
        p2.repaint();

    }


    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in MainScreenGUI");
        if (e.getSource() == checkAvailability) {
            CheckAvailabilityGUI ca = new CheckAvailabilityGUI(this, bt, progOps, bookingList, user);
        }
        else if (e.getSource() == mainDatePanel) {
            System.out.println("HERE");
            juDate = (Date) mainDatePicker.getModel().getValue();
            dt = new DateTime(juDate);

            dateSelected = dt.toDate();
            refreshTabbedPane(dateSelected);
        }
        else if (e.getSource() == logout) {
            if (JOptionPane.showConfirmDialog(null, "Are you sure to logout of this program?", "Logout?",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                LoginGUI ls = new LoginGUI(progOps);
                this.setVisible(false);
            }
        }
    }
}
