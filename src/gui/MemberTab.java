package gui;

import db.MainProgramOperations;
import lib.TableColumnAdjuster;
import model.Alley;
import model.Member;
import model.NumberValidator;

import javax.swing.*;
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
public class MemberTab extends JPanel implements ActionListener {

    private JPanel p1, p2, p1a;
    private JButton create, edit, delete, refresh;
    private DefaultTableModel model;
    private JTable table;
    private JTextField memId, memName;
    private ArrayList<Member> memList = new ArrayList<Member>();
    private MainProgramOperations progOps;
    private String header[] = new String[] { "Member Id", "Surname", "Name", "Gender", "Phone", "Email",
            "Address 1", "Town/City", "County", "No Visits"};

    public MemberTab(ArrayList<Member> m, MainProgramOperations po) {
        System.out.println("Inside : MemberTabGUI");
        this.progOps = po;
        this.memList = m;

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
        create = new JButton("Create New Member");
        edit = new JButton("Edit Member");
        delete = new JButton("Search For Member");
        refresh = new JButton("Refresh Member Table");

        p1a.add(create);
        create.addActionListener(this);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(edit);
        edit.addActionListener(this);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(delete);
        delete.addActionListener(this);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(refresh);
        refresh.addActionListener(this);
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

        fillTable(memList);

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

    public void fillTable(ArrayList<Member> m) {
        System.out.println("Inside : fillTable() in MemberTabGUI");
        memList = m;
        for (int i = 0; i < memList.size(); i ++) {
            model.addRow(new Object[]{memList.get(i).getId(), memList.get(i).getlName(), memList.get(i).getfName(),
                    memList.get(i).getGender(), memList.get(i).getPhone(), memList.get(i).getEmail(), memList.get(i).getAddress(),
                    memList.get(i).getTown(), memList.get(i).getCounty(), memList.get(i).getNumVisits()});
        }
    }

    public void refreshTable () {
        System.out.println("Inside : refreshTable() in MemberTabGUI");

        memList.clear();
        Alley a = new Alley(progOps);
        memList = a.getMemberList();

        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        fillTable(memList);
    }

    public String searchMember() {
        System.out.println("Inside : searchMember() in MemberTabGUI");
        String query = "";
        NumberValidator numValidator = new NumberValidator();
        memId = new JTextField();
        memName = new JTextField();
        Object[] options = {
                "Please Enter -\nMember Id:", memId,
                "Or\nMember Name:", memName
        };

        int option = JOptionPane.showConfirmDialog(null, options, "Search Members", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (numValidator.isNumeric(memId.getText())) {
                query = "memId = " + memId.getText();
            } else if (numValidator.isNumeric(memName.getText()) == false && memName.getText().contains(" ")) {
                String[] name = memName.getText().split(" ");
                if (name.length < 2) {
                    throw new IllegalArgumentException("String not in correct format");
                } else {
                    query = "fName = '" + name[0] + "' AND lName = '" + name[1] + "'";
                }
            } else {
                throw new IllegalArgumentException("String " + memName.getText() + " does not contain a ' ' (space)!");
            }
        }
        else if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
            this.setVisible(false);
        }
        return query;
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println("Inside : actionPerformed() in MemberTabGUI");
        if (ae.getSource() == create) {
            AddMemberGUI am = new AddMemberGUI(this, progOps, memList);
        }
        else if (ae.getSource() == edit) {
            String s = searchMember();
            UpdateMemberGUI um = new UpdateMemberGUI(this, progOps, memList, s);
        }
        else if (ae.getSource() == refresh) {
            refreshTable();
        }
    }
}