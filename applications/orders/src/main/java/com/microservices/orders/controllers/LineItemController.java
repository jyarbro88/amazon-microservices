package com.microservices.orders.controllers;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.services.LineItem.LineItemService;
import com.microservices.orders.services.LineItem.NewLineItem;
import com.microservices.orders.services.LineItem.UpdateLineItem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/lineItems")
public class LineItemController {

    private LineItemService lineItemService;
    private NewLineItem newLineItem;
    private UpdateLineItem updateLineItem;

    public LineItemController(LineItemService lineItemService, NewLineItem newLineItem, UpdateLineItem updateLineItem) {
        this.lineItemService = lineItemService;
        this.newLineItem = newLineItem;
        this.updateLineItem = updateLineItem;
    }

    @GetMapping
    public Iterable<LineItem> getAllLineItems(){
        return lineItemService.getAllLineItems();
    }

    @GetMapping(value = "/{id}")
    public Optional<LineItem> findLineItemById(
            @PathVariable(value = "id") Long lineItemId
    ){
        return lineItemService.findLineItemById(lineItemId);
    }

    @GetMapping(value = "/{orderId}/lookup")
    public List<LineItem> getLineItemsForOrderId(
            @PathVariable(value = "orderId") Long orderId
    ){
        return lineItemService.findByOrderId(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LineItem postNewLineItem(
            @RequestBody LineItem lineItem
    ){
        return newLineItem.postNew(lineItem);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LineItem updateLineItem(
            @RequestBody LineItem lineItem
    ){
        return updateLineItem.update(lineItem);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLineItem(Long lineItemId){
        lineItemService.deleteLineItem(lineItemId);
    }
}
