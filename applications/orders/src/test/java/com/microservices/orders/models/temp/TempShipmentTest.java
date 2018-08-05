package com.microservices.orders.models.temp;

import org.junit.Test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class TempShipmentTest {

    private TempShipment testItem = new TempShipment();

    @Test
    public void setId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testItem.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(testItem, 2L);
        Long testId = testItem.getId();
        assertEquals(2L, testId, 2L);
    }

    @Test
    public void setAccountId() throws IllegalAccessException, NoSuchFieldException {
        final Field field = testItem.getClass().getDeclaredField("accountId");
        field.setAccessible(true);
        field.set(testItem, 12L);
        Long accountId = testItem.getAccountId();
        assertEquals(12L, accountId, 12L);
    }

    @Test
    public void setShippingAddressId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testItem.getClass().getDeclaredField("shippingAddressId");
        field.setAccessible(true);
        field.set(testItem, 3L);
        Long shippingAddressId = testItem.getShippingAddressId();
        assertEquals(3L, shippingAddressId, 3L);
    }

    @Test
    public void setOrderId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testItem.getClass().getDeclaredField("orderId");
        field.setAccessible(true);
        field.set(testItem, 55L);
        Long orderId = testItem.getOrderId();
        assertEquals(55L, orderId, 55L);
    }

    @Test
    public void setShippedDate() throws NoSuchFieldException, IllegalAccessException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date testDate = dateFormat.parse("01-11-2018");

        final Field field = testItem.getClass().getDeclaredField("shippedDate");
        field.setAccessible(true);
        field.set(testItem, testDate);
        Date shippedDate = testItem.getShippedDate();
        assertEquals("", shippedDate, testDate);
    }

    @Test
    public void setDeliveredDate() throws NoSuchFieldException, IllegalAccessException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date testDate = dateFormat.parse("05-31-2018");

        final Field field = testItem.getClass().getDeclaredField("deliveredDate");
        field.setAccessible(true);
        field.set(testItem, testDate);
        Date deliveredDate = testItem.getDeliveredDate();
        assertEquals("", deliveredDate, testDate);
    }
}