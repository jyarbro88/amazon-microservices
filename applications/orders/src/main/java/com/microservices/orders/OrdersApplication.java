package com.microservices.orders;

import com.microservices.orders.lineItems.LineItem;
import com.microservices.orders.lineItems.LineItemRepository;
import com.microservices.orders.orders.OrderRepository;
import com.microservices.orders.orders.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class OrdersApplication {


    private CalculateUtil calculateUtil = new CalculateUtil();

    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(OrderRepository orderRepository, LineItemRepository lineItemRepository){
        return args -> {

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

            Date newDate = dateFormat.parse("05-12-2018");
            Date newDate2 = dateFormat.parse("03-07-2018");

            Order order = new Order(1L, new Date(), 1L, 2L, 20.23);
            Order order2 = new Order(1L, new Date(), 3L, 2L, 200.25);
            Order order3 = new Order(1L, newDate, 3L, 2L, 200.25);
            Order order4 = new Order(1L, newDate2, 3L, 2L, 200.25);

            orderRepository.save(order);
            orderRepository.save(order2);
            orderRepository.save(order3);
            orderRepository.save(order4);


            LineItem lineItem = new LineItem(1L, 1L, order.getId(), 4, 4.99, calculateUtil.calculatePriceBeforeTax(4.99, 4));
            LineItem lineItem2 = new LineItem(2L, 1L, order.getId(), 2, 4.99, calculateUtil.calculatePriceBeforeTax(4.99, 2));

            lineItemRepository.save(lineItem);
            lineItemRepository.save(lineItem2);
        };
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
