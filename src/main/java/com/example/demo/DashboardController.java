package com.example.demo;

import Entity.Singleton;
import Entity.User;
import Entity.Wallet;
import Entity.WalletRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.chart.XYChart;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

/*************************************************************************************
 *This is the DashboardPageController class it is used to control the DashboardPage  *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/
public class DashboardController extends NavBarController{

    @FXML
    TilePane tilePane;
    @FXML
    ScrollPane scrollPane;

    CoinrankinAPITools coinrankinAPITools = new CoinrankinAPITools();
    AlphaVantageAPITools alphaVantageAPITools = new AlphaVantageAPITools();

    public String jsonCoinrankingFiftyBestCoins;

    @Override
    public void initialize(){
        User utilisateur = Singleton.getInstance().getCurrentUser();
        try{
            jsonCoinrankingFiftyBestCoins = coinrankinAPITools.getFiftyBestCoins();
            WalletRepository repo = new WalletRepository();
            ArrayList<String> portefeuilleArray = repo.findByUserId(utilisateur.getId());
            Singleton.getInstance().setListWallet(portefeuilleArray);
            for(String portefeuille : portefeuilleArray){
                walletTileCreation(String.format("Wallet n°%s",User.checkRegex(User.checkRegex(portefeuille,"id: \\d{1,9},"),"\\d{1,9}")));
            }
        }catch (SQLException | NoSuchAlgorithmException | IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
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

    public void overviewOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "OverviewPage.fxml");

    }

    public void myAccountOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "MyAccountPage.fxml");
    }
    public void createNewWalletOnAction() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add new wallet");
        window.setWidth(600);

        Label label = new Label();
        label.setText("Wallet to add :");
        label.setFont(new Font("Liberation Mono",15));
        label.setTextFill(Color.WHITE);

        TextField textField = new TextField();

        Button button = new Button("Add new wallet");
        button.setTextFill(Color.WHITE);

        button.setStyle("-fx-background-color: #4990B8; -fx-background-radius: 20");
        button.setOnAction(event1 ->  {
            String walletName = textField.getText();
            try {
                User utilisateur = Singleton.getInstance().getCurrentUser();
                walletTileCreation(walletName);
                Wallet newWallet = new Wallet(utilisateur.getId(),false);
                newWallet.createWallet();
            } catch (SQLException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: #010831");
        layout.getChildren().addAll(label, textField, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void walletTileCreation(String wallet_id) throws SQLException, NoSuchAlgorithmException {
        User utilisateur = Singleton.getInstance().getCurrentUser();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("name");
        WalletRepository repo = new WalletRepository();
        String walletId = User.checkRegex(repo.findBySelectedAndId(utilisateur.getId()).toString(),"\\d{1,9}");
        ArrayList<String> symboles = WalletRepository.selectWhereStatic(columns,"crypto","id_wallet",walletId);
        ArrayList<String> stocks = WalletRepository.selectWhereStatic(columns,"stocks","id_wallet",walletId);
        ArrayList<String> finalSymboles = new ArrayList<>();
        ArrayList<String> finalStocks = new ArrayList<>();
        for (String symbole: symboles) {
            finalSymboles.add(User.checkRegex(symbole,"[A-Z]+"));
        }
        for (String stock: stocks) {
            finalStocks.add(User.checkRegex(stock,"[A-Z]+"));
        }
//      todo: create wallets tiles using the user owned wallets and display a sparkline base on its value over time (I think we will display them with a one day interval)
/*
        Double[] sparkline = new Double[28];
        for(String sbl: symboles){
            try {
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonData = (JSONObject) jsonParser.parse(jsonCoinrankingFiftyBestCoins);
                JSONArray coinsArray = (JSONArray) ((JSONObject) jsonData.get("data")).get("coins");

                for (Object coinObj : coinsArray) {
                    JSONObject coin = (JSONObject) coinObj;
                    String symbol = (String) coin.get("symbol");

                    if (sbl.equals(symbol)) {
                        JSONArray sparklineArray = (JSONArray) coin.get("sparkline");

                        if (sparkline != null) {
                            for (int i = 0; i < sparkline.length; i++) {
                                sparkline[i]= sparkline[i] + Double.parseDouble(sparklineArray.get(i).toString());
                            }
                            System.out.println(sparklineArray);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String[] shareSparkline;
        for (String stock : stocks){
            try {
                shareSparkline = alphaVantageAPITools.getShareSparkline(stock,"5min");
                System.out.println(shareSparkline);
            } catch (IOException | InterruptedException | ParseException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < shareSparkline.length; i++) {
                sparkline[i] = sparkline[i] + Double.parseDouble(shareSparkline[i]);
            }
        }
        */


        VBox vBox = new VBox();
        VBox vBowWalletValues = new VBox();
        HBox hBox = new HBox();
        boolean isSelected = false;

        tilePane.setHgap(10);
        tilePane.setVgap(10);
        tilePane.setPrefColumns(2);

        vBowWalletValues.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        vBox.getStyleClass().add("vBoxWalletDashboard");

        vBox.setOnMouseClicked(event -> {
            if(!isSelected){
                vBox.getStyleClass().add("vBoxWalletDashboardSelected");
                vBox.getStyleClass().remove("vBoxWalletDashboard");
//              todo: set wallet to selected in dbb and set all other wallets to not selected
            }
            else{
                vBox.getStyleClass().add("vBoxWalletDashboard");
                vBox.getStyleClass().remove("vBoxWalletDashboardSelected");
//              todo: same thing but the opposite
            }
        });

        Label name = new Label(wallet_id);
        name.setFont(new Font("Liberation Mono",33));
        vBox.getChildren().add(name);

//        Double walletValue = sparkline[sparkline.length - 1];
        Double walletValue = 2.0;
        Label value = new Label(walletValue+" usdt");
        value.setFont(new Font("Liberation Mono",25));
        value.setStyle("-fx-text-fill: WHITE;");
        vBowWalletValues.getChildren().add(value);

//        Double walletVar = (sparkline[sparkline.length - 1] - sparkline[0])/sparkline[sparkline.length - 1];
        Double walletVar = 2.5;
        Label var = new Label(walletVar + "%");
        var.setFont(new Font("Liberation Mono",25));
        var.setStyle("-fx-text-fill: #06f516;");
        vBowWalletValues.getChildren().add(var);

        hBox.getChildren().add(vBowWalletValues);

//      get wallet sparkline
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
//        for (int i = 0; i < sparkline.length; i++) {
//            series.getData().add(new XYChart.Data<>(i + 1, sparkline[i]));
//        }
        lineChart.getData().add(series);

        hBox.getChildren().add(lineChart);
        vBox.getChildren().add(hBox);

        tilePane.getChildren().add(tilePane.getChildren().size() - 1 ,vBox);
    }
}
