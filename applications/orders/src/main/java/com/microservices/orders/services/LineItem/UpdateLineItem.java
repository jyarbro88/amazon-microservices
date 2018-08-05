package com.microservices.orders.services.LineItem;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.repositories.LineItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateLineItem {

    private LineItemRepository lineItemRepository;

    public UpdateLineItem(LineItemRepository lineItemRepository) {
        this.lineItemRepository = lineItemRepository;
    }

    public LineItem update(LineItem lineItem){

        Optional<LineItem> lineItemById = lineItemRepository.findById(lineItem.getId());
        LineItem foundLineItem = lineItemById.get();

        if (lineItem.getProductId() != null){
            foundLineItem.setProductId(lineItem.getProductId());
        }
        if (lineItem.getQuantity() != null){
            foundLineItem.setQuantity(lineItem.getQuantity());
        }
        if (lineItem.getShipmentId() != null){
            foundLineItem.setShipmentId(lineItem.getShipmentId());
        }
        if (lineItem.getSingleItemPrice() != null){
            foundLineItem.setSingleItemPrice(lineItem.getSingleItemPrice());
        }
        if (lineItem.getTotalPrice() != null){
            foundLineItem.setTotalPrice(lineItem.getTotalPrice());
        }

        return lineItemRepository.save(foundLineItem);
    }
}
