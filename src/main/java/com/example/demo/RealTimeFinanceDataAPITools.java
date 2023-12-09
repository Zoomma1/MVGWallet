package com.example.demo;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*************************************************************************************
 *This is where we are getting all the Stock data.                                   *
 *The Real Time Finance API uses HTLM request to get various information about the   *
 *stock market. As a results of our different requests we are getting a JSON         *
 *string. We are currently gathering data about some companies listed by us          *
 *but the user can request data about a certain share.                               *
 *We are currently facing an issue with this API due to the fact that it only let us *
 *do 500 request per day which is very few for what we want to do.                   *
 *We might change API in a near future for the stock market but will still use this  *
 *for the USD/EUR exchange rate because it is really straight forward.               *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/

public class RealTimeFinanceDataAPITools {
    String query;
    String baseQuery = "Apple Google Microsoft Tesla";

    public RealTimeFinanceDataAPITools() {
        this.query = initialiseBaseQuery();
    }

    public String initialiseBaseQuery(){
        String query = "";
        for(String toQuery : baseQuery.split(" ")){
            query = query + " " +toQuery;
        }
        return query;
    }

    public void addToQuery(String queryToAdd){
        this.query = this.query + queryToAdd;
    }

    public String request(String toQuery) throws IOException, InterruptedException {
        String encodedQuery = URLEncoder.encode(toQuery, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://twelve-data1.p.rapidapi.com/stocks?exchange=NASDAQ&format=json"))
                .header("X-RapidAPI-Key", "11b63e8be8msh319854639b88765p157ae7jsn499f64df1c02")
                .header("X-RapidAPI-Host", "twelve-data1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public double getUSDToEURExchangeRate() throws IOException, InterruptedException {
        String jsonString = request("USD%20to%20EUR");
        System.out.println(jsonString);
        JSONParser parser = new JSONParser();
        double exchangeRate = 0;
        try {
            JSONObject json = (JSONObject) parser.parse(jsonString);
            JSONObject dataObject = (JSONObject) json.get("data");
            JSONArray currencyArray = (JSONArray) dataObject.get("currency");
            JSONObject currencyObject = (JSONObject) currencyArray.get(0);
             exchangeRate= (double) currencyObject.get("exchange_rate");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return exchangeRate;
    }

    public TreeMap<String, List<Double>> queryAll() throws IOException, InterruptedException {
        TreeMap<String, List<Double>> stockData= new TreeMap<>();
        List<String> jsonResponses = new ArrayList<>();
        for(String query : this.query.split(" ")){
            if (!Objects.equals(query, "")){
                jsonResponses.add(request(query));
            }
        }
        System.out.println(jsonResponses);
        try {
            stockData = parseJsonResponsesToTreeMap(jsonResponses);

            // Print the TreeMap
            for (var entry : stockData.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stockData;
    }

    private static TreeMap<String, List<Double>> parseJsonResponsesToTreeMap(List<String> jsonResponses) throws ParseException {
        TreeMap<String, List<Double>> stockData = new TreeMap<>();

        JSONParser parser = new JSONParser();

        for (String jsonResponse : jsonResponses) {
            JSONObject json = (JSONObject) parser.parse(jsonResponse);

            if (json.containsKey("data")) {
                JSONObject dataObject = (JSONObject) json.get("data");

                if (dataObject.containsKey("stock")) {
                    JSONArray stockArray = (JSONArray) dataObject.get("stock");

                    for (Object stockObject : stockArray) {
                        if (stockObject instanceof JSONObject) {
                            JSONObject stockEntry = (JSONObject) stockObject;
                            String companyName = (String) stockEntry.get("name");

                            Number priceNumber = (Number) stockEntry.get("price");
                            Double stockPrice = priceNumber.doubleValue();

                            stockData.computeIfAbsent(companyName, k -> new ArrayList<>()).add(stockPrice);
                        }
                    }
                }
            }
        }
        return stockData;
    }

}
