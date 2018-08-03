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

    public void deleteLineItem(Long id){
        lineItemRepository.deleteById(id);
    }

    public LineItem createNewLineItem(LineItem lineItem){

        //Todo: set up circuit breaker

        Long productId = lineItem.getProductId();
        TempProductObject tempProduct = restTemplate.getForObject("http://products-service/products/" + productId, TempProductObject.class);
        Double productPrice = tempProduct.getPrice();
        lineItem.setSingleItemPrice(productPrice);

        LineItem lineItemWithPrices = calculatePrices(lineItem);

        return lineItemRepository.save(lineItemWithPrices);
    }

    public LineItem updateLineItem(LineItem lineItem){

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
        if (lineItem.getLineItemTotalPrice() != null){
            foundLineItem.setLineItemTotalPrice(lineItem.getLineItemTotalPrice());
        }

        return lineItemRepository.save(foundLineItem);
    }

    private LineItem calculatePrices(LineItem lineItem) {
        CalculateUtil calculateUtil = new CalculateUtil();

        Long productId = lineItem.getProductId();
        Integer quantity = lineItem.getQuantity();

        //Todo: set up circuit breaker
        TempProductObject tempProduct = restTemplate.getForObject("http://products-service/products/" + productId, TempProductObject.class);

        Double productPrice = tempProduct.getPrice();
        Double totalPrice = calculateUtil.calculatePriceBeforeTax(productPrice, quantity);

        lineItem.setSingleItemPrice(productPrice);
        lineItem.setLineItemTotalPrice(totalPrice);

        return lineItem;
    }
}