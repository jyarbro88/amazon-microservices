package com.microservices.orders.controllers;

import com.microservices.orders.models.Order;
import com.microservices.orders.models.display.DisplayOrderDetails;
import com.microservices.orders.models.temp.TempProduct;
import com.microservices.orders.services.Order.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private OrderService orderService;
    private OrderDetails orderDetailsService;
    private NewOrder newOrderService;
    private ProductInfo productInfoService;
    private UpdateOrder updateOrderService;

    public OrderController(OrderService orderService, OrderDetails orderDetailsService, NewOrder newOrderService, ProductInfo productInfoService, UpdateOrder updateOrderService) {
        this.orderService = orderService;
        this.orderDetailsService = orderDetailsService;
        this.newOrderService = newOrderService;
        this.productInfoService = productInfoService;
        this.updateOrderService = updateOrderService;
    }


    @GetMapping(value = "/all")
    public Iterable<Order> getAllOrders(){
        return orderService.getAll();
    }

    @GetMapping
    public List<Order> findAllOrdersByAccountId(
            @RequestParam(value = "accountId") Long accountId
    ) {
        return orderService.findAllOrdersByAccountIdOrderByDate(accountId);
    }

    @GetMapping(value = "/{id}")
    public DisplayOrderDetails findDetailsByOrderId(
            @PathVariable(value = "id") Long orderId
    ){
        return orderDetailsService.findDetailsByOrderId(orderId);
    }

    @GetMapping(value = "/getProductInfo/{orderId}", produces = "application/json")
    public List<TempProduct> findProductInfoForOrderId(
            @PathVariable(value = "orderId") Long orderId
    ){
        return productInfoService.infoForOrderId(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order postNewOrder(
            @RequestBody Order order
    ){
        return newOrderService.postNew(order);
    }

    @PutMapping(value = "/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Order updateOrder(
            @RequestBody Order order
    ){
        return updateOrderService.updateOrder(order);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable(value = "id") Long orderId){
        orderService.deleteOrder(orderId);
    }
}