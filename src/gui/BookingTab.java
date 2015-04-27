package gui;

import db.MainProgramOperations;
import controller.TableColumnAdjuster;
import model.*;
import org.joda.time.DateTime;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Peter on 06/03/2015.
 */
class BookingTab extends JPanel implements ActionListener {

    private static String[] cols = {"Booking Id", "Name", "Date", "Time", "Players"};
    private MainScreen ms;
    private final JButton create;
    private final JButton edit;
    private final JButton delete;
    private JTextField bookingId;
    private DefaultTableModel model;
    private final JTable table;
    private final MainProgramOperations progOps;
    private ArrayList<Booking> bookingList = new ArrayList<>();
    private final Staff user;
    private int idToDelete, i = 0;

    public BookingTab(MainScreen ms, ArrayList<Booking> b, MainProgramOperations po, Staff user) {
        System.out.println("Inside : BookingTabGUI");
        this.progOps = po;
        this.ms = ms;
        this.bookingList = b;
        this.user = user;
        this.setPreferredSize(new Dimension(780, 300));
        this.setLayout(new FlowLayout());
        this.setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(220, 290));
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(Color.WHITE);

        Border etched = BorderFactory.createEtchedBorder();
        buttonPanel.setBorder(etched);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBorder(BorderFactory.createEtchedBorder());
        topPanel.setBackground(Color.WHITE);

        JTextField details = new JTextField("Bookings");
        Font font = new Font(Font.SERIF, Font.ITALIC | Font.BOLD, 40);
        details.setFont(font);
        details.setBackground(Color.WHITE);
        details.setEditable(false);
        details.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        topPanel.add(details, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(7, 1));
        detailsPanel.setBackground(Color.white);

        etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Booking Options");
        detailsPanel.setBorder(titled);
        Font font1 = new Font("Arial", Font.BOLD, 14);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        create = new JButton("Create Reservation");
        create.setFont(font1);
        detailsPanel.add(create);
        create.addActionListener(this);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        edit = new JButton("Edit Reservation");
        edit.setFont(font1);
        detailsPanel.add(edit);
        edit.addActionListener(this);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        delete = new JButton("Delete Reservation");
        delete.setFont(font1);
        detailsPanel.add(delete);
        delete.addActionListener(this);

        buttonPanel.add(topPanel, BorderLayout.NORTH);
        buttonPanel.add(detailsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);

        JPanel p2 = new JPanel();
        p2.setPreferredSize(new Dimension(520, 295));
        p2.setBackground(Color.WHITE);
        String[] header = new String[]{"ID", "Lane", "Surname", "First Name", "Date", "Start Time", "End Time"};
        model = new DefaultTableModel(null, header) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                comp.setBackground(row % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                return comp;
            }
        };

        table.getTableHeader().setReorderingAllowed(false);

        int row = table.getSelectedRow();
        int col = 0; // ID is the first Column

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (i == 0) {
                    if (event.getValueIsAdjusting() == false) {
                        idToDelete = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                        System.out.println("id to delete is " + idToDelete);
                        int reply = JOptionPane.showConfirmDialog(null, "Delete Booking number " + idToDelete
                                , "Confirm Delete", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            progOps.deleteBooking(idToDelete);
                            JOptionPane.showMessageDialog(null, "Booking deleted");
                        }
                        i++;
                        refreshTable();
                    }
                }
            }
        });

        //fillTable();
        refreshTable();

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();

        table.setColumnSelectionAllowed(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane sp = new JScrollPane();
        sp.setViewportView(table);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(500, 290));
        sp.setBackground(Color.WHITE);
        p2.add(sp);
        add(p2, BorderLayout.EAST);
    }

    private void fillTable() {
        System.out.println("Inside : fillTable() in BookingTabGUI");
        ResultSet rSet = progOps.getBookingDataForBookingTab();
        try {
            while (rSet.next()) {
                String bookingId = Integer.toString(rSet.getInt(1));
                String laneName = "Lane " + rSet.getInt(2);
                String lName = rSet.getString(3);
                String fName = rSet.getString(4);
                String date = new java.text.SimpleDateFormat("dd-MMM-yyyy").format(rSet.getDate(5));
                String start = rSet.getString(6);
                String end = rSet.getString(7);
                int players = rSet.getInt(8);

                model.addRow(new Object[]{bookingId, laneName, lName, fName, date, start, end, players});
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void fillTableForDeletion(int x, String y) {
        System.out.println("Inside : fillTable() in BookingTabGUI");
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ResultSet rSet = progOps.getBookingForDeletion(x, y);
        try {
            while (rSet.next()) {
                int bookingid = rSet.getInt(1);
                String laneName = "Lane " + rSet.getInt(2);
                String lName = rSet.getString(3);
                String fName = rSet.getString(4);
                String date = new java.text.SimpleDateFormat("dd-MMM-yyyy").format(rSet.getDate(5));
                String start = rSet.getString(6);
                String end = rSet.getString(7);

                model.addRow(new Object[]{bookingid, laneName, lName, fName, date, start, end});
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void refreshTable() {
        System.out.println("Inside : refreshTable() in BookingTabGUI");

        bookingList.clear();
        bookingList = new Alley(progOps).getBookingList();

        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        fillTable();
    }

    public String searchBooking() {
        System.out.println("Inside : searchBooking() in BookingTabGUI");
        String query = "";
        NumberValidator numValidator = new NumberValidator();
        bookingId = new JTextField();
        Object[] options = {
                "Please Enter -\nBooking Id:", bookingId
        };
        int option = JOptionPane.showConfirmDialog(null, options, "Search Bookings", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (!numValidator.isNumeric(bookingId.getText())) {
                JOptionPane.showMessageDialog(null,
                        "Booking ID must be numeric", "ERROR", JOptionPane.WARNING_MESSAGE);
            } else {
                query = "bookingId = " + bookingId.getText();
            }
        } else {
            query = "cancel";
        }
        return query;
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println("Inside : actionPerformed() in BookingTabGUI");
        if (ae.getSource() == create) {
            CheckAvailabilityGUI ca = new CheckAvailabilityGUI(ms, progOps, bookingList, this, user);
        } else if (ae.getSource() == edit) {
            String s = searchBooking();
            if (!s.equals("cancel")) {
                UpdateBookingGUI ub = new UpdateBookingGUI(this, ms, progOps, bookingList, s);
            }
        } else if (ae.getSource() == delete) {
            i = 0;
            JTextField bookingId = new JTextField();
            JTextField lname = new JTextField();
            Object[] options = {
                    "Please Enter -\nBooking Id:", bookingId,
                    "Or\nSurname:", lname
            };

            int option = JOptionPane.showConfirmDialog(null, options, "Search Bookings", JOptionPane.OK_CANCEL_OPTION);
            int bookId;
            if (bookingId.getText().isEmpty()) {
                bookId = 0;
            } else {
                bookId = Integer.parseInt(bookingId.getText());
            }
            String customer = "XXXX";
            if (lname.getText() != null) {
                customer = lname.getText();
            }
            if (option == JOptionPane.OK_OPTION) {
                fillTableForDeletion(bookId, customer);
            }
        }
    }
}