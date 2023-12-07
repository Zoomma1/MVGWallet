package com.example.demo;

import javafx.event.ActionEvent;
import java.io.IOException;

/**
 * The type Stock page controller.
 */
public class StockPageController extends NavBarController{

    /**
     * Dashboard on action.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void dashboardOnAction(ActionEvent event) throws IOException {
        switchPage(event, "DashboardPage.fxml");
    }

    /**
     * Cryptocurrency on action.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void cryptocurrencyOnAction(ActionEvent event) throws IOException {
        switchPage(event, "CryptocurrencyPage.fxml");
    }

    /**
     * Trades on action.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void tradesOnAction(ActionEvent event) throws IOException {
        switchPage(event, "TradesPage.fxml");
    }

    /**
     * Overview on action.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void overviewOnAction(ActionEvent event) throws IOException {
        switchPage(event, "OverviewPage.fxml");
    }

    /**
     * My account on action.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void myAccountOnAction(ActionEvent event) throws IOException {
        switchPage(event, "MyAccountPage.fxml");
    }
}
