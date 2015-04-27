package gui;

import db.MainProgramOperations;
import model.Alley;
import model.EmailValidator;
import model.Member;
import model.NumberValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

/**
 * Created by Peter on 09/03/2015.
 */
class UpdateMemberGUI implements ActionListener{
    private final JDialog updateD;
    private final MainProgramOperations progOps;
    private final MemberTab mTab;
    private final GuiElements ge;
    private final JButton updateB;
    private final JButton cancel;

    public UpdateMemberGUI(MemberTab mt, MainProgramOperations po, String s) {
        System.out.println("Inside : UpdateMemberGUI");
        this.progOps = po;
        this.mTab = mt;

        updateD = new JDialog();
        updateD.setTitle("Update Member");
        updateD.setSize(new Dimension(300, 400));
        updateD.setLocationRelativeTo(null);

        ge = new GuiElements();
        JPanel updatePanel = ge.membersGui();

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
        System.out.println("Inside : fillFields() in UpdateMemberGUI");
        try {
            ResultSet rSet = progOps.searchMembers(s);
            while (rSet.next()) {
                ge.idTxt.setText(Integer.toString(rSet.getInt(1)));
                ge.lNameTxt.setText(rSet.getString(2));
                ge.fNameTxt.setText(rSet.getString(3));
                String gender = rSet.getString(4);
                if (gender .equals("M")) {
                    ge.male.setSelected(true);
                }
                else if (gender .equals("F")) {
                    ge.female.setSelected(true);
                }
                ge.phoneTxt.setText(rSet.getString(5));
                ge.emailTxt.setText(rSet.getString(6));
                ge.addTxt.setText(rSet.getString(7));
                ge.townTxt.setText(rSet.getString(8));
                ge.coCombo.setSelectedItem(rSet.getString(9));
                ge.visTxt.setText(Integer.toString(rSet.getInt(10)));
            }
        } catch (Exception ignored) {
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in UpdateMemberGUI");
        EmailValidator emailValidator = new EmailValidator();
        NumberValidator numValidator = new NumberValidator();
        if (e.getSource().equals(updateB)) {
            try {
                String gender = "";
                if (ge.male.isSelected()) {
                    gender = "M";
                }
                else if (ge.female.isSelected()) {
                    gender = "F";
                }
                if (ge.fNameTxt.getText().equals("") || ge.lNameTxt.getText().equals("") || gender.equals("") || ge.phoneTxt.getText().equals("") ||
                        ge.emailTxt.getText().equals("") || ge.addTxt.getText().equals("") || ge.townTxt.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Fields cannot be blank");
                } else if (emailValidator.validate(ge.emailTxt.getText())) {
                    JOptionPane.showMessageDialog(null, "Email address is not valid", "ERROR", JOptionPane.WARNING_MESSAGE);
                } else {
                    String fName = ge.fNameTxt.getText();
                    String lName = ge.lNameTxt.getText();
                    String phone = ge.phoneTxt.getText();
                    String email = ge.emailTxt.getText();
                    String add = ge.addTxt.getText();
                    String town = ge.townTxt.getText();
                    String co = ge.coCombo.getSelectedItem().toString();

                    if (!numValidator.isNumeric(fName) && !numValidator.isNumeric(lName) && numValidator.isNumeric(phone)
                            && !numValidator.isNumeric(email) && !numValidator.isNumeric(add) && !numValidator.isNumeric(town)) {
                        progOps.updateMember(ge.idTxt.getText(), fName, lName, gender, phone, email, add, town, co);
                        Alley a = new Alley(progOps);
                        a.updateMember(new Member(fName, lName, gender, phone, email, add, town, co));
                        JOptionPane.showMessageDialog(null, "Updated Member Data Saved", "Member Updated", JOptionPane.WARNING_MESSAGE);
                        updateD.dispose();
                        mTab.refreshTable();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Only Phone Field can be numeric", "ERROR", JOptionPane.WARNING_MESSAGE);
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