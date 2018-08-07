package com.microservices.orders.models.display;

import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DisplayOrderDetailsTest {

    private final DisplayOrderDetails testOrderItem = new DisplayOrderDetails();

    @Test
    public void setOrderNumber() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testOrderItem.getClass().getDeclaredField("orderNumber");
        field.setAccessible(true);
        field.set(testOrderItem, 15L);
        Long orderNumber = testOrderItem.getOrderNumber();
        assertEquals(15L, orderNumber, 15L);
    }

    @Test
    public void setOrderTotalPrice() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testOrderItem.getClass().getDeclaredField("orderTotalPrice");
        field.setAccessible(true);
        field.set(testOrderItem, 15.99);
        Double orderTotalPrice = testOrderItem.getOrderTotalPrice();
        assertEquals(15.99, orderTotalPrice, 15.99);
    }

    @Test
    public void setShippingAddress() throws NoSuchFieldException, IllegalAccessException {
        DisplayOrderAddress mockAddress = Mockito.mock(DisplayOrderAddress.class);
        final Field field = testOrderItem.getClass().getDeclaredField("shippingAddress");
        field.setAccessible(true);
        field.set(testOrderItem, mockAddress);
        DisplayOrderAddress shippingAddress = testOrderItem.getShippingAddress();
        assertEquals("", mockAddress, shippingAddress);
    }

//    @Test
//    public void setLineItemsToDisplay() throws NoSuchFieldException, IllegalAccessException {
//        DisplayOrderLineItem mockLineItems = Mockito.mock(DisplayOrderLineItem.class);
//        DisplayOrderLineItem mockLineItems2 = Mockito.mock(DisplayOrderLineItem.class);
//        DisplayOrderLineItem mockLineItems3 = Mockito.mock(DisplayOrderLineItem.class);
//        List<DisplayOrderLineItem> mockList = new ArrayList<>();
//
//        mockList.add(mockLineItems);
//        mockList.add(mockLineItems2);
//        mockList.add(mockLineItems3);
//
//        final Field field = testOrderItem.getClass().getDeclaredField("orderLineItemsList");
//        field.setAccessible(true);
//        field.set(testOrderItem, mockList);
//        List<DisplayOrderLineItem> lineItemsToDisplay = testOrderItem.getDisplayOrderLineItemsList();
//        assertEquals("", mockList, lineItemsToDisplay);
//    }

//    @Test
//    public void setOrderShipmentsToDisplayList() throws NoSuchFieldException, IllegalAccessException {
//        DisplayOrderShipment mockDisplayOrderShipments = Mockito.mock(DisplayOrderShipment.class);
//        DisplayOrderShipment mockDisplayOrderShipments2 = Mockito.mock(DisplayOrderShipment.class);
//        DisplayOrderShipment mockDisplayOrderShipments3 = Mockito.mock(DisplayOrderShipment.class);
//        List<DisplayOrderShipment> mockList = new ArrayList<>();
//
//        mockList.add(mockDisplayOrderShipments);
//        mockList.add(mockDisplayOrderShipments2);
//        mockList.add(mockDisplayOrderShipments3);
//
//        final Field field = testOrderItem.getClass().getDeclaredField("orderShipmentsList");
//        field.setAccessible(true);
//        field.set(testOrderItem, mockList);
//        List<DisplayOrderShipment> displayOrderShipmentsList = testOrderItem.getDisplayOrderShipmentsList();
//        assertEquals("", mockList, displayOrderShipmentsList);
//    }
}