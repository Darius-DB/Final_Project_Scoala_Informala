package com.example.demo.destination.repository;

import com.example.demo.destination.entity.DestinationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends CrudRepository<DestinationEntity, Long> {

        public DestinationEntity findDestinationByName(String cityName);
}
