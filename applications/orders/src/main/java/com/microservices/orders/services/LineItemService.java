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

        LineItem lineItemWithPrices = calculatePrices(lineItem);

        return lineItemRepository.save(lineItemWithPrices);
    }

    public LineItem updateLineItem(LineItem lineItem){
        Optional<LineItem> lineItemById = lineItemRepository.findById(lineItem.getId());
        LineItem foundLineItem = lineItemById.get();

        foundLineItem.setProductId(lineItem.getProductId());
        foundLineItem.setQuantity(lineItem.getQuantity());
        foundLineItem.setShipmentId(lineItem.getShipmentId());
        foundLineItem.setSingleItemPrice(lineItem.getSingleItemPrice());
        foundLineItem.setLineItemTotalPrice(lineItem.getLineItemTotalPrice());

        return lineItemRepository.save(foundLineItem);
    }

    public void deleteLineItem(Long id){
        lineItemRepository.deleteById(id);
    }

    private LineItem calculatePrices(LineItem lineItem) {
        CalculateUtil calculateUtil = new CalculateUtil();

        Long productId = lineItem.getProductId();
        Integer quantity = lineItem.getQuantity();
        TempProductObject tempProduct = restTemplate.getForObject("http://products-service/products/" + productId, TempProductObject.class);

        Double productPrice = tempProduct.getPrice();
        Double totalPrice = calculateUtil.calculatePriceBeforeTax(productPrice, quantity);

        lineItem.setSingleItemPrice(productPrice);
        lineItem.setLineItemTotalPrice(totalPrice);

        return lineItem;
    }
}