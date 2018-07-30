package com.microservices.shipments;

import com.microservices.shipments.shipments.Shipment;
import com.microservices.shipments.shipments.ShipmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@SpringBootApplication
@EnableDiscoveryClient
public class ShipmentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShipmentsApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ShipmentRepository shipmentRepository){
        return args -> {

            Shipment shipment = new Shipment(1L, 1L, 1L, 2L, new Date(), null);

            shipmentRepository.save(shipment);
        };
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
