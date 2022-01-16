package com.example.demo.destination;

import com.example.demo.order.entity.OrderEntity;
import lombok.Data;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class DestinationDto {

    Long id;

    @NotEmpty(message = "City must not be empty")
    String city;

    @NotEmpty(message = "Distance must not be empty")
    Integer distance;


}
