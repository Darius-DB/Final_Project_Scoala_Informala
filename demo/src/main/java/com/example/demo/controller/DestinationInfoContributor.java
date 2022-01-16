package com.example.demo.controller;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DestinationInfoContributor implements InfoContributor {

    int noOfGeneratedProofs;
    int noOfFailedDestinationUpdates;

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("generated-proofs", noOfGeneratedProofs);
        stats.put("failed-updates", noOfFailedDestinationUpdates);

        builder.withDetail("destinations", stats);
    }

    public void incrementNoOfGeneratedProofs() {
        this.noOfGeneratedProofs++;
    }

    public void incrementNoOfFailedDestinationUpdates() {
        this.noOfFailedDestinationUpdates++;
    }

}
