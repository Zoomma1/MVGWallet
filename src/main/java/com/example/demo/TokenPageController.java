package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class TokenPageController extends Tile{
    @FXML
    ChoiceBox choiceBox;
    @FXML
    LineChart lineChart;
    @FXML
    NumberAxis xAxis;
    @FXML
    NumberAxis yAxis;

    protected Parent root;
    protected Stage stage;
    protected Scene scene;
    private static String coin;
    CoinrankinAPITools coinrankinAPITools = new CoinrankinAPITools();

    public TokenPageController() {
    }


//  todo : on initialize set spacing for top and bottom HBox we will see later to make it look good


    public static void setCoin(String symbol) {
        coin = symbol;
    }

    public void initialize() throws IOException, InterruptedException, ParseException {
        choiceBox.getItems().addAll("1y","24h","12h","1h");
        choiceBox.setValue("12h");
        updateSparkLineOnTimeStampChange("12h");
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    updateSparkLineOnTimeStampChange(choiceBox.getValue().toString());
                } catch (IOException | InterruptedException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void backButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CryptocurrencyPage.fxml"));
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
    }

//    todo : correct bug where all previous graph gets displayed
    private void updateSparkLineOnTimeStampChange(String timeStamp) throws IOException, InterruptedException, ParseException {
        String[] sparkline = coinrankinAPITools.getCoinPriceHistory(coinrankinAPITools.getUUIDByToken(coin),timeStamp);
        xAxis.setTickLabelsVisible(false);
        yAxis.setTickLabelsVisible(false);

        xAxis.getStyleClass().add("axisPreview");
        yAxis.getStyleClass().add("axisPreview");
        lineChart.getStyleClass().add("lineChartPreview");
        lineChart.setLegendVisible(false);

        ObservableList<XYChart.Data<Number, Number>> data = FXCollections.observableArrayList();

        double minValue = Double.parseDouble(sparkline[sparkline.length - 1]);
        double maxValue = Double.parseDouble(sparkline[sparkline.length - 1]);

        for (String s : sparkline) {
            if (Double.parseDouble(s) > maxValue) {
                maxValue = Double.parseDouble(s);
            } else if (Double.parseDouble(s) < minValue) {
                minValue = Double.parseDouble(s);
            }
        }

        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(maxValue * 1.001);
        yAxis.setLowerBound(minValue * 0.999);

        xAxis.setAutoRanging(false);
        xAxis.setUpperBound(sparkline.length);

        for (int i = 0; i < sparkline.length; i++) {
            data.add(new XYChart.Data<>(i, Double.parseDouble(sparkline[i])));
        }


        XYChart.Series<Number,Number> series = new XYChart.Series<>();
        series.setData(data);

        lineChart.getData().clear();
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
    }


}
