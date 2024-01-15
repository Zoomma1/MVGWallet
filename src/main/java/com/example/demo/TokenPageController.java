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
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class TokenPageController extends Tile{
    @FXML
    ChoiceBox choiceBox;
    @FXML
    ChoiceBox priceChoiceBox;
    @FXML
    LineChart lineChart;
    @FXML
    NumberAxis xAxis;
    @FXML
    NumberAxis yAxis;
    @FXML
    Label priceLabel;

    protected Parent root;
    protected Stage stage;
    protected Scene scene;
    private static String coin;
    private static Double price;
    private String wantedCurrency;
    private double exchangeRate = 1;
    private String selectedTimeStamp;
    CoinrankinAPITools coinrankinAPITools = new CoinrankinAPITools();
    CurrencyAPITools currencyConverterAPI = new CurrencyAPITools();
    Map<String, Object> dictionary;
    private double BTCtoUSDConversionRate;

    public TokenPageController() {
    }

    public static void setCoin(String symbol, Double coinPrice) {
        coin = symbol;
        price = coinPrice;
    }

    public void initialize() throws IOException, InterruptedException, ParseException {
        priceLabel.setText(String.format("price: %.3f", price));

        dictionary = currencyConverterAPI.getConversionRate("USD");
        BTCtoUSDConversionRate = coinrankinAPITools.convertFromBtcToUsdt();

        lineChart.setLegendVisible(false);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: #A3A3A3; -fx-opacity: 0.1;");
        lineChart.setVerticalGridLinesVisible(false);
        lineChart.setHorizontalGridLinesVisible(false);

        choiceBox.getItems().addAll("1y","30d","7d","24h","12h","1h");
        choiceBox.setValue("12h");
        selectedTimeStamp = choiceBox.getValue().toString();
        updateSparkLineOnTimeStampChange(selectedTimeStamp);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    selectedTimeStamp = choiceBox.getValue().toString();
                    updateSparkLineOnTimeStampChange(selectedTimeStamp);
                } catch (IOException | InterruptedException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Separator separator = new Separator();
        priceChoiceBox.getItems().addAll("BTC",separator,"USD");
        priceChoiceBox.setValue("USD");

        String[] availableCurrency = getAvailableCurrency();
        for (String currency : availableCurrency) {
            priceChoiceBox.getItems().add(currency);
        }

        priceChoiceBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->{
            if(newValue != null){
                if(Objects.equals(newValue.toString(), "BTC")){
                    wantedCurrency = newValue.toString();
                    try {
                        exchangeRate = BTCtoUSDConversionRate;
                        updateSparkLineOnTimeStampChange(selectedTimeStamp);
                    } catch (IOException | InterruptedException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(Objects.equals(newValue.toString(), "USD")){
                    wantedCurrency = newValue.toString();
                    exchangeRate = 1.0;
                    try {
                        updateSparkLineOnTimeStampChange(selectedTimeStamp);
                    } catch (IOException | InterruptedException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    wantedCurrency = newValue.toString();
                    exchangeRate = (double) dictionary.get(wantedCurrency);
                    try {
                        updateSparkLineOnTimeStampChange(selectedTimeStamp);
                    } catch (IOException | InterruptedException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } ));
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

    private void updateSparkLineOnTimeStampChange(String timeStamp) throws IOException, InterruptedException, ParseException {
        String[] sparkline = coinrankinAPITools.getCoinPriceHistory(coinrankinAPITools.getUUIDByToken(coin),timeStamp);
        xAxis.setTickLabelsVisible(false);
        yAxis.setTickLabelsVisible(false);

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
        yAxis.setUpperBound(maxValue * exchangeRate * 1.001);
        yAxis.setLowerBound(minValue * exchangeRate * 0.999);

        xAxis.setAutoRanging(false);
        xAxis.setUpperBound(sparkline.length);

        for (int i = 0; i < sparkline.length; i++) {
            data.add(new XYChart.Data<>(i, Double.parseDouble(sparkline[i])*exchangeRate));
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
    private String[] getAvailableCurrency() throws IOException, ParseException, InterruptedException {
        String[] keys = new String[dictionary.size()];
        int i = 0;
        for (String key : dictionary.keySet()) {
            keys[i] = key;
            i++;
        }
        return keys;
    }

}
