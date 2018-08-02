package com.microservices.orders.services;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.temp.TempProductObject;
import com.microservices.orders.repositories.LineItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class LineItemService {

    private LineItemRepository lineItemRepository;
    private RestTemplate restTemplate;

    public LineItemService(LineItemRepository lineItemRepository, RestTemplate restTemplate) {
        this.lineItemRepository = lineItemRepository;
        this.restTemplate = restTemplate;
    }

    public Iterable<LineItem> getAllLineItems(){
        return lineItemRepository.findAll();
    }

    public List<LineItem> findByOrderId(Long id){
        return lineItemRepository.findAllByOrderId(id);
    }


    public Optional<LineItem> findLineItemById(Long id){
        return lineItemRepository.findById(id);
    }

    public LineItem createNewLineItem(LineItem lineItem){

        getSingleItemPrice(lineItem);

        return lineItemRepository.save(lineItem);
    }

    public LineItem updateLineItem(LineItem passedInLineItem){
        Optional<LineItem> lineItemById = lineItemRepository.findById(passedInLineItem.getId());
        LineItem foundLineItem = lineItemById.get();
        foundLineItem.setProductId(passedInLineItem.getProductId());
        foundLineItem.setQuantity(passedInLineItem.getQuantity());
        foundLineItem.setShipmentId(passedInLineItem.getShipmentId());
//        foundLineItem.setOrder(passedInLineItem.getOrder());
        foundLineItem.setSingleItemPrice(passedInLineItem.getSingleItemPrice());
        foundLineItem.setLineItemTotalPrice(passedInLineItem.getLineItemTotalPrice());

        return lineItemRepository.save(foundLineItem);
    }

    public void deleteLineItem(Long id){
        lineItemRepository.deleteById(id);
    }

    private void getSingleItemPrice(LineItem lineItem) {
        Long productId = lineItem.getProductId();

        TempProductObject tempProduct = restTemplate.getForObject("http://products-service/products/" + productId, TempProductObject.class);

        Double productPrice = tempProduct.getPrice();

        lineItem.setSingleItemPrice(productPrice);
    }
}