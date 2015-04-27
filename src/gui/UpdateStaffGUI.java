package gui;

import db.MainProgramOperations;
import model.Alley;
import model.NumberValidator;
import model.Staff;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Peter on 10/03/2015.
 */
class UpdateStaffGUI implements ActionListener {
    private final JDialog updateD;
    private final MainProgramOperations progOps;
    private final StaffTab sTab;
    private final GuiElements ge;
    private final JButton updateB;
    private final JButton cancel;

    public UpdateStaffGUI(StaffTab st, MainProgramOperations po, String s) {
        System.out.println("Inside : UpdateStaffGUI");
        this.sTab = st;
        this.progOps = po;

        updateD = new JDialog();
        updateD.setTitle("Update Staff");
        updateD.setSize(new Dimension(300, 400));
        updateD.setLocationRelativeTo(null);

        ge = new GuiElements();
        JPanel updatePanel = ge.staffGui();

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
        System.out.println("Inside : fillFields() in UpdateStaffGUI");
        try {
            ResultSet rSet = progOps.searchStaff(s);
            while (rSet.next()) {
                ge.idTxt.setText(Integer.toString(rSet.getInt(1)));
                ge.lNameTxt.setText(rSet.getString(2));
                ge.fNameTxt.setText(rSet.getString(3));
                ge.phoneTxt.setText(rSet.getString(5));
                ge.loginTxt.setText(rSet.getString(6));
                ge.emailTxt.setText(rSet.getString(7));
            }
        } catch (Exception ignored) {
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in UpdateStaffGUI");
        NumberValidator numValidator = new NumberValidator();
        if (e.getSource().equals(updateB)) {
            try {
                if (ge.fNameTxt.getText().equals("") || ge.lNameTxt.getText().equals("") || ge.phoneTxt.getText().equals("") ||
                        ge.loginTxt.getText().equals("") || ge.passwordTxt.getPassword().equals("") || ge.confPassTxt.getPassword().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Fields cannot be blank!\n" +
                                    "Please input all details.", "ERROR", JOptionPane.WARNING_MESSAGE);
                } else if (!(Arrays.equals(ge.passwordTxt.getPassword(), ge.confPassTxt.getPassword()))) {
                    JOptionPane.showMessageDialog(null,
                            "Passwords do not match, please retry", "ERROR", JOptionPane.WARNING_MESSAGE);
                    ge.passwordTxt.setText("");
                    ge.confPassTxt.setText("");
                }
                else if (!ge.secAnsTxt.getText().equals(ge.confSecAnsTxt.getText())) {
                    JOptionPane.showMessageDialog(null,
                            "Security Answers do not match, please retry", "ERROR", JOptionPane.WARNING_MESSAGE);
                    ge.secAnsTxt.setText("");
                    ge.confSecAnsTxt.setText("");
                }
                else {
                    String fName = ge.fNameTxt.getText();
                    String lName = ge.lNameTxt.getText();
                    String phone = ge.phoneTxt.getText();
                    String email = ge.emailTxt.getText();
                    String login = ge.loginTxt.getText();
                    char [] password = ge.passwordTxt.getPassword();
                    String secQuestion = ge.quest.getSelectedItem().toString();
                    String secAnswer = ge.secAnsTxt.getText();
                    String access = "N";
                    if (numValidator.isNumeric(phone)) {
                        progOps.updateStaffinDB(ge.idTxt.getText(), lName, fName, phone, login, email, password, secQuestion, secAnswer);
                        Alley a = new Alley(progOps);
                        a.updateStaff(new Staff(lName, fName, phone, login, email, password, secQuestion, secAnswer, access));
                        ArrayList<Staff> sList = a.getStaffList();
                        JOptionPane.showMessageDialog(null, "Updated Staff Data Saved");
                        updateD.dispose();
                        sTab.refreshTable();
                        sTab.fillTable(sList);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Phone Field must be numeric", "ERROR", JOptionPane.WARNING_MESSAGE);
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
