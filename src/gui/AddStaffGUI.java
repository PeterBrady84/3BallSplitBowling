package gui;

import controller.SendMail;
import db.MainProgramOperations;
import model.Alley;
import model.NumberValidator;
import model.Staff;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Peter on 10/03/2015.
 */
class AddStaffGUI extends Thread implements ActionListener  {
    private final JDialog addD;
    private final MainProgramOperations progOps;
    private final StaffTab sTab;
    private ArrayList<Staff> staffList = new ArrayList<>();
    private final GuiElements ge;
    private final JButton addB;
    private final JButton clearB;
    private final JButton cancelB;

    public AddStaffGUI(StaffTab st, MainProgramOperations po, ArrayList<Staff> s) {
        this.sTab = st;
        this.progOps = po;
        this.staffList = s;
        System.out.println("Inside : AddStaffGUI");

        addD = new JDialog();
        addD.setTitle("Add New Staff");
        addD.setSize(new Dimension(300, 400));
        addD.setLocationRelativeTo(null);

        ge = new GuiElements();
        JPanel addPanel = ge.staffGui();
        ge.idTxt.setText(Integer.toString(staffList.size() + 1));

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
        System.out.println("Inside : ActionPerformed in AddStaffGUI");
        NumberValidator numValidator = new NumberValidator();
        if (e.getSource().equals(addB)) {
            try {
                if (ge.fNameTxt.getText().equals("") || ge.lNameTxt.getText().equals("") || ge.phoneTxt.getText().equals("") ||
                        ge.loginTxt.getText().equals("") || ge.passwordTxt.getPassword().length == 0 || ge.confPassTxt.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Fields cannot be blank!\n" +
                                    "Please input all details.", "ERROR", JOptionPane.WARNING_MESSAGE);
                } else if (progOps.isUniqueUsername(ge.loginTxt.getText())) {
                    JOptionPane.showMessageDialog(null,
                            "User name is not unique", "ERROR", JOptionPane.WARNING_MESSAGE);
                    ge.secAnsTxt.setText("");
                    ge.confSecAnsTxt.setText("");
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
                    String login = ge.loginTxt.getText();
                    String email = ge.emailTxt.getText();
                    char[] password = ge.passwordTxt.getPassword();
                    String secQuestion = ge.quest.getSelectedItem().toString();
                    String secAnswer = ge.secAnsTxt.getText();
                    String access = "N";
                    if (numValidator.isNumeric(phone)) {
                        Staff s = new Staff(lName, fName, phone, login, email, password, secQuestion, secAnswer, access);
                        new SendMail(s).run();
//                        SendMail registered= new SendMail(s);
                        progOps.addStaff(s);
                        Alley a = new Alley(progOps);
                        a.addStaffLastRow();
                        String t = "contact";
                        JOptionPane.showMessageDialog(null, "New Staff Data Saved");
                        addD.dispose();
                        sTab.refreshTable();
                        sTab.fillTableContact(a.getStaffList());
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Only Phone Field may be numeric", "ERROR", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }catch (NumberFormatException nf) {
                JOptionPane.showMessageDialog(null, "Wrong data format", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (e.getSource().equals(clearB)) {
            ge.fNameTxt.setText("");
            ge.lNameTxt.setText("");
            ge.phoneTxt.setText("");
            ge.loginTxt.setText("");
            ge.passwordTxt.setText("");
            ge.confPassTxt.setText("");
        }
        else if (e.getSource().equals(cancelB)) {
            addD.dispose();
        }
    }
}