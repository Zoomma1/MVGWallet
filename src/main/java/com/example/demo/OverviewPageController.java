package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.IOException;
/*************************************************************************************
 *This is the OverviewPageController class it is used to control the OverviewPage    *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/
public class OverviewPageController extends NavBarController{

    @FXML
    ScrollPane scrollPane;
    @FXML
    VBox vBoxBest;
    @FXML
    VBox vBoxWorst;
    @FXML
    VBox vBoxScrollPane;
    @FXML
    TitledPane tiltedPaneBest;
    @FXML
    TitledPane tiltedPaneWorst;

    RealTimeFinanceDataAPITools realTimeFinanceDataAPITools = new RealTimeFinanceDataAPITools();
    CoinrankinAPITools coinrankinAPITools = new CoinrankinAPITools();
    JSONParser jsonParser = new JSONParser();
    String[] fiveBestAndFiveWorstCrypt;

    @Override
    public void initialize() throws InterruptedException, IOException {
        try{
            fiveBestAndFiveWorstCrypt = coinrankinAPITools.getFiveBestAndFiveWorstCoinsLastMonth();
//            displayNewsTiles();
            displayFiveBestCrypto();
            displayFiveWorstCrypto();
        }catch (ParseException e){
            e.printStackTrace();
        }
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

    public void tradesOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "TradesPage.fxml");
    }

    public void myAccountOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "MyAccountPage.fxml");
    }

    public void displayNewsTiles() throws IOException, InterruptedException, ParseException {
        vBoxScrollPane.setSpacing(15);

        String jsonString = realTimeFinanceDataAPITools.getStockNews();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
        JSONObject jsonData = (JSONObject) jsonObject.get("data");
        JSONArray jsonNews = (JSONArray) jsonData.get("news");

        for (int i = 0; i < jsonNews.size()-1; i++) {
            JSONObject news = (JSONObject) jsonNews.get(i);

            String title = news.get("article_title").toString();
            String source = news.get("source").toString();
            String url = news.get("article_url").toString();

            NewsTile newsTile = new NewsTile(source,title,url);
            vBoxScrollPane.getChildren().add(newsTile.createNewNewsTile());
        }
    }

    public void displayFiveBestCrypto() throws ParseException {
//      todo: fix the fact that the axis still get displayed and that's ugly af
        vBoxBest.setSpacing(0);
        for (int i = 0; i < 3; i++) {
            HBox hBox = new HBox();
            hBox.getStyleClass().add("hBoxOverview");

            JSONObject jsonObject = (JSONObject) jsonParser.parse(fiveBestAndFiveWorstCrypt[i]);
            String iconUrl = jsonObject.get("iconUrl").toString();
            String symbol = jsonObject.get("symbol").toString();

            JSONArray sparklineArray = (JSONArray) jsonObject.get("sparkline");
            Double[] sparkline = new Double[sparklineArray.size() - 2];
            for (int j = 0; j < sparkline.length; j++) {
                sparkline[j] = Double.parseDouble(sparklineArray.get(j).toString());
            }

            double change = ((sparkline[sparkline.length - 1] - sparkline[0]) / sparkline[0]) * 100;

            Label symbolLabel = new Label(symbol);
            Label changeLabel = new Label(Double.toString(change));

            if(change > 0){
                changeLabel.setText(String.format("+%.2f",change) + "%");
                changeLabel.setStyle("-fx-text-fill: green;");
            }else {
                changeLabel.setText(String.format("%.2f",change) + "%");
                changeLabel.setStyle("-fx-text-fill: red;");
            }

            symbolLabel.setFont(new Font("Liberation Mono", 14));
            symbolLabel.setStyle("-fx-text-fill: WHITE");
            changeLabel.setFont(new Font("Liberation Mono", 14));

            WebView icon = new WebView();
            icon.getEngine().loadContent("<html><body style='margin:0; padding:0; display:flex; align-items:center; justify-content:center;'><img src='" + iconUrl + "' style='width:40px; height:40px;'></body></html>");
            icon.getStyleClass().add("webView");
            icon.setPageFill(Color.TRANSPARENT);

            NumberAxis xAxis = new NumberAxis();
            NumberAxis yAxis = new NumberAxis();
            xAxis.setTickLabelsVisible(false);
            xAxis.setVisible(false);
            yAxis.setTickLabelsVisible(false);
            yAxis.setVisible(false);

            LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

            ObservableList<XYChart.Data<Number, Number>> data = FXCollections.observableArrayList();

            double minValue = sparkline[sparkline.length - 1];
            double maxValue = sparkline[sparkline.length - 1];

            for (Double d : sparkline) {
                if (d > maxValue) {
                    maxValue = d;
                } else if (d < minValue) {
                    minValue = d;
                }
            }

            yAxis.setAutoRanging(false);
            yAxis.setUpperBound(maxValue * 1.001);
            yAxis.setLowerBound(minValue * 0.999);

            for (int j = 0; j < sparkline.length; j++) {
                data.add(new XYChart.Data<>(j, sparkline[j]));
            }

            XYChart.Series<Number,Number> series = new XYChart.Series<>();
            series.setData(data);

            lineChart.getData().add(series);

            for (XYChart.Data<Number, Number> dataPoint : series.getData()) {
                Node dataNode = dataPoint.getNode();
                Tooltip tooltip = new Tooltip(String.format("%.3f", dataPoint.getYValue()));
                Tooltip.install(dataNode, tooltip);

                dataNode.setOnMouseEntered(event -> {
                    tooltip.show(dataNode, event.getScreenX(), event.getScreenY() + 10);
                });

                dataNode.setOnMouseExited(event -> {
                    tooltip.hide();
                });
            }

            xAxis.getStyleClass().add("axisPreview");
            xAxis.setPrefHeight(80);
            yAxis.getStyleClass().add("axisPreview");
            lineChart.getStyleClass().add("lineChartPreview");
            lineChart.getYAxis().setVisible(false);
            lineChart.getXAxis().setVisible(false);
            lineChart.setLegendVisible(false);
            lineChart.setVerticalGridLinesVisible(false);
            lineChart.setHorizontalGridLinesVisible(false);

            lineChart.setPrefWidth(300);
            lineChart.setPrefHeight(90);
            lineChart.setMaxWidth(Double.MAX_VALUE);
            lineChart.setMaxHeight(Double.MAX_VALUE);
            icon.setMaxSize(50,50);
            symbolLabel.setMaxHeight(100);
            changeLabel.setMaxHeight(100);

            hBox.getChildren().addAll(icon,symbolLabel,changeLabel,lineChart);
            hBox.setSpacing(10);
            vBoxBest.getChildren().add(hBox);
        }
    }public void displayFiveWorstCrypto() throws ParseException {
        vBoxBest.setSpacing(0);
//      todo: fix the fact that the axis still get displayed and that's ugly af
        for (int i = 3; i < 6; i++) {
            HBox hBox = new HBox();

            JSONObject jsonObject = (JSONObject) jsonParser.parse(fiveBestAndFiveWorstCrypt[i]);
            String iconUrl = jsonObject.get("iconUrl").toString();
            String symbol = jsonObject.get("symbol").toString();

            JSONArray sparklineArray = (JSONArray) jsonObject.get("sparkline");
            Double[] sparkline = new Double[sparklineArray.size() - 2];
            for (int j = 0; j < sparkline.length; j++) {
                sparkline[j] = Double.parseDouble(sparklineArray.get(j).toString());
            }

            double change = ((sparkline[sparkline.length - 1] - sparkline[0]) / sparkline[0]) * 100;

            Label symbolLabel = new Label(symbol);
            Label changeLabel = new Label(Double.toString(change));

            if(change > 0){
                changeLabel.setText(String.format("+%.2f",change) + "%");
                changeLabel.setStyle("-fx-text-fill: green;");
            }else {
                changeLabel.setText(String.format("%.2f",change) + "%");
                changeLabel.setStyle("-fx-text-fill: red;");
            }

            symbolLabel.setFont(new Font("Liberation Mono", 14));
            symbolLabel.setStyle("-fx-text-fill: WHITE");
            changeLabel.setFont(new Font("Liberation Mono", 14));

            WebView icon = new WebView();
            icon.getEngine().loadContent("<html><body style='margin:0; padding:0; display:flex; align-items:center; justify-content:center;'><img src='" + iconUrl + "' style='width:40px; height:40px;'></body></html>");
            icon.getStyleClass().add("webView");
            icon.setPageFill(Color.TRANSPARENT);

            hBox.setPrefHeight(100);
            hBox.setPrefWidth(650);
            hBox.setStyle("-fx-background-color: transparent");
            hBox.setAlignment(Pos.CENTER);

            NumberAxis xAxis = new NumberAxis();
            NumberAxis yAxis = new NumberAxis();
            xAxis.setTickLabelsVisible(false);
            xAxis.setVisible(false);
            yAxis.setTickLabelsVisible(false);
            yAxis.setVisible(false);

            LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

            ObservableList<XYChart.Data<Number, Number>> data = FXCollections.observableArrayList();

            double minValue = sparkline[sparkline.length - 1];
            double maxValue = sparkline[sparkline.length - 1];

            for (Double d : sparkline) {
                if (d > maxValue) {
                    maxValue = d;
                } else if (d < minValue) {
                    minValue = d;
                }
            }

            yAxis.setAutoRanging(false);
            yAxis.setUpperBound(maxValue * 1.001);
            yAxis.setLowerBound(minValue * 0.999);

            for (int j = 0; j < sparkline.length; j++) {
                data.add(new XYChart.Data<>(j, sparkline[j]));
            }

            XYChart.Series<Number,Number> series = new XYChart.Series<>();
            series.setData(data);

            lineChart.getData().add(series);

            for (XYChart.Data<Number, Number> dataPoint : series.getData()) {
                Node dataNode = dataPoint.getNode();
                Tooltip tooltip = new Tooltip(String.format("%.3f", dataPoint.getYValue()));
                Tooltip.install(dataNode, tooltip);

                dataNode.setOnMouseEntered(event -> {
                    tooltip.show(dataNode, event.getScreenX(), event.getScreenY() + 10);
                });

                dataNode.setOnMouseExited(event -> {
                    tooltip.hide();
                });
            }

            xAxis.getStyleClass().add("axisPreview");
            xAxis.setPrefHeight(80);
            yAxis.getStyleClass().add("axisPreview");
            lineChart.getStyleClass().add("lineChartPreview");
            lineChart.getYAxis().setVisible(false);
            lineChart.getXAxis().setVisible(false);
            lineChart.setLegendVisible(false);
            lineChart.setVerticalGridLinesVisible(false);
            lineChart.setHorizontalGridLinesVisible(false);

            lineChart.setPrefWidth(340);
            lineChart.setPrefHeight(100);

            hBox.getChildren().addAll(icon,symbolLabel,changeLabel,lineChart);
            hBox.setSpacing(10);
            vBoxWorst.getChildren().add(hBox);
        }
    }
}
