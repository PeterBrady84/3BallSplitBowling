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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Peter on 06/03/2015.
 */
public class MainScreen extends JFrame implements ActionListener {

    private final JPanel p2;
    private final JButton bowl;
    private final JButton help;
    private final JButton checkAvailability;
    private final JButton logout;
    private JTabbedPane jtp;
    private final MainProgramOperations progOps;
    private final ArrayList<Member> memList;
    private final ArrayList<Staff> staffList;
    private final ArrayList<Stock> stockList;
    private ArrayList<Booking> bookingList = new ArrayList<>();
    private final JDatePanelImpl mainDatePanel;
    private final JDatePickerImpl mainDatePicker;
    private final JFormattedTextField dateInTxt;
    public static Date dateSelected;
    private java.util.Date juDate ;
    private DateTime dt;
    private final Staff user;

    public MainScreen(Staff user, ArrayList<Member> m, ArrayList<Staff> s, ArrayList<Stock> st, ArrayList<Booking> b,
                      MainProgramOperations po) {
        System.out.println("Inside : MainScreenGUI");

        this.progOps = po;
        this.memList = m;
        this.staffList = s;
        this.stockList = st;
        this.bookingList = b;
        this.user = user;

        //This the calendar panel for the mainscreen which sets a date in dd-MMM-yy format
        //String dateSelected can be used by all the tabs for all DB queries.
        UtilDateModel model = new UtilDateModel();
        mainDatePanel = new JDatePanelImpl(model);
        mainDatePicker = new JDatePickerImpl(mainDatePanel);
        juDate = (Date) mainDatePicker.getModel().getValue();
        dt = new DateTime(juDate);

        dateSelected = dt.toDate();

        setTitle("3-Ball-Strike Bowling");
        setSize(850, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(Color.WHITE);

        // Add panels to Frame
        // Add Panel 1
        JPanel p1 = new JPanel();

        p1.setPreferredSize(new Dimension(700, 100));
        p1.setLayout(new BorderLayout());
        p1.setBackground(Color.WHITE);
        add(p1, BorderLayout.NORTH);

        ImageIcon logo = new ImageIcon("src/lib/files/bray_bowl.png");
        bowl = new JButton(logo);
        bowl.setMargin(new Insets(0, 0, 0, 0));
        bowl.setBackground(Color.WHITE);
        bowl.setBorder(null);
        bowl.setContentAreaFilled(false);
        bowl.setOpaque(true);
        p1.add(bowl, BorderLayout.WEST);
        bowl.addActionListener(this);

        JLabel header = new JLabel("3-Ball-Strike Bowling System", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(40.0f));
        p1.add(header, BorderLayout.CENTER);

        ImageIcon logo2 = new ImageIcon("src/lib/files/bowling_help.png");
        help = new JButton(logo2);
        help.setMargin(new Insets(0, 0, 0, 0));
        help.setBackground(Color.WHITE);
        help.setBorder(null);
        help.setContentAreaFilled(false);
        help.setOpaque(true);
        p1.add(help, BorderLayout.EAST);
        help.addActionListener(this);


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

        JPanel p3 = new JPanel();
        p3.setPreferredSize(new Dimension(800, 100));
        p3.setBackground(Color.WHITE);

        String userID = "USERNAME: "+user.getUsername();
        JLabel loggedIn = new JLabel(userID);
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
        JLabel button = new JLabel(icon);
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

    private JTabbedPane createTabbedPane(Date d) {
        System.out.println("Inside : createTabbedPane() in MainScreenGUI");
        dateSelected = d;

        // Panel for Home Tab
        JPanel jp1 = new HomeTab(dateSelected, progOps);
        jp1.setPreferredSize(new Dimension(800, 310));
        jp1.setBackground(Color.WHITE);

        // Panel for Book Tab
        JPanel jp2 = new JPanel();
        BookingTab bt = new BookingTab(this, bookingList, progOps, user);
        jp2.add(bt);
        jp2.setPreferredSize(new Dimension(800, 310));
        jp2.setBackground(Color.WHITE);

        // Panel for Members Tab
        JPanel jp3 = new JPanel();
        jp3.add(new MemberTab(memList, progOps));
        jp3.setPreferredSize(new Dimension(800, 310));
        jp3.setBackground(Color.WHITE);

        // Panel for Stock Tab
        JPanel jp4 = new JPanel();
        jp4.add(new StockTab(stockList, progOps));
        jp4.setPreferredSize(new Dimension(800, 310));
        jp4.setBackground(Color.WHITE);

        // Panel for Staff Tab
        JPanel jp5 = new JPanel();
        jp5.add(new StaffTab(staffList, progOps));
        jp5.setPreferredSize(new Dimension(800, 310));
        jp5.setBackground(Color.WHITE);

        // Panel for Administrator Tab
        JPanel jp6 = new JPanel();
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

    private void refreshTabbedPane(Date d) {
        System.out.println("Inside : refreshTabbedPane() in MainScreenGUI");
        dateSelected = d;
        Alley a = new Alley(progOps);
        p2.removeAll();
        p2.add(createTabbedPane(d));
        p2.revalidate();
        p2.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in MainScreenGUI");
        if (e.getSource() == checkAvailability) {
            CheckAvailabilityGUI ca = new CheckAvailabilityGUI(progOps, bookingList, user);
        }
        else if (e.getSource() == mainDatePanel) {
            juDate = (Date) mainDatePicker.getModel().getValue();
            dt = new DateTime(juDate);

            dateSelected = dt.toDate();
            //if (dateSelected.before(new java.util.Date())) {
                //dateSelected = new java.util.Date();
            //}
            refreshTabbedPane(dateSelected);
        }
        else if (e.getSource() == bowl) {
            juDate = new Date();
            dt = new DateTime(juDate);
            dateSelected = dt.toDate();
            dateInTxt.setText(new java.text.SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));
            refreshTabbedPane(dateSelected);
        }
        else if (e.getSource() == help) {
            File helpPDF = new File("src/lib/files/helpManuals/UserManualTest.pdf");
            if (Desktop.isDesktopSupported()) {
                if (jtp.getTitleAt(jtp.getSelectedIndex()).contains("Home"))
                {
                    helpPDF = new File("src/lib/files/helpManuals/home.pdf");
                }
                else if (jtp.getTitleAt(jtp.getSelectedIndex()).contains("Booking"))
                {
                    helpPDF = new File("src/lib/files/helpManuals/booking.pdf");
                }
                else if (jtp.getTitleAt(jtp.getSelectedIndex()).contains("Members"))
                {
                    helpPDF = new File("src/lib/files/helpManuals/members.pdf");
                }
                else if (jtp.getTitleAt(jtp.getSelectedIndex()).contains("Stock"))
                {
                    helpPDF = new File("src/lib/files/helpManuals/stock.pdf");
                }
                else if (jtp.getTitleAt(jtp.getSelectedIndex()).contains("Staff"))
                {
                    helpPDF = new File("src/lib/files/helpManuals/staff.pdf");
                }
                else if (jtp.getTitleAt(jtp.getSelectedIndex()).contains("Administrator")) {
                    helpPDF = new File("src/lib/files/helpManuals/administrator.pdf");
                }
                try {
                    Desktop.getDesktop().open(helpPDF);
                } catch (IOException ex) {
                    System.out.println("Unable to open PDFs");
                }
            }
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
