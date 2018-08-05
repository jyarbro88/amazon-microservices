package com.microservices.orders.services;

import com.microservices.orders.circuits.ProductCircuitBreaker;
import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.Order;
import com.microservices.orders.models.temp.TempProduct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculateUtil {

    private ProductCircuitBreaker productCircuitBreaker;

    public CalculateUtil(ProductCircuitBreaker productCircuitBreaker) {
        this.productCircuitBreaker = productCircuitBreaker;
    }

    private Double multiplyPriceByQuantity(Double price, Integer quantity) { return price * quantity; }

    public Double orderTotalPrice(Order order){

        Double totalPrice = 0.00;

        List<LineItem> lineItems = order.getLineItems();
        for (LineItem lineItem : lineItems) {
            Double lineItemTotalPrice = lineItem.getTotalPrice();
            totalPrice += lineItemTotalPrice;
        }

        return totalPrice;
    }

    public LineItem lineItemTotalPrice(LineItem lineItem) {

        Long productId = lineItem.getProductId();
        Integer quantity = lineItem.getQuantity();

        TempProduct tempProduct = productCircuitBreaker.getTempProductWithId(productId);

        Double productPrice = tempProduct.getPrice();

        Double lineItemTotalPrice = multiplyPriceByQuantity(productPrice, quantity);

        lineItem.setSingleItemPrice(productPrice);
        lineItem.setTotalPrice(lineItemTotalPrice);

        return lineItem;
    }

    public Double priceAfterTaxes(Double totalBeforeTaxes) {

        Double taxAmount = .0978;

        Double totalTaxes = totalBeforeTaxes * taxAmount;

        return totalBeforeTaxes + totalTaxes;
    }
}
