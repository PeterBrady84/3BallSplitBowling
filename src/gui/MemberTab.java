package gui;

import db.MainProgramOperations;
import controller.TableColumnAdjuster;
import model.Alley;
import model.Member;
import model.NumberValidator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
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

        JTextField details = new JTextField("Members");
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
        Border titled = BorderFactory.createTitledBorder(etched, "Member Options");
        detailsPanel.setBorder(titled);
        Font font1 = new Font("Arial", Font.BOLD, 14);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        create = new JButton("Create New Member");
        create.setFont(font1);
        detailsPanel.add(create);
        create.addActionListener(this);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        edit = new JButton("Edit Member Details");
        edit.setFont(font1);
        detailsPanel.add(edit);
        edit.addActionListener(this);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        delete = new JButton("Delete Member Details");
        delete.setFont(font1);
        detailsPanel.add(delete);
        delete.addActionListener(this);

        buttonPanel.add(topPanel, BorderLayout.NORTH);
        buttonPanel.add(detailsPanel, BorderLayout.CENTER);

        add(buttonPanel, BorderLayout.WEST);

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
        } else if (ae.getSource() == edit) {
            String s = searchMember();
            if (!s.equals("cancel")) {
                UpdateMemberGUI um = new UpdateMemberGUI(this, progOps, s);
            }
        } else if (ae.getSource() == delete) {
            String s = searchMember();
            int option = JOptionPane.showConfirmDialog(null,
                    "Deleting a member will also\ndelete ALL their bookings!!!\n\n Do you wish to continue?.",
                    "WARNING", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                if (!s.equals("cancel")) {
                    progOps.deleteMember(s);
                    memList = new Alley(progOps).getMemberList();
                    refreshTable();
                }
            }
        }
    }
}