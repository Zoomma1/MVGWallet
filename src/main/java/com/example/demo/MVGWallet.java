package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

/*************************************************************************************
 *This is the application class it is used to launch the application                 *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/

public class MVGWallet extends Application {
    @Override
    public void start(Stage stage) throws IOException, ParseException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent root = loader.load();
        LoginPageController loginPageController = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinHeight(800);
        stage.setMinWidth(1160);
        stage.show();
        }

    public static void main(String[] args) {
        launch();
    }
}


