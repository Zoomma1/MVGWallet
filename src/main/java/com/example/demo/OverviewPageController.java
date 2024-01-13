package com.example.demo;

import javafx.event.ActionEvent;
import java.io.IOException;
/*************************************************************************************
 *This is the OverviewPageController class it is used to control the OverviewPage    *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/
public class OverviewPageController extends NavBarController{

    public void dashboardOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "DashboardPage.fxml");
    }

    public void cryptocurrencyOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "CryptocurrencyPage.fxml");
    }

    public void stockOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "StockPage.fxml");
    }

    public void tradesOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "TradesPage.fxml");
    }

    public void myAccountOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "MyAccountPage.fxml");
    }

}
