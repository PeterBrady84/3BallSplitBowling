package test;

import db.MainProgramOperations;
import gui.LoginGUI;

/**
 * Created by Peter on 06/03/2015.
 */
class TestProgram {
    public static void main(String[] args) {
        System.out.println("Inside : TestProgram");

        MainProgramOperations progOps = new MainProgramOperations();
        LoginGUI ls = new LoginGUI(progOps);
    }
}
