package com.example.demo.destination.entity;

import com.example.demo.order.entity.OrderEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "destinations")
@Data
public class DestinationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Integer distance;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private List<OrderEntity> orders;


    public DestinationEntity(String name, Integer distance) {
        this.name = name;
        this.distance = distance;
    }

    public DestinationEntity(String name) {
        this.name = name;
    }



    public DestinationEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
