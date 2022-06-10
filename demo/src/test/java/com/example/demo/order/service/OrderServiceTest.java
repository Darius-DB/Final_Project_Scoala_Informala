package com.example.demo.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.destination.entity.DestinationEntity;
import com.example.demo.destination.repository.DestinationRepository;
import com.example.demo.order.CourierBean;
import com.example.demo.order.OrderDto;
import com.example.demo.order.OrderStatus;
import com.example.demo.order.entity.OrderEntity;
import com.example.demo.order.repository.OrderRepository;
import com.example.demo.util.DisplayCurrentDate;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OrderService.class})
@ExtendWith(SpringExtension.class)
class OrderServiceTest {
    @MockBean
    private CourierBean courierBean;

    @MockBean
    private DestinationRepository destinationRepository;

    @MockBean
    private DisplayCurrentDate displayCurrentDate;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    /**
     * Method under test: {@link OrderService#cancelOrders(Long[])}
     */
    @Test
    void testCancelOrders() {
        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDeliveryDate(1L);
        orderEntity.setDestination(destinationEntity);
        orderEntity.setId(123L);
        orderEntity.setLastUpdated(1L);
        orderEntity.setStatusOrder(OrderStatus.NEW);
        Optional<OrderEntity> ofResult = Optional.of(orderEntity);

        DestinationEntity destinationEntity1 = new DestinationEntity();
        destinationEntity1.setDistance(1);
        destinationEntity1.setId(123L);
        destinationEntity1.setName("Name");
        destinationEntity1.setOrders(new ArrayList<>());

        OrderEntity orderEntity1 = new OrderEntity();
        orderEntity1.setDeliveryDate(1L);
        orderEntity1.setDestination(destinationEntity1);
        orderEntity1.setId(123L);
        orderEntity1.setLastUpdated(1L);
        orderEntity1.setStatusOrder(OrderStatus.NEW);
        when(this.orderRepository.save((OrderEntity) any())).thenReturn(orderEntity1);
        when(this.orderRepository.findById((Long) any())).thenReturn(ofResult);
        List<OrderDto> actualCancelOrdersResult = this.orderService.cancelOrders(new Long[]{1L});
        assertEquals(1, actualCancelOrdersResult.size());
        OrderDto getResult = actualCancelOrdersResult.get(0);
        assertEquals(OrderStatus.NEW, getResult.getStatus());
        assertEquals(123L, getResult.getId().longValue());
        assertEquals("Name", getResult.getDestination());
        verify(this.orderRepository).save((OrderEntity) any());
        verify(this.orderRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link OrderService#cancelOrders(Long[])}
     */
    @Test
    void testCancelOrders2() {
        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDeliveryDate(1L);
        orderEntity.setDestination(destinationEntity);
        orderEntity.setId(123L);
        orderEntity.setLastUpdated(1L);
        orderEntity.setStatusOrder(OrderStatus.NEW);
        Optional<OrderEntity> ofResult = Optional.of(orderEntity);

        DestinationEntity destinationEntity1 = new DestinationEntity();
        destinationEntity1.setDistance(1);
        destinationEntity1.setId(123L);
        destinationEntity1.setName("Name");
        destinationEntity1.setOrders(new ArrayList<>());

        DestinationEntity destinationEntity2 = new DestinationEntity();
        destinationEntity2.setDistance(1);
        destinationEntity2.setId(123L);
        destinationEntity2.setName("Name");
        destinationEntity2.setOrders(new ArrayList<>());
        OrderEntity orderEntity1 = mock(OrderEntity.class);
        when(orderEntity1.getDestination()).thenReturn(destinationEntity2);
        when(orderEntity1.getStatusOrder()).thenReturn(OrderStatus.NEW);
        when(orderEntity1.getDeliveryDate()).thenReturn(1L);
        when(orderEntity1.getId()).thenReturn(123L);
        when(orderEntity1.getLastUpdated()).thenReturn(1L);
        doNothing().when(orderEntity1).setDeliveryDate((Long) any());
        doNothing().when(orderEntity1).setDestination((DestinationEntity) any());
        doNothing().when(orderEntity1).setId((Long) any());
        doNothing().when(orderEntity1).setLastUpdated((Long) any());
        doNothing().when(orderEntity1).setStatusOrder((OrderStatus) any());
        orderEntity1.setDeliveryDate(1L);
        orderEntity1.setDestination(destinationEntity1);
        orderEntity1.setId(123L);
        orderEntity1.setLastUpdated(1L);
        orderEntity1.setStatusOrder(OrderStatus.NEW);
        when(this.orderRepository.save((OrderEntity) any())).thenReturn(orderEntity1);
        when(this.orderRepository.findById((Long) any())).thenReturn(ofResult);
        List<OrderDto> actualCancelOrdersResult = this.orderService.cancelOrders(new Long[]{1L});
        assertEquals(1, actualCancelOrdersResult.size());
        OrderDto getResult = actualCancelOrdersResult.get(0);
        assertEquals(OrderStatus.NEW, getResult.getStatus());
        assertEquals(123L, getResult.getId().longValue());
        assertEquals("Name", getResult.getDestination());
        verify(this.orderRepository).save((OrderEntity) any());
        verify(this.orderRepository).findById((Long) any());
        verify(orderEntity1).getDestination();
        verify(orderEntity1).getStatusOrder();
        verify(orderEntity1).getDeliveryDate();
        verify(orderEntity1).getId();
        verify(orderEntity1).getLastUpdated();
        verify(orderEntity1).setDeliveryDate((Long) any());
        verify(orderEntity1).setDestination((DestinationEntity) any());
        verify(orderEntity1).setId((Long) any());
        verify(orderEntity1).setLastUpdated((Long) any());
        verify(orderEntity1).setStatusOrder((OrderStatus) any());
    }

    /**
     * Method under test: {@link OrderService#cancelOrders(Long[])}
     */
    @Test
    void testCancelOrders3() {
        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());
        OrderEntity orderEntity = mock(OrderEntity.class);
        when(orderEntity.getStatusOrder()).thenReturn(OrderStatus.NEW);
        doNothing().when(orderEntity).setDeliveryDate((Long) any());
        doNothing().when(orderEntity).setDestination((DestinationEntity) any());
        doNothing().when(orderEntity).setId((Long) any());
        doNothing().when(orderEntity).setLastUpdated((Long) any());
        doNothing().when(orderEntity).setStatusOrder((OrderStatus) any());
        orderEntity.setDeliveryDate(1L);
        orderEntity.setDestination(destinationEntity);
        orderEntity.setId(123L);
        orderEntity.setLastUpdated(1L);
        orderEntity.setStatusOrder(OrderStatus.NEW);
        Optional<OrderEntity> ofResult = Optional.of(orderEntity);

        DestinationEntity destinationEntity1 = new DestinationEntity();
        destinationEntity1.setDistance(1);
        destinationEntity1.setId(123L);
        destinationEntity1.setName("Name");
        destinationEntity1.setOrders(new ArrayList<>());

        DestinationEntity destinationEntity2 = new DestinationEntity();
        destinationEntity2.setDistance(1);
        destinationEntity2.setId(123L);
        destinationEntity2.setName("Name");
        destinationEntity2.setOrders(new ArrayList<>());
        OrderEntity orderEntity1 = mock(OrderEntity.class);
        when(orderEntity1.getDestination()).thenReturn(destinationEntity2);
        when(orderEntity1.getStatusOrder()).thenReturn(OrderStatus.NEW);
        when(orderEntity1.getDeliveryDate()).thenReturn(1L);
        when(orderEntity1.getId()).thenReturn(123L);
        when(orderEntity1.getLastUpdated()).thenReturn(1L);
        doNothing().when(orderEntity1).setDeliveryDate((Long) any());
        doNothing().when(orderEntity1).setDestination((DestinationEntity) any());
        doNothing().when(orderEntity1).setId((Long) any());
        doNothing().when(orderEntity1).setLastUpdated((Long) any());
        doNothing().when(orderEntity1).setStatusOrder((OrderStatus) any());
        orderEntity1.setDeliveryDate(1L);
        orderEntity1.setDestination(destinationEntity1);
        orderEntity1.setId(123L);
        orderEntity1.setLastUpdated(1L);
        orderEntity1.setStatusOrder(OrderStatus.NEW);
        when(this.orderRepository.save((OrderEntity) any())).thenReturn(orderEntity1);
        when(this.orderRepository.findById((Long) any())).thenReturn(ofResult);
        List<OrderDto> actualCancelOrdersResult = this.orderService.cancelOrders(new Long[]{1L});
        assertEquals(1, actualCancelOrdersResult.size());
        OrderDto getResult = actualCancelOrdersResult.get(0);
        assertEquals(OrderStatus.NEW, getResult.getStatus());
        assertEquals(123L, getResult.getId().longValue());
        assertEquals("Name", getResult.getDestination());
        verify(this.orderRepository).save((OrderEntity) any());
        verify(this.orderRepository).findById((Long) any());
        verify(orderEntity1).getDestination();
        verify(orderEntity1).getStatusOrder();
        verify(orderEntity1).getDeliveryDate();
        verify(orderEntity1).getId();
        verify(orderEntity1).getLastUpdated();
        verify(orderEntity1).setDeliveryDate((Long) any());
        verify(orderEntity1).setDestination((DestinationEntity) any());
        verify(orderEntity1).setId((Long) any());
        verify(orderEntity1).setLastUpdated((Long) any());
        verify(orderEntity1).setStatusOrder((OrderStatus) any());
        verify(orderEntity).getStatusOrder();
        verify(orderEntity).setDeliveryDate((Long) any());
        verify(orderEntity).setDestination((DestinationEntity) any());
        verify(orderEntity).setId((Long) any());
        verify(orderEntity).setLastUpdated((Long) any());
        verify(orderEntity, atLeast(1)).setStatusOrder((OrderStatus) any());
    }

    /**
     * Method under test: {@link OrderService#cancelOrders(Long[])}
     */
    @Test
    void testCancelOrders4() {
        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());
        OrderEntity orderEntity = mock(OrderEntity.class);
        when(orderEntity.getStatusOrder()).thenReturn(OrderStatus.DELIVERED);
        doNothing().when(orderEntity).setDeliveryDate((Long) any());
        doNothing().when(orderEntity).setDestination((DestinationEntity) any());
        doNothing().when(orderEntity).setId((Long) any());
        doNothing().when(orderEntity).setLastUpdated((Long) any());
        doNothing().when(orderEntity).setStatusOrder((OrderStatus) any());
        orderEntity.setDeliveryDate(1L);
        orderEntity.setDestination(destinationEntity);
        orderEntity.setId(123L);
        orderEntity.setLastUpdated(1L);
        orderEntity.setStatusOrder(OrderStatus.NEW);
        Optional<OrderEntity> ofResult = Optional.of(orderEntity);

        DestinationEntity destinationEntity1 = new DestinationEntity();
        destinationEntity1.setDistance(1);
        destinationEntity1.setId(123L);
        destinationEntity1.setName("Name");
        destinationEntity1.setOrders(new ArrayList<>());

        DestinationEntity destinationEntity2 = new DestinationEntity();
        destinationEntity2.setDistance(1);
        destinationEntity2.setId(123L);
        destinationEntity2.setName("Name");
        destinationEntity2.setOrders(new ArrayList<>());
        OrderEntity orderEntity1 = mock(OrderEntity.class);
        when(orderEntity1.getDestination()).thenReturn(destinationEntity2);
        when(orderEntity1.getStatusOrder()).thenReturn(OrderStatus.NEW);
        when(orderEntity1.getDeliveryDate()).thenReturn(1L);
        when(orderEntity1.getId()).thenReturn(123L);
        when(orderEntity1.getLastUpdated()).thenReturn(1L);
        doNothing().when(orderEntity1).setDeliveryDate((Long) any());
        doNothing().when(orderEntity1).setDestination((DestinationEntity) any());
        doNothing().when(orderEntity1).setId((Long) any());
        doNothing().when(orderEntity1).setLastUpdated((Long) any());
        doNothing().when(orderEntity1).setStatusOrder((OrderStatus) any());
        orderEntity1.setDeliveryDate(1L);
        orderEntity1.setDestination(destinationEntity1);
        orderEntity1.setId(123L);
        orderEntity1.setLastUpdated(1L);
        orderEntity1.setStatusOrder(OrderStatus.NEW);
        when(this.orderRepository.save((OrderEntity) any())).thenReturn(orderEntity1);
        when(this.orderRepository.findById((Long) any())).thenReturn(ofResult);
        assertTrue(this.orderService.cancelOrders(new Long[]{1L}).isEmpty());
        verify(this.orderRepository).findById((Long) any());
        verify(orderEntity1).setDeliveryDate((Long) any());
        verify(orderEntity1).setDestination((DestinationEntity) any());
        verify(orderEntity1).setId((Long) any());
        verify(orderEntity1).setLastUpdated((Long) any());
        verify(orderEntity1).setStatusOrder((OrderStatus) any());
        verify(orderEntity).getStatusOrder();
        verify(orderEntity).setDeliveryDate((Long) any());
        verify(orderEntity).setDestination((DestinationEntity) any());
        verify(orderEntity).setId((Long) any());
        verify(orderEntity).setLastUpdated((Long) any());
        verify(orderEntity).setStatusOrder((OrderStatus) any());
    }

