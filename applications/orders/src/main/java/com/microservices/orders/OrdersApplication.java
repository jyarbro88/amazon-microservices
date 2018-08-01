package com.microservices.orders;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.lineItems.LineItemRepository;
import com.microservices.orders.models.Order;
import com.microservices.orders.orders.OrderRepository;
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

    //Todo:  Need to delete the command line runner stuff
    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(OrderRepository orderRepository, LineItemRepository lineItemRepository){
        return args -> {

            List<LineItem> lineItemList = new ArrayList<>();

            LineItem lineItem = new LineItem(1L, 1L, 1L, 4, 4.00, 16.00);
            LineItem lineItem2 = new LineItem(2L, 1L, 1L, 3, 2.00, 6.00);
            LineItem lineItem3 = new LineItem(1L, 1L, 2L, 3, 3.00, 9.00);

            lineItem.setProductId(1L);
            lineItem2.setProductId(2L);
            lineItem3.setProductId(3L);

            lineItemList.add(lineItem);
            lineItemList.add(lineItem2);
            lineItemList.add(lineItem3);

            lineItemRepository.save(lineItem);
            lineItemRepository.save(lineItem2);
            lineItemRepository.save(lineItem3);

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            Date newDate = dateFormat.parse("05-12-2018");
            Order order = new Order(1L, newDate, 1L, 2L, 20.23, lineItemList);

            orderRepository.save(order);
        };
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
