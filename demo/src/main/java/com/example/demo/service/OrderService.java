package com.example.demo.service;

import com.example.demo.DateUtils;
import com.example.demo.DisplayCurrentDate;
import com.example.demo.OrderStatus;
import com.example.demo.destination.entity.DestinationEntity;
import com.example.demo.destination.repository.DestinationRepository;
import com.example.demo.order.CourierBean;
import com.example.demo.order.OrderConverter;
import com.example.demo.order.OrderDto;
import com.example.demo.order.entity.OrderEntity;
import com.example.demo.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final DisplayCurrentDate displayCurrentDate;
    private final DestinationRepository destinationRepository;
    private final CourierBean courierBean;
    public Long profit = 0L;

    public OrderService(OrderRepository orderRepository, DisplayCurrentDate displayCurrentDate, DestinationRepository destinationRepository, CourierBean courierBean) {
        this.orderRepository = orderRepository;
        this.displayCurrentDate = displayCurrentDate;
        this.destinationRepository = destinationRepository;
        this.courierBean = courierBean;
    }


    public List<OrderDto> getAllOrders() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map(OrderConverter::fromOrderEntity)
                .collect(Collectors.toList());
    }

//    public List<OrderDto> getOrdersByDateAndDestination(long date, DestinationEntity destination) {
//
//        return orderRepository.findOrderByDeliveryDateAndDestination(date, destination).stream()
//                .map(OrderConverter::fromOrderEntity)
//                .collect(Collectors.toList());
//    }
//
//    public List<OrderDto> getOrdersByDate(long deliveryDateInMillis) {
//        return orderRepository.findOrderByDeliveryDate(deliveryDateInMillis).stream()
//                .map(OrderConverter::fromOrderEntity)
//                .collect(Collectors.toList());
//    }


//    public List<OrderDto> findOrderById(Long id) {
//        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
//
//        return orderEntityOptional.map(orderEntity -> Collections.singletonList(
//                OrderConverter.fromOrderEntity(orderEntity))).orElse(Collections.emptyList());
//    }


    public List<OrderDto> cancelOrders(Long[] ids) throws ParseException {
        List<OrderEntity> ordersToBeCanceled = new ArrayList<>();
        List<OrderDto> canceledOrders = new ArrayList<>();

        OrderEntity savedOrder = null; //destinationRepository.save(destinationEntity);

        for (Long id : ids) {
            Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
            orderEntityOptional.ifPresent(ordersToBeCanceled::add);
        }

        for (OrderEntity orderEntity : ordersToBeCanceled) {
            if (orderEntity.getStatusOrder() != OrderStatus.DELIVERED) {
                orderEntity.setStatusOrder(OrderStatus.CANCELED);

                savedOrder = orderRepository.save(orderEntity);

                canceledOrders.add(OrderConverter.fromOrderEntity(savedOrder));
            }
        }

        return canceledOrders;

    }

    public void shipping() throws InterruptedException {
        OrderEntity savedOrder = null;

        List<OrderEntity> ordersThatMatchDeliveryDate = orderRepository.findOrderByDeliveryDate(displayCurrentDate.getTimeInMillis());


        Map<String, List<OrderEntity>> orderToDestination = ordersThatMatchDeliveryDate.stream().
                collect(Collectors.groupingBy(orderEntity -> orderEntity.getDestination().getName()));

//        System.out.println("Today we will be delivering to " + orderToDestination.keySet());
        logger.info("Today we will be delivering to " + orderToDestination.keySet());

        for (String destinationName : orderToDestination.keySet()) {
            courierBean.courierStart(destinationName, orderToDestination.get(destinationName));
        }




        LocalDate newDate = displayCurrentDate.getCurrentDate().plusDays(1);
        displayCurrentDate.setCurrentDate(newDate);
        displayCurrentDate.setTimeInMillis(displayCurrentDate.transformDateToLong(newDate));
        System.out.println(displayCurrentDate.getCurrentDate());
    }

    @Transactional
    public List<OrderDto> addOrders(OrderDto[] orderDtos) throws ParseException {
        List<OrderEntity> orders = new ArrayList<>();

        for (OrderDto orderDto : orderDtos) {
            if (OrderConverter.transformDateToLong(orderDto.getDeliveryDate()) > displayCurrentDate.getTimeInMillis()) {
                OrderEntity orderEntity = createEntityFromMinimalData(orderDto.getDeliveryDate(), orderDto.getDestination());
                orders.add(orderEntity);
            }
        }

        Iterable<OrderEntity> savedOrders = orderRepository.saveAll(orders);

        List<OrderDto> orderDtoList = new ArrayList<>();
        savedOrders.forEach(orderEntity -> orderDtoList.add(OrderConverter.fromOrderEntity(orderEntity)));

        return orderDtoList;
    }

    public OrderEntity createEntityFromMinimalData(String deliveryDate, String destination) {
        Long dateCreated = DateUtils.getDeliveryDateInMillis(deliveryDate);
        DestinationEntity destinationEntity = destinationRepository.findDestinationByName(destination);

        return new OrderEntity(destinationEntity, dateCreated);
    }

    public List<OrderDto> getOrdersByDateAndDestination(String date, String destination) {
        if (Objects.nonNull(date) && Objects.nonNull(destination)) {

            long deliveryDateInMillis = DateUtils.getDeliveryDateInMillis(date);
            DestinationEntity destinationEntity = destinationRepository.findDestinationByName(destination.trim());


            return orderRepository.findOrderByDeliveryDateAndDestination(deliveryDateInMillis, destinationEntity).stream().map(OrderConverter::fromOrderEntity).collect(Collectors.toList());

        } else if (Objects.nonNull(date)) {

            long deliveryDateInMillis = DateUtils.getDeliveryDateInMillis(date);
            return orderRepository.findOrderByDeliveryDate(deliveryDateInMillis).stream().map(OrderConverter::fromOrderEntity).collect(Collectors.toList());

        } else if (Objects.nonNull(destination)) {

            DestinationEntity destinationEntity = destinationRepository.findDestinationByName(destination.trim());
            return orderRepository.findOrderByDeliveryDateAndDestination(displayCurrentDate.getTimeInMillis(), destinationEntity).stream().map(OrderConverter::fromOrderEntity).collect(Collectors.toList());
        }

        return this.getAllOrders();
    }
}
