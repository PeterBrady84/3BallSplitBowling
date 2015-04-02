package gui;

import db.MainProgramOperations;
import model.*;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import org.joda.time.DateTime;

import javax.swing.*;
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
    private ArrayList<Member> memList;
    private ArrayList<Staff> staffList;
    private ArrayList<Stock> stockList;
    private ArrayList<Lane> laneList;
    private ArrayList<Booking> bookingList = new ArrayList<Booking>();
    public JDatePanelImpl mainDatePanel;
    public JDatePickerImpl mainDatePicker;
    public JFormattedTextField dateInTxt;
    public static String calendarSelected;
    private java.util.Date juDate ;
    private DateTime dt;
    private Format formatter;
    private Alley list;
    private StaffTab staffTable;

    public MainScreen(ArrayList<Member> m, ArrayList<Staff> s, ArrayList<Stock> st, ArrayList<Booking> b, ArrayList<Lane> l, MainProgramOperations po) {
        System.out.println("Inside : MainScreenGUI");


        this.progOps = po;

        this.memList = m;
        list = new Alley(progOps);
        this.staffList = list.getStaffList();
        this.laneList = l;
        this.stockList = st;
        this.bookingList = b;

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

        //This the calendar panel for the mainscreen which sets a date in dd-MMM-yy format
        //String dateSelected can be used by all the tabs for all DB queries.
        UtilDateModel model=new UtilDateModel();
        mainDatePanel = new JDatePanelImpl(model);
        mainDatePicker = new JDatePickerImpl(mainDatePanel);
        juDate = (Date) mainDatePicker.getModel().getValue();
        dt = new DateTime(juDate);
        calendarSelected = dt.toString("dd-MMM-yy");
        calendarSelected = calendarSelected.toUpperCase();
        mainDatePicker.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                juDate = (Date) mainDatePicker.getModel().getValue();
                dt = new DateTime(juDate);
                calendarSelected = dt.toString("dd-MMM-yy");
                calendarSelected = calendarSelected.toUpperCase();
                System.out.println("______________%%%%%%%%%%    DATE  =    "+calendarSelected+"      ________________");
            }
        });
        dateInTxt = mainDatePicker.getJFormattedTextField();
        dateInTxt.setText(new java.text.SimpleDateFormat("dd-MMM-yy").format(new java.util.Date()));
        formatter = new SimpleDateFormat("dd-MMM-yy");

        dateInTxt.addActionListener(this);

        dateInTxt.setBackground(Color.WHITE);
        //p1.add(bowl, BorderLayout.WEST);
        p1.add(mainDatePicker, BorderLayout.WEST);

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

        // Panel for Home Tab
        jp1 = new HomeTab(bookingList, progOps);
        jp1.setPreferredSize(new Dimension(800, 310));
        jp1.setBackground(Color.WHITE);

        // Panel for Book Tab
        jp2 = new JPanel();
        jp2.add(new BookingTab(bookingList, memList, laneList, progOps));
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
        jp5.add(new StaffTab(staffList, progOps));
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
        p2.add(jtp, BorderLayout.CENTER);
        add(p2);

        p3 = new JPanel();
        p3.setPreferredSize(new Dimension(800, 100));
        p3.setBackground(Color.WHITE);

        checkAvailability = new JButton("Check For Availability");
        checkAvailability.addActionListener(this);
        p3.add(checkAvailability);

        changeUser = new JButton("Change User");
        p3.add(changeUser);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkAvailability) {
            CheckAvailabilityGUI ca = new CheckAvailabilityGUI(progOps);
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
