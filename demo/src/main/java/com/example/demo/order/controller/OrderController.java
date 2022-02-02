package com.example.demo.order.controller;

import com.example.demo.order.OrderDto;

import com.example.demo.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/status")
    public ResponseEntity<List<OrderDto>> getOrders(@RequestParam(required = false) String date,
                                                    @RequestParam(required = false) String destination)  {
        return orderService.getOrdersByDateAndDestination(date, destination);
    }

    @PostMapping("/add")
    public List<OrderDto> addOrders(@Valid @RequestBody OrderDto[] payload) {
        return orderService.addOrders(payload);
    }

    @PostMapping("/cancel")
    public List<OrderDto> cancelOrders(@RequestBody Long[] ids)  {
        return orderService.cancelOrders(ids);
    }

    @PostMapping("/shipping/new-day")
    public void shipping()  {
        orderService.shipping();
    }

}
