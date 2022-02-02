package com.example.demo.destination.controller;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DestinationInfoContributor implements InfoContributor {

    int noOfFailedDestinationUpdates;

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("failed-updates", noOfFailedDestinationUpdates);

        builder.withDetail("destinations", stats);
    }

    public void incrementNoOfFailedDestinationUpdates() {
        this.noOfFailedDestinationUpdates++;
    }

}
