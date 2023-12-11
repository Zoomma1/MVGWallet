package com.example.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Entity.Email.Email.
 */
public class Email {
    /**
     * The Entity.Email.Email.
     */
    String email;

    /**
     * Instantiates a new Entity.Email.Email.
     *
     * @param email the email
     */
    public Email(String email) {
        this.email = email;
    }

    /**
     * Is email valid boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Send email.
     *
     * @param email   the email
     * @param message the message
     */
    public void sendEmail(Email email,String message){
        //Add sendEmail method
    }
}
