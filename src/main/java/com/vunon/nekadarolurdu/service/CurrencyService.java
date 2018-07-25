package com.vunon.nekadarolurdu.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {

    public double getCurrencyRateForDate(String currencyCode, String date){
        String uri = "https://doviz.com/api/v1/currencies/";
        uri += currencyCode + "/archive?start=";
        uri += date + "&end=2099-04-29";
        RestTemplate restTemplate = new RestTemplate();
        String str = restTemplate.getForObject(uri, String.class);
        int first = str.indexOf(":");
        int second = str.indexOf(",");
        String rate = str.substring(first+1,second);
        double result = Double.parseDouble(rate);
        return result;
    }

    public double getCurrencyRateForToday(String currencyCode){
        String uri = "https://doviz.com/api/v1/currencies/";
        uri += currencyCode + "/latest";
        RestTemplate restTemplate = new RestTemplate();
        String str = restTemplate.getForObject(uri, String.class);
        int first = str.indexOf(":");
        int second = str.indexOf(",");
        String rate = str.substring(first+1,second);
        double result = Double.parseDouble(rate);
        return result;
    }

    public double calculate(int amount, double dateRate, double todayRate){
        double firstAmount = amount/dateRate;
        double finalAmount = firstAmount * todayRate;
        return finalAmount;
    }
}
