package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.TreeMap;


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

    public void dashboardOnAction(ActionEvent event) throws IOException {
        switchPage(event, "DashboardPage.fxml");
    }

    public void stockOnAction(ActionEvent event) throws IOException {
        switchPage(event, "StockPage.fxml");
    }

    public void tradesOnAction(ActionEvent event) throws IOException {
        switchPage(event, "TradesPage.fxml");
    }

    public void overviewOnAction(ActionEvent event) throws IOException {
        switchPage(event, "OverviewPage.fxml");
    }

    public void myAccountOnAction(ActionEvent event) throws IOException {
        switchPage(event, "MyAccountPage.fxml");
    }

    public void createNewCryptoTile(String symbol, String iconUrl, Double price, Double change, String[] sparkline){
        HBox hBox = new HBox();
        VBox vBox = new VBox();

        tilePane.setHgap(15);
        tilePane.setVgap(15);
        tilePane.setPrefColumns(4);

        hBox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        hBox.getStyleClass().add("hBoxCryptoPage");
        vBox.getStyleClass().add("vBoxCryptoPage");

        WebView icon = new WebView();
        icon.getEngine().loadContent("<html><body style='margin:0; padding:0;'><img src='" + iconUrl + "' style='width:100%; height:100%;'></body></html>");
        icon.getStyleClass().add("webView");
        icon.setPageFill(Color.TRANSPARENT);

        hBox.getChildren().add(icon);
        Label symbollabel = new Label(symbol);
        Label priceLabel = new Label(price.toString());
        Label changeLabel = new Label();

        if(change > 0){
            changeLabel.setText("+" + change);
            changeLabel.setStyle("-fx-text-fill: green;");
        }else {
            changeLabel.setText(change.toString());
            changeLabel.setStyle("-fx-text-fill: red;");
        }

        symbollabel.setFont(new Font("Liberation Mono", 14));
        priceLabel.setFont(new Font("Liberation Mono", 14));
        changeLabel.setFont(new Font("Liberation Mono", 14));

        hBox.getChildren().add(symbollabel);
        hBox.getChildren().add(priceLabel);
        hBox.getChildren().add(changeLabel);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setTickLabelsVisible(false);
        yAxis.setTickLabelsVisible(false);

        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        ObservableList<XYChart.Data<Number, Number>> data = FXCollections.observableArrayList();

        double minValue = Double.parseDouble(sparkline[sparkline.length - 1]);
        double maxValue = Double.parseDouble(sparkline[sparkline.length - 1]);

        for (int i = 12; i < sparkline.length; i++) {
            if (Double.parseDouble(sparkline[i]) > maxValue){
                maxValue = Double.parseDouble(sparkline[i]);
            } else if (Double.parseDouble(sparkline[i]) < minValue) {
                minValue = Double.parseDouble(sparkline[i]);
            }
        }

        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(maxValue * 1.001);
        yAxis.setLowerBound(minValue * 0.999);

        for (int i = 12; i < sparkline.length; i++) {
            data.add(new XYChart.Data<>(i - 12, Double.parseDouble(sparkline[i])));
        }

        XYChart.Series<Number,Number> series = new XYChart.Series<>();
        series.setData(data);

        lineChart.getData().add(series);

        xAxis.getStyleClass().add("axisPreview");
        yAxis.getStyleClass().add("axisPreview");
        lineChart.getStyleClass().add("lineChartPreview");

        vBox.getChildren().add(lineChart);
        vBox.getChildren().add(hBox);

        tilePane.getChildren().add(vBox);

    }
    public void testOnAction() throws IOException, InterruptedException {
        displayFiftyBestCoins();
    }

    public void displayFiftyBestCoins() throws IOException, InterruptedException {
        CoinrankinAPITools coinrankinAPITools = new CoinrankinAPITools();
        String jsonString = coinrankinAPITools.getFiftyBestCoins();
        JSONParser parser = new JSONParser();

        String symbol;
        String iconUrl;
        Double price;
        Double change;
        String[] sparkLine;

        try {
            JSONObject json = (JSONObject) parser.parse(jsonString);
            JSONArray coinsArray = (JSONArray) ((JSONObject) json.get("data")).get("coins");
            TreeMap<String,Double> coinPrice = new TreeMap<>();
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
