package com.example.demo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AlphaVantageAPITools {
    JSONParser jsonParser = new JSONParser();
    public String getTIME_SERIES_INTRADAY(String symbol, String interval) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?interval=" + interval + "&function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&datatype=json&output_size=compact"))
                .header("X-RapidAPI-Key", "11b63e8be8msh319854639b88765p157ae7jsn499f64df1c02")
                .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
    public String[] getShareSparkline(String symbol, String interval) throws IOException, InterruptedException, ParseException {
        String jsonData = getTIME_SERIES_INTRADAY(symbol,interval);

        JSONObject json = (JSONObject) jsonParser.parse(jsonData);

        JSONObject timeSeries = (JSONObject) json.get("Time Series (" + interval + ")");

        String[] openPrices = new String[timeSeries.size()];

        int i = 0;
        for (Object timestamp : timeSeries.keySet()) {
            JSONObject timeEntry = (JSONObject) timeSeries.get(timestamp);
            String openPrice = (String) timeEntry.get("1. open");
            openPrices[i++] = openPrice;
        }
        return openPrices;
    }

    public boolean isValidSymbol(String symbol) throws IOException, InterruptedException, ParseException {
        String response = getTIME_SERIES_INTRADAY(symbol,"5min");
        JSONObject jsonString = (JSONObject) jsonParser.parse(response);

        return !jsonString.containsKey("Error Message");
    }
}
