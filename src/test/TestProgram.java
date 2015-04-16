package test;

import controller.WelcomeAnimation;
import db.MainProgramOperations;
import gui.LoginGUI;
import gui.MainScreen;

/**
 * Created by Peter on 06/03/2015.
 */
public class TestProgram {
    public static void main(String[] args) {
        System.out.println("Inside : TestProgram");

        MainProgramOperations progOps = new MainProgramOperations();
        //WelcomeAnimation hello = new WelcomeAnimation();
        LoginGUI ls = new LoginGUI(progOps);
    }
}
