package com.example.demo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*************************************************************************************
 *This is where we are getting all the Cryptocurrencies data.                        *
 *The Coin Ranking API uses HTLM request to get various information about the        *
 *cryptocurrencies. As a results of our different requests we are getting a JSON     *
 *string. We are currently gathering data about the best fifty coins on the market   *
 *and about specified coins requested by the user                                    *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/

public class CoinrankinAPITools {

    public void getCoinPrice() throws IOException, InterruptedException {
        String  jsonString = getFiftyBestCoins();
        JSONParser parser = new JSONParser();

        try {
            JSONObject json = (JSONObject) parser.parse(jsonString);
            JSONArray coinsArray = (JSONArray) ((JSONObject) json.get("data")).get("coins");
            TreeMap<String,Double> coinPrice = new TreeMap<>();
            for (int i = 0; i < coinsArray.size(); i++) {
                double price = Double.parseDouble(((JSONObject) coinsArray.get(i)).get("price").toString());
                String coin = ((JSONObject) coinsArray.get(i)).get("symbol").toString();
                coinPrice.put(coin,price);
            }
            System.out.println(coinPrice);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getFiftyBestCoins() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://coinranking1.p.rapidapi.com/coins?referenceCurrencyUuid=yhjMzLPhuIDl&timePeriod=3h&tiers%5B0%5D=1&orderBy=marketCap&orderDirection=desc&limit=50&offset=0"))
                .header("X-RapidAPI-Key", "11b63e8be8msh319854639b88765p157ae7jsn499f64df1c02")
                .header("X-RapidAPI-Host", "coinranking1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String[] getCoinPriceHistory(String uuid, String timeperiod) throws IOException, InterruptedException, ParseException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://coinranking1.p.rapidapi.com/coin/" + uuid + "?referenceCurrencyUuid=yhjMzLPhuIDl&timePeriod=" + timeperiod))
                .header("X-RapidAPI-Key", "11b63e8be8msh319854639b88765p157ae7jsn499f64df1c02")
                .header("X-RapidAPI-Host", "coinranking1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String jsonString = response.body();
        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
        JSONObject coinData = (JSONObject) jsonObject.get("data");
        JSONObject coin = (JSONObject) coinData.get("coin");
        JSONArray sparklineArray = (JSONArray) coin.get("sparkline");

        int size = sparklineArray.size();
        String[] sparkline = new String[size];

        for (int i = 0; i < size; i++) {
            sparkline[i] = sparklineArray.get(i).toString();
        }
        return sparkline;
    }

    public String getUUIDByToken(String token) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://coinranking1.p.rapidapi.com/coins?referenceCurrencyUuid=yhjMzLPhuIDl&timePeriod=24h&tiers%5B0%5D=1&orderBy=marketCap&orderDirection=desc&limit=50&offset=0"))
                .header("X-RapidAPI-Key", "11b63e8be8msh319854639b88765p157ae7jsn499f64df1c02")
                .header("X-RapidAPI-Host", "coinranking1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.body();
        JSONParser parser = new JSONParser();

        try {
            JSONObject json = (JSONObject) parser.parse(jsonString);
            JSONArray coinsArray = (JSONArray) ((JSONObject) json.get("data")).get("coins");

            for (int i = 0; i < coinsArray.size(); i++) {
                String symbol = ((JSONObject) coinsArray.get(i)).get("symbol").toString();
                if (symbol.equalsIgnoreCase(token)) {
                    return ((JSONObject) coinsArray.get(i)).get("uuid").toString();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null; // Return null if the token is not found
    }

    public double convertFromBtcToUsdt(double tokenBTCprice) throws IOException, InterruptedException, ParseException {
        JSONParser parser = new JSONParser();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://coinranking1.p.rapidapi.com/coin/Qwsogvtv82FCd/price?referenceCurrencyUuid=yhjMzLPhuIDl"))
                .header("X-RapidAPI-Key", "11b63e8be8msh319854639b88765p157ae7jsn499f64df1c02")
                .header("X-RapidAPI-Host", "coinranking1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String jsonString = response.body();
        JSONObject json = (JSONObject) parser.parse(jsonString);
        JSONArray coinsArray = (JSONArray) ((JSONObject) json.get("data")).get("coins");
        double convertionRate = Double.parseDouble(((JSONObject) coinsArray.get(0)).get("price").toString());

        return tokenBTCprice*convertionRate;

    }
    public double convertUSDToEUR(Double price) throws IOException, InterruptedException {
        RealTimeFinanceDataAPITools realTimeFinanceDataAPITools = new RealTimeFinanceDataAPITools();
        Double conversionRate = realTimeFinanceDataAPITools.getUSDToEURExchangeRate();
        return price*conversionRate;
    }

}
