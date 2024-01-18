package com.example.demo;

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
import org.json.simple.parser.ParseException;

import java.io.IOException;
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

    @Override
    public void initialize() throws InterruptedException, IOException {
        super.initialize();
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

    public void createNewWalletOnAction(){
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
            walletTileCreation(walletName);
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: #010831");
        layout.getChildren().addAll(label, textField, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
//      todo:  add the wallet to the database
    }

    public void walletTileCreation(String walletName){
//      todo: create wallets using the user owned wallets and display a sparkline base on its value over time (I think we will display them with a one day interval)
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
            }
            else{
                vBox.getStyleClass().add("vBoxWalletDashboard");
                vBox.getStyleClass().remove("vBoxWalletDashboardSelected");
            }
        });

        Label name = new Label(walletName);
        name.setFont(new Font("Liberation Mono",33));
        vBox.getChildren().add(name);

//      get wallet value
        Double walletValue = 15.00;
        Label value = new Label(walletValue+" usdt");
        value.setFont(new Font("Liberation Mono",25));
        value.setStyle("-fx-text-fill: WHITE;");
        vBowWalletValues.getChildren().add(value);

//      get wallet change
        Double walletVar = 1.5;
        Label var = new Label(walletVar + "%");
        var.setFont(new Font("Liberation Mono",25));
        var.setStyle("-fx-text-fill: #06f516;");
        vBowWalletValues.getChildren().add(var);

        hBox.getChildren().add(vBowWalletValues);

//      get wallet sparkline
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        hBox.getChildren().add(lineChart);

        vBox.getChildren().add(hBox);

        tilePane.getChildren().add(tilePane.getChildren().size() - 1 ,vBox);
    }
}
