package com.example.demo.order.controller;

import com.example.demo.util.DisplayCurrentDate;
import com.example.demo.order.CourierBean;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderInfoContributor implements InfoContributor {

    private final DisplayCurrentDate displayCurrentDate;
    private final CourierBean courierBean;

    public OrderInfoContributor(DisplayCurrentDate displayCurrentDate, CourierBean courierBean) {
        this.displayCurrentDate = displayCurrentDate;
        this.courierBean = courierBean;
    }
    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> stats = new HashMap<>();
        stats.put("current-date: ", displayCurrentDate.getCurrentDate().toString());
        stats.put("overall-profit", courierBean.getProfit().toString());
        builder.withDetail("orders", stats);
    }




}
