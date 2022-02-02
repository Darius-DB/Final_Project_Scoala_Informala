package com.example.demo.destination.service;

import com.example.demo.destination.controller.DestinationInfoContributor;
import com.example.demo.destination.DestinationConverter;
import com.example.demo.destination.DestinationDto;
import com.example.demo.destination.entity.DestinationEntity;
import com.example.demo.destination.repository.DestinationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final DestinationInfoContributor destinationInfoContributor;

    public DestinationService(DestinationRepository destinationRepository, DestinationInfoContributor destinationInfoContributor) {
        this.destinationRepository = destinationRepository;
        this.destinationInfoContributor = destinationInfoContributor;
    }

    @Transactional
    public DestinationDto addDestination(DestinationDto destinationDto) {

        DestinationEntity destinationEntity = DestinationConverter.fromDestinationDto(destinationDto);
        DestinationEntity savedDestination = destinationRepository.save(destinationEntity);

        return DestinationConverter.fromDestinationEntity(savedDestination);
    }

    public ResponseEntity<DestinationDto> updateDestination(DestinationDto payload) {
        if (payload.getId() == null) {
            destinationInfoContributor.incrementNoOfFailedDestinationUpdates();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (destinationRepository.findById(payload.getId()).isPresent()) {
            DestinationEntity savedEntity = destinationRepository.save(DestinationConverter.fromDestinationDto(payload));
            DestinationDto studentDto = DestinationConverter.fromDestinationEntity(savedEntity);
            return new ResponseEntity<>(studentDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public List<DestinationDto> findDestinationById(Long id) {
        Optional<DestinationEntity> destinationEntityOptional = destinationRepository.findById(id);
        return destinationEntityOptional.map(destinationEntity -> Collections.singletonList(
                DestinationConverter.fromDestinationEntity(destinationEntity))).orElse(Collections.emptyList());
    }

    public List<DestinationDto> getAllDestinations() {
        return StreamSupport.stream(destinationRepository.findAll().spliterator(), false)
                .map(DestinationConverter::fromDestinationEntity)
                .collect(Collectors.toList());
    }

    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }


}
