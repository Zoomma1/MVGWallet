package com.example.demo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AlphaVantageAPITools {
    JSONParser jsonParser = new JSONParser();
    public String getTIME_SERIES_INTRADAY(String symbol, String interval) throws IOException, InterruptedException {
        int maxRetries = 5;
        int retryDelaySeconds = 60;

        for (int retryCount = 0; retryCount < maxRetries; retryCount++) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?interval=" + interval + "&function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&datatype=json&output_size=compact"))
                    .header("X-RapidAPI-Key", "11b63e8be8msh319854639b88765p157ae7jsn499f64df1c02")
                    .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            try {
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    String errorMessage = response.body();

                    if (errorMessage.contains("exceeded the rate limit per minute")) {
                        TimeUnit.SECONDS.sleep(retryDelaySeconds);
                        continue;
                    }
                    throw new RuntimeException("API request failed with message: " + errorMessage);
                } else {
                    return response.body();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("API request failed after multiple retries");
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
