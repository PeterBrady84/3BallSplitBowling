package gui;

import db.MainProgramOperations;
import controller.TableColumnAdjuster;
import model.Alley;
import model.NumberValidator;
import model.Stock;

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
class StockTab extends JPanel implements ActionListener{

    private final JButton create;
    private final JButton edit;
    private DefaultTableModel model;
    private final JTable table;
    private final MainProgramOperations progOps;
    private ArrayList<Stock> stockList = new ArrayList<>();

    public StockTab(ArrayList<Stock> st, MainProgramOperations po) {
        System.out.println("Inside : StockTabGUI");
        this.progOps = po;
        this.stockList = st;
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

        JTextField details = new JTextField("Stock");
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
        Border titled = BorderFactory.createTitledBorder(etched, "Stock Options");
        detailsPanel.setBorder(titled);
        Font font1 = new Font("Arial", Font.BOLD, 14);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        create = new JButton("Add New Stock");
        create.setFont(font1);
        detailsPanel.add(create);
        create.addActionListener(this);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        edit = new JButton("Edit Stock");
        edit.setFont(font1);
        detailsPanel.add(edit);
        edit.addActionListener(this);

        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        detailsPanel.add(Box.createRigidArea(new Dimension(100, 20)));

        buttonPanel.add(topPanel, BorderLayout.NORTH);
        buttonPanel.add(detailsPanel, BorderLayout.CENTER);

        add(buttonPanel, BorderLayout.WEST);

        JPanel p2 = new JPanel();
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
        String[] header = new String[]{"Stock Id", "Shoe Size", "Description", "Quantity"};
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

    private void fillTable(ArrayList<Stock> s) {
        System.out.println("Inside : fillTable() in StockTabGUI");
        this.stockList = s;
        for (Stock aStockList : stockList) {
            model.addRow(new Object[]{aStockList.getId(), aStockList.getShoeSize(), aStockList.getDetails(),
                    aStockList.getQuantity()});
        }
    }

    public void refreshTable () {
        System.out.println("Inside : refreshTable() in StockTabGUI");

        stockList.clear();
        stockList = new Alley(progOps).getStockList();

        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        fillTable(stockList);
    }

    private String searchStock() {
        System.out.println("Inside : searchStock() in StockTabGUI");
        String query = "";
        NumberValidator numValidator = new NumberValidator();
        JTextField stockId = new JTextField();
        JTextField stockName = new JTextField();
        Object[] options = {
                "Please Enter -\nStock Id:", stockId,
                "Or\nStaff Name:", stockName
        };

        int option = JOptionPane.showConfirmDialog(null, options, "Search Stock", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (numValidator.isNumeric(stockId.getText())) {
                query = "stockId = " + stockId.getText();
            } else if (!numValidator.isNumeric(stockName.getText())) {
                query = "shoeSize = '" + stockName.getText();
            } else {
                throw new IllegalArgumentException("ID must be numeric!");
            }
        }
        else {
            query = "cancel";
        }
        return query;
    }

    private void updateStock(String s) {
        System.out.println("Inside : updateStock() in StockTabGUI");
        UpdateStockGUI us = new UpdateStockGUI(progOps, s);
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println("Inside : actionPerformed() in StockTabGUI");
        if (ae.getSource() == create) {
            AddStockGUI as = new AddStockGUI(this, progOps, stockList);
        }
        else if (ae.getSource() == edit) {
            String str = searchStock();
            if (!str.equals("cancel")) {
                updateStock(str);
            }
        }
    }
}