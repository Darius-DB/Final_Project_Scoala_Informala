package com.example.demo.order.repository;

import com.example.demo.destination.entity.DestinationEntity;
import com.example.demo.order.OrderDto;
import com.example.demo.order.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    List<OrderEntity> findOrderByDeliveryDateAndDestination(long date, DestinationEntity destination);
    List<OrderEntity> findOrderByDeliveryDate(long deliveryDateInMillis);
}
