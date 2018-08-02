package com.microservices.shipments;

import com.microservices.shipments.shipments.Shipment;
import com.microservices.shipments.shipments.ShipmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class ShipmentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShipmentsApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ShipmentRepository shipmentRepository){
        return args -> {

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            Date newDate = dateFormat.parse("05-12-2018");
            Date deliveryDate = dateFormat.parse("05-16-2018");

            Shipment shipment = new Shipment(1L, 1L, 1L, 1L, newDate, deliveryDate);

            shipmentRepository.save(shipment);


        };
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
