package com.example.demo.order;

import com.example.demo.OrderStatus;
import com.example.demo.order.entity.OrderEntity;
import com.example.demo.order.repository.OrderRepository;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

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
        logger.info("Starting deliveries for " + destinationName + " on " + Thread.currentThread().getName() + " for " );

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getStatusOrder() != OrderStatus.DELIVERED && orderEntity.getStatusOrder() != OrderStatus.CANCELED) {
                orderEntity.setStatusOrder(OrderStatus.DELIVERING);
                orderRepository.save(orderEntity);
            }
        }


        for (OrderEntity orderEntity : orderEntities) {
            //adjust profit
            //set as delivered

            try {
                Thread.sleep(orderEntity.getDestination().getDistance() * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (orderEntity.getStatusOrder() == OrderStatus.DELIVERING) {
                profit += orderEntity.getDestination().getDistance();
                orderEntity.setStatusOrder(OrderStatus.DELIVERED);
                orderRepository.save(orderEntity);
            }
        }

//        System.out.println("Profit: " + profit);

        logger.info("Finish delivery for " + destinationName);

    }

    public Long getProfit() {
        return profit;
    }
}
