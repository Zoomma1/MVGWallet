package com.example.demo;

import javafx.event.ActionEvent;
import java.io.IOException;

/**
 * The type Cryptocurrency page controller.
 */
public class CryptocurrencyPageController extends NavBarController{

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
