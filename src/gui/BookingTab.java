package gui;

import db.MainProgramOperations;
import lib.TableColumnAdjuster;
import lib.ModifyDateAndTime;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Peter on 06/03/2015.
 */
public class BookingTab extends JPanel implements ActionListener {

    private static String[] cols = {"Booking Id", "Name", "Date", "Time", "Players"};
    private JPanel p1, p2, p1a;
    private JButton create, edit, delete;
    private DefaultTableModel model;
    private JTable table;
    private MainProgramOperations progOps;
    private ArrayList<Booking> bookingList = new ArrayList<Booking>();
    private ArrayList<Lane> laneList = new ArrayList<Lane>();
    private ArrayList<Member> memList = new ArrayList<Member>();
    private String header[] = new String[] { "Booking Id", "Member Name", "Lane", "Date", "Start Time", "End Time"};
    private JTextField bookingId, bookingName;

    public BookingTab(ArrayList<Booking> b, ArrayList<Member> m, ArrayList<Lane> l, MainProgramOperations po) {
        System.out.println("Inside : BookingTabGUI");
        this.progOps = po;
        this.bookingList = b;
        this.memList = m;
        this.laneList = l;
        this.setPreferredSize(new Dimension(780, 300));
        this.setLayout(new FlowLayout());
        this.setBackground(Color.WHITE);

        p1 = new JPanel();
        p1.setPreferredSize(new Dimension(200, 290));
        p1.setLayout(new BorderLayout());
        p1.setBackground(Color.WHITE);
        p1a = new JPanel();
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
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(delete);
        p1.add(p1a, BorderLayout.SOUTH);
        add(p1, BorderLayout.WEST);

        p2 = new JPanel();
        p2.setPreferredSize(new Dimension(520, 295));
        p2.setBackground(Color.WHITE);
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

        fillTable(bookingList);

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

    public void fillTable(ArrayList<Booking> b) {
        System.out.println("Inside : fillTable() in BookingTabGUI");
        bookingList = b;
        ModifyDateAndTime mdt = new ModifyDateAndTime();
        for (int i = 0; i < bookingList.size(); i ++) {
            try {
                String memName = memList.get(bookingList.get(i).getMemId()).getfName() + " "
                        + memList.get(bookingList.get(i).getMemId()).getlName();
                String lane = laneList.get(bookingList.get(i).getLaneId() - 1).getLaneName();
                String dateStr = bookingList.get(i).getFromDateTime();
                String timeStart = bookingList.get(i).getFromDateTime();
                String timeEnd = bookingList.get(i).getToDateTime();
                model.addRow(new Object[]{bookingList.get(i).getId(), memName, lane,
                        mdt.modifyDateLayout(dateStr), mdt.modifyTimeLayout(timeStart), mdt.modifyTimeLayout(timeEnd)});
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void refreshTable () {
        System.out.println("Inside : refreshTable() in BookingTabGUI");

        bookingList.clear();
        Alley a = new Alley(progOps);
        bookingList = a.getBookingList();

        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        fillTable(bookingList);
    }

    public String searchBooking() {
        System.out.println("Inside : searchBooking() in BookingTabGUI");
        String query = "";
        NumberValidator numValidator = new NumberValidator();
        bookingId = new JTextField();
        bookingName = new JTextField();
        Object[] options = {
                "Please Enter -\nMember Id:", bookingId,
                "Or\nMember Name:", bookingName
        };

        int option = JOptionPane.showConfirmDialog(null, options, "Search Members", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (numValidator.isNumeric(bookingId.getText())) {
                query = "memId = " + bookingId.getText();
            } else if (numValidator.isNumeric(bookingName.getText()) == false && bookingName.getText().contains(" ")) {
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
        else if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
        return query;
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println("Inside : actionPerformed() in BookingTabGUI");
        if (ae.getSource() == create) {
            AddBookingGUI ab = new AddBookingGUI(this, progOps, bookingList);
        }
        else if (ae.getSource() == edit) {
            String s = searchBooking();
            //UpdateMemberGUI um = new UpdateMemberGUI(this, progOps, bookingList, s);
        }
    }
}