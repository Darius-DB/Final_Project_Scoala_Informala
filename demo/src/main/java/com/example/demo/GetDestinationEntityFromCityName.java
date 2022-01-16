package com.example.demo;

import com.example.demo.destination.entity.DestinationEntity;
import com.example.demo.destination.repository.DestinationRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetDestinationEntityFromCityName {

    private final DestinationRepository destinationRepository;

    public GetDestinationEntityFromCityName(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public DestinationEntity getDestinationEntityFromCityName(String cityName) {


        List<DestinationEntity> destinationEntities;
        destinationEntities = (List<DestinationEntity>) destinationRepository.findAll();

        DestinationEntity destinationOrder = null;

        for (DestinationEntity destination : destinationEntities) {
            if (destination.getName().equals(cityName)) {
                destinationOrder =  destination;
            }
        }

        return destinationOrder;
    }
}
