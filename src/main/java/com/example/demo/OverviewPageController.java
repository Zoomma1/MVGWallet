package com.example.demo;

import javafx.event.ActionEvent;
import java.io.IOException;

/**
 * The type Overview page controller.
 */
public class OverviewPageController extends NavBarController{

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
     * Stock on action.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void stockOnAction(ActionEvent event) throws IOException {
        switchPage(event, "StockPage.fxml");
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
     * My account on action.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void myAccountOnAction(ActionEvent event) throws IOException {
        switchPage(event, "MyAccountPage.fxml");
    }
}
