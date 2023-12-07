package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
}
