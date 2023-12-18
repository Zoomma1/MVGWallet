package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/*************************************************************************************
 *This is the MyAccountController class it is used to control the MyAccountPage      *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/
public class MyAccountController extends NavBarController{

    public void dashboardOnAction(ActionEvent event) throws IOException {
        switchPage(event, "DashboardPage.fxml");
    }

    public void cryptocurrencyOnAction(ActionEvent event) throws IOException {
        switchPage(event, "CryptocurrencyPage.fxml");
    }

    public void stockOnAction(ActionEvent event) throws IOException {
        switchPage(event, "StockPage.fxml");
    }

    public void tradesOnAction(ActionEvent event) throws IOException {
        switchPage(event, "TradesPage.fxml");
    }

    public void overviewOnAction(ActionEvent event) throws IOException {
        switchPage(event, "OverviewPage.fxml");
    }

    public void disconnectOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        LoginPageController loginPageController = loader.getController();
        switchPage(event, "LoginPage.fxml");
    }
}
