package com.example.demo.order.entity;

import com.example.demo.OrderStatus;
import com.example.demo.destination.entity.DestinationEntity;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity(name = "orders")
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private DestinationEntity destination;

    private Long deliveryDate;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus statusOrder = OrderStatus.NEW;

    private Long lastUpdated;


    public OrderEntity(DestinationEntity destination, Long deliveryDate) {
        this.destination = destination;
        this.deliveryDate = deliveryDate;

        ZonedDateTime startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault());
        this.lastUpdated = startOfToday.toEpochSecond() * 1000;
    }

    public OrderEntity() {

    }
}
