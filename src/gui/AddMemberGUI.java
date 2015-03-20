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
import java.util.ArrayList;

/**
 * Created by x00115070 on 27/02/2015.
 */
public class AddMemberGUI implements ActionListener {
    private JDialog addD;
    private MainProgramOperations progOps;
    private ArrayList<Member> memList = new ArrayList<Member>();
    private GuiElements ge;
    private MemberTab mTab;
    private JPanel addPanel, bottomPanel;
    private JButton addB, clearB;

    public AddMemberGUI(MemberTab mt, MainProgramOperations po, ArrayList<Member> m) {
        System.out.println("Inside : AddMemberGUI");
        this.mTab = mt;
        this.progOps = po;
        this.memList = m;

        addD = new JDialog();
        addD.setTitle("Add New Member");
        addD.setSize(new Dimension(300, 400));
        addD.setLocationRelativeTo(null);

        ge = new GuiElements();
        addPanel = ge.membersGui();
        ge.idTxt.setText(Integer.toString(memList.size() + 1));
        ge.visTxt.setText("0");

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
        System.out.println("Inside : ActionPerformed() in AddMemberGUI");
        EmailValidator emailValidator = new EmailValidator();
        NumberValidator numValidator = new NumberValidator();
        if (e.getSource().equals(addB)) {
            try {
                if (ge.fNameTxt.getText().equals("") || ge.lNameTxt.getText().equals("") || ge.genTxt.getText().equals("")
                        || ge.phoneTxt.getText().equals("") || ge.emailTxt.getText().equals("") ||
                        ge.addTxt.getText().equals("") || ge.townTxt.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Fields cannot be blank!\n" +
                                    "Please input all details.", "ERROR", JOptionPane.WARNING_MESSAGE);
                } else if (emailValidator.validate(ge.emailTxt.getText()) == false) {
                    JOptionPane.showMessageDialog(null, "Email address is not valid", "ERROR", JOptionPane.WARNING_MESSAGE);
                } else {
                    String fName = ge.fNameTxt.getText();
                    String lName = ge.lNameTxt.getText();
                    String gender = ge.genTxt.getText();
                    String phone = ge.phoneTxt.getText();
                    String email = ge.emailTxt.getText();
                    String add = ge.addTxt.getText();
                    String town = ge.townTxt.getText();
                    String co = ge.coCombo.getSelectedItem().toString();
                    if (numValidator.isNumeric(fName) == false && numValidator.isNumeric(lName) == false && numValidator.isNumeric(gender) == false &&
                            numValidator.isNumeric(phone) == true && numValidator.isNumeric(email) == false && numValidator.isNumeric(add) == false &&
                            numValidator.isNumeric(town) == false) {
                        Member m = new Member(fName, lName, gender, phone, email, add, town, co);
                        progOps.addMember(m);
                        Alley a = new Alley(progOps);
                        a.addMember();
                        mTab.refreshTable();
                        JOptionPane.showMessageDialog(null, "New Member Data Saved");
                        addD.setVisible(false);
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
            ge.genTxt.setText("");
            ge.phoneTxt.setText("");
            ge.emailTxt.setText("");
            ge.addTxt.setText("");
            ge.townTxt.setText("");
            ge.coCombo.setSelectedItem(0);
        }
    }
}
