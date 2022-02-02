package com.example.demo.order;

import com.example.demo.order.entity.OrderEntity;
import com.example.demo.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourierBean {

    private static final Logger logger = LoggerFactory.getLogger(CourierBean.class);

    Long profit = 0L;
    private final OrderRepository orderRepository;

    public CourierBean(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Async
    public void courierStart(String destinationName, List<OrderEntity> orderEntities) {

        //log + timestamp + km
        int numberOfDeliveringOrders = 0;

        List<Long> idList = orderEntities
                .stream()
                .map(OrderEntity::getId)
                .collect(Collectors.toList());

        Iterable<OrderEntity> allById = orderRepository.findAllById(idList);

        for (OrderEntity orderEntity : allById) {
            if (orderEntity.getStatusOrder() != OrderStatus.DELIVERED && orderEntity.getStatusOrder() != OrderStatus.CANCELED) {
                orderEntity.setStatusOrder(OrderStatus.DELIVERING);
                orderRepository.save(orderEntity);
                numberOfDeliveringOrders++;
            }
        }

        logger.info("Starting " + numberOfDeliveringOrders + " deliveries for " + destinationName + " on " + Thread.currentThread().getName() + " for " +
                orderEntities.get(0).getDestination().getDistance() + " km");


        int numberOfDeliveredOrders = 0;

        try {
            Thread.sleep(orderEntities.get(0).getDestination().getDistance() * 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //preluare din nou din DB
        List<Long> idList2 = orderEntities
                .stream()
                .map(OrderEntity::getId)
                .collect(Collectors.toList());
        Iterable<OrderEntity> allById2 = orderRepository.findAllById(idList2);

        for (OrderEntity newOrderEntity : allById2) {
            if (newOrderEntity.getStatusOrder() == OrderStatus.DELIVERING) {
                profit += newOrderEntity.getDestination().getDistance();
                newOrderEntity.setStatusOrder(OrderStatus.DELIVERED);
                orderRepository.save(newOrderEntity);
                numberOfDeliveredOrders++;
            }
        }
        logger.info(numberOfDeliveredOrders + " deliveries completed for " + destinationName);

    }

    public Long getProfit() {
        return profit;
    }
}
