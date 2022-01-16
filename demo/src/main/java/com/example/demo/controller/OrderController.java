package com.example.demo.controller;

import com.example.demo.*;

import com.example.demo.destination.entity.DestinationEntity;

import com.example.demo.destination.repository.DestinationRepository;
import com.example.demo.order.OrderDto;

import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
//    private final DisplayCurrentDate displayCurrentDate;
//    private final DestinationRepository destinationRepository;


    @Autowired
    public OrderController(OrderService orderService) {

        this.orderService = orderService;
//        this.displayCurrentDate = displayCurrentDate;
//        this.destinationRepository = destinationRepository;
    }

    @GetMapping("/status")
    public List<OrderDto> getOrders(@RequestParam(required = false) String date,
                                    @RequestParam(required = false) String destination) throws ParseException {

        return orderService.getOrdersByDateAndDestination(date, destination);
    }


    @PostMapping("/add")
    public List<OrderDto> addOrders(@Valid @RequestBody OrderDto[] payload) throws ParseException {
        return orderService.addOrders(payload);
    }


    @PostMapping("/cancel")
    public List<OrderDto> cancelOrders(@RequestBody Long[] ids) throws ParseException {
        return orderService.cancelOrders(ids);
    }


    @PostMapping("/shipping/new-day")
    public void shipping() throws InterruptedException {
        orderService.shipping();
    }

}
