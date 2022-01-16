package com.example.demo.destination;

import com.example.demo.destination.entity.DestinationEntity;


import java.util.List;
import java.util.stream.Collectors;

public class DestinationConverter {

    public static DestinationEntity fromDestinationDto(DestinationDto destinationDto) {
        DestinationEntity destinationEntity = new DestinationEntity();

        if (destinationDto.getId() != null) {
            destinationEntity.setId(destinationDto.getId());
        }

        destinationEntity.setName(destinationDto.getCity());
        destinationEntity.setDistance(destinationDto.getDistance());


        return destinationEntity;
    }


    public static DestinationDto fromDestinationEntity(DestinationEntity destinationEntity) {
        DestinationDto destinationDto = new DestinationDto();

        destinationDto.setId(destinationEntity.getId());
        destinationDto.setCity(destinationEntity.getName());
        destinationDto.setDistance(destinationEntity.getDistance());


        return destinationDto;

    }

    private static List<String> getOrdersAsStringList(DestinationEntity destinationEntity) {
        return destinationEntity.getOrders().stream()
                .map(orderEntity -> orderEntity.getDestination().getName())
                .collect(Collectors.toList());
    }
}
