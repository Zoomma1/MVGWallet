package com.example.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
//  todo: replace this with the other one that's way better tho
    String email;

    public Email(String email) {
        this.email = email;
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void sendEmail(Email email,String message){
        //todo sendEmail method
    }
}