    /**
     * Method under test: {@link OrderService#shipping()}
     */
    @Test
    void testShipping() {
        // TODO: Complete this test.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     OrderService.courierBean
        //     OrderService.destinationRepository
        //     OrderService.displayCurrentDate
        //     OrderService.orderRepository

        when(this.orderRepository.findOrderByDeliveryDate(anyLong())).thenReturn(new ArrayList<>());
        when(this.displayCurrentDate.transformDateToLong((LocalDate) any())).thenReturn(1L);
        doNothing().when(this.displayCurrentDate).setCurrentDate((LocalDate) any());
        doNothing().when(this.displayCurrentDate).setTimeInMillis((Long) any());
        when(this.displayCurrentDate.getTimeInMillis()).thenReturn(10L);
        when(this.displayCurrentDate.getCurrentDate()).thenReturn(LocalDate.ofEpochDay(1L));
        this.orderService.shipping();
    }

    /**
     * Method under test: {@link OrderService#shipping()}
     */
    @Test
    void testShipping2() {
        // TODO: Complete this test.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     OrderService.courierBean
        //     OrderService.destinationRepository
        //     OrderService.displayCurrentDate
        //     OrderService.orderRepository

        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDeliveryDate(1L);
        orderEntity.setDestination(destinationEntity);
        orderEntity.setId(123L);
        orderEntity.setLastUpdated(1L);
        orderEntity.setStatusOrder(OrderStatus.NEW);

        ArrayList<OrderEntity> orderEntityList = new ArrayList<>();
        orderEntityList.add(orderEntity);
        when(this.orderRepository.findOrderByDeliveryDate(anyLong())).thenReturn(orderEntityList);
        when(this.displayCurrentDate.transformDateToLong((LocalDate) any())).thenReturn(1L);
        doNothing().when(this.displayCurrentDate).setCurrentDate((LocalDate) any());
        doNothing().when(this.displayCurrentDate).setTimeInMillis((Long) any());
        when(this.displayCurrentDate.getTimeInMillis()).thenReturn(10L);
        when(this.displayCurrentDate.getCurrentDate()).thenReturn(LocalDate.ofEpochDay(1L));
        doNothing().when(this.courierBean).courierStart((String) any(), (java.util.List<OrderEntity>) any());
        this.orderService.shipping();
    }

