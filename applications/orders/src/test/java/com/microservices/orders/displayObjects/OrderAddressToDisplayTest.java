package com.microservices.orders.displayObjects;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class OrderAddressToDisplayTest {

    private final OrderAddressToDisplay testAddressItem = new OrderAddressToDisplay();

    @Test
    public void setShippingAddressId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testAddressItem.getClass().getDeclaredField("shippingAddressId");
        field.setAccessible(true);
        field.set(testAddressItem, 5L);
        Long shippingAddressId = testAddressItem.getShippingAddressId();
        assertEquals(5L, shippingAddressId, 5L);
    }

    @Test
    public void setAddressOne() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testAddressItem.getClass().getDeclaredField("addressOne");
        field.setAccessible(true);
        field.set(testAddressItem, "811 e stone ct");
        String addressOne = testAddressItem.getAddressOne();
        assertEquals("", addressOne, "811 e stone ct");
    }

    @Test
    public void setAddressTwo() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testAddressItem.getClass().getDeclaredField("addressTwo");
        field.setAccessible(true);
        field.set(testAddressItem, "apt 3");
        String addressTwo = testAddressItem.getAddressTwo();
        assertEquals("", addressTwo, "apt 3");
    }

    @Test
    public void setCity() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testAddressItem.getClass().getDeclaredField("city");
        field.setAccessible(true);
        field.set(testAddressItem, "Addison");
        String city = testAddressItem.getCity();
        assertEquals("", city, "Addison");
    }

    @Test
    public void setState() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testAddressItem.getClass().getDeclaredField("state");
        field.setAccessible(true);
        field.set(testAddressItem, "IL");
        String state = testAddressItem.getState();
        assertEquals("", state, "IL");
    }

    @Test
    public void setZipCode() throws IllegalAccessException, NoSuchFieldException {
        final Field field = testAddressItem.getClass().getDeclaredField("zipCode");
        field.setAccessible(true);
        field.set(testAddressItem, "60101");
        String zipCode = testAddressItem.getZipCode();
        assertEquals("", zipCode, "60101");
    }

    @Test
    public void setCountry() throws IllegalAccessException, NoSuchFieldException {
        final Field field = testAddressItem.getClass().getDeclaredField("country");
        field.setAccessible(true);
        field.set(testAddressItem, "USA");
        String country = testAddressItem.getCountry();
        assertEquals("", country, "USA");
    }
}