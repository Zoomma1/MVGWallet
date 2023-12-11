package com.example.demo;

import Entity.Email.EmailUtility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**/

import javax.mail.internet.MimeMessage;
/**
 * The type Mvg wallet.
 */
public class MVGWallet extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) throws Exception {
        /*launch();*/
        EmailUtility.sendEmail("goebbelsvictor@","MVG Wallet teste","ça fontionne !");
    }
}
