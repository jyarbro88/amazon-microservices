package com.microservices.orders.services;

public class CalculateUtil {

    public Double calculatePriceBeforeTax(Double price, Integer quantity) {

        return price * quantity;
    }

    public Double calculatePriceAfterTax(Double totalBeforeTaxes) {

        Double taxAmount = .0978;
        Double totalTaxes = totalBeforeTaxes * taxAmount;

        return totalBeforeTaxes + totalTaxes;
    }
}
