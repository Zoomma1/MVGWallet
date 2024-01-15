package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
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
        tilePane.getChildren().add(tilePane.getChildren().size() - 1, (new Tile(symbol,iconUrl,price,change,sparkline)).displayStockTile());
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

    public void buttonOnAction(ActionEvent event) throws IOException, InterruptedException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add new stock to follow");
        window.setWidth(600);

        Label errorLabel = new Label("This is not a valid stock");
        errorLabel.setVisible(false);
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(new Font("Liberation Mono",15));

        Label label = new Label();
        label.setText("Stock to add :");
        label.setFont(new Font("Liberation Mono",15));
        label.setTextFill(Color.WHITE);

        TextField textField = new TextField();

        Button button = new Button("Add stock");
        button.setTextFill(Color.WHITE);

        button.setStyle("-fx-background-color: #4990B8; -fx-background-radius: 20");
        button.setOnAction(event1 ->  {
            try {
                if (alphaVantageAPITools.isValidSymbol(textField.getText())){
//                    todo: add the share to the wallet and check if stock isn't already in user wallet
                    String[] sparkLine = alphaVantageAPITools.getShareSparkline(textField.getText(),"5min");
                    String symbol = textField.getText();
                    Double price = Double.parseDouble(sparkLine[sparkLine.length - 1]);
                    createNewStockTile(symbol,"",price,-1.0,sparkLine);
                    window.close();
            }else {
                    errorLabel.setVisible(true);
                }
            } catch (IOException | InterruptedException | ParseException e) {
                throw new RuntimeException(e);
            }
        });


        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: #010831");
        layout.getChildren().addAll(errorLabel, label,textField, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
