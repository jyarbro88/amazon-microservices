package com.microservices.orders.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/orders"})
public class OrderController {

    @Autowired
    OrderRepository orderRepository;


//    @GetMapping
//    public List<Order> showAllOrders(){
//        return orderRepository.findAll();
//    }
//
//    @PostMapping(consumes = {"application/json"})
//    public ResponseEntity addNewOrder(
//            @RequestBody Order order
//    ){
//
//        orderRepository.save(order);
//
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @PutMapping(value = {"/{orderId}"})
//    public ResponseEntity updateOrder(
//            @PathVariable(value = "orderId") Long orderid,
//            @RequestBody Order order
//    ){
//
//        orderRepository.save(order);
//
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @DeleteMapping
//    public ResponseEntity deleteOrder(){
//
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @GetMapping
    public List<Order> getAllOrdersForAccountId(
            @RequestParam(value = "accountId") Long accountId
    ){
        return orderRepository.findAllByAccountIdOrderByOrderDate(accountId);
    }

}
