package com.microservices.orders.services.LineItem;

import com.microservices.orders.circuits.ProductCircuitBreaker;
import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.temp.TempProduct;
import com.microservices.orders.repositories.LineItemRepository;
import com.microservices.orders.services.CalculateUtil;
import org.springframework.stereotype.Service;

@Service
public class NewLineItem {

    private LineItemRepository lineItemRepository;
    private CalculateUtil calculateUtil;
    private ProductCircuitBreaker productCircuitBreaker;

    public NewLineItem(LineItemRepository lineItemRepository, CalculateUtil calculateUtil, ProductCircuitBreaker productCircuitBreaker) {
        this.lineItemRepository = lineItemRepository;
        this.calculateUtil = calculateUtil;
        this.productCircuitBreaker = productCircuitBreaker;
    }

    public LineItem postNew(LineItem lineItem){

        TempProduct tempProduct = productCircuitBreaker.getTempProductWithId(lineItem.getProductId());

        Double productPrice = tempProduct.getPrice();

        lineItem.setSingleItemPrice(productPrice);

        LineItem lineItemWithPrices = calculateUtil.lineItemTotalPrice(lineItem);

        return lineItemRepository.save(lineItemWithPrices);
    }
}
