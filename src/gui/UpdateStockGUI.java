package gui;

import db.MainProgramOperations;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Peter on 19/03/2015.
 */
public class UpdateStockGUI implements ActionListener {
    private JDialog updateD;
    private ResultSet rSet;
    private MainProgramOperations progOps;
    private StockTab sTab;
    private GuiElements ge;
    private JPanel updatePanel, bottomPanel;
    private JButton updateB, cancel;

    public UpdateStockGUI(StockTab st, MainProgramOperations po, String s) {
        System.out.println("Inside : UpdateStockGUI");
        this.sTab = st;
        this.progOps = po;

        updateD = new JDialog();
        updateD.setTitle("Update Stock");
        updateD.setSize(new Dimension(300, 220));
        updateD.setLocationRelativeTo(null);

        ge = new GuiElements();
        updatePanel = ge.stockGui();

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.add(Box.createRigidArea(new Dimension(40, 0)));
        bottomPanel.setBackground(Color.white);

        updateB = new JButton("Update");
        updateB.addActionListener(this);
        bottomPanel.add(updateB);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        bottomPanel.add(cancel);

        updatePanel.add(bottomPanel, BorderLayout.SOUTH);

        updatePanel.setBackground(Color.white);
        updateD.add(updatePanel);
        updateD.setVisible(true);

        fillFields(s);
    }

    public void fillFields(String s) {
        System.out.println("Inside : fillFields() in UpdateStockGUI");
        try {
            rSet = progOps.searchStock(s);
            while (rSet.next()) {
                ge.idTxt.setText(Integer.toString(rSet.getInt(1)));
                ge.sizeTxt.setText(rSet.getString(2));
                ge.detailsTxt.setText(rSet.getString(4));
                ge.qtyTxt.setText(rSet.getString(3));
            }
        } catch (Exception e) {
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in UpdateStockGUI");
        NumberValidator numValidator = new NumberValidator();
        if (e.getSource().equals(updateB)) {
            try {
                if (ge.sizeTxt.getText().equals("") || ge.detailsTxt.getText().equals("") || ge.qtyTxt.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Fields cannot be blank!\n" +
                                    "Please input all details.", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    String shoesize = ge.sizeTxt.getText();
                    String details = ge.detailsTxt.getText();
                    int quantity = Integer.parseInt(ge.qtyTxt.getText());

                    if (numValidator.isNumeric(shoesize) == false && numValidator.isNumeric(details) == false) {
                        progOps.updateStock(ge.idTxt.getText(), shoesize, details, quantity);
                        Alley a = new Alley(progOps);
                        a.updateStock(new Stock(shoesize, quantity, details));
                        JOptionPane.showMessageDialog(null, "Updated Stock Data Saved");
                        sTab.refreshTable();
                        updateD.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Only quantity Field can be numeric", "ERROR", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (NumberFormatException nf) {
                JOptionPane.showMessageDialog(null, "Wrong data format", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (e.getSource().equals(cancel)) {
            updateD.dispose();
        }
    }
}