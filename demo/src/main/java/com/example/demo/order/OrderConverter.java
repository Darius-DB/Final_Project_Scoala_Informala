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
import java.util.Optional;


public class OrderConverter {

    public static OrderEntity fromOrderEntityDto(OrderDto orderDto) throws ParseException {
        OrderEntity orderEntity = new OrderEntity();

        if (orderDto.getId() != null) {
            orderEntity.setId(orderDto.getId());
        }
        orderEntity.setStatusOrder(orderDto.getStatus());
        orderEntity.setDeliveryDate(transformDateToLong(orderDto.getDeliveryDate()).orElse(null));
        orderEntity.setLastUpdated(transformDateToLong(orderDto.getLastUpdated()).orElse(null));
        orderEntity.setDestination(getDestinationEntityFromDto(orderDto));
        return orderEntity;
    }

    private static DestinationEntity getDestinationEntityFromDto(OrderDto orderDto) {
        return new DestinationEntity(orderDto.getDestination());
    }

    public static Optional<Long> transformDateToLong(String dateCreated)  {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Date deliveryDate = null;
        try {
            deliveryDate = sdf.parse(dateCreated);
        } catch (ParseException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(deliveryDate.getTime());
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
        return date.format(formatter);
    }
}
