package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class Tile {
    public String symbol;
    public String iconUrl;
    public double price;
    public double change;
    public String[] sparkLine;
    protected Parent root;
    protected Stage stage;
    protected Scene scene;
    String selectedCoin = null;

    public Tile(String symbol, String iconUrl, double price, double change, String[] sparkLine) {
        this.symbol = symbol;
        this.iconUrl = iconUrl;
        this.price = price;
        this.change = change;
        this.sparkLine = sparkLine;
    }

    public Tile() {
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
        icon.getEngine().loadContent("<html><body style='margin:0; padding:0; display:flex; align-items:center; justify-content:center;'><img src='" + this.iconUrl + "' style='width:50px; height:50px;'></body></html>");
        icon.getStyleClass().add("webView");
        icon.setPageFill(Color.TRANSPARENT);

        Label symbollabel = new Label(this.symbol);
        Label priceLabel = new Label(String.format("%.3f",this.price));
        Label changeLabel = new Label();

        if(this.change > 0){
            changeLabel.setText("+" + this.change + "%");
            changeLabel.setStyle("-fx-text-fill: green;");
        }else {
            changeLabel.setText(this.change + "%");
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

        for (String s : this.sparkLine) {
            if (Double.parseDouble(s) > maxValue) {
                maxValue = Double.parseDouble(s);
            } else if (Double.parseDouble(s) < minValue) {
                minValue = Double.parseDouble(s);
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
        yAxis.getStyleClass().add("axisPreview");
        lineChart.getStyleClass().add("lineChartPreview");
        lineChart.setLegendVisible(false);

        vBox.getChildren().add(lineChart);
        vBox.getChildren().add(hBox);

        vBox.setOnMouseClicked(event -> {
            try {
                TokenPageController.setCoin(symbol);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("TokenPage.fxml"));
                root = loader.load();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                double currentWidth = stage.getWidth();
                double currentHeight = stage.getHeight();

                scene = new Scene(root, currentWidth , currentHeight);
                scene.getStylesheets().addAll(stage.getScene().getStylesheets());
                scene.setFill(stage.getScene().getFill());
                stage.setScene(scene);

                stage.setWidth(currentWidth);
                stage.setHeight(currentHeight);
                stage.setMinHeight(800);
                stage.setMinWidth(900);

                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return vBox;
    }
}
