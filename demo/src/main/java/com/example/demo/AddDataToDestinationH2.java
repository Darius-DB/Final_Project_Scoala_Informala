package com.example.demo;

import com.example.demo.destination.entity.DestinationEntity;
import com.example.demo.destination.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AddDataToDestinationH2 {

    private List<DestinationEntity> destinationEntityList = new ArrayList<>();


    private final DestinationRepository destinationRepository;

    @Autowired
    public AddDataToDestinationH2(DestinationRepository destinationRepository) {

        this.destinationRepository = destinationRepository;
    }


    public void addDestinations() {
        try (BufferedReader br = new BufferedReader(
                new FileReader("C:\\Users\\ALEX\\Desktop\\FinalProject (2)\\FinalProject\\FinalProject ()\\FinalProject\\demo\\src\\main\\resources\\destinations.csv"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                try {
                    destinationEntityList.add(getDestinationFromCsvLine(line));
                    destinationRepository.saveAll(destinationEntityList);

                } catch (IllegalArgumentException e) {
                    System.err.println(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected DestinationEntity getDestinationFromCsvLine(String line) {

        String[] destinationAttributes = line.split(",");

        if (destinationAttributes.length != 2) {
            throw new IllegalArgumentException("Corrupted data");
        }

        Integer distance = Integer.parseInt(destinationAttributes[1].trim());


        return new DestinationEntity(destinationAttributes[0].trim(),
                distance);

    }

    public List<DestinationEntity> getDestinationEntityList() {
        return destinationEntityList;
    }
}
