package gui;

import db.MainProgramOperations;
import model.Alley;
import model.NumberValidator;
import model.Staff;
import model.Stock;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Peter on 19/03/2015.
 */
public class AddStockGUI implements ActionListener {

    private JDialog addD;
    private MainProgramOperations progOps;
    private GuiElements ge;
    private StockTab sTab;
    private ArrayList<Stock> stockList = new ArrayList<Stock>();
    private JPanel addPanel, bottomPanel;
    private JButton addB, clearB;

    public AddStockGUI(StockTab st, MainProgramOperations po, ArrayList<Stock> s) {
        this.sTab = st;
        this.progOps = po;
        this.stockList = s;
        System.out.println("Add Stock GUI");

        addD = new JDialog();
        addD.setTitle("Add Stock Items");
        addD.setSize(new Dimension(300, 400));
        addD.setLocationRelativeTo(null);

        ge = new GuiElements();
        addPanel = ge.stockGui();
        ge.idTxt.setText(Integer.toString(stockList.size() + 1));

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.setBackground(Color.white);

        addB = new JButton("Add");
        addB.addActionListener(this);
        bottomPanel.add(addB);

        clearB = new JButton("Clear");
        clearB.addActionListener(this);
        bottomPanel.add(clearB);

        addPanel.add(bottomPanel, BorderLayout.SOUTH);

        addPanel.setBackground(Color.white);
        addD.add(addPanel);
        addD.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        NumberValidator numValidator = new NumberValidator();
        if (e.getSource().equals(addB)) {
            try {
                if (ge.sizeTxt.getText().equals("") || ge.qtyTxt.getText().equals("")
                        || ge.detailsTxt.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Fields cannot be blank");
                } else {
                    String shoeSize = ge.sizeTxt.getText();
                    String quantity = ge.qtyTxt.getText();
                    String details = ge.detailsTxt.getText();
                    if (numValidator.isNumeric(shoeSize) == false && numValidator.isNumeric(quantity) == true &&
                            numValidator.isNumeric(details) == false) {
                        int quantityIn = Integer.parseInt(quantity);
                        Stock s = new Stock(shoeSize, quantityIn, details);
                        progOps.addStock(s);
                        Alley a = new Alley(progOps);
                        a.addStock();
                        sTab.refreshTable();
                        JOptionPane.showMessageDialog(null, "New Stock Data Saved");
                        addD.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Only Quantity Field may be numeric");
                    }
                }
            } catch (NumberFormatException nf) {
                JOptionPane.showMessageDialog(null, "Wrong data format");
            }
        }
    }
}
