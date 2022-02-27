package com.example.demo.destination;

import com.example.demo.destination.entity.DestinationEntity;
import com.example.demo.destination.repository.DestinationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AddDataToDestinationH2 {
    private final DestinationRepository destinationRepository;
    private final Logger logger= LoggerFactory.getLogger(AddDataToDestinationH2.class);

    @Autowired
    public AddDataToDestinationH2(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public void addDestinations() {
         List<DestinationEntity> destinationEntityList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader("C:\\Users\\ALEX\\Desktop\\Final_Project_Scoala_Informala\\demo\\src\\main\\resources\\destinations.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    destinationEntityList.add(getDestinationFromCsvLine(line));
                    destinationRepository.saveAll(destinationEntityList);
                } catch (IllegalArgumentException e) {
                    logger.error(e.getMessage());
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

}
