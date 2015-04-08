package controller;

import model.Staff;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by User on 13/03/2015.
 */
public class SendMail implements Runnable {

    private Staff recepient;
    String emailContent;

    public SendMail(Staff newStaff) {
        recepient = newStaff;
    }

    @Override
    public void run() {
        emailContent = "Welcome to the team " + recepient.getfName() + "!!\nYour username is " + recepient.getUsername();
        // Recipient's email ID needs to be mentioned.
        String to = "buntmcbunt@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "javabraybowl@gmail.com";

        String host = "pop.gmail.com";

        // Get system properties
        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", from);
        properties.put("mail.smtp.password", "10Pin1990");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "javabraybowl", "10Pin1990");// Specify the Username and the PassWord
            }
        });

        try{
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Welcome new staff member");

            // Now set the actual message
            message.setText(emailContent);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}

