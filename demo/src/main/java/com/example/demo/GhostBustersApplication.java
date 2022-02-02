package com.example.demo;


import com.example.demo.destination.AddDataToDestinationH2;
import com.example.demo.order.AddDataToOrdersH2;
import com.example.demo.util.DisplayCurrentDate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class GhostBustersApplication {


    private static AddDataToDestinationH2 addDataToDestinationH2;
    private static AddDataToOrdersH2 addDataToOrdersH2;
    private static DisplayCurrentDate displayCurrentDate;

    public GhostBustersApplication(
            AddDataToDestinationH2 addDataToDestinationH2,
            AddDataToOrdersH2 addDataToOrdersH2,
            DisplayCurrentDate displayCurrentDate) {

        GhostBustersApplication.addDataToDestinationH2 = addDataToDestinationH2;
        GhostBustersApplication.addDataToOrdersH2 = addDataToOrdersH2;
        GhostBustersApplication.displayCurrentDate = displayCurrentDate;
    }

    public static void main(String[] args) {
        SpringApplication.run(GhostBustersApplication.class, args);
        System.out.println("New day starting: " + displayCurrentDate.getCurrentDate());
        addDataToDestinationH2.addDestinations();
        addDataToOrdersH2.addOrders();
    }


}


