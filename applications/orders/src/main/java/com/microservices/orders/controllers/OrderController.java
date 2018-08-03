package com.microservices.orders.controllers;

import com.microservices.orders.models.Order;
import com.microservices.orders.models.display.OrderToDisplay;
import com.microservices.orders.models.temp.TempProductObject;
import com.microservices.orders.services.OrderDetails;
import com.microservices.orders.services.OrderService;
import com.microservices.orders.services.UpdateOrder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Todo:  Create operations for new Order which links the address, account id when saving
//Todo:  Calculate Total Prices with utility class and not manually
//Todo:  Order Total Price is null
//Todo:  OrderShipmentToDisplay needs order line item id and order id
//Todo:  Link Account Address with Account when creating new, then remove from AccountApplication run file

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private OrderService orderService;
    private OrderDetails orderDetailsService;
    private UpdateOrder updateOrderService;

    public OrderController(OrderService orderService, OrderDetails orderDetailsService, UpdateOrder updateOrderService) {
        this.orderService = orderService;
        this.orderDetailsService = orderDetailsService;
        this.updateOrderService = updateOrderService;
    }

    @GetMapping(value = "/all")
    public Iterable<Order> getAllOrders(){
        return orderService.getAll();
    }

    @GetMapping
    public List<Order> getAllOrdersForAccountId(
            @RequestParam(value = "accountId") Long accountId
    ) {
        return orderService.getAllOrdersForIdOrderByDate(accountId);
    }

    @GetMapping(value = "/{id}")
    public OrderToDisplay getDetailsForOrder(
            @PathVariable(value = "id") Long orderId
    ){
        return orderDetailsService.getOrderDetailsById(orderId);
    }

    @GetMapping(value = "/getProductInfo/{orderId}", produces = "application/json")
    public List<TempProductObject> getInformationForProduct(
            @PathVariable(value = "orderId") Long orderId
    ){
        return orderService.getProductInformation(orderId);
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