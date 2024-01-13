package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Arrays;

/*************************************************************************************
 *This is the StockPageController class it is used to control the StockPage          *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/
public class StockPageController extends NavBarController{
    @FXML
    TilePane tilePane;
    @FXML
    ScrollPane scrollPane;
    AlphaVantageAPITools alphaVantageAPITools = new AlphaVantageAPITools();

    @Override
    public void initialize() throws InterruptedException, IOException {
        try {
            displayStock();
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void dashboardOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "DashboardPage.fxml");
    }

    public void cryptocurrencyOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "CryptocurrencyPage.fxml");
    }

    public void tradesOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "TradesPage.fxml");
    }

    public void overviewOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "OverviewPage.fxml");
    }

    public void myAccountOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "MyAccountPage.fxml");
    }

    public void createNewStockTile(String symbol, String iconUrl, Double price, Double change, String[] sparkline){
        tilePane.setHgap(15);
        tilePane.setVgap(15);
        tilePane.setPrefColumns(4);
        tilePane.getChildren().add((new Tile(symbol,iconUrl,price,change,sparkline)).displayStockTile());
    }

    public void displayStock() throws IOException, InterruptedException, ParseException {
//      todo: get the user followed stocks from sql database
//      todo: this one is for me tho add a button to add a new followed stock
        String[] userFollowedStocks = {"MSFT","AAPL"};

        String symbol;
        String iconUrl = "";
        double price;
        String[] sparkLine;

        for (String toQuery : userFollowedStocks){
            sparkLine = alphaVantageAPITools.getShareSparkline(toQuery,"5min");
            symbol = toQuery;
            price = Double.parseDouble(sparkLine[sparkLine.length - 1]);
            createNewStockTile(symbol,iconUrl,price,-1.0,sparkLine);
        }
    }

}