    /**
     * Method under test: {@link OrderService#shipping()}
     */
    @Test
    void testShipping3() {
        // TODO: Complete this test.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     OrderService.courierBean
        //     OrderService.destinationRepository
        //     OrderService.displayCurrentDate
        //     OrderService.orderRepository

        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDeliveryDate(1L);
        orderEntity.setDestination(destinationEntity);
        orderEntity.setId(123L);
        orderEntity.setLastUpdated(1L);
        orderEntity.setStatusOrder(OrderStatus.NEW);

        DestinationEntity destinationEntity1 = new DestinationEntity();
        destinationEntity1.setDistance(1);
        destinationEntity1.setId(123L);
        destinationEntity1.setName("Name");
        destinationEntity1.setOrders(new ArrayList<>());

        OrderEntity orderEntity1 = new OrderEntity();
        orderEntity1.setDeliveryDate(1L);
        orderEntity1.setDestination(destinationEntity1);
        orderEntity1.setId(123L);
        orderEntity1.setLastUpdated(1L);
        orderEntity1.setStatusOrder(OrderStatus.NEW);

        ArrayList<OrderEntity> orderEntityList = new ArrayList<>();
        orderEntityList.add(orderEntity1);
        orderEntityList.add(orderEntity);
        when(this.orderRepository.findOrderByDeliveryDate(anyLong())).thenReturn(orderEntityList);
        when(this.displayCurrentDate.transformDateToLong((LocalDate) any())).thenReturn(1L);
        doNothing().when(this.displayCurrentDate).setCurrentDate((LocalDate) any());
        doNothing().when(this.displayCurrentDate).setTimeInMillis((Long) any());
        when(this.displayCurrentDate.getTimeInMillis()).thenReturn(10L);
        when(this.displayCurrentDate.getCurrentDate()).thenReturn(LocalDate.ofEpochDay(1L));
        doNothing().when(this.courierBean).courierStart((String) any(), (java.util.List<OrderEntity>) any());
        this.orderService.shipping();
    }

