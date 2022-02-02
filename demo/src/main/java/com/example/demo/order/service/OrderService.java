package com.example.demo.order.service;

import com.example.demo.util.DateUtils;
import com.example.demo.util.DisplayCurrentDate;
import com.example.demo.order.OrderStatus;
import com.example.demo.destination.entity.DestinationEntity;
import com.example.demo.destination.repository.DestinationRepository;
import com.example.demo.order.CourierBean;
import com.example.demo.order.OrderConverter;
import com.example.demo.order.OrderDto;
import com.example.demo.order.entity.OrderEntity;
import com.example.demo.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final DisplayCurrentDate displayCurrentDate;
    private final DestinationRepository destinationRepository;
    private final CourierBean courierBean;

    public OrderService(OrderRepository orderRepository, DisplayCurrentDate displayCurrentDate, DestinationRepository destinationRepository, CourierBean courierBean) {
        this.orderRepository = orderRepository;
        this.displayCurrentDate = displayCurrentDate;
        this.destinationRepository = destinationRepository;
        this.courierBean = courierBean;
    }


    public List<OrderDto> cancelOrders(Long[] ids) {
        List<OrderEntity> ordersToBeCanceled = new ArrayList<>();
        List<OrderDto> canceledOrders = new ArrayList<>();
        OrderEntity savedOrder;
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

    public void shipping() {
        Map<String, List<OrderEntity>> orderToDestination;
        List<OrderEntity> ordersThatMatchDeliveryDate = orderRepository.findOrderByDeliveryDate(displayCurrentDate.getTimeInMillis());
      if(!ordersThatMatchDeliveryDate.isEmpty()) {
          orderToDestination = ordersThatMatchDeliveryDate.stream().
                  collect(Collectors.groupingBy(orderEntity -> orderEntity.getDestination().getName()));
          logger.info("Today we will be delivering to " + orderToDestination.keySet());
          for (String destinationName : orderToDestination.keySet()) {
              courierBean.courierStart(destinationName, orderToDestination.get(destinationName));
          }
      } else{
          logger.info("There are no orders for " +displayCurrentDate.getCurrentDate() +" !");
      }

        LocalDate newDate = displayCurrentDate.getCurrentDate().plusDays(1);
        displayCurrentDate.setCurrentDate(newDate);
        displayCurrentDate.setTimeInMillis(displayCurrentDate.transformDateToLong(newDate));
        System.out.println(displayCurrentDate.getCurrentDate());
    }

    @Transactional
    public List<OrderDto> addOrders(OrderDto[] orderDtos)  {
        List<OrderEntity> orders = new ArrayList<>();
        for (OrderDto orderDto : orderDtos) {
            Optional<Long> optionalDate = OrderConverter.transformDateToLong(orderDto.getDeliveryDate());
            if(optionalDate.isEmpty()){
                continue;
            }
            if ( optionalDate.get()> displayCurrentDate.getTimeInMillis()) {
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

    public ResponseEntity<List<OrderDto>> getOrdersByDateAndDestination(String date, String destination) {

        if (Objects.isNull(date)) {
            date = new SimpleDateFormat("dd-MM-yyyy").format(Date.from(displayCurrentDate.getCurrentDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        }

        long deliveryDateInMillis = DateUtils.getDeliveryDateInMillis(date);
        if (deliveryDateInMillis == -1) {
            return new ResponseEntity<>(BAD_REQUEST);
        }

        if (Objects.nonNull(destination)) {
            DestinationEntity destinationEntity = destinationRepository.findDestinationByName(destination.trim());
            List<OrderDto> orders = orderRepository.findOrderByDeliveryDateAndDestination(deliveryDateInMillis, destinationEntity).stream()
                    .map(OrderConverter::fromOrderEntity)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(orders, OK);

        } else {
            List<OrderDto> orders = orderRepository.findOrderByDeliveryDate(deliveryDateInMillis).stream()
                    .map(OrderConverter::fromOrderEntity)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(orders, OK);

        }
    }
}
