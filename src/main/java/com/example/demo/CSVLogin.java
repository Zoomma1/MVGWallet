package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.Scanner;

public class CSVLogin {
    private String filePath = "src/main/resources/com/example/demo/LoginDatas.csv";

    public CSVLogin() {
    }

    private String[] getData(){
        String lineIn = "";
        StringBuilder fileLines = new StringBuilder();
        String out = "";
        try{
            File file = new File(filePath);
            Scanner fileIn = new Scanner(file);
            while (fileIn.hasNextLine()){
                lineIn = fileIn.nextLine();
                if (!lineIn.equals("")) {
                    fileLines.append(lineIn).append("\n");
                }
            }
            out = fileLines.toString();
            fileIn.close();
        }
        catch (IOException e){
            System.out.println("File not found !" + e.getMessage());
        }
        return out.split("\n");
    }

    public void setNewUserData(String newUserData){
            File file = new File(filePath);
            try{
                if (!file.exists()) {
                    file.createNewFile();
                }
                String[] data = this.getData();
                String[] dataToWrite = new String[data.length+1];
                for(int i = 0; i < data.length; i++) {
                    dataToWrite[i]=data[i];
                }
                dataToWrite[data.length] = newUserData;
                PrintWriter printWriter = new PrintWriter(filePath);
                for (String lineToWrite : dataToWrite) {
                    printWriter.println(lineToWrite);
                }
                printWriter.close();
            } catch (IOException e){
                System.err.println("An error as occurred :" + e.getMessage());
            }
    }

    public Boolean isUserCorrect(String username, String password) {
        String[] data = this.getData();
        for (String currentLine : data) {
            if (!currentLine.isEmpty() && currentLine.split(",")[0].equals(username) && currentLine.split(",")[2].equals(encryptPassword(password))) {
                return true;
            }
        }
        return false;
    }

    public String encryptPassword(String password){
        String encyptedPassword = null;
        try
        {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes());
            byte[] bytes = m.digest();
            StringBuilder s = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encyptedPassword = s.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return encyptedPassword;
    }
}