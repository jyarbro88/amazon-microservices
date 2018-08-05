package com.microservices.orders.services;

public class CalculateUtil {

    public Double calculateSingleLineItemTotalPriceBeforeTax(Double price, Integer quantity) {

        return price * quantity;
    }


    //Todo:  method to calculate the order total before and after tax
    public Double calculatePriceAfterTax(Double totalBeforeTaxes) {

        Double taxAmount = .0978;
        Double totalTaxes = totalBeforeTaxes * taxAmount;

        return totalBeforeTaxes + totalTaxes;
    }
}
