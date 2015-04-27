package gui;

import db.MainProgramOperations;
import controller.TableColumnAdjuster;
import model.Alley;
import model.NumberValidator;
import model.Staff;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setPreferredSize(new Dimension(220, 290));
        buttonPanel.setBackground(Color.WHITE);

        Border etched = BorderFactory.createEtchedBorder();
        buttonPanel.setBorder(etched);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBorder(BorderFactory.createEtchedBorder());
        topPanel.setBackground(Color.WHITE);

        JTextField details = new JTextField("Staff ");
        Font font = new Font(Font.SERIF, Font.ITALIC | Font.BOLD, 40);
        details.setFont(font);
        details.setBackground(Color.WHITE);
        details.setEditable(false);
        details.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        topPanel.add(details, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(8, 1));
        detailsPanel.setBackground(Color.white);

        etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Staff Options");
        detailsPanel.setBorder(titled);
        Font font1 = new Font("Arial", Font.BOLD, 14);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        JToggleButton toggle = new JToggleButton("View Contact Details", false);
        toggle.setFont(font1);
        detailsPanel.add(toggle);
        toggle.addItemListener(this);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        create = new JButton("Add New Staff");
        create.setFont(font1);
        detailsPanel.add(create);
        create.addActionListener(this);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        edit = new JButton("Edit Staff Details");
        edit.setFont(font1);
        detailsPanel.add(edit);
        edit.addActionListener(this);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        delete = new JButton("Delete Staff Details");
        delete.setFont(font1);
        detailsPanel.add(delete);
        delete.addActionListener(this);

        buttonPanel.add(topPanel, BorderLayout.NORTH);
        buttonPanel.add(detailsPanel, BorderLayout.CENTER);

        add(buttonPanel, BorderLayout.WEST);

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

    public void fillTable(ArrayList<Staff> s) {
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
        staffList = new Alley(progOps).getStaffList();

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
                    query = "lName = '" + name[0] + "' AND fName = '" + name[1] + "'";
                }
            } else {
                throw new IllegalArgumentException("String " + staffName.getText() + " does not contain a ' ' (space)!");
            }
        }
        else {
            query = "cancel";
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
        } else if (ae.getSource() == edit) {
            String str = searchStaff();
            if (!str.equals("cancel")) {
                updateStaff(str);
            }
        } else if (ae.getSource() == delete) {
            String str = searchStaff();
            if (!str.equals("cancel")) {
                progOps.deleteStaff(str);
                refreshTable();
                fillTable(staffList);
                JOptionPane.showMessageDialog(null, "Staff Record Removed!");
            }
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