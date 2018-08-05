package com.microservices.orders.services.Order;

import com.microservices.orders.circuits.ProductCircuitBreaker;
import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.temp.TempProduct;
import com.microservices.orders.services.LineItem.LineItemService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductInfo {

    private LineItemService lineItemService;
    private ProductCircuitBreaker productCircuitBreaker;

    public ProductInfo(LineItemService lineItemService, ProductCircuitBreaker productCircuitBreaker) {
        this.lineItemService = lineItemService;
        this.productCircuitBreaker = productCircuitBreaker;
    }

    public List<TempProduct> infoForOrderId(Long orderId) {

        List<LineItem> lineItemsForOrder = lineItemService.findByOrderId(orderId);

        List<TempProduct> productsForOrder = new ArrayList<>();

        for (LineItem lineItem : lineItemsForOrder) {

            Long productId = lineItem.getProductId();
            TempProduct returnedProduct = productCircuitBreaker.getTempProductWithId(lineItem.getProductId());
            returnedProduct.setId(productId);
            productsForOrder.add(returnedProduct);
        }

        return productsForOrder;
    }
}
