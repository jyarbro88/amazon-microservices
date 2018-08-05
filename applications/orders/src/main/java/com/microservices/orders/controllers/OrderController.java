package com.microservices.orders.controllers;

import com.microservices.orders.models.Order;
import com.microservices.orders.models.display.DisplayOrderDetails;
import com.microservices.orders.models.temp.TempProduct;
import com.microservices.orders.services.Order.OrderDetailsService;
import com.microservices.orders.services.Order.OrderService;
import com.microservices.orders.services.Order.UpdateOrder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Todo:  Create operations for new Order which creates a new person, a new address and then links the address, and account id to the order when saving
//Todo:  Calculate Total Prices with utility class and not manually
//Todo:  Link Account Address with Account when creating new, then remove from AccountApplication run file
//Todo:  Add RequestMapping to the base of LineItemController and remove from all HTTPMethods

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private OrderService orderService;
    private OrderDetailsService orderDetailsService;
    private UpdateOrder updateOrderService;

    public OrderController(OrderService orderService, OrderDetailsService orderDetailsService, UpdateOrder updateOrderService) {
        this.orderService = orderService;
        this.orderDetailsService = orderDetailsService;
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
        return orderService.findProductInfoForOrderId(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createNewOrder(
            @RequestBody Order order
    ){
        return orderService.createNewOrder(order);
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