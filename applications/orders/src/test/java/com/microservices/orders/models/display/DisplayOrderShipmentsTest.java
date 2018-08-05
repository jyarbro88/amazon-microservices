package com.microservices.orders.models.display;

import org.junit.Test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class DisplayOrderShipmentsTest {

    private final DisplayOrderShipments testOrderShipmentItem = new DisplayOrderShipments();

    @Test
    public void setShipmentId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testOrderShipmentItem.getClass().getDeclaredField("shipmentId");
        field.setAccessible(true);
        field.set(testOrderShipmentItem, 15L);
        Long shipmentId = testOrderShipmentItem.getShipmentId();
        assertEquals(15L, shipmentId, 15L);
    }

    @Test
    public void setOrderId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testOrderShipmentItem.getClass().getDeclaredField("orderId");
        field.setAccessible(true);
        field.set(testOrderShipmentItem, 1L);
        Long orderId = testOrderShipmentItem.getOrderId();
        assertEquals(1L, orderId, 1L);
    }

    @Test
    public void setShippedDate() throws NoSuchFieldException, IllegalAccessException, ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date testDate = dateFormat.parse("05-12-2018");

        final Field field = testOrderShipmentItem.getClass().getDeclaredField("shippedDate");
        field.setAccessible(true);
        field.set(testOrderShipmentItem, testDate);
        Date shippedDate = testOrderShipmentItem.getShippedDate();

        assertEquals("", shippedDate, testDate);
    }

    @Test
    public void setDeliveryDate() throws NoSuchFieldException, IllegalAccessException, ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date testDate = dateFormat.parse("05-12-2018");

        final Field field = testOrderShipmentItem.getClass().getDeclaredField("deliveryDate");
        field.setAccessible(true);
        field.set(testOrderShipmentItem, testDate);
        Date deliveryDate = testOrderShipmentItem.getDeliveryDate();
        assertEquals("", deliveryDate, testDate);
    }
}