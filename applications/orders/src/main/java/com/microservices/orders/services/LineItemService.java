package com.microservices.orders.services;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.repositories.LineItemRepository;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Line;
import java.util.List;
import java.util.Optional;

@Service
public class LineItemService {

    private LineItemRepository lineItemRepository;

    public LineItemService(LineItemRepository lineItemRepository) {
        this.lineItemRepository = lineItemRepository;
    }

    public Iterable<LineItem> getAllLineItems(){
        return lineItemRepository.findAll();
    }

    public Optional<LineItem> findLineItemById(Long id){
        return lineItemRepository.findById(id);
    }

    public LineItem createNewLineItem(LineItem lineItem){
        return lineItemRepository.save(lineItem);
    }

    public LineItem updateLineItem(LineItem passedInLineItem){
        Optional<LineItem> lineItemById = lineItemRepository.findById(passedInLineItem.getId());
        LineItem foundLineItem = lineItemById.get();
        foundLineItem.setProductId(passedInLineItem.getProductId());
        foundLineItem.setLineItemTotalPrice(passedInLineItem.getLineItemTotalPrice());
        foundLineItem.setQuantity(passedInLineItem.getQuantity());
        foundLineItem.setShipmentId(passedInLineItem.getShipmentId());
//        foundLineItem.setOrder(passedInLineItem.getOrder());
        foundLineItem.setSingleItemPrice(passedInLineItem.getSingleItemPrice());

        return lineItemRepository.save(foundLineItem);
    }

    public void deleteLineItem(Long id){
        lineItemRepository.deleteById(id);
    }
}