package com.microservices.orders;

import com.microservices.orders.lineItems.LineItem;
import com.microservices.orders.lineItems.LineItemRepository;
import com.microservices.orders.orders.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class OrdersApplication {

    private CalculateUtil calculateUtil = new CalculateUtil();

    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }

    //Todo:  Calculate Total Price For Order
    //Todo:  Calculate Total Price For Line Item (auto calculate with productId)

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
