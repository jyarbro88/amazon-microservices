package com.microservices.orders.controllers;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.temp.TempProductObject;
import com.microservices.orders.services.LineItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class LineItemController {

    private LineItemService lineItemService;
    private RestTemplate restTemplate;

    public LineItemController(LineItemService lineItemService, RestTemplate restTemplate) {
        this.lineItemService = lineItemService;
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/lineItems")
    public Iterable<LineItem> getAllLineItems(){
        return lineItemService.getAllLineItems();
    }

    @GetMapping(value = "/lineItems/{id}")
    public Optional<LineItem> getLineItemById(
            @PathVariable(value = "id") Long lineItemId
    ){
        return lineItemService.findLineItemById(lineItemId);
    }

    @GetMapping(value = "/lineItems/{orderId}/lookup")
    public List<LineItem> findLineItemsForOrderId(
            @PathVariable(value = "orderId") Long orderId
    ){
        return lineItemService.findByOrderId(orderId);
    }

    @PostMapping(value = "/lineItems")
    @ResponseStatus(HttpStatus.CREATED)
    public LineItem createNewLineItem(
            @RequestBody LineItem lineItem
    ){
        Long productId = lineItem.getProductId();

        TempProductObject tempProduct = restTemplate.getForObject("http://products-service/products/" + productId, TempProductObject.class);

        Double productPrice = tempProduct.getPrice();

        lineItem.setSingleItemPrice(productPrice);

        return lineItemService.createNewLineItem(lineItem);
    }

    @PutMapping(value = "/lineItems")
    @ResponseStatus(HttpStatus.CREATED)
    public LineItem updateLineItem(
            @RequestBody LineItem lineItem
    ){
        return lineItemService.updateLineItem(lineItem);
    }

    @DeleteMapping(value = "/lineItems")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLineItem(Long lineItemId){
        lineItemService.deleteLineItem(lineItemId);
    }
}
