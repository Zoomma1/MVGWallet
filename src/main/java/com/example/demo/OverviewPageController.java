package com.example.demo;

import javafx.event.ActionEvent;
import java.io.IOException;

public class OverviewPageController extends NavBarController{

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

    public void myAccountOnAction(ActionEvent event) throws IOException {
        switchPage(event, "MyAccountPage.fxml");
    }
}
