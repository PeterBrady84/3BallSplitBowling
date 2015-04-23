package gui;

import db.MainProgramOperations;
import controller.TableColumnAdjuster;
import model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Peter on 06/03/2015.
 */
class BookingTab extends JPanel implements ActionListener {

    private static String[] cols = {"Booking Id", "Name", "Date", "Time", "Players"};
    private MainScreen ms;
    private final JButton create;
    private final JButton edit;
    private final JButton delete;
    private DefaultTableModel model;
    private final JTable table;
    private final MainProgramOperations progOps;
    private ArrayList<Booking> bookingList = new ArrayList<>();
    private final Staff user;
    private int idToDelete;

    public BookingTab(MainScreen ms, ArrayList<Booking> b, MainProgramOperations po, Staff user) {
        System.out.println("Inside : BookingTabGUI");
        this.progOps = po;
        this.ms = ms;
        this.bookingList = b;
        this.user = user;
        this.setPreferredSize(new Dimension(780, 300));
        this.setLayout(new FlowLayout());
        this.setBackground(Color.WHITE);

        JPanel p1 = new JPanel();
        p1.setPreferredSize(new Dimension(200, 290));
        p1.setLayout(new BorderLayout());
        p1.setBackground(Color.WHITE);
        JPanel p1a = new JPanel();
        p1a.setPreferredSize(new Dimension(180, 200));
        p1a.setLayout(new BoxLayout(p1a, BoxLayout.Y_AXIS));
        p1a.setBackground(Color.WHITE);
        create = new JButton("Create Booking");
        edit = new JButton("Edit Booking");
        delete = new JButton("Delete Booking");

        p1a.add(create);
        create.addActionListener(this);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(edit);
        edit.addActionListener(this);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(delete);
        delete.addActionListener(this);
        p1.add(p1a, BorderLayout.SOUTH);
        add(p1, BorderLayout.WEST);

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

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                idToDelete = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                System.out.println("id to delete is "+idToDelete);
                int reply = JOptionPane.showConfirmDialog(null, "Delete Booking number "+idToDelete
                        , "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    progOps.deleteBooking(idToDelete);
                    JOptionPane.showMessageDialog(null, "Booking deleted");
                }
                else {
                    System.exit(0);
                }
            }
        });

        fillTable();

        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
            while(rSet.next()) {
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
        ResultSet rSet = progOps.getBookingforDeletion(x, y);
        try {
            while(rSet.next()) {
                int bookingid = rSet.getInt(1);
                String laneName = "Lane " + rSet.getInt(2);
                String lName = rSet.getString(3);
                String fName = rSet.getString(4);
                String date = new java.text.SimpleDateFormat("dd-MMM-yyyy").format(rSet.getDate(5));
                String start = rSet.getString(6);
                String end = rSet.getString(7);
                //int players = rSet.getInt(8);

                model.addRow(new Object[]{bookingid, laneName, lName, fName, date, start, end});
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void refreshTable () {
        System.out.println("Inside : refreshTable() in BookingTabGUI");

        bookingList.clear();
        Alley a = new Alley(progOps);
        bookingList = a.getBookingList();

        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        fillTable();
    }

    private String searchBooking() {
        System.out.println("Inside : searchBooking() in BookingTabGUI");
        String query;
        NumberValidator numValidator = new NumberValidator();
        JTextField bookingId = new JTextField();
        JTextField bookingName = new JTextField();
        Object[] options = {
                "Please Enter -\nBooking Id:", bookingId,
                "Or\nBooking Name:", bookingName
        };

        int option = JOptionPane.showConfirmDialog(null, options, "Search Bookings", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (numValidator.isNumeric(bookingId.getText())) {
                query = "bookingId = " + bookingId.getText();
            } else if (!numValidator.isNumeric(bookingName.getText()) && bookingName.getText().contains(" ")) {
                String[] name = bookingName.getText().split(" ");
                if (name.length < 2) {
                    throw new IllegalArgumentException("String not in correct format");
                } else {
                    query = "fName = '" + name[0] + "' AND lName = '" + name[1] + "'";
                }
            } else {
                throw new IllegalArgumentException("String " + bookingName.getText() + " does not contain a ' ' (space)!");
            }
        }
        else  {
            query = "cancel";
        }
        return query;
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println("Inside : actionPerformed() in BookingTabGUI");
        if (ae.getSource() == create) {
            CheckAvailabilityGUI ca = new CheckAvailabilityGUI(ms, progOps, bookingList, this, user);
        }
        else if (ae.getSource() == edit) {
            String s = searchBooking();
            if (!s.equals("cancel")) {
                UpdateBookingGUI ub = new UpdateBookingGUI(this, progOps, s);
            }
        }
        else if (ae.getSource() == delete) {
            JTextField bookingId = new JTextField();
            JTextField lname = new JTextField();
            Object[] options = {
                    "Please Enter -\nBooking Id:", bookingId,
                    "Or\nSurname:", lname
            };

            int option = JOptionPane.showConfirmDialog(null, options, "Search Bookings", JOptionPane.OK_CANCEL_OPTION);
            int bookid;
            if(bookingId.getText().isEmpty()){
                bookid = 0;
            } else {
                bookid = Integer.parseInt(bookingId.getText().toString());
            };

            String customer = "XXXX";
            if(lname.getText()!= null){
                customer = lname.getText().toString();
            }
            System.out.println("value of int = " + bookid + " value of y = " + customer);
            if (option == JOptionPane.OK_OPTION) {
                fillTableForDeletion(bookid, customer);
            }

        }
    }
}