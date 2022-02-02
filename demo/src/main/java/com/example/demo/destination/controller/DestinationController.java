package com.example.demo.destination.controller;

import com.example.demo.destination.DestinationDto;
import com.example.demo.destination.service.DestinationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/destinations")
public class DestinationController {
    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }
    @PostMapping("/add")
    public DestinationDto addDestination(@Valid @RequestBody DestinationDto payload) {
        return destinationService.addDestination(payload);
    }

    @PutMapping("/update")
    public ResponseEntity<DestinationDto> updateDestination(@Valid @RequestBody DestinationDto payload) {
        return destinationService.updateDestination(payload);
    }

    @GetMapping
    public List<DestinationDto> getDestinations() {
        return destinationService.getAllDestinations();
    }

    @GetMapping("/{id}")
    public List<DestinationDto> getDestinationById(@PathVariable("id") Long id) {
        return destinationService.findDestinationById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteDestinationById(@PathVariable("id") Long id) {
        destinationService.deleteDestination(id);
    }


}
