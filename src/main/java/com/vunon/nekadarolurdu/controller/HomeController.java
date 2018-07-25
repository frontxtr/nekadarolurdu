package com.vunon.nekadarolurdu.controller;

import com.vunon.nekadarolurdu.model.Currency;
import com.vunon.nekadarolurdu.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private CurrencyService currencyService;

    @RequestMapping("/")
    public ModelAndView showIndex(){
        ModelAndView modelAndView = new ModelAndView();
        Currency currency = new Currency();
        modelAndView.setViewName("home");
        modelAndView.addObject("currency",currency);
        return modelAndView;
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ModelAndView showIndexPost(@ModelAttribute("currency")Currency currency){
        ModelAndView modelAndView = new ModelAndView();
        double dateRate = currencyService.getCurrencyRateForDate(currency.getCurrencyCode(),currency.getDate());
        double todayRate = currencyService.getCurrencyRateForToday(currency.getCurrencyCode());
        double finalAmount = currencyService.calculate(currency.getAmount(),dateRate,todayRate);
        modelAndView.addObject("currency",currency);
        modelAndView.addObject("finalAmount",String.format("%.2f", finalAmount));
        modelAndView.setViewName("home");
        return modelAndView;
    }

}
