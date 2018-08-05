package com.microservices.orders.models;

import org.junit.Test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class OrderTest {

    private final Order testLineItem = new Order();

    @Test
    public void setId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testLineItem.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(testLineItem, 62L);
        Long id = testLineItem.getId();
        assertEquals(62L, id, 62L);
    }

    @Test
    public void setAccountId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testLineItem.getClass().getDeclaredField("accountId");
        field.setAccessible(true);
        field.set(testLineItem, 622L);
        Long accountId = testLineItem.getAccountId();
        assertEquals(622L, accountId, 622L);
    }

    @Test
    public void setOrderDate() throws NoSuchFieldException, IllegalAccessException, ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date testDate = dateFormat.parse("05-12-2015");

        final Field field = testLineItem.getClass().getDeclaredField("orderDate");
        field.setAccessible(true);
        field.set(testLineItem, testDate);
        Date orderDate = testLineItem.getOrderDate();
        assertEquals("", orderDate, testDate);
    }

    @Test
    public void setShippingAddressId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testLineItem.getClass().getDeclaredField("shippingAddressId");
        field.setAccessible(true);
        field.set(testLineItem, 44L);
        Long shippingAddressId = testLineItem.getShippingAddressId();
        assertEquals(44L, shippingAddressId, 44L);
    }

    @Test
    public void setBillingAddressId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testLineItem.getClass().getDeclaredField("billingAddressId");
        field.setAccessible(true);
        field.set(testLineItem, 76L);
        Long billingAddressId = testLineItem.getBillingAddressId();
        assertEquals(76L, billingAddressId, 76L);
    }

    @Test
    public void setTotalPrice() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testLineItem.getClass().getDeclaredField("totalPrice");
        field.setAccessible(true);
        field.set(testLineItem, 32.99);
        Double totalPrice = testLineItem.getTotalPrice();
        assertEquals(32.99, totalPrice, 32.99);
    }

    @Test
    public void setLineItems() throws IllegalAccessException, NoSuchFieldException {

        List<LineItem> lineItemsTestList = new ArrayList<>();
        LineItem lineItem = new LineItem();
        lineItem.setOrderId(3L);
        lineItem.setShipmentId(4L);
        lineItem.setQuantity(3);
        lineItem.setProductId(4L);
        lineItem.setId(1L);
        lineItem.setSingleItemPrice(3.00);
        lineItem.setLineItemTotalPrice(9.00);

        lineItemsTestList.add(lineItem);

        final Field field = testLineItem.getClass().getDeclaredField("lineItems");
        field.setAccessible(true);
        field.set(testLineItem, lineItemsTestList);
        List<LineItem> lineItems = testLineItem.getLineItems();
        assertEquals("", lineItemsTestList, lineItems);
    }
}