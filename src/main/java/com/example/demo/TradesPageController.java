package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.parser.ParseException;

import java.io.IOException;
/*************************************************************************************
 *This is the TradesPageController class it is used to control the TradesPage        *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/
public class TradesPageController extends NavBarController{
    @FXML
    ChoiceBox tokenBuyChoiceBox;
    @FXML
    ChoiceBox tokenSellChoiceBox;
    @FXML
    ChoiceBox shareBuyChoiceBox;
    @FXML
    ChoiceBox shareSellChoiceBox;
    @FXML
    Label tokenBuyLabel;
    @FXML
    Label tokenBuyPriceLabel;
    @FXML
    Label tokenSellLabel;
    @FXML
    Label tokenSellPriceLabel;
    @FXML
    Label shareBuyLabel;
    @FXML
    Label shareBuyPriceLabel;
    @FXML
    Label shareSellLabel;
    @FXML
    Label shareSellPriceLabel;
    @FXML
    TextField tokenTotalBuyTextField;
    @FXML
    TextField tokenQuantityBuyTextField;
    @FXML
    TextField tokenTotalSellTextField;
    @FXML
    TextField tokenQuantitySellTextField;
    @FXML
    TextField shareTotalBuyTextField;
    @FXML
    TextField shareQuantityBuyTextField;
    @FXML
    TextField shareTotalSellTextField;
    @FXML
    TextField shareQuantitySellTextField;

    CoinrankinAPITools coinrankinAPITools = new CoinrankinAPITools();
    AlphaVantageAPITools alphaVantageAPITools = new AlphaVantageAPITools();
//todo: /!\ Check all todos
//todo: add a warning in case user doesnt have enough to buy or sell
    @Override
    public void initialize() throws InterruptedException, IOException {
//        todo: get user wallet tokens and shares
        tokenBuyChoiceBox.getItems().addAll("BTC"); //todo: add all tokens here
        tokenSellChoiceBox.getItems().addAll("BTC"); //todo: add all tokens here
        shareBuyChoiceBox.getItems().addAll(""); //todo: add all shares here
        shareSellChoiceBox.getItems().addAll(""); //todo: add all shares here
        tokenBuyChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                try {
                    double coinPrice = coinrankinAPITools.getCoinPrice((String) newValue); //todo: here we should get the token uuid instead of tne newValue
                    double conversionRate = 1/coinPrice;

                    tokenBuyLabel.setText(newValue.toString());
                    tokenBuyPriceLabel.setText(String.valueOf(coinPrice));

                    tokenTotalBuyTextField.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                        if(newValue1 != null){
                            tokenQuantityBuyTextField.setText(String.valueOf(coinPrice / Double.parseDouble(newValue1)));
                        }
                    });

                    tokenQuantityBuyTextField.textProperty().addListener((observable2, oldValue2, newValue2) ->{
                        if(newValue2 != null){
                            tokenTotalBuyTextField.setText(String.valueOf(conversionRate * Double.parseDouble(newValue2)));
                        }
                    });
                } catch (IOException | InterruptedException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        tokenSellChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                try {
                    double coinPrice = coinrankinAPITools.getCoinPrice((String) newValue); //todo: here we should get the token uuid instead of tne newValue
                    double conversionRate = 1/coinPrice;

                    tokenSellLabel.setText(newValue.toString());
                    tokenSellPriceLabel.setText(String.valueOf(coinPrice));

                    tokenTotalSellTextField.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                        if(newValue1 != null){
                            tokenQuantitySellTextField.setText(String.valueOf(coinPrice / Double.parseDouble(newValue1)));
                        }
                    });

                    tokenQuantitySellTextField.textProperty().addListener((observable2, oldValue2, newValue2) ->{
                        if(newValue2 != null){
                            tokenTotalSellTextField.setText(String.valueOf(conversionRate * Double.parseDouble(newValue2)));
                        }
                    });
                } catch (IOException | InterruptedException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        tokenSellChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                try {
                    String[] sparkline = alphaVantageAPITools.getShareSparkline((String) newValue, "5min");
                    double sharePrice = Double.parseDouble(sparkline[sparkline.length - 1]);
                    double conversionRate = 1/sharePrice;

                    shareBuyLabel.setText(newValue.toString());
                    shareBuyPriceLabel.setText(String.valueOf(sharePrice));

                    shareTotalBuyTextField.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                        if(newValue1 != null){
                            shareQuantityBuyTextField.setText(String.valueOf(sharePrice / Double.parseDouble(newValue1)));
                        }
                    });

                    shareQuantityBuyTextField.textProperty().addListener((observable2, oldValue2, newValue2) ->{
                        if(newValue2 != null){
                            shareTotalBuyTextField.setText(String.valueOf(conversionRate * Double.parseDouble(newValue2)));
                        }
                    });
                } catch (IOException | InterruptedException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        tokenSellChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                try {
                    String[] sparkline = alphaVantageAPITools.getShareSparkline((String) newValue, "5min");
                    double sharePrice = Double.parseDouble(sparkline[sparkline.length - 1]);
                    double conversionRate = 1/sharePrice;

                    shareSellLabel.setText(newValue.toString());
                    shareSellLabel.setText(String.valueOf(sharePrice));

                    shareTotalSellTextField.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                        if(newValue1 != null){
                            shareQuantitySellTextField.setText(String.valueOf(sharePrice / Double.parseDouble(newValue1)));
                        }
                    });

                    shareQuantitySellTextField.textProperty().addListener((observable2, oldValue2, newValue2) ->{
                        if(newValue2 != null){
                            shareTotalSellTextField.setText(String.valueOf(conversionRate * Double.parseDouble(newValue2)));
                        }
                    });
                } catch (IOException | InterruptedException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void dashboardOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "DashboardPage.fxml");
    }

    public void cryptocurrencyOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "CryptocurrencyPage.fxml");
    }

    public void stockOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "StockPage.fxml");
    }

    public void overviewOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "OverviewPage.fxml");
    }

    public void myAccountOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "MyAccountPage.fxml");
    }

    public void tokenBuyOnAction(ActionEvent event) {
        double price = Double.parseDouble(tokenTotalBuyTextField.getText());
        double quantity = Double.parseDouble(tokenQuantityBuyTextField.getText());
//      todo: check if user have enough to buy
//      todo: remove price from user wallet
//      todo: add quantity to user wallet
    }

    public void tokenSellButonOnAction(ActionEvent event) {
        double price = Double.parseDouble(tokenTotalSellTextField.getText());
        double quantity = Double.parseDouble(tokenQuantitySellTextField.getText());
//      todo: check if user have enough to buy
//      todo: add price from user wallet
//      todo: remove quantity to user wallet
    }

    public void shareBuyButtonOnAction(ActionEvent event) {
        double price = Double.parseDouble(shareTotalBuyTextField.getText());
        double quantity = Double.parseDouble(shareQuantityBuyTextField.getText());
//      todo: check if user have enough to buy
//      todo: remove price from user wallet
//      todo: add quantity to user wallet
    }

    public void shareSellButtonOnAction(ActionEvent event) {
        double price = Double.parseDouble(shareTotalSellTextField.getText());
        double quantity = Double.parseDouble(shareQuantitySellTextField.getText());
//      todo: check if user have enough to buy
//      todo: add price from user wallet
//      todo: remove quantity to user wallet
    }
}