    /**
     * Method under test: {@link OrderService#shipping()}
     */
    @Test
    void testShipping4() {
        // TODO: Complete this test.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     OrderService.courierBean
        //     OrderService.destinationRepository
        //     OrderService.displayCurrentDate
        //     OrderService.orderRepository

        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());

        DestinationEntity destinationEntity1 = new DestinationEntity();
        destinationEntity1.setDistance(1);
        destinationEntity1.setId(123L);
        destinationEntity1.setName("Name");
        destinationEntity1.setOrders(new ArrayList<>());
        OrderEntity orderEntity = mock(OrderEntity.class);
        when(orderEntity.getDestination()).thenReturn(destinationEntity1);
        doNothing().when(orderEntity).setDeliveryDate((Long) any());
        doNothing().when(orderEntity).setDestination((DestinationEntity) any());
        doNothing().when(orderEntity).setId((Long) any());
        doNothing().when(orderEntity).setLastUpdated((Long) any());
        doNothing().when(orderEntity).setStatusOrder((OrderStatus) any());
        orderEntity.setDeliveryDate(1L);
        orderEntity.setDestination(destinationEntity);
        orderEntity.setId(123L);
        orderEntity.setLastUpdated(1L);
        orderEntity.setStatusOrder(OrderStatus.NEW);

