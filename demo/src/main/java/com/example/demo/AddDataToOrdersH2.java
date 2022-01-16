package com.example.demo;

import com.example.demo.destination.repository.DestinationRepository;
import com.example.demo.order.entity.OrderEntity;
import com.example.demo.order.repository.OrderRepository;
import com.example.demo.service.OrderService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AddDataToOrdersH2 {


    private final DestinationRepository destinationRepository;
    private final OrderRepository orderRepository;
    private final GetDestinationEntityFromCityName getDestinationEntityFromCityName;
    private final OrderService orderService;
    //TODO orderservice

    private List<OrderEntity> orderEntityList = new ArrayList<>();


    public AddDataToOrdersH2(DestinationRepository destinationRepository,
                             OrderRepository orderRepository,
                             GetDestinationEntityFromCityName getDestinationEntityFromCityName, OrderService orderService) {

        this.orderRepository = orderRepository;
        this.destinationRepository = destinationRepository;
        this.getDestinationEntityFromCityName = getDestinationEntityFromCityName;
        this.orderService = orderService;
    }


    public void addOrders() {
        try (BufferedReader br = new BufferedReader(
                new FileReader("C:\\Users\\ALEX\\Desktop\\FinalProject (2)\\FinalProject\\FinalProject ()\\FinalProject\\demo\\src\\main\\resources\\orders.csv"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                try {
                    orderEntityList.add(getOrderFromCsvLine(line));
                    orderRepository.saveAll(orderEntityList);

                } catch (IllegalArgumentException e) {
                    System.err.println(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected OrderEntity getOrderFromCsvLine(String line) {

        String[] orderAttributes = line.split(",");

        if (orderAttributes.length != 2) {
            throw new IllegalArgumentException("Corrupted data");
        }


        /*LocalDate dateTime1 = DisplayCurrentDate.getCurrentDate();
        long seconds = dateTime1.atStartOfDay(ZoneId.systemDefault())
                .toEpochSecond(); // returns seconds
        long lastUpdated = seconds * 1000; // seconds to milliseconds*/


        return orderService.createEntityFromMinimalData(orderAttributes[1], orderAttributes[0]);

    }


    public List<OrderEntity> getOrderEntityList() {
        return orderEntityList;
    }
}
