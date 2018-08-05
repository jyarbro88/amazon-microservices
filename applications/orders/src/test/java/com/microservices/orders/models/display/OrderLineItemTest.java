package com.microservices.orders.models.display;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class OrderLineItemTest {

    private final OrderLineItem testItem = new OrderLineItem();

    @Test
    public void setOrderLineItemId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testItem.getClass().getDeclaredField("orderLineItemId");
        field.setAccessible(true);
        field.set(testItem, 10L);
        final Long result = testItem.getOrderLineItemId();
        assertEquals(10L, result, 10L);
    }

    @Test
    public void setProductName() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testItem.getClass().getDeclaredField("productName");
        field.setAccessible(true);
        field.set(testItem, "new name");
        final String result = testItem.getProductName();
        assertEquals("new name", result, "new name");
    }

    @Test
    public void setQuantity() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testItem.getClass().getDeclaredField("quantity");
        field.setAccessible(true);
        field.set(testItem, 55);
        final Integer result = testItem.getQuantity();
        assertEquals(55, result, 55);
    }
}