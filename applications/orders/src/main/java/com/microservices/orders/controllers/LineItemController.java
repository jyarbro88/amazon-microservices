package com.microservices.orders.controllers;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.services.LineItem.LineItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LineItemController {

    private LineItemService lineItemService;

    public LineItemController(LineItemService lineItemService) {
        this.lineItemService = lineItemService;
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
