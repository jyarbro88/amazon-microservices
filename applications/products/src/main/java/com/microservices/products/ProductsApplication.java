package com.microservices.products;

import com.microservices.products.products.Product;
import com.microservices.products.products.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductsApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProductsApplication.class, args);

    }

    @Bean
    public CommandLineRunner run(ProductRepository productRepository){
        return args -> {

            Product product = new Product("Box of Pens", "25 pack", 4.99);
            Product product2 = new Product("Box of Pencils", "25 pack", 2.99);
            Product product3 = new Product("iPad Pro", "10.7 inch", 649.99);
            Product product4 = new Product("iWatch", "Red", 549.99);

            List<Product> newProductsList = new ArrayList<>();

            newProductsList.add(product);
            newProductsList.add(product2);
            newProductsList.add(product3);
            newProductsList.add(product4);

            productRepository.saveAll(newProductsList);

        };
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
