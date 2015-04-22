package gui;

import db.MainProgramOperations;
import controller.TableColumnAdjuster;
import model.Alley;
import model.NumberValidator;
import model.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created by Peter on 06/03/2015.
 */
class StaffTab extends JPanel implements ActionListener, ItemListener {

    private final JButton create;
    private final JButton edit;
    private final JButton delete;
    private DefaultTableModel model;
    private final JTable table;
    private final MainProgramOperations progOps;
    private ArrayList<Staff> staffList = new ArrayList<>();
    private final String[] TimeHeader = new String[] { "ID", "Name", "Surname", "Bookings", "Start", "Finish"};
    private final String[] ContactHeader = new String[] { "ID", "Name", "Surname", "Bookings", "Phone", "Email"};


    public StaffTab(ArrayList<Staff> s, MainProgramOperations po) {
        System.out.println("Inside : StaffTabGUI");
        this.progOps = po;
        this.staffList = s;

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
        JToggleButton toggle = new JToggleButton("View Contact details", false);
        create = new JButton("Add Staff");
        edit = new JButton("Update Staff");
        delete = new JButton("Delete Staff");

        p1a.add(toggle);
        toggle.addItemListener(this);
        p1a.add(add(Box.createVerticalStrut(20)));
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
        model = new DefaultTableModel(null, TimeHeader) {
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
        refreshTable();

        fillTable(staffList);

        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();

        table.setColumnSelectionAllowed(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane sp = new JScrollPane();
        sp.setBackground(Color.WHITE);
        sp.setViewportView(table);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(500, 290));
        p2.add(sp);
        add(p2, BorderLayout.EAST);
    }

    private void fillTable(ArrayList<Staff> s) {
        System.out.println("Inside : fillTable() in StaffTab");
        this.staffList = s;
        for (Staff aStaffList : staffList) {
            model.addRow(new Object[]{aStaffList.getId(), aStaffList.getlName(), aStaffList.getfName(), aStaffList.getBookings(),
                    aStaffList.getStart(), aStaffList.getFinish()});
        }
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();
    }

    // this is to display contact details
    public void fillTableContact(ArrayList<Staff> s) {
        System.out.println("Inside : fillTableContact() in StaffTabGUI");
        this.staffList = s;
        for (Staff aStaffList : staffList) {
            model.addRow(new Object[]{aStaffList.getId(), aStaffList.getlName(), aStaffList.getfName(),
                    aStaffList.getBookings(), aStaffList.getPhone(), aStaffList.getEmail()});
        }
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();
    }

    public void refreshTable () {
        System.out.println("Inside : refreshTable() in StaffTabGUI");

        staffList.clear();
        Alley a = new Alley(progOps);
        staffList = a.getStaffList();

        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }

    private String searchStaff() {
        System.out.println("Inside : searchStaff() in StaffTabGUI");
        String query = "";
        NumberValidator numValidator = new NumberValidator();
        JTextField staffId = new JTextField();
        JTextField staffName = new JTextField();
        Object[] options = {
                "Please Enter -\nStaff Id:", staffId,
                "Or\nStaff Name:", staffName
        };

        int option = JOptionPane.showConfirmDialog(null, options, "Search Staff", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (numValidator.isNumeric(staffId.getText())) {
                query = "staffId = " + staffId.getText();
            } else if (!numValidator.isNumeric(staffName.getText()) && staffName.getText().contains(" ")) {
                String[] name = staffName.getText().split(" ");
                if (name.length < 2) {
                    throw new IllegalArgumentException("String not in correct format");
                } else {
                    query = "fName = '" + name[0] + "' AND lName = '" + name[1] + "'";
                }
            } else {
                throw new IllegalArgumentException("String " + staffName.getText() + " does not contain a ' ' (space)!");
            }
        }
        else if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
        return query;
    }

    private void updateStaff(String s) {
        System.out.println("Inside : updateStaff() in StaffTabGUI");
        UpdateStaffGUI us = new UpdateStaffGUI(this, progOps, s);
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println("Inside : actionPerformed() in StaffTabGUI");
        if (ae.getSource() == create) {
            AddStaffGUI as = new AddStaffGUI(this, progOps, staffList);
        }
        else if (ae.getSource() == edit) {
            String str = searchStaff();
            updateStaff(str);
        }
        else if (ae.getSource() == delete) {
            String str = searchStaff();
            //progOps.deleteStaff(user, id);
        }
    }

    public void itemStateChanged(ItemEvent ev) {
        System.out.println("Inside : itemStateChanged() in StaffTabGUI");
        if (ev.getStateChange() == ItemEvent.SELECTED) {
            model.setColumnIdentifiers(ContactHeader);
            refreshTable();
            fillTableContact(staffList);
        } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
            model.setColumnIdentifiers(TimeHeader);
            refreshTable();
            fillTable(staffList);
        }
    }

}