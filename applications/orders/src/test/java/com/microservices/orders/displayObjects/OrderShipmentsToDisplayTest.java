package com.microservices.orders.displayObjects;

import org.junit.Test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class OrderShipmentsToDisplayTest {

    private final OrderShipmentsToDisplay testOrderShipmentItem = new OrderShipmentsToDisplay();

    @Test
    public void setShipmentId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testOrderShipmentItem.getClass().getDeclaredField("shipmentId");
        field.setAccessible(true);
        field.set(testOrderShipmentItem, 15L);
        Long shipmentId = testOrderShipmentItem.getShipmentId();
        assertEquals(15L, shipmentId, 15L);
    }

    @Test
    public void setOrderLineItemId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testOrderShipmentItem.getClass().getDeclaredField("orderLineItemId");
        field.setAccessible(true);
        field.set(testOrderShipmentItem, 1L);
        Long orderLineItemId = testOrderShipmentItem.getOrderLineItemId();
        assertEquals(1L, orderLineItemId, 1L);
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