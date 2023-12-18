package com.example.demo;

import Entity.Email.EmailUtility;
import Entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import java.util.TreeMap;

/*************************************************************************************
 *This is the application class it is used to launch the application                 *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/

public class MVGWallet extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException, ParseException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent root = loader.load();
        LoginPageController loginPageController = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinHeight(800);
        stage.setMinWidth(900);
        stage.show();
        }

    public static void main(String[] args) {
        launch();
    }
}

import java.util.Objects;

public class MVGWallet extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginPage.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

