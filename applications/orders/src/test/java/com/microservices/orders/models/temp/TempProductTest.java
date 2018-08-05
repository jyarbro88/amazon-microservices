package com.microservices.orders.models.temp;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class TempProductTest {

    private TempProduct testItem = new TempProduct();

    @Test
    public void setProductId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testItem.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(testItem, 1L);
        Long productId = testItem.getId();
        assertEquals(1L, productId, 1L);
    }

    @Test
    public void setName() throws IllegalAccessException, NoSuchFieldException {
        final Field field = testItem.getClass().getDeclaredField("name");
        field.setAccessible(true);
        field.set(testItem, "new name");
        String name = testItem.getName();
        assertEquals("", name, "new name");
    }

    @Test
    public void setDescription() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testItem.getClass().getDeclaredField("description");
        field.setAccessible(true);
        field.set(testItem, "new desc");
        String description = testItem.getDescription();
        assertEquals("", description, "new desc");
    }

    @Test
    public void setPrice() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testItem.getClass().getDeclaredField("price");
        field.setAccessible(true);
        field.set(testItem, 2.00);
        Double price = testItem.getPrice();
        assertEquals(2.00, price, 2.00);
    }
}