        ArrayList<OrderEntity> orderEntityList = new ArrayList<>();
        orderEntityList.add(orderEntity);
        when(this.orderRepository.findOrderByDeliveryDate(anyLong())).thenReturn(orderEntityList);
        when(this.displayCurrentDate.transformDateToLong((LocalDate) any())).thenReturn(1L);
        doNothing().when(this.displayCurrentDate).setCurrentDate((LocalDate) any());
        doNothing().when(this.displayCurrentDate).setTimeInMillis((Long) any());
        when(this.displayCurrentDate.getTimeInMillis()).thenReturn(10L);
        when(this.displayCurrentDate.getCurrentDate()).thenReturn(LocalDate.ofEpochDay(1L));
        doNothing().when(this.courierBean).courierStart((String) any(), (java.util.List<OrderEntity>) any());
        this.orderService.shipping();
    }

    /**
     * Method under test: {@link OrderService#shipping()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testShipping5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.example.demo.order.service.OrderService.shipping(OrderService.java:79)
        //   In order to prevent shipping()
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   shipping().
        //   See https://diff.blue/R013 to resolve this issue.

        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());
        DestinationEntity destinationEntity1 = mock(DestinationEntity.class);
        when(destinationEntity1.getName()).thenReturn("Name");
        doNothing().when(destinationEntity1).setDistance((Integer) any());
        doNothing().when(destinationEntity1).setId((Long) any());
        doNothing().when(destinationEntity1).setName((String) any());
        doNothing().when(destinationEntity1).setOrders((java.util.List<OrderEntity>) any());
        destinationEntity1.setDistance(1);
        destinationEntity1.setId(123L);
        destinationEntity1.setName("Name");
        destinationEntity1.setOrders(new ArrayList<>());
        OrderEntity orderEntity = mock(OrderEntity.class);
        when(orderEntity.getDestination()).thenReturn(destinationEntity1);
        doNothing().when(orderEntity).setDeliveryDate((Long) any());
        doNothing().when(orderEntity).setDestination((DestinationEntity) any());
        doNothing().when(orderEntity).setId((Long) any());
        doNothing().when(orderEntity).setLastUpdated((Long) any());
        doNothing().when(orderEntity).setStatusOrder((OrderStatus) any());
        orderEntity.setDeliveryDate(1L);
        orderEntity.setDestination(destinationEntity);
        orderEntity.setId(123L);
        orderEntity.setLastUpdated(1L);
        orderEntity.setStatusOrder(OrderStatus.NEW);

        ArrayList<OrderEntity> orderEntityList = new ArrayList<>();
        orderEntityList.add(orderEntity);
        when(this.orderRepository.findOrderByDeliveryDate(anyLong())).thenReturn(orderEntityList);
        when(this.displayCurrentDate.transformDateToLong((LocalDate) any())).thenReturn(1L);
        doNothing().when(this.displayCurrentDate).setCurrentDate((LocalDate) any());
        doNothing().when(this.displayCurrentDate).setTimeInMillis((Long) any());
        when(this.displayCurrentDate.getTimeInMillis()).thenReturn(10L);
        when(this.displayCurrentDate.getCurrentDate()).thenReturn(null);
        doNothing().when(this.courierBean).courierStart((String) any(), (java.util.List<OrderEntity>) any());
        this.orderService.shipping();
    }

    /**
     * Method under test: {@link OrderService#addOrders(OrderDto[])}
     */
    @Test
    void testAddOrders() {
        Iterable<OrderEntity> iterable = (Iterable<OrderEntity>) mock(Iterable.class);
        doNothing().when(iterable).forEach((java.util.function.Consumer<OrderEntity>) any());
        when(this.orderRepository.saveAll((Iterable<OrderEntity>) any())).thenReturn(iterable);
        when(this.displayCurrentDate.getTimeInMillis()).thenReturn(10L);

        OrderDto orderDto = new OrderDto();
        orderDto.setDeliveryDate("2020-03-01");
        orderDto.setDestination("Destination");
        orderDto.setId(123L);
        orderDto.setLastUpdated("2020-03-01");
        orderDto.setStatus(OrderStatus.NEW);
        assertTrue(this.orderService.addOrders(new OrderDto[]{orderDto}).isEmpty());
        verify(this.orderRepository).saveAll((Iterable<OrderEntity>) any());
        verify(iterable).forEach((java.util.function.Consumer<OrderEntity>) any());
        verify(this.displayCurrentDate).getTimeInMillis();
    }

    /**
     * Method under test: {@link OrderService#addOrders(OrderDto[])}
     */
    @Test
    void testAddOrders2() {
        Iterable<OrderEntity> iterable = (Iterable<OrderEntity>) mock(Iterable.class);
        doNothing().when(iterable).forEach((java.util.function.Consumer<OrderEntity>) any());
        when(this.orderRepository.saveAll((Iterable<OrderEntity>) any())).thenReturn(iterable);
        when(this.displayCurrentDate.getTimeInMillis()).thenReturn(Long.MIN_VALUE);

        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());
        when(this.destinationRepository.findDestinationByName((String) any())).thenReturn(destinationEntity);

        OrderDto orderDto = new OrderDto();
        orderDto.setDeliveryDate("2020-03-01");
        orderDto.setDestination("Destination");
        orderDto.setId(123L);
        orderDto.setLastUpdated("2020-03-01");
        orderDto.setStatus(OrderStatus.NEW);
        assertTrue(this.orderService.addOrders(new OrderDto[]{orderDto}).isEmpty());
        verify(this.orderRepository).saveAll((Iterable<OrderEntity>) any());
        verify(iterable).forEach((java.util.function.Consumer<OrderEntity>) any());
        verify(this.displayCurrentDate).getTimeInMillis();
        verify(this.destinationRepository).findDestinationByName((String) any());
    }

    /**
     * Method under test: {@link OrderService#addOrders(OrderDto[])}
     */
    @Test
    void testAddOrders3() {
        Iterable<OrderEntity> iterable = (Iterable<OrderEntity>) mock(Iterable.class);
        doNothing().when(iterable).forEach((java.util.function.Consumer<OrderEntity>) any());
        when(this.orderRepository.saveAll((Iterable<OrderEntity>) any())).thenReturn(iterable);
        when(this.displayCurrentDate.getTimeInMillis()).thenReturn(Long.MIN_VALUE);

        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());
        when(this.destinationRepository.findDestinationByName((String) any())).thenReturn(destinationEntity);
        OrderDto orderDto = mock(OrderDto.class);
        when(orderDto.getDeliveryDate()).thenReturn("foo");
        doNothing().when(orderDto).setDeliveryDate((String) any());
        doNothing().when(orderDto).setDestination((String) any());
        doNothing().when(orderDto).setId((Long) any());
        doNothing().when(orderDto).setLastUpdated((String) any());
        doNothing().when(orderDto).setStatus((OrderStatus) any());
        orderDto.setDeliveryDate("2020-03-01");
        orderDto.setDestination("Destination");
        orderDto.setId(123L);
        orderDto.setLastUpdated("2020-03-01");
        orderDto.setStatus(OrderStatus.NEW);
        assertTrue(this.orderService.addOrders(new OrderDto[]{orderDto}).isEmpty());
        verify(this.orderRepository).saveAll((Iterable<OrderEntity>) any());
        verify(iterable).forEach((java.util.function.Consumer<OrderEntity>) any());
        verify(orderDto).getDeliveryDate();
        verify(orderDto).setDeliveryDate((String) any());
        verify(orderDto).setDestination((String) any());
        verify(orderDto).setId((Long) any());
        verify(orderDto).setLastUpdated((String) any());
        verify(orderDto).setStatus((OrderStatus) any());
    }

    /**
     * Method under test: {@link OrderService#createEntityFromMinimalData(String, String)}
     */
    @Test
    void testCreateEntityFromMinimalData() {
        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());
        when(this.destinationRepository.findDestinationByName((String) any())).thenReturn(destinationEntity);
        OrderEntity actualCreateEntityFromMinimalDataResult = this.orderService.createEntityFromMinimalData("2020-03-01",
                "Destination");
        assertEquals(-61956237600000L, actualCreateEntityFromMinimalDataResult.getDeliveryDate().longValue());
        assertEquals(OrderStatus.NEW, actualCreateEntityFromMinimalDataResult.getStatusOrder());
        assertSame(destinationEntity, actualCreateEntityFromMinimalDataResult.getDestination());
        verify(this.destinationRepository).findDestinationByName((String) any());
    }

    /**
     * Method under test: {@link OrderService#createEntityFromMinimalData(String, String)}
     */
    @Test
    void testCreateEntityFromMinimalData2() {
        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());
        when(this.destinationRepository.findDestinationByName((String) any())).thenReturn(destinationEntity);
        OrderEntity actualCreateEntityFromMinimalDataResult = this.orderService.createEntityFromMinimalData("2020/03/01",
                "Destination");
        assertEquals(-1L, actualCreateEntityFromMinimalDataResult.getDeliveryDate().longValue());
        assertEquals(OrderStatus.NEW, actualCreateEntityFromMinimalDataResult.getStatusOrder());
        assertSame(destinationEntity, actualCreateEntityFromMinimalDataResult.getDestination());
        verify(this.destinationRepository).findDestinationByName((String) any());
    }

    /**
     * Method under test: {@link OrderService#getOrdersByDateAndDestination(String, String)}
     */
    @Test
    void testGetOrdersByDateAndDestination() {
        ArrayList<OrderEntity> orderEntityList = new ArrayList<>();
        when(this.orderRepository.findOrderByDeliveryDateAndDestination(anyLong(), (DestinationEntity) any()))
                .thenReturn(orderEntityList);

        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());
        when(this.destinationRepository.findDestinationByName((String) any())).thenReturn(destinationEntity);
        ResponseEntity<List<OrderDto>> actualOrdersByDateAndDestination = this.orderService
                .getOrdersByDateAndDestination("2020-03-01", "Destination");
        assertEquals(orderEntityList, actualOrdersByDateAndDestination.getBody());
        assertEquals(HttpStatus.OK, actualOrdersByDateAndDestination.getStatusCode());
        assertTrue(actualOrdersByDateAndDestination.getHeaders().isEmpty());
        verify(this.orderRepository).findOrderByDeliveryDateAndDestination(anyLong(), (DestinationEntity) any());
        verify(this.destinationRepository).findDestinationByName((String) any());
    }

    /**
     * Method under test: {@link OrderService#getOrdersByDateAndDestination(String, String)}
     */
    @Test
    void testGetOrdersByDateAndDestination2() {
        when(this.orderRepository.findOrderByDeliveryDateAndDestination(anyLong(), (DestinationEntity) any()))
                .thenReturn(new ArrayList<>());

        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());
        when(this.destinationRepository.findDestinationByName((String) any())).thenReturn(destinationEntity);
        ResponseEntity<List<OrderDto>> actualOrdersByDateAndDestination = this.orderService
                .getOrdersByDateAndDestination("2020/03/01", "Destination");
        assertNull(actualOrdersByDateAndDestination.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualOrdersByDateAndDestination.getStatusCode());
        assertTrue(actualOrdersByDateAndDestination.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link OrderService#getOrdersByDateAndDestination(String, String)}
     */
    @Test
    void testGetOrdersByDateAndDestination3() {
        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setDistance(-1);
        destinationEntity.setId(123L);
        destinationEntity.setName("Name");
        destinationEntity.setOrders(new ArrayList<>());

        DestinationEntity destinationEntity1 = new DestinationEntity();
        destinationEntity1.setDistance(1);
        destinationEntity1.setId(123L);
        destinationEntity1.setName("Name");
        destinationEntity1.setOrders(new ArrayList<>());
        OrderEntity orderEntity = mock(OrderEntity.class);
        when(orderEntity.getDestination()).thenReturn(destinationEntity1);
        when(orderEntity.getStatusOrder()).thenReturn(OrderStatus.NEW);
        when(orderEntity.getDeliveryDate()).thenReturn(1L);
        when(orderEntity.getId()).thenReturn(123L);
        when(orderEntity.getLastUpdated()).thenReturn(1L);
        doNothing().when(orderEntity).setDeliveryDate((Long) any());
        doNothing().when(orderEntity).setDestination((DestinationEntity) any());
        doNothing().when(orderEntity).setId((Long) any());
        doNothing().when(orderEntity).setLastUpdated((Long) any());
        doNothing().when(orderEntity).setStatusOrder((OrderStatus) any());
        orderEntity.setDeliveryDate(-1L);
        orderEntity.setDestination(destinationEntity);
        orderEntity.setId(123L);
        orderEntity.setLastUpdated(-1L);
        orderEntity.setStatusOrder(OrderStatus.NEW);

        ArrayList<OrderEntity> orderEntityList = new ArrayList<>();
        orderEntityList.add(orderEntity);
        when(this.orderRepository.findOrderByDeliveryDateAndDestination(anyLong(), (DestinationEntity) any()))
                .thenReturn(orderEntityList);

        DestinationEntity destinationEntity2 = new DestinationEntity();
        destinationEntity2.setDistance(1);
        destinationEntity2.setId(123L);
        destinationEntity2.setName("Name");
        destinationEntity2.setOrders(new ArrayList<>());
        when(this.destinationRepository.findDestinationByName((String) any())).thenReturn(destinationEntity2);
        ResponseEntity<List<OrderDto>> actualOrdersByDateAndDestination = this.orderService
                .getOrdersByDateAndDestination("2020-03-01", "Destination");
        assertTrue(actualOrdersByDateAndDestination.hasBody());
        assertTrue(actualOrdersByDateAndDestination.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualOrdersByDateAndDestination.getStatusCode());
        OrderDto getResult = actualOrdersByDateAndDestination.getBody().get(0);
        assertEquals(OrderStatus.NEW, getResult.getStatus());
        assertEquals(123L, getResult.getId().longValue());
        assertEquals("Name", getResult.getDestination());
        verify(this.orderRepository).findOrderByDeliveryDateAndDestination(anyLong(), (DestinationEntity) any());
        verify(orderEntity).getDestination();
        verify(orderEntity).getStatusOrder();
        verify(orderEntity).getDeliveryDate();
        verify(orderEntity).getId();
        verify(orderEntity).getLastUpdated();
        verify(orderEntity).setDeliveryDate((Long) any());
        verify(orderEntity).setDestination((DestinationEntity) any());
        verify(orderEntity).setId((Long) any());
        verify(orderEntity).setLastUpdated((Long) any());
        verify(orderEntity).setStatusOrder((OrderStatus) any());
        verify(this.destinationRepository).findDestinationByName((String) any());
    }
}

