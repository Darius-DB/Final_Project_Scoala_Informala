package com.example.demo.order;

import com.example.demo.order.entity.OrderEntity;
import com.example.demo.order.repository.OrderRepository;
import com.example.demo.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AddDataToOrdersH2 {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final Logger logger= LoggerFactory.getLogger(AddDataToOrdersH2.class);

    public AddDataToOrdersH2(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    public void addOrders() {
        List<OrderEntity> orderEntityList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader("C:/Users/Bianca/Desktop/curs JAVA/ProiectFinal/FinalProject(9)/FinalProject/FinalProject/FinalProject ()/FinalProject/demo/src/main/resources/orders.csv"))) {
            String line ;
            while ((line = br.readLine()) != null) {
                try {
                    orderEntityList.add(getOrderFromCsvLine(line));
                    orderRepository.saveAll(orderEntityList);

                } catch (IllegalArgumentException e) {
                    logger.error(e.getMessage());
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
        return orderService.createEntityFromMinimalData(orderAttributes[1], orderAttributes[0]);
    }
}
