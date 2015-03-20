package gui;

import db.MainProgramOperations;
import lib.TableColumnAdjuster;
import model.Alley;
import model.NumberValidator;
import model.Stock;

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
public class StockTab extends JPanel implements ActionListener{

    private JPanel p1, p2, p1a;
    private JButton create, edit, delete;
    private DefaultTableModel model;
    private JTable table;
    private MainProgramOperations progOps;
    private ArrayList<Stock> stockList = new ArrayList<Stock>();
    private String header[] = new String[] { "Stock Id", "Shoe Size", "Description", "Quantity"};
    private JTextField stockId, stockName;

    public StockTab(ArrayList<Stock> st, MainProgramOperations po) {
        System.out.println("Inside : StockTabGUI");
        this.progOps = po;
        this.stockList = st;
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
        create = new JButton("Add Stock");
        create.addActionListener(this);
        edit = new JButton("Edit Stock");
        edit.addActionListener(this);
        delete = new JButton("Remove Stock");
        delete.addActionListener(this);

        p1a.add(create);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(edit);
        p1.add(p1a, BorderLayout.SOUTH);
        add(p1, BorderLayout.WEST);

        p2 = new JPanel();
        p2.setPreferredSize(new Dimension(520, 295));
        p2.setBackground(Color.WHITE);
        model = new DefaultTableModel(0, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                comp.setBackground(row % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                return comp;
            }
        };
        model.setColumnIdentifiers(header);
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);

        fillTable(stockList);

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

    public void fillTable(ArrayList<Stock> s) {
        System.out.println("Inside : fillTable() in StockTabGUI");
        this.stockList = s;
        for (int i = 0; i < stockList.size(); i ++) {
            model.addRow(new Object[]{stockList.get(i).getId(), stockList.get(i).getShoeSize(), stockList.get(i).getDetails(),
                    stockList.get(i).getQuantity()});
        }
    }

    public void refreshTable () {
        System.out.println("Inside : refreshTable() in StockTabGUI");

        stockList.clear();
        Alley a = new Alley(progOps);
        stockList = a.getStockList();

        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        fillTable(stockList);
    }

    public String searchStock() {
        System.out.println("Inside : searchStaff() in StaffTabGUI");
        String query = "";
        NumberValidator numValidator = new NumberValidator();
        stockId = new JTextField();
        stockName = new JTextField();
        Object[] options = {
                "Please Enter -\nStock Id:", stockId,
                "Or\nStaff Name:", stockName
        };

        int option = JOptionPane.showConfirmDialog(null, options, "Search Stock", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (numValidator.isNumeric(stockId.getText()) == true) {
                query = "stockId = " + stockId.getText();
            } else if (numValidator.isNumeric(stockName.getText()) == false) {
                query = "shoeSize = '" + stockName.getText();
            } else {
                throw new IllegalArgumentException("ID must be numeric!");
            }
        }
        else if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
        return query;
    }

    public void updateStock(String s) {
        System.out.println("Inside : updateStock() in StockTabGUI");
        UpdateStockGUI us = new UpdateStockGUI(this, progOps, s);
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println("Inside : actionPerformed() in StockTabGUI");
        if (ae.getSource() == create) {
            AddStockGUI as = new AddStockGUI(this, progOps, stockList);
        }
        else if (ae.getSource() == edit) {
            String str = searchStock();
            updateStock(str);
        }
        else if (ae.getSource() == delete) {
        }
    }
}