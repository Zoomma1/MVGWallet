package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

    RealTimeFinanceDataAPITools realTimeFinanceDataAPITools = new RealTimeFinanceDataAPITools();
    JSONParser jsonParser = new JSONParser();
//todo: request the 50 crypto and look which one is in the top five of the month and the last five of the month (we will use the ranks)
    @Override
    public void initialize() throws InterruptedException, IOException {
        try{
            displayNewsTiles();
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
}
