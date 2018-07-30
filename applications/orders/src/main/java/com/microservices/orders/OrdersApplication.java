package com.microservices.orders;

import com.microservices.orders.orders.OrderRepository;
import com.microservices.orders.orders.Order;
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
public class OrdersApplication {

    public static void main(String[] args) {

        SpringApplication.run(OrdersApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(OrderRepository orderRepository){
        return args -> {

            Order order = new Order(1L, new Date(), 1L, 2L, 20.23, null);
            Order order2 = new Order(1L, new Date(), 3L, 2L, 200.25, null);

            orderRepository.save(order);
            orderRepository.save(order2);

        };
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
