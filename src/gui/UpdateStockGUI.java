package gui;

import db.MainProgramOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Peter on 19/03/2015.
 */
class UpdateStockGUI implements ActionListener {
    private final MainProgramOperations progOps;
    private final GuiElements ge;
    private final JButton updateB;
    private final JButton cancel;

    public UpdateStockGUI(MainProgramOperations po, String s) {
        System.out.println("Inside : UpdateStockGUI");
        this.progOps = po;

        JDialog updateD = new JDialog();
        updateD.setTitle("Update Stock");
        updateD.setSize(new Dimension(300, 400));
        updateD.setLocationRelativeTo(null);

        ge = new GuiElements();
        JPanel updatePanel = ge.stockGui();

        JPanel bottomPanel = new JPanel();
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

    private void fillFields(String s) {
        System.out.println("Inside : fillFields() in UpdateStockGUI");
        try {
            ResultSet rSet = progOps.searchStock(s);
            while (rSet.next()) {
                ge.idTxt.setText(Integer.toString(rSet.getInt(1)));
                ge.sizeTxt.setText(rSet.getString(2));
                ge.detailsTxt.setText(rSet.getString(4));
                ge.qtyTxt.setText(rSet.getString(3));
            }
        } catch (Exception ignored) {
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in UpdateStockGUI");
        if (e.getSource().equals(updateB)) {
            try {
                if (ge.fNameTxt.getText().equals("") || ge.lNameTxt.getText().equals("") || ge.phoneTxt.getText().equals("") ||
                        ge.loginTxt.getText().equals("") || ge.passwordTxt.getPassword().length == 0 || ge.confPassTxt.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Fields cannot be blank!\n" +
                                    "Please input all details.", "ERROR", JOptionPane.WARNING_MESSAGE);
                } else if (!(Arrays.equals(ge.passwordTxt.getPassword(), ge.confPassTxt.getPassword()))) {
                    JOptionPane.showMessageDialog(null,
                            "Passwords do not match, please retry", "ERROR", JOptionPane.WARNING_MESSAGE);
                    ge.passwordTxt.setText("");
                    ge.confPassTxt.setText("");
                } else if (!Objects.equals(ge.secAnsTxt.getText(), ge.confSecAnsTxt.getText())) {
                    JOptionPane.showMessageDialog(null,
                            "Security Answers do not match, please retry", "ERROR", JOptionPane.WARNING_MESSAGE);
                    ge.secAnsTxt.setText("");
                    ge.confSecAnsTxt.setText("");
                } else {
                    String fName = ge.fNameTxt.getText();
                    String lName = ge.lNameTxt.getText();
                    String phone = ge.phoneTxt.getText();
                    String login = ge.loginTxt.getText();
                    char[] password = ge.passwordTxt.getPassword();
                    String secQuestion = ge.quest.getSelectedItem().toString();
                    String secAnswer = ge.secAnsTxt.getText();
                    /*if (numValidator.isNumeric(fName) == false && numValidator.isNumeric(lName) == false && numValidator.isNumeric(phone) == true
                            && numValidator.isNumeric(login) == false && numValidator.isNumeric(secAnswer)) {
                        progOps.updateStaff(ge.idTxt.getText(), fName, lName, phone, login, password, secQuestion, secAnswer);
                        Alley a = new Alley(progOps);
                        a.updateStaff(new Staff(fName, lName, phone, login, password, secQuestion, secAnswer));
                        JOptionPane.showMessageDialog(null, "Updated Staff Data Saved");
                        sTab.refreshTable();
                        updateD.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Only Phone Field can be numeric", "ERROR", JOptionPane.WARNING_MESSAGE);
                    }*/
                }
            } catch (NumberFormatException nf) {
                JOptionPane.showMessageDialog(null, "Wrong data format", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (e.getSource().equals(cancel)) {
            System.exit(0);
        }
    }
}