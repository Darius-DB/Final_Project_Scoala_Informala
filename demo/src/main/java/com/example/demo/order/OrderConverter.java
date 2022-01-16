package com.example.demo.order;


import com.example.demo.destination.entity.DestinationEntity;
import com.example.demo.order.entity.OrderEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class OrderConverter {

    public static OrderEntity fromOrderEntityDto(OrderDto orderDto) throws ParseException {
        OrderEntity orderEntity = new OrderEntity();

        if (orderDto.getId() != null) {
            orderEntity.setId(orderDto.getId());
        }

        orderEntity.setStatusOrder(orderDto.getStatus());
        orderEntity.setDeliveryDate(transformDateToLong(orderDto.getDeliveryDate()));
        orderEntity.setLastUpdated(transformDateToLong(orderDto.getLastUpdated()));
        orderEntity.setDestination(getDestinationEntityFromDto(orderDto));

        return orderEntity;
    }

    private static DestinationEntity getDestinationEntityFromDto(OrderDto orderDto) {
        return new DestinationEntity(orderDto.getDestination());
    }

    public static Long transformDateToLong(String dateCreated) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Date deliveryDate = sdf.parse(dateCreated);
        return deliveryDate.getTime();
    }

    public static OrderDto fromOrderEntity(OrderEntity orderEntity) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(orderEntity.getId());
        orderDto.setStatus(orderEntity.getStatusOrder());
        orderDto.setDeliveryDate(convertLongDateToString(orderEntity.getDeliveryDate()));
        orderDto.setLastUpdated(convertLongDateToString(orderEntity.getLastUpdated()));
        orderDto.setDestination(orderEntity.getDestination().getName());

        return orderDto;

    }

    private static String convertLongDateToString(Long deliveryDate) {
        LocalDate date =
                Instant.ofEpochMilli(deliveryDate).atZone(ZoneId.systemDefault()).toLocalDate();

//        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
        return date.format(formatter);
    }
}
