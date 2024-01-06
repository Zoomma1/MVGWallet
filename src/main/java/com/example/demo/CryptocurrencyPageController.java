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


/*************************************************************************************
 *This is the CryptocurrencyPageController class it is used to control the           *
 * CryptocurrencyPage                                                                *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/
public class CryptocurrencyPageController extends NavBarController{
    @FXML
    TilePane tilePane;
    @FXML
    ScrollPane scrollPane;
    CoinrankinAPITools coinrankinAPITools = new CoinrankinAPITools();

    public void initialize() throws InterruptedException, IOException {
        displayFiftyBestCoins();
    }

    public void dashboardOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "DashboardPage.fxml");
    }

    public void stockOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "StockPage.fxml");
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

    public void createNewCryptoTile(String symbol, String iconUrl, Double price, Double change, String[] sparkline){
        tilePane.setHgap(15);
        tilePane.setVgap(15);
        tilePane.setPrefColumns(4);
        tilePane.getChildren().add((new Tile(symbol,iconUrl,price,change,sparkline)).displayTile());
    }

//  todo: fix responsive on page change (this page is going back to pref size)
    public void displayFiftyBestCoins() throws IOException, InterruptedException {
        String jsonString = coinrankinAPITools.getFiftyBestCoins();
        JSONParser parser = new JSONParser();

        String symbol;
        String iconUrl;
        double price;
        double change;
        String[] sparkLine;

        try {
            JSONObject json = (JSONObject) parser.parse(jsonString);
            JSONArray coinsArray = (JSONArray) ((JSONObject) json.get("data")).get("coins");
            for (Object o : coinsArray) {

                symbol = ((JSONObject) o).get("symbol").toString();
                iconUrl = ((JSONObject) o).get("iconUrl").toString();
                price = Double.parseDouble(((JSONObject) o).get("price").toString());
                change = Double.parseDouble(((JSONObject) o).get("change").toString());

                JSONArray sparklineArray = (JSONArray) ((JSONObject) o).get("sparkline");
                sparkLine = new String[sparklineArray.size()];

                for (int i = 0; i < sparklineArray.size(); i++) {
                    sparkLine[i] = sparklineArray.get(i).toString();
                }
                createNewCryptoTile(symbol,iconUrl,price,change,sparkLine);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
