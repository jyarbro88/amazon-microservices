package com.microservices.orders.lineItems;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/lineItems"})
public class LineItemController {

    @Autowired
    private LineItemRepository lineItemRepository;

    @GetMapping
    public List<LineItem> showAllLineItems(){
        return lineItemRepository.findAll();
    }

    @GetMapping(value = {"/{lineItemId}"})
    public List<LineItem> showIndividualLineItem(
            @PathVariable(name = "lineItemId") Long lineItemId
    ){
        return lineItemRepository.findAllById(lineItemId);
    }

    @PostMapping
    public ResponseEntity newLineItem(
            @RequestBody LineItem lineItem
    ){
        lineItem.setQuantity(lineItem.getQuantity());
        lineItem.setLineItemTotalPrice(lineItem.getLineItemTotalPrice());
        lineItem.setOrderId(lineItem.getOrderId());
        lineItem.setProductId(lineItem.getProductId());
        lineItem.setShipmentId(lineItem.getShipmentId());
        lineItem.setSingleItemPrice(lineItem.getSingleItemPrice());

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateLineItem(){

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteLineItem(){

        return new ResponseEntity(HttpStatus.OK);
    }



}
