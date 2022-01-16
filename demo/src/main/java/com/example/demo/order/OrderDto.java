package com.example.demo.order;

import com.example.demo.OrderStatus;
import com.example.demo.destination.DestinationDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderDto {

    Long id;

    @NotEmpty
    String deliveryDate;

//    @NotEmpty
    String lastUpdated;

//    @NotEmpty
    OrderStatus status;

    @NotEmpty
    String destination;
}
