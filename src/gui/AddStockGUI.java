package gui;

import db.MainProgramOperations;
import model.Alley;
import model.NumberValidator;
import model.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Peter on 19/03/2015.
 */
class AddStockGUI implements ActionListener {

    private final JDialog addD;
    private final MainProgramOperations progOps;
    private final GuiElements ge;
    private final StockTab sTab;
    private final JButton addB;
    private final JButton clearB;
    private final JButton cancelB;

    public AddStockGUI(StockTab st, MainProgramOperations po, ArrayList<Stock> s) {
        this.sTab = st;
        this.progOps = po;
        System.out.println("Inside : Add Stock GUI");

        addD = new JDialog();
        addD.setTitle("Add Stock Items");
        addD.setSize(new Dimension(300, 220));
        addD.setLocationRelativeTo(null);

        ge = new GuiElements();
        JPanel addPanel = ge.stockGui();
        ge.idTxt.setText(Integer.toString(s.size() + 1));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.setBackground(Color.white);

        addB = new JButton("Add");
        addB.addActionListener(this);
        bottomPanel.add(addB);

        clearB = new JButton("Clear");
        clearB.addActionListener(this);
        bottomPanel.add(clearB);

        cancelB = new JButton("Cancel");
        cancelB.addActionListener(this);
        bottomPanel.add(cancelB);

        addPanel.add(bottomPanel, BorderLayout.SOUTH);

        addPanel.setBackground(Color.white);
        addD.add(addPanel);
        addD.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : actionPerformed() in AddStockGUI");
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
                    if (!numValidator.isNumeric(shoeSize) && numValidator.isNumeric(quantity) &&
                            !numValidator.isNumeric(details)) {
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
        else if (e.getSource() .equals(clearB)) {
            ge.sizeTxt.setText("");
            ge.qtyTxt.setText("");
            ge.detailsTxt.setText("");
        }
        else if (e.getSource() .equals(cancelB)) {
            addD.dispose();
        }
    }
}
