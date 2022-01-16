package com.example.demo;


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

        this.addDataToDestinationH2 = addDataToDestinationH2;
        this.addDataToOrdersH2 = addDataToOrdersH2;
        this.displayCurrentDate = displayCurrentDate;
    }


    public static void main(String[] args) {
        SpringApplication.run(GhostBustersApplication.class, args);
//        System.out.println(DisplayCurrentDate.getCurrentDate());
        System.out.println("New day starting: " + displayCurrentDate.getCurrentDate());

        addDataToDestinationH2.addDestinations();
        addDataToOrdersH2.addOrders();


    }


}


