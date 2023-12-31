package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;

public class Tile {
    public String symbol;
    public String iconUrl;
    public double price;
    public double change;
    public String[] sparkLine;

    public Tile(String symbol, String iconUrl, double price, double change, String[] sparkLine) {
        this.symbol = symbol;
        this.iconUrl = iconUrl;
        this.price = price;
        this.change = change;
        this.sparkLine = sparkLine;
    }

    public Node displayTile(){
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        VBox vBox1 = new VBox();

        hBox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(10);
        hBox.getStyleClass().add("hBoxCryptoPage");
        vBox.getStyleClass().add("vBoxCryptoPage");

        WebView icon = new WebView();
        icon.getEngine().loadContent("<html><body style='margin:0; padding:0;'><img src='" + this.iconUrl + "' style='width:50; height:50;'></body></html>");
        icon.getStyleClass().add("webView");
        icon.setPageFill(Color.TRANSPARENT);

        Label symbollabel = new Label(this.symbol);
        Label priceLabel = new Label(String.format("%.3f",this.price));
        Label changeLabel = new Label();

        if(this.change > 0){
            changeLabel.setText("+" + this.change);
            changeLabel.setStyle("-fx-text-fill: green;");
        }else {
            changeLabel.setText(String.valueOf(this.change));
            changeLabel.setStyle("-fx-text-fill: red;");
        }

        symbollabel.setFont(new Font("Liberation Mono", 14));
        priceLabel.setFont(new Font("Liberation Mono", 14));
        changeLabel.setFont(new Font("Liberation Mono", 14));

        vBox1.getChildren().add(symbollabel);
        vBox1.getChildren().add(priceLabel);
        hBox.getChildren().add(changeLabel);
        hBox.getChildren().add(vBox1);
        hBox.getChildren().add(icon);
        double spacing = (hBox.getWidth() - icon.getPrefWidth() - vBox1.getPrefWidth() -changeLabel.getPrefWidth()) / 4;
        hBox.setSpacing(spacing);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setTickLabelsVisible(false);
        yAxis.setTickLabelsVisible(false);

        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        ObservableList<XYChart.Data<Number, Number>> data = FXCollections.observableArrayList();

        double minValue = Double.parseDouble(this.sparkLine[this.sparkLine.length - 1]);
        double maxValue = Double.parseDouble(this.sparkLine[this.sparkLine.length - 1]);

        for (int i = 0; i < this.sparkLine.length; i++) {
            if (Double.parseDouble(this.sparkLine[i]) > maxValue){
                maxValue = Double.parseDouble(this.sparkLine[i]);
            } else if (Double.parseDouble(this.sparkLine[i]) < minValue) {
                minValue = Double.parseDouble(this.sparkLine[i]);
            }
        }

        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(maxValue * 1.001);
        yAxis.setLowerBound(minValue * 0.999);

        for (int i = 0; i < this.sparkLine.length; i++) {
            data.add(new XYChart.Data<>(i, Double.parseDouble(this.sparkLine[i])));
        }

        XYChart.Series<Number,Number> series = new XYChart.Series<>();
        series.setData(data);

        lineChart.getData().add(series);

        xAxis.getStyleClass().add("axisPreview");
        yAxis.getStyleClass().add("axisPreview");
        lineChart.getStyleClass().add("lineChartPreview");
        lineChart.setLegendVisible(false);

        vBox.getChildren().add(lineChart);
        vBox.getChildren().add(hBox);

        vBox.setOnMouseClicked(event -> {
            System.out.printf(this.symbol);
        });

        return vBox;
    }
}
