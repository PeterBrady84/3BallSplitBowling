package gui;

import db.MainProgramOperations;
import controller.TableColumnAdjuster;
import model.Alley;
import model.Member;
import model.NumberValidator;

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
class MemberTab extends JPanel implements ActionListener {

    private final JButton create;
    private final JButton edit;
    private final JButton delete;
    private DefaultTableModel model;
    private final JTable table;
    private ArrayList<Member> memList = new ArrayList<>();
    private final MainProgramOperations progOps;

    public MemberTab(ArrayList<Member> m, MainProgramOperations po) {
        System.out.println("Inside : MemberTabGUI");
        this.progOps = po;
        this.memList = m;

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
        create = new JButton("Create New Member");
        edit = new JButton("Edit Member");
        delete = new JButton("Delete Member");

        p1a.add(create);
        create.addActionListener(this);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(edit);
        edit.addActionListener(this);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(delete);
        delete.addActionListener(this);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1.add(p1a, BorderLayout.SOUTH);
        add(p1, BorderLayout.WEST);

        JPanel p2 = new JPanel();
        p2.setPreferredSize(new Dimension(520, 295));
        p2.setBackground(Color.WHITE);
        String[] header = new String[]{"Member Id", "Surname", "Name", "Gender", "Phone", "Email",
                "Address 1", "Town/City", "County", "No Visits"};
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

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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

    private void fillTable(ArrayList<Member> m) {
        System.out.println("Inside : fillTable() in MemberTabGUI");
        memList = m;
        for (Member aMemList : memList) {
            model.addRow(new Object[]{aMemList.getId(), aMemList.getlName(), aMemList.getfName(),
                    aMemList.getGender(), aMemList.getPhone(), aMemList.getEmail(), aMemList.getAddress(),
                    aMemList.getTown(), aMemList.getCounty(), aMemList.getNumVisits()});
        }
    }

    public void refreshTable () {
        System.out.println("Inside : refreshTable() in MemberTabGUI");

        memList.clear();
        memList = new Alley(progOps).getMemberList();

        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        fillTable(memList);
    }

    private String searchMember() {
        System.out.println("Inside : searchMember() in MemberTabGUI");
        String query;
        NumberValidator numValidator = new NumberValidator();
        JTextField memId = new JTextField();
        JTextField memName = new JTextField();
        Object[] options = {
                "Please Enter -\nMember Id:", memId,
                "Or\nMember Name:", memName
        };

        int option = JOptionPane.showConfirmDialog(null, options, "Search Members", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (numValidator.isNumeric(memId.getText())) {
                query = "memberId = " + memId.getText();
            } else if (!numValidator.isNumeric(memName.getText()) && memName.getText().contains(" ")) {
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
        else {
            query = "cancel";
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
            if (!s.equals("cancel")) {
                UpdateMemberGUI um = new UpdateMemberGUI(this, progOps, s);
            }
        }
        else if (ae.getSource() == delete) {
            String s = searchMember();
            if (!s.equals("cancel")) {
                progOps.deleteMember(s);
                refreshTable();
            }
        }
    }
}