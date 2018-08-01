package com.microservices.orders.models;

import com.microservices.orders.models.LineItem;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class LineItemTest {

    private final LineItem testLineItem = new LineItem();

    @Test
    public void setId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testLineItem.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(testLineItem, 55L);
        final Long id = testLineItem.getId();
        assertEquals(55L, id, 55L);
    }

    @Test
    public void setProductId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testLineItem.getClass().getDeclaredField("productId");
        field.setAccessible(true);
        field.set(testLineItem, 5L);
        final Long productId = testLineItem.getProductId();
        assertEquals(5L, productId, 5L);
    }

    @Test
    public void setShipmentId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testLineItem.getClass().getDeclaredField("shipmentId");
        field.setAccessible(true);
        field.set(testLineItem, 5L);
        final Long shipmentId = testLineItem.getShipmentId();
        assertEquals(5L, shipmentId, 5L);
    }

//    @Test
//    public void setOrderId() throws NoSuchFieldException, IllegalAccessException {
//        final Field field = testLineItem.getClass().getDeclaredField("orderId");
//        field.setAccessible(true);
//        field.set(testLineItem, 5L);
//        Long orderId = testLineItem.getOrderId();
//        assertEquals(5L, orderId, 5L);
//    }

    @Test
    public void setQuantity() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testLineItem.getClass().getDeclaredField("quantity");
        field.setAccessible(true);
        field.set(testLineItem, 52);
        Integer quantity = testLineItem.getQuantity();
        assertEquals(52, quantity, 52);
    }

    @Test
    public void setSingleItemPrice() throws IllegalAccessException, NoSuchFieldException {
        final Field field = testLineItem.getClass().getDeclaredField("singleItemPrice");
        field.setAccessible(true);
        field.set(testLineItem, 3.99);
        Double singleItemPrice = testLineItem.getSingleItemPrice();
        assertEquals(3.99, singleItemPrice, 3.99);
    }

    @Test
    public void setLineItemTotalPrice() throws IllegalAccessException, NoSuchFieldException {
        final Field field = testLineItem.getClass().getDeclaredField("lineItemTotalPrice");
        field.setAccessible(true);
        field.set(testLineItem, 32.99);
        Double lineItemTotalPrice = testLineItem.getLineItemTotalPrice();
        assertEquals(32.99, lineItemTotalPrice, 32.99);
    }
}