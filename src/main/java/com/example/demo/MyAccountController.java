package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type My account controller.
 */
public class MyAccountController extends NavBarController{

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
     * Overview on action.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void overviewOnAction(ActionEvent event) throws IOException {
        switchPage(event, "OverviewPage.fxml");
    }
}
