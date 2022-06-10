package com.example.demo.order.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.demo.order.OrderDto;
import com.example.demo.order.OrderStatus;
import com.example.demo.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {OrderController.class})
@ExtendWith(SpringExtension.class)
class OrderControllerTest {
    @Autowired
    private OrderController orderController;

    @MockBean
    private OrderService orderService;

    /**
     * Method under test: {@link OrderController#addOrders(OrderDto[])}
     */
    @Test
    void testAddOrders() throws Exception {
        when(this.orderService.addOrders((OrderDto[]) any())).thenReturn(new ArrayList<>());

        OrderDto orderDto = new OrderDto();
        orderDto.setDeliveryDate("2020-03-01");
        orderDto.setDestination("Destination");
        orderDto.setId(123L);
        orderDto.setLastUpdated("2020-03-01");
        orderDto.setStatus(OrderStatus.NEW);
        String content = (new ObjectMapper()).writeValueAsString(new OrderDto[]{orderDto});
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link OrderController#cancelOrders(Long[])}
     */
    @Test
    void testCancelOrders() throws Exception {
        when(this.orderService.cancelOrders((Long[]) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/orders/cancel")
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content((new ObjectMapper()).writeValueAsString(new Long[]{1L}));
        MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link OrderController#shipping()}
     */
    @Test
    void testShipping() throws Exception {
        doNothing().when(this.orderService).shipping();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders/shipping/new-day");
        MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link OrderController#shipping()}
     */
    @Test
    void testShipping2() throws Exception {
        doNothing().when(this.orderService).shipping();
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/orders/shipping/new-day");
        postResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link OrderController#getOrders(String, String)}
     */
    @Test
    void testGetOrders() throws Exception {
        when(this.orderService.getOrdersByDateAndDestination((String) any(), (String) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/status");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    /**
     * Method under test: {@link OrderController#getOrders(String, String)}
     */
    @Test
    void testGetOrders2() throws Exception {
        when(this.orderService.getOrdersByDateAndDestination((String) any(), (String) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/orders/status");
        getResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(getResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }
}

